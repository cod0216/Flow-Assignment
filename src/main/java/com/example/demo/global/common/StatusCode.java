package com.example.demo.global.common;

import org.springframework.http.HttpStatus;

/**
 * packageName    : com.example.demo.global.common<br>
 * fileName       : StatusCode.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 공통 응답 상태 코드를 정의하는 enum 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
public enum StatusCode {
    SUCCESS(HttpStatus.OK, "SUCCESS-200", "요청이 완료되었습니다."),

    INVALID_EXTENSION_FORMAT(HttpStatus.BAD_REQUEST, "EXT-400-001", "확장자 형식이 올바르지 않습니다."),
    EXTENSION_TOO_LONG(HttpStatus.BAD_REQUEST, "EXT-400-002", "확장자는 최대 20자까지 입력할 수 있습니다."),
    CUSTOM_EXTENSION_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "EXT-400-003", "커스텀 확장자는 최대 200개까지 가능합니다."),

    FIXED_EXTENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "EXT-404-001", "고정 확장자를 찾을 수 없습니다."),
    CUSTOM_EXTENSION_NOT_FOUND(HttpStatus.NOT_FOUND, "EXT-404-002", "커스텀 확장자를 찾을 수 없습니다."),

    CUSTOM_EXTENSION_DUPLICATE(HttpStatus.CONFLICT, "EXT-409-001", "이미 존재하는 커스텀 확장자입니다."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON-500", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    StatusCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
