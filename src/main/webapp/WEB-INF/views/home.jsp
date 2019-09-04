<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ page session="false" %> --%>
<%@page import="com.java.web.Bean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!--<link rel="stylesheet" type="text/css" href="css/servletNotice.css">-->
<link rel="stylesheet" type="text/css" href="../../resources/css/springNotice.css">

<style>

</style>
<script type="text/javascript">
/* document.getElementById("content").onclick=function (){
	alert("로그인필요");
} */
var login=false;
function login() {
	alert("로그인필요");
	document.getElementsByName("login")
}
function logout(){
	
	alert("1");
}

<%
if(session==null) {
	
}else{
	%>login=<%session.getAttribute("login");%>
	console.log(login);
	<%
}
System.out.println("session"+session.getAttribute("login"));
List<Bean> list=(List<Bean>) request.getAttribute("list");
%>

var checkIndex=-1;
function check(index){

	var check = document.getElementsByName("check");		
	for(var i=0;i<<%=list.size()%>;i++){
		if(i!=index)
		document.getElementsByName("check")[i].checked=false;
	}
	if(document.getElementsByName("check")[index].checked){
		console.log("check!");
		document.getElementById("content").no.value=document.getElementsByTagName("ul")[index].getElementsByTagName("li")[0].textContent;
		document.getElementById("content").val.value=document.getElementsByTagName("ul")[index].getElementsByTagName("li")[1].textContent;
	}else{
		document.getElementById("content").no.value="";
		document.getElementById("content").val.value="";
	}

}
</script>
</head>

<body>
<%
	int a;
	if(list==null) {
		a=0;
	}else {
		a=list.size();
	}
%>
	<form id="content">
		<input type="text" name="no">
		<input type="text" name="val">
		<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/create" value="추가"<%}%> method="GET">
		<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/update" value="업데이트"<%}%>method="GET">
		<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/delete" value="삭제"<%}%>method="GET">
		<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><input type="submit"  formaction="/login" value="login"  onclick="login()"><%
		}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/login" id="logout" value="logout">logout</button><% }%>
	</form>
	<% 
		for(int i=0;i<a;i++) {
		
		%><ul>
		<input type="checkbox" name="check"  id="check" onclick="check(<%=i%>)">	
		<li class="content"><%=list.get(i).getNo()%></li>
		<li class="content"><%=list.get(i).getVal() %></li>
		</ul>
		<%}
	%>
</body>
</html>
