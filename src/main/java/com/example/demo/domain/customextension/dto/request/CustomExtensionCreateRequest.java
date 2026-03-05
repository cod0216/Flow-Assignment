package com.example.demo.domain.customextension.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.demo.domain.customextension.dto.request<br>
 * fileName       : CustomExtensionCreateRequest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.05<br>
 * description    : 커스텀 확장자 추가 요청 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.05          cod0216            최초 생성<br>
 */
@Getter
@NoArgsConstructor
public class CustomExtensionCreateRequest {

    @NotBlank(message = "확장자 입력은 필수입니다.")
    @Size(max = 20, message = "확장자는 최대 20자까지 입력할 수 있습니다.")
    private String extension;
}
