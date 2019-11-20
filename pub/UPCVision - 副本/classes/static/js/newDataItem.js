/**
 * 
 */
// alert("newDataItem");
// 已经选择了的点
var selectedPoints = new Set();
var _selectedPoints = new Array();
// 组的名称
var targetName = "";
// 服务器名称
var serverName = "";
var defaultDevice;
var defaultPoints;
var serverList;
var defaultServer;

// 确保服务器和点位数据
if(serverList == null || serverList=="undefined"){
	// 去取服务器数据
	var data={'uid':uid,'token':token};
		$.ajax({
			// 提交数据的类型 POST GET
			type : "POST",
			// 提交的网址
			url : "getServerInfo",
			// 提交的数据
			data : JSON.stringify(data),
			contentType : "application/json",
			// 返回数据的格式
			datatype : "json",// "xml", "html", "script", "json", "jsonp",
								// "text".
			// 在请求之前调用的函数
			beforeSend : function() {
				$("#msg").html("logining");
			},
			// 成功返回之后调用的函数
			success : function(data) {
				if (data.status == "000"){
					serverList = data.data.data;
					 console.log("serverList: "+JSON.stringify(serverList));
					buildNewItemUI(serverList);
				} else {
					alert("失败 ： "+data.msg);
				}
			},
			// 调用执行后调用的函数
			complete : function(XMLHttpRequest, textStatus) {
			},
			// 调用出错执行的函数
			error : function(jqXHR, textStatus, errorThrown) {
			}
		});
} else {
	// alert("newDataItem2");
	buildNewItemUI(serverList);
}

function getPointByTagName(tagName){
	var ss = Object.keys(serverList);
	
	for(var indss=0;indss<ss.length;indss++){
		// 带服务器名称
//		console.log(tagName.startWith("demo"));
		if(tagName.indexOf(ss[indss])==0)
			tagName = tagName.substring(ss[indss].length+1);
	
		console.log("ss[indss]="+ss[indss]+" tagName="+tagName+"  "+tagName.indexOf(ss[indss]));
		var p = serverList[ss[indss]].points[tagName];
		if(p!=null && p!="undefined")
			return p;
	}
	return null;
}


function contains(selectedPoints,serverName,tagName) {
	var _selectedPoints_ = new Array();
	selectedPoints.forEach(function(key){
		_selectedPoints_.push(key);
	});
//	console.log("selectedPoints= "+JSON.stringify(selectedPoints));
	for(var i=0;i<_selectedPoints_.length;i++){
		var key = 	_selectedPoints_[i];
		var _serverP = splitServerPoint(key);
//		console.log(" selectedPoints->"+key+"  "+JSON.stringify(_serverP)+" "+serverName+" "+tagName);
		if(_serverP.length!=2) return false;
		if(_serverP[0].toLowerCase() == serverName.toLowerCase() && _serverP[1].toLowerCase() == tagName.toLowerCase()){
//			console.log(" ================= got equal.");
			return true;
		}
	}
	return false;
}

