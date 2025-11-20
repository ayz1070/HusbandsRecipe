# 데이터 모델 문서  
문서 버전: v1.0 (1차 기능 전용)

> 목적: 남편의 레시피 앱 **1차 기능(MVP)** 에서 사용하는 최소 데이터 구조 정의  
> 대상 기능: 로그인, 레시피 리스트/상세, 커뮤니티(댓글), 좋아요, 광고

---

## 1. 공통 필드 규칙

대부분의 주요 엔티티는 아래 필드를 공통으로 가질 수 있다.

- `id: String`  
- `createdAt: Long`  (epoch milliseconds)  
- `updatedAt: Long`  (epoch milliseconds)  

---

## 2. User (사용자)

### 설명
구글 로그인을 통해 생성되는 사용자 정보.

### 필드
- `id: String` — Firebase UID  
- `displayName: String?`  
- `photoUrl: String?`  
- `email: String?`  
- `createdAt: Long`  
- `updatedAt: Long`  

### 컬렉션 예시
- `users/{userId}`  

---

## 3. Recipe (레시피)

### 설명
홈 리스트·상세 화면에서 보여줄 레시피 카드 정보.

### 필드
- `id: String`  
- `title: String` — 레시피 제목  
- `category: String` — 예: `"요리"`, `""`, `""`  
- `thumbnailUrl: String` — 썸네일 이미지 URL  
- `youtubeUrl: String` — 유튜브 링크  
- `summary: String` — 리스트에 쓰이는 짧은 설명  
- `content: String` — 상세 설명/본문  
- `authorId: String` — 작성자 `User.id`  
- `likeCount: Int`  
- `commentCount: Int`  
- `publishedAt: Long`  
- `createdAt: Long`  
- `updatedAt: Long`  

### 컬렉션 예시
- `recipes/{recipeId}`  

---

## 4. Ingredient (재료)

### 설명
레시피에 사용되는 재료. 별도 컬렉션 없이 `Recipe` 내부에서 사용.

### 필드
- `name: String` — 재료명  
- `quantity: String` — 수량/단위 (예: `"1개"`, `"200g"`)  

### 사용 위치
- `Recipe.ingredients: List<Ingredient>` 형태로 포함  

---

## 5. Comment (댓글)

### 설명
레시피 상세 화면에 표시되는 댓글.

### 필드
- `id: String`  
- `recipeId: String` — 대상 레시피 ID  
- `authorId: String` — 작성자 User ID  
- `content: String` — 댓글 내용  
- `likeCount: Int` — 댓글 좋아요 수  
- `createdAt: Long`  
- `updatedAt: Long`  

### 컬렉션 예시
- 중첩 컬렉션 방식:
  - `recipes/{recipeId}/comments/{commentId}`  

---

## 6. Like (좋아요)

### 설명
레시피 또는 댓글에 대한 좋아요 상태.  
중복 방지를 위해 `(userId, targetType, targetId)` 조합을 유니크하게 관리.

### 필드
- `id: String`  
- `userId: String`  
- `targetType: String` — `"recipe"` 또는 `"comment"`  
- `targetId: String` — `Recipe.id` 또는 `Comment.id`  
- `createdAt: Long`  

### 컬렉션 예시
- `likes/{likeId}`  

> `likeCount` 는 집계 결과를 `Recipe.likeCount`, `Comment.likeCount` 에 업데이트하여 사용.

---

## 7. AdConfig (광고 설정 - 단순 버전)

### 설명
앱에서 사용할 광고 단위 ID 및 노출 여부를 정의하는 간단 설정.

### 필드
- `id: String`  
- `bannerUnitId: String` — 레시피 상세 하단 배너 ID  
- `enabled: Boolean` — 광고 사용 여부  
- `createdAt: Long`  
- `updatedAt: Long`  

### 컬렉션 예시
- `adConfigs/{configId}`  

---

## 8. 요약

1차 기능에 필요한 핵심 모델만 사용:

- **User**: 로그인/작성자 정보  
- **Recipe** + **Ingredient**: 레시피 목록/상세  
- **Comment**: 커뮤니티 댓글  
- **Like**: 좋아요 상태  
- **AdConfig**: 광고 설정  

2차 기능(냉장고, AI 추천)은 이 문서 범위에서 제외한다.