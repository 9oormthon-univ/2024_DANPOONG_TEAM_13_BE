package com.daon.onjung.security.application.service;

import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;
import com.daon.onjung.security.application.usecase.LoginOwnerByDefaultUseCase;
import com.daon.onjung.security.domain.service.RefreshTokenService;
import com.daon.onjung.security.info.CustomUserPrincipal;
import com.daon.onjung.security.repository.redis.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginOwnerByDefaultService implements LoginOwnerByDefaultUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    @Override
    @Transactional
    public void execute(CustomUserPrincipal principal, DefaultJsonWebTokenDto jsonWebTokenDto) {

        UUID accountId = principal.getId();
        String refreshToken = jsonWebTokenDto.getRefreshToken();

        // Refresh Token 저장
        if (refreshToken != null) {
            refreshTokenRepository.save(refreshTokenService.createRefreshToken(accountId, refreshToken));
        }
    }
}
