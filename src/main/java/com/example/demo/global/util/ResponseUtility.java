package com.example.demo.global.util;

import com.example.demo.global.common.StatusCode;
import com.example.demo.global.response.FailureResponseDTO;
import com.example.demo.global.response.SuccessResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * packageName    : com.example.demo.global.util<br>
 * fileName       : ResponseUtility.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 공통 성공/실패 응답 생성을 담당하는 유틸리티 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
public final class ResponseUtility {
    private ResponseUtility() {
        throw new UnsupportedOperationException("Utility 클래스는 인스턴스화 할 수 없습니다.");
    }

    public static <T> ResponseEntity<SuccessResponseDTO<T>> success(T data) {
        return ResponseEntity.ok(SuccessResponseDTO.create(data));
    }

    public static <T> ResponseEntity<SuccessResponseDTO<T>> success(T data, String message) {
        return ResponseEntity.ok(SuccessResponseDTO.create(data, message));
    }

    public static ResponseEntity<FailureResponseDTO> failure(StatusCode statusCode) {
        return ResponseEntity.status(statusCode.getStatus()).body(FailureResponseDTO.create(statusCode));
    }

    public static ResponseEntity<FailureResponseDTO> failure(StatusCode statusCode, String message) {
        return ResponseEntity.status(statusCode.getStatus()).body(FailureResponseDTO.create(statusCode, message));
    }
}
