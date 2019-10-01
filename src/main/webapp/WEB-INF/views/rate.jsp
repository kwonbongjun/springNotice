<%@page import="com.java.web.bean.Movie"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.java.web.bean.Login"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.java.web.bean.Bean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%> <!--pageEncoding="UTF-8"-->
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="UTF-8"> -->
<title>봉준의 게시판</title>
<link rel="stylesheet" type="text/css" href="/resources/css/springNotice.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!--  -->
<style>
</style>
<script>
/* document.getElementById("content").onclick=function (){
	alert("로그인필요");
} */
var login=false;
var a;
var contentperpage=10;
var pagepernotice=10;
var totalpage;
var pagenum=0;
var hash=location.search.split("=");
var curpage=parseInt(hash[1]);
var pageIndex=curpage;
if(pageIndex==undefined || isNaN(pageIndex)) {
	pageIndex=1;
}
 while(pageIndex%pagepernotice!=1){
	pageIndex--;
} 

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
List<Movie> list=(List<Movie>) request.getAttribute("list");
int finalno= (Integer) request.getAttribute("finalno");

System.out.println("list"+list);
%>
<%
int a;
int contentperpage=10;
int pagepernotice=10;
int totalpage=(Integer)(request.getAttribute("total"));
int pagenum=0;
int pageIndex=1;
if(list==null) {
	a=0;
}else {
	a=list.size();
	pagenum=totalpage/contentperpage;
	if(totalpage%contentperpage>0){
		pagenum++;
	}
	System.out.println(""+a+pagenum);
}
//String user= (String) request.getAttribute("user");
//System.out.println("user"+user);
/* if(user==null) {
//Login user=(Login)request.getAttribute("user");}
} */
%>
totalpage=<%=totalpage%>
pagenum=parseInt(totalpage/contentperpage);
if(totalpage%contentperpage>0){
	pagenum++;
}

var checkIndex=-1;
function check(index){
	var check = document.getElementsByName("check");	
	if(list!=null) {
	for(var i=0;i<<%=a%>;i++){
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
}
<%-- function logout(){
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
} --%>
function loginCheck() {
	alert("로그인필요");
	
}
function viewchange(){
	document.getElementById("list").style.display='none';
	document.getElementById("write").style.display='block'
}
function pageclick(a) {
/* temp=document.getElementsByTagName("ul")[3].getElementsByTagName("li")[a].innerText;
page=parseInt(temp.substring(1,2)); */
}
function leftpageclick(a) {
if(isNaN(curpage)) curpage=1;

console.log(pagenum);
while(curpage>pageIndex){
	curpage--;	
}
if(curpage>1) {
curpage-=pagepernotice;
pageIndex-=pagepernotice;
}
alert(curpage);

<%if(request.getParameter("search")==null){%>
location.search="?pageNum="+curpage;
<%}else{%>
location.search="?pageNum="+curpage+"&search="+search;
<%}%>
}
function rightpageclick(a) {
if(isNaN(curpage)) curpage=1;

console.log(pagenum);
while(curpage>pageIndex){
	curpage--;	
}
if(curpage<pagenum) {
	console.log(curpage);
	if(pagenum-curpage<10) {
		curpage+=pagenum-curpage;
		pageIndex+=pagenum-curpage;
		<%if(request.getParameter("search")==null){%>
		location.search="?pageNum="+curpage;
		<%}else{%>
		location.search="?pageNum="+curpage+"&search="+search;
		<%}%>
		return;
	}
	curpage+=pagepernotice;
	pageIndex+=pagepernotice;
}
alert(curpage);
<%if(request.getParameter("search")==null){%>
location.search="?pageNum="+curpage;
<%}else{%>
location.search="?pageNum="+curpage+"&search="+search;
<%}%>
}

var search; 
function onload(){
search="<%=request.getParameter("search")%>";

if(pagenum-pageIndex<10) {
		pagepernotice=pagenum-pageIndex+1;
	}
	
while(document.getElementById("page").hasChildNodes()) {
	document.getElementById("page").removeChild(document.getElementById("page").childNodes[0])
}
	
var pageli=document.createElement('li');
var pagetext=document.createTextNode("<");
var lia=document.createElement('a');
pageli.appendChild(pagetext);
lia.appendChild(pagetext);
document.getElementById("page").appendChild(pageli);
document.getElementById("page").getElementsByTagName("li")[0].classList.add("content");
document.getElementById("page").getElementsByTagName("li")[0].appendChild(lia);
//document.getElementById("page").getElementsByTagName("li")[0].getElementsByTagName("a")[0].setAttribute('href', "/?pageNum="+curpage );
document.getElementById("page").getElementsByTagName("li")[0].getElementsByTagName("a")[0].setAttribute('onclick', "leftpageclick()" );
console.log("pageIndex"+pageIndex+"pagepernotice"+pagepernotice);
for(var i=pageIndex;i<pageIndex+pagepernotice;i++) {

pageli=document.createElement('li');
lia=document.createElement('a');
pagetext=document.createTextNode("["+i+"]");
lia.appendChild(pagetext);
document.getElementById("page").appendChild(pageli);
document.getElementById("page").getElementsByTagName("li")[i+1-pageIndex].classList.add("content");
document.getElementById("page").getElementsByTagName("li")[i+1-pageIndex].appendChild(lia);
<%if(request.getParameter("search")==null){%>
document.getElementById("page").getElementsByTagName("li")[i+1-pageIndex].getElementsByTagName("a")[0].setAttribute('href', "/recommend?pageNum="+i);
<%}else{%>
document.getElementById("page").getElementsByTagName("li")[i+1-pageIndex].getElementsByTagName("a")[0].setAttribute('href', "/recommend?pageNum="+i+"&search="+search);
<%}%>
t=i+1-pageIndex;
document.getElementById("page").getElementsByTagName("li")[i+1-pageIndex].getElementsByTagName("a")[0].setAttribute('onclick', "pageclick("+t+")" );
}
pageli=document.createElement('li');
pagetext=document.createTextNode(">");
lia=document.createElement('a');
lia.appendChild(pagetext);
document.getElementById("page").appendChild(pageli);
document.getElementById("page").getElementsByTagName("li")[pagepernotice+1].classList.add("content");
document.getElementById("page").getElementsByTagName("li")[pagepernotice+1].appendChild(lia);
//document.getElementById("page").getElementsByTagName("li")[pageIndex+pagepernotice].getElementsByTagName("a")[0].setAttribute('href', "/?pageNum="+curpage );
document.getElementById("page").getElementsByTagName("li")[pagepernotice+1].getElementsByTagName("a")[0].setAttribute('onclick', "rightpageclick()" );
}
<%-- <li class="content"><a href="/?pageNum=<%=1%>" onclick="return leftpageclick();"> < </a></li>
<%if(list!=null){
for(int i=1;i<=pagenum;i++){%>
	<li class="content"><a href="/?pageNum=<%=i%>" onclick="return pageclick(<%=i%>);">[<%=i%>]</a></li>
<%}} %>
<li class="content"><a href="/?pageNum=<%=1%>">></a></li> --%>
</script>
</head>

<body onload="onload()">
	<div class="main_blk">
		<div>
			<a href="/">bong's movie</a>
		</div>
		<div class="nav">
			<ul>
				<li><a href="/board">게시판</a></li>
				<li><a href="/search">키워드 분석</a></li>
			</ul>
			<form id="content" class="textright">
<%-- 							<%String user= (String) request.getAttribute("user"); %> --%>
				<%Login user = (Login) session.getAttribute("login"); %>
				<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><input type="submit"  formaction="/loginpage" value="login"  onclick="logout()"><%
				}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/submitlogout" id="logout" value="logout"">logout</button><% }%>
				<%if(session.getAttribute("login")!=null){%><%=user.getNickname()%><%} %>
				<button type="submit" formaction="/join" value="회원가입">회원가입</button>
			</form>
		</div>
	</div>
