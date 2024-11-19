package com.daon.onjung.onjung.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateDonationRequestDto(

        @JsonProperty("donation_amount")
        Integer donationAmount
) {
}
