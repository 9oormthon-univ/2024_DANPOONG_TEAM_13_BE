package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.suggestion.application.dto.request.LikeMessage;
import com.daon.onjung.suggestion.application.dto.response.CreateOrDeleteLikeResponseDto;
import com.daon.onjung.suggestion.application.usecase.SendLikeRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendLikeRequestService implements SendLikeRequestUseCase {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public CreateOrDeleteLikeResponseDto execute(LikeMessage likeMessage) {

        Boolean response = (Boolean) rabbitTemplate.convertSendAndReceive("like-queue", likeMessage);
        return CreateOrDeleteLikeResponseDto.of(response);
    }
}
