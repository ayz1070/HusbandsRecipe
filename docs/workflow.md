# 작업 흐름 (Workflows)
문서 버전: v1.0

> 목적: 초기 MVP(1차 기능) 구현을 위한 상세 작업 리스트 정의  
> 구현 순서: **레시피 → 로그인 → 광고**  
> AI 자동 실행 및 체크 기반 관리 가능하도록 체크박스 포맷 사용

---

# 1. 프로젝트 기본 세팅

## 1.1 Android 프로젝트 초기 구성
- [x] Android 프로젝트 생성 (Kotlin, Compose)
- [x] Material3 적용
- [x] Theme/Color/Typos 기본 설정
- [x] Navigation Compose 세팅
- [x] Hilt DI 기본 설정
- [x] Timber 로그 설정
- [x] 모듈 구조 가볍게 구분 (data / domain / ui)
- [x] 패키지 구조 생성 (recipe, auth, comment, common 등)

## 1.2 Firebase 연동
- [x] Firebase 프로젝트 생성
- [x] Android SHA 키 등록
- [x] Firebase SDK 추가
- [x] Firestore 연동
- [x] Firebase Auth 연동

---

# 2. 레시피(Recipe) 기능 개발
> **가장 먼저 구현해야 하는 핵심 기능**

## 2.1 Data Layer
- [x] Recipe 데이터 모델 정의
- [x] Ingredient 모델 포함
- [x] Recipe Firestore 구조 정의
- [x] Repository 인터페이스 생성
- [x] Firestore 기반 Repository 구현
- [x] DTO 변환/매핑 로직 작성

## 2.2 UseCase Layer
- [x] FetchRecipeListUseCase
- [x] FetchRecipeDetailUseCase
- [x] UpdateLikeCountUseCase
- [x] UpdateCommentCountUseCase

## 2.3 UI – 레시피 리스트 화면
- [x] RecipeList 화면 레이아웃 구성
- [x] TopAppBar + TabRow(추천/최신)
- [x] RecipeCard 컴포저블 작성
- [x] 리스트 LazyColumn 연결
- [x] ViewModel에서 Recipe 리스트 로드
- [x] 로딩 / 오류 상태 처리
- [x] 카드 클릭 시 상세 페이지 이동

## 2.4 UI – 레시피 상세 화면
- [x] Header 이미지 + Gradient Overlay
- [x] 제목/카테고리/설명/작성일 표시
- [x] 유튜브 링크 연결 (Chrome CustomTab 등)
- [x] 레시피 본문(요약 + 재료 + 단계)
- [x] 좋아요 버튼 + 좋아요 카운트 연동
- [x] 댓글 수 표시
- [x] 상세 페이지 ViewModel 구성

## 2.5 댓글(Comment)
- [x] Comment 데이터 모델 작성
- [x] Firestore 구조 생성
- [x] Repository 작성
- [x] FetchCommentListUseCase
- [x] AddCommentUseCase
- [x] CommentList UI 작성
- [x] CommentItem UI 작성
- [x] 댓글 입력창(BottomInput)
- [x] 댓글 CRUD 연결
- [x] 레시피에 댓글 수 반영

## 2.6 좋아요(Like)
- [x] Like 데이터 모델 정의
- [x] Firestore likes 컬렉션 구성
- [x] Repository 작성
- [x] ToggleLikeUseCase
- [x] 레시피 좋아요 버튼 작동
- [x] 댓글 좋아요 버튼 작동
- [x] 좋아요 수 실시간 반영

---

# 3. 로그인(Login) 기능 개발
> 레시피 기능 이후 구현하는 2순위 기능

## 3.1 Firebase Authentication
- [x] Firebase Auth 라이브러리 추가
- [x] Google Sign-In 설정 (google-services.json)
- [x] AuthRepository 인터페이스 정의
- [x] FirebaseAuth 구현체 작성
- [x] SignInUseCase / SignOutUseCase

## 3.2 User Data Management
- [x] User 데이터 모델 정의
- [x] Firestore Users 컬렉션 연동
- [x] 로그인 성공 시 User 정보 저장/업데이트
- [x] GetCurrentUserUseCase

## 3.3 Login UI
- [x] LoginScreen 컴포저블 작성
- [x] 구글 로그인 버튼 UI
- [x] 로그인 성공 시 메인 화면 이동 처리
- [x] ViewModel 상태 관리 (Loading, Success, Error)가능하도록 처리
- [ ] 프로필 영역(아이콘/메뉴) 추가

---

# 4. 광고(Ads) 기능 개발
> MVP 마지막 구현 기능

## 4.1 AdMob 기본 설정
- [x] Google AdMob 프로젝트 생성
- [x] 앱 ID/광고단위 등록
- [x] AndroidManifest에 AdMob 설정 추가
- [x] 테스트 광고 먼저 활성화

## 4.2 Banner 광고
- [x] Banner Ad 컴포저블 작성
- [x] 레시피 상세 화면 하단에 배너 삽입
- [x] 네트워크 오류/로딩 처리
- [x] AdConfig 모델 적용 (광고 on/off 가능)

## 4.3 광고 안정성 확인
- [x] 광고 클릭 시 정상 동작 확인
- [ ] 화면 회전/다크모드에서 재렌더링 확인
- [ ] 배너 노출률 확인

---

# 5. 품질 검증 (QA)

## 5.1 기능 QA
- [ ] 레시피 리스트 로딩 테스트
- [ ] 상세 화면 정상 렌더링
- [ ] 댓글 작성 → Firestore 반영 확인
- [ ] 좋아요 동작 확인(중복 방지)
- [ ] 로그인 후 사용자 정보 정상 저장
- [ ] 광고 배너 정상 노출

## 5.2 UX/UI QA
- [ ] Material3 스타일 준수 확인
- [ ] 다크모드에서 UI 손상 여부 확인
- [ ] 텍스트 크기/정렬/여백 검증
- [ ] 이미지 로딩 상태 검증

---

# 6. 배포 준비

- [ ] 패키지명/버전코드/버전네임 설정
- [ ] Proguard / R8 설정
- [ ] 릴리즈 키스토어 생성
- [ ] Play Console 업로드 테스트
- [ ] Firebase 콘솔 로그/사용자 분석 점검

---

# 7. 전체 구현 순서 요약

1. **레시피 (Recipe)**
2. **댓글(Comment)**
3. **좋아요(Like)**
4. **로그인(Login)**
5. **광고(Ads)**

---

# 8. 완료 체크 예시 (AI 사용 시)

- [ ] 레시피 리스트 완료
- [ ] 상세 화면 완료
- [ ] 댓글 기능 완료
- [ ] 좋아요 기능 완료
- [ ] 로그인 완료
- [ ] 광고 노출 완료
- [ ] 최종 빌드 성공

---

이 문서는 **Codex가 직접 해석하기 좋은 구조**로 작성되어 있어서  
작업 단위 자동 분할, 체크 기반 앱 생성, 코드 자동 생성 등에 바로 사용할 수 있어.

원하면 **각 Task별 AI 프롬프트 템플릿**까지도 만들어줄 수 있어!