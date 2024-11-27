package com.daon.onjung.suggestion.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateOrDeleteLikeResponseDto extends SelfValidating<CreateOrDeleteLikeResponseDto> {

    @JsonProperty("is_like")
    private final Boolean isLike;

    @Builder
    public CreateOrDeleteLikeResponseDto(Boolean isLike) {
        this.isLike = isLike;

        this.validateSelf();
    }

    public static CreateOrDeleteLikeResponseDto of(Boolean isLike) {
        return CreateOrDeleteLikeResponseDto.builder()
                .isLike(isLike)
                .build();
    }
}
