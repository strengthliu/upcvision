function myBrowser() {
	alert("myBrowser");
    var userAgent = navigator.userAgent; // 取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; // 判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1
            && userAgent.indexOf("MSIE") > -1 && !isOpera; // 判断是否IE浏览器
    var isEdge = userAgent.indexOf("Edge") > -1; // 判断是否IE的Edge浏览器
    var isFF = userAgent.indexOf("Firefox") > -1; // 判断是否Firefox浏览器
    var isSafari = userAgent.indexOf("Safari") > -1
            && userAgent.indexOf("Chrome") == -1; // 判断是否Safari浏览器
    var isChrome = userAgent.indexOf("Chrome") > -1
            && userAgent.indexOf("Safari") > -1; // 判断Chrome浏览器

    if (isIE) {
    	alert("isIE");
    	window.location.href="downLoadBrowser.html";
// var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
// reIE.test(userAgent);
// var fIEVersion = parseFloat(RegExp["$1"]);
// if (fIEVersion == 7) {
// return "IE7";
// } else if (fIEVersion == 8) {
// return "IE8";
// } else if (fIEVersion == 9) {
// return "IE9";
// } else if (fIEVersion == 10) {
// return "IE10";
// } else if (fIEVersion == 11) {
// return "IE11";
// } else {
// return "0";
// }//IE版本过低
// return "IE";
    }
    if (isOpera) {
        return "Opera";
    }
    if (isEdge) {
        return "Edge";
    }
    if (isFF) {
        return "FF";
    }
    if (isSafari) {
        return "Safari";
    }
    if (isChrome) {
        return "Chrome";
    }
}

