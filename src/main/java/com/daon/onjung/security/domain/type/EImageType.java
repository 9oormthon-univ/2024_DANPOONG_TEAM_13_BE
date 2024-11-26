package com.daon.onjung.security.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EImageType {
    BANNER_IMG("배너 이미지", "BANNER_IMG"),
    LOGO_IMG("로고 이미지", "LOGO_IMG"),
    COMPANY_IMG("회사 이미지", "COMPANY_IMG"),
    BOARD_IMG("게시판 이미지", "BOARD_IMG")

    ;

    private final String krName;
    private final String enName;

    public static EImageType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "BANNER_IMG" -> BANNER_IMG;
            case "LOGO_IMG" -> LOGO_IMG;
            case "COMPANY_IMG" -> COMPANY_IMG;
            case "BOARD_IMG" -> BOARD_IMG;
            default -> throw new IllegalArgumentException("이미지 타입이 잘못되었습니다.");
        };
    }
}
