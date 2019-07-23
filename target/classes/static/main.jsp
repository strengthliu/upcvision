<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'main.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="viewport" content="width=1920, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		function tuichu() {
			
			
			  location="/xiangmu/index.jsp";
			
		}
		
	</script>
  </head>
  
  <body style="background:url('jpg/bg.jpg')">
  	<div align=right>
  	<text>用户:${name}</text><button onclick="tuichu();">退出</button>
  	</div>
    <div align=center style="width:400xp; height:200xp;">
		<img alt="" src="images/cartweb.png" style="margin: 0 auto;" />
    </div>
    	
    	<div align=center style="height: 100%; width: 100%">
    		<iframe align=center name="myiframe" id="myrame" src="left/left.do" frameborder="1" align="left" width="15%" height="700" style="background-color:#ffffff" >
       		</iframe>
   		 	<iframe align=center name="myiframe2" id="myrame2" src="cl.jsp" frameborder="1" align="left" width="80%" height="700"  style="background-color:#ffffff;" >
       	 	</iframe>
       	</div>
   	
  </body>
</html>