// 构建服务器、设备及点位
function buildNewItemUI(serverInfos) {
	 console.log("data =>"+actionType+"  "+itemID);

	
	console.log(" ----- debug 1 -----");
	// console.log("buildNewItemUI");
	// 清空服务器选择列表
	$("#newItem_ServerSelectSect").html("");
	$("#newItem_ServerSelectSect").empty();
	// 添加服务器选择项
	Object.keys(serverInfos).forEach(function(key){
		$("#newItem_ServerSelectSect").append("<option value='"+key+"'>"+key+"</option>");  // 为Select追加一个Option(下拉项)
	});
	console.log(" ----- debug 2 -----");
	// 添加默认位号组
	defaultServer = serverInfos[$("#newItem_ServerSelectSect").val()];
	 console.log(JSON.stringify(defaultServer.serverName));
	var deviceInfos = defaultServer.devices;
	Object.keys(deviceInfos).forEach(function(keyDevice){
		// $("#newItem_DeviceSelectSect").append("<option
		// value='"+deviceInfos[keyDevice].id+"'>"+deviceInfos[keyDevice].deviceNote+"</option>");
		// //为Select追加一个Option(下拉项)
		$("#newItem_DeviceSelectSect").append("<option value='"+deviceInfos[keyDevice].id+"'>"+deviceInfos[keyDevice].deviceName+"</option>");  // 为Select追加一个Option(下拉项)
	});

	console.log(" ----- debug 3 -----");

	// 添加默认左侧点位选择列表
	defaultDevice = serverInfos[$("#newItem_ServerSelectSect").val()].devices[$("#newItem_DeviceSelectSect").val()-1];
	defaultPoints = defaultDevice.points;
	// console.log(JSON.stringify(defaultDevice));
	var pointLeftBox = document.getElementById("newItem_pointlistleft");
	pointLeftBox.innerHTML="";
	var pointLeftBoxInnerHtml = "";
	console.log(" ----- debug 4 -----"+JSON.stringify(defaultPoints));
	Object.keys(defaultPoints).forEach(function(keyPoint){
	// 排除已经选择的点位
		
		if(contains(selectedPoints,defaultPoints[keyPoint].serverName,defaultPoints[keyPoint].tagName)){
			console.log("  has key:"+keyPoint);
		}else{
			var pointLeftBoxInnerHtmlItem = '<div class="card rounded mb-2" id="div'+defaultPoints[keyPoint].tagName+'">'+
			'<div><div class="media icheck-line"><input type="checkbox" id="check'+
			defaultPoints[keyPoint].tagName+'" onclick="newDataItem_leftboxitemcheck(\''+defaultPoints[keyPoint].tagName+'\');">'+
	        '<label for="line-radio-disabled-checked">'+defaultPoints[keyPoint].tagName+' : '+defaultPoints[keyPoint].desc+'</label></div></div></div>';
			pointLeftBoxInnerHtml += pointLeftBoxInnerHtmlItem;
		}


	});
	pointLeftBox.innerHTML=pointLeftBoxInnerHtml;
	console.log(" ----- debug 5 -----");
	
	// 将已经选择的点位加进去到右侧
	// 清空右侧已经选择项
	var pointRightBox = document.getElementById("newItem_pointlistright");
	pointRightBox.innerHTML="";
	var pointRightBoxInnerHtml = "";
	console.log(" ----- debug 6 -----");
 
	selectedPoints.forEach(function(keyPoint)
	{
		var _point = getPointByTagName(keyPoint);
		if(_point!=null){

			console.log(" selectedPoints - "+_point+"  defaultPoints[keyPoint]="+JSON.stringify(_point));
			var pointRightBoxInnerHtmlItem = '<div class="card rounded mb-2" id="div'+_point.tagName+'">'+
			'<div><div class="media icheck-line"><input type="checkbox" id="check'+
			_point.tagName+'" checked="checked" onclick="newDataItem_leftboxitemcheck(\''+_point.tagName+'\');">'+
	        '<label for="line-radio-disabled-checked">'+_point.tagName+' : '+_point.desc+'</label></div></div></div>';
			pointRightBoxInnerHtml += pointRightBoxInnerHtmlItem;
		}
	});
	pointRightBox.innerHTML=pointRightBoxInnerHtml;

	
	serverName = $("#newItem_ServerSelectSect").val();
	// console.log(" 1 => "+$("#newItem_ServerSelectSect").val()+" |
	// "+$("#newItem_ServerSelectSect").text);
	// 添加选择了服务器事件
	$("#newItem_ServerSelectSect").change(function(){
		$("#newItem_DeviceSelectSect").html("");
		$("#newItem_DeviceSelectSect").empty();
		
		serverName = $("#newItem_ServerSelectSect").val();
		// console.log($("#newItem_ServerSelectSect").val()+" |
		// "+$("#newItem_ServerSelectSect").text);
		
		var deviceInfos = serverInfos[$("#newItem_ServerSelectSect").val()];
		console.log("newDataItem.buildNewItemUI 175行 =》 deviceInfos= "+JSON.stringify(deviceInfos));
		Object.keys(deviceInfos).forEach(function(keyDevice){
			$("#newItem_DeviceSelectSect").append("<option value='Value'>"+keyDevice+"</option>");  // 为Select追加一个Option(下拉项)
		});
		var deviceInfos = defaultServer.devices;
		Object.keys(deviceInfos).forEach(function(keyDevice){
			// $("#newItem_DeviceSelectSect").append("<option
			// value='"+deviceInfos[keyDevice].id+"'>"+deviceInfos[keyDevice].deviceNote+"</option>");
			// //为Select追加一个Option(下拉项)
			$("#newItem_DeviceSelectSect").append("<option value='"+deviceInfos[keyDevice].id+"'>"+deviceInfos[keyDevice].deviceName+"</option>");  // 为Select追加一个Option(下拉项)
		});
		
	});
	
	// 添加选择了位号组事件
	$("#newItem_DeviceSelectSect").change(function(){
		var pointLeftBox = document.getElementById("newItem_pointlistleft");
		pointLeftBox.innerHTML="";
		var pointLeftBoxInnerHtml = "";

		defaultDevice = serverInfos[$("#newItem_ServerSelectSect").val()].devices[$("#newItem_DeviceSelectSect").val()-1];
		defaultPoints = defaultDevice.points;

		
		Object.keys(defaultPoints).forEach(function(keyPoint){
			if(contains(selectedPoints,JSON.stringify(defaultServer.serverName)+"\\"+keyPoint)){
				console.log("  has key:"+keyPoint);
			}else{

				var pointLeftBoxInnerHtmlItem = '<div class="card rounded mb-2" id="div'+defaultPoints[keyPoint].tagName+'">'+
				'<div><div class="media icheck-line"><input type="checkbox" id="check'+
				defaultPoints[keyPoint].tagName+'" onclick="newDataItem_leftboxitemcheck(\''+defaultPoints[keyPoint].tagName+'\');">'+
		        '<label for="line-radio-disabled-checked">'+defaultPoints[keyPoint].tagName+' : '+defaultPoints[keyPoint].desc+'</label></div></div></div>';
				pointLeftBoxInnerHtml += pointLeftBoxInnerHtmlItem;
			}
		});
		pointLeftBox.innerHTML=pointLeftBoxInnerHtml;

	});
}

