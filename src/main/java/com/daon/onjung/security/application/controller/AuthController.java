package com.daon.onjung.security.application.controller;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.constant.Constants;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.HeaderUtil;
import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;
import com.daon.onjung.security.application.usecase.DeleteAccountUseCase;
import com.daon.onjung.security.application.usecase.ReissueJsonWebTokenUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Hidden
public class AuthController {
    private final ReissueJsonWebTokenUseCase reissueJsonWebTokenUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    /**
     * 1.3 JWT 재발급
     */
    @PostMapping("/api/v1/auth/reissue/token")
    public ResponseDto<DefaultJsonWebTokenDto> reissueDefaultJsonWebToken(
            HttpServletRequest request
    ) {
        String refreshToken = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(ErrorCode.INVALID_HEADER_ERROR));

        return ResponseDto.created(reissueJsonWebTokenUseCase.execute(refreshToken));
    }

    /**
     * 2.2 회원 탈퇴
     */
    @DeleteMapping("/api/v1/auth")
    public ResponseDto<?> deleteAccount(
            @AccountID UUID accountId
    ) {
        deleteAccountUseCase.execute(accountId);
        return ResponseDto.ok(null);
    }
}
