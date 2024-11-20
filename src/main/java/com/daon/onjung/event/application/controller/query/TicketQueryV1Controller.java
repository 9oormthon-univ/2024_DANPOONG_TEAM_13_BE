package com.daon.onjung.event.application.controller.query;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.event.application.dto.response.ReadTicketBriefResponseDto;
import com.daon.onjung.event.application.dto.response.ReadTicketResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketBriefUseCase;
import com.daon.onjung.event.application.usecase.ReadTicketUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class TicketQueryV1Controller {

    private final ReadTicketUseCase readTicketUseCase;
    private final ReadTicketBriefUseCase readTicketBriefUseCase;

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

    /**
     * 5.4 식권 QR코드 조회하기
     */
    @GetMapping("/tickets/{id}/briefs")
    public ResponseDto<ReadTicketBriefResponseDto> readTicketBrief(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(
                readTicketBriefUseCase.execute(
                        id
                )
        );
    }
}
