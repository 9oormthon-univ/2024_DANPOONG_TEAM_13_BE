package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.request.CreateCommentRequestDto;

import java.util.UUID;

@UseCase
public interface CreateCommentUseCase {
    void execute(UUID accountId, Long boardId, CreateCommentRequestDto requestDto);
}
