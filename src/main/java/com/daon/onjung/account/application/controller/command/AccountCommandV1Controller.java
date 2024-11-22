package com.daon.onjung.account.application.controller.command;

import com.daon.onjung.account.application.dto.request.CreateStoreHistoryRequestDto;
import com.daon.onjung.account.application.dto.response.UpdateUserNotificationAllowedResponseDto;
import com.daon.onjung.account.application.usecase.CreateStoreHistoryUseCase;
import com.daon.onjung.account.application.usecase.UpdateUserNotificationAllowedUseCase;
import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountCommandV1Controller {

    private final CreateStoreHistoryUseCase createStoreHistoryUseCase;
    private final UpdateUserNotificationAllowedUseCase updateUserNotificationAllowedUseCase;

    /**
     * 3.3 가게 히스토리 등록하기
     */
    @PostMapping(value = "/api/v1/store-histories")
    public ResponseDto<Void> createStoreHistory(
            @RequestBody @Valid CreateStoreHistoryRequestDto requestDto
            ) {
        createStoreHistoryUseCase.execute(requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 3.5 푸시알림 설정 변경(토글)
     */
    @PatchMapping("/api/v1/users/notification-allowed")
    public ResponseDto<UpdateUserNotificationAllowedResponseDto> updateNotificationAllowed(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(
                updateUserNotificationAllowedUseCase.execute(accountId)
        );
    }
}
