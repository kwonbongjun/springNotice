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
<p>���� ����:<%=map.get("mrate") %></p>
<p>���� ����:<%=map.get("frate") %></p>
<p>10�� ����:<%=map.get("rate10") %></p>
<p>20�� ����:<%=map.get("rate20") %></p>
<p>30�� ����:<%=map.get("rate30") %></p>
<p>40�� ����:<%=map.get("rate40") %></p>
<p>50�� �̻� ����:<%=map.get("rate50") %></p>
</body>
</html>