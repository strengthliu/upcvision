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
    
    <title>My JSP 'libiao1.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		function f1(name) {
			if(window.ActiveXObject || "ActiveXObject" in window){
			   //是ie
			   location.href="toAdd.do?name="+name;
			}else{
				//不是ie
				location.href="monitor/toAdd.do?name="+name;
			}
			
		}
		function shanchu(name) {
			var num = $("input[name='id']:checked").length;
			var id ="";
			if(num>0&&num<11){
				for (var int = num; int >0; int--) {
					var a = $("input[name='id']:checked")[int-1].value;
					id+=a+",";	
					}	
					id=id.substring(0, id.length-1)	
					
					if(window.ActiveXObject || "ActiveXObject" in window){
			   			//是ie
			   			location.href="Delete.do?id="+id+"&name="+name;
					}else{
						//不是ie
						location.href="monitor/Delete.do?id="+id+"&name="+name;
					}
				}else{
					alert("未选中")
				}
		}
		function update(pwd,name) {
			location.href="monitor/update.do?name="+name+"&id="+pwd; 
		}
		function update2(pwd,name,name2) {
			location.href="monitor/update3.do?name="+name+"&id="+pwd+"&name2="+name2;
		}
		function toi(name,name2) {
			if(name2=="chart"){//视图列表
				parent.document.getElementById("myrame2").src="chart/chart.do?id="+name+"&name="+name2;
			}else if(name2=="monitor"){//实时数据
				parent.document.getElementById("myrame2").src="monitor/monitor.do?id="+name+"&name="+name2;
			}else if(name2=="vigilance"){//报价查询
				parent.document.getElementById("myrame2").src="vigilance/vigilance.do?id="+name+"&name="+name2;
			}else if(name2=="history"){//历史数据    
				parent.document.getElementById("myrame2").src="history/history.do?id="+name+"&name="+name2;
			}else if(name2=="directreporting"){//直线警报
				parent.document.getElementById("myrame2").src="directreporting/directreporting.do?id="+name+"&name="+name2;
			}else if(name2=="bacc"){//用户管理
				parent.document.getElementById("myrame2").src="account/account.do?id="+name+"&name="+name2;
			}
		}
	</script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body style="background-color:#8aade9; ">
  <div align="center"><c:if test="${qx<3}"><img src="images/btn_add.gif" onclick="f1('${name}');"> <img src="images/btn_del.gif"  onclick="shanchu('${name}');"></c:if></div>
  <div align="center">
  <table width="70%">
   	<c:forEach items="${list}" var="l">
   		<tr>
   		<td><input type="checkbox" name = "id" value="${l.pwd}"><a href="javascript:void(0)" onclick="toi('${l.pwd }','${name}');">${l.name}</a><br></td>
   		<td><c:if test="${qx<2}"><img src="images/btn_power_over.gif" onclick="update('${l.pwd}','${name}')"></c:if></td>
   		
   		<td><c:if test="${qx<2}"><img src="images/btn_modify_over.gif" onclick="update2('${l.pwd}','${name}','${l.name}')"></c:if></td>
   		<tr/>
   	</c:forEach>
   	</table>
   </div>
  </body>
</html>
