package com.daon.onjung.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ReadVirtualAccountResponseDto(
        @JsonProperty("success")
        Boolean success,

        @JsonProperty("data")
        Data data,

        @JsonProperty("error")
        ErrorDetails error
) {
        public record Data(
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

        public record ErrorDetails(
                @JsonProperty("code")
                String code,

                @JsonProperty("message")
                String message
        ) {
        }
}
