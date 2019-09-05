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
<script>
/* document.getElementById("content").onclick=function (){
	alert("로그인필요");
} */
var login=false;
/* function login() {
	alert("로그인필요");
	
} */
/* function logout(){
	alert("로그인 실패");
} */

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

function logout(){
if(<%=session.getAttribute("login")%>!=null) {
	//ajax
/* 	$.ajax("url":"/login", "type":"GET", "data":{id: $("#usrname").val(), pw:$("#psw").val()}, (data) => {
			data = JSON.parse(data);
			if(data.state){
				login = true;
				nickname = data.nickname;
				alert("로그인 완료");
				//$("#myModal").modal("hide");
				//
				
			}else {
				alert("로그인 실패");
			}
			//console.log(data);
			//console.log(nickname);

		}); */
	alert("로그인 성공");
}else{
	alert("로그인 실패");
	}
}
function loginCheck() {
	alert("로그인필요");
	
}

function viewchange(){
	document.getElementById("list").style.display='none';
	document.getElementById("write").style.display='block'

}


</script>
</head>

<body onload="">
<%
	int a;
	if(list==null) {
		a=0;
	}else {
		a=list.size();
	}
%>
<div id="list">
	<script>

	</script>
	<form id="content">
		<input type="text" name="no" >
		<input type="text" name="val">
		<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/create" value="추가"<%}else{%> method="GET" onclick="loginCheck()" value="추가" <% }%>>
		<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/update" value="업데이트"<%}%>method="GET" onclick="loginCheck()" value="업데이트">
		<input type="submit"  <%if(session.getAttribute("login")!=null){%>formaction="/delete" value="삭제"<%}%>method="GET" onclick="loginCheck()" value="삭제">
		<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><input type="submit"  formaction="/login" value="login"  onclick="logout()"><%
		}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/login" id="logout" value="logout"">logout</button><% }%>
		<button type="button" onclick="viewchange()">입력</button>
	</form>

	<% 
		for(int i=0;i<a;i++) {
		
		%><ul>
		<input type="checkbox" name="check"  id="check" onclick="check(<%=i%>)">	
		<li class="content"><%=list.get(i).getNo()%></li>
		<li class="content"><a href="/?boardNum=<%=list.get(i).getNo()%>"><%=list.get(i).getVal() %></a></li>
		</ul>
		<%}
	%>
	</div>

	
</body>
</html>
