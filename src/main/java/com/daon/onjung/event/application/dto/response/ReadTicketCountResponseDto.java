package com.daon.onjung.event.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadTicketCountResponseDto extends SelfValidating<ReadTicketCountResponseDto> {

    @JsonProperty("ticket_count")
    private final Long ticketCount;

    @Builder
    public ReadTicketCountResponseDto(Long ticketCount) {
        this.ticketCount = ticketCount;
        this.validateSelf();
    }

    public static ReadTicketCountResponseDto of(Long ticketCount) {
        return ReadTicketCountResponseDto.builder()
                .ticketCount(ticketCount)
                .build();
    }
}
