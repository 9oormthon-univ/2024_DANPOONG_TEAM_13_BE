package com.daon.onjung.event.domain.type;

import com.daon.onjung.account.domain.type.ECategory;
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

    public static EStatus fromString(String value) {
        return switch (value.toUpperCase()) {
            case "IN_PROGRESS" -> IN_PROGRESS;
            case "COMPLETED" -> COMPLETED;
            case "STORE_DELIVERY" -> STORE_DELIVERY;
            case "TICKET_ISSUE" -> TICKET_ISSUE;
            case "REPORT" -> REPORT;
            default -> throw new IllegalArgumentException("이벤트 상태가 잘못되었습니다.");
        };
    }
}
