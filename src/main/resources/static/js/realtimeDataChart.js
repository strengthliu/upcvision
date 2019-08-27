/**
 * 
 */

var gl = new Array();

console.log("_realtimeDataDetailKey: "+_realtimeDataDetailKey);

function newItemAction(){
	alert("realtimeData.js newItemAction。 从mainPanel中调用的。");
}

if(userSpace==null || userSpace == "undefined") { 
	console.log("userSpace 为空，重新到服务器去取。");
	getUserSpace(user.id,token,updateRealTimeDataChart);
} else
	updateRealTimeDataChart(userSpace);
	
function updateRealTimeDataChart(ruserSpace){
	var pointGroup = ruserSpace.realTimeData[_realtimeDataDetailKey];
	var uirealtimeDataPoints = document.getElementById("ui-realtimeDataPoints");
	// console.log(" updateRealTimeDataChart => "+JSON.stringify(pointGroup));
	if(pointGroup ==null || pointGroup == "undefined") return;
	var pointList = pointGroup.pointList;
	var innerHtml = "";
	for(var indpl=0;indpl<pointList.length;indpl++){
		// console.log(" updateRealTimeDataChart=>  "+JSON.stringify(pointList[indpl]));
		
		// 页面加一块
		var item = '<div class="box col-lg-3"><div class="gauge" id="point_'+pointList[indpl].tagName+'"></div></div>';
		innerHtml += item;
	}
	uirealtimeDataPoints.innerHTML = innerHtml;
	console.log(uirealtimeDataPoints.innerHTML);

	
	for(var indpl=0;indpl<pointList.length;indpl++) {
		console.log();
		// 对象加一条
		var gt = new JustGage({
		    id: "point_"+pointList[indpl].tagName,
		    value: getRandomInt(0, 100),
		    min: 0,
		    max: 100,
		    title: "一级电脱盐混合阀压差",
		    label: "度",
		    donut: true,
		    gaugeWidthScale: 0.6,
		    counter: true,
		    hideInnerShadow: true
		});
		gl[indpl] = gt;	
	}
}
/**
 * 刷新数据
 * @returns
 */
function refreshGage(){
	for(var indpl=0;indpl<gl.length;indpl++) {
			gl[indpl].refresh(getRandomInt(50, 100));
	}
}
setInterval(refreshGage, 1000);

/**
 * 右键菜单
 * @param $
 * @returns
 */
function menuFunc(key,options){
	switch(key){
	case "edit":
		stompClient.send("/app/aaa", {atytopic:"greetings"}, JSON.stringify({'name': user.name,'content':"hellofdjksla"}));
		alert(key);
		break;
	}
//	alert(JSON.stringify(options));
}

(function($) {
	if(user == null || user == "undefined"){
		user = JSON.parse(localStorage.user);
		if(user == null || user == "undefined") {
			alert("登录超时，请重新登录。");
			window.location.href="login.html";
		}
	}
	if(user.role<3)
	  $.contextMenu({
	    selector: '#ui-realtimeDataPoints',
	    callback: menuFunc,
	    items: {
	      "share": {
		        name: "分享",
		        icon: "share"
		      },
	      "edit": {
	        name: "修改",
	        icon: "edit"
	      },
	      "delete": {
	        name: "删除",
	        icon: "delete"
	      },
	      "sep1": "---------",
	      "quit": {
	        name: "Quit",
	        icon: function() {
	          return 'context-menu-icon context-menu-icon-quit';
	        }
	      }
	    }
	  });
})(jQuery);




var stompClient = null;
//var _realtimeDataDetailKey = null;
function setConnected(connected) {
//	$("#connect").prop("disabled", connected);
//	$("#disconnect").prop("disabled", !connected);
//	if (connected) {
//		$("#conversation").show();
//	} else {
//		$("#conversation").hide();
//	}
//	$("#greetings").html("");
}

 loginWebsocket();
function loginWebsocket() {
//	console.log("debug 1");
//	const chat_id = document.getElementById("chat_id").value;
//	const chat_name = document.getElementById("chat_name").value;
//	localStorage.setItem("chat_id", chat_id);
//	localStorage.setItem("chat_name", chat_name);
//	document.getElementById("main-content").hidden = null;
//	document.getElementById("login_div").hidden = "hidden";
	setTimeout(connect(), 1000)
	console.log("debug 2");
}

function connect() {
	//const id = localStorage.getItem("chat_id");
	//var socket = new SockJS('ws://localhost:8888/socketServer/');
	var socket = new SockJS('/socketServer');
	//alert("websocket connected 1.");
	stompClient = Stomp.over(socket);
	//连接服务器
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log("websocket connected.");
		// console.log('Connected: ' + frame);
        //连接成功后，主动拉取未读消息
        pullUnreadMessage("/topic/reply");
		// 接收消息设置
		stompClient.subscribe('/topic/greeting/' + _realtimeDataDetailKey, function(greeting) {
			alert("websocket connected 3.");
			// 收到消息后处理
			showGreeting(JSON.parse(greeting.body).content);
		});

		// 接收消息设置。该方法是接收广播消息。
        stompClient.subscribe('/topic/greeting/11', function(greeting){
            showGreeting(JSON.parse(greeting.body).content);
        });

		// 接收消息设置。该方法表示接收一对一消息，其主题是"/user/"+userId+"/message"，不同客户端具有不同的id。
        // 如果两个或多个客户端具有相同的id，那么服务器端给该userId发送消息时，这些客户端都可以收到。
        stompClient.subscribe('/user/' + user.id + '/message',function(greeting){
            alert(JSON.parse(greeting.body).content);
            showGreeting(JSON.parse(greeting.body).content);
        });
		
		// 发送消息给服务器
		//stompClient.send("/socketServer/", {atytopic:"greetings"}, JSON.stringify({'name': user.name,'content':"hellofdjksla"}));

		//alert("websocket connected 2.");

	});
}
//从服务器拉取未读消息
function pullUnreadMessage(destination) {
    $.ajax({
        url: "/wsTemplate/pullUnreadMessage",
        type: "POST",
        dataType: "json",
        async: true,
        data: {
            "destination": destination
        },
        success: function (data) {
            if (data.result != null) {
                $.each(data.result, function (i, item) {
                    log(JSON.parse(item).content);
                })
            } else if (data.code !=null && data.code == "500") {
                layer.msg(data.msg, {
                    offset: 'auto'
                    ,icon: 2
                });
            }
        }
    });
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
	console.log(message);
}
/*
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
*/
/*
var websocket = null;
if('WebSocket' in window){
	websocket = new WebSocket('ws://localhost:8888/socketServer');
}else{
	alert('该浏览器不支持websocket');
}

websocket.onopen=function(e){
	console.log('websocket建立连接');
	websocket.send('websocket建立连接');
}
websocket.onclose=function(e){
	console.log('websocket关闭连接');
}
websocket.onmessage=function(e){
	console.log(e,'websocket收到消息');
	document.getElementById('msgs').innerHTML = document.getElementById('msgs').innerHTML+'<br/>'+e.data;
}
websocket.onerror = function (event) {
    console.log('websocket通信发生错误');

}
window.onbeforeunload = function (event) {
    websocket.close();
}
document.getElementById('send').onclick=function(){
	var msg = document.getElementById('msg').value;
	websocket.send(msg);
}
*/
