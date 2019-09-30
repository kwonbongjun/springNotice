<%@page import="com.java.web.bean.Movie"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script>
<%Movie[] movie = (Movie[]) request.getAttribute("mlist"); 
if(movie!=null) {
%>var len=<%=movie.length%><%
}%>
var cnt=0;
var m=<%=movie%>
function check(idx){
	alert(len);
	document.getElementsByClassName("inline")[idx].style.display="none"
	/* document.getElementsByClassName("inline")[idx].parentNode.removeChild(document.getElementsByClassName("inline")[idx]); */

	for(var i=0;i<len;i++) {
	if(document.getElementsByClassName("inline")[i].style.display=="none")
		cnt++;
	}
	if(cnt==3){
		$.ajax({
			url:"/recommend",
			dataType:"json",
			data:{update:m}
		}).done(function(data){
			alert("추천")
		});
	}
	cnt=0;
}
</script>
</head>
<body>

<%if(movie!=null) { 
for(int i=0;i<movie.length;i++){%>
<div class="inline w30">
<input type="checkbox" onclick="check(<%=i%>)">
<img src="<%=movie[i].getImage()%>" alt="movie" width=110; height=150;>
<p>제목:<%=movie[i].getTitle()%></p>
<p>감독:<%=movie[i].getDirector() %>
<p>배우:<%=movie[i].getActor() %>
</div><%} 
}%>
</body>
</html>