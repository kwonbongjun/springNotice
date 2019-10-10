<%@page import="com.java.web.bean.FileBean"%>
<%@page import="com.java.web.bean.Bean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../../resources/css/springNotice.css">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!--  -->
<script>

<%
	Bean detail=(Bean) request.getAttribute("detail");	
	List<FileBean> fb= (List<FileBean>) request.getAttribute("file");
	String img = (String) request.getAttribute("img");
	String title;
	String val;
	int no;
	String filename;
	String nmwriter=(String) request.getAttribute("writer");
	String writer;
	if(detail==null){
		title="";
		val="";
		no=Integer.parseInt(request.getParameter("boardNum"));
		filename="";
		writer="";
	}else{
		no=detail.getNo();
		title=detail.getTitle();
		val=detail.getVal();
		writer=detail.getWriter();
	}
	
%>
var dt = new DataTransfer();
function load() {
	document.getElementById("title").value=<%="\""+title+"\""%>;
	document.getElementById("val").value=<%="\""+val+"\""%>;
	document.get
}
function formList() {
	console.log(dt);
}
function file_Event(obj){
	console.log(obj.files);
	for (var i =0; i< obj.files.length;i++) {
		var fileName = obj.files[i].name;
		var ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
		console.log(fileName,ext);
		dt.items.add(obj.files[i]);
	}
	//obj.files = dt.files;
	//console.log(obj.files); 
} 
function formCheck() {
	if(document.getElementById("title").value==""
			|| document.getElementById("val").value=="" ) {
		return false;
	}
}

</script>
</head>
<body onload="load()">

	<div id="write" class="container">
		<form method="POST" enctype="multipart/form-data">
			<input class="form-control" type="hidden" name="no" value="<%=no%>">
			<div>제목:<input id="title" class="form-control" type="text" name="title"  required></div>
			<div>내용:<input id="val" class="text" type="text" name="val"  required></div>
			<input type="hidden" name="writer" value="<%=nmwriter%>">
			<div contentEditable="true" id="val2">
				<%if(fb!=null && img!=null) {for (int i=0;i<fb.size();i++) {%>
			<% if(detail!=null && img.equals("true")){%><img src="/board/download?boardnum=<%=no%>&filename=<%=fb.get(i).getFilename()%>" alt="이미지" ><%} %>
			<%}} %>
			</div>
			<div>
			<%if(fb!=null) {for (int i=0;i<fb.size();i++) {%>
			<% if(detail!=null){%><a href="/board/download?boardnum=<%=no%>&filename=<%=fb.get(i).getFilename()%>"><%=fb.get(i).getFilename()%></a><%} %>
			<%}} %>
			<input id="file" type="file" name="file" multiple="multiple"  onchange="file_Event(this)">
			<%if(session.getAttribute("login")!=null && detail==null){%><input type="submit"  formaction="/board/create" value="추가" method="POST" onclick="return formCheck()"><%}%>
			<%if(session.getAttribute("login")!=null && (writer.equals(nmwriter) || nmwriter.equals("admin"))){%><input type="submit" formaction="/board/update" value="업데이트" method="POST" onclick="return formCheck()"><%}%>
			<%if(session.getAttribute("login")!=null && (writer.equals(nmwriter) || nmwriter.equals("admin"))){%><input type="submit" formaction="/board/delete" value="삭제" method="POST"><%}%>
			<button type="submit" formaction="/board" onclick="load()">뒤로가기</button>
			</div>
		</form>
	</div>
</body>
</html>