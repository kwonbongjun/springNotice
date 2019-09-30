<%@page import="com.java.web.bean.Movie"%>
<%@page import="com.java.web.bean.Login"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>  https://bootsnipp.com/
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="/resources/css/springNotice.css">
<script src="https://kit.fontawesome.com/a076d05399.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Load d3.js -->
<script src="https://d3js.org/d3.v4.js"></script>

<!-- Color palette -->
<script src="https://d3js.org/d3-scale-chromatic.v1.min.js"></script>
<!-- Load d3-cloud -->
<script src="https://cdn.jsdelivr.net/gh/holtzy/D3-graph-gallery@master/LIB/d3.layout.cloud.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<!--  -->

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<head>
<style>
.node:hover{
  stroke-width: 7px !important;
  opacity: 1 !important;
}

    body,html{
    height: 100%;
    width: 100%;
    margin: 0;
    padding: 0;
/*     background: #e74c3c !important; */
    }

    .searchbar{
    margin-bottom: auto;
    margin-top: auto;
    height: 60px;
    background-color: white;
    border-radius: 30px;
    padding: 10px;
    }

    .search_input{
    color: white;
    border: 0;
    outline: 0;
    background: none;
    width: 0;
    caret-color:transparent;
    line-height: 40px;
    transition: width 0.4s linear;
    }

    .searchbar:hover > .search_input{
    padding: 0 10px;
    width: 450px;
    /* caret-color:red; */
    transition: width 0.4s linear;
    }

    .searchbar:hover > .search_icon{
    background: white;
    color: #e74c3c;
    }

    .search_icon{
    height: 40px;
    width: 40px;
    float: right;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 50%;
    color:white;
    }
</style>


<%-- <script>
<%List<HashMap<String,Object>> jo = (List<HashMap<String,Object>>) request.getAttribute("data");%>
<%List<JSONObject> jo = (List<JSONObject>) request.getAttribute("data");%>
var data=<%=jo%>
<%System.out.println("j"+jo.size());%>
//set the dimensions and margins of the graph
var width = 450
var height = 450

//append the svg object to the body of the page
var svg = d3.select("#my_dataviz")
.append("svg")
 .attr("width", 450)
 .attr("height", 450)

//create dummy data -> just one element per circle
//var data = [{ "name": "A" }, { "name": "B" }, { "name": "C" }, { "name": "D" }, { "name": "E" }, { "name": "F" }, { "name": "G" }, { "name": "H" }]

console.log(data);
var node = svg.append("g")
.selectAll("circle")
.data(data)
.enter()
.append("circle")
  .attr("r", 25)
  .attr("cx", width / 2)
  .attr("cy", height / 2)
  .style("fill", "#69b3a2")
  .style("fill-opacity", 0.3)
  .attr("stroke", "#69a2b2")
  .style("stroke-width", 4)

//Features of the forces applied to the nodes:
var simulation = d3.forceSimulation()
  .force("center", d3.forceCenter().x(width / 2).y(height / 2)) // Attraction to the center of the svg area
  .force("charge", d3.forceManyBody().strength(0.5)) // Nodes are attracted one each other of value is > 0
  .force("collide", d3.forceCollide().strength(.01).radius(30).iterations(1)) // Force that avoids circle overlapping

//Apply these forces to the nodes and update their positions.
//Once the force algorithm is happy with positions ('alpha' value is low enough), simulations will stop.
simulation
  .nodes(data)
  .on("tick", function(d){
    node
        .attr("cx", function(d){ return d.x; })
        .attr("cy", function(d){ return d.y; })
  });


</script> --%>
 <%-- ${data} --%>

 
 <%-- <script>
 <%List<JSONObject> jo = (List<JSONObject>) request.getAttribute("data");%>
 var data=<%=jo%>
// set the dimensions and margins of the graph
var width = 460
var height = 460

// append the svg object to the body of the page
var svg = d3.select("#my_dataviz")
  .append("svg")
    .attr("width", width)
    .attr("height", height)

