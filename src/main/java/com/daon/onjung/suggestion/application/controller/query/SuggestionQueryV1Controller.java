package com.daon.onjung.suggestion.application.controller.query;

import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.suggestion.application.dto.response.ReadBoardOverviewResponseDto;
import com.daon.onjung.suggestion.application.usecase.ReadBoardOverviewUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SuggestionQueryV1Controller {

    private final ReadBoardOverviewUseCase readBoardOverviewUseCase;

    @GetMapping("/boards/overviews")
    public ResponseDto<ReadBoardOverviewResponseDto> readBoardOverview(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        return ResponseDto.ok(readBoardOverviewUseCase.execute(page, size));
    }
}
