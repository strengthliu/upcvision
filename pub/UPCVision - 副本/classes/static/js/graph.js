/**
 * 
 */
//var atags = document.getElementsByTagName("a");
//for(var inda = 0;inda<atags.length;inda++){
//	console.log("tag"+inda+" = "+atags[inda].outerHTML);
//	Object.keys(atags[inda]).forEach(function(key){
//		console.log("atags[inda]["+key+"] = "+JSON.stringify(atags[inda][key]));
//	});
//	console.log(JSON.stringify(Object.keys(atags[inda])));
////	alert(atags[inda].attributes["xlink"]);
//	atags[inda].onclick=function(){
//		//atags[inda]
////		alert(this.attributes["xlink:href"]);
//		return false;
//	}
//}

////编辑文章时阻止a标签跳转
//$(document).find("a").click(function(e){
//    //如果提供了事件对象，则这是一个非IE浏览器 
//        if ( e && e.preventDefault ) {
//        		console.log("preventDefault 阻止默认浏览器动作(W3C)");
//                    //阻止默认浏览器动作(W3C) 
//                    e.preventDefault(); 
//            }else{
//        		console.log("IE中阻止函数器默认动作");
//                //IE中阻止函数器默认动作的方式 
//                window.event.returnValue = false; 
//                return false;
//            }    
//    });


//
/**
 * 右键菜单
 * 
 * @param $
 * @returns
 */
function menuFunc(key, options) {
	switch (key) {
	case "reconnect":
		disconnect();
		connect();
		break;
	case "disconnect":
		disconnect();
		//stompClient.disconnect();
		break;
	case "edit":
		stompClient.send("/app/aaa", {
			atytopic : "greetings"
		}, JSON.stringify({
			'name' : user.name,
			'content' : "hellofdjksla"
		}));
		alert(key);
		break;
	}
	// alert(JSON.stringify(options));
}

(function($) {
	if (user == null || user == "undefined") {
		user = JSON.parse(localStorage.user);
		if (user == null || user == "undefined") {
			alert("登录超时，请重新登录。");
			window.location.href = "login.html";
		}
	}
	if (user.role < 3)
		$.contextMenu({
			selector : '#_diagramShow',
			callback : menuFunc,
			items : {
				"disconnect" : {
					name : "断开连接数据",
					icon : "share"
				},
				"reconnect" : {
					name : "重新连接数据",
					icon : "edit"
				},
				"delete" : { 
					name : "删除",
					icon : "delete"
				},
				"sep1" : "---------",
				"quit" : {
					name : "Quit",
					icon : function() {
						return 'context-menu-icon context-menu-icon-quit';
					}
				}
			}
		});
})(jQuery);

function refreshData(data) {
	var ids = document.getElementsByTagName("text");
//	console.log(" graph refreshData:"+JSON.stringify(data));
	var _data = JSON.parse(data.body);
console.log(JSON.stringify(_data));
	Object.keys(_data).forEach(function(key){
		var ele = document.getElementById(key);
		if(ele!=null && ele!="undefined"){
			console.log(" set value for "+key);
			ele.innerHTML =  Math.round(_data[key]*10000)/10000;
		}else{
			console.log("no element named "+key);
		}
	});
}

// var _xyGraphDetailKey = null;
function setConnected(connected) {
	// $("#connect").prop("disabled", connected);
	// $("#disconnect").prop("disabled", !connected);
	// if (connected) {
	// $("#conversation").show();
	// } else {
	// $("#conversation").hide();
	// }
	// $("#greetings").html("");
}

loginWebsocket();

function loginWebsocket() {
	console.log(" debug 1");

	if(!connected) {
		connect(graphSubscribe);
		return;
	}
	else {
		console.log("当前存在");
		graphSubscribe();
	}
}

addListener();
function addListener(){
	if(userSpace==null|userSpace=="undefined"){
		return getUserSpace(uid,token,addListener);
	}else{
		if(_graphId==null||_graphId=="undefined"){
			//console.log(" graph.js -> graphSubscribe() -> getGraphByURLPath(graph,_diagramShowKey="+_diagramShowKey+")");
			var g = getGraphByPath(userSpace.graph,_diagramShowKey);
			if(g == null || g == "undefined" || g.id==null ||g.id=="undefined"){
//				alert("您没有查看这个图形的权限。");
				return;
			}
			var ts = g.pointTextIDs;
			for(var indts=0;indts<ts.length;indts++){
				  var d = document.getElementById(ts[indtx]);
				  _a.onclick=function(){
					  console.log("=> text "+ts[indtx]+" be clicked.");
					  addXYRealTimeHistoryGraph(g.points[indts]);
				  }
			}
		}
	}
}

function addXYRealTimeHistoryGraph(point){
	console.log("addXYRealTimeHistoryGraph->point:"+JSON.stringify(point));
}

