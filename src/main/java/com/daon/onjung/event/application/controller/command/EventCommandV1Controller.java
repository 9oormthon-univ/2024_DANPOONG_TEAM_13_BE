package com.daon.onjung.event.application.controller.command;

import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.event.application.dto.request.ReadTicketValidateRequestDto;
import com.daon.onjung.event.application.dto.response.ReadTicketValidateResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketValidateUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventCommandV1Controller {

    private final ReadTicketValidateUseCase validateTicketUseCase;

    /**
     * 5.5 식권 유효성 검증 및 사용처리
     */
    @PostMapping("/api/v1/tickets/validate")
    public ResponseDto<ReadTicketValidateResponseDto> validateTicket(
            @RequestBody @Valid ReadTicketValidateRequestDto requestDto
    ) {
        return ResponseDto.ok(
                validateTicketUseCase.execute(requestDto)
        );
    }
}
