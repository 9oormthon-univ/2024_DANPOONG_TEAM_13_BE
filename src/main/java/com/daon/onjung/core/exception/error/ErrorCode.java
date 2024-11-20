package com.daon.onjung.core.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Method Not Allowed Error
    METHOD_NOT_ALLOWED(40500, HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드입니다."),

    // Not Found Error
    NOT_FOUND_END_POINT(40400, HttpStatus.NOT_FOUND, "존재하지 않는 API 엔드포인트입니다."),
    NOT_FOUND_RESOURCE(40404, HttpStatus.NOT_FOUND, "해당 리소스가 존재하지 않습니다."),
    NOT_FOUND_MATCHED_STORE(40410, HttpStatus.NOT_FOUND, "영수증 내용과 일치하는 매장이 존재하지 않습니다."),

    // Invalid Argument Error
    MISSING_REQUEST_PARAMETER(40000, HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    INVALID_ARGUMENT(40001, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),
    INVALID_PARAMETER_FORMAT(40002, HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자 형식입니다."),
    INVALID_HEADER_ERROR(40003, HttpStatus.BAD_REQUEST, "유효하지 않은 헤더입니다."),
    MISSING_REQUEST_HEADER(40004, HttpStatus.BAD_REQUEST, "필수 요청 헤더가 누락되었습니다."),
    BAD_REQUEST_PARAMETER(40005, HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
    UNSUPPORTED_MEDIA_TYPE(40006, HttpStatus.BAD_REQUEST, "지원하지 않는 미디어 타입입니다."),
    BAD_REQUEST_JSON(40007, HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다."),

    // OCR Error
    OCR_RECOGNITION_ERROR(40010, HttpStatus.BAD_REQUEST, "영수증 인식에 실패하였습니다."),

    // Access Denied Error
    ACCESS_DENIED(40300, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    NOT_LOGIN_USER(40301, HttpStatus.FORBIDDEN, "로그인하지 않은 사용자입니다."),

    // Unauthorized Error
    FAILURE_LOGIN(40100, HttpStatus.UNAUTHORIZED, "잘못된 아이디 또는 비밀번호입니다."),
    EXPIRED_TOKEN_ERROR(40101, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN_ERROR(40102, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_MALFORMED_ERROR(40103, HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
    TOKEN_TYPE_ERROR(40104, HttpStatus.UNAUTHORIZED, "토큰 타입이 일치하지 않거나 비어있습니다."),
    TOKEN_UNSUPPORTED_ERROR(40105, HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    TOKEN_GENERATION_ERROR(40106, HttpStatus.UNAUTHORIZED, "토큰 생성에 실패하였습니다."),
    TOKEN_UNKNOWN_ERROR(40107, HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(50000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다."),
    INTERNAL_DATA_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 데이터 에러입니다."),
    UPLOAD_FILE_ERROR(50002, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다."),
    SCHEDULER_ERROR(50003, HttpStatus.INTERNAL_SERVER_ERROR, "스케줄러 등록에 실패하였습니다."),

    // External Server Error
    EXTERNAL_SERVER_ERROR(50200, HttpStatus.BAD_GATEWAY, "서버 외부 에러입니다."),
    ;


    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
