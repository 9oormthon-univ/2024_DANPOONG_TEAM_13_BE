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
}
