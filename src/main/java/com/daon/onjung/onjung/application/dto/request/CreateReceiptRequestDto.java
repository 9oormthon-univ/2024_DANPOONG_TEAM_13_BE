package com.daon.onjung.onjung.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateReceiptRequestDto(
        @JsonProperty("store_name")
        String storeName,

        @JsonProperty("store_address")
        String storeAddress,

        @JsonProperty("payment_date")
        String paymentDate,

        @JsonProperty("payment_amount")
        String paymentAmount
) {
}
