package com.daon.onjung.event.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTicketBriefResponseDto {

    @NotNull(message = "qr_base64는 null일 수 없습니다.")
    @JsonProperty("qr_base64")
    private final String qrBase64;

    @Builder
    public ReadTicketBriefResponseDto(
            String qrBase64
    ) {
        this.qrBase64 = qrBase64;
    }

    public static ReadTicketBriefResponseDto fromEntity(
            String qrBase64
    ) {

        return ReadTicketBriefResponseDto.builder()
                .qrBase64(qrBase64)
                .build();
    }
}
