<%@page import="com.java.web.bean.Login"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉준의 게시판</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/resources/css/springNotice.css">
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>



function user(id) {
	$.ajax({
		url:"/recommend",
		dataType:"json",
		data:{user:id}
	}).done(function(data){
		alert("추천")
	});
}
<%String ac="false";
Login user = (Login) session.getAttribute("login"); 
String ra =(String) request.getAttribute("recommendAccess");
String ra2 =(String) request.getAttribute("rateAccess");
if(ra==null) {ra="false";}
if(ra2==null) {ra2="false";}
String uid=null;
if(user!=null) {uid=user.getId();}
if(ra!="false") {%>alert("로그인 후 이용 가능");<%}%>


</script>
</head>
<body>

	<div class="main_blk">
		<div class="logo">
			<a href="/">영화 분석 사이트</a>
		</div>
		<div class="nav">
			<ul onclick="nav(this)">
				<li><a href="/board">게시판</a></li>
				<li><a href="/search">키워드 분석</a></li>
				<li><a href="/recommend?user=<%=uid%>")>영화 추천</a></li>
				<li><a href="/rate">평점 분석</a></li>
			</ul>
			<form id="content" class="textright">
<%-- 							<%String user= (String) request.getAttribute("user"); %> --%>
				
				<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><button type="submit"  formaction="/loginpage" value="login"  onclick="logout() " class="login"><i class="fas fa-sign-in-alt"></i></button><%
				}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/submitlogout" id="logout" value="logout" class="login"><i class="fas fa-sign-out-alt"></i></button><% }%>
				<%if(session.getAttribute("login")!=null){%><%=user.getNickname()%><%} %>
				<%if(session.getAttribute("login")==null) {%><button  type="submit" formaction="/join" class="login"><i class="fas fa-user-plus"></i></button><%
				}else{%><input type="hidden" name="id" value="<%=user.getId()%>"><button type="submit" formaction="/mypage" method="post" id="mypage" value="mypage"><i class="fas fa-user"></i></button><% }%>


			</form>
		</div>
		<div class="body">
	 	<p>☆사이트 소개☆</p><br>
	 		<div class="c1">
		 		<div class="w5">
		 		<img src="/resources/img/board.PNG" alt="board">
		 		</div>
		 		<div class="w5">
				<p>1.게시판<br>
		 		영화에 대해 자유롭게 이야기 나누고 생각을 공유할 수 있는 게시판 기능</p>
		 		</div>
	 		</div>
	 		<div class="c1">
	 			<div class="w5">
			 	<p>2.키워드 분석<br>
			 	영화를 검색했을 때 웹에서 영화와 관련된 키워드 추출해서 WordCloud 형식으로 시각화</p>
			 	</div>
			 	<div class="w5 tc"><img src="/resources/img/keyword.PNG" alt="analysis" ></div>
			</div>
			
			<div class="c1">
		 		<div class="w5">
		 		<img src="/resources/img/recommend.jpg" alt="board">
		 		</div>
		 		<div class="w5">
				<p>3.영화추천<br>
		 		영화 검색 후에 평점을 주면 그 사용자에 맞춤 장르 추천 서비스</p>
		 		</div>
	 		</div>
	 		<div class="c1">
	 			<div class="w5">
			 	<p>4.평점 분석<br>
			 	전체 영화 평점 순위 뿐만 아니라 각 영화의 연령별, 나이별 평점 확인 가능</p>
			 	</div>
			 	<div class="w5"><img src="/resources/img/rate.PNG" alt="analysis" ></div>
			</div>
			
	</div>
	<div class="footer">
		<div class="ib">
			<ul class="footerul">
			<li>tel&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:010-1111-2222</li>
			<li>address:경기도 군포시</li><br>
			</ul>
			<p>Copyright@Bong Corp.All rights reserved.</p>
		</div>
	</div>
</body>
</html>
