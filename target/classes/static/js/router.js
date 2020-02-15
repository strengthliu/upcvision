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
	 console.log(" router... diagram= "+diagram+" ; key= " +key+" ;  graphId= "+graphId);

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

	// 快捷苹果按钮
	var mainInfoNr = document.getElementById("mainInfoNr");
	// 查询框
	var serachbox_topToolsBar = document.getElementById("serachbox_topToolsBar");
	// console.log(" router... switch前 , _routeID="+_routeID+" diagram="+diagram);
	// console.log(" router... switch前 ，currentStepInRouteList=" + currentStepInRouteList
	//		+ "  routeList.length=" + routeList.length);
	
	// 分级导航
	var items = new Array();
	var item1 = [
		{
			'name':'流程图',
			'diagram':"diagramlist",
			'_galleryKey' : null,
			'_routeID' : null,
			'_graphId' : null
		},
		{
			'name':'XY图',
			'diagram':"xydiagramlist",
			'_galleryKey' : null,
			'_routeID' : null,
			'_graphId' : null
		},
		{
			'name':'实时数据查询',
			'diagram':"realtimedatalist",
			'_galleryKey' : null,
			'_routeID' : null,
			'_graphId' : null
		},
		{
			'name':'报警查询',
			'diagram':"alertdatalist",
			'_galleryKey' : null,
			'_routeID' : null,
			'_graphId' : null
		},
		{
			'name':'历史数据',
			'diagram':"historydatalist",
			'_galleryKey' : null,
			'_routeID' : null,
			'_graphId' : null
		},
		{
			'name':'用户管理',
			'diagram':"usermanage",
			'_galleryKey' : null,
			'_routeID' : null,
			'_graphId' : null
		}
		];
	items.push(item1);

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
		if(graphId!=null&&graphId!="undefined"){
			// TODO: 根据ID取出图形目录树
			console.log("aaa => ");
			var _g = getGraphByID(userSpace.graph,_graphId);
			console.log("aaa => "+JSON.stringify(_g));
		}
//		var 
		var itemcur = ['流程图','全部'];
		items.push(itemcur);
		setBreadcrumb(items);
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
		var itemcur = ['XY图','全部'];
		items.push(itemcur);
		setBreadcrumb(items);
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
		var itemcur = ['用户管理','部门管理'];
		items.push(itemcur);
		setBreadcrumb(items);
		break;
	case "userManage".toLowerCase():
		currentUser = key;
		$("#mainPanel").load("_userManage.html",null,hideLoading);
		mainInfoNr.style.visibility = 'hidden';
		serachbox_topToolsBar.style.display = "none";
		var itemcur = ['用户管理','用户管理'];
		items.push(itemcur);
		setBreadcrumb(items);
		break;
	}
	
}

function setBreadcrumb(items){
	// 分级导航 <ol class="breadcrumb bg-inverse-primary" id="mainTools_Breadcrumb">
	var mainTools_Breadcrumb = document.getElementById("mainTools_Breadcrumb");
	mainTools_Breadcrumb.innerHTML = "";
	// 从items里取内容：最后一个元素，是所有显示值；第一个元素是第一级显示
	// TODO: 取出最后一个元素
	var tags = items.pop();
	console.log("aaa=> items= "+JSON.stringify(items));

	for(var inditem=0;inditem<items.length;inditem++){
		var itemD = items[inditem];
		// <li class="breadcrumb-item">
		var item = document.createElement('li');
		item.setAttribute('class', 'breadcrumb-item');
		var	itemdiv = document.createElement('div');
		itemdiv.setAttribute('class', 'btn-group');
		
		
		// <button type="button" class="btn btn-primary">Dropdown</button>
		var button1 = document.createElement('button');
		button1.setAttribute('type', 'button');
		button1.setAttribute('class', 'btn btn-primary');
		button1.innerHTML = tags[inditem];
		itemdiv.appendChild(button1);

		// <button type="button"
		// class="btn btn-primary dropdown-toggle dropdown-toggle-split"
		//	id="dropdownMenuSplitButton1" data-toggle="dropdown"
		//	aria-haspopup="true" aria-expanded="false">
		//	<span class="sr-only">Toggle Dropdown</span>
		// </button>

		var button2 = document.createElement('button');
		button2.setAttribute('type', 'button');
		button2.setAttribute('class', 'btn btn-primary dropdown-toggle dropdown-toggle-split');
		var bid = randomNum(1,10000);
		bid = tags[inditem] + bid;
		button2.setAttribute('id', bid);
		button2.setAttribute('data-toggle', 'dropdown');
		button2.setAttribute('aria-haspopup', 'true');
		button2.setAttribute('aria-expanded', 'false');
		// <span class="sr-only">Toggle Dropdown</span>
		var span1 =  document.createElement('span');
		span1.setAttribute('class', 'sr-only');
		span1.innerHTML = 'Toggle Dropdown';
		button2.appendChild(span1);
		itemdiv.appendChild(button2);

		var div1 = document.createElement('div');
		div1.setAttribute('class', 'dropdown-menu');
		div1.setAttribute('aria-labelledby', bid);
		// <h6 class="dropdown-header">Settings</h6> 第一条，只显示，不能按
		var label1 = document.createElement('h6');
		label1.setAttribute('class', 'dropdown-header');
		label1.innerHTML = tags[inditem];
		div1.appendChild(label1);
		for(var indi=0;indi<itemD.length;indi++){
			// <a class="dropdown-item" href="#">Action</a> 
			var itemDIV = document.createElement('a');
			var itemDI = itemD[indi];
			// 如果name=tags[inditem]，不显示
			if(itemDI.name != tags[inditem]){
				console.log("aaa=> itemD= "+JSON.stringify(itemD));
				itemDIV.setAttribute('class', 'dropdown-item');
				itemDIV.innerHTML = itemDI.name;
				itemDIV.setAttribute('onclick', 'routeTo(\''+itemDI.diagram+'\','+itemDI._galleryKey+','+itemDI._routeID+')');
				div1.appendChild(itemDIV);
			}
		}
		itemdiv.appendChild(div1);
		item.appendChild(itemdiv);
		mainTools_Breadcrumb.appendChild(item);
	}
	
	//<li class="breadcrumb-item active" aria-current="page">Data</li>
	var lastli = document.createElement('li');
	lastli.setAttribute('class', 'breadcrumb-item active');
	lastli.setAttribute('aria-current', 'page');
	lastli.innerHTML = tags[tags.length -1] ;
	mainTools_Breadcrumb.appendChild(lastli);

	console.log("aaa=> "+mainTools_Breadcrumb.innerHTML);
	
}
//生成从minNum到maxNum的随机数
function randomNum(minNum,maxNum){ 
    switch(arguments.length){ 
        case 1: 
            return parseInt(Math.random()*minNum+1,10); 
        break; 
        case 2: 
            return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10); 
        break; 
            default: 
                return 0; 
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
