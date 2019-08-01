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
    
    <title>My JSP 'listUpdate.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->	
	<script type="text/javascript">
		function update1(name) {
			var a = $("input[name='id']:checked").length;
			var data =document.getElementById("n").value+":";
			for (var int = 0; int < a; int++) {
				var c = $("input[name='id']:checked")[int].value
				data +=c+":";
			}
			data = data.substring(0,data.length-1);
			var l = document.getElementById("data").value;
			location.href="monitor/update2.do?id="+data+"&name2="+l+"&name="+name;
		}
	
	</script>

  </head>
  
  <body>
  
  	
    <div align="center">
    	<div style="width:300px; height:300px; overflow-y:scroll;overflow-x:scroll;">
    		<tabe>
    		<c:forEach items="${list }" var="l">
    		<tr>
    			<td><input type="checkbox" value="${l.id}" name="id"  <c:if test="${l.f1}">checked</c:if>></td>
    			<td><span>${l.name}</span></td>
    		</tr>
    		<br>
    		<input type="hidden" id="data" value="${l.bid},${l.sonid}">
    		</c:forEach>
    	</tabe>
    	</div>
    	
     	<input type="hidden" id="n" value="${n}">
    	<button onclick="update1('${name}');">提交</button>
    </div>
  </body>
</html>
