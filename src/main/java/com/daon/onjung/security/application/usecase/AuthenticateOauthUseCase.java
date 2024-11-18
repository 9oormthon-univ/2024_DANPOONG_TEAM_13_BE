package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.application.dto.request.OauthLoginRequestDto;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;

@UseCase
public interface AuthenticateOauthUseCase {
    /**
     * Security에서 Oauth 로그인 시 사용되는, Oauth 토큰을 바탕으로 Oauth Provider 로부터 유저 정보를 제공받는 유스케이스.
     * @param requestDto OauthLoginRequestDto
     * @return KakaoOauth2UserInfo Oauth Provider가 제공해 준 유저 정보
     */
    KakaoOauth2UserInfo execute(OauthLoginRequestDto requestDto);
}
