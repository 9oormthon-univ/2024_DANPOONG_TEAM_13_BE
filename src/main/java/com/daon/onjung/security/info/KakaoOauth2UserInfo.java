package com.daon.onjung.security.info;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoOauth2UserInfo(

        @JsonProperty("id")
        String id,

        @JsonProperty("connected_at")
        String connectedAt,

        @JsonProperty("properties")
        KakaoPropertiesDto properties,

        @JsonProperty("kakao_account")
        KakaoAccountDto kakaoAccount
) {
    public record KakaoPropertiesDto(
            @JsonProperty("nickname")
            String nickname,

            @JsonProperty("profile_image")
            String profileImage,

            @JsonProperty("thumbnail_image")
            String thumbnailImage
    ) {
    }

    public record KakaoAccountDto(
            @JsonProperty("profile_nickname_needs_agreement")
            boolean profileNicknameNeedsAgreement,

            @JsonProperty("profile_image_needs_agreement")
            boolean profileImageNeedsAgreement,

            @JsonProperty("profile")
            KakaoProfileDto profile
    ) {
        public record KakaoProfileDto(
                @JsonProperty("nickname")
                String nickname,

                @JsonProperty("thumbnail_image_url")
                String thumbnailImageUrl,

                @JsonProperty("profile_image_url")
                String profileImageUrl,

                @JsonProperty("is_default_image")
                boolean isDefaultImage,

                @JsonProperty("is_default_nickname")
                boolean isDefaultNickname
        ) {
        }
    }
}
