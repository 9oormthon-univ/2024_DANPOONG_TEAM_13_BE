package com.daon.onjung.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DepositOrTransferVirtualAccountResponseDto(
        @JsonProperty("bank_id")
        Long bankId,

        @JsonProperty("balance")
        Integer balance
) {
}
