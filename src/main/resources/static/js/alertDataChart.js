/**
 * 
 */

var gl = new Array();
var charts = new Object();
var _alertDataDetailKey = _routeID;

function setRefreshInterval(itime) {
	getAlertData();
	if (intervalId != null && intervalId != "undefined")
		clearInterval(intervalId);
	intervalId = setInterval(function() {
		getAlertData();
	}, itime);
}
setRefreshInterval(15 * 1000);
// console.log("_alertDataDetailKey: " + _alertDataDetailKey);
getAlertData(_alertDataDetailKey, _startTime_, _endTime_);
function _search() {
	getAlertData(_alertDataDetailKey, _startTime_, _endTime_);
}
function getAlertData(id, startTime, endTime) {
	if (startTime == "undefined")
		startTime = null;
	if (endTime == "undefined")
		endTime = null;
	// console.log("aac -> startTime=" + startTime + " endTime=" + endTime);
	if (startTime != null)
		var data = {
			'uid' : uid,
			'token' : token,
			'id' : _alertDataDetailKey,
			'startTime' : startTime.getTime(),
			'endTime' : endTime.getTime()
		};
	else
		var data = {
			'uid' : uid,
			'token' : token,
			'id' : _alertDataDetailKey
		};

	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getAlertData",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : " + JSON.stringify(data.data.data));
				var alertData = data.data.data;
				// console.log(JSON.stringify(alertData));
				if (startTime != null) {
					fillCdata(alertData);
					refreshDataTable(cdata);
				} else {
					fillCdata(alertData);
					refreshDataTableRealTime(cdata);
				}
			} else {
				alert("失败 ： " + data.msg);
			}
			hideLoading();
			// alert("本地存储："+localStorage.user);
			// window.location.href = "index.html";
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

function fillCdata(alertData) {
	// alertData = JSON.parse(alertData);
	// console.log(alertData);
	cdata = new Array();
	var ser = [ '序号', '位号', '报警实值', '报警类型', '服务器名', '报警开始时间', '连续报警时间', '高报警线',
			'低报警线', '高高报警线', '低低报警线' ];
	cdata.push(ser);
	for (var i = 0; i < alertData.length; i++) {
		var sd = new Array();
		sd.push(i);
		sd.push(alertData[i].tagName);
		sd.push(Math.round(alertData[i].alertValue * 10000) / 10000);
		sd.push(alertData[i].alertType);
		sd.push(alertData[i].serverName);
		sd.push(alertData[i].duration);
		sd.push(convertSecondTime(alertData[i].occuredTime));
		sd.push(alertData[i].hiLimit);
		sd.push(alertData[i].loLimit);
		sd.push(alertData[i].hihiLimit);
		sd.push(alertData[i].loloLimit);
		cdata.push(sd);
	}
}

function convertSecondTime(seconds) {
	var mm = Math.floor(seconds / 60);
	var ss = seconds - mm * 60;
	if (mm == 0) {
		return ss + "秒";
	}
	var hh = Math.floor(mm / 60);
	mm = mm - hh * 60;
	if (hh == 0) {
		return mm + "分" + ss + "秒";
	}
	var dd = Math.floor(hh / 24);
	hh = hh - dd * 24;
	if (dd == 0) {
		return hh + "小时" + mm + "分" + ss + "秒";
	} else {
		return dd + "天" + hh + "小时" + mm + "分" + ss + "秒";
	}
}
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

