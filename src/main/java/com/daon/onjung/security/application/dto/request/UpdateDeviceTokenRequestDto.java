package com.daon.onjung.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateDeviceTokenRequestDto(
        @JsonProperty("device_token")
        String deviceToken
) {
}
