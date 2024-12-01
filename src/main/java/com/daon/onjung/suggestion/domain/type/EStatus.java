package com.daon.onjung.suggestion.domain.type;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EStatus {
    IN_PROGRESS("진행중", "IN_PROGRESS"),
    EXPIRED("만료", "EXPIRED"),
    UNDER_REVIEW("검토중", "UNDER_REVIEW"),
    REGISTERED("등록됨", "REGISTERED"),
    REGISTRATION_FAILED("등록 실패", "REGISTRATION_FAILED")
    ;

    private final String krName;
    private final String enName;

    public static EStatus fromString(String value) {
        return switch (value.toUpperCase()) {
            case "IN_PROGRESS" -> IN_PROGRESS;
            case "EXPIRED" -> EXPIRED;
            case "UNDER_REVIEW" -> UNDER_REVIEW;
            case "REGISTERED" -> REGISTERED;
            case "REGISTRATION_FAILED" -> REGISTRATION_FAILED;
            default -> throw new CommonException(ErrorCode.INVALID_ARGUMENT);
        };
    }
}
