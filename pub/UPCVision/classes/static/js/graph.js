//
/**
 * 右键菜单
 * 
 * @param $
 * @returns
 */
// ***************** 开始 右键菜单 *************************
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
// ***************** 结束 右键菜单 *************************

var pointInfoMapper;
if (pointInfoMapper == null || pointInfoMapper == "undefined") {
	// TODO: ajax调用getGraphPointInfoMapper，取pointInfoMapper信息。
}

// ***************** 开始 画图 *************************

var gl = new Array();
var charts = new Object();
var _xyGraphDetailKey = _routeID;
var x_axis = 'time';
// console.log("_xyGraphDetailKey: " + _xyGraphDetailKey);
// console.log(""+JSON.stringify(userSpace,null,2));
var pointGroup;
if (pointGroup == null || pointGroup == "undefined")
	pointGroup = new Array();
var _dataIndex;
if (_dataIndex == null || _dataIndex == "undefined")
	_dataIndex = new Object();
var _cdataIndex;
if (_cdataIndex == null || _cdataIndex == "undefined")
	_cdataIndex = new Object();

/**
 * 将一个点添加到XY图中。
 */
function addPointToXYGraph(tagName) {
	// 如果点组里不包含这个点
	if (pointGroup.indexOf(tagName) == -1) {
		// console.log("addPointToXYGraph:"+tagName);
		// 添加进点组
		pointGroup.push(tagName);

		// 修改cdata和_data时，修改tagName值为当前状态下的值。
		var __tagName = getNewLabelForData(tagName);
		// console.log("__tagName = "+__tagName);
		// 在刷新数据时，addData时，在_data中加一个
		// 向_data和cdata中加入一个数据，之后addData才能真正将数据加入。
		// 构建_data序号。
		_dataIndex = new Array();
		for (var indrow = 0; indrow < _data.length; indrow++) {
			_dataIndex[_data[indrow][0]] = indrow;
		}
		_cdataIndex = new Array();
		for (var indrow = 0; indrow < cdata.length; indrow++) {
			_cdataIndex[cdata[indrow][0]] = indrow;
		}

		// 处理cdata。_data处理方法参考这个。
		if (_cdataIndex['time'] == "undefined" || _cdataIndex['time'] == null) {
			// console.log("deal with time serial. ");
			var timeserial = new Array();
			timeserial.push('time');
			cdata.push(timeserial);
			_cdataIndex['time'] = cdata.length - 1;
		}
		if (_cdataIndex[__tagName] == "undefined"
				|| _cdataIndex[__tagName] == null) {
			// console.log("deal with "+tagName+".");
			// console.log("deal with "+tagName+".
			// "+JSON.stringify(_cdataIndex)+" "+JSON.stringify(cdata));
			var tagserial = new Array();
			tagserial.push(tagName);
			// TODO: 先用0补齐数据，后面要改成取历史数据。
			if (_cdataIndex['time'] != null
					&& _cdataIndex['time'] != "undefined"
					&& cdata[_cdataIndex['time']] != null
					&& cdata[_cdataIndex['time']] != "undefined") {
				for (var i = 0; i < cdata[_cdataIndex['time']].length - 1; i++) {
					tagserial.push(0);
				}
			}
			cdata.push(tagserial);
			// console.log(a);
		}

		if (_dataIndex['time'] == "undefined" || _dataIndex['time'] == null) {
			// console.log("_data deal with time serial. ");
			var timeserial = new Array();
			timeserial.push('time');
			_data.push(timeserial);
			_dataIndex['time'] = _data.length - 1;
		}
		// console.log("_dataIndex[tagName]== " +_dataIndex[tagName] +"tagName=
		// "+tagName );
		if (_dataIndex[tagName] == "undefined" || _dataIndex[tagName] == null) {
			// console.log("deal with "+tagName+".");
			// console.log("deal with "+tagName+".
			// "+JSON.stringify(_cdataIndex)+" "+JSON.stringify(cdata));
			var tagserial = new Array();
			tagserial.push(tagName);
			// TODO: 先用0补齐数据，后面要改成取历史数据。
			if (_dataIndex['time'] != null && _dataIndex['time'] != "undefined"
					&& _data[_dataIndex['time']] != null
					&& _data[_dataIndex['time']] != "undefined") {
				for (var i = 0; i < _data[_dataIndex['time']].length - 1; i++) {
					tagserial.push(0);
				}
			}
			_data.push(tagserial);
			// console.log(a);
		}

		/**
		 * 历史数据怎么办？_data中保存，cdata中删除。 删除了一点，这个点的数据的cdata删除，_data中保存。
		 * 
		 * _data中保存最多点的数据，cdata中只保存当前看的数据。
		 * 
		 */

		updateXYGraphChart();
	}
}
/**
 * 将一个点从XY图中移出
 * 
 * @returns
 */
function removePointFromXYGraph(tagName) {
	// 将tagName返成txtid
	var tagName = getOrigionLabel(tagName);
	console.log("1-- " + tagName);
	if (pointGroup.indexOf(tagName) > -1) {
		pointGroup.splice(pointGroup.indexOf(tagName), 1);

		// 修改cdata和_data时，修改tagName值为当前状态下的值。
		var tagName = getNewLabelForData(tagName);
		// console.log("removePointFromXYGraph - "+tagName);
		// TODO: cdata中删除点。
		_cdataIndex = new Array();
		for (var indrow = 0; indrow < cdata.length; indrow++) {
			_cdataIndex[cdata[indrow][0]] = indrow;
		}
		cdata.splice(_cdataIndex[tagName], 1);

		_dataIndex = new Array();
		for (var indrow = 0; indrow < _data.length; indrow++) {
			_dataIndex[_data[indrow][0]] = indrow;
		}
		_data.splice(_dataIndex[tagName], 1);

		// console.log("cd -> "+JSON.stringify(cdata));
		var _dt_ = new Array();
		for (var i = 0; i < cdata.length; i++) {
			_dt_.push(cdata[i][0]);
			_dt_.push(cdata[i][cdata[i].length - 1]);
		}
		// c3LineChart.flow({columns:_dt_,to:new Date(),duration:1000});
		// c3LineChart.flow({columns:cdata,to:new Date(),duration:1000});
		if (pointGroup.length > 0) {
			c3LineChart = c3.generate({
				bindto : '#ui-historyDataLineChart',
				data : {
					x : 'time',
					xFormat : '%Y',
					columns : cdata,
					type : 'spline',
					axes : {
						CJY_XT31101_8 : 'y',
					}
				},
				zoom : {
					enabled : true
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
						show : true
					// ,
					// label: 'Y2 Axis Label'
					}
				}
			});
		}
	} else {
		console.log("pointGroup中没有要找的点：" + tagName);
		console.log("pointGroup=" + JSON.stringify(pointGroup));
	}
	updateXYGraphChart();
}

