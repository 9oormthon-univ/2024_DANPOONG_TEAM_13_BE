package com.daon.onjung.account.application.service;

import com.daon.onjung.account.application.dto.response.ReadStoreOverviewsResponseDto;
import com.daon.onjung.account.application.usecase.ReadStoreOverviewUseCase;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.EOnjungTag;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReadStoreOverviewService implements ReadStoreOverviewUseCase {
    private final StoreRepository storeRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadStoreOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String title,
            String onjungTags,
            String sortByDonationCount
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), (int) storeRepository.count());

        // 필터 파라미터 변환
        List<EOnjungTag> onjungTagsList = parseEnums(onjungTags, EOnjungTag.class);

        List<Store> storeList = storeRepository.findStoresByDonationCountWithDirection(title, onjungTagsList, sortByDonationCount, pageable).getContent();

        // 상점이 없을 경우
        if (storeList.isEmpty()) {
            return new ReadStoreOverviewsResponseDto(false, List.of());
        }

        Page<Store> storePage = new PageImpl<>(storeList.subList(start, end), pageable, storeList.size());

        return ReadStoreOverviewsResponseDto.fromPage(storePage);
    }

    private <E extends Enum<E>> List<E> parseEnums(String input, Class<E> enumClass) {
        if (input == null || input.isEmpty()) return null;
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(value -> Enum.valueOf(enumClass, value))
                .toList();
    }
}
