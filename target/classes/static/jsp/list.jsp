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
    
    <title>My JSP 'list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		
		var ws;
		var target = "ws://localhost:8081/xiangmu/echo";//获得服务器
		f();
		
		function f2() {
				
			for (var i = 1; i <=10; i++) {
			var id = "c"+i;
			var a =document.getElementById(id).value;
			name(a);
			}
		}
		
		
		function f() {
			if("WebSocket"in window){//判断浏览器类型
				ws = new WebSocket(target);
				
			}else if("MozWebSocket" in window){
				ws = new MozWebSocket(target);
				
			}else{
				alert("不支持您使用的浏览器");
				return
			}
			
			ws.onmessage = function(event) {//通道提交数据返回后分配数据
				var a = event.data.split(":");
				document.getElementById(a[0]).innerHTML=a[1];
				
			};
			ws.onclose = function () {
			ws.close;
			
			};
			
		}
		function name(id) {//
			
			if(ws.readyState == 1) {//判断是否可以向通道提交数据
				
				ws.send(id);
			}
			
			
			setTimeout(function(){ //提交完以后固定时间提交数据
			name(id);
   			}, 1500);
		}
		function to(equipment){
			try {//获得设备名转跳
				document.getElementById("page").value="";
				document.getElementById("equipment").value=equipment;
				location.href="list";
			} catch (e) {
				
			}finally{
				ws.close();
			}
			
			
		}
		function topage(id) {
			try {
				//获得页码转跳
				
				document.getElementById("page").value=id;
				
				location.href="list";
			} catch (e) {
				
			}finally{
				ws.close();
			}
		}
		function topages() {
			try {//将输入的页码转跳
				var a = document.getElementById("pade").value;
				var patrn=/^(\w){1,3}$/;
				if(patrn.exec(a)){
					document.getElementById("page").value=a
					location.href="list";
				}else {
					alert("请输入1到3位数转跳!")
				}
				
			} catch (e) {
			}finally{
				ws.close();
			}
		}
		
	</script>
	
  </head>
  
  <body onload="f2();" style="background-color:#8aade9; ">
  	
  	
  	<br>
  	<form action="${pageContext.request.contextPath }/monitor/monitor.do" id = "form1" name = "form1">
  		
    <div align=center>
    		<c:if test="${page!=null}">
    	
    	<span>当前第${page.page }页/共${page.pagetotal }页</span>
    	<br>
    	<span>共${page.total }条数据</span>
    	<br>
    	<button onclick="topage(1)">首页</button>
    	<button onclick="topage(${page.page-1 })">上一页</button>
    	<input id = "pade">
    	<button onclick="topage(${page.page+1 })">下一页</button>
    	<button onclick="topage(${page.pagetotal})">末页</button><br>
    	<button onclick="topages();">转跳</button>
    	</c:if>
    	
    		<table width="70%">
    			<tr  style="background-color:#4468a6">
    				<td width="20%">服务器	</td>
    				<td width="20%">号位</td>
    				<td width="20%">说明</td>
    				<td width="20%">当前值</td>
    				<td width="20%">单位</td>
    			</tr>
    			<c:forEach items="${datas}" var="d" varStatus="a">
    			<tr style="background-color:#8894a7">
    				<td width="20%">${d.lpszServerName }</td>
    				<td width="20%">${d.number }</td>
    				<td width="20%">${d.explain }</td>
    				<td width="20%" id = "${d.id}">${d.value }<input type="hidden" id='c${a.count}' value='${d.id}' ></td>
    				
    				<td width="20%">${d.company }</td>
    			</tr>
    			</c:forEach> 
    		</table>
    	
    </div>
    
    
    <input type="hidden"  id = "page" name = "page" value="${page.page }" ><!-- 当前页码 -->
    <input type="hidden" id = "equipment" name = "equipment" value="${page.lpszDeviceName }" ><!-- 设备 -->
    </form>
  </body>
</html>
