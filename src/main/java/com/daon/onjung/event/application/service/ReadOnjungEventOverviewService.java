package com.daon.onjung.event.application.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.event.application.dto.response.ReadOnjungEventOverviewResponseDto;
import com.daon.onjung.event.application.usecase.ReadOnjungEventOverviewUseCase;
import com.daon.onjung.event.domain.Event;
import com.daon.onjung.event.repository.mysql.EventRepository;
import com.daon.onjung.onjung.domain.type.EOnjungType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadOnjungEventOverviewService implements ReadOnjungEventOverviewUseCase {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final StoreRepository storeRepository;

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

        // store와 onjung 객체를 매핑한 데이터 조회
        List<Object[]> allRelationByUser = storeRepository.findStoreActionsByUser(user);

        List<ReadOnjungEventOverviewResponseDto.EventDto> eventDtos = allRelationByUser.stream()
                .map(row -> {
                    // Object[] 배열에서 데이터 추출
                    Store store = (Store) row[0];       // 첫 번째 요소: Store 객체
                    String relationType = (String) row[1]; // 두 번째 요소: Relation Type (DONATION, RECEIPT, SHARE)

                    // Store에 해당하는 가장 최근 이벤트 가져오기
                    Event event = eventRepository.findMostRecentEventByStore(store);

                    return ReadOnjungEventOverviewResponseDto.EventDto.fromEntity(
                            ReadOnjungEventOverviewResponseDto.EventDto.StoreInfoDto.fromEntity(
                                    store.getLogoImgUrl(),
                                    store.getTitle(),
                                    store.getName()
                            ),
                            EOnjungType.fromString(relationType),
                            event.getStatus(),
                            DateTimeUtil.convertLocalDatesToDotSeparatedDatePeriod(event.getStartDate(), event.getEndDate()),
                            DateTimeUtil.convertLocalDateToDotSeparatedDateTime(event.getStoreDeliveryDate()),
                            DateTimeUtil.convertLocalDateToDotSeparatedDateTime(event.getTicketIssueDate()),
                            DateTimeUtil.convertLocalDateToDotSeparatedDateTime(event.getReportDate())
                    );
                })
                .filter(Objects::nonNull) // null 값 제거
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
