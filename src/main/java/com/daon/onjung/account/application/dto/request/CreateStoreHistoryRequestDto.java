package com.daon.onjung.account.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record CreateStoreHistoryRequestDto(
        @JsonProperty("store_id")
        Long storeId,

        @JsonProperty("action_date")
        LocalDate actionDate,

        @JsonProperty("content")
        String content,

        @JsonProperty("amount")
        String amount
) {
}