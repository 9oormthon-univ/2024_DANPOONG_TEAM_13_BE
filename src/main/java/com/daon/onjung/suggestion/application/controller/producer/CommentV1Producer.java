package com.daon.onjung.suggestion.application.controller.producer;

import com.daon.onjung.suggestion.application.dto.request.CommentMessage;
import com.daon.onjung.suggestion.application.dto.response.CreateCommentResponseDto;
import com.daon.onjung.suggestion.application.usecase.SendCommentRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentV1Producer {

    private final SendCommentRequestUseCase sendCommentRequestUseCase;

    public CreateCommentResponseDto sendComment (CommentMessage commentMessage) {
        return sendCommentRequestUseCase.execute(commentMessage);
    }

}
