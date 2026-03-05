package com.example.demo.domain.fixedextension.repository;

import com.example.demo.domain.fixedextension.entity.FixedExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.example.demo.domain.fixedextension.repository<br>
 * fileName       : FixedExtensionRepository.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 고정 확장자 엔티티 영속 처리를 담당하는 Repository 인터페이스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
public interface FixedExtensionRepository extends JpaRepository<FixedExtensionEntity, Long> {
    Optional<FixedExtensionEntity> findByExtension(String extension);
    List<FixedExtensionEntity> findAllByOrderByExtensionAsc();
}
