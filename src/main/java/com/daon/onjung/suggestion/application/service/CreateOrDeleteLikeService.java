package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.suggestion.application.controller.producer.LikeV1Producer;
import com.daon.onjung.suggestion.application.dto.request.LikeMessage;
import com.daon.onjung.suggestion.application.usecase.CreateOrDeleteLikeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrDeleteLikeService implements CreateOrDeleteLikeUseCase {

    private final LikeV1Producer likeProducer;

    @Override
    @Transactional
    public void execute(UUID accountId, Long boardId) {

        likeProducer.sendLike(LikeMessage.builder()
                    .boardId(boardId)
                    .userId(accountId)
                    .build());

    }
}