function closeGraph() {

}

function buildChart() {
	// console.log("buildChart()");
	// 取数据到_data中。
	if (_data.length > 0 && _data[0].length < 10) {
		console.log(" abbc -> _backward()");
		_backward();
		$('#exampleModal').modal('show');
		// loadCData();
		// getHistoryData1(,buildChart1)
	} else {
		buildChart1();
	}

}

function buildChart1() {
	// console.log(" abbc -> buildChart1()");
	// console.log("cdata => " + JSON.stringify(cdata));
	$('#exampleModal').modal('show');
	loadCData();

	c3LineChart = c3.generate({
		bindto : '#ui-historyDataLineChart',
		data : {
			x : 'time',
			xFormat : '%Y',
			columns : cdata,
			type : 'spline'
		// ,
		// axes: {
		// CJY_XT31101_8: 'y',
		// }
		},
		zoom : {
			enabled : true
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
				show : true
			// ,
			// label: 'Y2 Axis Label'
			}
		}
	});
	// currentPlayStatus = true;
	_backward();
	// console.log("buildChart() end");

}

/**
 * 线图
 */
var c3LineChart = {};
var cols;
(function($) {
	'use strict';
	cols = new Array();
	var pointList = pointGroup;
	// console.log("pointList -> "+JSON.stringify(pointList));
	var _time_ = new Array();
	_time_.push('time', 0);
	cols.push(_time_);

	for (var indpl = 0; indpl < pointList.length; indpl++) {
		if (pointList[indpl] != null && pointList[indpl] != "undefined") {
			var _c_ = new Array();
			_c_.push(pointList[indpl].tagName, 0);
			cols.push(_c_);
		} else {
			console.log(" get null tagName => "
					+ JSON.stringify(pointList, null, 2));
		}
	}
	cdata = cols;
	_data = cols;

})(jQuery);

/*******************************************************************************
 * 关于趋势图显示标签的相关设计 1、数据结构 （1）. _data、cdata内，保存的是当前显示的标签值，如点位描述。 （2）.
 * pointGroup内，保存的是原始显示标签，是从服务器返回的那个，图形中text的id。
 * 
 */

/**
 * 趋势图上，占位显示内容： 0：tagName; 1: 点位说明; 2: 没有呢。 default: 不变
 */
var tagFeatureInXYGraph = 0;

/**
 * 当前图形
 */
// console.log("graph.js -> _graph = getGraphByID _graphId="+_graphId);
var _graph = getGraphByID(userSpace.graph, _graphId);

/**
 * 当前图形中的点列表： { "DATAPOINT1_pbTextEl":{ "id":8792, "tagName":"570_FT_10104",
 * "desc":"生活污水去T-101", "deviceName":"GROUP1", "serverName":"RTDBB",
 * "enunit":"", "tagType":"", "values":null }, ... }
 */
var pointKs;
var isAddOnclickFunc = false;
/**
 * 构建当前图形的点位信息表
 * 
 * @returns
 */
function buildPointKs() {
	if (pointKs == null || pointKs == "undefined") {
		pointKs = new Object();
	}
	// console.log("========================= buildPointKs
	// =========================");
	// console.log("-_graphId= "+_graphId+" graph=
	// "+JSON.stringify(_graph,null,2));
	// console.log("========================= buildPointKs
	// =========================");
	// alert();
	var pointTextIDs = _graph.pointTextIDs;
	var pointList = _graph.pointList;
	if (pointTextIDs != null && pointTextIDs != "undefined")
		for (var ind = 0; ind < pointTextIDs.length; ind++) {
			pointKs[pointTextIDs[ind]] = pointList[ind];
		}
}

var multistatesObj = new Object();
var bargraphsObj = new Object();

/**
 * 获取当前状态的下指定标签的原始标签txtid
 * 
 * @param currenttxtid
 * @returns
 */
function getOrigionLabel(currenttxtid) {
	var tartxt = null;
	var points = _graph.pointList;
	// console.log("getOrigionLabel 1 - "+currenttxtid);
	// 将tagFeature下的txtid转成标准txtid
	for (var ind = 0; ind < points.length; ind++) {
		switch (tagFeatureInXYGraph) {
		case 0:
			tartxt = points[ind].tagName;
			break;
		case 1:
			tartxt = points[ind].desc;
			break;
		default:
			tartxt = pointTextIDs[ind];
			break;
		}
		// console.log("getOrigionLabel 2 - "+tartxt);
		if (tartxt == currenttxtid) {
			tartxt = _graph.pointTextIDs[ind];
			// console.log("getOrigionLabel 3 - "+tartxt);
			return tartxt;
		}
	}
	return null;

}
/**
 * 获取指定状态下指定标签所对应当前状态的标签值
 * 
 * @param txtid
 *            标签值
 * @param tagFeature
 *            状态
 * @returns 当前状态下的值
 */
function getNewLabelForData(txtid, tagFeature) {
	// console.log("getNewLabelForData=> "+txtid);
	// console.log("pointKs=> "+JSON.stringify(pointKs));
	var tartxt = null;
	if (tagFeature != null && tagFeature != "undefined") {

	}
	if (tartxt == null || tartxt == "undefined")
		tartxt = txtid;

	if (pointKs[tartxt] != null && pointKs[tartxt] != "undefined") {
		switch (tagFeatureInXYGraph) {
		case 0:
			return pointKs[tartxt].tagName;
		case 1:
			return pointKs[tartxt].desc;
		case 2:
			break;
		default:
			return tartxt;
		}
	}
	return null
}

/**
 * 
 * @param data
 *            服务器返的数据对象。其中数据的标签一定是txtid。
 * @returns
 */
function changeDataToNewLabel(data) {
	// console.log("data");
	var ret = new Object();
	Object.keys(data).forEach(function(key) {
		var item = data[key];
		if (key != "time") {
			if (pointKs[key] != null && pointKs[key] != "undefined")
				switch (tagFeatureInXYGraph) {
				case 0:
					ret[pointKs[key].tagName] = data[key];
					break;
				case 1:
					ret[pointKs[key].desc] = data[key];
					break;
				case 2:
					break;
				default:
					ret[key] = data[key];
					break;
				}
		}
	});
	return ret;
}

/**
 * 切换显示标签。
 * 
 * @param tarF
 * @returns
 */
