package com.example.demo.domain.fileupload.service;

import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.customextension.service.CustomExtensionService;
import com.example.demo.domain.fileupload.dto.response.UploadedFileResponse;
import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import com.example.demo.domain.fixedextension.service.FixedExtensionService;
import com.example.demo.global.common.StatusCode;
import com.example.demo.global.error.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.demo.domain.fileupload.service<br>
 * fileName       : FileUploadComplexServiceTest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : FileUploadComplexService 단위 테스트 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
class FileUploadComplexServiceTest {

    @Mock
    private UploadedFileService uploadedFileService;

    @Mock
    private FixedExtensionService fixedExtensionService;

    @Mock
    private CustomExtensionService customExtensionService;

    private FileUploadComplexService fileUploadComplexService;

    @BeforeEach
    void setUp() {
        fileUploadComplexService = new FileUploadComplexService(uploadedFileService, fixedExtensionService, customExtensionService);
    }

    @Test
    @DisplayName("차단된 확장자면 업로드가 거절된다")
    void uploadBlockedExtension() {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "virus.exe", "application/octet-stream", "dummy".getBytes());
        given(fixedExtensionService.getFixedExtensions()).willReturn(List.of(
                FixedExtensionResponse.builder().extension("exe").blocked(true).build()
        ));
        given(customExtensionService.getCustomExtensions()).willReturn(List.of());

        // When / Then
        assertThatThrownBy(() -> fileUploadComplexService.uploadFile(file))
                .isInstanceOf(CustomException.class)
                .extracting("statusCode")
                .isEqualTo(StatusCode.FILE_EXTENSION_BLOCKED);
    }

    @Test
    @DisplayName("확장자가 안전해도 위험 시그니처면 업로드가 거절된다")
    void uploadBlockedByMagicBytes() {
        // Given
        byte[] executableHeader = new byte[]{'M', 'Z', 0x00, 0x00};
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", executableHeader);

        given(fixedExtensionService.getFixedExtensions()).willReturn(List.of(
                FixedExtensionResponse.builder().extension("exe").blocked(true).build()
        ));
        given(customExtensionService.getCustomExtensions()).willReturn(List.of());

        // When / Then
        assertThatThrownBy(() -> fileUploadComplexService.uploadFile(file))
                .isInstanceOf(CustomException.class)
                .extracting("statusCode")
                .isEqualTo(StatusCode.FILE_SIGNATURE_BLOCKED);
    }

    @Test
    @DisplayName("검증 통과 파일은 저장 서비스로 전달된다")
    void uploadSuccess() {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "note.txt", "text/plain", "hello".getBytes());

        given(fixedExtensionService.getFixedExtensions()).willReturn(List.of(
                FixedExtensionResponse.builder().extension("exe").blocked(true).build()
        ));
        given(customExtensionService.getCustomExtensions()).willReturn(List.of(
                CustomExtensionResponse.builder().id(1L).extension("sh").build()
        ));
        given(uploadedFileService.storeFile(any(), eq("txt"), any()))
                .willReturn(UploadedFileResponse.builder().originalFileName("note.txt").storedFileName("stored.txt").build());

        // When
        UploadedFileResponse response = fileUploadComplexService.uploadFile(file);

        // Then
        ArgumentCaptor<String> extensionCaptor = ArgumentCaptor.forClass(String.class);
        verify(uploadedFileService).storeFile(eq(file), extensionCaptor.capture(), any());
        assertThat(extensionCaptor.getValue()).isEqualTo("txt");
        assertThat(response.getStoredFileName()).isEqualTo("stored.txt");
    }
}
