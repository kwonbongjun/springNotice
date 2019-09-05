<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../resources/css/springNotice.css">
<script>
function load() {
	location.search="";
}
</script>
</head>
<body>
	<div id="write">
		<form>
			<input class="text" type="text" name="text">
			<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/create" value="추가"<%}else{%> method="GET" onclick="loginCheck()" value="추가" <% }%>>
			<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/update" value="업데이트"<%}%>method="GET" onclick="loginCheck()" value="업데이트">
			<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/delete" value="삭제"<%}%>method="GET" onclick="loginCheck()" value="삭제">
			<button type="button" formaction="/" onclick="load()">뒤로가기</button>
		</form>
	</div>
</body>
</html>