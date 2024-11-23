package com.daon.onjung.event.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.event.application.dto.response.ReadOnjungEventOverviewResponseDto;

import java.util.UUID;

@UseCase
public interface ReadOnjungEventOverviewUseCase {

    ReadOnjungEventOverviewResponseDto execute(
            Integer page,
            Integer size,
            UUID accountId
    );
}
