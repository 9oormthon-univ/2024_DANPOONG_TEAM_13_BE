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
}