package com.example.demo.domain.customextension.service;

import com.example.demo.domain.customextension.entity.CustomExtensionEntity;
import com.example.demo.domain.customextension.mapper.CustomExtensionMapper;
import com.example.demo.domain.customextension.repository.CustomExtensionRepository;
import com.example.demo.global.common.StatusCode;
import com.example.demo.global.error.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * packageName    : com.example.demo.domain.customextension.service<br>
 * fileName       : CustomExtensionServiceTest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : CustomExtensionService 단위 테스트 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
class CustomExtensionServiceTest {

    @Mock
    private CustomExtensionRepository customExtensionRepository;

    private final CustomExtensionMapper customExtensionMapper = new CustomExtensionMapper();

    private CustomExtensionService customExtensionService;

    @BeforeEach
    void setUp() {
        customExtensionService = new CustomExtensionService(customExtensionRepository, customExtensionMapper);
    }

    @Test
    @DisplayName("커스텀 확장자 추가에 성공한다")
    void addCustomExtensionSuccess() {
        // Given
        given(customExtensionRepository.count()).willReturn(0L);
        given(customExtensionRepository.existsByExtension("sh")).willReturn(false);
        given(customExtensionRepository.save(any(CustomExtensionEntity.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // When
        var result = customExtensionService.addCustomExtension(".Sh ");

        // Then
        ArgumentCaptor<CustomExtensionEntity> captor = ArgumentCaptor.forClass(CustomExtensionEntity.class);
        verify(customExtensionRepository).save(captor.capture());
        assertThat(captor.getValue().getExtension()).isEqualTo("sh");
        assertThat(result.getExtension()).isEqualTo("sh");
    }

    @Test
    @DisplayName("커스텀 확장자가 중복이면 예외가 발생한다")
    void addCustomExtensionDuplicate() {
        // Given
        given(customExtensionRepository.count()).willReturn(10L);
        given(customExtensionRepository.existsByExtension("sh")).willReturn(true);

        // When / Then
        assertThatThrownBy(() -> customExtensionService.addCustomExtension("sh"))
                .isInstanceOf(CustomException.class)
                .extracting("statusCode")
                .isEqualTo(StatusCode.CUSTOM_EXTENSION_DUPLICATE);
    }

    @Test
    @DisplayName("커스텀 확장자 개수 제한을 넘으면 예외가 발생한다")
    void addCustomExtensionLimitExceeded() {
        // Given
        given(customExtensionRepository.count()).willReturn(200L);

        // When / Then
        assertThatThrownBy(() -> customExtensionService.addCustomExtension("sh"))
                .isInstanceOf(CustomException.class)
                .extracting("statusCode")
                .isEqualTo(StatusCode.CUSTOM_EXTENSION_LIMIT_EXCEEDED);
    }

    @Test
    @DisplayName("커스텀 확장자 길이가 20자를 넘으면 예외가 발생한다")
    void addCustomExtensionTooLong() {
        // Given
        String tooLong = "abcdefghijklmnopqrstu";

        // When / Then
        assertThatThrownBy(() -> customExtensionService.addCustomExtension(tooLong))
                .isInstanceOf(CustomException.class)
                .extracting("statusCode")
                .isEqualTo(StatusCode.EXTENSION_TOO_LONG);
    }

    @Test
    @DisplayName("존재하지 않는 커스텀 확장자 삭제시 예외가 발생한다")
    void removeCustomExtensionNotFound() {
        // Given
        given(customExtensionRepository.findById(1L)).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> customExtensionService.removeCustomExtension(1L))
                .isInstanceOf(CustomException.class)
                .extracting("statusCode")
                .isEqualTo(StatusCode.CUSTOM_EXTENSION_NOT_FOUND);
    }
}
