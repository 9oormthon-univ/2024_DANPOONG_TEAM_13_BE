package com.daon.onjung.account.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ECategory {
    WESTERN("양식"),
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    ETC("기타");

    private final String categoryName;

    public static ECategory fromString(String value) {
        return switch (value.toUpperCase()) {
            case "WESTERN" -> WESTERN;
            case "KOREAN" -> KOREAN;
            case "CHINESE" -> CHINESE;
            case "JAPANESE" -> JAPANESE;
            case "ETC" -> ETC;
            default -> throw new IllegalArgumentException("음식 종류가 잘못되었습니다.");
        };
    }
}
