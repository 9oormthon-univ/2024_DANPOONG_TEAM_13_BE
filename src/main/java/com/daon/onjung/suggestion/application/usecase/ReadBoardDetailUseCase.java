package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.response.ReadBoardDetailResponseDto;

import java.util.UUID;

@UseCase
public interface ReadBoardDetailUseCase {
    ReadBoardDetailResponseDto execute(UUID accountId, Long boardId);
}
