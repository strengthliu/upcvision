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
    
    <title>My JSP 'left.jsp' starting page</title>
    
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
		function png(name,f) {
			if(document.getElementById(name).style.display=="none"){
				document.getElementById(name).style.display="";
				document.getElementsByName(name)[0 ].style.background= "url('images/nav_bg_heading_selected.png') no-repeat";
				var f1 = f=="true";
				if (f1) {
						if(name=="chart"){//视图列表
						//parent.document.getElementById("myrame2").src="chart/chart.do?id="+name;
					}else if(name=="monitor"){//实时数据
						
						parent.document.getElementById("myrame2").src="monitor/list.do?id="+name;
					}else if(name=="vigilance"){//报警查询
						parent.document.getElementById("myrame2").src="monitor/list.do?id="+name;
					}else if(name=="history"){//历史数据    
						parent.document.getElementById("myrame2").src="monitor/list.do?id="+name;
					}else if(name=="directreporting"){//直线警报
						parent.document.getElementById("myrame2").src="monitor/list.do?id="+name;
					}else if(name=="bacc"){//用户管理
						//parent.document.getElementById("myrame2").src="account/account.do?id="+name+"&name="+name2;
					}else if(name=="xytu"){
						parent.document.getElementById("myrame2").src="monitor/list.do?id="+name;
					}
				}
				
			}else{
				document.getElementById(name).style.display="none";
				document.getElementsByName(name)[0].style.background= "url('images/nav_bg_heading.png') no-repeat";
				
			}
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
			}else if(name2=="xytu"){
				parent.document.getElementById("myrame2").src="xytu/xytu.do?id="+name+"&name="+name2;
			}
			
			
		}
		
		
	</script>
  </head>
  
  <body style="background-color:#8aade9; ">
  	<c:forEach items="${name }" var="l">
  		
  		<div style="background:url('images/nav_bg_heading.png') no-repeat"<c:if test="${l.pwd =='chart' }">background:url('images/nav_bg_heading.png') no-repeat</c:if> name="${l.pwd}" onclick="png('${l.pwd}','${f}');">
  			${l.name}  
  		</div>
  		
  		<div id="${l.pwd }"<c:if test="${l.pwd !='chart' }">style="display: none;"</c:if>>
  		<c:forEach items="${requestScope[''.concat(l.pwd)]}" var="j">
  			
  			<img alt="" src="images/ball_green.gif">
  			<a href="javascript:void(0)" onclick="toi('${j.pwd}','${l.pwd }');">
  				${j.name }
  			</a>
  				
  			<br>
  		</c:forEach>
  		</div>
    </c:forEach>
  </body>
</html>
