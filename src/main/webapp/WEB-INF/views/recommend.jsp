<%@page import="java.util.List"%>
<%@page import="com.java.web.bean.Login"%>
<%@page import="com.java.web.bean.Movie"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/springNotice.css">
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<script>
<%String ac="false";
Login user = (Login) session.getAttribute("login"); 
String ra =(String) request.getAttribute("recommendAccess");
String ra2 =(String) request.getAttribute("rateAccess");
if(ra==null) {ra="false";}
if(ra2==null) {ra2="false";}
String uid=null;
if(user!=null) {uid=user.getId();}
if(ra!="false") {%>alert("로그인 후 이용 가능");<%}%>


<%
/* Movie[] movie = (Movie[]) request.getAttribute("mlist");  */
List<Movie> movie = (List<Movie>) request.getAttribute("mlist");
if(movie!=null) {
%>var m=new Array();
var d = new Array();
var r = new Array();
var len=<%=movie.size()%>;<%
for(int i=0;i<movie.size();i++) {
	%>m[<%=i%>]=<%="\""+movie.get(i).getTitle()+"\""%>;
	d[<%=i%>]=<%="\""+movie.get(i).getDirector()+"\""%>;
	r[<%=i%>]=<%="\""+movie.get(i).getRelease()+"\""%>;
	<%
}
}%>
var cnt=0;

var u=<%="\""+user.getId()+"\""%>
function check(idx){
	alert(len);
	document.getElementsByClassName("recommend")[idx].style.display="none"
	/* document.getElementsByClassName("inline")[idx].parentNode.removeChild(document.getElementsByClassName("inline")[idx]); */

	for(var i=0;i<len;i++) {
	if(document.getElementsByClassName("recommend")[i].style.display=="none")
		cnt++;
	}
	if(cnt==len){
		
		$.ajax({
			url:"/recommend",
			dataType:"json",
			data:{update1:m[0],update2:m[1],update3:m[2],user:u,director1:d[0],director2:d[1],director3:d[2],release1:r[0],release2:r[1],release3:r[2]},
			async: false
		}).done(function(data){
			alert("추천")

		});
		 location.reload();
	}
	cnt=0;
}
</script>
</head>
<body>
<div class="logo">
			<a href="/">영화 분석 사이트</a>
		</div>
		<div class="nav">
			<ul onclick="nav(this)">
				<li><a href="/board">게시판</a></li>
				<li><a href="/search">키워드 분석</a></li>
				<li><a href="/recommend?user=<%=uid%>" class="navClick")>영화 추천</a></li>
				<li><a href="/rate">평점 분석</a></li>
			</ul>
			<form id="content" class="textright">
<%-- 							<%String user= (String) request.getAttribute("user"); %> --%>
				
				<%if(session.getAttribute("login")==null) {%><input type="hidden" name="flag" value="1"><button type="submit"  formaction="/loginpage" value="login"  onclick="logout() " class="login"><i class="fas fa-sign-in-alt"></i></button><%
				}else{%><input type="hidden" name="flag" value="0"><button type="submit" formaction="/submitlogout" id="logout" value="logout" class="login"><i class="fas fa-sign-out-alt"></i></button><% }%>
				<%if(session.getAttribute("login")!=null){%><%=user.getNickname()%><%} %>
				<%if(session.getAttribute("login")==null) {%><button  type="submit" formaction="/join" class="login"><i class="fas fa-user-plus"></i></button><%
				}else{%><button type="submit" formaction="/mypage" method="post" id="mypage" value="mypage"><i class="fas fa-user"></i></button><% }%>


			</form>
		</div>
		
<%if(movie!=null) { 
for(int i=0;i<movie.size();i++){%>
<div class="recommend">
<input type="checkbox" onclick="check(<%=i%>)">
<img src="<%=movie.get(i).getImage()%>" alt="movie" width=110; height=150;>
<p>제목:<%=movie.get(i).getTitle()%></p>
<p>감독:<%=movie.get(i).getDirector() %>
<p>배우:<%=movie.get(i).getActor() %>
</div><%} 
}else{%>
	추천할 영화가 없습니다.
<%}%>

	<div class="footer">
		<ul>
			<li>tel:010-1111-2222</li>
			<li>address:경기도 군포시</li>
			<li>Copyright@Bong Corp.All rights reserved.</li>
		</ul>
	</div>
	
	
</body>
</html>