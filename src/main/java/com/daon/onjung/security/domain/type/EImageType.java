package com.daon.onjung.security.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EImageType {
    BANNER_IMG("배너 이미지", "BANNER_IMAGE"),
    LOGO_IMG("로고 이미지", "LOGO_IMAGE")

    ;

    private final String krName;
    private final String enName;

    public static EImageType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "BANNER_IMAGE" -> BANNER_IMG;
            case "LOGO_IMAGE" -> LOGO_IMG;
            default -> throw new IllegalArgumentException("이미지 타입이 잘못되었습니다.");
        };
    }
}
