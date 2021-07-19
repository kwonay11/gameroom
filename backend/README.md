# Dependencies
[Back-end]
MySQL
Java 8+
Gradle 6.7+
Eclipse or IntelliJ

[Front-end]
Node 14+
VS Studio

# mysql 접근
url: jdbc:mysql://localhost:3306
username: root
password: 1234

# 데이터베이스 생성
```sql
create database IF NOT EXISTS `ssafy_web_db` collate utf8mb4_general_ci;
```

# Front-end Setup
```bash
cd src/main/resources 
npm install
```

# Front-end Webpack Dev. 모드 실행
```bash
cd src/main/resources 
npm run serve
```

# Front-end 빌드
```bash
cd src/main/resources 
npm run build
```

# Back-end + Front-end 전체 빌드 (프로젝트 루트 디렉토리에서 수행)
```bash
gradle clean build
```


# 프로젝트 디렉토리 구조 설명 (/src 하위 디렉토리 및 파일)
[Back-end]
```bash
.
└── main
    ├── generated
    ├── java
    │   └── com
    │       └── ssafy
    │           ├── GroupCallApplication.java
    │           ├── api  /* REST API 요청관련 컨트롤러, 서비스, 요청/응답 모델 정의*/
    │           │   ├── controller
    │           │   │   ├── AuthController.java
    │           │   │   └── UserController.java
    │           │   ├── request
    │           │   │   ├── UserLoginPostReq.java
    │           │   │   └── UserRegisterPostReq.java
    │           │   ├── response
    │           │   │   ├── UserLoginPostRes.java
    │           │   │   └── UserRes.java
    │           │   └── service
    │           │       ├── UserService.java
    │           │       └── UserServiceImpl.java
    │           ├── common /* 공용 유틸, 응답 모델, 인증, 예외처리 관련 정의*/
    │           │   ├── auth
    │           │   │   ├── JwtAuthenticationFilter.java
    │           │   │   ├── SsafyUserDetailService.java
    │           │   │   └── SsafyUserDetails.java
    │           │   ├── exception
    │           │   │   └── handler
    │           │   │       └── NotFoundHandler.java
    │           │   ├── model
    │           │   │   └── response
    │           │   │       └── BaseResponseBody.java
    │           │   └── util
    │           │       ├── JwtTokenUtil.java
    │           │       └── ResponseBodyWriteUtil.java
    │           ├── config /* WebMvc 및 JPA, Security, Swagger 등의 추가 플러그인 설정 정의*/
    │           │   ├── JpaConfig.java
    │           │   ├── SecurityConfig.java
    │           │   ├── SwaggerConfig.java
    │           │   └── WebMvcConfig.java
    │           └── db /* 디비에 저장될 모델 정의 및 쿼리 구현 */
    │               ├── entity
    │               │   ├── BaseEntity.java
    │               │   └── User.java
    │               └── repository
    │                   ├── UserRepository.java
    │                   └── UserRepositorySupport.java
    └── resources
        ├── README.md
        ├── application.properties /* 웹 리소스(서버 host/port, 디비 host/port/계정/패스워드) 관련 설정 정의 */
        ├── babel.config.js
        ├── dist
        ├── package-lock.json
        ├── package.json
        ├── public
```

[Front-end]
```bash
.
└── main
    ├── generated
    ├── java
    └── resources
        ├── README.md
        ├── application.properties
        ├── babel.config.js
        ├── dist
        │   ├── css
        │   │   ├── app.22fde46a.css
        │   │   └── chunk-vendors.bfd3c584.css
        │   ├── favicon.ico
        │   ├── fonts
        │   │   ├── element-icons.abe71f7d.ttf
        │   │   └── element-icons.d9491be2.woff
        │   ├── img
        │   │   └── ssafy-logo.74eec4f3.png
        │   ├── index.html
        │   └── js
        │       ├── app.2a195a37.js
        │       ├── app.2a195a37.js.map
        │       ├── chunk-vendors.184466aa.js
        │       └── chunk-vendors.184466aa.js.map
        ├── package-lock.json
        ├── package.json /* 필요 플러그인, 모듈 목록 */
        ├── public
        │   ├── favicon.ico
        │   └── index.html
        ├── src
        │   ├── App.vue /* 진입점 */
        │   ├── assets /* 리소스 저장용 폴더 */
        │   │   ├── images
        │   │   |   ├── sample-image.png
        │   │   |   └── ssafy-logo.png
        |   |   └── fonts /* 폰트 저장 폴더 */
        │   ├── common
        │   │   ├── css
        │   │   │   ├── common.css /* 공통 요소 (html, body, font), 공통 반응형 .hide-on-small 등 */
        │   │   │   └── element-plus.css /* Element Plus Overriding CSS */
        │   │   ├── lib
        │   │   │   ├── axios.js
        │   │   │   ├── element-plus.js
        │   │   │   ├── i18n.js
        │   │   │   ├── store.js
        │   │   │   └── vue-router.js
        │   │   ├── config.js /* 공용 설정 관련 함수 정의 */
        │   │   └── util.js  /* 공용 유틸 관련 함수 정의 */
        │   ├── main.js
        │   └── views
        │       ├── conferences /* 방 정보 페이지 */
        │       │   └── conference-detail.vue /* 방 상세 정보 페이지 */
        │       ├── history /* 이력 페이지 */
        │       │   └── history.vue
        │       ├── home
        │       │   ├── components /* 홈 메뉴 컴포넌트 */
        │       │   │   └── conference.vue /* 방 카드 */
        │       │   └── home.vue
        │       └── main
        │           ├── components /* 메인 관련 컴포넌트(사이드바, 헤더, 푸터, 로그인 다이얼로그) */
        │           │   ├── login-dialog.vue
        │           │   ├── main-footer.vue
        │           │   ├── main-header.vue
        │           │   └── main-sidebar.vue
        │           ├── main.css
        │           ├── main.vue
        |           ├── menu.json
        │           └── store
        │               ├── actions.js
        │               ├── getters.js
        │               ├── index.js
        │               ├── mutations.js
        │               └── state.js
        ├── vue.config.js /* Vue3 관련 설정 파일(프록시, 기타 옵션) */
        └── webpack.config.js /* stylus 파일 확장자(.styl) 관련 설정 */
```
