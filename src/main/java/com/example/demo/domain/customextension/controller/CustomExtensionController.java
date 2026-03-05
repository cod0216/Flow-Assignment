package com.example.demo.domain.customextension.controller;

import com.example.demo.domain.customextension.dto.request.CustomExtensionCreateRequest;
import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.customextension.service.CustomExtensionService;
import com.example.demo.global.response.SuccessResponseDTO;
import com.example.demo.global.util.ResponseUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * packageName    : com.example.demo.domain.customextension.controller<br>
 * fileName       : CustomExtensionController.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 커스텀 확장자 API 요청을 처리하는 Controller 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@RestController
@RequestMapping("/api/v1/custom-extensions")
@RequiredArgsConstructor
public class CustomExtensionController {
    private final CustomExtensionService customExtensionService;

    @GetMapping
    public ResponseEntity<SuccessResponseDTO<List<CustomExtensionResponse>>> getCustomExtensions() {
        return ResponseUtility.success(customExtensionService.getCustomExtensions());
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDTO<CustomExtensionResponse>> addCustomExtension(
            @Valid @RequestBody CustomExtensionCreateRequest request
    ) {
        return ResponseUtility.success(customExtensionService.addCustomExtension(request.getExtension()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO<Map<String, Object>>> removeCustomExtension(@PathVariable("id") Long id) {
        customExtensionService.removeCustomExtension(id);
        return ResponseUtility.success(Map.of("deletedId", id));
    }
}
