package com.example.demo.global.error;

import com.example.demo.global.common.StatusCode;

/**
 * packageName    : com.example.demo.global.error<br>
 * fileName       : CustomException.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 비즈니스 예외를 처리하는 커스텀 예외 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
public class CustomException extends RuntimeException {
    private final StatusCode statusCode;

    public CustomException(StatusCode statusCode) {
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }

    public CustomException(StatusCode statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
