<%@page import="java.util.List"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta charset="utf-8">

<!-- Load d3.js -->
<script src="https://d3js.org/d3.v4.js"></script>

<!-- Color palette -->
<script src="https://d3js.org/d3-scale-chromatic.v1.min.js"></script>

<!-- Create a div where the graph will take place -->
<div id="my_dataviz"></div>

<style>
.node:hover{
  stroke-width: 7px !important;
  opacity: 1 !important;
}
</style>


<script>
<%List<JSONObject> jo = (List<JSONObject>) request.getAttribute("data");%>
//var data=<%=jo%>
//set the dimensions and margins of the graph
var width = 450
var height = 450

//append the svg object to the body of the page
var svg = d3.select("#my_dataviz")
.append("svg")
 .attr("width", 450)
 .attr("height", 450)

//create dummy data -> just one element per circle
var data = [{ "name": "A" }, { "name": "B" }, { "name": "C" }, { "name": "D" }, { "name": "E" }, { "name": "F" }, { "name": "G" }, { "name": "H" }]

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


</script>
 <%-- ${data} --%>