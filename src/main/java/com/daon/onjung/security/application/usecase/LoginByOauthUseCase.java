package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.application.dto.response.DefaultJsonWebTokenDto;

import java.util.UUID;

@UseCase
public interface LoginByOauthUseCase {
    void execute(UUID accountId, DefaultJsonWebTokenDto jsonWebTokenDto);
}
