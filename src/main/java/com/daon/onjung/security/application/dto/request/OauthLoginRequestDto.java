package com.daon.onjung.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OauthLoginRequestDto(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("provider")
        String provider
) {
}
