var userSpace = null;
var serverList = null;

var stompClient = null;
//var _realtimeDataDetailKey = null;
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	} else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}
// connect();
function connect() {
	const id = localStorage.getItem("chat_id");
	//var socket = new SockJS('ws://localhost:8888/socketServer/admin');
	var socket = new SockJS('/socketServer');
	//alert("websocket connected 1.");
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		//alert("websocket connected.");
		// console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings/' + id, function(greeting) {
			//alert("websocket connected 3.");
			showGreeting(JSON.parse(greeting.body).content);
		});
		//alert("websocket connected 2.");

	});
}
function loginWebsocket() {
	console.log("debug 1");
	const chat_id = document.getElementById("chat_id").value;
	const chat_name = document.getElementById("chat_name").value;
	localStorage.setItem("chat_id", chat_id);
	localStorage.setItem("chat_name", chat_name);
	document.getElementById("main-content").hidden = null;
	document.getElementById("login_div").hidden = "hidden";
	setTimeout(connect(), 1000)
	console.log("debug 2");
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendName() {
	const chat_id = localStorage.getItem("chat_id");
	const chat_name = localStorage.getItem("chat_name")
	const content = document.getElementById("content").value;
	stompClient.send("/app/hello/" + chat_id, {}, JSON.stringify({
		'name' : chat_name,
		'content' : content
	}));
}

function showGreeting(message) {
	$("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	$("#connect").click(function() {
		connect();
	});
	$("#disconnect").click(function() {
		disconnect();
	});
	$("#send").click(function() {
		sendName();
	});
});


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
			$("#msg").html("logining");
		},
		// 成功返回之后调用的函数
		success : function(data) {
			console.log("登录成功返回： "+data);
			if (data.status == GlobalConsts.ResultCode_SUCCESS) {
				// us = data.data;
				userSpace = data.data.userSpace;
				user = userSpace.user;
				token = userSpace.token;
				//console.log("登录成功，token="+token);
				//alert("登录成功能 ，用户名为： "+JSON.stringify(user)+" token="+token);
				window.userSpace = userSpace;
				localStorage.user = JSON.stringify(user);
				console.log("登录成功，user="+JSON.stringify(userSpace));
				localStorage.token = token;
			} else {
				alert("登录失败 ： "+data.msg);
			}
			
			//alert("本地存储："+localStorage.user);
			window.location.href = "index.html";
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			//alert(XMLHttpRequest.responseText);
			//alert(textStatus);
			// HideLoading();
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
		}
	});
}
function getUserSpace(uid,token,sucessFucn) {
    
    $.ajax({
      // 提交数据的类型 POST GET
      type:"POST",
      // 提交的网址
      url:"getUserSpace",
      // 提交的数据
      data:{uid:uid,token:token},
      // 返回数据的格式
      datatype: "json",// "xml", "html", "script", "json", "jsonp", "text".
      // 在请求之前调用的函数
      beforeSend:function(){
      	// $("#msg").html("logining");
      },
      // 成功返回之后调用的函数
      success:function(data){
    	 //console.log(JSON.stringify(data));
    	  if(data.status != GlobalConsts.ResultCode_SUCCESS) { // 不成功
    		  alert(data.msg);
    		  window.location.href="login.html";
    		  
    	  }
      	//alert(" getUserSpace : "+JSON.stringify(data));
      	if(userSpace == null || userSpace == "undefined") {
      		//console.log("getUserSpace -> set userSpace.");
      		userSpace = data.data.userSpace;
      		// alert(JSON.stringify(userSpace));
      		//return userSpace;
      		window.userSpace = userSpace;
      	}
      	
      	//alert(" getUserSpace : "+JSON.stringify(window.userSpace));
      	sucessFucn(window.userSpace);
        //localStorage.user = JSON.stringify(data);
        //window.location.href ="index.html";
      }   ,
      // 调用执行后调用的函数
      complete: function(XMLHttpRequest, textStatus){
        //alert(XMLHttpRequest.responseText);
        //alert(textStatus);
        // HideLoading();
      },
      // 调用出错执行的函数
      error: function(jqXHR, textStatus, errorThrown){
        // 请求出错处理
        alert(errorThrown.toString());
      }
    });

}
function checkRight(uid,token) {
      console.log("checkRight token="+token);
      $.ajax({
        // 提交数据的类型 POST GET
        type:"POST",
        // 提交的网址
        url:"checkAuthorcation",
        // 提交的数据
        data:{uid:uid,token:token},
        // 返回数据的格式
        datatype: "json",// "xml", "html", "script", "json", "jsonp", "text".
        // 在请求之前调用的函数
        beforeSend:function(){
        	// $("#msg").html("logining");
        },
        // 成功返回之后调用的函数
        success:function(data){
      	  if(data.status != GlobalConsts.ResultCode_SUCCESS) { // 不成功
    		  alert(data.msg);
    		  window.location.href="login.html";
    	  }

        	//alert(" aaaa : "+JSON.stringify(data));
          //localStorage.user = JSON.stringify(data);
          //window.location.href ="index.html";
        }   ,
        // 调用执行后调用的函数
        complete: function(XMLHttpRequest, textStatus){
          //alert(XMLHttpRequest.responseText);
          //alert(textStatus);
          // HideLoading();
        },
        // 调用出错执行的函数
        error: function(jqXHR, textStatus, errorThrown){
          // 请求出错处理
          alert(errorThrown.toString());
          window.location.href ="login.html";
        }
      });

      // alert(JSON.stringify(localStorage.user));
      // document.getElementById("name").value = localStorage.weixinname;
    }