function switchTagFeatureInXYGraph(tarF) {
	if (tagFeatureInXYGraph != tarF) {
		// 修改前端显示
		var but = document.getElementById("labelSelectButtonUI");
		switch (tarF) {
		case 0:
			but.innerHTML = "tagName";
			break;
		case 1:
			but.innerHTML = "点位说明";
			break;
		}

		// 修改_data、cdata的标签
		var _cdataLabelArray = new Array();
		for (var ind = 0; ind < cdata.length; ind++) {
			var txtid = getOrigionLabel(cdata[ind][0]);
			_cdataLabelArray.push(txtid);
		}

		var __dataLabelArray = new Array();
		for (var ind = 0; ind < _data.length; ind++) {
			var txtid = getOrigionLabel(_data[ind][0]);
			__dataLabelArray.push(txtid);
		}

		// 修改tagFeatureInXYGraph的值
		tagFeatureInXYGraph = tarF;

		for (var ind = 0; ind < cdata.length; ind++) {
			if (_data[ind][0] != "time") {
				var txtid = _cdataLabelArray[ind];
				cdata[ind][0] = getNewLabelForData(txtid);
				// console.log("switch -> "+cdata[ind][0]);
			} else {
			}// 暂时不处理时间
		}
		// console.log("cdata -> "+JSON.stringify(cdata));

		var __dataLabelArray = new Array();
		for (var ind = 0; ind < _data.length; ind++) {
			if (_data[ind][0] != "time") {
				var txtid = getOrigionLabel(_data[ind][0]);
				_data[ind][0] = getNewLabelForData(txtid);
			} else {
			}// 暂时不处理时间
		}

		// 修改下面方法里保存后的标签：
		// _addPointToXY(getNewLabelForData(ele.getAttribute("id")));
		// addPointToXYGraph(getNewLabelForData(ele.getAttribute("id")));
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : xray,
				xFormat : '%Y',
				columns : cdata,
				type : 'spline',
			},
			zoom : {
				enabled : true
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
					show : true,
					label : 'Y2 Axis Label'
				}
			}
		});

		play();
	}
}

updateXYGraphChart();
function updateXYGraphChart(ruserSpace) {
	if (ruserSpace == null || ruserSpace == "undefined")
		ruserSpace = userSpace;
	// var pointGroup = _graph;
	var uixyGraphPoints = document.getElementById("ui-xyGraphPoints");
	// console.log(" updateXYGraphChart => "+JSON.stringify(pointGroup));
	if (pointGroup == null || pointGroup == "undefined")
		return;
	var pointList = pointGroup;
	// console.log("pointList = "+JSON.stringify(pointGroup));
	var innerHtml = "";
	var menuitem = document.getElementById("x_axisSelectButtonUIMenu");
	var _innerHtml = '<a class="dropdown-item" onclick="changex(\'time\')">时间</a>';
	for (var indpl = 0; indpl < pointList.length; indpl++) {
		var _itemHtml = '<a class="dropdown-item" onclick="changex(\''
				+ getNewLabelForData(pointList[indpl]) + '\')">'
				+ getNewLabelForData(pointList[indpl]) + '</a>';
		_innerHtml = _innerHtml + _itemHtml;
	}
	menuitem.innerHTML = _innerHtml;
}

/**
 * 刷新数据
 * 
 * @param data
 * @returns
 */
