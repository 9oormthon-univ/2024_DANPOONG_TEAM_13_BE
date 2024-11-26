package com.daon.onjung.suggestion.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBoardRequestDto(

        @JsonProperty("title")
        @Size(min = 1, max = 20, message = "제목은 1자 이상 20자 이하로 입력해주세요.")
        @NotNull(message = "제목은 null일 수 없습니다.")
        String title,

        @JsonProperty("content")
        @Size(min = 1, max = 500, message = "내용은 1자 이상 500자 이하로 입력해주세요.")
        @NotNull(message = "내용은 null일 수 없습니다.")
        String content
) {
}
