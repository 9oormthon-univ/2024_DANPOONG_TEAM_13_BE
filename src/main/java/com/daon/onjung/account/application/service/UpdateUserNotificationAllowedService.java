package com.daon.onjung.account.application.service;

import com.daon.onjung.account.application.dto.response.UpdateUserNotificationAllowedResponseDto;
import com.daon.onjung.account.application.usecase.UpdateUserNotificationAllowedUseCase;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.domain.service.AccountService;
import com.daon.onjung.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserNotificationAllowedService implements UpdateUserNotificationAllowedUseCase {

    private final UserRepository userRepository;

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public UpdateUserNotificationAllowedResponseDto execute(UUID accountId) {

        // Account 조회
        Account account = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // Notification 허용 여부 업데이트
        account = accountService.updateNotificationAllowed(account);
        accountRepository.save(account);

        return UpdateUserNotificationAllowedResponseDto.fromEntity((User) account);
    }
}
