package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.response.ReadCommentOverviewResponseDto;

import java.util.UUID;

@UseCase
public interface ReadCommentOverviewUseCase {

    ReadCommentOverviewResponseDto execute(UUID accountId, Long boardId, Integer page, Integer size);
}
