<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function add() {
			location="login/add.do";
		}
	</script>
  </head>
  
  <body style="background:url('jpg/bg.jpg')">
  <form action="login/login.do" method="post">
  	<div align=center style="width:1570px; height:750px;" >
    	<h1>石大赛普公司</h1>
    	<span style="color: red">${sse}</span><br>
    	账号：<input align=center type="text" id = "name" name ="name" ><br>
    	<br>
   		密码：<input align=center type="password" id = "pwd" name = "pwd"><br>
   		<br>
   			<input align=center type="submit" value="登录">
	</div>
	</form>
  </body>
</html>
