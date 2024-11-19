package com.daon.onjung.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReadVirtualAccountResponseDto(
        @JsonProperty("bank_id")
        Long bankId,

        @JsonProperty("event_id")
        Long eventId,

        @JsonProperty("bank_number")
        String bankNumber,

        @JsonProperty("balance")
        Integer balance
) {
}
