package com.daon.onjung.event.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.event.application.dto.response.ReadOnjungEventOverviewResponseDto;
import com.daon.onjung.event.application.usecase.ReadOnjungEventOverviewUseCase;
import com.daon.onjung.event.domain.Event;
import com.daon.onjung.event.domain.type.EStatus;
import com.daon.onjung.event.repository.mysql.EventRepository;
import com.daon.onjung.onjung.domain.Donation;
import com.daon.onjung.onjung.domain.Onjung;
import com.daon.onjung.onjung.domain.Receipt;
import com.daon.onjung.onjung.domain.Share;
import com.daon.onjung.onjung.domain.service.OnjungService;
import com.daon.onjung.onjung.domain.type.EOnjungType;
import com.daon.onjung.onjung.repository.mysql.DonationRepository;
import com.daon.onjung.onjung.repository.mysql.ReceiptRepository;
import com.daon.onjung.onjung.repository.mysql.ShareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadOnjungEventOverviewService implements ReadOnjungEventOverviewUseCase {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final DonationRepository donationRepository;
    private final ReceiptRepository receiptRepository;
    private final ShareRepository shareRepository;

    private final OnjungService onjungService;

    @Override
    @Transactional(readOnly = true)
    public ReadOnjungEventOverviewResponseDto execute(
            Integer page,
            Integer size,
            UUID accountId
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<Donation> donations = donationRepository.findAllByUser(user);
        List<Receipt> receipts = receiptRepository.findAllByUser(user);
        List<Share> shares = shareRepository.findAllByUser(user);

        Onjung onjung = onjungService.createOnjung(donations, receipts, shares);

        List<Object> sortedOnjungByCreatedAt = onjungService.sortOnjungByCreatedAt(onjung);

        List<ReadOnjungEventOverviewResponseDto.EventDto> eventDtos = sortedOnjungByCreatedAt.stream()
                .map(entity -> {
                    if (entity instanceof Donation donation) {
                    // donation 날짜가 포함된 이벤트 가져오기
                        Event event = eventRepository.findTopEventByStoreAndLocalDate(donation.getStore().getId(), donation.getCreatedAt().toLocalDate())
                                .stream()
                                .findFirst() // 첫 번째 값만 가져옴
                                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

                        return ReadOnjungEventOverviewResponseDto.EventDto.fromEntity(
                            ReadOnjungEventOverviewResponseDto.EventDto.StoreInfoDto.fromEntity(
                                    donation.getStore().getId(),
                                    donation.getStore().getLogoImgUrl(),
                                    donation.getStore().getTitle(),
                                    donation.getStore().getName()
                            ),
                            DateTimeUtil.convertLocalDateTimeToSHORTKORString(donation.getCreatedAt()),
                            EOnjungType.fromString("DONATION"),
                            event.getStatus(),
                            DateTimeUtil.convertLocalDatesToDotSeparatedDatePeriod(event.getStartDate(), event.getEndDate()),
                            DateTimeUtil.convertLocalDateToDotSeparatedDateTime(event.getStoreDeliveryDate()),
                            DateTimeUtil.convertLocalDateToDotSeparatedDateTime(event.getTicketIssueDate()),
                            DateTimeUtil.convertLocalDateToDotSeparatedDateTime(event.getReportDate())
                    );
                    } else if (entity instanceof Receipt receipt) {
                        return ReadOnjungEventOverviewResponseDto.EventDto.fromEntity(
                            ReadOnjungEventOverviewResponseDto.EventDto.StoreInfoDto.fromEntity(
                                    receipt.getStore().getId(),
                                    receipt.getStore().getLogoImgUrl(),
                                    receipt.getStore().getTitle(),
                                    receipt.getStore().getName()
                            ),
                                DateTimeUtil.convertLocalDateTimeToSHORTKORString(receipt.getCreatedAt()),
                            EOnjungType.fromString("RECEIPT"),
                                EStatus.COMPLETED,
                                DateTimeUtil.convertLocalDateToDotSeparatedDateTime(receipt.getCreatedAt().toLocalDate()),
                            null,
                            null,
                            null
                    );
                    } else if (entity instanceof Share share) {
                        return ReadOnjungEventOverviewResponseDto.EventDto.fromEntity(
                            ReadOnjungEventOverviewResponseDto.EventDto.StoreInfoDto.fromEntity(
                                    share.getStore().getId(),
                                    share.getStore().getLogoImgUrl(),
                                    share.getStore().getTitle(),
                                    share.getStore().getName()
                            ),
                                DateTimeUtil.convertLocalDateTimeToSHORTKORString(share.getCreatedAt().atTime(0, 0)),
                            EOnjungType.fromString("SHARE"),
                                EStatus.COMPLETED,
                                DateTimeUtil.convertLocalDateToDotSeparatedDateTime(share.getCreatedAt()),
                                null,
                                null,
                                null
                    );
                    }
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                })
                .collect(Collectors.toList());


        // 페이지네이션
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), eventDtos.size());

        // 페이지 범위가 유효한지 확인
        if (start >= eventDtos.size()) {
            return ReadOnjungEventOverviewResponseDto.fromPage(
                    List.of(),
                    false // hasNext 계산
            );
        }

        List<ReadOnjungEventOverviewResponseDto.EventDto> pagedEventDtos = eventDtos.subList(start, end);

        return ReadOnjungEventOverviewResponseDto.fromPage(
                pagedEventDtos,
                end < eventDtos.size() // hasNext 계산
        );
    }
}
