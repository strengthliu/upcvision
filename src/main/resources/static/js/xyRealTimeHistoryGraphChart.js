/**
 * 
 */

var gl = new Array();
var charts = new Object();
var _xyGraphDetailKey = _routeID;
var x_axis = 'time';
// console.log("_xyGraphDetailKey: " + _xyGraphDetailKey);

function newItemAction() {
	alert("xyGraph.js newItemAction。 从mainPanel中调用的。");
}

if (userSpace == null || userSpace == "undefined") {
	console.log("userSpace 为空，重新到服务器去取。");
	getUserSpace(user.id, token, updateXYGraphChart);
} else
	updateXYGraphChart(userSpace);

/**
 * 画点图
 */
function updateXYGraphChart(ruserSpace) {
	var pointGroup = ruserSpace.xyGraph[_xyGraphDetailKey];
	var uixyGraphPoints = document.getElementById("ui-xyGraphPoints");
	 // console.log(" updateXYGraphChart => "+JSON.stringify(pointGroup));
	if (pointGroup == null || pointGroup == "undefined")
		return;
	var pointList = pointGroup.pointList;
	var innerHtml = "";
//	// console.log("pointList" + JSON.stringify(pointList));
//	for (var indpl = 0; indpl < pointList.length; indpl++) {
//// console.log(" updateXYGraphChart=> "+JSON.stringify(pointList[indpl]));
//		try{
//			// 页面加一块
//			var item = '<div class="box col-lg-3"><div class="gauge" id="point_'
//					+ pointList[indpl].tagName + '"></div></div>';
//			innerHtml += item;
//		}catch(e){
//			
//		}
//	}
//	uixyGraphPoints.innerHTML = innerHtml;
//	console.log(uixyGraphPoints.innerHTML);
//
//	for (var indpl = 0; indpl < pointList.length; indpl++) {
//		// 对象加一条
//		var gt = new JustGage({
//			id : "point_" + pointList[indpl].tagName,
//			value : 0,
//			min : 0,
//			max : 100,
//			title : pointList[indpl].desc,//"一级电脱盐混合阀压差",
//			label : pointList[indpl].enunit,
//			donut : true,
//			gaugeWidthScale : 0.6,
//			counter : true,
//			hideInnerShadow : true
//		});
//		var _tagName_ = pointList[indpl].tagName;
//		charts[_tagName_] = indpl;
//		gl[indpl] = gt;
//	}
//
	var menuitem = document.getElementById("x_axisSelectButtonUIMenu");
	var _innerHtml = '<a class="dropdown-item" onclick="changex(\'time\')">时间</a>';
	for (var indpl = 0; indpl < pointList.length; indpl++) {
		var _itemHtml = '<a class="dropdown-item" onclick="changex(\''+pointList[indpl].tagName+'\')">'+pointList[indpl].tagName+'</a>';
		_innerHtml = _innerHtml + _itemHtml;
	}
	menuitem.innerHTML = _innerHtml;
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
	addData(data.body,cdata,cdataCount);

	_forward(data.body);
	// 刷新表格
	refreshDataTable(cdata);

}

function refreshDataTable(_cdata){
	var _datatableUI = document.getElementById("_datatableUI");
	_datatableUI.innerHTML = "";
	var _thead = document.createElement("thead");
	var _tbody = document.createElement("tbody");

//	console.log("refreshDataTable - _cdata "+JSON.stringify(_cdata));
//	alert();
	for(var coli = 0;coli<_cdata.length;coli++){
		var _tr = document.createElement("tr");  
		for(var rowi = 0;rowi<_cdata[coli].length;rowi++){
//			console.log('fdsafdsa')
			// 设为x轴
			
			var _td = document.createElement("td"); 
			
			var _value = _cdata[coli][rowi];
			switch(typeof _value){
//			console.log(" typeof => "+typeof(_value));			
			case 'number':
				_td.innerText = (Math.round(_value * 10000)) / 10000+"";		
				break;
			case 'string':
				_td.innerText += _cdata[coli][rowi];//_timeStr;
//				if(rowi==0){
//					var _rbox = document.createElement("input");
//					_rbox.type="radio";
//					_rbox.id="xray";
//					_rbox.name="xray";
//					_rbox.value=_cdata[coli][rowi];
//					 if(x_axis === _cdata[coli][rowi])
//						 _rbox.setAttribute("checked","checked"); 
//					 _rbox.addEventListener("click",changex(cdata[coli][rowi]));
//					        console.log(" = "+x_axis);
//
//					_td.prepend(_rbox);
//				}
				break;
			default:
				var _t = new Date(_cdata[coli][rowi]);
				_td.innerText = _t.Format("hh:mm:ss");//_timeStr;					
//				_td.innerText = _cdata[coli][rowi];//_timeStr;
				break;
			}
			_tr.append(_td);
		}

		//		console.log(" _cdata[coli] "+JSON.stringify(_cdata[coli]));
		if(_cdata[coli][0]=="time"){
			_thead.append(_tr);
			_datatableUI.prepend(_thead);
		}
		else
			_tbody.append(_tr);
	}
	_datatableUI.append(_tbody);

}
/**
 * 更新线图
 * 
 * @returns
 */
