<%@page import="com.java.web.bean.Movie"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<%Movie movie = (Movie) request.getAttribute("mlist"); %>
<%if(movie!=null) { System.out.println(movie.getTitle());%>
<div class="inline w30">
<img src="<%=movie.getImage()%>" alt="movie" width=110; height=150;>
<p>제목:<%=movie.getTitle()%></p>
<p>감독:<%=movie.getDirector() %>
<p>배우:<%=movie.getActor() %>
<%} %>
</body>
</html>