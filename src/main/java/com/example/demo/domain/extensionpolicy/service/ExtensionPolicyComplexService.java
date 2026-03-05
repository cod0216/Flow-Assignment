package com.example.demo.domain.extensionpolicy.service;

import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.customextension.service.CustomExtensionService;
import com.example.demo.domain.extensionpolicy.dto.response.ExtensionPolicyResponse;
import com.example.demo.domain.extensionpolicy.mapper.ExtensionPolicyMapper;
import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import com.example.demo.domain.fixedextension.service.FixedExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : com.example.demo.domain.extensionpolicy.service<br>
 * fileName       : ExtensionPolicyComplexService.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 복합 정책 조회를 처리하는 ComplexService 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtensionPolicyComplexService {
    private static final long CUSTOM_LIMIT = 200L;

    private final FixedExtensionService fixedExtensionService;
    private final CustomExtensionService customExtensionService;
    private final ExtensionPolicyMapper extensionPolicyMapper;

    public ExtensionPolicyResponse getExtensionPolicy() {
        List<FixedExtensionResponse> fixedExtensions = fixedExtensionService.getFixedExtensions();
        List<CustomExtensionResponse> customExtensions = customExtensionService.getCustomExtensions();
        long customCount = customExtensionService.getCustomExtensionCount();

        return extensionPolicyMapper.toResponse(fixedExtensions, customExtensions, customCount, CUSTOM_LIMIT);
    }
}
