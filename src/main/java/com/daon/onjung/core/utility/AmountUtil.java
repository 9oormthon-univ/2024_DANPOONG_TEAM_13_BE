package com.daon.onjung.core.utility;

/**
 * 금액 관련 유틸리티 클래스
 */
public class AmountUtil {
    public static String convertToWon(Integer amount) {
        return amount / 10000 + "만원";
    }
}
