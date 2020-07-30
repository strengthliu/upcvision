/**
 * 
 */

// 图表组件
var gl = new Array();
var charts = new Object();

// ID
console.log("_historyDataDetailKey: " + _routeID);
var _historyDataDetailKey = _routeID;

function newItemAction() {
	alert("historyData.js newItemAction。 从mainPanel中调用的。");
}

/**
 * 刷新数据
 * 
 * @returns
 */
function refreshData(data) {

	var pointList_ = JSON.parse(data.body);
	// console.log("current Time = "+new Date()+" data:"+data.body);
	addData(data.body, cdata, cdataCount);
	// 刷新显示点图
	for ( var key in pointList_) {
		for ( var p in gl) {
			// console.log(gl[p].config.id + " == "+"point_" + key );
			if (gl[p].config.id == ("point_" + key)) {
				gl[p].refresh(pointList_[key]);
			}
		}
	}
	_forward(data.body);
	// 刷新表格
	refreshDataTable(cdata);
}

function refreshDataTable(_cdata) {
	var _datatableUI = document.getElementById("_datatableUI");
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
			switch (typeof _value) {
			// console.log(" typeof => "+typeof(_value));
			case 'number':
				_td.innerText = (Math.round(_value * 1000)) / 1000 + "";
				break;
			case 'string':
				_td.innerText = _cdata[coli][rowi];// _timeStr;
				break;
			default:
				var _t = new Date(_cdata[coli][rowi]);
				_td.innerText = _t.Format("hh:mm:ss");// _timeStr;
				// _td.innerText = _cdata[coli][rowi];//_timeStr;
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
var _dataCount = 10000;

/**
 * 当前值范围
 */
var cdata = new Array();
var cdataCount = _dataCount;// 300;

/**
 * x轴范围，y轴范围
 */

var _maxX = 0;
var _minX = 0;
var _rateX = 1;

/**
 * x轴放大缩小
 */
function zoomin_x() {
	_rateX = _rateX / 2;
	cdataCount = cdataCount * 1.5;
	loadCData(getCurrentStartTime());
	reloadDataToDiagram();
	console.log("cdataCount = " + cdataCount);
}

function zoomout_x() {
	_rateX = _rateX * 2;
	cdataCount = cdataCount / 1.5;
	console.log("cdataCount = " + cdataCount);
	loadCData(getCurrentStartTime());
	reloadDataToDiagram();
}
var _maxY = 0;
var _minY = 0;
var _rateY = 1;
function zoomin_y() {
	_rateY = _rateY / 2
	console.log(" _rateY = " + _rateY);
	reloadDataToDiagram();

}
function zoomout_y() {
	_rateY = _rateY * 2
	console.log(" _rateY = " + _rateY);
	reloadDataToDiagram();
}

/**
 * 换X轴
 */
function changex() {

}

function reloadDataToDiagram() {
	console.log('------ reloadDataToDiagram 1 ----');
	console.log(cdata);
	console.log('------ reloadDataToDiagram 2 ----');
	console.log(_data);
	console.log('------ reloadDataToDiagram 3 ----');

	// UI加载数据，显示
	// TODO: 增加flow效果
	c3LineChart.load({
		columns : cdata
	});

	// console.log("_rateY="+_rateY);
	// console.log("minY_b="+_minY);
	// console.log("minY="+Math.round(_minY - (_maxY-_minY)/2 * _rateY));
	// c3LineChart.axis.min({
	// y : Math.round(_minY - (_maxY - _minY) / 2 * _rateY)
	// });
	// // console.log("maxY_b="+_maxY);
	// // console.log("maxY="+Math.round(_maxY + (_maxY-_minY)/2 * _rateY));
	// c3LineChart.axis.max({
	// y : Math.round(_maxY + (_maxY - _minY) / 2 * _rateY)
	// });
	// console.log('------ reloadDataToDiagram 4 ----');

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

	// 更新_data索引
	var _dataIndex = {};
	for (var indrow = 0; indrow < _data.length; indrow++) {
		_dataIndex[_data[indrow][0]] = indrow;
	}
	// 确定时间数量
	var _currentStartTimeInd;// 当前要查的时间的序号，是具体时间。// 因为要数据数量。
	var _endTime;
	oneStep = cdataCount * 0.9;
	if (currentStartTimeInd == null || currentStartTimeInd == "undefined") {
		currentStartTimeInd = 2;
		// console.log("_backward -> currentStartTimeInd === 2");
	}
	console.log("currentStartTimeInd=" + currentStartTimeInd + " oneStep="
			+ oneStep);
	if (currentStartTimeInd - oneStep > 0) { // 当前时间序号，向左一个oneStep那么多，仍然有值
		currentStartTimeInd = currentStartTimeInd - oneStep;
	} else { // 如果向左一步已经超过了_data的左边界，就去服务器取数据，添加后，再取
		// console.log("_backward currentStartTimeInd =
		// "+currentStartTimeInd);
		// 如果时间维数据大于1，且时间维里是有时间数据的
		if (_data[_dataIndex['time']].length > 1
				&& _data[_dataIndex['time']][1] != "undefined") {
			// 待取数的开始时间是第一个数据时间向前2个小时
			_currentStartTimeInd = _data[_dataIndex['time']][1].getTime() - 60
					* 60 * 2 * 1000;
			_endTime = _data[_dataIndex['time']][1].getTime();
			// console.log("_backward 1..
			// _currentStartTimeInd="+_currentStartTimeInd+"
			// _endTime="+_endTime);
		} else {
			_currentStartTimeInd = new Date().getTime() - 60 * 60 * 2 * 1000;
			_endTime = new Date().getTime();
			// console.log("_backward 2..
			// _currentStartTimeInd="+_currentStartTimeInd+"
			// _endTime="+_endTime);
		}
		// console.log("_backward.. to getHistoryData1 _startTime="+new
		// Date(_currentStartTimeInd)+" _endTime="+new Date(_endTime));
		backcount++;
		if (backcount < 10) {
			// console.log("_backward 3 "+ new Date());
			// getHistoryData1("", _currentStartTimeInd, _endTime,
			// buildChart, true);
			console.log("_currentStartTimeInd=" + _currentStartTimeInd
					+ " _endTime=" + _endTime);
			getHistoryData1(_historyDataDetailKey, _currentStartTimeInd,
					_endTime, _backward);

			// console.log("_backward 4 "+ new Date());
		} else
			backcount = 0;
		return;
	}

	var currentTime = _data[_dataIndex['time']][currentStartTimeInd];
	// console.log("_backward 5 "+ new Date());
	loadCData(currentTime);
	console.log("cdata=" + JSON.stringify(cdata, null, 2));
	// console.log("_backward 6 "+ new Date());
	reloadDataToDiagram();

	// console.log("_backward 7 "+ new Date());

}

function _search() {
	// var _startTime_;
	// var _endTime_;
	console.log('--------------------- _search 1--------------');
	console.log("_startTime_=" + _startTime_ + ' => ' + new Date(_startTime_)
			+ "  _endTime_=" + _endTime_ + ' => ' + new Date(_endTime_));
	// getHistoryData1(_historyDataDetailKey, _startTime_, _endTime_,
	// addHistoryData);
	getHistoryData1(_historyDataDetailKey, _startTime_, _endTime_,
			reloadDataToDiagram);
	console.log('--------------------- _search 2--------------');
	// reloadDataToDiagram();
	console.log('--------------------- _search 3--------------');
	console.log(cdata);
	console.log('--------------------- _search 4--------------');
}

/**
 * 向前，向后
 */
function _forward() {
	console.log(" _forward 0");
	// 更新cdata数据
	var _dataIndex = {};
	for (var indrow = 0; indrow < _data.length; indrow++) {
		_dataIndex[_data[indrow][0]] = indrow;
	}

	// if(_dataIndex==null||_dataIndex=="undefined"){
	// for(var indrow=0;indrow<_data.length;indrow++){
	//			
	// _dataIndex[_data[indrow][0]]= indrow;
	// }
	// }
	console.log(" _forward 1");
	oneStep = cdataCount * 0.9;
	var startTime;
	if (currentStartTimeInd == null || currentStartTimeInd == "undefined") {
		currentStartTimeInd = 1;
	} else {
		console.log(" _forward 2");

		if (currentStartTimeInd + oneStep < _data[_dataIndex['time']].length) { // 向左
			currentStartTimeInd = currentStartTimeInd + oneStep;
		} else {
			// TODO： 如果向右一步已经超过了_data的左边界，就去服务器取数据，添加后，再取
			currentStartTimeInd = _data[_dataIndex['time']][_data[_dataIndex['time']].length - 1]
					.getTime();
			if (currentStartTimeInd + oneStep * 1000 > new Date().getTime()) {
				if (currentStartTimeInd > new Date().getTime() - 60 * 1000) {
					alert("当前已经是最近的历史数据，，最近1分钟的数据请查看实时数据或稍后再看。");
				} else {
					getHistoryData1(_historyDataDetailKey, currentStartTimeInd,
							new Date().getTime(), _forward);
				}
			} else {
				getHistoryData1(_historyDataDetailKey, currentStartTimeInd,
						currentStartTimeInd + oneStep * 1000, _forward);
			}
			return;
		}
	}
	var currentTime = _data[_dataIndex['time']][currentStartTimeInd];
	console.log("currentTime=" + currentTime);
	loadCData(currentTime);
	reloadDataToDiagram();
}

/**
 * 去服务器取数据，取回数据后，执行func方法
 * 
 * @param func
 * @returns
 */
function getHistoryData1(_historyDataDetailKey, startTime, endTime, func) {
	var data = {
		'uid' : uid,
		'token' : token,
		'id' : _historyDataDetailKey,
		'beginTime' : startTime,
		'endTime' : endTime
	};
	console.log("getHistoryData1  data = " + JSON.stringify(data));
	$
			.ajax({
				// 提交数据的类型 POST GET
				type : "POST",
				// 提交的网址
				url : "getHistoryData",
				// 提交的数据
				data : JSON.stringify(data),
				contentType : "application/json",
				// 返回数据的格式
				datatype : "json",// "xml", "html", "script", "json", "jsonp",
				// "text".
				// 在请求之前调用的函数
				beforeSend : function() {
					showLoading();
				},
				// 成功返回之后调用的函数
				success : function(data) {
					hideLoading();
					if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS)
						// {
						var historyData = data.data.data;
						// console.log("historydata.js => historyData1= "
						// + JSON.stringify(historyData));
						// if(__a<3)
						console
								.log('--------------------- getHistoryData1 1--------------');

						addHistoryData(historyData);
						console
								.log('--------------------- getHistoryData1 2--------------');
						console.log('gethistorydata 1');
						// console.log("getHistoryData1 startTime="+startTime+"
						// - "+new
						// Date(startTime));
						// console.log("getHistoryData1 historyData[0].length=
						// "+historyData[0].length);
						if (func != null && func != "undefined")
							func();
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
				// console.log("historydata.js => historyData= "
				// + JSON.stringify(historyData));
				console.log('gethistorydata no 1');
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

var currentStartTime;
var currentStartTimeInd;
function loadCData(startTime) {

	var _currentStartTimeInd = 0;
	var _dataIndex = {};
	for (var indrow = 0; indrow < _data.length; indrow++) {
		_dataIndex[_data[indrow][0]] = indrow;
	}
	if (startTime == null || startTime == "undefined") {
		startTime = _data[_dataIndex['time']][1];
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
		console.log(' ---> ' + _data[rowcount].length);
		for (var icdata = 0; icdata < cdataCount; icdata++) {
			if (dataInd + icdata < _data[rowcount].length) {
				// console.log("_data[rowcount][dataInd+icdata]="+_data[rowcount][dataInd+icdata]);
				if (_data[rowcount][dataInd + icdata] < 1500000000000) {
					if (_minY == 0)
						_minY = Math.round(_data[rowcount][dataInd + icdata]);
					if (_maxY == 0)
						_maxY = Math.round(_data[rowcount][dataInd + icdata]);
					if (_data[rowcount][dataInd + icdata] != -1
							&& _minY > _data[rowcount][dataInd + icdata])
						_minY = Math.round(_data[rowcount][dataInd + icdata]);
					if (_maxY < _data[rowcount][dataInd + icdata])
						_maxY = Math.round(_data[rowcount][dataInd + icdata]);
				} else {
					// console.log(" time: "+_data[rowcount][dataInd+icdata]+" =
					// "+new Date(_data[rowcount][dataInd+icdata]));
				}

				// 
				// if (_data[rowcount][dataInd + icdata] != -1 // 0
				// && !isNaN(_data[rowcount][dataInd + icdata])) {
				// rowd.push(_data[rowcount][dataInd + icdata]);
				// loadCount++;
				// }
				if (!isNaN(_data[rowcount][dataInd + icdata])) {
					rowd.push(_data[rowcount][dataInd + icdata]);
					loadCount++;
				}
				// console.log("minY_b="+_minY);
				// console.log("maxY_b="+_maxY);

			} else {
				console.log('empty dataInd=' + dataInd + '  icdata=' + icdata
						+ ' rowcount=' + rowcount + ' ');
				break;
			}
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
	console.log(_cdata);
	// cdata = _cdata;
	cdata = _data;
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
function addHistoryData(historyData) {
	// 数据不正常就返回
	if (historyData == null || historyData == "undefined")
		return;
	// _data = historyData;
	// for (var i = 1; i < _data[0].length; i++) {
	// _data[0][i] = new Date(_data[0][i] * 1000);
	// }

	// _data = historyData;
	if (_data == null || _data == "undefined") {
		// console.log("addHistoryData 1");
		_data = historyData;
		for (var indrow = 0; indrow < _data.length; indrow++) {
			_dataIndex[_data[indrow][0]] = indrow;
		}
		currentStartTime = _data[_dataIndex['time']][1];
		currentStartTimeInd = 1;
	} else {
		for (var in1 = 0; in1 < _data.length; in1++) {
			_data[in1].splice(1, _data[in1].length - 1);
		}
		// console.log("addHistoryData 2 _data="+JSON.stringify(_data));
		var historyIndex = {};
		for (var indrow = 0; indrow < historyData.length; indrow++) {
			historyIndex[historyData[indrow][0]] = indrow;
		}
		var _dataIndex = {};
		for (var indrow = 0; indrow < _data.length; indrow++) {
			_dataIndex[_data[indrow][0]] = indrow;
			if (_data[indrow].length == 2) {
				_data[indrow].pop();
			}
		}
		// console.log("addHistoryData 3 _data=" + JSON.stringify(_data));

		var index_data = _data[0].length - 1;
		for (var indHis = historyData[0].length - 1; indHis > 1; indHis--) {
			while (index_data > 0
					&& _data[_dataIndex["time"]][index_data] > new Date(
							historyData[historyIndex["time"]][indHis])) {
				index_data--;
			}
			console.log(" index_data = " + index_data);
			if (_data[_dataIndex["time"]][index_data] < historyData[historyIndex["time"]][indHis]
					|| !_IsNaN(_data[_dataIndex["time"]][index_data])) {
				Object
						.keys(historyIndex)
						.forEach(
								function(key) {
									if (key == "time") {
										_data[_dataIndex[key]]
												.splice(
														index_data + 1,
														0,
														new Date(
																historyData[historyIndex[key]][indHis] * 1000));
										// console
										// .log(key
										// + " -
										// historyData[historyIndex[key]][indHis]="
										// +
										// historyData[historyIndex[key]][indHis]);
									} else {
										_data[_dataIndex[key]]
												.splice(
														index_data + 1,
														0,
														historyData[historyIndex[key]][indHis]);
									}
									// console.log(key+" -
									// historyData[historyIndex[key]][indHis]="+historyData[historyIndex[key]][indHis]);
								});
				if (currentStartTimeInd >= index_data) {

					currentStartTimeInd++;
					// console.log("addHistoryData 0 -> currentStartTimeInd="
					// + currentStartTimeInd);
				} else {
					// console.log("addHistoryData 1 -> currentStartTimeInd="
					// + currentStartTimeInd);
				}
				// console.log("currentStartTimeInd=" + currentStartTimeInd
				// + " <=> index_data=" + index_data);
			}
		}
		// console.log("addHistoryData 4 _data="+JSON.stringify(_data));
		// console.log("addHistoryData 4 _data.length = "+_data[0].length);
	}
	__a++;
	console.log('--------- addHistoryData 1 -----');

	console.log(_data);
	console.log('--------- addHistoryData 2 -----');

}
function _IsNaN(value) {
	return typeof value === 'number' && !isNaN(value);
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

getHistoryData();

var c3LineChart;
(function($) {
	'use strict';
	var cols = new Array();
	var pointGroup = userSpace.historyData[_historyDataDetailKey];
	var pointList = pointGroup.pointList;
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
	c3LineChart = c3.generate({
		bindto : '#ui-historyDataLineChart',
		data : {
			x : 'time',
			xFormat : '%Y',
			columns : cols,
			type : 'spline',
			axes : {},
			labels : false,
			onclick : function(d, element) {
				showDataList(d, element);
			}
		},
		grid : {
			x : {
				show : true
			},
			y : {
				lines : [ {
					value : 50,
					text : 'Label 50 for y'
				}, {
					value : 1300,
					text : 'Label 1300 for y2',
					axis : 'y2',
					position : 'start'
				}, {
					value : 350,
					text : 'Label 350 for y',
					position : 'middle'
				} ]
			}
		},
		zoom : {
			enabled : true
		},
		color : {
			pattern : [ 'rgba(88,216,163,1)', 'rgba(237,28,36,0.6)',
					'rgba(4,189,254,0.6)' ]
		},
		padding : {
			top : 10,
			right : 50,
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
			// label: 'Y2 Axis Label',
			// max:100,
			// min:0
			}
		}
	});

})(jQuery);
// TODO: 点击下面的位号名，显示图中的数据的列表
function showDataList(d, element) {
	console.log(JSON.stringify(d) + "  " + JSON.stringify(element));
}
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

//
// if (userSpace == null || userSpace == "undefined") {
// console.log("userSpace 为空，重新到服务器去取。");
// getUserSpace(user.id, token, updateHistoryDataChart);
// } else
// updateHistoryDataChart(userSpace);

// /**
// * 画点图 getHistoryData
// */
// function updateHistoryDataChart(ruserSpace) {
// var pointGroup = ruserSpace.historyData[_historyDataDetailKey];
// var uihistoryDataPoints = document.getElementById("ui-historyDataPoints");
// console.log(" updateHistoryDataChart => " + JSON.stringify(pointGroup));
// if (pointGroup == null || pointGroup == "undefined")
// return;
// var pointList = pointGroup.pointList;
// var innerHtml = "";
// // console.log("pointList" + JSON.stringify(pointList));
// for (var indpl = 0; indpl < pointList.length; indpl++) {
// // console.log(" updateHistoryDataChart=>
// // "+JSON.stringify(pointList[indpl]));
// try {
// // 页面加一块
// var item = '<div class="box col-lg-3"><div class="gauge" id="point_'
// + pointList[indpl].tagName + '"></div></div>';
// innerHtml += item;
// } catch (e) {
//
// }
// }
// uihistoryDataPoints.innerHTML = innerHtml;
// console.log(uihistoryDataPoints.innerHTML);
//
// for (var indpl = 0; indpl < pointList.length; indpl++) {
// console.log();
// // 对象加一条
// var gt = new JustGage({
// id : "point_" + pointList[indpl].tagName,
// value : 0,
// min : 0,
// max : 100,
// title : pointList[indpl].desc,// "一级电脱盐混合阀压差",
// label : pointList[indpl].enunit,
// donut : true,
// gaugeWidthScale : 0.6,
// counter : true,
// hideInnerShadow : true
// });
// var _tagName_ = pointList[indpl].tagName;
// charts[_tagName_] = indpl;
// gl[indpl] = gt;
// }
// }

// loginWebsocket();
//
// function loginWebsocket() {
// if (socket.readyState != 1) {
// alert("未连接。");
// connect();
// return;
// } else {
// console.log("当前存在");
// if (subscribe != null && subscribe != "undefined")
// subscribe.unsubscribe();
// stompClient.send("/app/aaa", {
// atytopic : _historyDataDetailKey,
// type : 'historyData',
// id : _historyDataDetailKey + ""
// }, JSON.stringify({
// 'type' : 'historyData',
// 'id' : _historyDataDetailKey + ""
// }));
// // 接收消息设置
// subscribe = stompClient.subscribe('/topic/Key_HistoryData_pre_/'
// + _historyDataDetailKey, function(data) {
// // alert("websocket connected 3.");
// // 收到消息后处理
// refreshData(data);
// });
// }
// }
//
// function connect() {
// sessionStorage.setItem('token', token);// 设置指定session值
// sessionStorage.setItem('uid', user.id);// 设置指定session值
//
// // const id = localStorage.getItem("chat_id");
// // var socket = new SockJS('ws://localhost:8888/socketServer/');
// socket = new SockJS('/socketServer');
// // alert("websocket connected 1.");
// stompClient = Stomp.over(socket);
// stompClient.heartbeat.outgoing = 10000; // 客户端每20000ms发送一次心跳检测
// stompClient.heartbeat.incoming = 10000; // client接收serever端的心跳检测
// // 连接服务器
// var headers = {
// login : user.id,
// token : token,
// // additional header
// 'client-id' : 'my-client-id'
// };
//
// stompClient.connect(headers, function(frame) {
// setConnected(true);
// console.log("websocket connected." + _historyDataDetailKey + " .");
// // console.log('Connected: ' + frame);
//
// // 发送消息给服务器
// stompClient.send("/app/aaa", {
// atytopic : _historyDataDetailKey,
// type : 'Key_HistoryData_pre_',
// id : _historyDataDetailKey
// }, JSON.stringify({
// 'type' : 'historyData',
// 'id' : _historyDataDetailKey
// }));
// // 连接成功后，主动拉取未读消息
// // pullUnreadMessage("/topic/reply");
// // 接收消息设置
// subscribe = stompClient.subscribe('/topic/Key_HistoryData_pre_/'
// + _historyDataDetailKey, function(data) {
// // alert("websocket connected 3.");
// // 收到消息后处理
// refreshData(data);
// });
//
// /**
// * // 接收消息设置。该方法是接收广播消息。 stompClient.subscribe('/topic/greeting/11',
// * function(greeting){ showGreeting(JSON.parse(greeting.body).content);
// * }); //
// * 接收消息设置。该方法表示接收一对一消息，其主题是"/user/"+userId+"/message"，不同客户端具有不同的id。 //
// * 如果两个或多个客户端具有相同的id，那么服务器端给该userId发送消息时，这些客户端都可以收到。
// * stompClient.subscribe('/user/' + user.id +
// * '/message',function(greeting){
// * alert(JSON.parse(greeting.body).content);
// * showGreeting(JSON.parse(greeting.body).content); });
// *
// */
// // alert("websocket connected 2.");
// stompClient.ws.onclose = function() {
// connect();
// }
// stompClient.ws.onerror = function() {
// connect();
// }
// }, function(message) {
// console.log(message);
// });
// }
//
// // 从服务器拉取未读消息
// function pullUnreadMessage(destination) {
// $.ajax({
// url : "/wsTemplate/pullUnreadMessage",
// type : "POST",
// dataType : "json",
// async : true,
// data : {
// "destination" : destination
// },
// success : function(data) {
// if (data.result != null) {
// $.each(data.result, function(i, item) {
// log(JSON.parse(item).content);
// })
// } else if (data.code != null && data.code == "500") {
// layer.msg(data.msg, {
// offset : 'auto',
// icon : 2
// });
// }
// }
// });
// }

