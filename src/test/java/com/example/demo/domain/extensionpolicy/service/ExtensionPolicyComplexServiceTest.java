package com.example.demo.domain.extensionpolicy.service;

import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.customextension.service.CustomExtensionService;
import com.example.demo.domain.extensionpolicy.mapper.ExtensionPolicyMapper;
import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import com.example.demo.domain.fixedextension.service.FixedExtensionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * packageName    : com.example.demo.domain.extensionpolicy.service<br>
 * fileName       : ExtensionPolicyComplexServiceTest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : ExtensionPolicyComplexService 단위 테스트 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@ExtendWith(MockitoExtension.class)
class ExtensionPolicyComplexServiceTest {

    @Mock
    private FixedExtensionService fixedExtensionService;

    @Mock
    private CustomExtensionService customExtensionService;

    private final ExtensionPolicyMapper extensionPolicyMapper = new ExtensionPolicyMapper();

    private ExtensionPolicyComplexService extensionPolicyComplexService;

    @BeforeEach
    void setUp() {
        extensionPolicyComplexService = new ExtensionPolicyComplexService(
                fixedExtensionService,
                customExtensionService,
                extensionPolicyMapper
        );
    }

    @Test
    @DisplayName("통합 정책 조회에 성공한다")
    void getExtensionPolicySuccess() {
        // Given
        given(fixedExtensionService.getFixedExtensions()).willReturn(List.of(
                FixedExtensionResponse.builder().extension("exe").blocked(true).build()
        ));
        given(customExtensionService.getCustomExtensions()).willReturn(List.of(
                CustomExtensionResponse.builder().id(1L).extension("sh").build()
        ));
        given(customExtensionService.getCustomExtensionCount()).willReturn(1L);

        // When
        var result = extensionPolicyComplexService.getExtensionPolicy();

        // Then
        assertThat(result.getFixedExtensions()).hasSize(1);
        assertThat(result.getCustomExtensions()).hasSize(1);
        assertThat(result.getCustomLimit()).isEqualTo(200L);
    }
}
