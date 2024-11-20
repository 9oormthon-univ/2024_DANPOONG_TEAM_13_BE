package com.daon.onjung.company.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCompanyRequestDto(
        @JsonProperty("name")
        String name
) {
}
