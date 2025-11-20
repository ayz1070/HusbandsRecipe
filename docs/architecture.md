# 아키텍처 및 기술 스택
문서 버전: v1.0

---

## 1. 개요

- 프론트엔드: Android (Kotlin, Jetpack Compose)
- 백엔드: Firebase 기반 (Auth / Firestore / Storage 등 활용)
- 아키텍처: MVVM + 가벼운 Clean Layering
    - Domain / Data / Presentation 레이어만 명확히 구분
    - Use case와 Repository 인터페이스 중심으로 경계 최소 유지

본 문서는 앱 내 코드 구조 및 사용 기술 스택에 대한 기준을 정의한다.

---

## 2. 기술 스택 요약

- Language: **Kotlin**
- UI: **Jetpack Compose + Material 3**
- Architecture Pattern: **MVVM + lightweight Clean Architecture**
- DI: **Hilt**
- Async: **Kotlin Coroutines + Flow**
- Networking: **Retrofit + OkHttp**
    - JSON: `kotlinx.serialization` 또는 `Moshi` 중 하나 선택 후 일관되게 사용
- Backend: **Firebase**
    - Firebase Authentication (Google 로그인)
    - Cloud Firestore (레시피, 커뮤니티 데이터 저장)
    - Firebase Storage (이미지, 향후 냉장고 사진 등)
- Persistence:
    - **DataStore**: 필터, 설정, 간단한 로컬 상태
    - **Room (선택 사항)**: 오프라인 캐시, 복잡한 로컬 데이터가 필요할 때만 도입
- Logging: **Timber**
    - 로그 메시지 내 이모지 사용 금지
- Testing:
    - 단위 테스트: **JUnit**
    - Mocking: **MockK**
    - Flow 테스트: **Turbine**
    - UI 테스트: **Compose UI Test**

---

## 3. 레이어 구조

### 3.1 레이어 개요

- **Presentation 레이어**
    - Jetpack Compose UI (Screen, Composable)
    - ViewModel (AndroidX ViewModel + StateFlow 사용)
    - UIState / UIEvent 정의 (sealed class 등)

- **Domain 레이어**
    - 비즈니스 로직의 중심
    - `UseCase` 클래스들 (단일 책임, 한 기능당 하나의 UseCase 지향)
    - `Repository` 인터페이스 정의
    - Domain `Model` (UI/네트워크/DB와 분리된 순수 Kotlin 모델)

- **Data 레이어**
    - Repository 구현체 (`RepositoryImpl`)
    - Remote Data Source
        - Firebase SDK (Auth / Firestore / Storage)
        - Retrofit API (외부 API 또는 추후 자체 서버)
    - Local Data Source
        - DataStore
        - Room(선택)

### 3.2 의존성 방향

- Presentation → Domain → Data (단방향)
- Presentation 레이어에서 Data 레이어에 직접 접근 금지
- Domain 레이어는 Android 프레임워크에 의존하지 않도록 유지
- Data 레이어는 Domain 레이어의 Repository 인터페이스를 구현

---

## 4. DI(Hilt) 규칙

- Application 클래스에 `@HiltAndroidApp` 사용
- 각 레이어별 Hilt Module 구성:
    - Domain: UseCase 제공 Module
    - Data: RepositoryImpl / DataSource / Retrofit / Firebase / DataStore / Room 제공 Module
    - Presentation: ViewModel은 `@HiltViewModel` 사용
- DI는 생성자 주입을 우선 사용

---

## 5. 비동기 및 네트워크 규칙

- 비동기 처리:
    - 모든 비동기 로직은 `suspend fun` 또는 `Flow` 기반으로 구현
    - ViewModel에서 `viewModelScope` + `launch` 사용
- Flow 사용:
    - 데이터 스트림(레시피 리스트, 커뮤니티 글 목록, 설정 값 등)에 Flow 활용
    - 테스트에서 Turbine으로 Flow 검증
- Retrofit:
    - 공통 OkHttpClient 사용 (로깅 인터셉터, 타임아웃 기본 설정)
    - 응답 래핑은 일관된 방식 유지 (예: Result 타입, sealed class 등은 별도 정의 문서에서 상세화 가능)

---

## 6. 저장소(Persistence) 규칙

- DataStore:
    - 사용자 설정(필터, 보기 모드 등)에만 사용
    - Key-Value 기반으로 간단하게 유지
- Room (사용 시):
    - 오프라인 캐시 또는 검색/정렬이 복잡할 때 도입
    - Entity는 Domain Model과 1:1이 아닐 수 있으며, Mapper를 통해 변환

---

## 7. 로깅 및 테스트 기준

- 로깅:
    - Timber 사용
    - Log Tag는 클래스명 또는 의미 있는 모듈명 사용
    - 이모지 사용 금지

- 테스트:
    - 비즈니스 로직(Unit Test): JUnit + MockK
    - Flow/비동기 로직: JUnit + Turbine
    - ViewModel 테스트: Repository/UseCase를 MockK로 대체
    - UI 테스트: Compose UI Test를 통해 주요 화면 렌더링 및 상호작용 검증

---