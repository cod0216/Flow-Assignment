package com.example.demo.global.error;

import com.example.demo.global.common.StatusCode;
import com.example.demo.global.response.FailureResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * packageName    : com.example.demo.global.error<br>
 * fileName       : GlobalExceptionHandler.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 전역 예외를 공통 응답 형식으로 변환하는 핸들러 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<FailureResponseDTO> handleCustomException(CustomException exception) {
        StatusCode statusCode = exception.getStatusCode();
        return ResponseEntity.status(statusCode.getStatus())
                .body(FailureResponseDTO.create(statusCode, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailureResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String message = Objects.nonNull(fieldError) ? fieldError.getDefaultMessage() : StatusCode.INVALID_EXTENSION_FORMAT.getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(FailureResponseDTO.create(StatusCode.INVALID_EXTENSION_FORMAT, message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<FailureResponseDTO> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(FailureResponseDTO.create(StatusCode.INVALID_EXTENSION_FORMAT));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FailureResponseDTO> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(FailureResponseDTO.create(StatusCode.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }
}
