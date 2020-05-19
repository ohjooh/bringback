# Bring Back
레트로 컨셉 음악 플레이어 앱

## 팀원

이름 | 역할1 | 
---- | ---- | 
[오유림](https://github.com/ohyr18) | 디자인 80%, 모바일 10% (화면작업 보조), 백엔드 
[조수연](https://github.com/jopopcorn) | 디자인 20%, 모바일 90%

## 사용 스택
스택 | 사용기술 | 
---- | ---- | 
Design | Adobe XD
Frontend | Android(Java)
[Backend](https://github.com/ohjooh/bringback_server) | Node.js, MongoDB

## 개발 동기
바쁜 세상 속에서 과거를 회상하며 행복을 느끼는 현대인이 많아지고 있다. 이들이 좋아할 수 있는 감각적인 음악 플레이어를 개발하는 것이 목적이다.

## 진행 기간
2019년 10월 14일 ~ 12월 8일

## 주요 기능
* 회원 기능
  * 회원 가입 및 로그인 기능
  * 유저의 프로필 사진, 비밀번호, 취향 변경 기능
* 플레이어 기능
  * 앱 내의 하단 플레이어바와 플레이어바를 펼친 플레이어 화면, 위젯으로 오디오 재생 제어
  * 잠금 화면에 현재 재생 중인 음악 정보와 현재 시간 및 LP 화면 제공
  * 백그라운드에서 음악 재생
  * 오디오 포커스 처리

## 활용 기술

* [Exoplayer](https://github.com/google/ExoPlayer) - 커스텀 플레이어 설계, 다양한 기기 및 버전 지원, 안정성 등을 제공하는 미디어 플레이어 라이브러리
* [tedPermission](https://github.com/ParkSangGwon/TedPermission) - 갤러리 접근 권한을 허용받기 위한 오픈소스 라이브러리
* [Glide](https://github.com/bumptech/glide) - 이미지 최적화 및 크기 조정 등을 위해 사용한 오픈소스 라이브러리
* [Ucrop](https://github.com/Yalantis/uCrop) - 갤러리에서 불러온 사진을 편집하는 작업을 수행하는 오픈소스 라이브러리
* [Retrofit](https://github.com/square/retrofit) - 서버와의 HTTP 통신을 돕는 오픈소스 라이브러리
* Fragment - 한 화면에서 여러 메뉴를 전환하여 여러 개의 UI를 제공
* Animation - 사용자의 작업에 어울리는 UI를 제공하기 위해 취향 선택 화면, 잠금 화면에서 애니메이션을 적용


## 설계도
<img width="80%" alt="설계도_white" src="https://user-images.githubusercontent.com/35393459/82349131-b55d7800-9a34-11ea-8259-8cf5ab565502.PNG"></img>

## 결과

#### Register
<img width="35%" alt="register" src="https://user-images.githubusercontent.com/35393459/82351284-5baa7d00-9a37-11ea-9382-2a873755b8c4.gif"></img>

#### Login
<img width="35%" alt="login" src="https://user-images.githubusercontent.com/35393459/82351280-59e0b980-9a37-11ea-9a33-7f9f44b9f8c9.gif"></img>

#### Home
<img width="35%" alt="home" src="https://user-images.githubusercontent.com/35393459/82351277-59482300-9a37-11ea-9acc-aa1759ca23a0.gif"></img>

#### Player
<img width="35%" alt="player" src="https://user-images.githubusercontent.com/35393459/82351282-5a795000-9a37-11ea-80ba-b3afbc6a338b.gif"></img>

#### Browser
<img width="35%" alt="browser" src="https://user-images.githubusercontent.com/35393459/82351273-577e5f80-9a37-11ea-96a6-8fadb547ab6c.gif"></img>

#### Playlist
<img width="35%" alt="playlist" src="https://user-images.githubusercontent.com/35393459/82351283-5b11e680-9a37-11ea-91d4-07949437c6a3.gif"></img>

#### Sharing Tastes
<img width="35%" alt="sharingTaste" src="https://user-images.githubusercontent.com/35393459/82351291-5cdbaa00-9a37-11ea-9060-d4a6635f4c34.gif"></img>

#### Search
<img width="35%" alt="search" src="https://user-images.githubusercontent.com/35393459/82351288-5c431380-9a37-11ea-9705-cd2e2cc83ce1.gif"></img>
