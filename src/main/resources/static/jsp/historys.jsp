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
    
    <title>My JSP 'historys.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="js/echarts.common.min.js"></script> 
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		
		function chan(id) {
			var i =$("#"+id).val();
			$("#equipment").val(i);
			$("#form1").submit();
		}
		function update() {
			document.getElementById("form1").action="history/update.do?";
			$("#form1").submit();
		}
	</script>
  </head>
  
  <body style="background-color:#8aade9; ">
   	<div align=center>
   		<h1>石大赛普科技</h1>
   		<form action="history/history.do" id = "form1">
   		<input type="hidden" id = "data" name = "data" value='history'>
   		<input type="hidden" id = "lid" name = "lid" value="${lid}" >
   		<input type="hidden" id = "val" name = "val" value="${device}" >
   		<input type="hidden" id = "idNum"   name = "idNum" value="${id }" >
   		<input type="hidden" id = "equipment" name = "equipment" value="<c:if test="${equipment==null }">1</c:if>${equipment}" >
   		</form>
   			<div>
   				<select id="upval" onchange="chan('upval');">
   					<option value="1" <c:if test="${equipment==1 }">selected="selected"</c:if>>1小时</option>
   					<option value="2"<c:if test="${equipment==2 }">selected="selected"</c:if>>2小时</option>
   					<option value="3"<c:if test="${equipment==3 }">selected="selected"</c:if>>3小时</option>
   					<option value="4"<c:if test="${equipment==4 }">selected="selected"</c:if>>4小时</option>
   					<option value="5"<c:if test="${equipment==5 }">selected="selected"</c:if>>5小时</option>
   					<option value="6"<c:if test="${equipment==6 }">selected="selected"</c:if>>6小时</option>
   					<option value="7"<c:if test="${equipment==7 }">selected="selected"</c:if>>7小时</option>
   					<option value="8"<c:if test="${equipment==8 }">selected="selected"</c:if>>8小时</option>
   					<option value="9"<c:if test="${equipment==9 }">selected="selected"</c:if>>9小时</option>
   					<option value="10"<c:if test="${equipment==10 }">selected="selected"</c:if>>10小时</option>
   					<option value="11"<c:if test="${equipment==11 }">selected="selected"</c:if>>11小时</option>
   					<option value="12"<c:if test="${equipment==12 }">selected="selected"</c:if>>12小时</option>
   				</select>
   				<!-- <button onclick="update();">修改</button>  取消的修改-->
   				<br>
   				
   			</div><br>
   			<c:if test="${f}"><h1 align=center>暂时没有数据！</h1></c:if><br>
   		<div id="main" style="width: 1000px;height:500px;"></div><br><br>
    	
   		</div>
   		
	<script type="text/javascript">
		
		option = {
	    tooltip: {
	        trigger: 'none',
	        axisPointer: {
	            type: 'cross'
	        }
	    },
	    legend: {
	    },
	    grid: {
	        top: 70,
	        bottom: 50
	    },
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {
	                alignWithLabel: true
	            },
	            axisLine: {
	                onZero: false,
	               
	            },
	            axisPointer: {
	                label: {
	                    formatter: function (params) {
	                        return '时间' + params.value
	                            + (params.seriesData.length ? '：' + params.seriesData[0].data : '');
	                    }
	                }
	            },
	            
	            data: ${data}
	        },
	        {
	            type: 'category'
	           }
	    ],
	    yAxis: [
	        {
	            type: 'value'
	        }
	    ],
	    series: [<c:forEach items='${list}' var='l'>{name:'${l.id}',type:'line',xAxisIndex: 1,smooth: true,data: ${l.val}},</c:forEach>]
	};
	
		var a = echarts.init(document.getElementById('main')).setOption(option)
	    	
    </script>
  </body>
</html>
