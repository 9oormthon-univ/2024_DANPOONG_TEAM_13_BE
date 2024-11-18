package com.daon.onjung.security.application.service;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.domain.service.AccountService;
import com.daon.onjung.security.info.CustomUserPrincipal;
import com.daon.onjung.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public CustomUserPrincipal execute(UUID accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        return accountService.createCustomUserPrincipalByAccount(account);
    }
}
