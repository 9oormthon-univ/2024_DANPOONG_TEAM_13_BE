package com.daon.onjung.onjung.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.onjung.application.dto.request.CreateReceiptRequestDto;

import java.util.UUID;

@UseCase
public interface CreateReceiptUseCase {
    void execute(UUID accountId, CreateReceiptRequestDto requestDto);
}
