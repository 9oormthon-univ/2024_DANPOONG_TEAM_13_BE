package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.suggestion.application.dto.request.CommentMessage;
import com.daon.onjung.suggestion.application.usecase.SendCommentRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendCommentRequestService implements SendCommentRequestUseCase {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void execute(CommentMessage commentMessage) {

        // 모듈러 연산을 사용하여 Queue 분리. boardId가 짝수인경우 board.0을 통해 Queue2로 라우팅
        // 홀수인경우 board.1을 통해 Queue 1 로 라우팅
        // Comment의 경우, 하나의 게시물에 대해 순서가 보장되어야 하므로, routingKey를 이용해 boardId를 기반으로 라우팅
        String routingKey = "board." + (commentMessage.boardId() % 2);
        rabbitTemplate.convertAndSend("board-exchange", routingKey, commentMessage);
    }
}
