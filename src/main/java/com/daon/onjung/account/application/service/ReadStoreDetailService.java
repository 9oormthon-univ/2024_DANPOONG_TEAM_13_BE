package com.daon.onjung.account.application.service;

import com.daon.onjung.account.application.dto.response.ReadStoreDetailResponseDto;
import com.daon.onjung.account.application.usecase.ReadStoreDetailUseCase;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.StoreHistory;
import com.daon.onjung.account.repository.mysql.StoreHistoryRepository;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.core.dto.ReadVirtualAccountResponseDto;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.BankUtil;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.core.utility.RestClientUtil;
import com.daon.onjung.event.domain.mysql.Event;
import com.daon.onjung.event.domain.service.EventService;
import com.daon.onjung.event.repository.mysql.EventRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReadStoreDetailService implements ReadStoreDetailUseCase {
    private final StoreRepository storeRepository;
    private final StoreHistoryRepository storeHistoryRepository;
    private final EventRepository eventRepository;

    private final EventService eventService;

    private final BankUtil bankUtil;
    private final RestClientUtil restClientUtil;

    @Override
    @Transactional(readOnly = true)
    public ReadStoreDetailResponseDto execute(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        Event event = eventRepository.findINPROGRESSEventByStoreId(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 상점 정보
        ReadStoreDetailResponseDto.StoreInfoDto storeInfoDto = ReadStoreDetailResponseDto.StoreInfoDto.fromEntity(store);

        // event 정보
        Integer totalAmount = getTotalAmount(event.getBankId());

        ReadStoreDetailResponseDto.EventInfoDto eventInfoDto = ReadStoreDetailResponseDto.EventInfoDto.of(totalAmount, eventService.getRestOfDate(event));

        // onjung 정보 (null 값 대신 0으로)
        Integer totalOnjungCount = Optional.ofNullable(storeRepository.countUsersByStoreId(id)).orElse(0);
        Integer totalDonationAmount = Optional.ofNullable(storeRepository.sumDonationAmountByStoreId(id)).orElse(0);
        Integer totalReceiptAmount = Optional.ofNullable(storeRepository.sumReceiptAmountByStoreId(id)).orElse(0);
        Integer totalShareCount = Optional.ofNullable(storeRepository.sumShareAmountByStoreId(id)).orElse(0);

        ReadStoreDetailResponseDto.OnjungInfoDto onjungInfoDto = ReadStoreDetailResponseDto.OnjungInfoDto.fromEntity(
                totalOnjungCount,
                totalDonationAmount,
                totalReceiptAmount,
                totalShareCount
        );

        List<StoreHistory> storeHistories = storeHistoryRepository.findByStoreSortedByActionDate(store);

        // 그룹화: actionDate를 기준으로 그룹화
        Map<String, List<StoreHistory>> groupedByYearMonth = storeHistories.stream()
                .collect(Collectors.groupingBy(
                        sh -> DateTimeUtil.convertLocalDateToKORYearMonthString(sh.getActionDate()) // "yyyy년 MM월" 기준 그룹화
                ));


        List<ReadStoreDetailResponseDto.StoreHistoryDto> storeHistoryDtos = groupedByYearMonth.entrySet().stream()
                .sorted((e1, e2) -> e2.getKey().compareTo(e1.getKey())) // 키(yearMonth)를 기준으로 내림차순 정렬
                .map(entry -> {
                    String yearMonth = entry.getKey();

                    // 그룹화된 리스트를 actionDate 기준으로 내림차순 정렬
                    List<ReadStoreDetailResponseDto.StoreHistoryDto.StoreHistoryInfo> infos = entry.getValue().stream()
                            .sorted((sh1, sh2) -> sh2.getActionDate().compareTo(sh1.getActionDate())) // actionDate 기준 정렬
                            .map(ReadStoreDetailResponseDto.StoreHistoryDto::fromEntity) // StoreHistory -> StoreHistoryDto 변환
                            .flatMap(dto -> dto.getStoreHistoryInfo().stream()) // StoreHistoryInfo만 추출
                            .toList();

                    return ReadStoreDetailResponseDto.StoreHistoryDto.builder()
                            .date(yearMonth)
                            .storeHistoryInfo(infos)
                            .build();
                })
                .toList();


        return ReadStoreDetailResponseDto.fromEntity(storeInfoDto, eventInfoDto, onjungInfoDto, storeHistoryDtos);
    }

    // bank api 호출
    private Integer getTotalAmount(Long eventId) {
        String url = bankUtil.createReadVirtualAccountRequestUrl(eventId);

        HttpHeaders headers = bankUtil.createVirtualAccountRequestHeaders();

        JSONObject jsonObject = restClientUtil.sendGetMethod(url, headers);

        ReadVirtualAccountResponseDto readVirtualAccountResponseDto = bankUtil.mapToReadVirtualAccountResponseDto(jsonObject);

        return readVirtualAccountResponseDto.data().balance();
    }
}
