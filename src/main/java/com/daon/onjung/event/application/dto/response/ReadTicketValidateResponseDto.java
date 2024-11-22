package com.daon.onjung.event.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTicketValidateResponseDto {

    @JsonProperty("is_valid")
    private final Boolean isValid;

    @JsonProperty("reason")
    private final String reason;

    @Builder
    public ReadTicketValidateResponseDto(
            Boolean isValid,
            String reason
    ) {
        this.isValid = isValid;
        this.reason = reason;
    }

    public static ReadTicketValidateResponseDto fromEntity(
            Boolean isValid,
            String reason
    ) {
        return ReadTicketValidateResponseDto.builder()
                .isValid(isValid)
                .reason(reason)
                .build();
    }
}
