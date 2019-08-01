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
    
    <title>My JSP 'option.jsp' starting page</title>
    
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
		function tonum() {
			var num = $("input[name='in']:checked").length;
			$("#spid").html(num);
		}
		function update() {
			var num = $("input[name='in']:checked").length;
			var id ="";
			if(num>0&&num<11){
				for (var int = num; int >0; int--) {
					var a = $("input[name='in']:checked")[int-1].value;
					id+=a+",";
				}
				id=id.substring(0, id.length-1)
				$("#data").val(id);
				$("#form1").submit();
			}else{
				var a = num<=0?"请至少选择一位数！":"请选择小于10位数！"
				alert(a)
				
			}
			
		}
		
	</script>

  </head>
  
  <body style="background-color:#8aade9; ">
  	<h2 align=center>请选择你所要显示的数据</h2>
  	<form action="directreporting/Update.do" id = "form1"name = "form1">
  		<input type="hidden" id = "name" name = "name" value="${name}">
  		<input type="hidden" id = "id" name = "id" value="${id }">
  		<input type="hidden" id = "val" name = "val" value="${val }">
  		<input type="hidden" id = "data" name = "data" value="">
  	</form>
  	<div align=center>
  		已选择中<span id = "spid">0</span>个<br>
    	<div style="width:300px; height:300px; overflow-y:scroll;overflow-x:scroll;">
    		<c:forEach items="${list}" var="l">
    			<span><input type="checkbox" name="in" value="${l.pwd}" onclick="tonum();">${l.name}</span><br>
    		</c:forEach>
    		
    	</div>
    	<button onclick="update();">提交</button>
    </div>
  </body>
</html>