function refreshData(data) {
	// console.log("refreshData(data) -> "+JSON.stringify(data.body));
	var o = JSON.parse(data.body);
	Object.keys(o).forEach(function(key) {
		if (key != "time") {
			if (o[key] == -999999) {
				o[key] = "-";
			} else {
				var num = o[key];
				// var num = num+"";
				if (num != null && num != "undefined") {
					if (Math.round(num) < 999) {
						num = num.toFixed(3);
					} else {
						num = num.toFixed(1);
					}
					o[key] = num;
					// console.log("num = "+num+"");
				}
			}
		}
	});
	data.body = JSON.stringify(o);
	// console.log(" refreshData-> "+ JSON.stringify(data.body,null,2));
	// 添加进趋势图的换成点位说明。
	var _dat = changeDataToNewLabel(JSON.parse(data.body));
	// 添加趋势图数据到当前数据
	addData(_dat, cdata, cdataCount);
	// 添加趋势数据到全部数据
	addData(_dat, _data, 10);
	// console.log("cdata => "+JSON.stringify(cdata));

	var ids = document.getElementsByTagName("text");
	var _data_ = JSON.parse(data.body);
	// console.log(_data_);
	// console.log(pointKs);
	Object
			.keys(_data_)
			.forEach(
					function(key) {
						if (key != "time") {
							var ele = document.getElementById(key);
							// 画图形,要移到外层
							// var multistatesObj = new Object();
							// var bargraphsObj = new Object();
							if (multistatesObj[key] != null
									& multistatesObj[key] != "undefined") {
								console.log(" -> 刷新数据 ->添加一个点，text类型。");
								// 小数点位数
								var decimalCount = multistatesObj[key].pbnumberformat;
								decimalCount = decimalCount.substr(decimalCount
										.indexOf('.') + 1);
								// console.log(" decimalCount=" + decimalCount
								// + " decimalCount.length="
								// + decimalCount.length);
								ele.innerHTML = Math.round(_data_[key]
										* decimalCount.length * 10)
										/ (decimalCount.length * 10);
								console.log("ele.style.fill=" + ele.style.fill);
								if (ele.style.fill == "#000000")
									ele.style.fill = "#007800";
								// 设置显示样式
								var multistates = multistatesObj[key].pbmsstates;
								if (multistates != null
										&& multistates != "undefined") {
									// ele.style.color = "#007800";
									// ele.style.backgroundColor = "red";
									console.log("multistates[0].color="
											+ multistates[0].color);
									ele.style.fill = '#' + multistates[0].color;
									for (var imultistates = 0; imultistates < multistates.length; imultistates++) {
										if (_data_[key] != null
												&& _data_[key] >= multistates[imultistates].lowervalue
												&& _data_[key] <= multistates[imultistates].uppervalue) {
											ele.style.fill = '#'
													+ multistates[imultistates].color;
										} else {
										}
									}
								}
								// ele.style.fill = "#007800";

							} else if (bargraphsObj[key] != null
									& bargraphsObj[key] != "undefined") {
								// console.log(ele);
								var brotherRect = ele.previousElementSibling;
								if (brotherRect == null
										|| brotherRect == "undefined") {
									brotherRect = ele.nextElementSibling;
								}
								brotherRect.style.fill = brotherRect
										.getAttribute('fill');
								var rate = 0;
								if (_data_[key] >= bargraphsObj[key].start) {
									rate = bargraphsObj[key].upper
											- bargraphsObj[key].start;
								} else {
									rate = bargraphsObj[key].start
											- bargraphsObj[key].lower;
								}
								var start = bargraphsObj[key].start
										/ (bargraphsObj[key].upper - bargraphsObj[key].lower);

								switch (bargraphsObj[key].orientation) {
								case '0':
									rate = _data_[key] / rate;
									var height = brotherRect
											.getAttribute('height')
											* rate;
									ele.setAttribute('height', height);
									var startHeight = brotherRect
											.getAttribute('height')
											* start;
									start = eval(brotherRect.getAttribute('y'))
											+ eval(brotherRect
													.getAttribute('height'))
											- startHeight;
									// console.log("start = " + start
									// + " brotherRect.getAttribute('y')="
									// + brotherRect.getAttribute('y'));
									if (_data_[key] >= bargraphsObj[key].start) {
										ele.setAttribute('y', start - height);
									} else {
										ele.setAttribute('y', start);
									}
									// console.log(ele);

									break;
								case '1':
									rate = _data_[key] / rate;
									var width = brotherRect
											.getAttribute('width')
											* rate;
									ele.setAttribute('width', width);
									var startwidth = brotherRect
											.getAttribute('width')
											* start;
									start = eval(brotherRect.getAttribute('x'))
											+ eval(brotherRect
													.getAttribute('width'))
											- startwidth;
									// console.log("start = " + start
									// + " brotherRect.getAttribute('y')="
									// + brotherRect.getAttribute('y'));
									if (_data_[key] >= bargraphsObj[key].start) {
										ele.setAttribute('x', start - width);
									} else {
										ele.setAttribute('x', start);
									}
									// console.log(ele);
									break;
								}

							} else {
								if (ele != null && ele != "undefined") {
									ele.innerHTML = Math
											.round(_data_[key] * 10000) / 10000;
									// ele.style.fill = "#007800";
									// console.log("aa");
								} else {
									// console.log("不存在点：" + key);
								}
							}
						}
					});
	if (currentPlayStatus) {
		// 刷新线图
		// _forward(data.body);
		// play();
		// 刷新表格
		// refreshDataTable(cdata);
	}
}
function addOnClickEvent() {
	var _data_ = pointKs;
	Object
			.keys(_data_)
			.forEach(
					function(key) {
						if (key != "time") {
							var ele = document.getElementById(key);
							if (!isAddOnclickFunc && ele != null
									&& ele != "undefined") {
								if (ele.parentNode != null
										&& ele.parentNode != "undefined") {
									var _gNode = ele.parentNode;
									var ismultistate = _gNode
											.getAttribute("pb:ismultistate");
									console.log(ele);
									console.log();
									if (ismultistate != null
											&& ismultistate != "undefined"
											&& ele.nodeName == "text") {
										var pbtype = _gNode
												.getAttribute('pb:type');
										var pbnumberformat = _gNode
												.getAttribute('pb:numberformat');
										if (eval(ismultistate.toLowerCase())) {
											var multistate = ele
													.getElementsByTagName("pb:multistate");
											if (multistate != null
													&& multistate != "undefined"
													&& multistate.length > 0) {
												var pbmsstates = new Array();
												var states = multistate[0]
														.getElementsByTagName("pb:msstate");
												if (states != null
														&& states != "undefined") {
													for (var i = 0; i < states.length; i++) {
														var state = new Object();
														state['blink'] = states[i]
																.getAttribute('blink');
														state['color'] = states[i]
																.getAttribute('color');
														state['lowervalue'] = states[i]
																.getAttribute('lowervalue');
														state['uppervalue'] = states[i]
																.getAttribute('uppervalue');
														pbmsstates.push(state);
													}
												}
											}
										}
										var pointMultistate = new Object();
										pointMultistate['id'] = key;
										pointMultistate['pbtype'] = pbtype;
										pointMultistate['pbnumberformat'] = pbnumberformat;
										pointMultistate['pbmsstates'] = pbmsstates;
										// console.log(pointMultistate);
										multistatesObj[key] = pointMultistate;
									} else if (_gNode.getAttribute('pb:type') != null
											&& _gNode.getAttribute('pb:type') != "undefined") {
										// 找父g，找Orientation
										// bargraphsObj
										var state = new Object();
										state['type'] = _gNode
												.getAttribute('pb:type');
										state['lower'] = _gNode
												.getAttribute('pb:lower');
										state['orientation'] = _gNode
												.getAttribute('pb:orientation');
										state['canonicalnumberformat'] = _gNode
												.getAttribute('pb:canonicalnumberformat');
										state['start'] = _gNode
												.getAttribute('pb:start');
										state['upper'] = _gNode
												.getAttribute('pb:upper');
										bargraphsObj[key] = state;
										// console.log(_gNode);
										// console.log(state);
										// console.log(bargraphsObj);
									}

									if (ele.onclick == null
											|| ele.onclick == "undefined") {
										ele.onclick = function() { // 加事件
											// 添加进趋势图的换成点位说明。
											var _tagname = getNewLabelForData(ele
													.getAttribute("id"));
											_addPointToXY(_tagname);
											addPointToXYGraph(_tagname);
										}
									}
								} else {
									console.log("no element named " + key);
								}
							}
						}
					});
	console.log(multistatesObj);
	console.log(bargraphsObj);
}

/**
 * 刷新表格数据
 * 
 * @param _cdata
 * @returns
 */
