package com.daon.onjung.onjung.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.onjung.application.dto.response.ReadUserOnjungOverviewResponseDto;

import java.util.UUID;

@UseCase
public interface ReadUserOnjungOverviewUseCase {

    ReadUserOnjungOverviewResponseDto execute(UUID accountId);
}
