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
</script>
</head>
<body>
<%Login user = (Login) session.getAttribute("login"); %>
	<div class="main_blk">
		<div class="logo">
			<a href="/">bong's movie</a>
		</div>
		<div class="nav">
			<ul>
				<li><a href="/board">게시판</a></li>
				<li><a href="/search">키워드 분석</a></li>
				<%if(user!=null) {%>
				<li><a href="/recommend?user=<%=user.getId()%>")>영화 추천</a></li>
				<%} %>
			</ul>
			<form id="content" class="textright">
<%-- 							<%String user= (String) request.getAttribute("user"); %> --%>
				
				<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><button type="submit"  formaction="/loginpage" value="login"  onclick="logout() " class="login"><i class="fas fa-sign-in-alt"></i></button><%
				}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/submitlogout" id="logout" value="logout" class="login"><i class="fas fa-sign-out-alt"></i></button><% }%>
				<%if(session.getAttribute("login")!=null){%><%=user.getNickname()%><%} %>
				<button  type="submit" formaction="/join" class="login"><i class="fas fa-user-plus"></i></button>

			</form>
		</div>
		<div class="body">
	 	<p>☆사이트 소개☆</p><br>
	 		<div class="c1">
	 		<p>1.게시판</p>
	 		<img class="fl" src="/resources/img/board.PNG" alt="board">
	 		<p>영화에 대해 자유롭게 이야기 나누고 생각을 공유할 수 있는 게시판 기능</p>
	 		</div>
	 		<div class="c2">
			 	<p>2.키워드 분석</p>
			 	<p>영화를 검색했을 때 웹에서 영화와 관련된 키워드 추출해서 WordCloud 형식으로 시각화</p>
			 	<img  class="tr" src="/resources/img/analysis1.jpg" alt="analysis" >
				
			</div>
	</div>
	<div class="footer">
		<ul>
			<li>tel:010-1111-2222</li>
			<li>address:경기도 군포시</li>
			<li>Copyright@Bong Corp.All rights reserved.</li>
		</ul>
	</div>
</body>
</html>