// updateLineChart();
/**
 * 总值
 */
var _data = new Array();
var _dataCount = 1000;

/**
 * 当前值范围
 */
var cdata = new Array();
var cdataCount = 10;

/**
 * x轴范围，y轴范围
 */

/**
 * 向前，向后
 */
function _forward(_newData) {

	c3LineChart.load({
		columns : cdata
	});
	c3LineChart.axis.min({
		y : _minY - (_maxY-_minY)/2 * _rateY
	});
	c3LineChart.axis.max({
		y : _maxY + (_maxY-_minY)/2 * _rateY 
	});

}

function _backward() {

}
var _maxX = 0;
var _minX = 0;
var _rateX = 1;

/**
 * x轴放大缩小
 */
function zoomin_x() {
	_rateX = _rateX /2;
	cdataCount = cdataCount * 1.5;
	console.log("cdataCount = "+cdataCount);
}

function zoomout_x() {
	_rateX = _rateX *2;
	cdataCount = cdataCount / 1.5;
	console.log("cdataCount = "+cdataCount);
}
var _maxY = 0;
var _minY = 0;
var _rateY = 1;
function zoomin_y() {
	_rateY = _rateY / 2
	console.log(" _rateY = "+_rateY);
}
function zoomout_y() {
	_rateY = _rateY * 2
	console.log(" _rateY = "+_rateY);
}

/**
 * 换X轴
 */
function changex(tname) {
	// console.log("click: ========================== "+tname);
	var but = document.getElementById("x_axisSelectButtonUI");
	but.innerHTML = tname;
	console.log(tname);
//	but.text = tname;
	if(tname=="time"){
		c3LineChart = c3.generate({
			bindto : '#ui-xyGraphLineChart',
			data : {
				x : tname,
				xFormat : '%Y',
				columns : cdata,
				type : 'spline',
			},
			grid : {
				x : {
					show : true
				},
				y : {
					show : true
				}
			},
			color : {
				pattern : [ 'rgba(88,216,163,1)', 'rgba(237,28,36,0.6)',
						'rgba(4,189,254,0.6)' ]
			},
			padding : {
				top : 10,
				right : 30,
				bottom : 30,
				left : 50,
			},
			axis : {
				x : {
					type : 'timeseries',
					// if true, treat x value as localtime (Default)
					// if false, convert to UTC internally
					localtime : false,
					tick : {
						format : '%Y-%m-%d %H:%M:%S'
					}
				},
				y : {
					show: true,
					label: 'Y2 Axis Label'
				}
			}
		});
	}else{
		c3LineChart = c3.generate({
			bindto : '#ui-xyGraphLineChart',
			data : {
				x : tname,
				xFormat : '%Y',
				columns : cdata,
				type : 'spline',
			},
			grid : {
				x : {
					show : true
				},
				y : {
					show : true
				}
			},
			color : {
				pattern : [ 'rgba(88,216,163,1)', 'rgba(237,28,36,0.6)',
						'rgba(4,189,254,0.6)' ]
			},
			padding : {
				top : 10,
				right : 30,
				bottom : 30,
				left : 50,
			},
			axis : {
				y : {
					show: true,
					label: 'Y2 Axis Label'
				}
			}
		});
	}

}

/**
 * 刷新值
 * 
 */
