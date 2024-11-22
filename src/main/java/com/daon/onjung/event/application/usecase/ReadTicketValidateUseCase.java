package com.daon.onjung.event.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.event.application.dto.request.ReadTicketValidateRequestDto;
import com.daon.onjung.event.application.dto.response.ReadTicketValidateResponseDto;

@UseCase
public interface ReadTicketValidateUseCase {
    ReadTicketValidateResponseDto execute(ReadTicketValidateRequestDto requestDto);
}
