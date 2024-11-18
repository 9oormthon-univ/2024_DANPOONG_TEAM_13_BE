package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;

import java.util.UUID;

@UseCase
public interface LoginUserByOauthUseCase {
    /**
     * Security에서 사용되는 Oauth Login 유스케이스. 로그인한 사용자에 대해 Refresh Token을 생성
     * @param accountId 새롭게 생성된 계정 ID
     * @param jsonWebTokenDto DefaultJsonWebTokenDto
     */
    void execute(UUID accountId, DefaultJsonWebTokenDto jsonWebTokenDto);
}
