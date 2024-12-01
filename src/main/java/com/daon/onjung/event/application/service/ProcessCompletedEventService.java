package com.daon.onjung.event.application.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.domain.type.EBankName;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.dto.CreateVirtualAccountResponseDto;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.BankUtil;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.core.utility.FirebaseUtil;
import com.daon.onjung.core.utility.RestClientUtil;
import com.daon.onjung.event.application.usecase.ProcessCompletedEventUseCase;
import com.daon.onjung.event.domain.mysql.Event;
import com.daon.onjung.event.domain.mysql.Ticket;
import com.daon.onjung.event.domain.service.EventService;
import com.daon.onjung.event.domain.service.ScheduledEventJobService;
import com.daon.onjung.event.domain.service.TicketService;
import com.daon.onjung.event.repository.mysql.EventRepository;
import com.daon.onjung.event.repository.mysql.TicketRepository;
import com.daon.onjung.event.repository.redis.ScheduledEventJobRepository;
import com.daon.onjung.onjung.repository.mysql.DonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessCompletedEventService implements ProcessCompletedEventUseCase {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final DonationRepository donationRepository;
    private final ScheduledEventJobRepository scheduledEventJobRepository;

    private final EventService eventService;
    private final TicketService ticketService;
    private final ScheduledEventJobService scheduledEventJobService;

    private final RestClientUtil restClientUtil;
    private final BankUtil bankUtil;
    private final FirebaseUtil firebaseUtil;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(Long eventId) {

        // eventId를 통해 ScheduledEventJob을 삭제
        scheduledEventJobRepository.findByEventId(eventId)
                .ifPresent(scheduledEventJobRepository::delete);
        log.info("ScheduledEventJob 삭제 완료. eventId: {}", eventId);

        // 이벤트 조회
        Event currentEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 가게 조회
        Store store = currentEvent.getStore();

        // 현재 진행중인 이벤트의 상태를 모금완료로 변경
        currentEvent = eventService.completeEvent(currentEvent);
        eventRepository.save(currentEvent);

        // 새로운 이벤트 생성
        Event newEvent = eventService.createEvent(
                LocalDate.now(),
                LocalDate.now().plusDays(13),
                store
        );
        newEvent = eventRepository.save(newEvent);

        // 가상 계좌 생성
        String url = bankUtil.createCreateVirtualAccountRequestUrl();
        HttpHeaders headers = bankUtil.createVirtualAccountRequestHeaders();
        String body = bankUtil.createCreateVirtualAccountRequestBody(newEvent.getId(), EBankName.KAKAO.toString());

        CreateVirtualAccountResponseDto createVirtualAccountResponseDto =
                bankUtil.mapToCreateVirtualAccountResponseDto(restClientUtil.sendPostMethod(url, headers, body));

        // 이벤트에 은행 정보 업데이트
        newEvent.updateBankInfo(
                EBankName.fromString(createVirtualAccountResponseDto.data().bankName()),
                createVirtualAccountResponseDto.data().bankId()
        );
        eventRepository.save(newEvent);

        // 새롭게 생성된 이벤트에 대한 종료일자에 맞춘 이벤트 발행. 발행한 이벤트는 이벤트 리스너에 의해 스케줄러에 등록됨
        applicationEventPublisher.publishEvent(
                scheduledEventJobService.createScheduledJob(
                        newEvent.getId(),
                        newEvent.getEndDate().plusDays(1).atStartOfDay()
//                        LocalDateTime.now().plusMinutes(1) // 테스트용 1분 뒤
                )
        );

        // 종료된 이벤트와 연결된 가상계좌에 모급된 금액을 조회
        headers = bankUtil.createVirtualAccountRequestHeaders();
        url = bankUtil.createReadVirtualAccountRequestUrl(currentEvent.getBankId());
        Integer totalBalance = bankUtil.mapToReadVirtualAccountResponseDto(restClientUtil.sendGetMethod(url, headers)).data().balance();

        // 종료된 이벤트와 연결된 가상계좌에 모금된 금액을 고용주 계좌에 이체
        headers = bankUtil.createVirtualAccountRequestHeaders();
        url = bankUtil.createTransferVirtualAccountRequestUrl(currentEvent.getBankId());
        String requestBody = bankUtil.createTransferVirtualAccountRequestBody(totalBalance, store.getOwner().getBankAccountNumber());
        bankUtil.mapToDepositOrTransferVirtualAccountResponseDto(restClientUtil.sendPostMethod(url, headers, requestBody));

        // 발행 가능한 식권
        int ticketNumber = totalBalance / 10000;

        // 발행 가능한 식권만큼 랜덤한 유저 선택
        List<UUID> userIds = userRepository.findAllUserIds();
        Random random = new Random();

        // 이미 티켓을 발급받은 유저를 저장
        Set<UUID> issuedUserIds = new HashSet<>();
        int issuedTickets = 0; // 발급된 티켓 개수

        // 발급 가능한 티켓 수만큼 루프
        while (issuedTickets < ticketNumber) {
            if (issuedUserIds.size() == userIds.size()) {
                // 모든 유저가 티켓을 발급받은 경우 루프 종료
                break;
            }

            // 랜덤하게 유저 선택
            UUID userId = userIds.get(random.nextInt(userIds.size()));
            int userSize = userIds.size();
            int cursor = 0;
            // 해당 이벤트에 대해 동참하기를 한 유저인지 확인. 동참하기를 한 유저라면 티켓 발급 안하고 다음 유저를 뽑음
            while(cursor < userSize) {
                if (donationRepository.findByUserIdAndEventId(userId, eventId).isEmpty())
                    break;
                userId = userIds.get(random.nextInt(userIds.size()));
                cursor++;
            }

            if (cursor == userSize) {
                // 더 이상 동참하기 안한 유저를 찾을 수 없는 경우 티켓 발급 종료
                log.info("-----------------더 이상 동참하기 안한 유저를 찾을 수 없음. 티켓 발급 종료--------------------");
                break;
            }

            // 이미 티켓을 발급받은 유저인지 확인
            if (!issuedUserIds.contains(userId)) {

                User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

                // 티켓 발급
                Ticket ticket = ticketService.createTicket(
                        LocalDate.now().plusDays(30),
                        10000,
                        true,
                        store,
                        user,
                        currentEvent
                );
                ticketRepository.save(ticket);

                // 유저의 디바이스 토큰 여부 확인
                String deviceToken = user.getDeviceToken();
                if (deviceToken == null) {
                    log.info("유저 {}의 디바이스 토큰이 없어 푸시 알림 발송 불가", user.getNickName());
                }
                else {
                    // 푸시 알림 발송
                    url = firebaseUtil.createFirebaseRequestUrl();
                    headers = firebaseUtil.createFirebaseRequestHeaders();
                    String firebaseRequestBody = firebaseUtil.createFirebaseRequestBody(
                            deviceToken,
                            store.getName(),
                            user.getNickName(),
                            store.getCategory().toString(),
                            store.getOcrStoreAddress(),
                            store.getLogoImgUrl(),
                            DateTimeUtil.convertLocalDateToDotSeparatedDateTime(ticket.getExpirationDate())
                    );
                    restClientUtil.sendPostMethod(url, headers, firebaseRequestBody);
                }

                // 발급된 유저 기록 및 발급된 티켓 수 증가
                issuedUserIds.add(userId);
                issuedTickets++;
                log.info("유저 {}에게 티켓 발급 완료", userId);

            }
        }
    }
}
