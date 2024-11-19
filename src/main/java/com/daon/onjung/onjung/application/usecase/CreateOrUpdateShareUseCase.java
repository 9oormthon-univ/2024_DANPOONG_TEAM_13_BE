package com.daon.onjung.onjung.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface CreateOrUpdateShareUseCase {
    Boolean execute(UUID accountId, Long storeId);
}
