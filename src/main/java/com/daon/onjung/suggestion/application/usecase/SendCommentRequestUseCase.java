package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.request.CommentMessage;
import com.daon.onjung.suggestion.application.dto.response.CreateCommentResponseDto;

@UseCase
public interface SendCommentRequestUseCase {
    CreateCommentResponseDto execute(CommentMessage commentMessage);
}
