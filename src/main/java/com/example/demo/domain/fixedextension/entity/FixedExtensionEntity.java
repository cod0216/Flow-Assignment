package com.example.demo.domain.fixedextension.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName    : com.example.demo.domain.fixedextension.entity<br>
 * fileName       : FixedExtensionEntity.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 고정 확장자 차단 상태를 저장하는 엔티티 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
@Entity
@Table(name = "fixed_extension", uniqueConstraints = {
        @UniqueConstraint(name = "uq_fixed_extension", columnNames = "extension")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FixedExtensionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "extension", nullable = false, length = 20)
    private String extension;

    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public FixedExtensionEntity(String extension, boolean blocked) {
        this.extension = extension;
        this.blocked = blocked;
    }

    public void changeBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
