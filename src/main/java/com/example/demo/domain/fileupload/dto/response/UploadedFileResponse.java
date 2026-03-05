package com.example.demo.domain.fileupload.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * packageName    : com.example.demo.domain.fileupload.dto.response<br>
 * fileName       : UploadedFileResponse.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 업로드 파일 응답 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@Getter
@Builder
public class UploadedFileResponse {
    private Long id;
    private String originalFileName;
    private String storedFileName;
    private String extension;
    private String mimeType;
    private long fileSize;
    private String storagePath;
    private LocalDateTime createdAt;
}
