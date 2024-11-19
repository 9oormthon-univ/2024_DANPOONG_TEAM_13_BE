package com.daon.onjung.onjung.domain.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.onjung.domain.Receipt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReceiptService {

    public Receipt createReceipt(
            LocalDate paymentDate,
            Integer paymentAmount,
            User user,
            Store store
    ) {
        return Receipt.builder()
                .paymentAmount(paymentAmount)
                .paymentDate(paymentDate)
                .user(user)
                .store(store)
                .build();
    }
}
