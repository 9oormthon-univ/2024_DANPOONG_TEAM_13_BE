package com.daon.onjung.onjung.application.controller.query;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungCountResponseDto;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungSummaryResponseDto;
import com.daon.onjung.onjung.application.usecase.ReadOnjungCountUseCase;
import com.daon.onjung.onjung.application.usecase.ReadOnjungSummaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OnjungQueryV1Controller {

    private final ReadOnjungSummaryUseCase readOnjungSummaryUseCase;
    private final ReadOnjungCountUseCase readOnjungCountUseCase;

    /**
     * 4.1 전체 온기 통계 조회하기
     */
    @GetMapping("/api/v1/onjungs/summaries")
    public ResponseDto<ReadOnjungSummaryResponseDto> readOnjungSummary() {
        return ResponseDto.ok(readOnjungSummaryUseCase.execute());
    }

    /**
     * 4.2 후원 횟수 조회하기
     */
    @GetMapping("/api/v1/onjungs/count")
    public ResponseDto<ReadOnjungCountResponseDto> readOnjungCount(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readOnjungCountUseCase.execute(accountId));
    }
}
