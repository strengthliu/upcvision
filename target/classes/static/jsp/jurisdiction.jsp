<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'jurisdiction.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		function give(id) {
			var a = $("input[name='Fruit']:checked").length;
			var data = "";
			for (var int = 0; int < a; int++) {
				var c = $("input[name='Fruit']:checked")[int].value
				data +=c+":";
			}
			data = data.substring(0,data.length-1);
			var f = $("input[id='f']:checked").length;
			location.href="account/updateJ.do?id="+id+"&data="+data+"&f="+f;
		}
		function dian(name) {
			var a =  document.getElementsByName("sy").length;
			for (var int = 0; int < a; int++) {
				document.getElementsByName("sy")[int].style.display="none";
			}
			if (document.getElementById(name).style.display=="none") {
				document.getElementById(name).style.display="";
			}else {
				document.getElementById(name).style.display="none";
			}
		}
	
	</script>

  </head>
  
  <body style="background-color:#8aade9; ">
  	<h1 align="center">石大赛普科技</h1>
  	
  	<div align="center">
    	<span style="color: red">${snn}</span><br>
  		<c:forEach items="${list }" var="l">
  			
  			<text style="color:#096b25;" onclick="dian('${l.name}')">${l.name }</text>
  			
  			
  		</c:forEach>
  		<c:forEach items="${list }" var="l">
  			
  			
  			<div id = "${l.name}" name  = "sy" style="width:300px; height:300px; overflow-y:scroll;overflow-x:scroll; display: none;">
  				<c:forEach items="${l.bid }" var="j">
  					
  					<label><input name="Fruit" type="checkbox" value="${j.id }" <c:if test="${j.f }">checked="${j.f }"</c:if> />${j.name }</label>
  					<br>
  				</c:forEach>
  			</div>
  			
  		</c:forEach>
  		<br>
  		
  		<button onclick="give('${id}');">提交</button>
  	</div>
  </body>
</html>
