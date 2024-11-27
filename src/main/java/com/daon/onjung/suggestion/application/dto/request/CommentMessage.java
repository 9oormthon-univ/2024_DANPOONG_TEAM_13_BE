package com.daon.onjung.suggestion.application.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentMessage(
        String content,
        Long boardId,
        UUID userId
) {
}
