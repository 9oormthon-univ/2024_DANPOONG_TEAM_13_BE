package com.daon.onjung.suggestion.application.controller.producer;

import com.daon.onjung.suggestion.application.dto.request.LikeMessage;
import com.daon.onjung.suggestion.application.usecase.SendLikeRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeV1Producer {

    private final SendLikeRequestUseCase sendLikeRequestUseCase;

    public void sendLike (LikeMessage likeMessage) {
        sendLikeRequestUseCase.execute(likeMessage);
    }

}
