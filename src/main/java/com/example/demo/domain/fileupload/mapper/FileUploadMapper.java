package com.example.demo.domain.fileupload.mapper;

import com.example.demo.domain.fileupload.dto.response.UploadedFileResponse;
import com.example.demo.domain.fileupload.entity.UploadedFileEntity;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.example.demo.domain.fileupload.mapper<br>
 * fileName       : FileUploadMapper.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 업로드 파일 Entity와 DTO 간 변환을 담당하는 Mapper 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@Component
public class FileUploadMapper {

    public UploadedFileResponse toResponse(UploadedFileEntity entity) {
        return UploadedFileResponse.builder()
                .id(entity.getId())
                .originalFileName(entity.getOriginalFileName())
                .storedFileName(entity.getStoredFileName())
                .extension(entity.getExtension())
                .mimeType(entity.getMimeType())
                .fileSize(entity.getFileSize())
                .storagePath(entity.getStoragePath())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
