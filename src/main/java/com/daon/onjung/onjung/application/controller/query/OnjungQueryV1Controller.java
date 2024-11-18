package com.daon.onjung.onjung.application.controller.query;

import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungSummaryResponseDto;
import com.daon.onjung.onjung.application.usecase.ReadOnjungSummaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OnjungQueryV1Controller {
    private final ReadOnjungSummaryUseCase readOnjungSummaryUseCase;

    /**
     * 4.1 전체 온기 통계 조회하기
     */
    @GetMapping("/api/v1/onjung/summaries")
    public ResponseDto<ReadOnjungSummaryResponseDto> readOnjungSummary() {
        return ResponseDto.ok(readOnjungSummaryUseCase.execute());
    }
}
