package com.daon.onjung.account.application.usecase;

import com.daon.onjung.account.application.dto.response.UpdateUserNotificationAllowedResponseDto;
import com.daon.onjung.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface UpdateUserNotificationAllowedUseCase {

    UpdateUserNotificationAllowedResponseDto execute(UUID accountId);
}
