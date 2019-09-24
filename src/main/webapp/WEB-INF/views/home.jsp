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
				<li>게시판</li>
				<li>키워드 분석</li>
				<li>	
					<form id="content">
						<%String user= (String) request.getAttribute("user"); %>
						<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><input type="submit"  formaction="/login" value="login"  onclick="logout()"><%
						}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/login" id="logout" value="logout"">logout</button><% }%>
						<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><input type="submit"  formaction="/kakao" value="카카오""><%
						}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/kakaologout" value="카카오로그아웃"">카카오로그아웃</button><% }%>
						<%if(session.getAttribute("login")!=null){%><p><%=user%></p><%} %>
						<input type="submit" formaction="/join" value="회원가입">
					</form>
				</li>
			</ul>
		</div>
	 	<p>☆사이트 소개☆<br>
	 	1.게시판<br>
	 	2.키워드 분석</p>
	</div>
	<div></div>
</body>
</html>
