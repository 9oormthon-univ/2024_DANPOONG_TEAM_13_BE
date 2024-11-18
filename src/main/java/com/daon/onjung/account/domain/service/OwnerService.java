package com.daon.onjung.account.domain.service;

import com.daon.onjung.account.domain.Owner;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.EBankName;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    public Owner createOwner(ESecurityProvider provider,
                             String serialId,
                             String password,
                             EBankName bankName,
                             String bankAccountNumber,
                             Boolean notificationAllowed) {
        return Owner.builder()
                .serialId(serialId)
                .password(password)
                .provider(provider)
                .bankName(bankName)
                .bankAccountNumber(bankAccountNumber)
                .notificationAllowed(notificationAllowed)
                .build();
    }
}
