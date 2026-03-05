package com.example.demo.domain.customextension.service;

import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.customextension.entity.CustomExtensionEntity;
import com.example.demo.domain.customextension.mapper.CustomExtensionMapper;
import com.example.demo.domain.customextension.repository.CustomExtensionRepository;
import com.example.demo.global.common.StatusCode;
import com.example.demo.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * packageName    : com.example.demo.domain.customextension.service<br>
 * fileName       : CustomExtensionService.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 커스텀 확장자 비즈니스 로직을 처리하는 Service 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomExtensionService {
    private static final int MAX_EXTENSION_LENGTH = 20;
    private static final long MAX_CUSTOM_EXTENSION_COUNT = 200L;

    private final CustomExtensionRepository customExtensionRepository;
    private final CustomExtensionMapper customExtensionMapper;

    public List<CustomExtensionResponse> getCustomExtensions() {
        List<CustomExtensionEntity> entities = customExtensionRepository.findAllByOrderByCreatedAtAsc();
        return customExtensionMapper.toResponseList(entities);
    }

    public long getCustomExtensionCount() {
        return customExtensionRepository.count();
    }

    @Transactional
    public CustomExtensionResponse addCustomExtension(String extension) {
        String normalizedExtension = normalizeExtension(extension);

        if (customExtensionRepository.count() >= MAX_CUSTOM_EXTENSION_COUNT) {
            throw new CustomException(StatusCode.CUSTOM_EXTENSION_LIMIT_EXCEEDED);
        }

        if (customExtensionRepository.existsByExtension(normalizedExtension)) {
            throw new CustomException(StatusCode.CUSTOM_EXTENSION_DUPLICATE);
        }

        CustomExtensionEntity saved = customExtensionRepository.save(new CustomExtensionEntity(normalizedExtension));
        return customExtensionMapper.toResponse(saved);
    }

    @Transactional
    public void removeCustomExtension(Long id) {
        CustomExtensionEntity entity = customExtensionRepository.findById(id)
                .orElseThrow(() -> new CustomException(StatusCode.CUSTOM_EXTENSION_NOT_FOUND));
        customExtensionRepository.delete(entity);
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

        if (normalized.length() > MAX_EXTENSION_LENGTH) {
            throw new CustomException(StatusCode.EXTENSION_TOO_LONG);
        }

        return normalized;
    }
}