// ************************ 表格 *****************************
function refreshDataTableRealTime(_cdata) {
	var _datatableUI = document.getElementById("_datatableUI");
	_datatableUI.innerHTML = "";
	var _thead = document.createElement("thead");
	var _tbody = document.createElement("tbody");

	// console.log("refreshDataTable - _cdata " + JSON.stringify(_cdata));
	// alert();
	for (var coli = 0; coli < _cdata.length; coli++) {
		var _tr = document.createElement("tr");
		for (var rowi = 0; rowi < _cdata[coli].length; rowi++) {
			// console.log('fdsafdsa')
			var _td = document.createElement("td");
			var _value = _cdata[coli][rowi];
			var _value = _cdata[coli][rowi];
			switch (typeof _value) {
			// console.log(" typeof => "+typeof(_value));
			case 'number':

				if (rowi == 3) {
					switch (_value) {
					case 0:
						_td.innerText = '0';
						break;
					case 1:
						_td.innerText = '1';
						break;
					case 2:
						_td.innerText = '低报';
						break;
					case 3:
						_td.innerText = '高报';
						break;
					}

				} else if ((rowi == 5 || rowi == 6) && _value > 999999999
						&& _value < 1999999999) {
					var tt1 = new Date(_cdata[coli][rowi] * 1000);
					_td.innerText = tt1.Format("yyyy年MM月dd日 hh:mm:ss");
				} else {
					_td.innerText = (Math.round(_value * 1000)) / 1000 + "";
				}
				break;
			case 'string':
				_td.innerText += _cdata[coli][rowi];// _timeStr;
				// if(rowi==0){
				// var _rbox = document.createElement("input");
				// _rbox.type="radio";
				// _rbox.id="xray";
				// _rbox.name="xray";
				// _rbox.value=_cdata[coli][rowi];
				// if(x_axis === _cdata[coli][rowi])
				// _rbox.setAttribute("checked","checked");
				// _rbox.addEventListener("click",changex(cdata[coli][rowi]));
				// console.log(" = "+x_axis);
				//
				// _td.prepend(_rbox);
				// }
				break;
			default:
				var _t = new Date(_cdata[coli][rowi]);
				_td.innerText = _t.Format("hh:mm:ss");// _timeStr;
				// _td.innerText = _cdata[coli][rowi];//_timeStr;
				break;
			}
			// _td.innerText = _cdata[coli][rowi];// _timeStr;

			_tr.append(_td);
		}

		// console.log(" _cdata[coli] "+JSON.stringify(_cdata[coli]));
		if (_cdata[coli][0] == "time") {
			_thead.append(_tr);
			_datatableUI.prepend(_thead);
		} else
			_tbody.append(_tr);
	}
	_datatableUI.append(_tbody);

}

function refreshDataTable(_cdata) {
	var _datatableUI = document.getElementById("_datatableUIHistory");
	_datatableUI.innerHTML = "";
	var _thead = document.createElement("thead");
	var _tbody = document.createElement("tbody");

	// console.log("refreshDataTable - _cdata "+JSON.stringify(_cdata));
	// alert();
	for (var coli = 0; coli < _cdata.length; coli++) {
		var _tr = document.createElement("tr");
		for (var rowi = 0; rowi < _cdata[coli].length; rowi++) {
			// console.log('fdsafdsa')
			var _td = document.createElement("td");
			var _value = _cdata[coli][rowi];
			var _value = _cdata[coli][rowi];
			switch (typeof _value) {
			case 'number':
				if (rowi == 3) {
					switch (_value) {
					case 0:
						_td.innerText = '0';
						break;
					case 1:
						_td.innerText = '1';
						break;
					case 2:
						_td.innerText = '低报';
						break;
					case 3:
						_td.innerText = '高报';
						break;
					}

				} else if ((rowi == 5 || rowi == 6) && _value > 999999999
						&& _value < 1999999999) {
					var tt1 = new Date(_cdata[coli][rowi] * 1000);
					_td.innerText = tt1.Format("yyyy年MM月dd日 hh:mm:ss");
				} else {
					_td.innerText = (Math.round(_value * 1000)) / 1000 + "";
				}
				break;
			case 'string':
				_td.innerText += _cdata[coli][rowi];// _timeStr;
				break;
			default:
				var _t = new Date(_cdata[coli][rowi]);
				_td.innerText = _t.Format("hh:mm:ss");// _timeStr;
				break;
			}
			_tr.append(_td);
		}

		// console.log(" _cdata[coli] "+JSON.stringify(_cdata[coli]));
		if (_cdata[coli][0] == "time") {
			_thead.append(_tr);
			_datatableUI.prepend(_thead);
		} else
			_tbody.append(_tr);
	}
	_datatableUI.append(_tbody);

}
// function refreshGage() {
// for (var indpl = 0; indpl < gl.length; indpl++) {
// gl[indpl].refresh(getRandomInt(50, 100));
// }
// }
// setInterval(refreshGage, 1000);

