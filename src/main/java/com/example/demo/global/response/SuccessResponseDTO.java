package com.example.demo.global.response;

import com.example.demo.global.common.StatusCode;
import lombok.Getter;

/**
 * packageName    : com.example.demo.global.response<br>
 * fileName       : SuccessResponseDTO.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 성공 응답 payload를 담는 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
public final class SuccessResponseDTO<T> extends ResponseDTO {
    private final T data;

    private SuccessResponseDTO(T data) {
        super(true, StatusCode.SUCCESS);
        this.data = data;
    }

    private SuccessResponseDTO(T data, String message) {
        super(true, StatusCode.SUCCESS, message);
        this.data = data;
    }

    public static <T> SuccessResponseDTO<T> create(T data) {
        return new SuccessResponseDTO<>(data);
    }

    public static <T> SuccessResponseDTO<T> create(T data, String message) {
        return new SuccessResponseDTO<>(data, message);
    }
}
