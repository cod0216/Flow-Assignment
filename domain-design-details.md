# 도메인 설계 상세 (ComplexService 기준)

## 1) fixedextension 도메인

- 구성:
  - `entity/FixedExtensionEntity`
  - `repository/FixedExtensionRepository`
  - `service/FixedExtensionService`
  - `mapper/FixedExtensionMapper`
  - `dto/request/FixedExtensionUpdateRequest`
  - `dto/response/FixedExtensionResponse`
  - `controller/FixedExtensionController`
- 책임:
  - 고정 확장자 목록 조회
  - 체크/해제(`blocked`) 상태 저장
- 의도:
  - 과제 핵심 요구사항인 "고정 확장자 토글 + DB 유지"를 직접 담당
  - 서비스가 하나의 레포지토리만 의존하도록 분리 가능

## 2) customextension 도메인

- 구성:
  - `entity/CustomExtensionEntity`
  - `repository/CustomExtensionRepository`
  - `service/CustomExtensionService`
  - `mapper/CustomExtensionMapper`
  - `dto/request/CustomExtensionCreateRequest`
  - `dto/response/CustomExtensionResponse`
  - `controller/CustomExtensionController`
- 책임:
  - 커스텀 확장자 추가/조회/삭제
  - 길이 20자 제한, 최대 200개 제한, 중복 검증
- 의도:
  - 커스텀은 "추가/삭제" 중심 요구사항이라 행 존재 자체가 차단 의미
  - 요구사항 검증 로직을 서비스에 집중

## 3) extensionpolicy 도메인 (ComplexService 전용)

- 구성:
  - `service/ExtensionPolicyComplexService`
  - `mapper/ExtensionPolicyMapper`
  - `dto/response/ExtensionPolicyResponse`
  - `controller/ExtensionPolicyController`
- 책임:
  - 화면 진입/새로고침용 통합 응답 제공
  - 고정 + 커스텀 데이터를 하나의 응답으로 조합
- 의도:
  - "서비스 1개당 레포지토리 1개" 규칙 유지
  - 두 도메인을 조합하는 복합 유스케이스를 ComplexService에서만 처리

## 4) 계층 분리 원칙

- `Controller`:
  - 요청/응답 처리만 수행
  - 비즈니스 로직 금지
- `Service`:
  - 정규화, 검증, 트랜잭션 등 비즈니스 로직 수행
- `Mapper`:
  - DTO <-> Entity 변환 전담
- 의도:
  - 책임 분리로 테스트/유지보수 용이성 향상
  - 순환 참조 위험 감소

## 5) 의존성 방향

- `controller -> service -> repository`
- `extensionpolicy.controller -> ExtensionPolicyComplexService -> (FixedExtensionService, CustomExtensionService)`
- 서비스 간 양방향 주입 금지

## 6) 네이밍/구조 규칙 반영 사항

- 기존 `FacadeService` 명칭 대신 `ComplexService` 사용
- 도메인별 `controller/service/repository/entity/dto/request/dto/response/mapper` 구조 유지
- 클래스 상단 Javadoc 이력 규칙(`author: cod0216`, 최초 생성/변경 이력) 준수

