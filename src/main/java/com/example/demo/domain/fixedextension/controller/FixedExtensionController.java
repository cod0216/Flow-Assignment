package com.example.demo.domain.fixedextension.controller;

import com.example.demo.domain.fixedextension.dto.request.FixedExtensionUpdateRequest;
import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import com.example.demo.domain.fixedextension.service.FixedExtensionService;
import com.example.demo.global.response.SuccessResponseDTO;
import com.example.demo.global.util.ResponseUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.example.demo.domain.fixedextension.controller<br>
 * fileName       : FixedExtensionController.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 고정 확장자 API 요청을 처리하는 Controller 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@RestController
@RequestMapping("/api/v1/fixed-extensions")
@RequiredArgsConstructor
public class FixedExtensionController {
    private final FixedExtensionService fixedExtensionService;

    @GetMapping
    public ResponseEntity<SuccessResponseDTO<List<FixedExtensionResponse>>> getFixedExtensions() {
        return ResponseUtility.success(fixedExtensionService.getFixedExtensions());
    }

    @PutMapping("/{extension}")
    public ResponseEntity<SuccessResponseDTO<FixedExtensionResponse>> updateFixedExtension(
            @PathVariable("extension") String extension,
            @Valid @RequestBody FixedExtensionUpdateRequest request
    ) {
        return ResponseUtility.success(fixedExtensionService.updateFixedExtension(extension, request.getBlocked()));
    }
}
