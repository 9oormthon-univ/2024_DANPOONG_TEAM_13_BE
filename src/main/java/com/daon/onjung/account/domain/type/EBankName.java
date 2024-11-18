package com.daon.onjung.account.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EBankName {

    KB("국민은행"),
    SOL("신한은행"),
    WON("우리은행"),
    KEB("하나은행"),
    KAKAO("카카오뱅크"),
    TOSS("토스뱅크"),
    KBANK("케이뱅크"),
    IBK("기업은행"),
    NH("농협은행");

    private final String koName;

    public static EBankName fromString(String value) {
        return switch (value.toUpperCase()) {
            case "KB" -> KB;
            case "SOL" -> SOL;
            case "WON" -> WON;
            case "KEB" -> KEB;
            case "KAKAO" -> KAKAO;
            case "TOSS" -> TOSS;
            case "KBANK" -> KBANK;
            case "IBK" -> IBK;
            case "NH" -> NH;
            default -> throw new IllegalArgumentException("은행 이름이 잘못되었습니다.");
        };
    }
}