function getGraphByURLPath(graph,urlPath){
	if(urlPath==null||urlPath=="undefined") return graph;
	console.log("graph.urlPath="+graph.urlPath.toLowerCase()+"  ==  "+"urlPath="+urlPath.toLowerCase());
	if(graph.urlPath.toLowerCase() == urlPath.toLowerCase()){
		console.log("找到了");
		_graphId = graph.id;
		return graph;
	}
	else {
		console.log(graph.urlPath.toLowerCase()+" 3");
		var children = graph.children;
		if(children!=null){
			var keys = new Array();
			Object.keys(children).forEach(function(key){
				console.log("4"+key);
				keys.push(key);
				children[key]
			});
			for(var i=0;i<keys.length;i++){
				var child = children[keys[i]];
				if(urlPath.toLowerCase().indexOf(child.urlPath)!=-1){
					return getGraphByURLPath(child,urlPath);
				}
			}
		}
		return null;
	}
}
function getGraphByPath(graph,path){
	if(path==null||path=="undefined") return graph;
	//console.log("graph.wholePath="+graph.wholePath.toLowerCase()+"  ==  "+"path="+path.toLowerCase());
	if(graph.wholePath.toLowerCase().indexOf(path.toLowerCase())!=-1){
		//console.log("找到了");
		_graphId = graph.id;
		return graph;
	}
	else {
		console.log(graph.wholePath.toLowerCase()+" 3");
		var children = graph.children;
		if(children!=null){
			var keys = new Array();
			Object.keys(children).forEach(function(key){
				//console.log("4"+key);
				keys.push(key);
				children[key]
			});
			for(var i=0;i<keys.length;i++){
				var child = children[keys[i]];
				var t = getGraphByPath(child,path);
				if(t!=null) return t;
			}
		}
		return null;
	}
}


function graphSubscribe(){
	//console.log(" debug 1");
	if(userSpace==null|userSpace=="undefined"){
		return getUserSpace(uid,token,graphSubscribe);
	}else{
		if(_graphId==null||_graphId=="undefined"){
			//console.log(" graph.js -> graphSubscribe() -> getGraphByURLPath(graph,_diagramShowKey="+_diagramShowKey+")");
			var g = getGraphByPath(userSpace.graph,_diagramShowKey);
			if(g == null || g == "undefined" || g.id==null ||g.id=="undefined"){
				alert("您没有查看这个图形的权限。");
				return;
			}
			
			_graphId = g.id;
			
		}
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
		stompClient.send("/app/aaa", {
			atytopic : _graphId,
			type : 'graph',
			id : _graphId+""
		}, JSON.stringify({
			'type' : 'graph',
			'id' : _graphId+""
		}));
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_Graph_pre_/'
				+ _graphId, function(data) {
			// alert("websocket connected 3.");
			// 收到消息后处理
			refreshData(data);
		});
	}
}

function connectlocal() {
	sessionStorage.setItem('token', token);// 设置指定session值
	sessionStorage.setItem('uid', user.id);// 设置指定session值

	// const id = localStorage.getItem("chat_id");
	// var socket = new SockJS('ws://localhost:8888/socketServer/');
	socket = new SockJS('/socketServer');
	// alert("websocket connected 1.");
	stompClient = Stomp.over(socket);
	stompClient.heartbeat.outgoing = 10000; // 客户端每20000ms发送一次心跳检测
	stompClient.heartbeat.incoming = 10000;     // client接收serever端的心跳检测
	// 连接服务器
	var headers = {
		    login: user.id,
		    token: token,
		    // additional header
		    'client-id': 'my-client-id'
		};

	stompClient.connect(headers, function(frame) {
		setConnected(true);
		console.log("websocket connected.  .");
		// console.log('Connected: ' + frame);

		// 发送消息给服务器
		stompClient.send("/app/aaa", {
			atytopic : _graphId,
			type : 'Key_Graph_pre_',
			id : _graphId
		}, JSON.stringify({
			'type' : 'gGraph',
			'id' : _graphId
		}));
		// 连接成功后，主动拉取未读消息
		// pullUnreadMessage("/topic/reply");
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_Graph_pre_/'
				+ _graphId, function(data) {
			// alert("websocket connected 3.");
			// 收到消息后处理
			refreshData(data);
		});

		/**
		 * // 接收消息设置。该方法是接收广播消息。 stompClient.subscribe('/topic/greeting/11',
		 * function(greeting){ showGreeting(JSON.parse(greeting.body).content);
		 * }); //
		 * 接收消息设置。该方法表示接收一对一消息，其主题是"/user/"+userId+"/message"，不同客户端具有不同的id。 //
		 * 如果两个或多个客户端具有相同的id，那么服务器端给该userId发送消息时，这些客户端都可以收到。
		 * stompClient.subscribe('/user/' + user.id +
		 * '/message',function(greeting){
		 * alert(JSON.parse(greeting.body).content);
		 * showGreeting(JSON.parse(greeting.body).content); });
		 * 
		 */
		// alert("websocket connected 2.");
		stompClient.ws.onclose = function() {
			connect();
		}
		stompClient.ws.onerror = function() {
			connect();
		}
	},function(message){
		console.log(message);
	});
}

// 从服务器拉取未读消息
function pullUnreadMessage(destination) {
	$.ajax({
		url : "/wsTemplate/pullUnreadMessage",
		type : "POST",
		dataType : "json",
		async : true,
		data : {
			"destination" : destination
		},
		success : function(data) {
			if (data.result != null) {
				$.each(data.result, function(i, item) {
					log(JSON.parse(item).content);
				})
			} else if (data.code != null && data.code == "500") {
				layer.msg(data.msg, {
					offset : 'auto',
					icon : 2
				});
			}
		}
	});
}

