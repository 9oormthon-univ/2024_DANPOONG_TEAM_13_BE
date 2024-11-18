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
}