// 左侧点击过滤
function filterNewItem_pointlistleft(){
	var filterTagName = document.getElementById("newItem_pointfilterpointname").value;
	var filterTagDesc = document.getElementById("newItem_pointfilterpointdesc").value;
	if(filterTagName==null || filterTagName=="undefined") 
		filterTagName = ""; 
	else 
		filterTagName = filterTagName.toLowerCase();
	if(filterTagDesc==null || filterTagDesc=="undefined") 
		filterTagDesc = ""; 
	else 
		filterTagDesc = filterTagDesc.toLowerCase();
	//
	
	var defaultDevice = serverList[$("#newItem_ServerSelectSect").val()].devices[$("#newItem_DeviceSelectSect").val()-1];
	var _Points = defaultDevice.points;
	// console.log(JSON.stringify(_Points));
	var filterPoints = new Array();
	// 过滤
	if(!(filterTagDesc == "" && filterTagName == "")) 
	{
		var filterPointsInd = 0;
		Object.keys(_Points).forEach(function(keyPoint){
			var _point = _Points[keyPoint];
			// console.log(" check point : "+JSON.stringify(_point));
			var _tagName = _point.tagName.toLowerCase();
			var _desc = _point.desc.toLowerCase();
			if((filterTagName!="" && _tagName.search(filterTagName) != -1) || (filterTagDesc!="" && _desc.search(filterTagDesc) != -1)) {
				// console.log("tagName="+_tagName +" =? "+filterTagName+" = "+
				// _tagName.search(filterTagName));
				filterPoints[filterPointsInd]=_point;
				filterPointsInd ++;
			}
		});
		
		var pointLeftBox = document.getElementById("newItem_pointlistleft");
		pointLeftBox.innerHTML="";
		var pointLeftBoxInnerHtml = "";

		// console.log(JSON.stringify(filterPoints));
		for(var keyPoint in filterPoints){
			var pointLeftBoxInnerHtmlItem = '<div class="card rounded mb-2" id="div'+filterPoints[keyPoint].tagName+'">'+
			'<div><div class="media icheck-line"><input type="checkbox" id="check'+
			filterPoints[keyPoint].tagName+'" onclick="newDataItem_leftboxitemcheck(\''+filterPoints[keyPoint].tagName+'\');">'+
	        '<label for="line-radio-disabled-checked">'+filterPoints[keyPoint].tagName+' : '+filterPoints[keyPoint].desc+'</label></div></div></div>';
			pointLeftBoxInnerHtml += pointLeftBoxInnerHtmlItem;
		}
		pointLeftBox.innerHTML=pointLeftBoxInnerHtml;
	} else {
		var pointLeftBox = document.getElementById("newItem_pointlistleft");
		pointLeftBox.innerHTML="";
		var pointLeftBoxInnerHtml = "";

		Object.keys(_Points).forEach(function(keyPoint){
			var pointLeftBoxInnerHtmlItem = '<div class="card rounded mb-2" id="div'+_Points[keyPoint].tagName+'">'+
			'<div><div class="media icheck-line"><input type="checkbox" id="check'+
			_Points[keyPoint].tagName+'" onclick="newDataItem_leftboxitemcheck(\''+_Points[keyPoint].tagName+'\');">'+
	        '<label for="line-radio-disabled-checked">'+_Points[keyPoint].tagName+' : '+_Points[keyPoint].desc+'</label></div></div></div>';
			pointLeftBoxInnerHtml += pointLeftBoxInnerHtmlItem;
		});
		pointLeftBox.innerHTML=pointLeftBoxInnerHtml;
		
	}
}

