package com.example.demo.domain.fileupload.service;

import com.example.demo.domain.customextension.service.CustomExtensionService;
import com.example.demo.domain.fileupload.dto.response.UploadedFileResponse;
import com.example.demo.domain.fixedextension.service.FixedExtensionService;
import com.example.demo.global.common.StatusCode;
import com.example.demo.global.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * packageName    : com.example.demo.domain.fileupload.service<br>
 * fileName       : FileUploadComplexService.java<br>
 * author         : cod0216 <br>
 * date           : 26.03.06<br>
 * description    : 파일 업로드 검증과 저장을 조합 처리하는 ComplexService 클래스입니다. <br>
 * ===========================================================<br>
 * DATE              AUTHOR             NOTE<br>
 * -----------------------------------------------------------<br>
 * 26.03.06          cod0216            최초 생성<br>
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileUploadComplexService {
    private static final Set<String> DANGEROUS_MIME_TYPES = Set.of(
            "application/x-msdownload",
            "application/x-dosexec",
            "application/x-msdos-program",
            "application/x-sh",
            "text/x-shellscript"
    );

    private final UploadedFileService uploadedFileService;
    private final FixedExtensionService fixedExtensionService;
    private final CustomExtensionService customExtensionService;

    @Transactional
    public UploadedFileResponse uploadFile(MultipartFile file) {
        validateFileNotEmpty(file);

        String extension = extractExtension(file.getOriginalFilename());
        validateBlockedExtension(extension);

        String detectedMimeType = detectMimeType(file);
        validateMimeType(detectedMimeType);
        validateMagicBytes(file);

        return uploadedFileService.storeFile(file, extension, detectedMimeType);
    }

    private void validateFileNotEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CustomException(StatusCode.FILE_EMPTY);
        }
    }

    private String extractExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new CustomException(StatusCode.INVALID_EXTENSION_FORMAT);
        }

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == originalFileName.length() - 1) {
            throw new CustomException(StatusCode.INVALID_EXTENSION_FORMAT);
        }

        String extension = originalFileName.substring(dotIndex + 1)
                .trim()
                .toLowerCase(Locale.ROOT);

        if (extension.isBlank()) {
            throw new CustomException(StatusCode.INVALID_EXTENSION_FORMAT);
        }

        if (extension.length() > 20) {
            throw new CustomException(StatusCode.EXTENSION_TOO_LONG);
        }

        return extension;
    }

    private void validateBlockedExtension(String extension) {
        boolean fixedBlocked = fixedExtensionService.getFixedExtensions().stream()
                .filter(fixed -> fixed.getExtension().equals(extension))
                .anyMatch(fixed -> fixed.isBlocked());

        boolean customBlocked = customExtensionService.getCustomExtensions().stream()
                .anyMatch(custom -> custom.getExtension().equals(extension));

        if (fixedBlocked || customBlocked) {
            throw new CustomException(StatusCode.FILE_EXTENSION_BLOCKED);
        }
    }

    private String detectMimeType(MultipartFile file) {
        String declaredMimeType = Optional.ofNullable(file.getContentType())
                .orElse("application/octet-stream")
                .toLowerCase(Locale.ROOT);

        try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
            String guessedMimeType = URLConnection.guessContentTypeFromStream(inputStream);

            if (guessedMimeType != null && !guessedMimeType.isBlank()) {
                return guessedMimeType.toLowerCase(Locale.ROOT);
            }

            return declaredMimeType;
        } catch (IOException exception) {
            throw new CustomException(StatusCode.FILE_STORE_FAILED, exception.getMessage());
        }
    }

    private void validateMimeType(String mimeType) {
        if (DANGEROUS_MIME_TYPES.contains(mimeType)) {
            throw new CustomException(StatusCode.FILE_MIME_BLOCKED);
        }
    }

    private void validateMagicBytes(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            byte[] header = inputStream.readNBytes(64);

            if (isPortableExecutable(header) || isElf(header) || isMachO(header) || isShellScript(header)) {
                throw new CustomException(StatusCode.FILE_SIGNATURE_BLOCKED);
            }
        } catch (IOException exception) {
            throw new CustomException(StatusCode.FILE_STORE_FAILED, exception.getMessage());
        }
    }

    private boolean isPortableExecutable(byte[] header) {
        return header.length >= 2 && header[0] == 'M' && header[1] == 'Z';
    }

    private boolean isElf(byte[] header) {
        return header.length >= 4
                && header[0] == 0x7F
                && header[1] == 'E'
                && header[2] == 'L'
                && header[3] == 'F';
    }

    private boolean isMachO(byte[] header) {
        if (header.length < 4) {
            return false;
        }

        int magic = ((header[0] & 0xFF) << 24)
                | ((header[1] & 0xFF) << 16)
                | ((header[2] & 0xFF) << 8)
                | (header[3] & 0xFF);

        return magic == 0xFEEDFACE
                || magic == 0xFEEDFACF
                || magic == 0xCEFAEDFE
                || magic == 0xCFFAEDFE;
    }

    private boolean isShellScript(byte[] header) {
        String head = new String(header, StandardCharsets.UTF_8).toLowerCase(Locale.ROOT);
        return head.startsWith("#!/") && (
                head.contains("sh")
                        || head.contains("bash")
                        || head.contains("zsh")
                        || head.contains("ksh")
                        || head.contains("powershell")
                        || head.contains("cmd")
        );
    }
}
