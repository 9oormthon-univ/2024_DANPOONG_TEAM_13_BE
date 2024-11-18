package com.daon.onjung.security.application.service;

import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;
import com.daon.onjung.security.application.usecase.LoginByDefaultUseCase;
import com.daon.onjung.security.domain.service.RefreshTokenService;
import com.daon.onjung.security.info.CustomUserPrincipal;
import com.daon.onjung.security.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginByDefaultService implements LoginByDefaultUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto) {
        UUID accountId = principal.getId();
        String refreshToken = jsonWebTokenDto.getRefreshToken();

        if (refreshToken != null) {
            refreshTokenRepository.save(refreshTokenService.createRefreshToken(accountId, refreshToken));
        }
    }
}
