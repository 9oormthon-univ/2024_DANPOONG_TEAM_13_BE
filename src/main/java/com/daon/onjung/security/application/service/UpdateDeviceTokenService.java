package com.daon.onjung.security.application.service;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.daon.onjung.security.application.usecase.UpdateDeviceTokenUseCase;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.domain.service.AccountService;
import com.daon.onjung.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateDeviceTokenService implements UpdateDeviceTokenUseCase{

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    @Transactional
    public void execute(UUID accountId, UpdateDeviceTokenRequestDto requestDto) {

        // Account 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 디바이스 토큰 갱신
        account = accountService.updateDeviceToken(account, requestDto.deviceToken());
        accountRepository.save(account);
    }
}
