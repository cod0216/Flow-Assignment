package com.example.demo.domain.fixedextension.service;

import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import com.example.demo.domain.fixedextension.entity.FixedExtensionEntity;
import com.example.demo.domain.fixedextension.mapper.FixedExtensionMapper;
import com.example.demo.domain.fixedextension.repository.FixedExtensionRepository;
import com.example.demo.global.common.StatusCode;
import com.example.demo.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * packageName    : com.example.demo.domain.fixedextension.service<br>
 * fileName       : FixedExtensionService.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 고정 확장자 비즈니스 로직을 처리하는 Service 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FixedExtensionService {
    private final FixedExtensionRepository fixedExtensionRepository;
    private final FixedExtensionMapper fixedExtensionMapper;

    public List<FixedExtensionResponse> getFixedExtensions() {
        List<FixedExtensionEntity> entities = fixedExtensionRepository.findAllByOrderByExtensionAsc();
        return fixedExtensionMapper.toResponseList(entities);
    }

    @Transactional
    public FixedExtensionResponse updateFixedExtension(String extension, Boolean blocked) {
        String normalizedExtension = normalizeExtension(extension);

        FixedExtensionEntity entity = fixedExtensionRepository.findByExtension(normalizedExtension)
                .orElseThrow(() -> new CustomException(StatusCode.FIXED_EXTENSION_NOT_FOUND));

        entity.changeBlocked(blocked);

        return fixedExtensionMapper.toResponse(entity);
    }

    private String normalizeExtension(String extension) {
        if (extension == null) {
            throw new CustomException(StatusCode.INVALID_EXTENSION_FORMAT);
        }

        String normalized = extension.trim().toLowerCase(Locale.ROOT);
        while (normalized.startsWith(".")) {
            normalized = normalized.substring(1);
        }

        if (normalized.isBlank()) {
            throw new CustomException(StatusCode.INVALID_EXTENSION_FORMAT);
        }

        if (normalized.length() > 20) {
            throw new CustomException(StatusCode.EXTENSION_TOO_LONG);
        }

        return normalized;
    }
}
