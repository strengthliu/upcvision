/**
 * 
 */

// var userSpace = null;
// var user = null;
// var token = null;
var _routeType;
var _routeID;
var _graphId;
var routeList;
var historyStepCount = 50;
var currentStep;
var _realtimeDataDetailKey;

function routeTo(diagram, key, graphId) {
	// 初始化步数记录
	if(routeList == null || routeList =="undefined")
		routeList = new Array();
	if(currentStep == null || currentStep == "undefined")
		currentStep = -1;
	console.log("currentStep="+currentStep+"  routeList.length="+routeList.length);
//	console.log("currentStep= "+currentStep);
	// 如果当前参数diagram为空，就是点了前进后退
	if(diagram==null||diagram=="undefined"){
		console.log("点了前进后退");
		// 如果当前步骤数在记录范围，就跳转
		if(currentStep<routeList.length && currentStep>=0){
			// 传递参数
			_routeType = routeList[currentStep]._routeType;
			_routeID = routeList[currentStep]._routeID;
			_graphId = routeList[currentStep]._graphId;
			diagram = _routeType.toLowerCase();
			key = _routeID;
			graphId = _graphId;

		}else{
			alert("已经到头了。");
			// TODO: 应该设置页面显示不能点击。现在是因为没有这个图标，就先提示。
			if(currentStep<0)
				currentStep = 0;
			if(currentStep>routeList.length-1)currentStep=routeList.length-1;
			return;
		}
	} else {
		// 如果当前是最后一步，就是正常点进来的
		if(currentStep==routeList.length-1){
			
		} else { // 否则就是上一步下一步后，点了跳转功能
			// 删除当前步后面的记录
			console.log("前进后退后，中间点了正常跳转功能。前：  currentStep="+currentStep+"  routeList.length="+routeList.length);
			routeList.splice(currentStep+1,routeList.length-currentStep-1);
			console.log("前进后退后，中间点了正常跳转功能。后：  currentStep="+currentStep+"  routeList.length="+routeList.length);
		}
		// 传递参数
		_routeType = diagram.toLowerCase();
		_routeID = key;
		_graphId = graphId;

		var _step = new Object();
		_step["_routeType"] = _routeType;
		_step["_routeID"] = _routeID;
		_step["_graphId"] = _graphId;

		routeList.push(_step);
		if(routeList.length>historyStepCount)
			routeList.shift();
		currentStep ++;
		console.log("执行正常跳转后，currentStep="+currentStep+"  routeList.length="+routeList.length);
	}
	
	if (unsubscribe != null && unsubscribe != "undefined")
		unsubscribe();
	
	
	switch (diagram.toLowerCase()) {
	case "diagramList".toLowerCase():
		if (key == '未分类')
			key = "unclassify";
		_galleryKey = key;
		_routeID = key;
		_graphId = graphId;
		console.log("case diagramList " + key + " " + graphId);
		$("#mainPanel").load("_gallery.html");
		break;
	case "diagramDetail".toLowerCase():
		_diagramShowKey = key;
		_graphId = graphId;
		$("#mainPanel").load("_diagramShow.html");
		break;
	case "xydiagramList".toLowerCase():
		_xydiagramListKey = key;
		$("#mainPanel").load("_xyGraphList.html");
		break;
	case "xyGraphDetail".toLowerCase():
		_xydiagramListKey = key;
		$("#mainPanel").load("_xyGraphChart.html");
		break;
	case "realtimedataList".toLowerCase():
		_realtimedataListKey = key;
		$("#mainPanel").load("_realtimedataList.html");
		break;
	case "realtimedataDetail".toLowerCase():
		_realtimeDataDetailKey = key;
		$("#mainPanel").load("_realtimeDataChart.html");
		break;
	case "alertdataList".toLowerCase():

		$("#mainPanel").load("_alertdataList.html");
		break;
	case "alertdataDetail".toLowerCase():

		$("#mainPanel").load("_alertdataChart.html");
		break;
	case "historydataList".toLowerCase():
		$("#mainPanel").load("_historDataList.html");
		break;
	case "historydatadetail".toLowerCase():
		$("#mainPanel").load("_historyDataChart.html");
		break;
	case "lineAlertdataList".toLowerCase():
		_linealertdataListKey = key;
		$("#mainPanel").load("_lineAlertDataList.html");
		break;
	case "linalertdataDetail".toLowerCase():
		_linealertdataListKey = key;
		$("#mainPanel").load("_lineAlertDataChart.html");
		break;

	//     
	case "userManage".toLowerCase():
		currentUser = key;
		$("#mainPanel").load("_userManage.html");
		break;
	}

}


function stepBackward(){
	currentStep --;
	routeTo();
}
function stepForward(){
	currentStep ++;
	routeTo();	
}
