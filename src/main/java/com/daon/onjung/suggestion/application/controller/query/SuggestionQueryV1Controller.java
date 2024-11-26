package com.daon.onjung.suggestion.application.controller.query;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.suggestion.application.dto.response.ReadBoardDetailResponseDto;
import com.daon.onjung.suggestion.application.dto.response.ReadBoardOverviewResponseDto;
import com.daon.onjung.suggestion.application.usecase.ReadBoardDetailUseCase;
import com.daon.onjung.suggestion.application.usecase.ReadBoardOverviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SuggestionQueryV1Controller {

    private final ReadBoardOverviewUseCase readBoardOverviewUseCase;
    private final ReadBoardDetailUseCase readBoardDetailUseCase;

    @GetMapping("/boards/overviews")
    public ResponseDto<ReadBoardOverviewResponseDto> readBoardOverview(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseDto.ok(readBoardOverviewUseCase.execute(page, size));
    }

    @GetMapping("/boards/{id}/details")
    public ResponseDto<ReadBoardDetailResponseDto> readBoardDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readBoardDetailUseCase.execute(accountId, id));
    }

}
