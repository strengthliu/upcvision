/**
 * 
 */

var gl = new Array();
var charts = new Object();

console.log("_historyDataDetailKey: " + _routeID);
var _historyDataDetailKey = _routeID;
function newItemAction() {
	alert("historyData.js newItemAction。 从mainPanel中调用的。");
}

if (userSpace == null || userSpace == "undefined") {
	console.log("userSpace 为空，重新到服务器去取。");
	getUserSpace(user.id, token, updateHistoryDataChart);
} else
	updateHistoryDataChart(userSpace);



/**
 * 画点图
 */
function updateHistoryDataChart(ruserSpace) {
	var pointGroup = ruserSpace.historyData[_historyDataDetailKey];
	var uihistoryDataPoints = document.getElementById("ui-historyDataPoints");
	 console.log(" updateHistoryDataChart => "+JSON.stringify(pointGroup));
	if (pointGroup == null || pointGroup == "undefined")
		return;
	var pointList = pointGroup.pointList;
	var innerHtml = "";
	// console.log("pointList" + JSON.stringify(pointList));
	for (var indpl = 0; indpl < pointList.length; indpl++) {
// console.log(" updateHistoryDataChart=> "+JSON.stringify(pointList[indpl]));
		try{
			// 页面加一块
			var item = '<div class="box col-lg-3"><div class="gauge" id="point_'
					+ pointList[indpl].tagName + '"></div></div>';
			innerHtml += item;
		}catch(e){
			
		}
	}
	uihistoryDataPoints.innerHTML = innerHtml;
	console.log(uihistoryDataPoints.innerHTML);

	for (var indpl = 0; indpl < pointList.length; indpl++) {
		console.log();
		// 对象加一条
		var gt = new JustGage({
			id : "point_" + pointList[indpl].tagName,
			value : 0,
			min : 0,
			max : 100,
			title : pointList[indpl].desc,//"一级电脱盐混合阀压差",
			label : pointList[indpl].enunit,
			donut : true,
			gaugeWidthScale : 0.6,
			counter : true,
			hideInnerShadow : true
		});
		var _tagName_ = pointList[indpl].tagName;
		charts[_tagName_] = indpl;
		gl[indpl] = gt;
	}
}
/**
 * 刷新数据
 * 
 * @returns
 */

function refreshData(data) {
	var pointList_ = JSON.parse(data.body);
	for(var key in pointList_){
		for(var p in gl){
			//console.log(gl[p].config.id + "  ==  "+"point_" + key );
			if(gl[p].config.id == "point_" + key){
				gl[p].refresh(pointList_[key]);
			}
		}
	}
}

/**
 * 更新线图
 * @returns
 */
//updateLineChart();

/**
 * 总值
 */
var _data;
var _dataCount;
/**
 * 当前值范围
 */
var cdata;
var cdataCount;

/**
 * x轴范围，y轴范围
 */

/**
 * 向前，向后
 */
