package com.daon.onjung.account.application.service;

import com.daon.onjung.account.application.dto.response.ReadStoreOverviewsResponseDto;
import com.daon.onjung.account.application.usecase.ReadStoreOverviewUseCase;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.service.StoreService;
import com.daon.onjung.account.domain.type.EOnjungTag;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadStoreOverviewService implements ReadStoreOverviewUseCase {
    private final StoreRepository storeRepository;

    private final StoreService storeService;

    @Override
    @Transactional(readOnly = true)
    public ReadStoreOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String title,
            String onjungTags,
            String sortByOnjungCount
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        // 필터 파라미터 변환
        List<EOnjungTag> onjungTagsList = storeService.convertToOnjungTags(onjungTags);

        // title null 처리
        title = storeService.convertToTitle(title);

        List<Store> storeList = storeRepository.findStoresByEarliestEventOrdered(title, onjungTagsList);

        // 상점이 없을 경우
        if (storeList.isEmpty()) {
            return new ReadStoreOverviewsResponseDto(false, List.of());
        }

        // total onjung count별 정렬
        List<ReadStoreOverviewsResponseDto.StoreOverviewDto> storeOverviewDtos = storeList.stream()
                .map(store -> {
                    long shareCount = storeRepository.countSharesByStoreId(store.getId());
                    long donationCount = storeRepository.countDonationsByStoreId(store.getId());
                    long receiptCount = storeRepository.countReceiptsByStoreId(store.getId());

                    Integer totalOnjungCount = (int) (shareCount + donationCount + receiptCount);

                    return ReadStoreOverviewsResponseDto.StoreOverviewDto.fromEntity(store, totalOnjungCount);
                })
                .sorted((o1, o2) -> {
                    // 대소문자 구분 삭제
                    if ("asc".equalsIgnoreCase(sortByOnjungCount)) {
                        return Integer.compare(o1.getTotalOnjungCount(), o2.getTotalOnjungCount());
                    } else {
                        return Integer.compare(o2.getTotalOnjungCount(), o1.getTotalOnjungCount());
                    }
                })
                .toList();

        // 페이지네이션
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), storeOverviewDtos.size());
        List<ReadStoreOverviewsResponseDto.StoreOverviewDto> pagedStoreOverviewDtos = storeOverviewDtos.subList(start, end);

        // 응답 생성 - 변경
        return ReadStoreOverviewsResponseDto.fromEntity(
                pagedStoreOverviewDtos,
                end < storeList.getTotalElements() // hasNext 계산
        );
    }
}
