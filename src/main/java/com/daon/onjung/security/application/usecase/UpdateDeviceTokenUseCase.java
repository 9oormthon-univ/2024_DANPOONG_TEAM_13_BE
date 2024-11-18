package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.application.dto.request.UpdateDeviceTokenRequestDto;

import java.util.UUID;

@UseCase
public interface UpdateDeviceTokenUseCase {
    /**
     * 디바이스 토큰을 갱신하는 유스케이스
     * @param accountId 계정 ID
     * @param requestDto 디바이스 토큰 갱신 요청 DTO
     */
    void execute(UUID accountId, UpdateDeviceTokenRequestDto requestDto);
}
