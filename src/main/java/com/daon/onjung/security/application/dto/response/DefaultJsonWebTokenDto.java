package com.daon.onjung.security.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DefaultJsonWebTokenDto extends SelfValidating<DefaultJsonWebTokenDto> {
    @JsonProperty("access_token")
    @NotBlank
    private final String accessToken;

    @JsonProperty("refresh_token")
    @NotBlank
    private final String refreshToken;

    @Builder
    public DefaultJsonWebTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.validateSelf();
    }
}
