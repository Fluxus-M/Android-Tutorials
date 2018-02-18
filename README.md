# Android-Tutorials
실무에 바로 적용 할 수 있는 안드로이드 8.0 앱 프로그래밍(Oreo 버전) 의 예제 소스코드 및 수정사항을 제공합니다.

<img src="https://s26.postimg.org/fmiyzofex/title_book.png"><br>

안드로이드 8.0 앱 프로그래밍<br>
저자 : 임규민, 박헌우<br>
출판사: [다본](http://da-bon.com/) <br>
페이지: 600<br>
ISBN: 9791188577057<br>

### 도서 소개 및 구매 페이지<br>
[네이버 책](http://book.naver.com/bookdb/book_detail.nhn?bid=13328867)<br>
[교보문고](http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9791188577057) / 
[Yes24](http://www.yes24.com/24/goods/58666252?scode=032&OzSrank=1) / 
[알라딘](http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=133981853) <br>

***
### 프로젝트 소스 코드 참고 사항
- 모든 프로젝트는 Windows 10 운영체제에서 실행되는  안드로이드 스튜디오 3.0.1에서 작성되었습니다. 테스트 기기로는  삼성 갤럭시 S6, LG V10, 구글 픽셀 XL을 사용하였습니다.

- [Github의 소스코드 이용법](https://github.com/Fluxus-M/Android-Tutorials/wiki/Github%EC%9D%98-%EC%86%8C%EC%8A%A4%EC%BD%94%EB%93%9C-%EC%9D%B4%EC%9A%A9%EB%B2%95)

***
### 각 장에 관한 간단한 설명
**제1장 안드로이드란 무엇인가**<br>
- 안드로이드 운영체제의 역사와 현재, 그리고 최신 버전인 Oreo에 대하여 소개한다. 안드로이드가 어떻게 탄생되었고 어떤 이념을 가지고 있으며, 어떻게 변화하여 왔는지 살펴본다.



**제2장 안드로이드 개발환경 구축하기**<br>
- 안드로이드를 개발하기 위한 안드로이드 스튜디오의 설치와 개발을 위해 제공되는 안드로이드 에뮬레이터 (Android Virtual Device : AVD) 의 개발 성정 및 실제 안드로이드 폰의 개발설정에 대하여 알아본다.  안드로이드 스튜디오와 자바 개발도구(JDK)를 설치하고 간단한 앱을 기기에 설치하고 실행하는 방법에 대해 알아본다.


**제3장 [안드로이드 앱의 화면 구성하기](https://github.com/Fluxus-M/Android-Tutorials/tree/master/chap3)**<br>
- 안드로이드 프로젝트의 구조를 알아보고, 안드로이드 앱을 구성하는 요소들에 대하여 알아본다.  안드로이드 앱에서 사용자가 보는 화면인 액티비티와 액티비티의 동작 과정인 생명주기를 자세히 알아본다. 액티비티를 구성하는 요소인 뷰(View) 의 구조와 뷰를 이용해서 앱의 UI(User Interface)화면을 구성하는 기본적인 방법에 대해 공부한다.


**제4장 [사용자와의 상호작용 처리하기](https://github.com/Fluxus-M/Android-Tutorials/tree/master/chap4)**<br>
- 3장에서 배운 UI화면 구성방법을 응용하여 사용자의 입력을 받고 버튼을 통해 결과를 처리 한 후 알람 및 토스트로 입력결과를 출력하는 연습을 해본다. 앱을 개발하기 위한 기본이자 기초사항인 이벤트 처리방법과 메시지 전달방법, 여러개의 화면을 사용하고 각 화면을 이동, 전환하는 방법에 대해 공부한다.


**제5장 [마이크를 이용한 앱 만들기](https://github.com/Fluxus-M/Android-Tutorials/tree/master/chap5)**<br>
- 안드로이드 기기의 여러가지 장치를 앱에 사용하기 위하여 시스템의 권한을 사용자로부터 얻고 사용하는 방법을 알아보고, 멀티미디어를 다루기 위한 MediaRecoder 의 사용방법을 공부한다. 사용 중 발생하는 여러 예외 상황을 처리하기 위한 try-catch 문법을 연습한다. 이를 응용하여 마이크를 이용한 녹음 앱을 만들어본다.


**제6장 [카메라를 이용한 앱 만들기](https://github.com/Fluxus-M/Android-Tutorials/tree/master/chap6)**<br>
- 카메라를 이용한 동영상 녹화 앱을 만들어본다. 카메라 장치의 사용권한을 얻는 방법과 카메라의 미리보기를 위한 텍스쳐뷰의 사용방법 및 동영상 재생을 위한 비디오뷰의 사용방법을 공부한다.


**제7장 [파일과 데이터베이스를 이용한 앱 만들기](https://github.com/Fluxus-M/Android-Tutorials/tree/master/chap7)**<br>
- 안드로이드에서 제공하는 저장 방법들을 사용하는 방법을 알아본다. 파일과 데이터베이스를 사용하여 데이터를 저장하고 읽어올 수 있는 방법을 알아본다. 사용자 상태를 저장하는 SharedPreferences 사용법, 파일 입출력을 담당하는 File 의 사용법, SQLite를 이용하여 안드로이드에서 데이터베이스를 만들고 저장하는 방법, RecyclerView를 이용하여 목록을 사용자에게 보여주는 방법에 대하여 공부하고 이를  응용하여 메모장 앱을 만들어본다.


**제8장 [위치서비스 와 네트워크를 이용한 앱 만들기](https://github.com/Fluxus-M/Android-Tutorials/tree/master/chap8)**<br>
- 화면에 구글 지도를 표시하는 MapView와 구글 API를 사용하여 위치를 얻는 방법을 공부하고 안드로이드의 네트워크를 사용하는 방법과 여러 작업을 동시에 처리할수 있는 스레드(Thread) 에 대해 알아본다. 이를 응용하여 구글 지도에서 내가 원하는 장소로 갈 수 있는 경로를 안내해주는 앱을 만들어본다.


**제9장 Play 스토어에 앱 출시하기**<br>
- 9장에서는 구글 개발자로 등록하는 방법을 알아본다. 개발자로 등록한 후 지금까지 만든 앱을 구글 플레이스토어에 올려보고, 앱을 배포하고 사용자의 에러를 확인하는 방법과 수정 사항을 반영하여 앱을 업데이트 하는 방법을 알아본다.



***

### 참고
안드로이드 공식 개발자 사이트<br>
https://developer.android.com/index.html
