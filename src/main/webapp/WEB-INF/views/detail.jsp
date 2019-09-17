<%@page import="com.java.web.FileBean"%>
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
	List<FileBean> fb= (List<FileBean>) request.getAttribute("file");
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
</script>
</head>
<body onload="load()">

	<div id="write">
		<form method="POST" enctype="multipart/form-data">
			<input type="hidden" name="no" value="<%=no%>">
			<input id="title" type="text" name="title"  required>
			<input id="val" class="text" type="text" name="val"  required>
			<input type="hidden" name="writer" value="<%=nmwriter%>">
			<%if(fb!=null) {for (int i=0;i<fb.size();i++) {%>
			<% if(detail!=null){%><a href="/download?boardnum=<%=no%>&filename=<%=fb.get(i).getFilename()%>"><%=fb.get(i).getFilename()%></a><%} %>
			<%}} %>
			<input id="file" type="file" name="file" multiple="multiple"  onchange="file_Event(this)">
			<%if(session.getAttribute("login")!=null && detail==null){%><input type="submit"  formaction="/create" value="추가" method="POST"><%}%>
			<%if(session.getAttribute("login")!=null && (writer.equals(nmwriter) || nmwriter.equals("admin"))){%><input type="submit" formaction="/update" value="업데이트" method="POST"><%}%>
			<%if(session.getAttribute("login")!=null && (writer.equals(nmwriter) || nmwriter.equals("admin"))){%><input type="submit" formaction="/delete" value="삭제" method="POST"><%}%>
			<button type="submit" formaction="/" onclick="load()">뒤로가기</button>
		</form>
	</div>
</body>
</html>