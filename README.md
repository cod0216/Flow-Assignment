# Flow-Assignment

파일 업로드 차단 확장자 정책 관리 과제입니다.

## 1) 과제 목표 해석
이 과제의 핵심은 "확장자 정책을 저장/관리"하는 CRUD 자체보다,
요구사항 해석과 보안/예외/설계 판단 근거를 보여주는 것이라고 판단했습니다.

- 고정 확장자 차단 상태를 관리하고 영속화한다.
- 커스텀 확장자를 추가/삭제하고 영속화한다.
- 확장자 길이(최대 20자), 개수(최대 200개) 제한을 지킨다.
- 새로고침/재조회 시 정책이 유지된다.

## 2) 구현 범위

### 필수 요구사항 기준 구현
- 고정 확장자 조회/수정
- 커스텀 확장자 조회/추가/삭제
- 정책 통합 조회 API
- 서버 검증
  - 확장자 형식 검증
  - 확장자 최대 길이(20)
  - 커스텀 확장자 최대 개수(200)
  - 커스텀 확장자 중복 방지

### 추가 구현
- 실제 파일 업로드 API 추가
- 확장자 정책(고정/커스텀) 기반 업로드 차단
- MIME 타입 차단
- 매직바이트(시그니처) 차단
  - PE(MZ), ELF, Mach-O, Shell script 시그니처

## 3) 설계 시 고려한 점과 의도

### 3-1. 입력 정규화
- `sh`, `.sh`, ` SH ` 같은 입력을 동일하게 다루기 위해 trim/lowercase/선행 점 제거를 적용했습니다.

### 3-2. 중복/제한 처리
- 커스텀 확장자 중복 등록 방지
- 커스텀 확장자 200개 제한
- 확장자 20자 제한

### 3-3. 레이어 분리
- Controller에는 비즈니스 로직을 두지 않았습니다.
- 단일 도메인 로직은 Domain Service, 다중 도메인 조합은 ComplexService로 분리했습니다.
- 순환참조와 책임 비대화를 방지하고 변경 범위를 예측 가능하게 만들기 위함입니다.

### 3-4. 에러/응답 표준화
- 전역 응답 DTO + 상태코드(enum) + 글로벌 예외 처리기를 적용했습니다.
- 이를 통해 API 소비자가 일관된 방식으로 성공/실패를 처리하도록 하기 위함입니다.

### 3-5. 보안 검증 다층화
- 확장자만 보면 우회 가능(`file.exe.jpg`)하므로 MIME/매직바이트 검증을 함께 적용했습니다.

## 4) 데이터 모델링

- `fixed_extension`
  - 고정 확장자 목록과 차단 여부(`blocked`) 저장
  - 고정 정책을 체크박스 토글로 관리할 수 있도록 설계

- `custom_extension`
  - 사용자 추가 차단 확장자 저장
  - extension unique 제약으로 중복 방지

- `uploaded_file` (추가 구현)
  - 업로드된 파일 메타정보(원본명/저장명/확장자/MIME/경로/크기) 저장

### 4-1) ASCII ERD
```text
+------------------------------+
| fixed_extension              |
+------------------------------+
| PK id BIGINT                 |
| UQ extension VARCHAR(20)     |
| blocked BOOLEAN              |
| created_at DATETIME          |
| updated_at DATETIME          |
+------------------------------+

+------------------------------+
| custom_extension             |
+------------------------------+
| PK id BIGINT                 |
| UQ extension VARCHAR(20)     |
| created_at DATETIME          |
+------------------------------+

+------------------------------+
| uploaded_file                |
+------------------------------+
| PK id BIGINT                 |
| original_file_name VARCHAR   |
| stored_file_name VARCHAR     |
| extension VARCHAR(20)        |
| mime_type VARCHAR(100)       |
| file_size BIGINT             |
| storage_path VARCHAR(500)    |
| created_at DATETIME          |
+------------------------------+

논리 관계(서비스 레벨):
fixed_extension ----\
                     >---- (업로드 차단 정책 판단) ---- uploaded_file 저장
custom_extension ---/
```

## 5) API 요약

- `GET /api/v1/fixed-extensions`
- `PUT /api/v1/fixed-extensions/{extension}`
- `GET /api/v1/custom-extensions`
- `POST /api/v1/custom-extensions`
- `DELETE /api/v1/custom-extensions/{id}`
- `GET /api/v1/extensions/policy`
- `POST /api/v1/files/upload`

## 6) 기술 스택
- Java 17
- Spring Boot 4.0.3
- Spring Web MVC, Validation, Spring Data JPA
- MySQL
- Gradle
