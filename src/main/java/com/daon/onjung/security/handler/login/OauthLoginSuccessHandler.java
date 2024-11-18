package com.daon.onjung.security.handler.login;

import com.daon.onjung.core.utility.HttpServletUtil;
import com.daon.onjung.core.utility.JsonWebTokenUtil;
import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;
import com.daon.onjung.security.application.usecase.LoginUserByOauthUseCase;
import com.daon.onjung.security.application.usecase.ReadOrCreateUserUseCase;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.domain.type.ESecurityRole;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OauthLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final ReadOrCreateUserUseCase readOrCreateUserUseCase;
    private final LoginUserByOauthUseCase loginUserByOauthUseCase;

    private final JsonWebTokenUtil jwtUtil;
    private final HttpServletUtil httpServletUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        KakaoOauth2UserInfo principal = (KakaoOauth2UserInfo) authentication.getPrincipal();

        Account loginUser = readOrCreateUserUseCase.execute(principal);

        DefaultJsonWebTokenDto jsonWebTokenDto = jwtUtil.generateDefaultJsonWebTokens(
                loginUser.getId(),
                ESecurityRole.USER
        );

        loginUserByOauthUseCase.execute(loginUser.getId(), jsonWebTokenDto);

        httpServletUtil.onSuccessBodyResponseWithJWTBody(response, jsonWebTokenDto);
    }
}
