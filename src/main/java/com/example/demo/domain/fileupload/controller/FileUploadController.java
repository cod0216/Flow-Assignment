package com.example.demo.domain.fileupload.controller;

import com.example.demo.domain.fileupload.dto.request.FileUploadRequest;
import com.example.demo.domain.fileupload.dto.response.UploadedFileResponse;
import com.example.demo.domain.fileupload.service.FileUploadComplexService;
import com.example.demo.global.response.SuccessResponseDTO;
import com.example.demo.global.util.ResponseUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.example.demo.domain.fileupload.controller<br>
 * fileName       : FileUploadController.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 파일 업로드 API 요청을 처리하는 Controller 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadComplexService fileUploadComplexService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponseDTO<UploadedFileResponse>> uploadFile(
            @Valid @ModelAttribute FileUploadRequest request
    ) {
        return ResponseUtility.success(fileUploadComplexService.uploadFile(request.getFile()));
    }
}
