package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.info.CustomUserPrincipal;

import java.util.UUID;

@UseCase
public interface AuthenticateJsonWebTokenUseCase {

    /**
     * Security에서 사용되는, CustomUserPrincipal을 생성하는 유스케이스
     * @param accountId 계정 ID
     * @return CustomUserPrincipal
     */
    CustomUserPrincipal execute(UUID accountId);
}
