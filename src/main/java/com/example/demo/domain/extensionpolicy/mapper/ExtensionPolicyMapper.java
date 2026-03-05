package com.example.demo.domain.extensionpolicy.mapper;

import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.extensionpolicy.dto.response.ExtensionPolicyResponse;
import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * packageName    : com.example.demo.domain.extensionpolicy.mapper<br>
 * fileName       : ExtensionPolicyMapper.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 통합 정책 응답 DTO 매핑을 담당하는 Mapper 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Component
public class ExtensionPolicyMapper {

    public ExtensionPolicyResponse toResponse(
            List<FixedExtensionResponse> fixedExtensions,
            List<CustomExtensionResponse> customExtensions,
            long customCount,
            long customLimit
    ) {
        return ExtensionPolicyResponse.builder()
                .fixedExtensions(fixedExtensions)
                .customExtensions(customExtensions)
                .customCount(customCount)
                .customLimit(customLimit)
                .build();
    }
}
