package com.example.demo.domain.fixedextension.mapper;

import com.example.demo.domain.fixedextension.dto.response.FixedExtensionResponse;
import com.example.demo.domain.fixedextension.entity.FixedExtensionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * packageName    : com.example.demo.domain.fixedextension.mapper<br>
 * fileName       : FixedExtensionMapper.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 고정 확장자 Entity와 DTO 간 변환을 담당하는 Mapper 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Component
public class FixedExtensionMapper {

    public FixedExtensionResponse toResponse(FixedExtensionEntity entity) {
        return FixedExtensionResponse.builder()
                .extension(entity.getExtension())
                .blocked(entity.isBlocked())
                .build();
    }

    public List<FixedExtensionResponse> toResponseList(List<FixedExtensionEntity> entities) {
        return entities.stream().map(this::toResponse).toList();
    }
}
