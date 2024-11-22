package com.daon.onjung.event.application.controller.command;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.event.application.dto.request.ReadTicketValidateRequestDto;
import com.daon.onjung.event.application.dto.response.ReadTicketResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketValidateUseCase;
import com.daon.onjung.onjung.application.dto.request.CreateReceiptRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventCommandV1Controller {

    private final ReadTicketValidateUseCase validateTicketUseCase;

    /**
     * 5.5 식권 유효성 검증 및 사용처리
     */
    @PostMapping("/api/v1/tickets/validate")
    public ResponseDto<ReadTicketResponseDto> validateTicket(
            @RequestBody @Valid ReadTicketValidateRequestDto requestDto
    ) {
        validateTicketUseCase.execute(requestDto);
        return ResponseDto.ok(null);
    }
}
