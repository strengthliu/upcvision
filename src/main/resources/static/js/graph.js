//
/**
 * 右键菜单
 * 
 * @param $
 * @returns
 */
//***************** 开始  右键菜单  *************************
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
//***************** 结束  右键菜单  *************************


var pointInfoMapper;
if(pointInfoMapper == null || pointInfoMapper=="undefined") {
	// TODO: ajax调用getGraphPointInfoMapper，取pointInfoMapper信息。
}

//***************** 开始  画图  *************************

var gl = new Array();
var charts = new Object();
var _xyGraphDetailKey = _routeID;
var x_axis = 'time';
console.log("_xyGraphDetailKey: " + _xyGraphDetailKey);
console.log(""+JSON.stringify(userSpace,null,2));
var pointGroup;
if(pointGroup==null||pointGroup=="undefined")
	pointGroup = new Array();
var _dataIndex ;
if(_dataIndex==null||_dataIndex=="undefined")
	_dataIndex = new Object();
var _cdataIndex ;
if(_cdataIndex==null||_cdataIndex=="undefined")
	_cdataIndex = new Object();


/**
 * 将一个点添加到XY图中。
 */
function addPointToXYGraph(tagName){
	// 如果点组里不包含这个点
	if(pointGroup.indexOf(tagName)==-1){
		console.log("addPointToXYGraph:"+tagName);
		// 添加进点组
		pointGroup.push(tagName);
		
		// 修改cdata和_data时，修改tagName值为当前状态下的值。
		var tagName = getNewLabelForData(tagName);
		// 在刷新数据时，addData时，在_data中加一个
		// 向_data和cdata中加入一个数据，之后addData才能真正将数据加入。
		// 构建_data序号。
		_dataIndex = new Array();
		for(var indrow=0;indrow<_data.length;indrow++){
			_dataIndex[_data[indrow][0]]= indrow;
		}			
		_cdataIndex = new Array();
		for(var indrow=0;indrow<cdata.length;indrow++){
			_cdataIndex[cdata[indrow][0]]= indrow;
		}			
		
		// 处理cdata。_data处理方法参考这个。
		if(_cdataIndex['time']=="undefined"||_cdataIndex['time']==null){
			console.log("deal with time serial. ");
			var timeserial = new Array();
			timeserial.push('time');
			cdata.push(timeserial);
			_cdataIndex['time'] = cdata.length -1;
		}
		if(_cdataIndex[tagName]=="undefined"||_cdataIndex[tagName]==null){
			console.log("deal with "+tagName+".");
			console.log("deal with "+tagName+". "+JSON.stringify(_cdataIndex)+" "+JSON.stringify(cdata));
			var tagserial = new Array();
			tagserial.push(tagName);
			// TODO: 先用0补齐数据，后面要改成取历史数据。
			if(_cdataIndex['time']!=null && _cdataIndex['time']!="undefined" && cdata[_cdataIndex['time']]!=null && cdata[_cdataIndex['time']]!="undefined") {
				for(var i=0;i<cdata[_cdataIndex['time']].length-1;i++){
					tagserial.push(0);
				}
			}
			cdata.push(tagserial);
			//console.log(a);
		}
		
		if(_dataIndex['time']=="undefined"||_dataIndex['time']==null){
			console.log("_data deal with time serial. ");
			var timeserial = new Array();
			timeserial.push('time');
			_data.push(timeserial);
			_dataIndex['time'] = _data.length -1;
		}
		if(_dataIndex[tagName]=="undefined"||_dataIndex[tagName]==null){
			console.log("deal with "+tagName+".");
			console.log("deal with "+tagName+". "+JSON.stringify(_cdataIndex)+" "+JSON.stringify(cdata));
			var tagserial = new Array();
			tagserial.push(tagName);
			// TODO: 先用0补齐数据，后面要改成取历史数据。
			if(_dataIndex['time']!=null && _dataIndex['time']!="undefined" && _data[_dataIndex['time']]!=null && _data[_dataIndex['time']]!="undefined") {
				for(var i=0;i<_data[_dataIndex['time']].length-1;i++){
					tagserial.push(0);
				}
			}
			_data.push(tagserial);
			//console.log(a);
		}
				
		/**
		 * 历史数据怎么办？_data中保存，cdata中删除。
		 * 删除了一点，这个点的数据的cdata删除，_data中保存。
		 * 
		 * _data中保存最多点的数据，cdata中只保存当前看的数据。
		 * 
		 */
		
		updateXYGraphChart();
	}
}
/**
 * 将一个点从XY图中移出
 * @returns
 */
