package com.daon.onjung.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateVirtualAccountResponseDto(
        @JsonProperty("bank_id")
        Long bankId,

        @JsonProperty("bank_name")
        String bankName,

        @JsonProperty("bank_number")
        String bankNumber,

        @JsonProperty("balance")
        Integer balance
) {
}
