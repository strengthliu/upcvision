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
    
    <title>My JSP 'detailed.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>

  </head>
  
  <body style="background-color:#8aade9; ">
  	<form action="account/update2.do">
  		<div  align=center >
  				
  				<input type="hidden" id = "id" name = "id" value="${id }"><br> 
   			 账号：<input type="text" id = "name" name = "name" value="${name }"><br>
   			 密码：<input type="password" id = "pwd" name = "pwd" value="${pwd }" disabled="disabled"><br>
   			用户等级： <select id="upval" name="upval">
   					<option value="1" <c:if test="${num==1}">selected="selected"</c:if>>管理员用户</option>
   					<option value="2"<c:if test="${num==2}">selected="selected"</c:if>>组态用户</option>
   					<option value="3"<c:if test="${num==3}">selected="selected"</c:if>>普通用户</option>
   				</select><br>
   			 修改密码：<input type="text" id = "pwd2" name = "pwd2" value=""><br>
   			<input type="submit" value="确认"><br>
   			<span style="color: red">${snn}</span>
   	 	</div>
   	 </form>
   	 
  </body>
</html>
