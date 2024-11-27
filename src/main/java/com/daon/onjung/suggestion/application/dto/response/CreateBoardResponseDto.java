package com.daon.onjung.suggestion.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateBoardResponseDto extends SelfValidating<CreateBoardResponseDto> {

    @JsonProperty("id")
    private final Long id;

    @Builder
    public CreateBoardResponseDto(Long id) {
        this.id = id;

        this.validateSelf();
    }

    public static CreateBoardResponseDto of(Long id) {
        return CreateBoardResponseDto.builder()
                .id(id)
                .build();
    }
}
