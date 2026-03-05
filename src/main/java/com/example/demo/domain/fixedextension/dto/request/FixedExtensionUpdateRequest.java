package com.example.demo.domain.fixedextension.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.demo.domain.fixedextension.dto.request<br>
 * fileName       : FixedExtensionUpdateRequest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 고정 확장자 차단 상태 변경 요청 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
@NoArgsConstructor
public class FixedExtensionUpdateRequest {

    @NotNull(message = "차단 여부는 필수입니다.")
    private Boolean blocked;
}
