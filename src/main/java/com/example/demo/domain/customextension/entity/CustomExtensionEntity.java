package com.example.demo.domain.customextension.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName    : com.example.demo.domain.customextension.entity<br>
 * fileName       : CustomExtensionEntity.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 커스텀 확장자 정보를 저장하는 엔티티 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
@Entity
@Table(name = "custom_extension", uniqueConstraints = {
        @UniqueConstraint(name = "uq_custom_extension", columnNames = "extension")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomExtensionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "extension", nullable = false, length = 20)
    private String extension;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public CustomExtensionEntity(String extension) {
        this.extension = extension;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
