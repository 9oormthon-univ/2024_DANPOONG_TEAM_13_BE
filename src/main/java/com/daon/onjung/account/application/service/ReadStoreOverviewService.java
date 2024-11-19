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
            List<EOnjungTag> tags,
            String sortByDonationCount
    ) {
        Pageable pageable = PageRequest.of(page - 1, size,
                sortByDonationCount.equalsIgnoreCase("asc") ? Sort.by("donationCount").ascending() : Sort.by("donationCount").descending());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), (int) storeRepository.count());

        List<Store> storeList = storeRepository.findByTitleContainingAndTagsIn(title, tags, pageable).getContent();

        Page<Store> storePage = new PageImpl<>(storeList.subList(start, end), pageable, storeList.size());

        return ReadStoreOverviewsResponseDto.fromPage(storePage);
    }
}
