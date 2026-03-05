package com.example.demo.domain.extensionpolicy.controller;

import com.example.demo.domain.extensionpolicy.dto.response.ExtensionPolicyResponse;
import com.example.demo.domain.extensionpolicy.service.ExtensionPolicyComplexService;
import com.example.demo.global.response.SuccessResponseDTO;
import com.example.demo.global.util.ResponseUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.demo.domain.extensionpolicy.controller<br>
 * fileName       : ExtensionPolicyController.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 정책 통합 조회 API 요청을 처리하는 Controller 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@RestController
@RequestMapping("/api/v1/extensions")
@RequiredArgsConstructor
public class ExtensionPolicyController {
    private final ExtensionPolicyComplexService extensionPolicyComplexService;

    @GetMapping("/policy")
    public ResponseEntity<SuccessResponseDTO<ExtensionPolicyResponse>> getPolicy() {
        return ResponseUtility.success(extensionPolicyComplexService.getExtensionPolicy());
    }
}
