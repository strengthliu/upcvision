
/**
 * websocket变量。
 */
var socket = null;
// alert("websocket connected 1.");
var stompClient = null;
var subscribe = null;
socket = new SockJS('/socketServer');
stompClient = Stomp.over(socket);
var connected = true;

var socketRetryTimes = 3;


//避免刷新时
function connect(callback) {
	console.log(" app.js connect....");
//	disconnect();
//	if (socket.readyState == 1) {
//		return;
//	}

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
	if (stompClient !== null) {
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
	}
	console.log('unsubscribe');
}
