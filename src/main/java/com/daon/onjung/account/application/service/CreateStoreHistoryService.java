package com.daon.onjung.account.application.service;

import com.daon.onjung.account.application.dto.request.CreateStoreHistoryRequestDto;
import com.daon.onjung.account.application.usecase.CreateStoreHistoryUseCase;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.StoreHistory;
import com.daon.onjung.account.domain.service.StoreHistoryService;
import com.daon.onjung.account.repository.mysql.StoreHistoryRepository;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateStoreHistoryService implements CreateStoreHistoryUseCase {

    private final StoreRepository storeRepository;
    private final StoreHistoryRepository storeHistoryRepository;

    private final StoreHistoryService storeHistoryService;

    @Override
    @Transactional
    public void execute(CreateStoreHistoryRequestDto requestDto) {
        Store store = storeRepository.findById(requestDto.storeId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        StoreHistory storeHistory = storeHistoryService.createStoreHistory(
                requestDto.actionDate(),
                requestDto.content(),
                requestDto.amount(),
                store
        );

        storeHistoryRepository.save(storeHistory);
    }
}
