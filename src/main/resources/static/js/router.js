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

//// console.log(" router... 2222222 = " + currentStepInRouteList);
function routeTo(diagram, key, graphId) {
	//// console.log(" router... 11111 = " + currentStepInRouteList);

	// 初始化步数记录
	if (routeList == null || routeList == "undefined") {
		if (localStorage.routeList == null
				|| localStorage.routeList == "null"
				|| localStorage.routeList == "undefined")
			routeList = new Array();
		else{
			// console.log(" router... localStorage.routeList="+JSON.stringify(localStorage.routeList));
			routeList = JSON.parse(localStorage.routeList);
		}
	}
	if(currentStepInRouteList!=currentStepInRouteList){
		// console.log(" router... 重新初始化频数");
		currentStepInRouteList = -1;
	}
	
	if (currentStepInRouteList == null || currentStepInRouteList == "undefined"
			|| currentStepInRouteList == 'NaN') {
		if (localStorage.currentStepInRouteList == null
				|| localStorage.currentStepInRouteList == "null"
				|| localStorage.currentStepInRouteList == "undefined") {
			//// console.log(" router... if");
			currentStepInRouteList = -1;
		} else {
			// console.log(" router... 重新从缓存里取currentStepInRouteList");
			currentStepInRouteList = parseInt(localStorage.currentStepInRouteList);
		}
	}
	
	// console.log(" router... currentStepInRouteList=" + currentStepInRouteList + "  routeList.length="+ routeList.length);
	// // console.log(" router... currentStepInRouteList= "+currentStepInRouteList);
	// 如果当前参数diagram为空，就是点了前进后退
	if (diagram == null || diagram == "undefined") {
		// console.log(" router... 点了前进后退");
		// 如果当前步骤数在记录范围，就跳转
//		// console.log(" router... ");
		if (currentStepInRouteList < routeList.length && currentStepInRouteList >= 0) {
			// 传递参数
			_routeType = routeList[currentStepInRouteList]._routeType;
			_routeID = routeList[currentStepInRouteList]._routeID;
			_graphId = routeList[currentStepInRouteList]._graphId;
			diagram = _routeType.toLowerCase();
			key = _routeID;
			graphId = _graphId;

			// console.log(" router... 执行前进后退后, _routeID="+_routeID+" diagram="+diagram);
			// console.log(" router... 执行前进后退后，currentStepInRouteList=" + currentStepInRouteList
			//		+ "  routeList.length=" + routeList.length);
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
			// console.log(" router... 前进后退后，中间点了正常跳转功能。前：  currentStepInRouteList=" + currentStepInRouteList
			//		+ "  routeList.length=" + routeList.length);
//			currentStepInRouteList = routeList.length - 1;
			routeList.splice(currentStepInRouteList + 1, routeList.length - currentStepInRouteList
					- 1);
			// console.log(" router... 前进后退后，中间点了正常跳转功能。后：  currentStepInRouteList=" + currentStepInRouteList
			//		+ "  routeList.length=" + routeList.length);
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

		console.log("router.js -> routeList => "+JSON.stringify(routeList));
		localStorage.routeList = JSON.stringify(routeList);

		currentStepInRouteList++;
		if(currentStepInRouteList > routeList.length - 1)
			currentStepInRouteList = routeList.length - 1;
		
		localStorage.currentStepInRouteList = currentStepInRouteList+"";

		// console.log(" router... 执行正常跳转后，currentStepInRouteList=" + currentStepInRouteList
		//		+ "  routeList.length=" + routeList.length);
	}

	if (unsubscribe != null && unsubscribe != "undefined")
		unsubscribe();

	var mainInfoNr = document.getElementById("mainInfoNr");
	var serachbox_topToolsBar = document.getElementById("serachbox_topToolsBar");
	// console.log(" router... switch前 , _routeID="+_routeID+" diagram="+diagram);
	// console.log(" router... switch前 ，currentStepInRouteList=" + currentStepInRouteList
	//		+ "  routeList.length=" + routeList.length);
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
		// // console.log(" router... case diagramList " + key + " " + graphId);
		$("#mainPanel").load("_gallery.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		serachbox_topToolsBar.style.display = "block";
		break;
	case "diagramDetail".toLowerCase():
		// console.log(" router... key= "+key);
		_diagramShowKey = key;
		_graphId = graphId;
		// console.log(" router... =====调试图形svg未加载问题 ===== router.js -> diagramDetail");
		// console.log(" router... =====调试图形svg未加载问题 ===== router.js -> diagramDetail");
		$("#mainPanel").load("_diagramShow.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;
	case "xydiagramList".toLowerCase():
		_xydiagramListKey = key;
		$("#mainPanel").load("_xyGraphList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		serachbox_topToolsBar.style.display = "block";
		break;
	case "xyGraphDetail".toLowerCase():
		_xydiagramListKey = key;
		$("#mainPanel").load("_xyGraphChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;
	case "realtimedataList".toLowerCase():
		_realtimedataListKey = key;
		// console.log(" router... load _realtimeDataList.html");
		$("#mainPanel").load("_realtimeDataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		serachbox_topToolsBar.style.display = "block";
		break;
	case "realtimedataDetail".toLowerCase():
		_realtimeDataDetailKey = key;
		$("#mainPanel").load("_realtimeDataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;
	case "alertdataList".toLowerCase():
		$("#mainPanel").load("_alertDataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		serachbox_topToolsBar.style.display = "block";
		break;
	case "alertdataDetail".toLowerCase():
		$("#mainPanel").load("_alertDataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;
	case "historydataList".toLowerCase():
		$("#mainPanel").load("_historDataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		serachbox_topToolsBar.style.display = "block";
		break;
	case "historydatadetail".toLowerCase():
		$("#mainPanel").load("_historyDataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;
	case "lineAlertdataList".toLowerCase():
		_linealertdataListKey = key;
		$("#mainPanel").load("_lineAlertDataList.html",null,hideLoading);
		mainInfoNr.style.visibility = 'visible';
		serachbox_topToolsBar.style.display = "block";
		break;
	case "linalertdataDetail".toLowerCase():
		_linealertdataListKey = key;
		$("#mainPanel").load("_lineAlertDataChart.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;

	//     
	case "departManage".toLowerCase():
		currentUser = key;
		$("#mainPanel").load("_departManage.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;
	case "userManage".toLowerCase():
		currentUser = key;
		$("#mainPanel").load("_userManage.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		break;
	}

}

function stepBackward() {
	if (currentStepInRouteList == null || currentStepInRouteList == "undefined"
		|| currentStepInRouteList == 'NaN') {
		if (localStorage.currentStepInRouteList == null
				|| localStorage.currentStepInRouteList == "undefined") {
			// console.log(" router... 1111");
			currentStepInRouteList = -1;
		} else {
			// console.log(" router... 2222 = "+localStorage.currentStepInRouteList);
			currentStepInRouteList = parseInt(localStorage.currentStepInRouteList);
		}
	}
	currentStepInRouteList--;
	// console.log(" router... stepBackward->  currentStepInRouteList=" + currentStepInRouteList + "  routeList.length="+ routeList.length);
	routeTo();
}
function stepForward() {
	if (currentStepInRouteList == null || currentStepInRouteList == "undefined"
		|| currentStepInRouteList == 'NaN') {
		if (localStorage.currentStepInRouteList == null
				|| localStorage.currentStepInRouteList == "undefined") {
			currentStepInRouteList = -1;
		} else {
			currentStepInRouteList = parseInt(localStorage.currentStepInRouteList);
		}
	}
	currentStepInRouteList++;
	routeTo();
}
