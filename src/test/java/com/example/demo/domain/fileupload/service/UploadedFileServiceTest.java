package com.example.demo.domain.fileupload.service;

import com.example.demo.domain.fileupload.mapper.FileUploadMapper;
import com.example.demo.domain.fileupload.repository.UploadedFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * packageName    : com.example.demo.domain.fileupload.service<br>
 * fileName       : UploadedFileServiceTest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : UploadedFileService 단위 테스트 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
class UploadedFileServiceTest {

    @Mock
    private UploadedFileRepository uploadedFileRepository;

    private UploadedFileService uploadedFileService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        uploadedFileService = new UploadedFileService(uploadedFileRepository, new FileUploadMapper());
        ReflectionTestUtils.setField(uploadedFileService, "uploadDir", tempDir.toString());
        given(uploadedFileRepository.save(any())).willAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @DisplayName("파일 저장에 성공하면 로컬 경로에 파일이 생성된다")
    void storeFileSuccess() {
        // Given
        MockMultipartFile file = new MockMultipartFile("file", "sample.txt", "text/plain", "sample-content".getBytes());

        // When
        var response = uploadedFileService.storeFile(file, "txt", "text/plain");

        // Then
        assertThat(response.getStoredFileName()).isNotBlank();
        assertThat(response.getStoragePath()).isNotBlank();
        assertThat(Files.exists(Path.of(response.getStoragePath()))).isTrue();
    }
}
