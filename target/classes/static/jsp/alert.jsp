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
    
    <title>My JSP 'alert.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
	var ws;
		var target = "ws://localhost:8081/xiangmu/echo";
		f();
		
		function f2() {
				
			for (var i = 1; i <=${num}; i++) {
			var id = "c"+i;
			var a =document.getElementById(id).value;
			name(a);
			}
		}
		function name(id) {
			
			if(ws.readyState == 1) {	
				ws.send(id);
			}
			setTimeout(function(){ 
			name(id);
   			}, 1500);
		}
		
		function f() {
			if("WebSocket"in window){
				ws = new WebSocket(target);
				
			}else if("MozWebSocket" in window){
				ws = new MozWebSocket(target);
				
			}else{
				alert("不支持您使用的浏览器");
				return
			}
			
			ws.onmessage = function(event) {
				var a = event.data.split(":");
				document.getElementById(a[0]).innerHTML=a[1];
				
			};
			
			
		}
		function toal() {
			
			try {
				location.href="alert"
				setTimeout(function(){ 
				toal()
   				}, 8000);
			} catch (e) {
			e.prototype;
			}finally{
				ws.close();
			}
		}
		function update() {
			document.getElementById("form1").action="directreporting/update.do?";
			$("#form1").submit();
		}
		
		</script>
  </head>
  <body onload="f2();" style="background-color:#8aade9; ">
  
    <h1 align=center>石大赛普科技</h1>
    <form action="" id = "form1" name = "form1" >
    	<input type="hidden" id = "val" name = "val" value="${val }">
    	<input type="hidden" id = "name" name = "name" value="${name }">
    	<input type="hidden" id = "id" name = "id" value="${id }">
    	
    </form>
    <div>
  	</div><br>
    <div align=center>
    	<table width="70%">
    		<tr style="background-color:#4468a6">
    			<td width="20%">序号</td>
    			<td width="20%">号位</td>
    			<td width="20%">说明</td>
    			<td width="20%">当前值</td>
    			<td width="20%">状态</td>
    		</tr>
    		<c:forEach items="${list }" var="l" varStatus="a">
    			<tr style="background-color:#8894a7">
    				<td width="20%" >${l.id }</td>
    				<td width="20%">${l.position }</td>
    				<td width="20%">${l.explain }</td>
 	   				<td width="20%" id="${l.id }" name = "${l.id }">${l.value }<input type="hidden" id='c${a.count}' value='${l.id}' ></td>
    				<td width="20%">${l.status }</td>
    			</tr>
    		</c:forEach>
    			
    			
    			
    	</table>
    	
    </div>
    
  </body>
</html>
