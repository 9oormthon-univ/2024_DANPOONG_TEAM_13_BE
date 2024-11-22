package com.daon.onjung.event.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.event.application.dto.request.ReadTicketValidateRequestDto;
import com.daon.onjung.event.application.dto.response.ReadTicketValidateResponseDto;

@UseCase
public interface ReadTicketValidateUseCase {
    /**
     * 식권 유효성 검사하기
     * @RequestBody hashed_ticket_id 티켓 ID
     * @RequestBody password 비밀번호
     */

    ReadTicketValidateResponseDto execute(ReadTicketValidateRequestDto requestDto);
}
