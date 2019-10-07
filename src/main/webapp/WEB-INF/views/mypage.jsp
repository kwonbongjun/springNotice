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
		<div class="mypage">
	 		<p>id:<%=user.getId() %></p>
	 		<p>nickname:<%=user.getNickname() %></p>
	 		<p>gender:<%=user.getGender() %></p>
	 		<p>age:<%=user.getAge() %></p>
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