function refreshDataTable(_cdata) {
	var _datatableUI = document.getElementById("_datatableUI");
	_datatableUI.innerHTML = "";
	var _thead = document.createElement("thead");
	var _tbody = document.createElement("tbody");
	for (var coli = 0; coli < _cdata.length; coli++) {
		var _tr = document.createElement("tr");
		for (var rowi = 0; rowi < _cdata[coli].length; rowi++) {
			// 设为x轴
			var _td = document.createElement("td");

			var _value = _cdata[coli][rowi];
			switch (typeof _value) {
			case 'number':
				_td.innerText = (Math.round(_value * 1000)) / 1000 + "";
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

		if (_cdata[coli][0] == "time") {
			_thead.append(_tr);
			_datatableUI.prepend(_thead);
		} else
			_tbody.append(_tr);
	}
	_datatableUI.append(_tbody);

}

// ********************* 开始 线图 *************************
/**
 * 总值
 */
var _data = new Array();
var _dataCount = 2400; // 前端保存的历史数据个数，这个要根据浏览器性能决定。

/**
 * 当前值范围
 */
var cdata = new Array();
var cdataCount = 600; // 趋势图里，一页的数据量。30秒一个数，这是两个小时间的数据量。
/**
 * 刷新值
 * 
 */

var currentStartTime;
var currentStartTimeInd;
function loadCData(startTime) {

	var _currentStartTimeInd = 0;
	var _dataIndex = {};
	for (var indrow = 0; indrow < _data.length; indrow++) {
		_dataIndex[_data[indrow][0]] = indrow;
	}
	if (startTime == null || startTime == "undefined") {
		_data[_dataIndex['time']][1];
	}

	// console.log("loadCData startTime = "+startTime);
	// console.log("debug 2");
	var dataInd = 0;
	var _datatime = _data[_dataIndex['time']];
	for (var inddata = 0; inddata < _datatime.length; inddata++) {
		if (startTime <= _datatime[inddata]) {
			dataInd = inddata;
			_currentStartTimeInd = dataInd;
			break;
		}
	}
	// console.log("debug 3");
	// console.log("debug "+JSON.stringify(_data));

	var _cdata = new Array();
	// console.log("cdataCount="+cdataCount);
	for (var rowcount = 0; rowcount < _data.length; rowcount++) {
		var loadCount = 0;
		var rowd = new Array();
		for (var icdata = 0; icdata < cdataCount; icdata++) {
			if (dataInd + icdata < _data[rowcount].length) {
				// console.log("_data[rowcount][dataInd+icdata]="+_data[rowcount][dataInd+icdata]);
				if (_data[rowcount][dataInd + icdata] < 1500000000000) {
					if (_minY == 0)
						_minY = Math.round(_data[rowcount][dataInd + icdata]);
					if (_maxY == 0)
						_maxY = Math.round(_data[rowcount][dataInd + icdata]);
					if (_minY > _data[rowcount][dataInd + icdata])
						_minY = Math.round(_data[rowcount][dataInd + icdata]);
					if (_maxY < _data[rowcount][dataInd + icdata])
						_maxY = Math.round(_data[rowcount][dataInd + icdata]);
				} else {
					// console.log(" time: "+_data[rowcount][dataInd+icdata]+" =
					// "+new Date(_data[rowcount][dataInd+icdata]));
				}

				if (_data[rowcount][dataInd + icdata] != 0
						&& !isNaN(_data[rowcount][dataInd + icdata])) {
					rowd.push(_data[rowcount][dataInd + icdata]);
					loadCount++;
				}
				// console.log("minY_b="+_minY);
				// console.log("maxY_b="+_maxY);

			} else
				break;
		}
		// console.log("loadCdata -> 加载数据："+_data[rowcount][0]+"，加载了
		// "+loadCount+"个数据。");
		// 从前面把头添加上
		// console.log("debug 4 => "+JSON.stringify(rowd));
		rowd.splice(0, 0, _data[rowcount][0]);
		// console.log("debug 5 => "+JSON.stringify(rowd));
		_cdata.push(rowd);
	}
	// console.log("debug 6 => "+JSON.stringify(_cdata));

	cdata = _cdata;
	// console.log(" ***** cdata="+JSON.stringify(cdata));

	// console.log("cdata= "+JSON.stringify(cdata));
	// currentStartTime = _data[_dataIndex['time']][1];
	// currentStartTimeInd = 1;
	// console.log("loadCdata 加载了"+cdata[0].length+"个数据。");
	currentStartTime = startTime;
	currentStartTimeInd = _currentStartTimeInd;
}

var _dataIndex;
var __a = 0;
/**
 * 将历史数据添加到_data中
 * 
 * @param historyData
 *            [["time",1582150656,1582150716],["CJY_TI2210",50.8666648864]]
 * @returns
 */
function addHistoryData(historyData) {
	// 数据不正常就返回
	if (historyData == null || historyData == "undefined")
		return;

	// console.log("1222f -> " + JSON.stringify(historyData));
	if (_data == null || _data == "undefined") { // 如果_data为空，就直接把数据赋值。因为参数里是有time列的。
		// console.log("addHistoryData 1");
		_data = historyData;
		for (var indrow = 0; indrow < _data.length; indrow++) {
			_dataIndex[_data[indrow][0]] = indrow;
		}
		currentStartTime = _data[_dataIndex['time']][1];
		currentStartTimeInd = 1;
	} else {
		// 先把time去掉，加完数据再加上
		// console.log("addHistoryData 2 _data="+JSON.stringify(_data));
		var historyIndex = {}; // 构建参数的索引
		for (var indrow = 0; indrow < historyData.length; indrow++) {
			historyIndex[historyData[indrow][0]] = indrow;
		}
		var _dataIndex = {}; // 构建_data的索引
		for (var indrow = 0; indrow < _data.length; indrow++) {
			_dataIndex[_data[indrow][0]] = indrow;
			if (_data[indrow].length == 2) {
				_data[indrow].pop();
			}
		}
		// console.log("addHistoryData 3 _data="+JSON.stringify(_data));

		var index_data = _data[0].length - 1; // 从_data数据的最后一个数据开始
		for (var indHis = historyData[0].length - 1; indHis > 1; indHis--) { // 从最后一个数据开始，到第2个数据结束，因为第一个数据是time标签列。
			// console.log(" addHistoryData ==>
			// "+_data[_dataIndex["time"]][index_data]+"
			// _IsNaN(_data[_dataIndex[time]][index_data])="+_IsNaN(_data[_dataIndex["time"]][index_data]));
			// console.log(" indHis = "+indHis + " =
			// "+_data[_dataIndex["time"]][index_data]+" =
			// "+historyData[historyIndex["time"]][indHis]);
			while (index_data > 0
					&& _data[_dataIndex["time"]][index_data] > new Date(
							historyData[historyIndex["time"]][indHis])) {
				index_data--; // 索引到目标数据，只会执行一次，下次并不会从头执行。
			}
			// console.log(" index_data = "+index_data);
			// 如果_data数据里的时间点 小于 历史数据的时间点，并且_data里的这个时间不为空
			if (_data[_dataIndex["time"]][index_data] < historyData[historyIndex["time"]][indHis]
					|| !_IsNaN(_data[_dataIndex["time"]][index_data])) {
				Object
						.keys(historyIndex)
						.forEach(
								function(key) {
									if (_data[_dataIndex[key]] != null
											&& _data[_dataIndex[key]] != "undefined") {
										if (key == "time") {
											_data[_dataIndex[key]]
													.splice(
															index_data + 1,
															0,
															new Date(
																	historyData[historyIndex[key]][indHis] * 1000));
											// console.log(key+" -
											// historyData[historyIndex[key]][indHis]="+historyData[historyIndex[key]][indHis]);
										} else {
											_data[_dataIndex[key]]
													.splice(
															index_data + 1,
															0,
															historyData[historyIndex[key]][indHis]);
										}
									} else {
										console
												.log(" abb1 -> "
														+ key
														+ "  - _dataIndex[key]="
														+ JSON
																.stringify(_dataIndex[key]));
										console.log(" abb1 -> " + key
												+ "  - _data=> "
												+ JSON.stringify(_data));
									}
									// console.log(key+" -
									// historyData[historyIndex[key]][indHis]="+historyData[historyIndex[key]][indHis]);
								});
				if (currentStartTimeInd > index_data)
					currentStartTimeInd++;
				// console.log("currentStartTimeInd="+currentStartTimeInd+" <=>
				// index_data="+index_data);
			}
		}
		// console.log("addHistoryData 4 _data="+JSON.stringify(_data));
		// console.log("addHistoryData 4 _data.length = "+_data[0].length);
	}
	__a++;
	// console.log("addHistoryData 4 _data.length = "+_data[0].length);

}

function _IsNaN(value) {
	return typeof value === 'number' && !isNaN(value);
}

/**
 * 添加数据到目标中，并且设置最大数据量上限。
 * 
 * @param newData
 *            待加入的新数据
 * @param _data_
 *            存储数据的库
 * @param cdatacount
 *            最大上限
 * @returns
 */
function addData(newData, _data_, cdatacount) {
	if (typeof newData == "string") {
		newData = JSON.parse(newData);
	}
	// console.log("addData");
	var _d_ = newData;
	var _ctime = new Date();// .Format('yyyy-MM-dd hh:mm:ss');
	var timeInd = 0;

	// console.log(a);
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
			if (_data_[ind_data][1] == 0) {
				_data_[ind_data][1] = _ctime;
			} else {
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

/**
 * 去服务器取数据，取回数据后，执行func方法
 * 
 * @param func
 * @returns
 */
function getHistoryData1(_historyDataDetailKey, startTime, endTime, pointGroup,
		func, backward_d) {
	// console.log("getHistoryData1 -> backcount="+backcount);
	var data = {
		'uid' : uid,
		'token' : token,
		'id' : _historyDataDetailKey,
		'beginTime' : startTime,
		'endTime' : endTime,
		"pointList" : pointGroup
	};
	// console.log("getHistoryData1 data = "+JSON.stringify(data));
	// console.log("getHistoryData1 startTime="+new Date(startTime)+"
	// endTime="+new Date(endTime)+" ");
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getHistoryData",
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
			hideLoading();
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
				var historyData = data.data.data;
				// console.log("historydata1.js 取回数据个数 => historyData1=
				// "+historyData[0].length);
				if (backward_d)
					currentStartTimeInd = currentStartTimeInd
							+ historyData[0].length;
				else {

				}
				// console.log("historydata.js => historyData1=
				// "+JSON.stringify(historyData));
				// if(__a<3)
				addHistoryData(historyData);
				// console.log("getHistoryData1 startTime="+startTime+" - "+new
				// Date(startTime));
				// console.log("getHistoryData1 historyData[0].length=
				// "+historyData[0].length);
				func();
			} else {
				alert("失败 ： " + data.msg);
				hideLoading();
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
function getHistoryData() {
	// console.log("otherrule = "+JSON.stringify(rule));
	var data = {
		'uid' : uid,
		'token' : token,
		'id' : _historyDataDetailKey,
		'beginTime' : '',
		'endTime' : ''
	};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getHistoryData",
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
				// console.log("server info : "+JSON.stringify(data.data.data));
				// console.log("historydata.js => submitNewDataItem 3");
				var historyData = data.data.data;
				// console.log("historydata.js => historyData=
				// "+JSON.stringify(historyData));
				addHistoryData(historyData);
				loadCData();
				reloadDataToDiagram();
				//
			} else {
				alert("失败 ： " + data.msg);
			}
			hideLoading();
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			hideLoading();

		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			/* 弹出jqXHR对象的信息 */
			hideLoading();
		}
	});

}

/**
 * x轴范围，y轴范围
 */
var _maxX = 0;
var _minX = 0;
var _rateX = 1;

/**
 * 当前播放状态。 true：播放状态。 false：停止状态。
 */
var currentPlayStatus = false;

function play() {
	currentPlayStatus = true;

	if (currentPlayStatus) {
		c3LineChart.load({
			type : 'spline',
			columns : cdata
		});
	}
	// console.log("play -> "+JSON.stringify(cdata));
}

/**
 * 取历史数据时开始时间与当前时间最小差
 */
var minCurrentTime = 60 * 1000;

/**
 * 向前，向后
 */
function _forward(_newData) {

	currentPlayStatus = false;

	// 如果是播放状态，就播放
	if (currentPlayStatus) {
		c3LineChart.load({
			type : 'spline',
			columns : cdata
		});
		c3LineChart.axis.min({
			y : _minY - (_maxY - _minY) / 2 * _rateY
		});
		c3LineChart.axis.max({
			y : _maxY + (_maxY - _minY) / 2 * _rateY
		});
	} else {
		// 否则加载历史数据
		// console.log(" _forward 0");
		// 更新cdata数据
		var _dataIndex = {};
		for (var indrow = 0; indrow < _data.length; indrow++) {
			_dataIndex[_data[indrow][0]] = indrow;
		}
		// console.log(" _forward 1");
		oneStep = cdataCount * 0.9;
		var startTime;
		if (currentStartTimeInd == null || currentStartTimeInd == "undefined") {
			currentStartTimeInd = 1;
		}
		{
			// console.log(" _forward 2
			// currentStartTimeInd="+currentStartTimeInd+"
			// _data.length="+_data[_dataIndex['time']].length+"
			// oneStep="+oneStep);
			if (currentStartTimeInd + oneStep < _data[_dataIndex['time']].length) { // 向左
				currentStartTimeInd = currentStartTimeInd + oneStep;
			} else {
				// TODO： 如果向右一步已经超过了_data的左边界，就去服务器取数据，添加后，再取
				currentStartTimeInd = _data[_dataIndex['time']][_data[_dataIndex['time']].length - 1]
						.getTime();
				var _endTime = currentStartTimeInd + oneStep * 1000;
				if (_endTime > new Date().getTime())
					_endTime = new Date().getTime();
				if (new Date().getTime() - currentStartTimeInd < minCurrentTime) {
					alert("取数据的开始时间太近了，现在还有更近的历史数据。");
					return;
				}
				getHistoryData1("", currentStartTimeInd, _endTime, pointGroup,
						_forward, false);
				return;
			}
		}
		var currentTime = _data[_dataIndex['time']][currentStartTimeInd];
		// console.log("currentTime="+currentTime);
		loadCData(currentTime);
		reloadDataToDiagram();
	}
}

/**
 * 向前，向后
 */
var oneStep = 0; // 点一下向前后，移动的点位数
function getCurrentStartTime() {
	for (var ind = 0; ind < cdata.length; ind++) {
		if (cdata[ind][0] == 'time')
			return cdata[ind][1];
	}

}
/**
 * 向前（左）移动一次
 * 
 * @returns
 */
var backcount = 0;
function _backward() {
	// console.log("_backward 1 "+ new Date());
	// 停止播放
	currentPlayStatus = false;
	// console.log("_backward ... ");
	// 更新cdata数据
	var _dataIndex = {};
	for (var indrow = 0; indrow < _data.length; indrow++) {
		_dataIndex[_data[indrow][0]] = indrow;
	}
	var _currentStartTimeInd;// 当前要查的时间的序号，是具体时间。// 因为要数据数量。
	var _endTime;
	oneStep = cdataCount * 0.9;
	if (currentStartTimeInd == null || currentStartTimeInd == "undefined") {
		currentStartTimeInd = 2;
		// console.log("_backward -> currentStartTimeInd === 2");
	}
	// console.log("_backward 2 "+ new Date());
	{
		if (currentStartTimeInd - oneStep > 0) { // 当前时间序号，向左一个oneStep那么多，仍然有值
			currentStartTimeInd = currentStartTimeInd - oneStep;
		} else { // 如果向左一步已经超过了_data的左边界，就去服务器取数据，添加后，再取
			// console.log("_backward currentStartTimeInd =
			// "+currentStartTimeInd);
			// 如果时间维数据大于1，且时间维里是有时间数据的
			if (_data[_dataIndex['time']].length > 1
					&& _data[_dataIndex['time']][1] != "undefined") {
				// 待取数的开始时间是第一个数据时间向前2个小时
				_currentStartTimeInd = _data[_dataIndex['time']][1].getTime()
						- 60 * 60 * 2 * 1000;
				_endTime = _data[_dataIndex['time']][1].getTime();
				// console.log("_backward 1..
				// _currentStartTimeInd="+_currentStartTimeInd+"
				// _endTime="+_endTime);
			} else {
				_currentStartTimeInd = new Date().getTime() - 60 * 60 * 2
						* 1000;
				_endTime = new Date().getTime();
				// console.log("_backward 2..
				// _currentStartTimeInd="+_currentStartTimeInd+"
				// _endTime="+_endTime);
			}
			// console.log("_backward.. to getHistoryData1 _startTime="+new
			// Date(_currentStartTimeInd)+" _endTime="+new Date(_endTime));
			backcount++;
			if (backcount < 3) {
				// console.log("_backward.. to getHistoryData1
				// backcount="+backcount);
				// console.log("_backward 3 "+ new Date());
				getHistoryData1("", _currentStartTimeInd, _endTime, pointGroup,
						buildChart, true);
				// console.log("_backward 4 "+ new Date());
			} else
				backcount = 0;
			return;
		}
	}
	var currentTime = _data[_dataIndex['time']][currentStartTimeInd];
	// console.log("_backward 5 "+ new Date());
	loadCData(currentTime);
	// console.log("_backward 6 "+ new Date());
	reloadDataToDiagram();
	// console.log("_backward 7 "+ new Date());

}

function _search() {
	// var _startTime_;
	// var _endTime_;
	// c3LineChart.flow(cdata,new Date()+"",1000);
	// console.log("flow");
	console.log(" abb2 -> _startTime_=" + _startTime_ + "  _endTime_="
			+ _endTime_);
	console.log(" abb2 -> pointGroup=" + JSON.stringify(pointGroup));
	// getHistoryData1(_historyDataDetailKey,_startTime_,_endTime_,addHistoryData);
	getHistoryData1("", _startTime_, _endTime_, pointGroup, _backward, true);
}

/**
 * x轴放大缩小
 */
// function zoomin_x() {
// _rateX = _rateX /2;
// cdataCount = cdataCount * 1.5;
// console.log("cdataCount = "+cdataCount);
// }
//
// function zoomout_x() {
// _rateX = _rateX *2;
// cdataCount = cdataCount / 1.5;
// console.log("cdataCount = "+cdataCount);
// }
function zoomin_x() {
	_rateX = _rateX / 2;
	cdataCount = cdataCount * 1.5;
	if (cdataCount >= _dataCount) {
		cdataCount = _dataCount;
		alert("最大可以同时显示" + _dataCount + "个数据，现在已经最大了。");
	}
	loadCData(getCurrentStartTime());
	reloadDataToDiagram();
	// console.log("cdataCount = "+cdataCount);
}

function zoomout_x() {
	_rateX = _rateX * 2;
	cdataCount = cdataCount / 1.5;
	if (cdataCount < 100)
		cdataCount = 100;
	// console.log("cdataCount = "+cdataCount);
	loadCData(getCurrentStartTime());
	reloadDataToDiagram();
}

/**
 * 重新加载cdata到图形
 * 
 * @returns
 */
function reloadDataToDiagram() {
	if (c3LineChart == null || c3LineChart == "undefined") {
		console.log("reloadDataToDiagram -> c3LineChart没有创建，执行buildChart");
		buildChart();
		return;
	}
	console.log("reloadDataToDiagram -> c3LineChart已经创建");
	// UI加载数据，显示
	// TODO: 增加flow效果
	// var _ids = new Array();
	// for(var indc=0;indc<cdata.length;indc++){
	// _ids.push(cdata[indc][0]);
	// }
	// c3LineChart.unload({
	// ids:_ids
	// });
	// c3LineChart.load({
	// columns : cdata,
	// unload: ['data2', 'data3']
	// });

	showLoading();
	c3LineChart = c3.generate({
		bindto : '#ui-historyDataLineChart',
		data : {
			x : xray,
			xFormat : '%Y',
			columns : cdata,
			type : 'spline',
		},
		zoom : {
			enabled : true
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
				localtime : true,
				tick : {
					format : '%Y-%m-%d %H:%M:%S'
				}
			},
			y : {
				show : true,
				label : 'Y2 Axis Label'
			}
		}
	});
	hideLoading();
	c3LineChart.axis.min({
		y : Math.round(_minY - (_maxY - _minY) / 2 * _rateY)
	});
	c3LineChart.axis.max({
		y : Math.round(_maxY + (_maxY - _minY) / 2 * _rateY)
	});
}

