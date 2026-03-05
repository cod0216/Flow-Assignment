package com.example.demo.domain.fixedextension.service;

import com.example.demo.domain.fixedextension.entity.FixedExtensionEntity;
import com.example.demo.domain.fixedextension.mapper.FixedExtensionMapper;
import com.example.demo.domain.fixedextension.repository.FixedExtensionRepository;
import com.example.demo.global.common.StatusCode;
import com.example.demo.global.error.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

/**
 * packageName    : com.example.demo.domain.fixedextension.service<br>
 * fileName       : FixedExtensionServiceTest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : FixedExtensionService 단위 테스트 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
class FixedExtensionServiceTest {

    @Mock
    private FixedExtensionRepository fixedExtensionRepository;

    private final FixedExtensionMapper fixedExtensionMapper = new FixedExtensionMapper();

    private FixedExtensionService fixedExtensionService;

    @BeforeEach
    void setUp() {
        fixedExtensionService = new FixedExtensionService(fixedExtensionRepository, fixedExtensionMapper);
    }

    @Test
    @DisplayName("고정 확장자 목록 조회에 성공한다")
    void getFixedExtensionsSuccess() {
        // Given
        given(fixedExtensionRepository.findAllByOrderByExtensionAsc())
                .willReturn(List.of(new FixedExtensionEntity("exe", true), new FixedExtensionEntity("js", false)));

        // When
        var result = fixedExtensionService.getFixedExtensions();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getExtension()).isEqualTo("exe");
        assertThat(result.get(0).isBlocked()).isTrue();
    }

    @Test
    @DisplayName("고정 확장자 상태 변경에 성공한다")
    void updateFixedExtensionSuccess() {
        // Given
        FixedExtensionEntity entity = new FixedExtensionEntity("exe", false);
        given(fixedExtensionRepository.findByExtension("exe")).willReturn(Optional.of(entity));

        // When
        var result = fixedExtensionService.updateFixedExtension(".Exe", true);

        // Then
        assertThat(result.isBlocked()).isTrue();
        assertThat(entity.isBlocked()).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 고정 확장자면 예외가 발생한다")
    void updateFixedExtensionNotFound() {
        // Given
        given(fixedExtensionRepository.findByExtension("exe")).willReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> fixedExtensionService.updateFixedExtension("exe", true))
                .isInstanceOf(CustomException.class)
                .extracting("statusCode")
                .isEqualTo(StatusCode.FIXED_EXTENSION_NOT_FOUND);
    }
}
