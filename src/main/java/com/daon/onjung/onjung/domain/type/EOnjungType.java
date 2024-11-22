package com.daon.onjung.onjung.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EOnjungType {
    DONATION("동참"),
    SHARE("공유"),
    RECEIPT("방문");

    private final String onjungTypeName;

    public static EOnjungType fromString(String value) {
        return switch (value.toUpperCase()) {
            case "DONATION" -> DONATION;
            case "SHARE" -> SHARE;
            case "RECEIPT" -> RECEIPT;
            default -> throw new IllegalArgumentException("온정 타입이 잘못되었습니다.");
        };
    }
}