var _maxY = 0;
var _minY = 0;
var _rateY = 1;
function zoomin_y() {
	_rateY = _rateY / 2
	// console.log(" _rateY = "+_rateY);
}
function zoomout_y() {
	_rateY = _rateY * 2
	// console.log(" _rateY = "+_rateY);
}

/**
 * 换X轴
 */
var xray = "time";
function changex(tname) {
	// console.log("click: ========================== "+tname);
	var but = document.getElementById("x_axisSelectButtonUI");
	but.innerHTML = tname;
	xray = tname;
	// console.log(tname);
	// but.text = tname;
	if (tname == "time") {
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : xray,
				xFormat : '%Y',
				columns : cdata,
				type : 'spline',
			},
			zoom : {
				enabled : true
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
					localtime : true,
					tick : {
						format : '%Y-%m-%d %H:%M:%S'
					}
				},
				y : {
					show : true,
					label : 'Y2 Axis Label'
				}
			}
		});
	} else {
		// console.log("");
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : xray,
				xFormat : '%Y',
				columns : cdata,
				type : 'spline',
			},
			zoom : {
				enabled : true
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
					show : true,
					label : 'Y2 Axis Label'
				}
			}
		});
	}
}

function setConnected(connected) {
}

loginWebsocket();
function loginWebsocket() {
	// console.log(" debug 1");

	if (!connected) {
		// console.log("新建连接");
		connect(graphSubscribe);
		return;
	} else {
		// console.log("当前存在");
		graphSubscribe();
	}
}

