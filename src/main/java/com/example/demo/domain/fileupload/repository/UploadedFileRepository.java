package com.example.demo.domain.fileupload.repository;

import com.example.demo.domain.fileupload.entity.UploadedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.example.demo.domain.fileupload.repository<br>
 * fileName       : UploadedFileRepository.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 업로드 파일 엔티티 영속 처리를 담당하는 Repository 인터페이스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
public interface UploadedFileRepository extends JpaRepository<UploadedFileEntity, Long> {
}
