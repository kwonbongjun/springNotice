<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<%Map<String,Integer> map= (Map<String,Integer>) request.getAttribute("map");%>
</head>
<body>
<p>남성 비율:<%=map.get("mrate") %></p>
<p>여성 비율:<%=map.get("frate") %></p>
<p>10대 비율:<%=map.get("rate10") %></p>
<p>20대 비율:<%=map.get("rate20") %></p>
<p>30대 비율:<%=map.get("rate30") %></p>
<p>40대 비율:<%=map.get("rate40") %></p>
<p>50대 이상 비율:<%=map.get("rate50") %></p>
</body>
</html>