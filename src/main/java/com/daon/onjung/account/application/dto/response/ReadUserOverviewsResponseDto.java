package com.daon.onjung.account.application.dto.response;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadUserOverviewsResponseDto extends SelfValidating<ReadUserOverviewsResponseDto> {

    @NotNull(message = "user_name은 null이 될 수 없습니다.")
    @JsonProperty("user_name")
    private final String userName;

    @NotNull(message = "profile_img_url은 null이 될 수 없습니다.")
    @JsonProperty("profile_img_url")
    private final String profileImgUrl;

    @NotNull(message = "notification_allowed은 null이 될 수 없습니다.")
    @JsonProperty("notification_allowed")
    private final Boolean notificationAllowed;

    @Builder
    public ReadUserOverviewsResponseDto(
            String userName,
            String profileImgUrl,
            Boolean notificationAllowed
    ) {
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.notificationAllowed = notificationAllowed;
        this.validateSelf();
    }

    public static ReadUserOverviewsResponseDto fromEntity(
            User user
    ) {
        return ReadUserOverviewsResponseDto.builder()
                .userName(user.getNickName())
                .profileImgUrl(user.getProfileImgUrl())
                .notificationAllowed(user.getNotificationAllowed())
                .build();
    }
}