<div id="list">
	<script>
	</script>
	
	<form class="ml10">
	 <%if(session.getAttribute("login")!=null){%><button type="button"><a href="/recommend/?boardNum=<%if(list!=null){%><%=finalno+1%><%}else{%><%=1%><%}%>">입력</a></button><%}; %>
	<input type="text" name="search">
	<a href="/"><button type="submit">search</button></a>
	</form>
	<table class="table table-striped table-bordered table-hover ml10">
	<thead>
		<tr>
			<td>글 번호</td>
			<td class="title">제목</td>
			<td>평점</td>
		</tr>
	</thead>
	<tbody>
	<% 
		for(int i=0;i<a;i++) {
		
		%><tr>
		<%-- <input type="checkbox" name="check"  id="check" onclick="check(<%=i%>)"> --%>	
		<td><%=i%></td>
		<td><a href="/recommend/?boardNum=<%=list.get(i).getTitle()%>"><%=list.get(i).getTitle() %></a></td>
		<td><%=list.get(i).getDirector() %></td>
		<td><%=list.get(i).getUserRating()%></td>
		<td><%=list.get(i).getUserRatingCnt()%></td>
		
		<%-- <li class="content"><%=request.getAttribute("user")%></li> --%>
		</tr>
		
		<%}
	%>
	</tbody>
	</table>
<%-- 	<ul>
	<li class="content"><a href="/?pageNum=<%=1%>" onclick="return leftpageclick();"> < </a></li>
	<%if(list!=null){
	for(int i=1;i<=pagenum;i++){%>
		<li class="content"><a href="/?pageNum=<%=i%>" onclick="return pageclick(<%=i%>);">[<%=i%>]</a></li>
	<%}} %>
	<li class="content"><a href="/?pageNum=<%=1%>">></a></li>
	</ul> --%>
	<ul id="page" class="pagination"></ul>
	</div>

	
</body>
</html>