// Read data
//d3.csv("https://raw.githubusercontent.com/holtzy/data_to_viz/master/Example_dataset/11_SevCatOneNumNestedOneObsPerGroup.csv", function(data) {

  // Filter a bit the data -> more than 1 million inhabitants
  data = data.filter(function(d){ return d.value>10000000 })

  // Color palette for continents?
  var color = d3.scaleOrdinal()
    .domain(["Asia", "Europe", "Africa", "Oceania", "Americas"])
    .range(d3.schemeSet1);

  // Size scale for countries
  var size = d3.scaleLinear()
    .domain([0, 1400000000])
    .range([7,55])  // circle will be between 7 and 55 px wide

  // create a tooltip
  var Tooltip = d3.select("#my_dataviz")
    .append("div")
    .style("opacity", 0)
    .attr("class", "tooltip")
    .style("background-color", "white")
    .style("border", "solid")
    .style("border-width", "2px")
    .style("border-radius", "5px")
    .style("padding", "5px")

  // Three function that change the tooltip when user hover / move / leave a cell
  var mouseover = function(d) {
    Tooltip
      .style("opacity", 1)
  }
  var mousemove = function(d) {
    Tooltip
      .html('<u>' + d.key + '</u>' + "<br>" + d.value + " inhabitants")
      .style("left", (d3.mouse(this)[0]+20) + "px")
      .style("top", (d3.mouse(this)[1]) + "px")
  }
  var mouseleave = function(d) {
    Tooltip
      .style("opacity", 0)
  }

  // Initialize the circle: all located at the center of the svg area
  var node = svg.append("g")
    .selectAll("circle")
    .data(data)
    .enter()
    .append("circle")
      .attr("class", "node")
      .attr("r", function(d){ return size(d.value)})
      .attr("cx", width / 2)
      .attr("cy", height / 2)
      .style("fill", function(d){ return color(d.region)})
      .style("fill-opacity", 0.8)
      .attr("stroke", "black")
      .style("stroke-width", 1)
      .on("mouseover", mouseover) // What to do when hovered
      .on("mousemove", mousemove)
      .on("mouseleave", mouseleave)
      .call(d3.drag() // call specific function when circle is dragged
           .on("start", dragstarted)
           .on("drag", dragged)
           .on("end", dragended));

  // Features of the forces applied to the nodes:
  var simulation = d3.forceSimulation()
      .force("center", d3.forceCenter().x(width / 2).y(height / 2)) // Attraction to the center of the svg area
      .force("charge", d3.forceManyBody().strength(.1)) // Nodes are attracted one each other of value is > 0
      .force("collide", d3.forceCollide().strength(.2).radius(function(d){ return (size(d.value)+3) }).iterations(1)) // Force that avoids circle overlapping

  // Apply these forces to the nodes and update their positions.
  // Once the force algorithm is happy with positions ('alpha' value is low enough), simulations will stop.
  simulation
      .nodes(data)
      .on("tick", function(d){
        node
            .attr("cx", function(d){ return d.x; })
            .attr("cy", function(d){ return d.y; })
      });

  // What happens when a circle is dragged?
  function dragstarted(d) {
    if (!d3.event.active) simulation.alphaTarget(.03).restart();
    d.fx = d.x;
    d.fy = d.y;
  }
  function dragged(d) {
    d.fx = d3.event.x;
    d.fy = d3.event.y;
  }
  function dragended(d) {
    if (!d3.event.active) simulation.alphaTarget(.03);
    d.fx = null;
    d.fy = null;
  }

//})
</script> --%>

<script>
$(document).ready(function(){$(".datepicker").datepicker({
        changeYear: true,
        showButtonPanel: true,
        dateFormat: 'yy',
        onClose: function(dateText, inst) { 
            var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
            $(this).datepicker('setDate', new Date(year, 1));
        }
	
	});

}); 
</script>
</head>
 	<%Movie[] movie=(Movie[]) request.getAttribute("movie"); %>
 	
  <body>

	<div class="main_blk tc">
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
  
    <div class="container h-100">
      <div class="d-flex justify-content-center h-100">
        <div >
        <form class="center">
          <%if(user!=null){ %><input type="hidden" nm="user_id" value="<%=user.getId()%>"><%} %>
<%--           <%if(movie!=null){ %><input type="hidden" nm="m_no" value="<%=movie.getTitle()%>"><%} %> --%>

  			<input type="text" name="sdate" id="datepicker" class=".ui-datepicker-calendar datepicker" placeholder="시작날짜"/>   

  			<input type="text" name="edate" id="datepicker" class=".ui-datepicker-calendar datepicker" placeholder="끝날짜"/>   

		  <select name = "nation">
		  	  <option value="">국가</option>
			  <option value="KR">Korea</option>
			  <option value="JP">America</option>
			  <option value="US">Japan</option>
			  <option value="HK">China</option>
		  </select>
		  <input class="searchbar" type="text" name="director" placeholder="director">
          <input class="searchbar" type="text" name="search" placeholder="Search...">
          <button type="submit" formaction="/collect" value=""><i class="fas fa-search"></i></button>
        </form>
        </div>
      </div>
    </div>
     <%-- <%List<HashMap<String,Object>> jo = (List<HashMap<String,Object>>) request.getAttribute("data");%> --%>
 <%-- <%List<JSONObject> jo = (List<JSONObject>) request.getAttribute("data");%> --%>
 <% String[] jo = (String[]) request.getAttribute("data");%>
