package com.daon.onjung.account.application.dto.response;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserOverviewsResponseDto extends SelfValidating<ReadUserOverviewsResponseDto> {

    @JsonProperty("user_name")
    private final String userName;

    @JsonProperty("profile_img_url")
    private final String profileImgUrl;

    @Builder
    public ReadUserOverviewsResponseDto(
            String userName,
            String profileImgUrl
    ) {
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
    }

    public static ReadUserOverviewsResponseDto fromEntity(
            User user
    ) {
        return ReadUserOverviewsResponseDto.builder()
                .userName(user.getNickName())
                .profileImgUrl(user.getProfileImgUrl())
                .build();
    }
}
