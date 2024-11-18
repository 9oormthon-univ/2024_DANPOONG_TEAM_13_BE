package com.daon.onjung.security.handler.login;

import com.daon.onjung.core.utility.HttpServletUtil;
import com.daon.onjung.core.utility.JsonWebTokenUtil;
import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;
import com.daon.onjung.security.application.usecase.LoginOwnerByDefaultUseCase;
import com.daon.onjung.security.info.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DefaultLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginOwnerByDefaultUseCase loginOwnerByDefaultUseCase;

    private final JsonWebTokenUtil jwtUtil;
    private final HttpServletUtil httpServletUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();

        DefaultJsonWebTokenDto jsonWebTokenDto = jwtUtil.generateDefaultJsonWebTokens(
                principal.getId(),
                principal.getRole()
        );

        loginOwnerByDefaultUseCase.execute(principal, jsonWebTokenDto);

        httpServletUtil.onSuccessBodyResponseWithJWTBody(response, jsonWebTokenDto);
    }
}
