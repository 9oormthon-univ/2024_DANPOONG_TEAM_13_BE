package com.daon.onjung.event.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReadTicketValidateRequestDto(
        @JsonProperty("hashed_ticket_id")
        String hashedTicketId,

        @JsonProperty("password")
        String password
) {
}
