<%@page import="com.java.web.bean.Login"%>
<%@page import="com.java.web.bean.Movie"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/springNotice.css">
<script>
<%Login user = (Login) session.getAttribute("login");
Movie[] movie = (Movie[]) request.getAttribute("mlist"); 

if(movie!=null) {
%>var m=new Array();
var len=<%=movie.length%>;<%
for(int i=0;i<movie.length;i++) {
	%>m[<%=i%>]=<%="\""+movie[i].getTitle()+"\""%>;<%
}
}%>
var cnt=0;

var u=<%="\""+user.getId()+"\""%>
function check(idx){
	alert(len);
	document.getElementsByClassName("inline")[idx].style.display="none"
	/* document.getElementsByClassName("inline")[idx].parentNode.removeChild(document.getElementsByClassName("inline")[idx]); */

	for(var i=0;i<len;i++) {
	if(document.getElementsByClassName("inline")[i].style.display=="none")
		cnt++;
	}
	if(cnt==len){
		
		$.ajax({
			url:"/recommend",
			dataType:"json",
			data:{update1:m[0],update2:m[1],update3:m[2],user:u},
			async: false
		}).done(function(data){
			alert("추천")

		});
		 location.reload();
	}
	cnt=0;
}
</script>
</head>
<body>

<%if(movie!=null) { 
for(int i=0;i<movie.length;i++){%>
<div class="recommend">
<input type="checkbox" onclick="check(<%=i%>)">
<img src="<%=movie[i].getImage()%>" alt="movie" width=110; height=150;>
<p>제목:<%=movie[i].getTitle()%></p>
<p>감독:<%=movie[i].getDirector() %>
<p>배우:<%=movie[i].getActor() %>
</div><%} 
}else{%>
	추천할 영화가 없습니다.
<%}%>
</body>
</html>