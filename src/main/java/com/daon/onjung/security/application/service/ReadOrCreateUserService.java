package com.daon.onjung.security.application.service;

import com.daon.onjung.account.domain.service.UserService;
import com.daon.onjung.security.application.usecase.ReadOrCreateUserUseCase;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;
import com.daon.onjung.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReadOrCreateUserService implements ReadOrCreateUserUseCase {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;

    @Override
    @Transactional
    public Account execute(KakaoOauth2UserInfo userInfo) {

        return accountRepository.findBySerialIdAndProvider(userInfo.id(), ESecurityProvider.KAKAO)
                .orElseGet(() -> {
                    Account newAccount = userService.createUser(userInfo, bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
                    return accountRepository.save(newAccount);
                });
    }
}
