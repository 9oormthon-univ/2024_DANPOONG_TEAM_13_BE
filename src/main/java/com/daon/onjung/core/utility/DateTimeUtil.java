package com.daon.onjung.core.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

/**
 * 날짜 및 시간 관련 유틸리티 클래스
 */
public class DateTimeUtil {

    public static final DateTimeFormatter ISODateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter ISODateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter ISOTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter KORDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"); // 새로운 포맷터 추가
    public static final DateTimeFormatter SHORTKORDateFormatter = DateTimeFormatter.ofPattern("yy년 MM월 dd일");
    public static final DateTimeFormatter KORYearMonthDateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월"); // 새로운 포맷터 추가
    public static final DateTimeFormatter DotSeparatedDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    public static final DateTimeFormatter CustomDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (EEE)", Locale.KOREAN);
    public static final DateTimeFormatter CustomDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH시 mm분", Locale.KOREAN);

    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(
            DateTimeFormatter.ofPattern("yyyyMMdd"),
            DateTimeFormatter.ofPattern("yyyyMMdd(E)"),
            DateTimeFormatter.ofPattern("yyyyMMdd (E)"),
            DateTimeFormatter.ofPattern("yyyyMMdd E"),
            DateTimeFormatter.ofPattern("yyyyMMddHHmm"),
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss"),
            DateTimeFormatter.ofPattern("yyyyMMdd HHmm"),
            DateTimeFormatter.ofPattern("yyyyMMdd HHmmss"),
            DateTimeFormatter.ofPattern("yyyyMMddHH:mm"),
            DateTimeFormatter.ofPattern("yyyyMMddHH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy-M-d"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd(E)"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd (E)"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd E"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("yyyy/M/d"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd(E)"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd (E)"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd E"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd"),
            DateTimeFormatter.ofPattern("yyyy.M.d"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd(E)"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd (E)"),
            DateTimeFormatter.ofPattern("yyyy.MM.dd E"),
            DateTimeFormatter.ofPattern("yyyy년 M월 d일"),
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"),
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일(E)"),
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 (E)"),
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 E")
    );

    /**
     * String을 LocalDateTime으로 변환
     *
     * @param date String
     * @return LocalDateTime
     */
    public static LocalDateTime convertStringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, ISODateTimeFormatter);
    }

    /**
     * LocalDateTime을 String으로 변환
     *
     * @param date LocalDateTime
     * @return String
     */
    public static String convertLocalDateTimeToString(LocalDateTime date) {
        return date.format(ISODateTimeFormatter);
    }

    /**
     * String을 LocalTime으로 변환
     *
     * @param time String
     * @return LocalTime
     */
    public static LocalTime convertStringToLocalTime(String time) {
        return LocalTime.parse(time, ISOTimeFormatter);
    }

    /**
     * LocalTime을 String으로 변환
     *
     * @param time LocalTime
     * @return String
     */
    public static String convertLocalTimeToString(LocalTime time) {
        return time.format(ISOTimeFormatter);
    }

    /**
     * String을 LocalDate로 변환
     *
     * @param date String
     * @return LocalDate
     */
    public static LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date, ISODateFormatter);
    }

    /**
     * LocalDate를 String으로 변환
     *
     * @param date LocalDate
     * @return String
     */
    public static String convertLocalDateToString(LocalDate date) {
        return date.format(ISODateFormatter);
    }

    /**
     * LocalDate를 한국어 날짜 형식으로 변환 (yyyy년 MM월 dd일)
     *
     * @param date LocalDate
     * @return String
     */
    public static String convertLocalDateToKORString(LocalDateTime date) {
        return date.format(KORDateFormatter);
    }

    /**
     * LocalDateTime을 짧은 한국어 날짜 형식으로 변환 (yy년 MM월 dd일)
     *
     * @param date LocalDateTime
     * @return String
     */
    public static String convertLocalDateTimeToSHORTKORString(LocalDateTime date) {
        return date.format(SHORTKORDateFormatter);
    }

    /**
     * String(한국어 날짜 형식, yyyy년 MM월 dd일)을 LocalDate로 변환
     *
     * @param date String
     * @return LocalDate
     */
    public static LocalDateTime convertKORStringToLocalDate(String date) {
        return LocalDateTime.parse(date, KORDateFormatter);
    }

    /**
     * LocalDateTime을 한국어 날짜 형식으로 변환 (yyyy년 MM월)
     *
     * @param date LocalDate
     * @return String
     */
    public static String convertLocalDateToKORYearMonthString(LocalDate date) {
        return date.format(KORYearMonthDateFormatter);
    }

    /**
     * LocalDateTime을 한국어 날짜 형식으로 변환 (yyyy년 MM월)
     *
     * @param date LocalDate
     * @return String
     */
    public static LocalDate convertKORSYearMonthtringToLocalDate(String date) {
        return LocalDate.parse(date, KORYearMonthDateFormatter);
    }


    /**
     * 두 날짜 사이의 일 수 계산
     *
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return Integer
     */
    public static Integer calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) (endDate.toEpochDay() - startDate.toEpochDay());
    }

    /**
     * 알림 시간 포맷팅: "mins ago", "hours ago", "yyyy.MM.dd (요일)"
     *
     * @param dateTime LocalDateTime
     * @return String
     */
    public static String convertLocalDateTimeToCustomDate(LocalDateTime dateTime) {
        Duration duration = Duration.between(dateTime, LocalDateTime.now());
        long minutesAgo = duration.toMinutes();
        long hoursAgo = duration.toHours();

        if (minutesAgo < 60) {
            return minutesAgo + " mins ago";
        } else if (hoursAgo < 24) {
            return hoursAgo + " hours ago";
        } else {
            return dateTime.format(CustomDateFormatter);
        }
    }

    /**
     * 홈화면 시간 포맷팅: "yyyy.MM.dd HH시 mm분"
     *
     * @param dateTime LocalDateTime
     * @return String
     */
    public static String convertLocalDateTimeToCustomDateTime(LocalDateTime dateTime) {
        return dateTime.format(CustomDateTimeFormatter);
    }


    /**
     * 나의 식권 조회 시간 포맷팅: "yyyy.MM.dd"
     *
     * @param date LocalDate
     * @return String
     */
    public static String convertLocalDateToDotSeparatedDateTime(LocalDate date) {
        if (date == null) {
            return null; // 온기 우편함 조회 null 처리
        }

        return date.format(DotSeparatedDateFormatter);
    }

    /**
     * 나의 온기 우편함 조회 시간 포맷팅: "yyyy.MM.dd"
     *
     * @param startDate LocalDateTime
     * @param endDate LocalDateTime
     * @return String
     */
    public static String convertLocalDatesToDotSeparatedDatePeriod(LocalDate startDate, LocalDate endDate) {
        return startDate.format(DotSeparatedDateFormatter) + " - " + endDate.format(DotSeparatedDateFormatter);
    }


    /**
     * String을 LocalDate 형식으로 변환 (yyyy. MM. dd)
     *
     * @param date LocalDate
     * @return String
     */
    public static LocalDate convertDotSeparatedToLocalDate(String date) {
        return LocalDate.parse(date, DotSeparatedDateFormatter);
    }

    /**
     * 나의 온기 조회 시간 포맷팅: "yyyy.MM.dd"
     *
     * @param dateTime LocalDate
     * @return String
     */
    public static String convertLocalDateTimeToDotSeparatedDateTime(LocalDateTime dateTime) {
        return dateTime.format(DotSeparatedDateFormatter);
    }


    /**
     * 날짜 문자열을 특정 포맷으로 변환
     *
     * @param dateString 날짜 문자열
     * @return String
     */
    public static String formatDateString(String dateString) {
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                return date.format(ISODateFormatter);
            } catch (DateTimeParseException e) {
                // Ignore and try the next format
            }
        }
        throw new IllegalArgumentException("Unrecognized date format: " + dateString);
    }

    /**
     * 게시글 및 댓글 작성 시간이 현재로부터 얼마나되었는지를 계산하여 반환
     *
     * @param postedAt 작성 시각
     * @return 게시글 작성 시각과 현재 시각의 차이(예: 1분 전, 1시간 전, 1일 전, 1달 전, 1년 전)
     */
    public static String calculatePostedAgo(LocalDateTime postedAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(postedAt, now);

        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long months = days / 30;
        long years = days / 365;

        if (minutes < 1) {
            return seconds + "초 전";
        } else if (hours < 1) {
            return minutes + "분 전";
        } else if (days < 1) {
            return hours + "시간 전";
        } else if (months < 1) {
            return days + "일 전";
        } else if (years < 1) {
            return months + "달 전";
        } else {
            return years + "년 전";
        }
    }
}

