package com.example.demo.domain.fixedextension.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.example.demo.domain.fixedextension.dto.response<br>
 * fileName       : FixedExtensionResponse.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 고정 확장자 조회 응답 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
@Builder
public class FixedExtensionResponse {
    private String extension;
    private boolean blocked;
}
