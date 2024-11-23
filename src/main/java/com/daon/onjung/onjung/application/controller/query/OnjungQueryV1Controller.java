package com.daon.onjung.onjung.application.controller.query;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.onjung.application.dto.response.*;
import com.daon.onjung.onjung.application.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OnjungQueryV1Controller {

    private final ReadOnjungSummaryUseCase readOnjungSummaryUseCase;
    private final ReadOnjungCountUseCase readOnjungCountUseCase;
    private final ReadOnjungBriefUseCase readOnjungBriefUseCase;
    private final ReadUserOnjungOverviewUseCase readUserOnjungOverviewUseCase;
    private final ReadUserOnjungBriefUseCase readUserOnjungBriefUseCase;

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

    /**
     * 4.3 온기 간단 정보 조회
     */
    @GetMapping("/api/v1/onjungs/briefs")
    public ResponseDto<ReadOnjungBriefResponseDto> readOnjungBrief(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readOnjungBriefUseCase.execute(accountId));
    }

    /**
     * 4.8 함께한 식당 조회
     */
    @GetMapping("/api/v1/users/onjungs/overviews")
    public ResponseDto<ReadUserOnjungOverviewResponseDto> readUserOnjungOverview(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserOnjungOverviewUseCase.execute(accountId));
    }

    /**
     * 4.9 나의 온기 조회
     */
    @GetMapping("/api/v1/users/onjungs/briefs")
    public ResponseDto<ReadUserOnjungBriefResponseDto> readUserOnjungBrief(
            @AccountID UUID accountId
    ) {
        return ResponseDto.ok(readUserOnjungBriefUseCase.execute(accountId));
    }
}