// 格式化日期
Date.prototype.Format = function (fmt) {
var o = {
  "y+": this.getFullYear(),
  "M+": this.getMonth() + 1,                 // 月份
  "d+": this.getDate(),                    // 日
  "h+": this.getHours(),                   // 小时
  "m+": this.getMinutes(),                 // 分
  "s+": this.getSeconds(),                 // 秒
  "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
  "S+": this.getMilliseconds()             // 毫秒
};
for (var k in o) {
  if (new RegExp("(" + k + ")").test(fmt)){
    if(k == "y+"){
      fmt = fmt.replace(RegExp.$1, ("" + o[k]).substr(4 - RegExp.$1.length));
    }
    else if(k=="S+"){
      var lens = RegExp.$1.length;
      lens = lens==1?3:lens;
      fmt = fmt.replace(RegExp.$1, ("00" + o[k]).substr(("" + o[k]).length - 1,lens));
    }
    else{
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
  }
}
return fmt;
}

var splitKeyServerPoint = "\\";
var splitKeyServerPre = "\\\\";
function splitServerPoint(tag){
	var ret = new Array();
	if(tag.indexOf(splitKeyServerPre)==0)
		tag = tag.substring(0,2);
	if(tag.indexOf(splitKeyServerPoint)>0){
		ret.push(tag.substring(0,tag.indexOf(splitKeyServerPoint)));
		ret.push(tag.substring(tag.indexOf(splitKeyServerPoint)+1));
		return ret;
	}else {
		ret.push("");
		ret.push(tag);
		return ret;
	}
}

function unicodeSpecialChar(url){
	var rchar = ["%3F","%23","%2B","%20","%26","%3D"];
	url = url.replace( /%/g,rchar[0]);
	url = url.replace(/#/g,rchar[1]);
	url = url.replace(/\+/g,rchar[2]);
	url = url.replace(/\?/g,rchar[3]);
	url = url.replace(/ /g,rchar[4]);
	url = url.replace(/&/g,rchar[5]);
	url = url.replace(/=/g,rchar[6]);
	return url;
}

var userSpace = null;
var serverList = null;
var users = null;
var userInfoList = null;
var token;
var user;
var _galleryKey = null;
var serverList;



/**
 * websocket变量。
 */
var socket;
// alert("websocket connected 1.");
var stompClient;
var subscribe;
var connected;

var socketRetryTimes = 3;

function checkToken(){
	if(localStorage.user != null && localStorage.user != "undefined")
		user = JSON.parse(localStorage.user);
	if(localStorage.token != null && localStorage.token != "undefined")
		token = localStorage.token;
	// 如果没有token或user，通过检查权限，将重新登录。
	if(user == null || token == null)
		checkRight();
}
// 避免刷新时

$(document).ready(function () {
    if (window.WebSocket){
    	connect();
    } else {
        alert("错误","浏览器不支持websocket技术通讯.");
    }
});

function connect(callback) {
	if(user==null || user =="undefined")
		return;
	console.log(" app.js connect....");
// disconnect();
// if (socket.readyState == 1) {
// return;
// }
// socket = new SockJS("${request.contextPath}/socketServer");
	socket = new SockJS('/socketServer');
	// 通过sock对象监听每个事件节点，非必须,这个必须放在stompClient的方法前面
	sockHandle();
    // 获取 STOMP 子协议的客户端对象
    stompClient = Stomp.over(socket);

	// 停止调试信息
	stompClient.debug = null;
	checkToken();
	sessionStorage.setItem('token', token);// 设置指定session值
	sessionStorage.setItem('uid', user.id);// 设置指定session值

	stompClient.heartbeat.outgoing = 10000; // 客户端每20000ms发送一次心跳检测
	stompClient.heartbeat.incoming = 10000; // client接收serever端的心跳检测

	// 连接服务器
	var headers = {
		login : user.id,
		token : token,
		session_id:"${session_id}",
		// additional header
		'client-id' : 'my-client-id'
	};
	
	if(socketRetryTimes>3){
		socketRetryTimes=0;
		alert("连续3次没有连接成功，请检查网络，或与系统管理员联系。");
		return;
	}else
		socketRetryTimes++;
	
	stompClient.connect(headers, function(){
		connected = true;
		console.log("websocket connected.");
		if(callback!=null && callback!="undefined")
			callback();
		},function(error){
			console.log("websocket连接出错： "+JSON.stringify(error));
		});
}

// 通过sock对象监听每个事件节点，非必须，这里开启了stomp的websocket 也不会生效了
function sockHandle() {

    // 连接成功后的回调函数
    socket.onopen = function () {
        console.log("------连接成功------");
    };

    // 监听接受到服务器的消息
    socket.onmessage = function (event) {
        console.log('-------收到的消息: ' + event.data);
    };

    // 关闭连接的回调函数
    socket.onclose = function (event) {
        console.log('--------关闭连接: connection closed.------');
    };

    // 连接发生错误
    socket.onerror = function () {
        alert("连接错误", "网络超时或通讯地址错误.");
        disconnect();
    } ;
}

function testSocketConnected(){
	// 发送消息给服务器
	try{
	stompClient.send("/app/message", {
		atytopic : "testMessage",
		type : 'testMessage',
		id : '122215432'
	}, JSON.stringify({
		'type' : 'testMessage',
		'id' : 1234567
	}));
	return true;
	}catch(error){
		connected = false;
		return false;
	}

}
function disconnect() {
	console.log('disconnect');
// if (socket.readyState != 1) {
// // return;
// }
	
	if (stompClient !== null && stompClient!="undefined") {
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
		
		stompClient.disconnect();
		socket.close();
        socket = null;
		console.log('do disconnect');
	}
	connected = false;
// setConnected(false);

	console.log("Disconnected");
}

function unsubscribe(){
// if(aabbcc == "undefined")
// console.log("yes");
// if(stompClient=="undefined" || stompClient==null )
// return;
	try{
	if (stompClient !== null) {
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
	}
	}catch(error){
		
	}
	console.log('unsubscribe');
}


function logout(){
	localStorage.user = null;
	user = null;
	localStorage.token = null;
	localStorage.routeList = null;
	token = null;
	userSpace = null;
	window.location.href = "login.html";
}

function loginByUserPassWord(uname, pwd) {
	var data = {
		'uname' : uname,
		'pwd' : pwd
	};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "login",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
// hideLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			console.log("登录成功返回： " + data);
			if (data.status == "000"){// GlobalConsts.ResultCode_SUCCESS) {
				// us = data.data;
				userSpace = data.data.userSpace;
				user = userSpace.user;
				token = userSpace.token;
// console.log("登录成功，用户名为： "+JSON.stringify(user)+" token="+token);
				window.userSpace = userSpace;
				localStorage.user = JSON.stringify(user);
				// console.log("登录成功，userSpace=" + JSON.stringify(userSpace));
				localStorage.token = token;
// alert("login end. wait..");
			} else {
				console.log("登录失败 ： " + data.msg);
// alert();
				window.location.href="login.html"
			}
			hideLoading();

			// alert("本地存储："+localStorage.user);
			window.location.href = "index.html";
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			hideLoading();
		}
	});
}