function getGraphByURLPath(graph, urlPath) {
	if (urlPath == null || urlPath == "undefined")
		return graph;
	// console.log("graph.urlPath="+graph.urlPath.toLowerCase()+" ==
	// "+"urlPath="+urlPath.toLowerCase());
	if (graph.urlPath.toLowerCase() == urlPath.toLowerCase()) {
		// console.log("找到了");
		_graphId = graph.id;
		return graph;
	} else {
		// console.log(graph.urlPath.toLowerCase()+" 3");
		var children = graph.children;
		if (children != null) {
			var keys = new Array();
			Object.keys(children).forEach(function(key) {
				// console.log("4"+key);
				keys.push(key);
				children[key]
			});
			for (var i = 0; i < keys.length; i++) {
				var child = children[keys[i]];
				if (urlPath.toLowerCase().indexOf(child.urlPath) != -1) {
					return getGraphByURLPath(child, urlPath);
				}
			}
		}
		return null;
	}
}

function getGraphByPath(graph, path) {
	if (path == null || path == "undefined")
		return graph;
	// console.log("graph.wholePath="+graph.wholePath.toLowerCase()+" ==
	// "+"path="+path.toLowerCase());
	if (graph.wholePath.toLowerCase().indexOf(path.toLowerCase()) != -1) {
		// console.log("找到了");
		_graphId = graph.id;
		return graph;
	} else {
		// console.log(graph.wholePath.toLowerCase()+" 3");
		var children = graph.children;
		if (children != null) {
			var keys = new Array();
			Object.keys(children).forEach(function(key) {
				// console.log("4"+key);
				keys.push(key);
				children[key]
			});
			for (var i = 0; i < keys.length; i++) {
				var child = children[keys[i]];
				var t = getGraphByPath(child, path);
				if (t != null)
					return t;
			}
		}
		return null;
	}
}

