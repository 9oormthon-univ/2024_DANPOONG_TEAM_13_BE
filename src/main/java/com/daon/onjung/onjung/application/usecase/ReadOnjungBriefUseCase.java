package com.daon.onjung.onjung.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungBriefResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOnjungBriefUseCase {
    ReadOnjungBriefResponseDto execute(UUID accountId);
}
