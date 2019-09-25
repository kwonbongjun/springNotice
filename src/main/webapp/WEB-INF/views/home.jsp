<%@page import="com.java.web.bean.Login"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>봉준의 게시판</title>
<link rel="stylesheet" type="text/css" href="/resources/css/springNotice.css">
</head>
<body>
	<div class="main_blk">
		<div>
			<a href="/">bong's movie</a>
		</div>
		<div class="nav">
			<ul>
				<li><a href="/board">게시판</a></li>
				<li><a href="/collect">키워드 분석</a></li>
			</ul>
			<form id="content" class="textright">
<%-- 							<%String user= (String) request.getAttribute("user"); %> --%>
				<%Login user = (Login) session.getAttribute("login"); %>
				<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><input type="submit"  formaction="/loginpage" value="login"  onclick="logout()"><%
				}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/submitlogout" id="logout" value="logout"">logout</button><% }%>
				<%if(session.getAttribute("login")!=null){%><%=user.getNickname()%><%} %>
				<button type="submit" formaction="/join" value="회원가입">회원가입</button>
			</form>
		</div>
		<div class="body">
	 	<p>☆사이트 소개☆<br>
	 	1.게시판<br>
	 	2.키워드 분석</p>
			<img src="/resources/img/analysis1.jpg" alt="analysis">
		</div>
	</div>
	<div class="footer">
		
	</div>
</body>
</html>