function addData(newData, _data_, cdatacount) {
	if (typeof newData == "string") {
		newData = JSON.parse(newData);
	}
	var _d_ = newData;
	var _ctime = new Date();// .Format('yyyy-MM-dd hh:mm:ss');
	var timeInd = 0;

	// 先处理时间段
	for (var ind_data = 0; ind_data < _data_.length; ind_data++) {
		var pname = _data_[ind_data][0];
		// 找到time段
		if (pname == "time") {
			// 如果没传入time段
			if (_d_['time'] == null || _d_['time'] == "undefined")
				_ctime = new Date();
			else {
				_ctime = new Date(_d_['time']);
			}
			if(_data_[ind_data][1]==0){
				_data_[ind_data][1] = _ctime;
			}else{
				_data_[ind_data].push(_ctime);				
			}
			timeInd = ind_data;
		}
		// 如果当前数据量多于cdatacount，就从前面删除
		if (_data_[ind_data].length > cdatacount) {
			_data_[ind_data].splice(1, 1);
		}
	}

	Object
			.keys(_d_)
			.forEach(
					function(key) {
						// 向cdata中添加
						var _time = true;
						var _timeD_ = null;
						// 循环 新点值的每个值 -> 库值
						for (var ind_data = 0; ind_data < _data_.length; ind_data++) {
							// 行名称
							var pname = _data_[ind_data][0];
							if (pname != null && pname != "undefined") {
								if (pname == key) {
									if (key == "time") {
										// 不处理time了
									} else {
										if (_data_[ind_data].length == 2
												&& _data_[ind_data][1] == 0)
											_data_[ind_data][1] = _d_[key];
										else {
											var lastData = _data_[ind_data][_data_[ind_data].length - 1];
											_timeD_ = _data_[timeInd][_data_[ind_data].length - 1];
											// 添加值
											_data_[ind_data].push(_d_[key]);
										}
										// 更新最大值和最小值
										if (_maxY < _d_[key])
											_maxY = _d_[key];
										if (_minY > _d_[key] || _minY == 0)
											_minY = _d_[key];
										if (_data_[ind_data].length > cdatacount) {
											_data_[ind_data].splice(1, 1);
										}
									}
								}
							}
						}
					});
}

var c3LineChart;
(function($) {
	'use strict';
	var cols = new Array();
	var pointGroup = userSpace.xyGraph[_xyGraphDetailKey];
	var pointList = pointGroup.pointList;
	//console.log("pointList -> "+JSON.stringify(pointList));
	var _time_ = new Array();
	_time_.push('time',0);
	cols.push(_time_);

	for (var indpl = 0; indpl < pointList.length; indpl++) {
		var _c_ = new Array();
		_c_.push(pointList[indpl].tagName,0);
		
		cols.push(_c_);
	}
	cdata=cols;
	_data=cols;
	c3LineChart = c3.generate({
		bindto : '#ui-xyGraphLineChart',
		data : {
			x : 'time',
			xFormat : '%Y',
			columns : cols,
			type : 'spline',
	        axes: {
	        	CJY_XT31101_8: 'y',
	        }
		},
		grid : {
			x : {
				show : true
			},
			y : {
				show : true
			}
		},
		color : {
			pattern : [ 'rgba(88,216,163,1)', 'rgba(237,28,36,0.6)',
					'rgba(4,189,254,0.6)' ]
		},
		padding : {
			top : 10,
			right : 20,
			bottom : 30,
			left : 50,
		},
		axis : {
			x : {
				type : 'timeseries',
				// if true, treat x value as localtime (Default)
				// if false, convert to UTC internally
				localtime : true,
				tick : {
					format : '%Y-%m-%d %H:%M:%S'
				}
			},
			y : {
				show: true
//				,
//				label: 'Y2 Axis Label'
			}
		}
	});

})(jQuery);

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
			selector : '#ui-xyGraphPoints',
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
			atytopic : _xyGraphDetailKey,
			type : 'xyGraph',
			id : _xyGraphDetailKey+""
		}, JSON.stringify({
			'type' : 'xyGraph',
			'id' : _xyGraphDetailKey+""
		}));
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_XYGraph_pre_/'
				+ _xyGraphDetailKey, function(data) {
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
		// 停止调试信息
		stompClient.debug = null;

		setConnected(true);
		console.log("websocket connected." + _xyGraphDetailKey + "  .");
		// console.log('Connected: ' + frame);

		// 发送消息给服务器
		stompClient.send("/app/aaa", {
			atytopic : _xyGraphDetailKey,
			type : 'Key_XYGraph_pre_',
			id : _xyGraphDetailKey
		}, JSON.stringify({
			'type' : 'xyGraph',
			'id' : _xyGraphDetailKey
		}));
		// 连接成功后，主动拉取未读消息
		// pullUnreadMessage("/topic/reply");
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_XYGraph_pre_/'
				+ _xyGraphDetailKey, function(data) {
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

