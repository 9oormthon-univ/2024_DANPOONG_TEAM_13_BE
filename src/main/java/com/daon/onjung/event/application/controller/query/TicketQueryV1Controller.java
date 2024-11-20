package com.daon.onjung.event.application.controller.query;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.event.application.dto.response.ReadTicketResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class TicketQueryV1Controller {

    private final ReadTicketUseCase readTicketUseCase;

    /**
     * 5.3 나의 식권 조회하기
     */
    @GetMapping("/tickets")
    public ResponseDto<ReadTicketResponseDto> readTicketList(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(
                readTicketUseCase.execute(
                        page,
                        size,
                        accountId
                )
        );
    }
}
