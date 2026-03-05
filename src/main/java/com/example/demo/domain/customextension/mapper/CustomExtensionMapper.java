package com.example.demo.domain.customextension.mapper;

import com.example.demo.domain.customextension.dto.response.CustomExtensionResponse;
import com.example.demo.domain.customextension.entity.CustomExtensionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * packageName    : com.example.demo.domain.customextension.mapper<br>
 * fileName       : CustomExtensionMapper.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 커스텀 확장자 Entity와 DTO 간 변환을 담당하는 Mapper 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Component
public class CustomExtensionMapper {

    public CustomExtensionResponse toResponse(CustomExtensionEntity entity) {
        return CustomExtensionResponse.builder()
                .id(entity.getId())
                .extension(entity.getExtension())
                .build();
    }

    public List<CustomExtensionResponse> toResponseList(List<CustomExtensionEntity> entities) {
        return entities.stream().map(this::toResponse).toList();
    }
}
