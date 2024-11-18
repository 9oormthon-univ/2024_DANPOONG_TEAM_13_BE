package com.daon.onjung.security.application.service;

import com.daon.onjung.core.utility.RestClientUtil;
import com.daon.onjung.security.application.dto.request.OauthLoginRequestDto;
import com.daon.onjung.security.application.usecase.AuthenticateOauthUseCase;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticateOauthService implements AuthenticateOauthUseCase {
    @Value("${oauth.kakao.url}")
    private String kakaoUrl;

    @Value("${oauth.kakao.path}")
    private String kakaoPath;

    private final RestClientUtil restClientUtil;
    private final ObjectMapper objectMapper;

    @Override
    public KakaoOauth2UserInfo execute(OauthLoginRequestDto requestDto) {
        try {
            JSONObject jsonObject = restClientUtil.sendGetMethodWithAuthorizationHeader(
                    kakaoUrl + kakaoPath, requestDto.accessToken()
            );
            return objectMapper.readValue(jsonObject.toString(), KakaoOauth2UserInfo.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to authenticate with Kakao : {}", e);
        }
    }
}

