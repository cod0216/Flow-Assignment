package com.example.demo.domain.fileupload.service;

import com.example.demo.domain.fileupload.dto.response.UploadedFileResponse;
import com.example.demo.domain.fileupload.entity.UploadedFileEntity;
import com.example.demo.domain.fileupload.mapper.FileUploadMapper;
import com.example.demo.domain.fileupload.repository.UploadedFileRepository;
import com.example.demo.global.common.StatusCode;
import com.example.demo.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * packageName    : com.example.demo.domain.fileupload.service<br>
 * fileName       : UploadedFileService.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 업로드 파일 저장과 메타데이터 영속화를 담당하는 Service 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadedFileService {
    private final UploadedFileRepository uploadedFileRepository;
    private final FileUploadMapper fileUploadMapper;

    @Value("${app.upload.dir:${user.home}/flow-assignment/uploads}")
    private String uploadDir;

    @Transactional
    public UploadedFileResponse storeFile(MultipartFile file, String extension, String mimeType) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String storedFileName = UUID.randomUUID() + "." + extension;
            Path targetPath = uploadPath.resolve(storedFileName).normalize();

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            UploadedFileEntity saved = uploadedFileRepository.save(new UploadedFileEntity(
                    file.getOriginalFilename(),
                    storedFileName,
                    extension,
                    mimeType,
                    file.getSize(),
                    targetPath.toString()
            ));

            return fileUploadMapper.toResponse(saved);
        } catch (IOException exception) {
            throw new CustomException(StatusCode.FILE_STORE_FAILED, exception.getMessage());
        }
    }
}
