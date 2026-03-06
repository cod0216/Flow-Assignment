package com.example.demo.domain.fileupload.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : com.example.demo.domain.fileupload.dto.request<br>
 * fileName       : FileUploadRequest.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 파일 업로드 요청 DTO 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 * 26.03.06          cod0216            Multipart 바인딩을 위한 setter 추가<br>
 */
@Getter
@Setter
@NoArgsConstructor
public class FileUploadRequest {

    @NotNull(message = "업로드 파일은 필수입니다.")
    private MultipartFile file;
}