<!-- Create a div where the graph will take place -->
<%if(movie!=null) { %><div class="inline w30"><% for(int i=0;i<movie.length;i++) {%>
<div class="">
<img src="<%=movie[i].getImage()%>" alt="movie" width=110; height=150;>
<p>제목:<%=movie[i].getTitle()%></p>
<p>감독:<%=movie[i].getDirector() %>
<p>배우:<%=movie[i].getActor() %>
<%if(request.getAttribute("isSetScore")==null && (user!=null)) {%>
<script>

var rstar;
function star(i) {
	rstar=i;
	for(var j=0;j<5;j++) {
		document.getElementsByClassName("star")[j].style.color="black"
	}
	for(var j=0;j<i;j++) {
		document.getElementsByClassName("star")[j].style.color="yellow"
	}
}


function setstar(user_id,m_no) {
	$.ajax({
		url:"/setstar",
		dataType:"json",
		data:{star:rstar,user_id:user_id,m_no:m_no}
	}).done(function(data){
		alert("평점을 주었습니다.");
	});
	alert("평점을 주었습니다.");
}
</script>

<%-- <form id="star">
	<div onclick="star(1,<%="\'"+user.getId()+"\'"%>,<%="\'"+movie.getTitle()+"\'"%>)" class="star">☆</div>
	<div onclick="star(2,<%="\'"+user.getId()+"\'"%>,<%="\'"+movie.getTitle()+"\'"%>)" class="star">☆</div>
	<div onclick="star(3,<%="\'"+user.getId()+"\'"%>,<%="\'"+movie.getTitle()+"\'"%>)" class="star">☆</div>
	<div onclick="star(4,<%="\'"+user.getId()+"\'"%>,<%="\'"+movie.getTitle()+"\'"%>)" class="star">☆</div>
	<div onclick="star(5,<%="\'"+user.getId()+"\'"%>,<%="\'"+movie.getTitle()+"\'"%>)" class="star">☆</div>
	<button type="button">등록</button>
</form> --%>
<form id="star">
	<div onclick="star(1)" class="star">☆</div>
	<div onclick="star(2)" class="star">☆</div>
	<div onclick="star(3)" class="star">☆</div>
	<div onclick="star(4)" class="star">☆</div>
	<div onclick="star(5)" class="star">☆</div>
	<button type="button" onclick="setstar(<%="\'"+user.getId()+"\'"%>,<%="\'"+movie[i].getTitle()+"\'"%>)">등록</button>
</form>
<%} %>
</div>
	<% }%>
	</div>
<%} %>
<%if(jo!=null) { %>

<div id="visual" class="inline w60"></div>	
 <script>

<%--  var myWord=<%=jo%> --%>
var myWords = new Array(10);
<%
 for(int i=0;i<jo.length;i++) {
	 
	 %>myWords[<%=i%>]=<%="\""+jo[i]+"\""%>
	 <%
 	}
 %>
 
 console.log(myWords);
// List of words
//var myWords = ["Hello", "Everybody", "How", "Are", "You", "Today", "It", "Is", "A", "Lovely", "Day", "I", "Love", "Coding", "In", "My", "Van", "Mate"]

// set the dimensions and margins of the graph
var margin = {top: 10, right: 10, bottom: 10, left: 10},
    width = 600 - margin.left - margin.right,
    height = 600 - margin.top - margin.bottom;

// append the svg object to the body of the page
var svg = d3.select("#visual").append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform",
          "translate(" + margin.left + "," + margin.top + ")");

// Constructs a new cloud layout instance. It run an algorithm to find the position of words that suits your requirements
var layout = d3.layout.cloud()
  .size([width, height])
  .words(myWords.map(function(d) { return {text: d}; }))
  .padding(10)
  .fontSize(60)
  .on("end", draw);
layout.start();

// This function takes the output of 'layout' above and draw the words
// Better not to touch it. To change parameters, play with the 'layout' variable above
function draw(words) {
  svg
    .append("g")
      .attr("transform", "translate(" + layout.size()[0] / 2 + "," + layout.size()[1] / 2 + ")")
      .selectAll("text")
        .data(words)
      .enter().append("text")
        .style("font-size", function(d) { return d.size + "px"; })
        .attr("text-anchor", "middle")
        .attr("transform", function(d) {
          return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
        })
        .text(function(d) { return d.text; });
}
</script>
<%}%>
  </body>
  