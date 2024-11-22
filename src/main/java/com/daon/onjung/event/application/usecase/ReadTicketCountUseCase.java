package com.daon.onjung.event.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.event.application.dto.response.ReadTicketCountResponseDto;

import java.util.UUID;

@UseCase
public interface ReadTicketCountUseCase {

    ReadTicketCountResponseDto execute(UUID accountId);
}
