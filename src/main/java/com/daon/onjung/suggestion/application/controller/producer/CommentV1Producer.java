package com.daon.onjung.suggestion.application.controller.producer;

import com.daon.onjung.suggestion.application.dto.request.CommentMessage;
import com.daon.onjung.suggestion.application.usecase.SendCommentRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentV1Producer {

    private final SendCommentRequestUseCase sendCommentRequestUseCase;

    public void sendComment (CommentMessage commentMessage) {
        sendCommentRequestUseCase.execute(commentMessage);
    }

}