// 点击左侧框选择操作
function newDataItem_leftboxitemcheck(elemId,editAction) {
	if(editAction==null || editAction=="undefined")
		editAction = false;
	// alert("check "+ elemId);
	var newItem_pointlistleft = document.getElementById("newItem_pointlistleft");
	var newItem_pointlistright = document.getElementById("newItem_pointlistright");
	console.log(" elemId="+elemId);
	// 找出目标
	var targetCheck = document.getElementById("check"+elemId);
	if(targetCheck!=null && targetCheck!="undefined"){
		if(targetCheck.checked == true || editAction){
			targetCheck.checked = true;
			var targetDiv = document.getElementById("div"+elemId);
			// 左侧删除
			newItem_pointlistleft.removeChild(targetDiv);
			// 右侧添加
			newItem_pointlistright.appendChild(targetDiv);
			selectedPoints.add(serverName+"\\"+elemId);
			var p = {'server':serverName,
					'tagName':elemId,
					'alertInterval':0,
					"stageValue":0,
					"upperLimit":0,
					"floorLimit":0,
					"color":"#ffe74c",
					"isx":false
					};
			_selectedPoints.push(p);
		}else {
//			Elements es = document.querySelectorAll("#div"+elemId);
//			for(var i =0;i<es.length;i++){
//				var targetDiv1=es[i];
//				console.log("outer: "+targetDiv1.outerHTML);
//			}
			var targetDiv = document.getElementById("div"+elemId);
			console.log("outer: "+targetDiv.parentNode.outerHTML);
			console.log("====================================================");
			console.log("html: "+newItem_pointlistright.outerHTML);
			// 右侧删除
//			targetDiv.parentNode.removeChild(targetDiv);
			newItem_pointlistright.removeChild(targetDiv);
			targetCheck.checked = false;
			// 左侧添加
			console.log("defaultPoints => "+JSON.stringify(defaultPoints));
			
			if(defaultPoints[elemId]!=null &&defaultPoints[elemId]!="undefined" ){
				newItem_pointlistleft.appendChild(targetDiv);				
			}
			selectedPoints.delete(serverName+"\\"+elemId);
			var p = {'server':serverName,
					'tagName':elemId,
					'alertInterval':0,
					"stageValue":0,
					"upperLimit":0,
					"floorLimit":0,
					"color":"#ffe74c",
					"isx":false,
					"relativetime":0,
					"starttime":"",
					"terminaltime":""
					};
	
			_selectedPoints.splice(p);
	
		}
	}else {
//		var targetDiv = document.getElementById("div"+elemId);
//
//		// 右侧添加
//		newItem_pointlistright.appendChild(targetDiv);
//		selectedPoints.add(serverName+"\\"+elemId);
//		var p = {'server':serverName,
//				'tagName':elemId,
//				'alertInterval':0,
//				"stageValue":0,
//				"upperLimit":0,
//				"floorLimit":0,
//				"color":"#ffe74c",
//				"isx":false
//				};
//		_selectedPoints.push(p);

	}
}

