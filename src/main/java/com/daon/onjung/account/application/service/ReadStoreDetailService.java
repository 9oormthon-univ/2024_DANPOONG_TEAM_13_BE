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
import com.daon.onjung.core.utility.RestClientUtil;
import com.daon.onjung.event.domain.Event;
import com.daon.onjung.event.domain.service.EventService;
import com.daon.onjung.event.repository.mysql.EventRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Integer totalAmount = getTotalAmount(id);

        ReadStoreDetailResponseDto.EventInfoDto eventInfoDto = ReadStoreDetailResponseDto.EventInfoDto.of(event.getId(), totalAmount, eventService.getRestOfDate(event));

        // onjung 정보 (null 값 대신 0으로
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

        List<StoreHistory> storeHistories = storeHistoryRepository.findByStoreOrderByActionDateAsc(store);

        List<ReadStoreDetailResponseDto.StoreHistoryDto> storeHistoryDtos = storeHistories.stream()
                .map(ReadStoreDetailResponseDto.StoreHistoryDto::fromEntity)
                .toList();

        return ReadStoreDetailResponseDto.fromEntity(storeInfoDto, eventInfoDto, onjungInfoDto, storeHistoryDtos);
    }

    // bank api 호출
    private Integer getTotalAmount(Long storeId) {
        String url = bankUtil.createReadVirtualAccountRequestUrl(storeId);

        HttpHeaders headers = bankUtil.createVirtualAccountRequestHeaders();

        JSONObject jsonObject = restClientUtil.sendGetMethod(url, headers);

        ReadVirtualAccountResponseDto readVirtualAccountResponseDto = bankUtil.mapToReadVirtualAccountResponseDto(jsonObject);

        return readVirtualAccountResponseDto.data().balance();
    }
}
