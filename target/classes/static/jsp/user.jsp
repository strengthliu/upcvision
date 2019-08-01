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
    
    <title>My JSP 'user.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		function toJurisdiction(id) {
			
			if(window.ActiveXObject || "ActiveXObject" in window){
				location.href="/account/jurisdiction.do?id="+id;
			}else{
				location.href="account/jurisdiction.do?id="+id;
			}
			
		}
		function todetailed(id) {
			
			if(window.ActiveXObject || "ActiveXObject" in window){
				location.href="/account/update.do?id="+id;
			}else{
				location.href="account/update.do?id="+id;
			}
			
		}
		function toAdd() {
			location="jsp/xinjian.jsp";
		}
		function Delete(name) {
			var num = $("input[name='in']:checked").length;
			var id ="";
			if(num>0&&num<11){
				for (var int = num; int >0; int--) {
					var a = $("input[name='in']:checked")[int-1].value;
					id+=a+",";
				}
				id=id.substring(0, id.length-1)
				location.href="account/Delete.do?id="+id+"&name="+name;
			}
		}
		
	</script>
  </head>
  
  <body style="background-color:#8aade9; ">
    <div align="center">
    	<div >
    			<img src="images/btn_add.gif" onclick="toAdd();"> 
    			<img src="images/btn_del.gif" onclick="Delete('${name}')">
    	</div>
    	<table width="70%">
    			<tr  style="background-color:#4468a6">
    				<td width="20%">位号</td>
    				<td width="20%">用户名</td>
    				<td width="20%">权限和设置</td>
    				
    			</tr>
    			<c:forEach items="${list }" var="l">
    				<tr style="background-color:#8894a7">
    					<td><input type="checkbox" name="in" value="${l.pwd}" onclick="tonum();">${l.pwd}</td>
    					<td>${l.name }</td>
    					<td><button onclick="todetailed('${l.pwd}');">用户修改</button><button onclick="toJurisdiction('${l.pwd}')">权限</button></td>
    					
    				</tr>
    			</c:forEach>
    		</table>
    		
    </div>
  </body>
</html>
