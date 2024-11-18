package com.daon.onjung.security.application.controller;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.constant.Constants;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.HeaderUtil;
import com.daon.onjung.security.application.dto.request.UpdateDeviceTokenRequestDto;
import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;
import com.daon.onjung.security.application.usecase.DeleteAccountUseCase;
import com.daon.onjung.security.application.usecase.ReissueJsonWebTokenUseCase;
import com.daon.onjung.security.application.usecase.UpdateDeviceTokenUseCase;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Hidden
public class AuthController {
    private final UpdateDeviceTokenUseCase updateDeviceTokenUseCase;
    private final ReissueJsonWebTokenUseCase reissueJsonWebTokenUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    /**
     * 1.2 디바이스 토큰 갱신
     */
    @PatchMapping("/api/v1/auth/device-token")
    public ResponseDto<?> updateDeviceToken(
            @AccountID UUID accountId,
            @RequestBody @Valid UpdateDeviceTokenRequestDto requestDto
            ) {
        updateDeviceTokenUseCase.execute(accountId, requestDto);
        return ResponseDto.ok(null);
    }

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