//
//// 点击左侧框选择操作
//function newDataItem_addboxitemcheck(elemId,editAction) {
//	if(editAction==null || editAction=="undefined")
//		editAction = false;
//	// alert("check "+ elemId);
//	var newItem_pointlistleft = document.getElementById("newItem_pointlistleft");
//	var newItem_pointlistright = document.getElementById("newItem_pointlistright");
//	console.log("elemId="+elemId);
//	// 找出目标
//	var targetCheck = document.getElementById("check"+elemId);
//	if(targetCheck.checked == true || editAction){
//		targetCheck.checked = true;
//		var targetDiv = document.getElementById("div"+elemId);
//		// 左侧删除
//		newItem_pointlistleft.removeChild(targetDiv);
//		// 右侧添加
//		newItem_pointlistright.appendChild(targetDiv);
//		selectedPoints.add(serverName+"\\"+elemId);
//		var p = {'server':serverName,
//				'tagName':elemId,
//				'alertInterval':0,
//				"stageValue":0,
//				"upperLimit":0,
//				"floorLimit":0,
//				"color":"#ffe74c",
//				"isx":false
//				};
//		_selectedPoints.push(p);
//	}else {
//		targetCheck.checked = false;
//		var targetDiv = document.getElementById("div"+elemId);
//		console.log();
//		// 左侧删除
//		newItem_pointlistright.removeChild(targetDiv);
//		// 右侧添加
//		newItem_pointlistleft.appendChild(targetDiv);
//		selectedPoints.delete(serverName+"\\"+elemId);
//		var p = {'server':serverName,
//				'tagName':elemId,
//				'alertInterval':0,
//				"stageValue":0,
//				"upperLimit":0,
//				"floorLimit":0,
//				"color":"#ffe74c",
//				"isx":false,
//				"relativetime":0,
//				"starttime":"",
//				"terminaltime":""
//				};
//
//		_selectedPoints.splice(p);
//
//	}
//}

// 编辑按钮
function editItem(){
	var item;
	// 编辑
	 console.log("data =>"+actionType+"  "+itemID);
	if(actionType!=null && actionType!="undefined" && itemID!=null && itemID!="undefined"){
		switch(actionType.toLowerCase()){
			case "realTimeData".toLowerCase():
				item = userSpace.realTimeData[itemID];
				break;
			case "alertData".toLowerCase():
				item = userSpace.alertData[itemID];
				break;
			case "historyData".toLowerCase():
				item = userSpace.historyData[itemID];
				break;
			case "lineAlertData".toLowerCase():
				item = userSpace.lineAlertData[itemID];
				break;
			case "xyGraph".toLowerCase():
				item = userSpace.xyGraph[itemID];
				break;
		}
		console.log("data =>"+JSON.stringify(item));
		if(item!=null && item!="undefined"){
			targetName = item.name; // 设置名称
			var psel = document.getElementById("_targetNameUI");
			console.log("newDataItem.js debug 1");
			psel.value = targetName; // 设置名称显示
			console.log("newDataItem.js debug 1_1");
			
			var pseld = document.getElementById("targetDesc");
			targetDesc = item.desc;
			console.log("newDataItem.js debug 2");
			pseld.value = targetDesc;// 设置描述显示
			console.log("newDataItem.js debug 2_2");
			selectedPoints = new Set();
			
			Object.keys(item.pointList).forEach(function(key){
				var id__= item.pointList[key].tagName;
				console.log(" id = "+id__);
				selectedPoints.add(serverName+"\\"+id__);

//				newDataItem_addboxitemcheck(id__,true);
			});
			if(actionType.toLowerCase() == "historyData".toLowerCase()){
				var _selectedPoints = item.otherrule1._selectedPoints;
				fillAttribute(item.otherrule1);
			}
			buildNewItemUI(serverList);
		}
	}
}

