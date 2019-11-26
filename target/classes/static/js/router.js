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
var currentStepInRouteList;
var _realtimeDataDetailKey;

console.log("2222222 = " + currentStepInRouteList);
function routeTo(diagram, key, graphId) {
	console.log("11111 = " + currentStepInRouteList);

	// 初始化步数记录
	if (routeList == null || routeList == "undefined") {
		if (localStorage.routeList == null
				|| localStorage.routeList == "undefined")
			routeList = new Array();
		else
			routeList = JSON.parse(localStorage.routeList);
	}
	if(currentStepInRouteList!=currentStepInRouteList){
		console.log("重新初始化频数");
		currentStepInRouteList = -1;
	}
	
	if (currentStepInRouteList == null || currentStepInRouteList == "undefined"
			|| currentStepInRouteList == 'NaN') {
		if (localStorage.currentStepInRouteList == null
				|| localStorage.currentStepInRouteList == "undefined") {
			console.log("if");
			currentStepInRouteList = -1;
		} else {
			console.log("else");
			currentStepInRouteList = parseInt(localStorage.currentStepInRouteList);
		}
	}
	console.log("currentStepInRouteList=" + currentStepInRouteList + "  routeList.length="
			+ routeList.length);
	// console.log("currentStepInRouteList= "+currentStepInRouteList);
	// 如果当前参数diagram为空，就是点了前进后退
	if (diagram == null || diagram == "undefined") {
		console.log("点了前进后退");
		// 如果当前步骤数在记录范围，就跳转
		if (currentStepInRouteList < routeList.length && currentStepInRouteList >= 0) {
			// 传递参数
			_routeType = routeList[currentStepInRouteList]._routeType;
			_routeID = routeList[currentStepInRouteList]._routeID;
			_graphId = routeList[currentStepInRouteList]._graphId;
			diagram = _routeType.toLowerCase();
			key = _routeID;
			graphId = _graphId;

		} else {
			alert("已经到头了。");
			// TODO: 应该设置页面显示不能点击。现在是因为没有这个图标，就先提示。
			if (currentStepInRouteList < 0)
				currentStepInRouteList = 0;
			if (currentStepInRouteList > routeList.length - 1)
				currentStepInRouteList = routeList.length - 1;
			localStorage.currentStepInRouteList = currentStepInRouteList+"";

			return;
		}
	} else {
		// 如果当前是最后一步，就是正常点进来的
		if (currentStepInRouteList == routeList.length - 1) {

		} else { // 否则就是上一步下一步后，点了跳转功能
			// 删除当前步后面的记录
			console.log("前进后退后，中间点了正常跳转功能。前：  currentStepInRouteList=" + currentStepInRouteList
					+ "  routeList.length=" + routeList.length);
			routeList.splice(currentStepInRouteList + 1, routeList.length - currentStepInRouteList
					- 1);
			console.log("前进后退后，中间点了正常跳转功能。后：  currentStepInRouteList=" + currentStepInRouteList
					+ "  routeList.length=" + routeList.length);
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
		if (routeList.length > historyStepCount)
			routeList.shift();

		localStorage.routeList = JSON.stringify(routeList);

		currentStepInRouteList++;
		localStorage.currentStepInRouteList = currentStepInRouteList+"";

		console.log("执行正常跳转后，currentStepInRouteList=" + currentStepInRouteList
				+ "  routeList.length=" + routeList.length);
	}

	if (unsubscribe != null && unsubscribe != "undefined")
		unsubscribe();

	var mainInfoNr = document.getElementById("mainInfoNr");
//	showLoading();
	switch (diagram.toLowerCase()) {
	case "dashboard".toLowerCase():
		$("#mainPanel").load("_dashboard.html",null,hideLoading);
		break;
	
	case "diagramList".toLowerCase():
		if (key == '未分类')
			key = "unclassify";
		_galleryKey = key;
		_routeID = key;
		_graphId = graphId;
		// console.log("case diagramList " + key + " " + graphId);
		$("#mainPanel").load("_gallery.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		break;
	case "diagramDetail".toLowerCase():
		console.log("key= "+key);
		_diagramShowKey = key;
		_graphId = graphId;
		$("#mainPanel").load("_diagramShow.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		break;
	case "xydiagramList".toLowerCase():
		_xydiagramListKey = key;
		$("#mainPanel").load("_xyGraphList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		break;
	case "xyGraphDetail".toLowerCase():
		_xydiagramListKey = key;
		$("#mainPanel").load("_xyGraphChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		break;
	case "realtimedataList".toLowerCase():
		_realtimedataListKey = key;
		$("#mainPanel").load("_realtimedataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		break;
	case "realtimedataDetail".toLowerCase():
		_realtimeDataDetailKey = key;
		$("#mainPanel").load("_realtimeDataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		break;
	case "alertdataList".toLowerCase():
		$("#mainPanel").load("_alertdataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		break;
	case "alertdataDetail".toLowerCase():
		$("#mainPanel").load("_alertdataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		break;
	case "historydataList".toLowerCase():
		$("#mainPanel").load("_historDataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		break;
	case "historydatadetail".toLowerCase():
		$("#mainPanel").load("_historyDataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		break;
	case "lineAlertdataList".toLowerCase():
		_linealertdataListKey = key;
		$("#mainPanel").load("_lineAlertDataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		break;
	case "linalertdataDetail".toLowerCase():
		_linealertdataListKey = key;
		$("#mainPanel").load("_lineAlertDataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		break;

	//     
	case "userManage".toLowerCase():
		currentUser = key;
		$("#mainPanel").load("_userManage.html",null,hideLoading);
		break;
	}

}

function stepBackward() {
	if (localStorage.currentStepInRouteList == null
			|| localStorage.currentStepInRouteList == "undefined") {
		console.log("1111");
		currentStepInRouteList = -1;
	} else {
		console.log("2222 = "+localStorage.currentStepInRouteList);
		currentStepInRouteList = parseInt(localStorage.currentStepInRouteList);
	}
	currentStepInRouteList--;
	routeTo();
}
function stepForward() {
	if (localStorage.currentStepInRouteList == null
			|| localStorage.currentStepInRouteList == "undefined") {
		currentStepInRouteList = -1;
	} else {
		currentStepInRouteList = parseInt(localStorage.currentStepInRouteList);
	}
	currentStepInRouteList++;
	routeTo();
}
