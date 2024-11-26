package com.daon.onjung.suggestion.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCommentRequestDto(
        @JsonProperty("content")
        String content
) {
}
