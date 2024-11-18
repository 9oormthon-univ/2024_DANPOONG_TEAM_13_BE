package com.daon.onjung.account.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EOnjungTag {
    PATRIOT("국가유공자"),
    GOOD_PRICE("착한가격"),
    UNDERFED_CHILD("결식아동");

    private final String onjungTagName;

    public static EOnjungTag fromString(String value) {
        return switch (value.toUpperCase()) {
            case "PATRIOT" -> PATRIOT;
            case "GOOD_PRICE" -> GOOD_PRICE;
            case "UNDERFED_CHILD" -> UNDERFED_CHILD;
            default -> throw new IllegalArgumentException("온정 태그가 잘못되었습니다.");
        };
    }
}