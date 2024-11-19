package com.daon.onjung.onjung.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.onjung.application.dto.request.CreateDonationRequestDto;
import com.daon.onjung.onjung.application.dto.response.CreateDonationResponseDto;

import java.util.UUID;

@UseCase
public interface CreateDonationUseCase {
    CreateDonationResponseDto execute(UUID accountId, Long StoreId, CreateDonationRequestDto requestDto);
}
