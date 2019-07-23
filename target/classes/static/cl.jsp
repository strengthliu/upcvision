<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<title>Test Data Display</title>
<head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
	<script type="text/javascript">
		/*
		var ws;
		var target = "ws://localhost:8081/shidasaipu01/echo";
		f();
		for (var int = 1; int <=0; int++) {
			name(int);
		}
		function f() {
			if("WebSocket" in window){
				ws = new WebSocket(target);
				
			}else if("MozWebSocket" in window){
				ws = new MozWebSocket(target);
				
			}else{
				alert("不支持您使用的浏览器");
				return
			}
			ws.onmessage = function(event) {
				var a = event.data.split(":");
				document.getElementById(a[0]).innerHTML=a[1];
				
			};
			
		}
		function name(id) {
			if(ws.readyState == 1) {
				ws.send(id);
			}
			
			
			setTimeout(function(){ 
			name(id);
   			}, 1500);
		}
		function toMainMenu() {
			
			try {
				location.href="jsp/option.jsp"
			} catch (e) {
			}finally{
				ws.close();
			}
		}
		function toHistory() {
			
			try {
				location.href="history";
			} catch (e) {
			}finally{
				ws.close();
			}
		}
		*/
	</script>
  </head>
<body style="background-color:#8aade9; ">
<div align=center>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<?xml version="1.0" encoding="utf-8"?>
<!-- Create with Visual Graph (http://www.visual-graph.com/) -->
<svg  xmlns="http://www.w3.org/2000/svg" version="1.1" xmlns:xlink="http://www.w3.org/1999/xlink" style="background-color:#33CCCC" id="main" viewBox="0.000000 0.000000 900.000000 600.000000" width="900.000000" height="600.000000">
<script type="text/javascript" xlink:href="vg.js"/>
<defs>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-1">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-2">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-3">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-4">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-5">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-6">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-7">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-8">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-9">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-10">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-11">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-12">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-13">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-14">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-15">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-16">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-17">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-18">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-19">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-20">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-21">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-22">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-23">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-24">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-25">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-26">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-27">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-28">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-29">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-30">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-31">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-32">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-33">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-34">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-35">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-36">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-37">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-38">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-39">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-40">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-41">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-42">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-43">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-44">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-45">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-46">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-47">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-48">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-49">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-50">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-51">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-52">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-53">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-54">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-55">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-56">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-57">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-58">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-59">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-60">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-61">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="1" stop-color="#ffffff" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-62">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="1" stop-color="#ffffff" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-63">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-64">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-65">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-66">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-67">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-68">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-69">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-70">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-71">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-72">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-73">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-74">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-75">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-76">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-77">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-78">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-79">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-80">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-81">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-82">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-83">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="50.000000%" y1="0.000000%" x2="50.000000%" y2="100.000000%" id="gradient-84">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-85">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-86">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-87">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="0.000000%" x2="0.000000%" y2="100.000000%" id="gradient-88">
<stop offset="0" stop-color="#808080"/>
<stop offset="0.5" stop-color="#FFFFFF"/>
<stop offset="1" stop-color="#808080"/>
</linearGradient>
<linearGradient x1="0.000000%" y1="50.000000%" x2="100.000000%" y2="50.000000%" id="gradient-89">
<stop offset="0" stop-color="#808080" stop-opacity="1"/>
<stop offset="0.5" stop-color="#ffffff" stop-opacity="1"/>
<stop offset="1" stop-color="#808080" stop-opacity="1"/>
</linearGradient>
<marker id="marker1" orient="auto" refX="10" refY="1.5" viewBox="0.000000 -1.500001 10.000000 6.000000" markerWidth="10" markerHeight="6">
<polygon id="marker1" points="-10.000000,0.000000 -10.000000,-3.000000 0.000000,0.000000 -10.000000,3.000000" stroke-width="0" transform="translate(10.000000,1.499999) "/>
</marker>
</defs>
<text id="text1" x="-1.08887" y="8" font-family="SimSun" font-size="26" text-anchor="start" transform="translate(404.088867,18.000000) ">
初馏部分</text>
<rect id="rect1" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(158.500000,51.000000) "/>
<text id="text2" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(127.088867,59.000000) ">
初馏</text>
<rect id="rect2" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(223.500000,51.000000) "/>
<text id="text3" x="25.6221" y="-8" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(184.888867,61.400000) ">
加热炉</text>
<rect id="rect3" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(289.500000,51.000000) "/>
<text id="text4" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(258.088867,59.000000) ">
常压</text>
<rect id="rect4" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(354.500000,51.000000) "/>
<text id="text5" x="21.9111" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(322.088867,59.000000) ">
减压</text>
<rect id="rect5" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(420.500000,51.000000) "/>
<text id="text6" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(377.088867,59.500000) ">
电精制</text>
<rect id="rect6" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(485.500000,51.000000) "/>
<text id="text7" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(453.088867,59.000000) ">
产汽</text>
<rect id="rect7" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(551.500000,51.000000) "/>
<text id="text8" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(509.088867,59.000000) ">
电脱盐</text>
<rect id="rect8" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(616.500000,51.000000) "/>
<text id="text9" x="21.9111" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(584.088867,59.000000) ">
报警</text>
<rect id="rect9" x="-30.5" y="-11" width="65" height="22" fill="#999999" stroke="#FFFFFF" transform="translate(682.500000,51.000000) "/>
<text id="text10" x="21.9111" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(650.088867,59.000000) ">
返回</text>
<ellipse id="circle1" cx="1.5" cy="0" rx="25.5" ry="22" fill="url(#gradient-1)" stroke="#000000" transform="translate(391.000000,192.000000) "/>
<ellipse id="circle2" cx="1.5" cy="0" rx="25.5" ry="22" fill="url(#gradient-2)" stroke="#000000" transform="translate(391.000000,420.000000) "/>
<rect id="rect10" x="-20.5" y="-120.5" width="51" height="231" fill="url(#gradient-3)" stroke="#000000" transform="translate(387.500000,309.500000) "/>
<line id="line1" x1="0.25" y1="-23" x2="-0.25" y2="23" fill="none" stroke="#000000" transform="translate(392.750000,147.000000) "/>
<line id="line2" x1="-96" y1="0" x2="79" y2="0" fill="none" stroke="#000000" transform="translate(489.000000,124.000000) "/>
<line id="line3" x1="0" y1="-65.5" x2="-0.54118" y2="95.9944" fill="none" stroke="#000000" transform="translate(568.000000,189.500000) "/>
<g id="element1" transform="translate(660.250000,253.500000) ">
<ellipse id="circle3" cx="0.75" cy="0" rx="12.75" ry="7.07721" fill="url(#gradient-4)" stroke="#000000" transform="translate(-55.577206,-13.500000) rotate(90.000000) "/>
<ellipse id="circle4" cx="0.75" cy="0" rx="12.75" ry="7.07721" fill="url(#gradient-5)" stroke="#000000" transform="translate(-128.922794,-13.500000) rotate(90.000000) "/>
<rect id="rect11" x="-10.25" y="-38.7638" width="25.5" height="74.3107" fill="url(#gradient-6)" stroke="#000000" transform="translate(-93.375919,-15.250000) rotate(90.000000) "/>
</g>
<line id="line4" x1="-18" y1="-0.5" x2="65" y2="-0.5" fill="none" stroke="#000000" transform="translate(296.000000,353.500000) "/>
<line id="line5" x1="0" y1="-124" x2="0" y2="124" fill="none" stroke="#000000" transform="translate(276.000000,229.000000) "/>
<line id="line6" x1="-56" y1="0" x2="105" y2="0" fill="none" stroke="#000000" transform="translate(169.000000,103.000000) "/>
<line id="line7" x1="0" y1="-17" x2="0" y2="17" fill="none" stroke="#000000" transform="translate(113.000000,120.000000) "/>
<line id="line8" x1="-58" y1="0" x2="58" y2="0" fill="none" stroke="#000000" transform="translate(115.000000,137.000000) "/>
<line id="line9" x1="0" y1="-50.5" x2="-1" y2="69.5" fill="none" stroke="#000000" transform="translate(57.000000,187.500000) "/>
<line id="line10" x1="0" y1="-50.5" x2="-1" y2="69.5" fill="none" stroke="#000000" transform="translate(173.000000,187.500000) "/>
<line id="line11" x1="-58" y1="0" x2="58" y2="0" fill="none" stroke="#000000" transform="translate(114.000000,257.000000) "/>
<line id="line12" x1="0" y1="-38" x2="-1.5" y2="82" fill="none" stroke="#000000" transform="translate(116.000000,297.000000) "/>
<g id="element2" transform="translate(61.500000,165.000000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-7)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-8)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-9)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-10)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-104</text>
</g>
<g id="element3" transform="translate(173.000000,165.000000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-11)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-12)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-13)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-14)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-119</text>
</g>
<g id="element4" transform="translate(61.500000,200.000000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-15)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-16)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-17)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-18)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-109</text>
</g>
<g id="element5" transform="translate(173.000000,202.000000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-19)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-20)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-21)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-22)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-114</text>
</g>
<g id="element6" transform="translate(75.691827,261.480765) ">
<rect id="rect11" x="-1.2" y="-3.58333" width="2.4" height="7.16667" fill="url(#gradient-23)" stroke="#000000" transform="translate(-12.200000,-28.250000) "/>
</g>
<text id="text11" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(72.044434,109.000000) ">
TI2104</text>
<text id="text12" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(119.044434,108.500000) ">
TI2105</text>
<text id="text18" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(58.544434,260.500000) ">
FI2104</text>
<text id="text19" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(128.544434,260.500000) ">
FI2105</text>
<text id="1" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(57.847168,272.500000) ">
XXXXXXXX</text>
<text id="text23" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(118.544434,312.000000) ">
TI2103</text>
<text id="2" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(125.544434,272.500000) ">
XXXXXXXX</text>
<text id="3" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(117.757280,325.472357) ">
XXXXXXXX</text>
<line id="line14" x1="-58" y1="0" x2="58" y2="0" fill="none" stroke="#000000" transform="translate(115.500000,381.500000) "/>
<line id="line15" x1="0" y1="-50.5" x2="-1" y2="69.5" fill="none" stroke="#000000" transform="translate(57.500000,432.000000) "/>
<line id="line16" x1="0" y1="-50.5" x2="-1" y2="69.5" fill="none" stroke="#000000" transform="translate(173.500000,432.000000) "/>
<line id="line17" x1="-58" y1="0" x2="58" y2="0" fill="none" stroke="#000000" transform="translate(114.500000,501.500000) "/>
<line id="line18" x1="0" y1="-38" x2="0" y2="-1" fill="none" stroke="#000000" transform="translate(116.500000,541.500000) "/>
<g id="element8" transform="translate(62.000000,409.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-25)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-26)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-27)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-28)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-104</text>
</g>
<g id="element9" transform="translate(173.500000,409.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-29)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-30)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-31)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-32)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-108</text>
</g>
<g id="element10" transform="translate(62.000000,444.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-33)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-34)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-35)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-36)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-101</text>
</g>
<g id="element11" transform="translate(173.500000,446.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-37)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-38)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-39)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-40)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-105</text>
</g>
<text id="text27" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(43.044434,365.000000) ">
TI2101</text>
<text id="text34" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(53.844434,519.850000) ">
TI2109</text>
<text id="text35" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(146.244434,518.200000) ">
FI2101</text>
<text id="4" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(53.147168,531.850000) ">
XXXXXXXX</text>
<text id="5" x="-0.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(125.544434,530.000000) ">
XXXXXXXX M3/h</text>
<text id="6" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(79.834831,357.501185) ">
XXXXXX</text>
<text id="7" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(43.044434,375.000000) ">
XXXXXX</text>
<text id="text38" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(78.135865,344.013643) ">
FIC2102</text>
<text id="8" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(117.600988,349.975707) ">
XXXXXXXX</text>
<text id="9" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(176.044434,375.000000) ">
XXXXXX</text>
<text id="text41" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(171.544434,362.500000) ">
TI2102</text>
<g id="element7" transform="translate(152.687522,203.997396) rotate(180.000000) ">
<rect id="rect11" x="-1.2" y="-3.58333" width="2.4" height="7.16667" fill="url(#gradient-41)" stroke="#000000" transform="translate(-12.200000,-28.250000) "/>
</g>
<line id="line13" x1="0.25" y1="-62.25" x2="-0.75" y2="56.75" fill="#FFFFFF" stroke="#FFFFFF" stroke-dasharray="16,8" transform="translate(92.250000,189.250000) "/>
<line id="line19" x1="-17.75" y1="0.25" x2="17.75" y2="-0.25" fill="none" stroke="#000000" transform="translate(98.750000,540.750000) "/>
<line id="line20" x1="-0.25" y1="-15.25" x2="0.25" y2="15.25" fill="none" stroke="#000000" transform="translate(81.250000,556.250000) "/>
<g id="element14" transform="translate(77.750000,581.800000) ">
<g id="element14" transform="translate(0.450000,-6.400000) rotate(90.000000) ">
<path id="line21" d="M-9.966766,-2.556093 L-1.255655,-2.556093 C1.255656,-2.556093 1.020221,0.768906 -1.491091,0.742092 L-9.888288,0.742092 " fill="url(#gradient-43)" stroke="#000000" transform="translate(8.136336,-2.106094) "/>
<rect id="rect11" x="-0.706306" y="-3.20031" width="1.41261" height="6.40062" fill="url(#gradient-44)" stroke="#000000" transform="translate(-2.693694,-2.999687) "/>
<ellipse id="circle4" cx="5e-007" cy="-0.207812" rx="6.16056" ry="6.52531" fill="#808080" stroke="#000000" transform="translate(8.097097,0.782500) "/>
<rect id="rect12" x="-0.706306" y="-5.94344" width="2.64865" height="11.305" fill="#00FF80" stroke="#33FF33" transform="translate(14.257658,1.239687) "/>
</g>
<ellipse id="circle3" cx="0" cy="-1.13687e-013" rx="4.7" ry="4.3" fill="#FF0000" stroke="#000000" transform="translate(-0.250000,1.300000) "/>
</g>
<g id="element15" transform="translate(106.650000,581.300000) ">
<g id="element14" transform="translate(0.450000,-6.400000) rotate(90.000000) ">
<path id="line21" d="M-9.966766,-2.556093 L-1.255655,-2.556093 C1.255656,-2.556093 1.020221,0.768906 -1.491091,0.742092 L-9.888288,0.742092 " fill="url(#gradient-45)" stroke="#000000" transform="translate(8.136336,-2.106094) "/>
<rect id="rect11" x="-0.706306" y="-3.20031" width="1.41261" height="6.40062" fill="url(#gradient-46)" stroke="#000000" transform="translate(-2.693694,-2.999687) "/>
<ellipse id="circle4" cx="5e-007" cy="-0.207812" rx="6.16056" ry="6.52531" fill="#808080" stroke="#000000" transform="translate(8.097097,0.782500) "/>
<rect id="rect12" x="-0.706306" y="-5.94344" width="2.64865" height="11.305" fill="#00FF80" stroke="#33FF33" transform="translate(14.257658,1.239687) "/>
</g>
<ellipse id="circle3" cx="0" cy="-1.13687e-013" rx="4.7" ry="4.3" fill="#FF0000" stroke="#000000" transform="translate(-0.250000,1.300000) "/>
</g>
<line id="line21" x1="0.250595" y1="-14.4578" x2="-0.250595" y2="14.4578" fill="none" stroke="#000000" transform="translate(110.250595,555.042245) "/>
<line id="line25" x1="-11.25" y1="0" x2="11.25" y2="0" fill="none" stroke="#000000" transform="translate(59.750000,584.000000) "/>
<line id="line26" x1="0.25" y1="-9.75" x2="-0.25" y2="9.75" fill="none" stroke="#000000" transform="translate(48.750000,574.250000) "/>
<line id="line27" x1="-22" y1="0" x2="22" y2="0" fill="none" stroke="#000000" transform="translate(50.500000,564.000000) "/>
<line id="line28" x1="0.5" y1="-49.25" x2="-0.5" y2="49.25" fill="none" stroke="#000000" transform="translate(392.000000,491.250000) "/>
<line id="line29" x1="-17.5" y1="-0.5" x2="17.5" y2="0.5" fill="none" stroke="#000000" transform="translate(409.000000,541.000000) "/>
<g id="element16" transform="translate(433.150000,542.300000) ">
<g id="element14" transform="translate(0.450000,-6.400000) rotate(90.000000) ">
<path id="line21" d="M-9.966766,-2.556093 L-1.255655,-2.556093 C1.255656,-2.556093 1.020221,0.768906 -1.491091,0.742092 L-9.888288,0.742092 " fill="url(#gradient-47)" stroke="#000000" transform="translate(8.136336,-2.106094) "/>
<rect id="rect11" x="-0.706306" y="-3.20031" width="1.41261" height="6.40062" fill="url(#gradient-48)" stroke="#000000" transform="translate(-2.693694,-2.999687) "/>
<ellipse id="circle4" cx="5e-007" cy="-0.207812" rx="6.16056" ry="6.52531" fill="#808080" stroke="#000000" transform="translate(8.097097,0.782500) "/>
<rect id="rect12" x="-0.706306" y="-5.94344" width="2.64865" height="11.305" fill="#00FF80" stroke="#33FF33" transform="translate(14.257658,1.239687) "/>
</g>
<ellipse id="circle3" cx="0" cy="-1.13687e-013" rx="4.7" ry="4.3" fill="#FF0000" stroke="#000000" transform="translate(-0.250000,1.300000) "/>
</g>
<g id="element17" transform="translate(463.100000,535.900000) rotate(90.000000) ">
<path id="line21" d="M-9.966766,-2.556093 L-1.255655,-2.556093 C1.255656,-2.556093 1.020221,0.768906 -1.491091,0.742092 L-9.888288,0.742092 " fill="url(#gradient-49)" stroke="#000000" transform="translate(8.136336,-2.106094) "/>
<rect id="rect11" x="-0.706306" y="-3.20031" width="1.41261" height="6.40062" fill="url(#gradient-50)" stroke="#000000" transform="translate(-2.693694,-2.999687) "/>
<ellipse id="circle4" cx="5e-007" cy="-0.207812" rx="6.16056" ry="6.52531" fill="#808080" stroke="#000000" transform="translate(8.097097,0.782500) "/>
<rect id="rect12" x="-0.706306" y="-5.94344" width="2.64865" height="11.305" fill="#00FF80" stroke="#33FF33" transform="translate(14.257658,1.239687) "/>
</g>
<ellipse id="circle3" cx="0" cy="-1.13687e-013" rx="4.7" ry="4.3" fill="#00FF80" stroke="#000000" transform="translate(462.400000,543.600000) "/>
<path id="line30" d="M-8.393472,-14.751747 L1.893471,-14.748254 M2.393471,-14.248254 L1.393471,14.251746 8.393471,14.751746 " fill="none" stroke="#000000" transform="translate(93.106529,567.748254) "/>
<path id="line22" d="M-8.393472,-14.751747 L1.893471,-14.748254 M2.393471,-14.248254 L1.393471,14.251746 8.393471,14.751746 " fill="none" stroke="#000000" transform="translate(449.306529,528.848254) "/>
<line id="line23" x1="-0.25" y1="-17.75" x2="0.25" y2="17.75" fill="none" stroke="#000000" transform="translate(436.250000,514.750000) "/>
<line id="line24" x1="-22.0657" y1="-0.014235" x2="22.0657" y2="0.014235" fill="none" stroke="#000000" transform="translate(413.976909,501.013333) "/>
<line id="line31" x1="-24.4787" y1="0.513784" x2="292.479" y2="2.48622" fill="none" stroke="#000000" transform="translate(460.521321,498.013784) "/>
<g id="element12" transform="translate(96.450000,498.000000) rotate(270.000000) ">
<rect id="rect11" x="1.2" y="-3.58333" width="3.55" height="9.01667" fill="#33FF33" stroke="#FFFFFF" transform="translate(1.800000,-1.333333) "/>
<path id="line32" d="M-4.800000,-5.416667 L1.460870,-5.500000 -1.982609,-0.333333Z-4.695652,5.250000 1.147826,5.166667 1.252174,5.500000 1.147826,5.416667 1.147826,5.250000ZM-1.982609,-0.333333 L4.800000,-0.250000 " fill="url(#gradient-51)" stroke="#000000" transform="translate(-1.750000,0.000000) "/>
</g>
<g id="element13" transform="translate(134.500000,497.500000) rotate(270.000000) ">
<rect id="rect11" x="1.2" y="-3.58333" width="3.55" height="9.01667" fill="#33FF33" stroke="#FFFFFF" transform="translate(1.800000,-1.333333) "/>
<path id="line32" d="M-4.800000,-5.416667 L1.460870,-5.500000 -1.982609,-0.333333Z-4.695652,5.250000 1.147826,5.166667 1.252174,5.500000 1.147826,5.416667 1.147826,5.250000ZM-1.982609,-0.333333 L4.800000,-0.250000 " fill="url(#gradient-52)" stroke="#000000" transform="translate(-1.750000,0.000000) "/>
</g>
<line id="line32" x1="0.25" y1="-61.25" x2="-0.25" y2="61.25" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(92.750000,429.750000) "/>
<line id="line33" x1="-125.5" y1="0.25" x2="125.5" y2="-0.25" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(218.500000,368.250000) "/>
<line id="line34" x1="0.25" y1="-17.25" x2="-0.25" y2="17.25" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(343.750000,385.250000) "/>
<line id="line35" x1="-11.75" y1="0" x2="11.75" y2="0" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(355.250000,402.500000) "/>
<text id="text15" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(321.544434,420.500000) ">
LIC2181</text>
<text id="10" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(321.544433,435.000000) ">
XXXXXXXX</text>
<text id="11" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(324.044434,338.000000) ">
TI2183</text>
<text id="12" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(323.347167,350.000000) ">
XXXXXXXX</text>
<text id="text32" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(318.044434,144.500000) ">
PI2181</text>
<text id="13" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(316.957520,159.750000) ">
XXXXX</text>
<text id="text43" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(430.544434,423.000000) ">
LI2182</text>
<text id="14" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(427.044433,434.500000) ">
XXXXXXXX</text>
<text id="text45" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(406.544434,458.000000) ">
TI2184</text>
<text id="15" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(407.044433,473.500000) ">
XXXXXXXX</text>
<line id="line36" x1="-28.5" y1="0" x2="28.5" y2="0" fill="none" stroke="#000000" transform="translate(446.500000,193.000000) "/>
<line id="line37" x1="0.25" y1="-68.5" x2="-0.25" y2="68.5" fill="none" stroke="#000000" transform="translate(474.750000,261.500000) "/>
<line id="line39" x1="-83.75" y1="-0.25" x2="63.7502" y2="0.190299" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(427.250000,162.250000) "/>
<line id="line40" x1="8.9e-005" y1="-51.2799" x2="-8.9e-005" y2="51.2799" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(491.000089,213.720150) "/>
<line id="line41" x1="-4.51276" y1="-0.004609" x2="11.75" y2="0" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(479.250000,265.000000) "/>
<text id="text47" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(417.544434,150.500000) ">
TIC2181</text>
<text id="16" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(420.233859,165.721461) ">
XXXXX</text>
<text id="text49" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(420.544434,204.800000) ">
TI2185</text>
<text id="17" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(420.544434,216.800000) ">
XXXXXXX</text>
<text id="text51" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(427.044434,272.000000) ">
TI2182</text>
<text id="18" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(420.544434,284.000000) ">
XXXXXXX</text>
<g id="element18" transform="translate(478.079859,264.996339) ">
<rect id="rect11" x="1.2" y="-3.58333" width="3.55" height="9.01667" fill="#33FF33" stroke="#FFFFFF" transform="translate(1.800000,-1.333333) "/>
<path id="line32" d="M-4.800000,-5.416667 L1.460870,-5.500000 -1.982609,-0.333333Z-4.695652,5.250000 1.147826,5.166667 1.252174,5.500000 1.147826,5.416667 1.147826,5.250000ZM-1.982609,-0.333333 L4.800000,-0.250000 " fill="url(#gradient-53)" stroke="#000000" transform="translate(-1.750000,0.000000) "/>
</g>
<g id="element19" transform="translate(512.000000,326.550000) rotate(270.000000) ">
<rect id="rect11" x="1.2" y="-3.58333" width="3.55" height="9.01667" fill="#33FF33" stroke="#FFFFFF" transform="translate(1.800000,-1.333333) "/>
<path id="line32" d="M-4.800000,-5.416667 L1.460870,-5.500000 -1.982609,-0.333333Z-4.695652,5.250000 1.147826,5.166667 1.252174,5.500000 1.147826,5.416667 1.147826,5.250000ZM-1.982609,-0.333333 L4.800000,-0.250000 " fill="url(#gradient-54)" stroke="#000000" transform="translate(-1.750000,0.000000) "/>
</g>
<text id="text53" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(477.212308,276.002437) ">
FIC2181</text>
<text id="19" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(477.214140,287.000619) ">
XXXXXXX</text>
<rect id="rect11" x="-13.5" y="-5.75" width="27" height="11.5" fill="#FF0066" stroke="#000000" transform="translate(497.000000,231.250000) "/>
<text id="text55" x="-1.54443" y="4.5" font-family="SimSun" font-size="9" text-anchor="start" stroke="#FFFFFF" transform="translate(484.143799,230.500000) ">
单回路</text>
<g id="element20" transform="translate(573.750000,187.500000) ">
<g id="element2" transform="translate(11.811404,-9.392857) ">
<rect id="rect11" x="-1.57673" y="-8.55114" width="1.35149" height="20.5227" fill="url(#gradient-55)" stroke="#000000" transform="translate(-38.734671,3.943994) "/>
<ellipse id="circle3" cx="0" cy="0.488637" rx="7.65842" ry="6.84091" fill="url(#gradient-56)" stroke="#000000" transform="translate(-2.469820,4.676948) "/>
<rect id="rect12" x="-18.4703" y="-6.84091" width="36.9406" height="13.6818" fill="url(#gradient-57)" stroke="#000000" transform="translate(-20.489622,5.165584) "/>
<rect id="rect13" x="-1.57673" y="-8.55114" width="1.35149" height="20.5227" fill="url(#gradient-58)" stroke="#000000" transform="translate(-33.328731,4.921266) "/>
</g>
<text id="text11" x="20.8562" y="-0.75" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-32.151350,2.125000) ">
</text>
</g>
<g id="element21" transform="translate(499.000000,143.075610) ">
<rect id="rect12" x="-30.5" y="1.37578" width="61" height="3.29453" fill="url(#gradient-59)" stroke="#000000" transform="translate(-7.500000,-25.800170) "/>
<polygon id="line42" points="-23.276316,-9.859547 23.276315,-9.859547 12.039473,9.859546 -8.828948,9.340623 -10.434211,9.859546" fill="url(#gradient-60)" stroke="#000000" transform="translate(-6.697368,-11.270312) "/>
<ellipse id="circle4" cx="0" cy="-5e-007" rx="6.42105" ry="2.33516" fill="url(#gradient-61)" stroke="#000000" transform="translate(-13.118421,-1.929689) "/>
<ellipse id="circle5" cx="-5e-007" cy="2.84217e-014" rx="6.42105" ry="2.33516" fill="url(#gradient-62)" stroke="#000000" transform="translate(0.526316,-1.410766) "/>
</g>
<line id="line43" x1="-58" y1="0" x2="58" y2="0" fill="none" stroke="#000000" transform="translate(752.000000,341.500000) "/>
<line id="line44" x1="0" y1="-50.5" x2="-1" y2="69.5" fill="none" stroke="#000000" transform="translate(694.000000,392.000000) "/>
<line id="line45" x1="0" y1="-50.5" x2="-1" y2="69.5" fill="none" stroke="#000000" transform="translate(810.000000,392.000000) "/>
<line id="line46" x1="-58" y1="0" x2="58" y2="0" fill="none" stroke="#000000" transform="translate(751.000000,461.500000) "/>
<line id="line47" x1="0" y1="-38" x2="0" y2="-1" fill="none" stroke="#000000" transform="translate(753.000000,501.500000) "/>
<g id="element22" transform="translate(698.500000,369.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-63)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-64)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-65)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-66)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-122</text>
</g>
<g id="element23" transform="translate(810.000000,369.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-67)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-68)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-69)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-70)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-125</text>
</g>
<g id="element24" transform="translate(698.500000,404.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-71)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-72)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-73)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-74)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-120</text>
</g>
<g id="element25" transform="translate(810.000000,406.500000) ">
<g id="element2" transform="translate(22.000000,-8.000000) ">
<rect id="rect11" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-75)" stroke="#000000" transform="translate(-48.524752,5.136364) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.636364" rx="9.59406" ry="8.90909" fill="url(#gradient-76)" stroke="#000000" transform="translate(-3.094059,6.090909) "/>
<rect id="rect12" x="-23.1386" y="-8.90909" width="46.2772" height="17.8182" fill="url(#gradient-77)" stroke="#000000" transform="translate(-25.668317,6.727273) "/>
<rect id="rect13" x="-1.97525" y="-11.1364" width="1.69307" height="26.7273" fill="url(#gradient-78)" stroke="#000000" transform="translate(-41.752475,6.409091) "/>
</g>
<text id="text11" x="25.6221" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(-33.074219,7.000000) ">
E-123</text>
</g>
<text id="text56" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(686.544434,327.000000) ">
TI2106</text>
<text id="20" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(687.544434,340.000000) ">
XXXXXX</text>
<text id="21" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(777.968262,336.000000) ">
XXXXXX</text>
<text id="text62" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(774.544434,324.000000) ">
TI2107</text>
<g id="element26" transform="translate(696.208489,436.481321) ">
<rect id="rect11" x="1.2" y="-3.58333" width="3.55" height="9.01667" fill="#33FF33" stroke="#FFFFFF" transform="translate(1.800000,-1.333333) "/>
<path id="line32" d="M-4.800000,-5.416667 L1.460870,-5.500000 -1.982609,-0.333333Z-4.695652,5.250000 1.147826,5.166667 1.252174,5.500000 1.147826,5.416667 1.147826,5.250000ZM-1.982609,-0.333333 L4.800000,-0.250000 " fill="url(#gradient-79)" stroke="#000000" transform="translate(-1.750000,0.000000) "/>
</g>
<line id="line42" x1="-0.25" y1="-17.75" x2="0.25" y2="17.75" fill="none" stroke="#000000" transform="translate(466.264225,516.464082) "/>
<text id="text63" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(429.544434,559.500000) ">
P-103/1.2</text>
<text id="22" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(705.051232,493.907524) ">
XXXXXXXX</text>
<text id="23" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(761.847167,495.000000) ">
XXXXXXXX</text>
<text id="text66" x="-0.544434" y="1.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(707.544434,485.500000) ">
FI2116</text>
<text id="text67" x="-0.544434" y="1.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(762.544434,487.000000) ">
FI2117</text>
<text id="24" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(814.577695,453.508611) ">
XXXXXXXX</text>
<text id="25" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(660.032316,450.516566) ">
XXXXXXXX</text>
<g id="element27" transform="translate(806.050000,438.500000) rotate(180.000000) ">
<rect id="rect11" x="1.2" y="-3.58333" width="3.55" height="9.01667" fill="#33FF33" stroke="#FFFFFF" transform="translate(1.800000,-1.333333) "/>
<path id="line32" d="M-4.800000,-5.416667 L1.460870,-5.500000 -1.982609,-0.333333Z-4.695652,5.250000 1.147826,5.166667 1.252174,5.500000 1.147826,5.416667 1.147826,5.250000ZM-1.982609,-0.333333 L4.800000,-0.250000 " fill="url(#gradient-80)" stroke="#000000" transform="translate(-1.750000,0.000000) "/>
</g>
<text id="text69" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(816.544433,441.000000) ">
HIC2104</text>
<text id="text70" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(660.544433,438.500000) ">
HIC2103</text>
<rect id="rect12" x="-18" y="-6.25" width="36" height="12.5" fill="#33CC66" stroke="#FFFFFF" transform="translate(114.471801,301.255898) "/>
<text id="text71" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" stroke="#FFFF00" transform="translate(99.897095,299.005898) ">
电脱盐</text>
<rect id="rect13" x="-18" y="-6.25" width="36" height="12.5" fill="#33CC66" stroke="#FFFFFF" transform="translate(751.000000,287.750000) "/>
<text id="text72" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" stroke="#FFFF00" transform="translate(736.425294,285.500000) ">
电脱盐</text>
<line id="line48" x1="0" y1="-23.75" x2="0" y2="23.75" fill="none" stroke="#000000" transform="translate(751.000000,317.750000) "/>
<g id="element28" transform="translate(763.982456,311.214286) ">
<rect id="rect11" x="-1.52475" y="-8.75" width="1.30693" height="21" fill="url(#gradient-81)" stroke="#000000" transform="translate(-37.457703,4.035714) "/>
<ellipse id="circle3" cx="-5e-007" cy="0.5" rx="7.40594" ry="7" fill="url(#gradient-82)" stroke="#000000" transform="translate(-2.388396,4.785714) "/>
<rect id="rect12" x="-17.8614" y="-7" width="35.7228" height="14" fill="url(#gradient-83)" stroke="#000000" transform="translate(-19.814139,5.285714) "/>
<rect id="rect13" x="-1.52475" y="-8.75" width="1.30693" height="21" fill="url(#gradient-84)" stroke="#000000" transform="translate(-32.229981,5.035714) "/>
</g>
<text id="text58" x="19.2346" y="-4" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(717.130653,323.750000) ">
E-113</text>
<text id="text60" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(771.544434,291.250000) ">
TI2101</text>
<text id="26" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(774.544434,304.500000) ">
XXXXXX</text>
<text id="text74" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(722.544434,257.500000) ">
至D-112</text>
<g id="element29" transform="translate(494.650000,377.800000) ">
<g id="element14" transform="translate(0.450000,-6.400000) rotate(90.000000) ">
<path id="line21" d="M-9.966766,-2.556093 L-1.255655,-2.556093 C1.255656,-2.556093 1.020221,0.768906 -1.491091,0.742092 L-9.888288,0.742092 " fill="url(#gradient-85)" stroke="#000000" transform="translate(8.136336,-2.106094) "/>
<rect id="rect11" x="-0.706306" y="-3.20031" width="1.41261" height="6.40062" fill="url(#gradient-86)" stroke="#000000" transform="translate(-2.693694,-2.999687) "/>
<ellipse id="circle4" cx="5e-007" cy="-0.207813" rx="6.16056" ry="6.52531" fill="#808080" stroke="#000000" transform="translate(8.097097,0.782500) "/>
<rect id="rect12" x="-0.706306" y="-5.94344" width="2.64865" height="11.305" fill="#00FF80" stroke="#33FF33" transform="translate(14.257658,1.239687) "/>
</g>
<ellipse id="circle3" cx="0" cy="0" rx="4.7" ry="4.3" fill="#FF0000" stroke="#000000" transform="translate(-0.250000,1.300000) "/>
</g>
<g id="element30" transform="translate(524.600000,371.400000) rotate(90.000000) ">
<path id="line21" d="M-9.966766,-2.556093 L-1.255655,-2.556093 C1.255656,-2.556093 1.020221,0.768906 -1.491091,0.742092 L-9.888288,0.742092 " fill="url(#gradient-87)" stroke="#000000" transform="translate(8.136336,-2.106094) "/>
<rect id="rect11" x="-0.706306" y="-3.20031" width="1.41261" height="6.40062" fill="url(#gradient-88)" stroke="#000000" transform="translate(-2.693694,-2.999687) "/>
<ellipse id="circle4" cx="5e-007" cy="-0.207813" rx="6.16056" ry="6.52531" fill="#808080" stroke="#000000" transform="translate(8.097097,0.782500) "/>
<rect id="rect12" x="-0.706306" y="-5.94344" width="2.64865" height="11.305" fill="#00FF80" stroke="#33FF33" transform="translate(14.257658,1.239687) "/>
</g>
<ellipse id="circle4" cx="1.13687e-013" cy="0" rx="4.7" ry="4.3" fill="#00FF80" stroke="#000000" transform="translate(523.900000,379.100000) "/>
<line id="line50" x1="-0.25" y1="-17.75" x2="0.25" y2="17.75" fill="none" stroke="#000000" transform="translate(497.750000,350.250000) "/>
<line id="line51" x1="1.73577" y1="-69.9641" x2="0.735775" y2="27.0359" fill="none" stroke="#000000" transform="translate(565.764225,351.464082) "/>
<text id="text75" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(491.044434,395.000000) ">
P-103/1.2</text>
<line id="line52" x1="-17.2" y1="0.05" x2="17.2" y2="-0.05" fill="none" stroke="#000000" transform="translate(545.800000,379.050000) "/>
<line id="line49" x1="-31.8712" y1="-0.74094" x2="24.3712" y2="0.240941" fill="none" stroke="#000000" transform="translate(542.371227,354.740940) "/>
<line id="line53" x1="-0.25" y1="-12.5" x2="0.25" y2="12.5" fill="none" stroke="#000000" transform="translate(510.750000,366.500000) "/>
<line id="line54" x1="4" y1="-0.25" x2="-4" y2="0.25" fill="none" stroke="#000000" transform="translate(507.000000,379.250000) "/>
<line id="line55" x1="0" y1="-11.25" x2="0" y2="11.25" fill="none" stroke="#000000" transform="translate(526.500000,355.250000) "/>
<line id="line56" x1="-14.4299" y1="-0.772115" x2="14.4014" y2="-1.24861" fill="none" stroke="#000000" transform="translate(512.098572,345.248612) "/>
<text id="27" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(580.968750,355.500000) ">
XXXXXXX</text>
<text id="text78" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(583.544434,343.500000) ">
FI2182</text>
<text id="text79" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(590.552840,336.213994) ">
汽油线</text>
<text id="text80" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(620.040205,323.895395) ">
至常压</text>
<line id="line57" x1="-0.25" y1="-5.25" x2="0.25" y2="5.25" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(511.250000,314.750000) "/>
<line id="line58" x1="-66" y1="-0.5" x2="36.5055" y2="0.276557" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(577.000000,310.000000) "/>
<line id="line59" x1="0.49726" y1="-46.6383" x2="-0.497259" y2="21.1383" fill="none" stroke="#000000" stroke-dasharray="1" transform="translate(614.002740,289.138278) "/>
<rect id="rect14" x="-3" y="-4.5" width="6" height="9" fill="none" stroke="#000000" transform="translate(537.500000,259.000000) "/>
<text id="text81" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(546.044434,259.500000) ">
LIC2184</text>
<text id="28" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(543.044434,267.000000) ">
XXXXXXX</text>
<line id="line60" x1="-10.25" y1="0.5" x2="6.25" y2="0.5" fill="none" stroke="#000000" transform="translate(528.250000,258.500000) "/>
<line id="line61" x1="0.25" y1="-12.5" x2="-0.25" y2="12.5" fill="none" stroke="#000000" transform="translate(517.750000,271.500000) "/>
<line id="line62" x1="-10.25" y1="0.5" x2="6.25" y2="0.5" fill="none" stroke="#000000" transform="translate(527.750000,283.500000) "/>
<g id="element31" transform="translate(541.050000,284.500000) rotate(180.000000) ">
<rect id="rect11" x="1.2" y="-3.58333" width="3.55" height="9.01667" fill="#33FF33" stroke="#FFFFFF" transform="translate(1.800000,-1.333333) "/>
<path id="line32" d="M-4.800000,-5.416667 L1.460870,-5.500000 -1.982609,-0.333333Z-4.695652,5.250000 1.147826,5.166667 1.252174,5.500000 1.147826,5.416667 1.147826,5.250000ZM-1.982609,-0.333333 L4.800000,-0.250000 " fill="url(#gradient-89)" stroke="#000000" transform="translate(-1.750000,0.000000) "/>
</g>
<text id="text83" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(508.050064,301.756782) ">
LIC2183</text>
<text id="text84" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(550.849610,238.500000) ">
D-106</text>
<text id="text85" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(550.691309,168.988763) ">
E14/1.2</text>
<text id="text86" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(464.044434,109.500000) ">
EC-101/1、2</text>
<line id="line63" x1="-25.5" y1="-0.5" x2="25.5" y2="0.5" fill="none" stroke="#000000" transform="translate(392.500000,275.500000) "/>
<line id="line64" x1="-25.5" y1="-0.5" x2="25.5" y2="0.5" fill="none" stroke="#000000" transform="translate(392.500000,315.500000) "/>
<line id="line65" x1="-25.5" y1="-0.5" x2="25.5" y2="0.5" fill="none" stroke="#000000" transform="translate(392.500000,357.500000) "/>
<line id="line66" x1="-25.5" y1="-0.5" x2="25.5" y2="0.5" fill="none" stroke="#000000" transform="translate(392.500000,389.500000) "/>
<text id="text87" x="-0.544434" y="7.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(390.562989,180.500000) ">
24</text>
<text id="text88" x="-0.544434" y="9.7" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(375.344434,223.900000) ">
初馏塔</text>
<text id="text89" x="-0.544434" y="9.7" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(378.944434,236.900000) ">
C-101</text>
<text id="text90" x="-0.544434" y="9.7" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(388.133947,263.634832) ">
9</text>
<text id="text91" x="-0.544434" y="9.7" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(393.744434,302.300000) ">
5</text>
<text id="text92" x="-0.544434" y="9.7" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(393.744434,347.100000) ">
4</text>
<text id="text93" x="-0.544434" y="9.7" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(394.163081,377.349021) ">
1</text>
<rect id="rect15" x="-2" y="-7" width="4" height="14" fill="#CCCCCC" stroke="#000000" transform="translate(369.000000,416.800000) "/>
<rect id="rect16" x="-2" y="-7" width="4" height="14" fill="#CCCCCC" stroke="#000000" transform="translate(416.000000,416.800000) "/>
<text id="text17" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(62.789142,218.985250) ">
HIC2101</text>
<text id="text94" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(143.365992,220.506846) ">
HIC2102</text>
<text id="29" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(70.544434,229.500000) ">
XXXXXXXX</text>
<text id="30" x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(120.989038,120.506846) ">
XXXXXXXX</text>
<text id="text16" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(117.600988,337.975707) ">
FIC2103</text>
<text id="text21" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(112.882474,553.497193) ">
原油自罐区来</text>
<polygon id="line67" points="-29.200000,3.500000 -35.500000,1.900000 -35.500000,2.500000 -35.500000,5.200000" fill="#000000" stroke="#000000" transform="translate(68.000000,560.500000) "/>
<polygon id="line68" points="-29.200000,3.500000 -35.500000,1.900000 -35.500000,2.500000 -35.500000,5.200000" fill="#000000" stroke="#000000" transform="translate(380.000000,349.500000) "/>
<polygon id="line69" points="-29.200000,3.500000 -35.500000,1.900000 -35.500000,2.500000 -35.500000,5.200000" fill="#000000" stroke="#000000" transform="translate(395.803221,490.582774) rotate(90.000000) "/>
<line id="line38" x1="-97" y1="-0.75" x2="97" y2="0.75" fill="none" stroke="#000000" marker-end="url(#marker1)" transform="translate(571.500000,330.750000) "/>
<polygon id="line70" points="-29.200000,3.500000 -35.500000,1.900000 -35.500000,2.500000 -35.500000,5.200000" fill="#000000" stroke="#000000" transform="translate(448.500000,333.700000) rotate(180.000000) "/>
<polygon id="line71" points="-29.200000,3.500000 -35.500000,1.900000 -35.500000,2.500000 -35.500000,5.200000" fill="#000000" stroke="#000000" transform="translate(494.263566,307.013189) rotate(270.000000) "/>
<line id="line72" x1="-25.75" y1="-0.25" x2="25.75" y2="0.25" fill="none" stroke="#000000" transform="translate(514.250000,403.250000) "/>
<text id="text25" x="-0.544434" y="5.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(491.044434,406.500000) ">
初顶回流泵</text>
<text id="31"  x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(123.489038,231.006846)">
111111</text>
<text id="32"  x="-1.54443" y="3.5" font-family="SimSun" font-size="12" text-anchor="start" transform="translate(69.989038,121.506846) ">
1111111</text>
</svg>
</div>
</body></html>
