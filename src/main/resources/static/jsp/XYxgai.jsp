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
    
    <title>My JSP 'xgai.jsp' starting page</title>
    
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
		function tijiao(name) {
			var num = $("input[name='id']:checked").length;
			var id ="";
			if(num=2){
				for (var int = num; int >0; int--) {
					var a = $("input[name='id']:checked")[int-1].value;
					id+=a+",";
				}
				id=id.substring(0, id.length-1)
				
				var mz = document.getElementById("mz").value;
				alert(mz);
					if(window.ActiveXObject || "ActiveXObject" in window){
			   			//是ie
			   			location.href="Add.do?id="+id+"&name="+name+"&mz="+mz;
					}else{
						//不是ie
						location.href="monitor/Add.do?id="+id+"&name="+name+"&mz="+mz;
					}
				
				
			}else{
				var a = "请选择两位数！你选择了"+num+"位！";
				alert(a)
				
			}
			
		}
	</script>

  </head>
  
  <body style="background-color:#8aade9; ">
    <div align="center" >
    	<c:forEach items="${list}" var="l">
    	
    	<text onclick="dian('${l.name}')">${l.name}</text>
    	<div id = "${l.name}" name  = "sy" style="width:300px; height:300px; overflow-y:scroll;overflow-x:scroll; display: none;">
    		<c:forEach items="${l.bid}" var="j">
    		
    				<input type="checkbox" name = "id" value="${j.id}" <c:if test="${j.f}">checked</c:if>>${j.name}<br>
    				
    		</c:forEach>
    	</div><br>
    	</c:forEach>
    	<h4>名称：</h><input id = "mz" value="${name2}"><br>
    	<button onclick="tijiao('${name}');">提交</button>
    </div>

  </body>
</html>
