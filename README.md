# Android_RegisterationProject
> 안드로이드 수강신청 앱 프로젝트

## 개발환경 및 사용버전
- Android Studio(Java)
- SdkVersion 29(Android 10.0)
- 테스팅 환경(Galaxy Note9)
- cafe24 웹호스팅

## 구현내용
- 로그인/회원가입(아이디중복체크)
- 어플리케이션 정보(팝업창)   
- 공지사항, 강의목록, 시간표, 강의분석
- 학부, 년도, 학기, 전공에 따른 강의목록 조회
- 추가한 강의에 대하여 시간표를 나타냄
- 추가한 강의에 대하여 삭제기능      
- 강의 신청인원, 강의 경쟁률을 나타냄 
- Volley 라이브러리를 사용해 서버와 통신

## ERD
<img src="https://user-images.githubusercontent.com/76413580/112757870-79b51680-9026-11eb-8a08-d3ef321bd8ee.PNG"></image>

## Dataflow
<img src="https://user-images.githubusercontent.com/76413580/113073079-5eebc900-9203-11eb-858c-857073205136.png"></image>

## Screenshots
<img src="https://user-images.githubusercontent.com/76413580/110277785-54ca1680-8019-11eb-9534-47b54df72ef3.jpg" width="22%"></image>
<img src="https://user-images.githubusercontent.com/76413580/110277798-58f63400-8019-11eb-945f-8426bbd88b81.jpg" width="22%"></image>
<img src="https://user-images.githubusercontent.com/76413580/110277804-5abff780-8019-11eb-909d-3339280fac21.jpg" width="22%"></image>
<img src="https://user-images.githubusercontent.com/76413580/110277808-5c89bb00-8019-11eb-9b6c-6dbbaa06a80f.jpg" width="22%"></image>
<img src="https://user-images.githubusercontent.com/76413580/110403170-fc018900-80bf-11eb-8387-5a1882cb46fa.jpg" width="22%"></image>
<img src="https://user-images.githubusercontent.com/76413580/110403181-01f76a00-80c0-11eb-81a0-70c5eee97d29.jpg" width="22%"></image>
<img src="https://user-images.githubusercontent.com/76413580/110403192-058af100-80c0-11eb-8bbc-100d5b0bfc7b.jpg" width="22%"></image>
<img src="https://user-images.githubusercontent.com/76413580/110403195-091e7800-80c0-11eb-89a2-a60251591743.jpg" width="22%"></image>

## 프로젝트 고찰
문제점
> 시간표를 보여주는 부분에서 글씨들이 크게 나와 표의 크기가 화면을 넘어가는 현상이 발생<br>
> Spinner 선택시에 리스트가 보이지 않음<br>
> 안드로이드 스튜디오 내에 있는 vector 이미지가 sdk 버전29에선 출력이 되지 않음<br> 

해결
> AutoResizeTextView 오픈소스 라이브러리를 이용해 글자 크기를 조정<br>
> Spinner에 어댑터를 연결할 때 리스트가 비어있는 상태여서 생긴 문제(adapter에 변동을 알리는 소스코드 추가)<br>
> 28픽셀 크기의 무료 icon으로 대체하였음<br>

프로그램 장/단점
- 웹 크롤링을 하여 DB정보를 받아오는 방법이 있지만 직접 입력하였기 때문에 정확도와 시간소요가 많이 되었음<br>
- DB 구축에 웹 서버 호스팅을 이용했기 때문에 기간이 지나면 DB 서버 구축을 재설정 해줘야함<br>
- sdk버전별로 호환성이 떨어짐<br>


