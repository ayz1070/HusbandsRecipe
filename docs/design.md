# 디자인 요구사항  
문서 버전: v1.1  
주요 변경: Primary 색상을 블랙(#000000)으로 변경  
준수 기준: Material Design 3 (https://m3.material.io)

---

## 1. 디자인 방향 (Design Philosophy)

- **심플·직관성 중심**: 레시피 앱 특성상 콘텐츠 몰입을 위해 여백·가독성 강조
- **Material 3 컴포넌트 중심 설계**
  - TopAppBar, Card, ElevatedCard, FloatingActionButton, NavigationBar 사용
- **사진 중심 레이아웃**: 제공 이미지처럼 큰 이미지가 화면 상단을 차지
- **콘텐츠 우선 흐름**: 이미지 → 제목 → 설명 → 액션 버튼 → 댓글 순 구조
- **부드러운 라운드·모노톤 컬러·고대비 메인 글자 스타일** 적용
- **아이콘 최소화**, 의미 명확한 액션만 배치 (좋아요, 댓글, 공유)
- **색상 중앙관리**: Primary/Secondary/Surface는 단일 파일에서 관리, 변경 시 앱 전체 반영

---

## 2. 컬러 시스템 (Color System)

### Primary  
- **Primary**: `#000000` (블랙)  
- **OnPrimary**: `#FFFFFF`  

### Secondary
- **Secondary**: `#6B7280` (Gray 500)  
- **OnSecondary**: `#FFFFFF`  

### Surface / Background  
- **Surface**: `#FFFFFF`  
- **SurfaceVariant**: `#F3F4F6`  
- **Background**: `#FAFAFA`  

### Error  
- Material 3 기본 Error 팔레트 사용

---

## 3. 타이포그래피 (Typography)

- **HeadlineMedium**: 레시피 제목  
- **BodyMedium**: 상세 설명, 댓글 본문  
- **LabelLarge**: 액션 버튼  
- **BodySmall**: 날짜/조회수/메타 정보  

가독성을 위해 모바일 기준 +1sp 적용 가능  

---

## 4. 컴포넌트 요구사항

### 4-1. TopAppBar
- 좌측 Back 버튼, 우측 Home 버튼  
- 이미지 위 구간에서는 투명 또는 반투명(black alpha 0.7)  
- 스크롤 시 Surface 색으로 전환  

### 4-2. 이미지 영역 (Recipe Header Image)
- Full width, 16:9 비율  
- Material 3 Medium Shape  
- 텍스트 가독성을 위한 **Dark Gradient Overlay**  

### 4-3. 레시피 정보 (Card / Column)
1. **카테고리 칩 (AssistChip)**  
2. **제목**  
   - Bold, 최대 2줄  
3. **서브텍스트 (작성일/설명)**  
   - Gray 600  
4. **본문 설명(BodyMedium)**  
5. **Primary Action (FilledButton)**  
   - 텍스트: “레시피 보기 / 신청하기”  
   - Background = **Primary = #000000**  
   - Text = OnPrimary = #FFFFFF  

### 4-4. 댓글 영역 (BottomSheet)
- Surface: White  
- Radius: 24dp  
- Elevation: 2  
- 구성 요소: 제목, 프로필, 작성자명, 날짜, 댓글 텍스트, 수정/삭제 버튼  

### 4-5. Interaction 버튼
- Like / Comment / Share  
- IconButton + LabelSmall  
- 활성 아이콘 = **Primary(#000000)**  
- 비활성 = Gray 400  

### 4-6. NavigationBar
- Background = Surface  
- Active icon = Primary(#000000)  
- Inactive icon = Gray 400  

---

## 5. 페이지별 레이아웃 상세

### 5-1. 메인 화면 (추천)
1. TabRow (추천/최신)  
2. ElevatedCard  
3. 이벤트 카드  
4. FAB(그리드 아이콘) — Primary 색상(#000000)

### 5-2. 레시피 상세 화면
1. 상단 이미지  
2. 레시피 정보 섹션  
3. 본문 설명  
4. Primary Action Button  
5. Interaction Bar  
6. 댓글 입력창 (TextField + Send IconButton)  

### 5-3. 댓글 화면
- 반투명 상단 오버레이  
- BottomSheet 스타일 카드  
- 댓글 리스트 + 입력창 구성  

---

## 6. 모션 / 애니메이션

- Material 3 기본 모션 적용  
- Card 클릭: shared axis transition  
- BottomSheet: standard modal transition  
- FAB: scale + fade  

---

## 7. 아이콘 가이드

Material Symbols 사용:

- Back: `arrow_back`  
- Home: `home`  
- Like: `favorite` / `favorite_border`  
- Comment: `chat_bubble`  
- Share: `share`  
- Menu/Grid: `dashboard`  

Active = Primary(#000000), Inactive = Gray400  

---

## 8. 컴포저블 네이밍 규칙

- `RecipeHeaderImage()`  
- `RecipeInfoSection()`  
- `RecipeActionButton()`  
- `InteractionBar()`  
- `CommentList()`  
- `CommentItem()`  
- `BottomCommentInput()`  
- `HomeRecommendationCard()`  

---

## 9. 전체 스타일 요약

- Primary 색상 = 블랙(#000000)  
- Material 3 컴포넌트 중심  
- 사진 중심 · 텍스트 강조형 구조  
- BottomSheet 기반 댓글 구조  
- 직관적이고 가벼운 인터랙션 유지  

---