package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.response.ReadBoardOverviewResponseDto;

@UseCase
public interface ReadBoardOverviewUseCase {
    ReadBoardOverviewResponseDto execute(Integer page, Integer size);
}
