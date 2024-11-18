package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.application.dto.request.OauthLoginRequestDto;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;

@UseCase
public interface AuthenticateOauthUseCase {
    KakaoOauth2UserInfo execute(OauthLoginRequestDto requestDto);
}
