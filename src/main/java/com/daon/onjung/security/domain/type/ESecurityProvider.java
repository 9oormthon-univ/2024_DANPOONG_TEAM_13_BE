package com.daon.onjung.security.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ESecurityProvider {

    DEFAULT("기본", "Default"),
    KAKAO("카카오", "Kakao"),
    ;

    private final String koName;
    private final String enName;

    public static ESecurityProvider fromString(String value) {
        return switch (value.toUpperCase()) {
            case "DEFAULT" -> DEFAULT;
            case "KAKAO" -> KAKAO;
            default -> throw new IllegalArgumentException("Security Provider가 잘못되었습니다.");
        };
    }
}