async function getUserSpace(uid, token, sucessFucn) {
	// console.log("async function getUserSpace uid="+uid+" token="+token);
	if(userSpace==null||userSpace=="undefined"){
		
	await $.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getUserSpace",
		// 提交的数据
		data : {
			uid : uid,
			token : token
		},
		async: false,
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
// console.log("getUserSpace -> "+JSON.stringify(data));
			if (data.status != "000"){// GlobalConsts.ResultCode_SUCCESS) { //
										// 不成功
// alert(data.msg);
// console.log("getUserSpace-> "+data.msg);
				localStorage.user = null;
				localStorage.token = null;
				userSpace = null;
				window.location.href = "login.html";

			}
			// alert(" getUserSpace : "+JSON.stringify(data));
			if (userSpace == null || userSpace == "undefined") {
				// console.log("getUserSpace -> set userSpace.");
				userSpace = data.data.userSpace;
// console.log("userspace: "+JSON.stringify(data));
				// return userSpace;
				window.userSpace = userSpace;
			}

			// alert(" getUserSpace : "+JSON.stringify(window.userSpace));
			if(sucessFucn!=null && sucessFucn!="undefined")
				sucessFucn(window.userSpace);
			// localStorage.user = JSON.stringify(data);
			// window.location.href ="index.html";
			hideLoading();
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			showLoading();
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			// 请求出错处理
			
			hideLoading();
		}
	});
	}else{
		sucessFucn(window.userSpace);
	}
}

function checkRight(uid, token, loginPage,sucessPage) {
	// console.log("checkRight token="+token);
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "checkAuthorcation",
		// 提交的数据
		data : {
			uid : uid,
			token : token
		},
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status != "000"){// GlobalConsts.ResultCode_SUCCESS) { //
										// 不成功
				// alert(data.msg);
				localStorage.user = null;
				localStorage.token = null;
				user = null;
				token = null;
				userSpace = null;
				if (loginPage == null || loginPage == "undefined")
					showLoading();
					console.log("checkright -> goto login.html");
					window.location.href = "login.html";
				
// if (loginPage != "login.html")
// window.location.href = loginPage;
			} else {
				if(sucessPage!=null && sucessPage!="undefined")
					window.location.href = sucessPage;
			}
			hideLoading();
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			// 请求出错处理
			if (loginPage != null && loginPage != "undefined" && loginPage !="login.html")
				window.location.href = "login.html";
			hideLoading();
		}
	});
}


var loadStartTime;
function showLoading() {
	loadStartTime = Date.now();
	setTimeout(function() {
		_showLoading();
	}, 500);
}

function _showLoading() {
	if (loadStartTime != 0)
		$("#loading").show();
}

