package com.daon.onjung.security.info;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoOauth2UserInfo(

        @JsonProperty("id")
        String id,

        @JsonProperty("connected_at")
        String connectedAt,

        @JsonProperty("kakao_account")
        KakaoAccountDto kakaoAccount
) {
    public record KakaoAccountDto(
            @JsonProperty("profile")
            KakaoProfileDto profile,

            @JsonProperty("name_needs_agreement")
            boolean nameNeedsAgreement,

            @JsonProperty("name")
            String name,

            @JsonProperty("has_email")
            boolean hasEmail,

            @JsonProperty("email_needs_agreement")
            boolean emailNeedsAgreement,

            @JsonProperty("is_email_valid")
            boolean isEmailValid,

            @JsonProperty("is_email_verified")
            boolean isEmailVerified,

            @JsonProperty("email")
            String email,

            @JsonProperty("has_phone_number")
            boolean hasPhoneNumber,

            @JsonProperty("phone_number_needs_agreement")
            boolean phoneNumberNeedsAgreement,

            @JsonProperty("phone_number")
            String phoneNumber
    ) {
        public record KakaoProfileDto(
                @JsonProperty("nickname")
                String nickname,

                @JsonProperty("profile_image_url")
                String profileImageUrl
        ) {
        }
    }
}
