package com.daon.onjung.account.application.dto.response;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateUserNotificationAllowedResponseDto extends SelfValidating<UpdateUserNotificationAllowedResponseDto> {

    @NotNull(message = "notification_allowed은 null이 될 수 없습니다.")
    @JsonProperty("notification_allowed")
    private final Boolean notificationAllowed;

    @Builder
    public UpdateUserNotificationAllowedResponseDto(
            Boolean notificationAllowed
    ) {
        this.notificationAllowed = notificationAllowed;
        this.validateSelf();
    }

    public static UpdateUserNotificationAllowedResponseDto fromEntity(User user) {
        return UpdateUserNotificationAllowedResponseDto.builder()
                .notificationAllowed(user.getNotificationAllowed())
                .build();
    }
}
