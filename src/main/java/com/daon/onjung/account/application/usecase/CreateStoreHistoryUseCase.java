package com.daon.onjung.account.application.usecase;

import com.daon.onjung.account.application.dto.request.CreateStoreHistoryRequestDto;
import com.daon.onjung.core.annotation.bean.UseCase;

import java.time.LocalDate;

@UseCase
public interface CreateStoreHistoryUseCase {
    void execute(CreateStoreHistoryRequestDto requestDto);
}
