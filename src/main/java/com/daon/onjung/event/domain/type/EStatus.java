package com.daon.onjung.event.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EStatus {
    IN_PROGRESS("모금중"),
    COMPLETED("모금완료"),
    STORE_DELIVERY("식당전달"),
    TICKET_ISSUE("식권발송"),
    REPORT("보고");

    private final String statusName;
}
