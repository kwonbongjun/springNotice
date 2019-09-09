<%@page import="com.java.web.Bean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../resources/css/springNotice.css">
<script>

<%
	Bean detail=(Bean) request.getAttribute("detail");	
	String title;
	String val;
	int no;
	if(detail==null){
		title="";
		val="";
		no=0;
	}else{
		no=detail.getNo();
		title=detail.getTitle();
		val=detail.getVal();
	}
%>

function load() {
	document.getElementById("title").value=<%="\""+title+"\""%>;
	document.getElementById("val").value=<%="\""+val+"\""%>;
}


</script>
</head>
<body onload="load()">

	<div id="write">
		<form method="POST">
			<input type="hidden" name="no" value="<%=no%>">
			<input id="title" type="text" name="title">
			<input id="val" class="text" type="text" name="val">
			<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/create" value="추가" method="POST"<%}else{%> method="GET" onclick="loginCheck()" value="추가" <% }%>>
			<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/update" value="업데이트" method="POST"<%}%>method="GET" onclick="loginCheck()" value="업데이트">
			<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/delete" value="삭제" method="POST"<%}%>method="GET" onclick="loginCheck()" value="삭제">
			<button type="button" formaction="/" onclick="load()">뒤로가기</button>
		</form>
	</div>
</body>
</html>