function graphSubscribe() {
	// console.log(" debug 1");
	if (userSpace == null | userSpace == "undefined") {
		return getUserSpace(uid, token, graphSubscribe);
	} else {
		if (_graphId == null || _graphId == "undefined") {
			// console.log(" graph.js -> graphSubscribe() ->
			// getGraphByURLPath(graph,_diagramShowKey="+_diagramShowKey+")");
			var g = getGraphByPath(userSpace.graph, _diagramShowKey);
			if (g == null || g == "undefined" || g.id == null
					|| g.id == "undefined") {
				alert("不能访问这个图形。");
				return;
			}

			_graphId = g.id;

		}
		if (subscribe != null && subscribe != "undefined")
			subscribe.unsubscribe();
		stompClient.send("/app/aaa", {
			atytopic : _graphId,
			type : 'graph',
			id : _graphId + ""
		}, JSON.stringify({
			'type' : 'graph',
			'id' : _graphId + ""
		}));
		// 接收消息设置
		subscribe = stompClient.subscribe('/topic/Key_Graph_pre_/' + _graphId,
				function(data) {
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
		// console.log("websocket connected. .");
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
		subscribe = stompClient.subscribe('/topic/Key_Graph_pre_/' + _graphId,
				function(data) {
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

function save_Data2File() {
	console.log("save_Data2File");
	if (_data == null | _data == "undefined" || _data.length == 0) {
		alert("当前还没有历史数据，请先查询后再执行导出操作。");
		return;
	}
	var _exp = "";
	console.log("0 -> " + JSON.stringify(_data));
	var _dataIndex = new Array();
	for (var indrow = 0; indrow < _data.length; indrow++) {
		_dataIndex[_data[indrow][0]] = indrow;
	}

	var title = "";
	title += 'time';
	for (var itind = 0; itind < _data.length; itind++) {
		console.log(" 1 -> " + _data[itind]);
		if (itind != _dataIndex['time'])
			title += " \t ," + (_data[itind][0]);
	}
	_exp += title;

	for (var indtime = 1; indtime < _data[_dataIndex['time']].length; indtime++) {
		var _row = "";
		var _ti = new Date(_data[_dataIndex['time']][indtime]);
		console.log("year = " + _ti.getFullYear());
		_ti = _ti.getFullYear() + "-" + (_ti.getMonth() + 1) + "-"
				+ _ti.getDate() + " " + _ti.getHours() + ":" + _ti.getMinutes()
				+ ":" + _ti.getSeconds();// +" "+_ti.getMilliseconds();
		_ti = _ti.format("yyyy-MM-dd hh:mm:ss");
		console.log(" 2 -> " + _data[_dataIndex['time']][indtime]);
		_row += _ti;
		for (var itind1 = 0; itind1 < _data.length; itind1++) {
			console.log(" 3 -> " + _data[itind1][indtime] + " ");
			if (itind1 != _dataIndex['time'])
				_row += " \t ," + (_data[itind1][indtime]);
		}
		_exp += " \n " + _row;
	}
	console.log("_exp => " + _exp);
	saveToFile("exportHistory.csv", _exp);
	// saveToFile("exportHistory.csv",_data);
}

function saveToFile(fileName, code) {
	// if(isEmpty(code)) {
	// code = '';
	// }

	var file = new File([ code ], fileName, {
		type : "text/plain;charset=utf-8"
	});
	saveAs(file);
}

function loadDirectory() {
	if (isloadDefaultGraph != null && isloadDefaultGraph != "undefined") {
		isloadDefaultGraph = false;
	} else {
		isloadDefaultGraph = false;
	}

	if (_graph != null && _graph != "undefined") {
		var path = _graph.path;
		var __g = getGraphByPath(userSpace.graph, path);
		routeTo('diagramList', path, __g.id);
	}
}
