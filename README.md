# springNotice
교육센터에서 개인프로젝트로 세 달 진행
- 사용기술 : 화면은 html, CSS, Javascript, AJAX를 이용했고 서버는 Java spring framework를, DB는 Oracle을 사용 
- 설명
  - 회원가입, 로그인, 게시판, 키워드 분석, 영화 추천, 평점 분석` 기능. Oauth 기술을 이용한 로그인 통합
  - 키워드 분석 기능은 영화를 검색했을 때 open API를 사용해서 관련 영화를 보여주고 평점을 매길 수 있음. 
  - 웹 사이트에서 얻은 데이터를 Hadoop의 map-reduce를 사용해서 정제한 뒤 키워드 순으로 정렬하여 시각화.
---

![image](https://user-images.githubusercontent.com/49854611/157050614-e9ecb67e-8341-4914-9da6-dda1232aa427.png)
- 영화 검색
  - Open api 를 사용하여 영화 목록 가져온 후 DB에 저장
  - 웹에서 데이터를 크롤링 하고 하둡의 map-reduce를 통해 데이터 정제. 정제된 데이터를 많이 검색된 순으로 정렬하여 워드클라워드 형식으로 시각화. 

- 평점 추가
  - Ajax를 이용하여 평점 등록


![image](https://user-images.githubusercontent.com/49854611/157050664-6638bf3b-e476-4024-a35e-1a10eaf5578c.png)
- 영화 추천
  - 영화를 추천해주고 해당 영화를 봤거나 보고 싶지 않을 때 체크박스 클릭하면 같은 장르의 다른 영화 추천
  - 추천 기준: 사용자가 평점 준 영화 중에 평균 평점이 가장 높은 영화와 동일한 장르 추천

![image](https://user-images.githubusercontent.com/49854611/157051494-95e3a45f-e1dc-4a48-bddc-a8e6626283bd.png)
![image](https://user-images.githubusercontent.com/49854611/157050192-15ec9fcb-104a-471d-97cf-fc7a94743aca.png)
- 영화 평점 순위 목록
- 영화 평점 순위와 평점 준 회수 조회 가능