function _forward(newData){
	c3LineChart.flow({
        columns: [
            ['x', '2013-03-01', '2013-03-02'],
            ['data1', 200, 300],
            ['data2', 150, 250],
            ['data3', 100, 100]
        ],
        length: 2,
        duration: 1000,
        done:function (){
        	}
    }
}

function _backward(){
	
}
/** 
 * x轴放大缩小
 */
function zoomin_x(){
	c3LineChart.axis.min({x: -10});
}
function zoomout_x(){
	
}
function zoomin_y(){
	c3LineChart.axis.min({x: -10});
}
function zoomout_y(){
	
}

 /**
  * 换X轴
  */
function changex(){
	
}

/**
 * 刷新值
 * 
 */
function addData(newData){
	c3LineChart.flow({
        columns: [
            ['x', '2013-03-01', '2013-03-02'],
            ['data1', 200, 300],
            ['data2', 150, 250],
            ['data3', 100, 100]
        ],
        length: 2,
        duration: 1000,
        done:function (){
        	}
    }
}

var c3LineChart ;
(function($) {
	  'use strict';
	  c3LineChart = c3.generate({
	    bindto: '#ui-historyDataLineChart',
	    data: {
	        x: 'x',
	      columns: [
	            ['x', '2013-01-01', '2013-01-02', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06'],
	            ['data1', 130, 150, 200, 300, 200, 100]
	      ],
	      type: 'spline'
	    },
	    grid: {
	        x: {
	            show: true
	        },
	        y: {
	            show: true
	        }
	    },
	    color: {
	      pattern: ['rgba(88,216,163,1)', 'rgba(237,28,36,0.6)', 'rgba(4,189,254,0.6)']
	    },
	    padding: {
	      top: 0,
	      right: 0,
	      bottom: 30,
	      left: 0,
	    }
	  });


})(jQuery);

setTimeout(function () {
    chart.axis.labels({y2: 'New Y2 Axis Label'});
}, 1000);

setTimeout(function () {
    chart.axis.labels({y: 'New Y Axis Label', y2: 'New Y2 Axis Label Again'});
}, 2000);

setTimeout(function() {
    c3LineChart.load({
      columns: [
//          ['x', '2013-01-02', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06', '2013-01-07'],
        ['data1', 130, 150, 200, 300, 200, 100]
      ]
    });
  }, 1000);

  setTimeout(function() {
    c3LineChart.load({
      columns: [
//          ['x', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06', '2013-01-07', '2013-01-08'],
        ['data1', 150, 200, 300, 200, 100,150]
      ]
    });
  }, 1500);
  setTimeout(function() {
	    c3LineChart.load({
	      columns: [
//	            ['x', '2013-01-04', '2013-01-05', '2013-01-06', '2013-01-07', '2013-01-08', '2013-01-09'],
	          ['data1', 200, 300, 200, 100,150,159]
	      ]
	    });
	  }, 2000);
  setTimeout(function() {
	    c3LineChart.load({
	      columns: [
//	            ['x', '2013-01-04', '2013-01-05', '2013-01-06', '2013-01-07', '2013-01-08', '2013-01-09'],
	          ['data1', 300, 200, 100,150,159,187]
	      ]
	    });
	  }, 2500);

//  setTimeout(function() {
//    c3LineChart.unload({
//      ids: 'data1'
//    });
//  }, 2000);
//function refreshGage() {
//	for (var indpl = 0; indpl < gl.length; indpl++) {
//		gl[indpl].refresh(getRandomInt(50, 100));
//	}
//}
// setInterval(refreshGage, 1000);

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
			selector : '#ui-historyDataPoints',
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

// var _historyDataDetailKey = null;
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
	if(socket.readyState!=1){
		alert("未连接。");
		connect();
		return;
		}
	else {
		console.log("当前存在");
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
		stompClient.send("/app/aaa", {
			atytopic : _historyDataDetailKey,
			type : 'historyData',
			id : _historyDataDetailKey+""
		}, JSON.stringify({
			'type' : 'historyData',
			'id' : _historyDataDetailKey+""
		}));
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_HistoryData_pre_/'
				+ _historyDataDetailKey, function(data) {
			// alert("websocket connected 3.");
			// 收到消息后处理
			refreshData(data);
		});
	}
}

function connect() {
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
		console.log("websocket connected." + _historyDataDetailKey + "  .");
		// console.log('Connected: ' + frame);

		// 发送消息给服务器
		stompClient.send("/app/aaa", {
			atytopic : _historyDataDetailKey,
			type : 'Key_HistoryData_pre_',
			id : _historyDataDetailKey
		}, JSON.stringify({
			'type' : 'historyData',
			'id' : _historyDataDetailKey
		}));
		// 连接成功后，主动拉取未读消息
		// pullUnreadMessage("/topic/reply");
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_HistoryData_pre_/'
				+ _historyDataDetailKey, function(data) {
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

