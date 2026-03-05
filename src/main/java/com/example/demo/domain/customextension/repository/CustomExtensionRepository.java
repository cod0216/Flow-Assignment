package com.example.demo.domain.customextension.repository;

import com.example.demo.domain.customextension.entity.CustomExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.example.demo.domain.customextension.repository<br>
 * fileName       : CustomExtensionRepository.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 커스텀 확장자 영속 처리를 담당하는 Repository 인터페이스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
public interface CustomExtensionRepository extends JpaRepository<CustomExtensionEntity, Long> {
    boolean existsByExtension(String extension);
    List<CustomExtensionEntity> findAllByOrderByCreatedAtAsc();
}
