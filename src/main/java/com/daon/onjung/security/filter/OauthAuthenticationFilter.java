package com.daon.onjung.security.filter;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.security.application.dto.request.OauthLoginRequestDto;
import com.daon.onjung.security.application.usecase.AuthenticateOauthUseCase;
import com.daon.onjung.security.handler.login.OauthLoginSuccessHandler;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class OauthAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final OauthLoginSuccessHandler oauthLoginSuccessHandler;
    private final AuthenticateOauthUseCase authenticateOauthUseCase;

    public OauthAuthenticationFilter(
            String defaultFilterProcessesUrl,
            OauthLoginSuccessHandler oauthLoginSuccessHandler,
            AuthenticateOauthUseCase authenticateOauthUseCase
            ) {
        super(defaultFilterProcessesUrl);
        this.oauthLoginSuccessHandler = oauthLoginSuccessHandler;
        this.authenticateOauthUseCase = authenticateOauthUseCase;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            OauthLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), OauthLoginRequestDto.class);

            KakaoOauth2UserInfo principal = authenticateOauthUseCase.execute(requestDto);

            return new UsernamePasswordAuthenticationToken(principal, null, null);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.BAD_REQUEST_JSON);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException {
        oauthLoginSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }
}
