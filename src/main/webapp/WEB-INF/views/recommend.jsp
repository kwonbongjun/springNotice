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
<p>����:<%=movie.getTitle()%></p>
<p>����:<%=movie.getDirector() %>
<p>���:<%=movie.getActor() %>
<%} %>
</body>
</html>