// ************************ 右键菜单 *****************************
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
		// stompClient.disconnect();
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
			selector : '#ui-alertDataPoints',
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

// ************************ websocke *****************************
// var _alertDataDetailKey = null;
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

// loginWebsocket();
function loginWebsocket() {
	if (socket.readyState != 1) {
		alert("未连接。");
		connect();
		return;
	} else {
		// console.log("当前存在");
		if (subscribe != null && subscribe != "undefined")
			subscribe.unsubscribe();
		stompClient.send("/app/aaa", {
			atytopic : _alertDataDetailKey,
			type : 'alertData',
			id : _alertDataDetailKey + ""
		}, JSON.stringify({
			'type' : 'alertData',
			'id' : _alertDataDetailKey + ""
		}));
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_AlertData_pre_/'
				+ _alertDataDetailKey, function(data) {
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
	stompClient.heartbeat.incoming = 10000; // client接收serever端的心跳检测
	// 连接服务器
	var headers = {
		login : user.id,
		token : token,
		// additional header
		'client-id' : 'my-client-id'
	};

	stompClient.connect(headers, function(frame) {
		setConnected(true);
		console.log("websocket connected." + _alertDataDetailKey + "  .");
		// console.log('Connected: ' + frame);

		// 发送消息给服务器
		stompClient.send("/app/aaa", {
			atytopic : _alertDataDetailKey,
			type : 'Key_AlertData_pre_',
			id : _alertDataDetailKey
		}, JSON.stringify({
			'type' : 'alertData',
			'id' : _alertDataDetailKey
		}));
		// 连接成功后，主动拉取未读消息
		// pullUnreadMessage("/topic/reply");
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_AlertData_pre_/'
				+ _alertDataDetailKey, function(data) {
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
	}, function(message) {
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

function newItemAction() {
	alert("alertData.js newItemAction。 从mainPanel中调用的。");
}

// if (userSpace == null || userSpace == "undefined") {
// console.log("userSpace 为空，重新到服务器去取。");
// getUserSpace(user.id, token, updateAlertDataChart);
// } else
// updateAlertDataChart(userSpace);

// ************************ 画仪表盘图形 *****************************
/**
 * 画点图
 */
function updateAlertDataChart(ruserSpace) {
	var pointGroup = ruserSpace.alertData[_alertDataDetailKey];
	var uialertDataPoints = document.getElementById("ui-alertDataPoints");
	// console.log(" updateAlertDataChart => " + JSON.stringify(pointGroup));
	if (pointGroup == null || pointGroup == "undefined")
		return;
	var pointList = pointGroup.pointList;
	var innerHtml = "";
	// console.log("pointList" + JSON.stringify(pointList));
	for (var indpl = 0; indpl < pointList.length; indpl++) {
		// console.log(" updateAlertDataChart=>
		// "+JSON.stringify(pointList[indpl]));
		try {
			// 页面加一块
			var item = '<div class="box col-lg-3"><div class="gauge" id="point_'
					+ pointList[indpl].tagName + '"></div></div>';
			innerHtml += item;
		} catch (e) {

		}
	}
	uialertDataPoints.innerHTML = innerHtml;
	// console.log(uialertDataPoints.innerHTML);

	for (var indpl = 0; indpl < pointList.length; indpl++) {
		// console.log();
		// 对象加一条
		var gt = new JustGage({
			id : "point_" + pointList[indpl].tagName,
			value : 0,
			min : 0,
			max : 100,
			title : pointList[indpl].desc,// "一级电脱盐混合阀压差",
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
	for ( var key in pointList_) {
		for ( var p in gl) {
			// console.log(gl[p].config.id + " == "+"point_" + key );
			if (gl[p].config.id == "point_" + key) {
				gl[p].refresh(pointList_[key]);
			}
		}
	}
}
