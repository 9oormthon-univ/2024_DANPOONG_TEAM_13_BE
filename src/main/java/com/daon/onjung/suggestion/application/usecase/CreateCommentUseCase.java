package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.request.CreateCommentRequestDto;
import com.daon.onjung.suggestion.application.dto.response.CreateCommentResponseDto;

import java.util.UUID;

@UseCase
public interface CreateCommentUseCase {
    CreateCommentResponseDto execute(UUID accountId, Long boardId, CreateCommentRequestDto requestDto);
}
