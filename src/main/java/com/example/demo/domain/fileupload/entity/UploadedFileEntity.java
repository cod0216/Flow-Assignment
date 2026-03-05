package com.example.demo.domain.fileupload.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName    : com.example.demo.domain.fileupload.entity<br>
 * fileName       : UploadedFileEntity.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 업로드 파일 메타데이터를 저장하는 엔티티 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@Getter
@Entity
@Table(name = "uploaded_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadedFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_file_name", nullable = false, length = 255)
    private String originalFileName;

    @Column(name = "stored_file_name", nullable = false, length = 255)
    private String storedFileName;

    @Column(name = "extension", nullable = false, length = 20)
    private String extension;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "storage_path", nullable = false, length = 500)
    private String storagePath;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public UploadedFileEntity(
            String originalFileName,
            String storedFileName,
            String extension,
            String mimeType,
            long fileSize,
            String storagePath
    ) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.extension = extension;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
        this.storagePath = storagePath;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
