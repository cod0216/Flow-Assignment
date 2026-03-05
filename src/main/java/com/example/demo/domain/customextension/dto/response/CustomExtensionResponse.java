package com.example.demo.domain.customextension.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : com.example.demo.domain.customextension.dto.response<br>
 * fileName       : CustomExtensionResponse.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 커스텀 확장자 조회 응답 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
@Builder
public class CustomExtensionResponse {
    private Long id;
    private String extension;
}
