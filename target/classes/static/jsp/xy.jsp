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
    
    <title>My JSP 'xy.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="js/echarts.common.min.js"></script> 
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function test() {
			var date = document.getElementById("date").value;
			var date2 = document.getElementById("date2").value;
			
			
			
			if(window.ActiveXObject || "ActiveXObject" in window){
			  	 	var id = $("#id").val();
					var name = $("#name").val();
			  		location.href="xytu.do?id="+id+"&name="+name+"&date2="+date+"&date3="+date2;
			}else{
					var da = date.split("T");
					var da2 = date2.split("T");
					date = da[0]+" "+da[1]+":00";
					date2 = da2[0]+" "+da2[1]+":00";
					alert(date.length()+"::"+date2.length());
					var id = $("#id").val();
					var name = $("#name").val();
						location.href="xytu/xytu.do?id="+id+"&name="+name+"&date2="+date+"&date3="+date2;
					}
		}
		function chan(id) {
			var date =$("#"+id).val();
			
			if (date>4) {
				var f = confirm("时间大于2小时数据会过于密集，是否确认操作？");
				if(f){
					var id = $("#id").val();
					var name = $("#name").val();
					
					if(window.ActiveXObject || "ActiveXObject" in window){
			  	 		//是ie
			  			location.href="xytu.do?id="+id+"&name="+name+"&date="+date ;
					}else{
						//不是ie
						location.href="xytu/xytu.do?id="+id+"&name="+name+"&date="+date ;
					}
				}
				
			}else{
					var id = $("#id").val();
					var name = $("#name").val();
					if(window.ActiveXObject || "ActiveXObject" in window){
			  	 		//是ie
			  			location.href="xytu.do?id="+id+"&name="+name+"&date="+date ;
					}else{
						//不是ie
						location.href="xytu/xytu.do?id="+id+"&name="+name+"&date="+date ;
					}
			}
			
		}
	
	</script>
  </head>
  
  <body>
  	<input type="hidden" id = "id"  value="${id}" >
   	<input type="hidden" id = "name"  value="${name}" >
  	<div  align=center >
  		<select id="upval" onchange="chan('upval');">
   			<option value="1" <c:if test="${date==1 }">selected="selected"</c:if>>1小时</option>
   			<option value="2"<c:if test="${date==2 }">selected="selected"</c:if>>2小时</option>
   			<option value="3"<c:if test="${date==3 }">selected="selected"</c:if>>3小时</option>
   			<option value="4"<c:if test="${date==4 }">selected="selected"</c:if>>4小时</option>
   			<option value="5"<c:if test="${date==5 }">selected="selected"</c:if>>5小时</option>
   			<option value="6"<c:if test="${date==6 }">selected="selected"</c:if>>6小时</option>
   			<option value="7"<c:if test="${date==7 }">selected="selected"</c:if>>7小时</option>
   			<option value="8"<c:if test="${date==8 }">selected="selected"</c:if>>8小时</option>
   			<option value="9"<c:if test="${date==9 }">selected="selected"</c:if>>9小时</option>
   			<option value="10"<c:if test="${date==10 }">selected="selected"</c:if>>10小时</option>
   			<option value="11"<c:if test="${date==11 }">selected="selected"</c:if>>11小时</option>
   			<option value="12"<c:if test="${date==12 }">selected="selected"</c:if>>12小时</option>
   		</select><br>	从<input id="date" type="datetime-local" value="${date2 }"/>到<input id="date2" type="datetime-local" value="${date3 }"/><br>
    	<text>< 非H5例：2019-04-26 16:05:00></text><br>
    	<button onclick="test();">提交</button>
   	</div><br>
   
   	<div align=center id="main" style="width: 1000px;height:500px;"></div>
  	
    <script type="text/javascript">
    	option = {
    
     		tooltip : {
        	// trigger: 'axis',
        	showDelay : 0,
        	formatter : function (params) {
        	    if (params.value.length > 1) {
        	        return params.seriesName + ' :<br/>'
           		    + params.value[0] + ';'
                	+ params.value[1] + ' ';//标点显示具体内容
            	}
            
       		},
        	axisPointer:{
            	show: true,
            	type : 'cross',
            	lineStyle: {
            	    type : 'dashed',
                	width : 1
            	}
        	}
    		},
    		toolbox: {
        		feature: {
            		dataZoom: {},
            		brush: {
            	    	type: ['rect', 'polygon', 'clear']//右上角缩放
            		}
        		}
    		},
    		 dataZoom: [{
        type: 'inside'
    }, {
        type: 'slider'
    }],
    		xAxis: {},
    		yAxis: {},
    		series: [{
        		symbolSize: 5,
        		data: [<c:forEach items='${list}' var='l'>[${l.pwd},${l.name}],</c:forEach>
        		],
        		type:'line'
    		}]
		};

    	var a = echarts.init(document.getElementById('main')).setOption(option)
    </script>
  </body>
</html>
