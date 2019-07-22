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
    
    <title>My JSP 'xinjian.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		
		function sub() {
			var a = document.getElementById("quanxian").value;
			var name = document.getElementById("name").value;
			var pwd = document.getElementById("pwd").value;
			location="account/Add.do?id="+a+"&name="+name+"&pwd="+pwd;
		}
		
	</script>

  </head>
  
  <body>
    <div  align=center >
  				
  				<input type="hidden" id = "id" name = "id" ><br> 
   			 账号：<input type="text" id = "name" name = "name" ><br>
   			 密码：<input type="password" id = "pwd" name = "pwd" ><br>
   			<select id = "quanxian">
  				<option value ="3">普通用户</option>
  				<option value ="2">组态用户</option>
 	 			<option value="1">管理员</option>
			</select>
   			 <br>
   			<input type="submit" value="确认" onblur="sub();"><br>
   			<span style="color: red">${snn}</span>
   	 	</div>
  </body>
</html>
