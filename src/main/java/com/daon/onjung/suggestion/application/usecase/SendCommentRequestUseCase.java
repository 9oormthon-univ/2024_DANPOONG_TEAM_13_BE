package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.request.CommentMessage;

@UseCase
public interface SendCommentRequestUseCase {
    void execute(CommentMessage commentMessage);
}