function removePointFromXYGraph(tagName){
	// 将tagName返成txtid
	var tagName = getOrigionLabel(tagName);
	console.log("1-- "+tagName);
	if(pointGroup.indexOf(tagName)>-1){
		pointGroup.splice(pointGroup.indexOf(tagName),1);

		// 修改cdata和_data时，修改tagName值为当前状态下的值。
		var tagName = getNewLabelForData(tagName);

		// TODO: cdata中删除点。
		_cdataIndex = new Array();
		for(var indrow=0;indrow<cdata.length;indrow++){
			_cdataIndex[cdata[indrow][0]]= indrow;
		}			
		cdata.splice(_cdataIndex[tagName],1);
		
		console.log("cd -> "+JSON.stringify(cdata));
		var _dt_ = new Array();
		for(var i=0;i<cdata.length;i++){
			_dt_.push(cdata[i][0]);
			_dt_.push(cdata[i][cdata[i].length-1]);
		}
//		c3LineChart.flow({columns:_dt_,to:new Date(),duration:1000});
//		c3LineChart.flow({columns:cdata,to:new Date(),duration:1000});
		if(pointGroup.length>0){
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : 'time',
				xFormat : '%Y',
				columns : cdata,
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
//					,
//					label: 'Y2 Axis Label'
				}
			}
		});
		}
	}
	updateXYGraphChart();
}

function closeGraph(){
	
}

