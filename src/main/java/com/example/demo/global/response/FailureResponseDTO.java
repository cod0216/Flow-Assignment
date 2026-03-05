package com.example.demo.global.response;

import com.example.demo.global.common.StatusCode;

/**
 * packageName    : com.example.demo.global.response<br>
 * fileName       : FailureResponseDTO.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 실패 응답 payload를 담는 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
public final class FailureResponseDTO extends ResponseDTO {
    private FailureResponseDTO(StatusCode statusCode) {
        super(false, statusCode);
    }

    private FailureResponseDTO(StatusCode statusCode, String message) {
        super(false, statusCode, message);
    }

    public static FailureResponseDTO create(StatusCode statusCode) {
        return new FailureResponseDTO(statusCode);
    }

    public static FailureResponseDTO create(StatusCode statusCode, String message) {
        return new FailureResponseDTO(statusCode, message);
    }
}
