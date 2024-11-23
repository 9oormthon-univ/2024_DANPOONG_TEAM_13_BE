package com.daon.onjung.account.domain.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.StoreHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StoreHistoryService {
    public StoreHistory createStoreHistory(
            LocalDate actionDate,
            String content,
            String amount,
            Store store
    ) {
        return StoreHistory.builder()
                .actionDate(actionDate)
                .content(content)
                .amount(amount)
                .store(store)
                .build();
    }
}