function buildChart(){
	console.log("buildChart()");
	console.log("cdata => "+JSON.stringify(cdata));
	$('#exampleModal').modal('show');
	c3LineChart = c3.generate({
		bindto : '#ui-historyDataLineChart',
		data : {
			x : 'time',
			xFormat : '%Y',
			columns : cdata,
			type : 'spline'
//				,
//	        axes: {
//	        	CJY_XT31101_8: 'y',
//	        }
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
	currentPlayStatus = true;
	console.log("buildChart() end");

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
	//console.log("pointList -> "+JSON.stringify(pointList));
	var _time_ = new Array();
	_time_.push('time',0);
	cols.push(_time_);

	for (var indpl = 0; indpl < pointList.length; indpl++) {
		if(pointList[indpl] != null && pointList[indpl]!="undefined"){
			var _c_ = new Array();
			_c_.push(pointList[indpl].tagName,0);
			cols.push(_c_);
		} else {
			console.log(" get null tagName => "+JSON.stringify(pointList,null,2));
		}
	}
	cdata=cols;
	_data=cols;
//	c3LineChart = c3.generate({
//		bindto : '#ui-historyDataLineChart',
//		data : {
//			x : 'time',
//			xFormat : '%Y',
//			columns : cols,
//			type : 'spline',
//	        axes: {
//	        	CJY_XT31101_8: 'y',
//	        }
//		},
//		grid : {
//			x : {
//				show : true
//			},
//			y : {
//				show : true
//			}
//		},
//		color : {
//			pattern : [ 'rgba(88,216,163,1)', 'rgba(237,28,36,0.6)',
//					'rgba(4,189,254,0.6)' ]
//		},
//		padding : {
//			top : 10,
//			right : 20,
//			bottom : 30,
//			left : 50,
//		},
//		axis : {
//			x : {
//				type : 'timeseries',
//				// if true, treat x value as localtime (Default)
//				// if false, convert to UTC internally
//				localtime : true,
//				tick : {
//					format : '%Y-%m-%d %H:%M:%S'
//				}
//			},
//			y : {
//				show: true
////				,
////				label: 'Y2 Axis Label'
//			}
//		}
//	});

})(jQuery);

/******************************************************
 *      关于趋势图显示标签的相关设计
 * 1、数据结构
 * （1）. _data、cdata内，保存的是当前显示的标签值，如点位描述。
 * （2）. pointGroup内，保存的是原始显示标签，是从服务器返回的那个，图形中text的id。
 * 
 */

/**
 * 趋势图上，占位显示内容：
 * 0：tagName;
 * 1: 点位说明;
 * 2: 没有呢。
 * default: 不变
 */
var tagFeatureInXYGraph = 1;

/**
 * 
 */
var _graph = getGraphByID(userSpace.graph,_graphId);
var pointKs;
buildPointKs();
function buildPointKs(){
	if(pointKs==null || pointKs=="undefined"){
		pointKs = new Object();
	}
	console.log("--"+_graphId);
	var pointTextIDs = _graph.pointTextIDs;
	var pointList = _graph.pointList;
	for(var ind=0;ind<pointTextIDs.length;ind++){
		pointKs[pointTextIDs[ind]] = pointList[ind];
	}
//	console.log("pointKs = "+JSON.stringify(pointKs));
}

/**
 * 获取当前状态的下指定标签的原始标签txtid
 * @param currenttxtid
 * @returns
 */
function getOrigionLabel(currenttxtid){
	var tartxt = null;
	var points = _graph.pointList;
//	console.log("getOrigionLabel 1 - "+currenttxtid);
	// 将tagFeature下的txtid转成标准txtid
	for(var ind=0;ind<points.length;ind++){
		switch(tagFeatureInXYGraph){
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
//		console.log("getOrigionLabel 2 - "+tartxt);
		if(tartxt == currenttxtid){
			tartxt = _graph.pointTextIDs[ind];
//			console.log("getOrigionLabel 3 - "+tartxt);
			return tartxt;
		}
	}
	return null;

}
/**
 * 获取指定状态下指定标签所对应当前状态的标签值
 * @param txtid 标签值
 * @param tagFeature 状态
 * @returns 当前状态下的值
 */
function getNewLabelForData(txtid,tagFeature){
	var tartxt = null;
	if(tagFeature!=null&&tagFeature!="undefined"){
		
	}
	if(tartxt==null||tartxt=="undefined")
		tartxt = txtid;
	
	if(pointKs[tartxt]!=null&&pointKs[tartxt]!="undefined"){
		switch(tagFeatureInXYGraph){
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
 * @param data 服务器返的数据对象。其中数据的标签一定是txtid。
 * @returns
 */
function changeDataToNewLabel(data){
	console.log("data");
	var ret = new Object();
	Object.keys(data).forEach(function(key){
		var item = data[key]; 
		if(key!="time"){
			if(pointKs[key]!=null&&pointKs[key]!="undefined")
			switch(tagFeatureInXYGraph){
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
 * @param tarF
 * @returns
 */
function switchTagFeatureInXYGraph(tarF){
	if(tagFeatureInXYGraph != tarF ){
		// 修改前端显示
		var but = document.getElementById("labelSelectButtonUI");
		switch(tarF){
		case 0:
			but.innerHTML = "tagName";
			break;
		case 1:
			but.innerHTML = "点位说明";
			break;
		}


		// 修改_data、cdata的标签
		var _cdataLabelArray = new Array();
		for(var ind=0;ind<cdata.length;ind++){
			var txtid = getOrigionLabel(cdata[ind][0]);
			_cdataLabelArray.push(txtid);
		}
		
		var __dataLabelArray = new Array();
		for(var ind=0;ind<_data.length;ind++){
			var txtid = getOrigionLabel(_data[ind][0]);
			__dataLabelArray.push(txtid);
		}
		
		// 修改tagFeatureInXYGraph的值
		tagFeatureInXYGraph = tarF;
		
		for(var ind=0;ind<cdata.length;ind++){
			if(_data[ind][0]!="time"){
				var txtid = _cdataLabelArray[ind];
				cdata[ind][0] = getNewLabelForData(txtid);
				console.log("switch -> "+cdata[ind][0]);
			}else{}// 暂时不处理时间
		}
		console.log("cdata -> "+JSON.stringify(cdata));
		
		var __dataLabelArray = new Array();
		for(var ind=0;ind<_data.length;ind++){
			if(_data[ind][0]!="time"){
				var txtid = getOrigionLabel(_data[ind][0]);
				_data[ind][0] = getNewLabelForData(txtid);
			}else{}// 暂时不处理时间
		}

		// 修改下面方法里保存后的标签：
		//	_addPointToXY(getNewLabelForData(ele.getAttribute("id")));
		//	addPointToXYGraph(getNewLabelForData(ele.getAttribute("id")));
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : xray,
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

		play();
	}
}

updateXYGraphChart();
function updateXYGraphChart(ruserSpace) {
	if(ruserSpace==null||ruserSpace=="undefined")
		ruserSpace = userSpace;
	// var pointGroup = _graph;
	var uixyGraphPoints = document.getElementById("ui-xyGraphPoints");
	 console.log(" updateXYGraphChart => "+JSON.stringify(pointGroup));
	if (pointGroup == null || pointGroup == "undefined")
		return;
	var pointList = pointGroup;
	console.log("pointList = "+JSON.stringify(pointGroup));
	var innerHtml = "";
	var menuitem = document.getElementById("x_axisSelectButtonUIMenu");
	var _innerHtml = '<a class="dropdown-item" onclick="changex(\'time\')">时间</a>';
	for (var indpl = 0; indpl < pointList.length; indpl++) {
		var _itemHtml = '<a class="dropdown-item" onclick="changex(\''+getNewLabelForData(pointList[indpl])+'\')">'+getNewLabelForData(pointList[indpl])+'</a>';
		_innerHtml = _innerHtml + _itemHtml;
	}
	menuitem.innerHTML = _innerHtml;
}

/**
 * 刷新数据
 * @param data
 * @returns
 */
function refreshData(data) {
	// console.log("refreshData(data) -> "+JSON.stringify(data.body));
	var o = JSON.parse(data.body);
	Object.keys(o).forEach(function(key){
		if(key!="time"){
			if(o[key] == -999999){
				o[key] = "-";
			}else{
				var num = o[key];
				//var num = num+"";
				if(num!=null&&num!="undefined"){
					if(Math.round(num)<999){
						num=num.toFixed(3);
					}else{
						num=num.toFixed(1);
					}
					o[key] = num;
				// console.log("num = "+num+"");
				}
			}
		}
	});
	data.body = JSON.stringify(o);
//	console.log(" refreshData-> "+ JSON.stringify(data.body,null,2));
	// 添加进趋势图的换成点位说明。
	var _dat = changeDataToNewLabel(JSON.parse(data.body));
	// 添加趋势图数据到当前数据
	addData(_dat,cdata,cdataCount);
	// 添加趋势数据到全部数据
	addData(_dat,_data,10);
	console.log("cdata => "+JSON.stringify(cdata));

	var ids = document.getElementsByTagName("text"); 
	var _data_ = JSON.parse(data.body);
//	console.log(JSON.stringify(_data));
	Object.keys(_data_).forEach(function(key){
		if(key!="time"){
			var ele = document.getElementById(key);
			if(ele!=null && ele!="undefined"){
	//				console.log(" set value for "+key);
				// 加事件
				ele.onclick = function(){
	//					alert(ele.getAttribute("id"));
					// 添加进趋势图的换成点位说明。
					_addPointToXY(getNewLabelForData(ele.getAttribute("id")));
					// pointGroup中的值不变，还是txtid。
					addPointToXYGraph((ele.getAttribute("id")));
				}
				// 画点图
	//				if(gl[p].config.id == "point_" + key){
	//					gl[p].refresh(pointList_[key]);
	//				}
				// 	画图形
				ele.innerHTML =  Math.round(_data_[key]*10000)/10000;
				
			}else{
				console.log("no element named "+key);
			}
		}
	});
	
//	for(var key in pointList_){
//	for(var p in gl){
//		//console.log(gl[p].config.id + "  ==  "+"point_" + key );
//		if(gl[p].config.id == "point_" + key){
//			gl[p].refresh(pointList_[key]);
//		}
//	}
//}

	if(currentPlayStatus){
		// 刷新线图
//		_forward(data.body);
		play();
		// 刷新表格
		refreshDataTable(cdata);
	}
}




/**
 * 刷新表格数据
 * @param _cdata
 * @returns
 */
function refreshDataTable(_cdata){
	var _datatableUI = document.getElementById("_datatableUI");
	_datatableUI.innerHTML = "";
	var _thead = document.createElement("thead");
	var _tbody = document.createElement("tbody");
	for(var coli = 0;coli<_cdata.length;coli++){
		var _tr = document.createElement("tr");  
		for(var rowi = 0;rowi<_cdata[coli].length;rowi++){
			// 设为x轴
			var _td = document.createElement("td"); 
			
			var _value = _cdata[coli][rowi];
			switch(typeof _value){
			case 'number':
				_td.innerText = (Math.round(_value * 10000)) / 10000+"";		
				break;
			case 'string':
				_td.innerText += _cdata[coli][rowi];//_timeStr;
				break;
			default:
				var _t = new Date(_cdata[coli][rowi]);
				_td.innerText = _t.Format("hh:mm:ss");//_timeStr;					
				break;
			}
			_tr.append(_td);
		}

		if(_cdata[coli][0]=="time"){
			_thead.append(_tr);
			_datatableUI.prepend(_thead);
		}
		else
			_tbody.append(_tr);
	}
	_datatableUI.append(_tbody);

}

//********************* 开始 线图 *************************
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
 * 刷新值
 * 
 */


var currentStartTime;
var currentStartTimeInd;
function loadCData(startTime){

	var _currentStartTimeInd = 0;
	var _dataIndex ={};
	for(var indrow=0;indrow<_data.length;indrow++){
		_dataIndex[_data[indrow][0]]= indrow;
	}			
	
// console.log("debug 2");
	var dataInd = 0;
	var _datatime = _data[_dataIndex['time']];
	for(var inddata = 0;inddata<_datatime.length;inddata++){
		if(startTime <= _datatime[inddata]){
			dataInd = inddata;
			_currentStartTimeInd = dataInd;
			break;
		}
	}
// console.log("debug 3");
// console.log("debug "+JSON.stringify(_data));
	
	var _cdata = new Array();
// console.log("cdataCount="+cdataCount);
	for(var rowcount=0;rowcount<_data.length;rowcount++){
		var rowd = new Array();
		for(var icdata=0;icdata<cdataCount;icdata++){
			if(dataInd+icdata<_data[rowcount].length){
//				console.log("_data[rowcount][dataInd+icdata]="+_data[rowcount][dataInd+icdata]);
				if(_data[rowcount][dataInd+icdata]<1500000000000){
					if(_minY==0)_minY=Math.round(_data[rowcount][dataInd+icdata]);
					if(_maxY==0)_maxY=Math.round(_data[rowcount][dataInd+icdata]);
					if(_minY>_data[rowcount][dataInd+icdata])_minY=Math.round(_data[rowcount][dataInd+icdata]);
					if(_maxY<_data[rowcount][dataInd+icdata])_maxY=Math.round(_data[rowcount][dataInd+icdata]);
				} else {
//					console.log(" time: "+_data[rowcount][dataInd+icdata]+"  =  "+new Date(_data[rowcount][dataInd+icdata]));
				}
				
				if(_data[rowcount][dataInd+icdata]!=0)
					rowd.push(_data[rowcount][dataInd+icdata]);
//				console.log("minY_b="+_minY);
//				console.log("maxY_b="+_maxY);
				
			}
			else
				break;
		}
		// 从前面把头添加上
//		console.log("debug 4 => "+JSON.stringify(rowd));
		rowd.splice(0,0,_data[rowcount][0]);
//		console.log("debug 5 => "+JSON.stringify(rowd));
		_cdata.push(rowd);
	}
//	console.log("debug 6 => "+JSON.stringify(_cdata));

	cdata = _cdata;
//	console.log(" *****   cdata="+JSON.stringify(cdata));

// console.log("cdata= "+JSON.stringify(cdata));
// currentStartTime = _data[_dataIndex['time']][1];
// currentStartTimeInd = 1;

	currentStartTime = startTime;
	currentStartTimeInd = _currentStartTimeInd;
}

var _dataIndex;
var __a = 0;
function addHistoryData(historyData){
	// 数据不正常就返回
	if(historyData==null|| historyData=="undefined")return;
	
	if(_data ==null || _data=="undefined"){
		console.log("addHistoryData 1");
		_data = historyData;
		for(var indrow=0;indrow<_data.length;indrow++){
			_dataIndex[_data[indrow][0]]= indrow;
		}				
		currentStartTime = _data[_dataIndex['time']][1];
		currentStartTimeInd = 1;
	}
	else {
//		console.log("addHistoryData 2 _data="+JSON.stringify(_data));
		var historyIndex = {};
		for(var indrow=0;indrow<historyData.length;indrow++){
			historyIndex[historyData[indrow][0]]= indrow;
		}
		var _dataIndex = {};
		for(var indrow=0;indrow<_data.length;indrow++){
			_dataIndex[_data[indrow][0]]= indrow;
			if(_data[indrow].length==2){
				_data[indrow].pop();
			}
		}			
		console.log("addHistoryData 3 _data="+JSON.stringify(_data));

		var index_data = _data[0].length-1;
		for(var indHis=historyData[0].length-1;indHis>1;indHis--){
//			console.log(" addHistoryData ==> "+_data[_dataIndex["time"]][index_data]+"  _IsNaN(_data[_dataIndex[time]][index_data])="+_IsNaN(_data[_dataIndex["time"]][index_data]));
//			console.log(" indHis = "+indHis + "  =  "+_data[_dataIndex["time"]][index_data]+"  = "+historyData[historyIndex["time"]][indHis]);
			while(index_data>0 && _data[_dataIndex["time"]][index_data]>new Date(historyData[historyIndex["time"]][indHis])){
				index_data --;
			}
			console.log(" index_data = "+index_data);
			if(_data[_dataIndex["time"]][index_data]<historyData[historyIndex["time"]][indHis] || !_IsNaN(_data[_dataIndex["time"]][index_data])){
				Object.keys(historyIndex).forEach(function(key){
					if(key=="time"){
						_data[_dataIndex[key]].splice(index_data+1,0,new Date(historyData[historyIndex[key]][indHis]*1000));
						console.log(key+"  -  historyData[historyIndex[key]][indHis]="+historyData[historyIndex[key]][indHis]);
					}else{
						_data[_dataIndex[key]].splice(index_data+1,0,historyData[historyIndex[key]][indHis]);
					}
//					console.log(key+"  -  historyData[historyIndex[key]][indHis]="+historyData[historyIndex[key]][indHis]);
				});
				if(currentStartTimeInd>index_data) currentStartTimeInd++;
				console.log("currentStartTimeInd="+currentStartTimeInd+"  <=>  index_data="+index_data);
			}
		}
//		console.log("addHistoryData 4 _data="+JSON.stringify(_data));
//		console.log("addHistoryData 4 _data.length = "+_data[0].length);
	}
	__a++;

}
function _IsNaN(value) {
    return typeof value === 'number' && !isNaN(value);
  }

/**
 * 添加数据到目标中，并且设置最大数据量上限。
 * @param newData 待加入的新数据
 * @param _data_ 存储数据的库
 * @param cdatacount 最大上限
 * @returns
 */
function addData(newData, _data_, cdatacount) {
	if (typeof newData == "string") {
		newData = JSON.parse(newData);
	}
	//console.log("addData");
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

/**
 * 去服务器取数据，取回数据后，执行func方法
 * 
 * @param func
 * @returns
 */
function getHistoryData1(_historyDataDetailKey,startTime,endTime,pointGroup,func){
	var data={'uid':uid,'token':token,'id':_historyDataDetailKey,'beginTime':startTime,'endTime':endTime,"pointList":pointGroup};
	 console.log("getHistoryData1  data = "+JSON.stringify(data));
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
			if (data.status == "000"){ // GlobalConsts.ResultCode_SUCCESS) {
				var historyData = data.data.data;
				console.log("historydata.js => historyData1= "+JSON.stringify(historyData));
//				if(__a<3)
				addHistoryData(historyData);
//				console.log("getHistoryData1 startTime="+startTime+" - "+new Date(startTime));
//				console.log("getHistoryData1 historyData[0].length= "+historyData[0].length);

//				func();
			} else {
				alert("失败 ： "+data.msg);
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
function getHistoryData(){
	// console.log("otherrule = "+JSON.stringify(rule));
		var data={'uid':uid,'token':token,'id':_historyDataDetailKey,'beginTime':'','endTime':''};
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
				if (data.status == "000"){ // GlobalConsts.ResultCode_SUCCESS) {
					// console.log("server info : "+JSON.stringify(data.data.data));
	// console.log("historydata.js => submitNewDataItem 3");
					var historyData = data.data.data;
					console.log("historydata.js => historyData= "+JSON.stringify(historyData));
					addHistoryData(historyData);
					loadCData(1569151501);
					reloadDataToDiagram();
					// 
				} else {
					alert("失败 ： "+data.msg);
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
 * 当前播放状态。
 * true：播放状态。
 * false：停止状态。
 */
var currentPlayStatus = false;

function play(){
	currentPlayStatus = true;

	if(currentPlayStatus){
		c3LineChart.load({
			columns : cdata
		});
	}
	console.log("play -> "+JSON.stringify(cdata));
}
/**
 * 向前，向后
 */
function _forward(_newData) {
	
	currentPlayStatus = false;
	
	// 如果是播放状态，就播放
	if(currentPlayStatus){
		c3LineChart.load({
			columns : cdata
		});
		c3LineChart.axis.min({
			y : _minY - (_maxY-_minY)/2 * _rateY
		});
		c3LineChart.axis.max({
			y : _maxY + (_maxY-_minY)/2 * _rateY 
		});
	}else{
		// 否则加载历史数据
		console.log(" _forward 0");
		// 更新cdata数据
		var _dataIndex ={};
		for(var indrow=0;indrow<_data.length;indrow++){
			_dataIndex[_data[indrow][0]]= indrow;
		}						
		console.log(" _forward 1");
		oneStep = cdataCount*0.9;
		var startTime;
		if(currentStartTimeInd == null || currentStartTimeInd=="undefined"){
			currentStartTimeInd =1;
		}else{
			console.log(" _forward 2");

			if(currentStartTimeInd+oneStep<_data[_dataIndex['time']].length){ // 向左
				currentStartTimeInd = currentStartTimeInd+oneStep;
			}
			else{
				// TODO： 如果向右一步已经超过了_data的左边界，就去服务器取数据，添加后，再取
				currentStartTimeInd = _data[_dataIndex['time']][_data[_dataIndex['time']].length-1].getTime();
				var _endTime = currentStartTimeInd+oneStep*1000;
				if(_endTime>new Date().getTime())
					_endTime = new Date().getTime();
				getHistoryData1("",currentStartTimeInd,_endTime,pointGroup,_forward);
				return;
			}
		}
		var currentTime = _data[_dataIndex['time']][currentStartTimeInd];
		console.log("currentTime="+currentTime);
		loadCData(currentTime);
		reloadDataToDiagram();
	}
}


/**
 * 向前，向后
 */
var oneStep = 0; // 点一下向前后，移动的点位数
function getCurrentStartTime(){
	for(var ind=0;ind<cdata.length;ind++){
		if(cdata[ind][0]=='time')
			return cdata[ind][1];
	}

}
/**
 * 向前（左）移动一次
 * 
 * @returns
 */
function _backward() {
	// 停止播放
	currentPlayStatus = false;
	
	// 更新cdata数据
	console.log("hisotryData debug 0");
	var _dataIndex ={};
	for(var indrow=0;indrow<_data.length;indrow++){
		_dataIndex[_data[indrow][0]]= indrow;
	}						
	oneStep = cdataCount*0.9;
	console.log("currentStartTimeInd="+currentStartTimeInd+" ?  oneStep="+oneStep);
	console.log("hisotryData debug 1");
	if(currentStartTimeInd == null || currentStartTimeInd=="undefined"){
		console.log("hisotryData debug 2");
		currentStartTimeInd =2;
	}else{
		console.log("hisotryData debug 3");
		if(currentStartTimeInd-oneStep>0){ // 向左
			currentStartTimeInd = currentStartTimeInd-oneStep;
			console.log("hisotryData debug 4");
		}else{
			console.log("hisotryData debug 5");
			// TODO： 如果向左一步已经超过了_data的左边界，就去服务器取数据，添加后，再取
// currentStartTimeInd
			console.log("_data[_dataIndex['time']][1]="+JSON.stringify(_data));
			console.log("_data[_dataIndex['time']][1]="+JSON.stringify(_dataIndex));
			console.log("_data[_dataIndex['time']][1]="+JSON.stringify(_data[_dataIndex['time']]));
			console.log("_data[_dataIndex['time']][1]="+JSON.stringify(_data[_dataIndex['time']][1]));
//			var _currentStartTimeInd = _data[_dataIndex['time']][1].getTime() - oneStep*1000;
			var _currentStartTimeInd ;
			if(_data[_dataIndex['time']][1]!="undefined"){
				_currentStartTimeInd = _data[_dataIndex['time']][1].getTime() - 60*60*2*1000;
			} else {
				_currentStartTimeInd = new Date().getTime() - 60*60*2*1000;				
			}
			getHistoryData1("",_currentStartTimeInd,_data[_dataIndex['time']][1].getTime(),pointGroup,_backward);

			var _currentStartTimeInd ;// 当前要查的时间的序号，不是具体时间。因为要数据数量。
			var _endTime;
			if(_data[_dataIndex['time']].length>1 && _data[_dataIndex['time']][1] != "undefined"){
				_currentStartTimeInd = _data[_dataIndex['time']][1].getTime() - 60*60*2*1000;
				_endTime = _data[_dataIndex['time']][1].getTime();
			} else {
				_currentStartTimeInd = new Date().getTime() - 60*60*2*1000;
				_endTime = new Date().getTime();
			}
			getHistoryData1("",_currentStartTimeInd,_endTime,pointGroup,_backward);
			return;
		}
	}
	console.log("hisotryData debug 6");

	var currentTime = _data[_dataIndex['time']][currentStartTimeInd];
	console.log("currentTime="+currentTime);
	loadCData(currentTime);
	reloadDataToDiagram();

}

function _search(){
// var _startTime_;
// var _endTime_;
//	c3LineChart.flow(cdata,new Date()+"",1000);
//console.log("flow");
	console.log("_startTime_="+_startTime_+"  _endTime_="+_endTime_);
	//getHistoryData1(_historyDataDetailKey,_startTime_,_endTime_,addHistoryData);
	getHistoryData1("",_startTime_,_endTime_,pointGroup,_backward);
}

/**
 * x轴放大缩小
 */
//function zoomin_x() {
//	_rateX = _rateX /2;
//	cdataCount = cdataCount * 1.5;
//	console.log("cdataCount = "+cdataCount);
//}
//
//function zoomout_x() {
//	_rateX = _rateX *2;
//	cdataCount = cdataCount / 1.5;
//	console.log("cdataCount = "+cdataCount);
//}
function zoomin_x() {
	_rateX = _rateX /2;
	cdataCount = cdataCount * 1.5;
	loadCData(getCurrentStartTime());
	reloadDataToDiagram();
	console.log("cdataCount = "+cdataCount);
}

function zoomout_x() {
	_rateX = _rateX *2;
	cdataCount = cdataCount / 1.5;
	console.log("cdataCount = "+cdataCount);
	loadCData(getCurrentStartTime());
	reloadDataToDiagram();
}

/**
 * 重新加载cdata到图形
 * @returns
 */
function reloadDataToDiagram(){
	// UI加载数据，显示
	// TODO: 增加flow效果
	c3LineChart.load({
		columns : cdata
	});
	c3LineChart.axis.min({
		y : Math.round(_minY - (_maxY-_minY)/2 * _rateY)
	});
	c3LineChart.axis.max({
		y : Math.round(_maxY + (_maxY-_minY)/2 * _rateY) 
	});	
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
var xray = "time";
function changex(tname) {
	console.log("click: ========================== "+tname);
	var but = document.getElementById("x_axisSelectButtonUI");
	but.innerHTML = tname;
	xray = tname;
	console.log(tname);
//	but.text = tname;
	if(tname=="time"){
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : xray,
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
		console.log("");
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : xray,
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







function setConnected(connected) {
}

loginWebsocket();
function loginWebsocket() {
	console.log(" debug 1");

	if(!connected) {
		console.log("新建连接");
		connect(graphSubscribe);
		return;
	}
	else {
		console.log("当前存在");
		graphSubscribe();
	}
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

