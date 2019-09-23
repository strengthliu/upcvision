
//格式化日期
Date.prototype.Format = function (fmt) {
var o = {
  "y+": this.getFullYear(),
  "M+": this.getMonth() + 1,                 //月份
  "d+": this.getDate(),                    //日
  "h+": this.getHours(),                   //小时
  "m+": this.getMinutes(),                 //分
  "s+": this.getSeconds(),                 //秒
  "q+": Math.floor((this.getMonth() + 3) / 3), //季度
  "S+": this.getMilliseconds()             //毫秒
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

var userSpace = null;
var serverList = null;
var users = null;
var userInfoList = null;


/**
 * websocket变量。
 */
var socket;
// alert("websocket connected 1.");
var stompClient;
var subscribe;
var connected;

var socketRetryTimes = 3;

connect();
//避免刷新时
function connect(callback) {
	console.log(" app.js connect....");
//	disconnect();
//	if (socket.readyState == 1) {
//		return;
//	}
	socket = new SockJS('/socketServer');
	stompClient = Stomp.over(socket);

	sessionStorage.setItem('token', token);// 设置指定session值
	sessionStorage.setItem('uid', user.id);// 设置指定session值

	// const id = localStorage.getItem("chat_id");
	// var socket = new SockJS('ws://localhost:8888/socketServer/');
	socket = new SockJS('/socketServer');
	// alert("websocket connected 1.");
	stompClient = Stomp.over(socket);
	stompClient.heartbeat.outgoing = 10000; // 客户端每20000ms发送一次心跳检测
	stompClient.heartbeat.incoming = 10000; // client接收serever端的心跳检测
	// 连接服务器
	var headers = {
		login : user.id,
		token : token,
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
		if(callback!=null && callback!="undefined")
			callback();
		});
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
//	if (socket.readyState != 1) {
////		return;
//	}
	if (stompClient !== null) {
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
		
		stompClient.disconnect();
		console.log('do disconnect');
	}
	connected = false;
//	setConnected(false);

	console.log("Disconnected");
}

function unsubscribe(){
//	if(aabbcc == "undefined")
//		console.log("yes");
//	if(stompClient=="undefined" || stompClient==null )
//		return;
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
	token = null;
	window.location.href = "index.html";
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
//			hideLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			//console.log("登录成功返回： " + data);
			if (data.status == GlobalConsts.ResultCode_SUCCESS) {
				// us = data.data;
				userSpace = data.data.userSpace;
				user = userSpace.user;
				token = userSpace.token;
				// console.log("登录成功，token="+token);
				// alert("登录成功能 ，用户名为： "+JSON.stringify(user)+" token="+token);
				window.userSpace = userSpace;
				localStorage.user = JSON.stringify(user);
				console.log("登录成功，user=" + JSON.stringify(userSpace));
				localStorage.token = token;
			} else {
				alert("登录失败 ： " + data.msg);
			}
			hideLoading();

			// alert("本地存储："+localStorage.user);
			window.location.href = "index.html";
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			// alert(XMLHttpRequest.responseText);
			// alert(textStatus);
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			/* 弹出jqXHR对象的信息 */
			// alert(jqXHR.responseText);
			// alert(jqXHR.status);
			// alert(jqXHR.readyState);
			// alert(jqXHR.statusText);
			/* 弹出其他两个参数的信息 */
			// alert(textStatus);
			// alert(errorThrown);
			hideLoading();
		}
	});
}

function getUserSpace(uid, token, sucessFucn) {

	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getUserSpace",
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
			// console.log(JSON.stringify(data));
			if (data.status != GlobalConsts.ResultCode_SUCCESS) { // 不成功
				alert(data.msg);
				window.location.href = "login.html";

			}
			// alert(" getUserSpace : "+JSON.stringify(data));
			if (userSpace == null || userSpace == "undefined") {
				// console.log("getUserSpace -> set userSpace.");
				userSpace = data.data.userSpace;
				console.log("userspace: "+JSON.stringify(userSpace));
				// return userSpace;
				window.userSpace = userSpace;
			}

			// alert(" getUserSpace : "+JSON.stringify(window.userSpace));
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
			hideLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status != GlobalConsts.ResultCode_SUCCESS) { // 不成功
				// alert(data.msg);
				localStorage.user = null;
				localStorage.token = null;
				if (loginPage == null || loginPage == "undefined")
					window.location.href = "login.html";
				
//				if (loginPage != "login.html")
//					window.location.href = loginPage;
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
			//alert("访问服务出错："+textStatus);
			if (loginPage != null && loginPage != "undefined" && loginPage !="login.html")
				window.location.href = "login.html";
			hideLoading();
		}
	});

	// alert(JSON.stringify(localStorage.user));
	// document.getElementById("name").value = localStorage.weixinname;
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
	//console.log(difference);
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

//addLoadListener(unsubscribe);
//addLoadListener(disconnect);

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

