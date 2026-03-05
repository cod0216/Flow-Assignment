package com.example.demo.domain.extensionpolicy.dto.response;

import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * packageName    : com.example.demo.domain.extensionpolicy.dto.response<br>
 * fileName       : ExtensionPolicyResponse.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 정책 화면 통합 조회 응답 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
@Builder
public class ExtensionPolicyResponse {
    private List<FixedExtensionResponse> fixedExtensions;
    private List<CustomExtensionResponse> customExtensions;
    private long customCount;
    private long customLimit;
}
