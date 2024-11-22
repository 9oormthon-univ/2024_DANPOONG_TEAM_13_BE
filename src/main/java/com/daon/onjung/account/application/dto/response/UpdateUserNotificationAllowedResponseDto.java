package com.daon.onjung.account.application.dto.response;

import com.daon.onjung.account.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserNotificationAllowedResponseDto {

    @JsonProperty("notification_allowed")
    private final Boolean notificationAllowed;

    @Builder
    public UpdateUserNotificationAllowedResponseDto(
            Boolean notificationAllowed
    ) {
        this.notificationAllowed = notificationAllowed;
    }

    public static UpdateUserNotificationAllowedResponseDto fromEntity(User user) {
        return UpdateUserNotificationAllowedResponseDto.builder()
                .notificationAllowed(user.getNotificationAllowed())
                .build();
    }
}
