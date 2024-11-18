package com.daon.onjung.security.domain.service;

import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public CustomUserPrincipal createCustomUserPrincipalByAccount(Account account) {
        return CustomUserPrincipal.create(account);
    }
}