function hideLoading() {
	var currentTime = Date.now();
	var difference = currentTime - loadStartTime;
	// console.log(difference);
	if (difference < 500)
		loadStartTime = 0;
	else {
		if (difference < 1000) {
			setTimeout(function() {
				$("#loading").hide();
			}, 1000);
		} else
			$("#loading").hide();
	}
}

// addLoadListener(unsubscribe);
// addLoadListener(disconnect);

var departdata ;
fillDepartmentData();
function fillDepartmentData() {
	var data = {
		'uid' : 2,
		'token' : token
	};
// console.log("fdsfdsafdsa");
	$.ajax({
		// 提交数据的类型 POST
		// GET
		type : "POST",
		// 提交的网址
		url : "getDepartList",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml",
		// "html",
		// "script",
		// "json",
		// "jsonp",
		// "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS)
				console.log("department => " + JSON.stringify(data));
				var departs = data.data.departs;
				departdata = new Array();
				for(var inddep=0;inddep<departs.length;inddep++){
					var dep = new Object();
					var depdata = departs[inddep];
					Object.keys(depdata).forEach(function(key){
						var value = depdata[key];
						if(key=="id") value = value+"";
						dep[key] = value;
					});
					departdata.push(dep);
				}
			} else {
				console.log("department => " + JSON.stringify(data));
				console.log("department => " + "失败11 ： " + data.msg);
// alert("失败11 ： " + data.msg);
			}
			hideLoading();
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			hideLoading();
		}
	});
}

function addLoadListener(fn){
    if (typeof window.addEventListener != 'undefined'){
        window.addEventListener('load',fn,false);
    }else if(typeof document.addEventListener != 'undefined'){
        document.addEventListener('load',fn,false);
    }else if (typeof window.attachEvent != 'undefined'){
        window.attachEvent('onload',fn);
    }else{
        var oldfn = window.onload;
        if(typeof window.onload != 'function'){
            window.onload = fn;
        }else{
            window.onload = function(){
                oldfn();
                fn();
            };
        }
    }
}


function getGraphByID(graph,id,l){
	if(l==null||l=="undefined")
		l=0;
	l++;
	var level=l;
	step++;
// console.log(" ============ "+step+" === "+step+" === "+step+" === ");
	if(id==null||id=="undefined"){
		_graphs = graph;
		step ++;
// console.log("level="+level+" "+step+" = return path 1 : " + graph.name+"
// id="+graph.id);
		return graph;
	}
	if(graph.id == id) {
		_graphs = graph;
		step ++;
// console.log("level="+level+" "+step+" = 得到了结果 return path 2 : " +
// graph.name+" id="+graph.id);
		return graph;
	} 

	{
		var children = graph.children;
		if(children!=null){
			var ids = new Array();
			Object.keys(children).forEach(function(key){
				ids.push(children[key]);
			});
			
			for(var i=0;i<ids.length;i++){
				var r = getGraphByID(ids[i],id,level);
				if(r!=null) {
					step ++;
// console.log("level="+level+" "+step+" = 得到了结果 return path 3 : " +
// graph.name+" id="+graph.id);
					return r;
				}
			}
			step ++;
// console.log("level="+level+" "+step+" = null null 4 : " + graph.name+"
// id="+graph.id);
			return null;
		} 
		else 
		{
			step ++;
// console.log("level="+level+" "+step+" = null null 5 : " + graph.name+"
// id="+graph.id);
			return null;				
		}
	}
}


function addLoadListener(fn){
    if (typeof window.addEventListener != 'undefined'){
        window.addEventListener('load',fn,false);
    }else if(typeof document.addEventListener != 'undefined'){
        document.addEventListener('load',fn,false);
    }else if (typeof window.attachEvent != 'undefined'){
        window.attachEvent('onload',fn);
    }else{
        var oldfn = window.onload;
        if(typeof window.onload != 'function'){
            window.onload = fn;
        }else{
            window.onload = function(){
                oldfn();
                fn();
            };
        }
    }
}