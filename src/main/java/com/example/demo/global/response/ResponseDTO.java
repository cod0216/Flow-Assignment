package com.example.demo.global.response;

import com.example.demo.global.common.StatusCode;
import lombok.Getter;

/**
 * packageName    : com.example.demo.global.response<br>
 * fileName       : ResponseDTO.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 공통 응답 필드를 담는 추상 응답 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
public abstract class ResponseDTO {
    private final boolean success;
    private final int httpCode;
    private final String httpStatus;
    private final String serverCode;
    private final String message;

    protected ResponseDTO(boolean success, StatusCode statusCode) {
        this.success = success;
        this.httpCode = statusCode.getStatus().value();
        this.httpStatus = statusCode.getStatus().name();
        this.serverCode = statusCode.getCode();
        this.message = statusCode.getMessage();
    }

    protected ResponseDTO(boolean success, StatusCode statusCode, String message) {
        this.success = success;
        this.httpCode = statusCode.getStatus().value();
        this.httpStatus = statusCode.getStatus().name();
        this.serverCode = statusCode.getCode();
        this.message = message;
    }
}
