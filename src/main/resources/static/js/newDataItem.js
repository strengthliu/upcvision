/**
 * 
 */
// alert("newDataItem");
var selectedPoints = new Set();
var targetName = "";
var serverName = "";


if(serverList == null || serverList=="undefined"){
	// alert("newDataItem1");

	// 去取服务器数据
// console.log("进入");
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
				if (data.status == GlobalConsts.ResultCode_SUCCESS) {
					// console.log("server info :
					// "+JSON.stringify(data.data.data));
					serverList = data.data.data;
					// console.log(JSON.stringify(serverList));
					buildNewItemUI(serverList);
				} else {
					alert("失败 ： "+data.msg);
				}
				
				// alert("本地存储："+localStorage.user);
				// window.location.href = "index.html";
			},
			// 调用执行后调用的函数
			complete : function(XMLHttpRequest, textStatus) {
				// alert(XMLHttpRequest.responseText);
				// alert(textStatus);
				// HideLoading();
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
			}
		});
} else {
	// alert("newDataItem2");
	buildNewItemUI(serverList);
}


function buildNewItemUI(serverInfos) {
	// console.log("buildNewItemUI");
	// 清空服务器选择列表
	$("#newItem_ServerSelectSect").html("");
	$("#newItem_ServerSelectSect").empty();
	// 添加服务器选择项
	Object.keys(serverInfos).forEach(function(key){
		$("#newItem_ServerSelectSect").append("<option value='"+key+"'>"+key+"</option>");  // 为Select追加一个Option(下拉项)
	});
	// 添加默认位号组
	var defaultServer = serverInfos[$("#newItem_ServerSelectSect").val()];
	// console.log(JSON.stringify(defaultServer));
	var deviceInfos = defaultServer.devices;
	Object.keys(deviceInfos).forEach(function(keyDevice){
		// $("#newItem_DeviceSelectSect").append("<option
		// value='"+deviceInfos[keyDevice].id+"'>"+deviceInfos[keyDevice].deviceNote+"</option>");
		// //为Select追加一个Option(下拉项)
		$("#newItem_DeviceSelectSect").append("<option value='"+deviceInfos[keyDevice].id+"'>"+deviceInfos[keyDevice].deviceName+"</option>");  // 为Select追加一个Option(下拉项)
	});


	// 添加默认左侧点位选择列表
	var defaultDevice = serverInfos[$("#newItem_ServerSelectSect").val()].devices[$("#newItem_DeviceSelectSect").val()-1];
	var defaultPoints = defaultDevice.points;
	// console.log(JSON.stringify(defaultDevice));
	var pointLeftBox = document.getElementById("newItem_pointlistleft");
	pointLeftBox.innerHTML="";
	var pointLeftBoxInnerHtml = "";
	Object.keys(defaultPoints).forEach(function(keyPoint){
		var pointLeftBoxInnerHtmlItem = '<div class="card rounded mb-2" id="div'+defaultPoints[keyPoint].tagName+'">'+
		'<div><div class="media icheck-line"><input type="checkbox" id="check'+
		defaultPoints[keyPoint].tagName+'" onclick="newDataItem_leftboxitemcheck(\''+defaultPoints[keyPoint].tagName+'\');">'+
        '<label for="line-radio-disabled-checked">'+defaultPoints[keyPoint].tagName+' : '+defaultPoints[keyPoint].desc+'</label></div></div></div>';
		pointLeftBoxInnerHtml += pointLeftBoxInnerHtmlItem;
	});
	pointLeftBox.innerHTML=pointLeftBoxInnerHtml;
	// 清空右侧已经选择项
	var pointLeftBox = document.getElementById("newItem_pointlistright");
	pointLeftBox.innerHTML="";
	
	serverName = $("#newItem_ServerSelectSect").val();
	// console.log(" 1  => "+$("#newItem_ServerSelectSect").val()+" | "+$("#newItem_ServerSelectSect").text);
	// 添加选择了服务器事件
	$("#newItem_ServerSelectSect").change(function(){
		$("#newItem_DeviceSelectSect").html("");
		$("#newItem_DeviceSelectSect").empty();
		
		serverName = $("#newItem_ServerSelectSect").val();
		// console.log($("#newItem_ServerSelectSect").val()+" | "+$("#newItem_ServerSelectSect").text);
		
		var deviceInfos = serverInfos[$("#newItem_ServerSelectSect").val()];
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

		var defaultDevice = serverInfos[$("#newItem_ServerSelectSect").val()].devices[$("#newItem_DeviceSelectSect").val()-1];
		var defaultPoints = defaultDevice.points;

		Object.keys(defaultPoints).forEach(function(keyPoint){
			var pointLeftBoxInnerHtmlItem = '<div class="card rounded mb-2" id="div'+defaultPoints[keyPoint].tagName+'">'+
			'<div><div class="media icheck-line"><input type="checkbox" id="check'+
			defaultPoints[keyPoint].tagName+'" onclick="newDataItem_leftboxitemcheck(\''+defaultPoints[keyPoint].tagName+'\');">'+
	        '<label for="line-radio-disabled-checked">'+defaultPoints[keyPoint].tagName+' : '+defaultPoints[keyPoint].desc+'</label></div></div></div>';
			pointLeftBoxInnerHtml += pointLeftBoxInnerHtmlItem;
		});
		pointLeftBox.innerHTML=pointLeftBoxInnerHtml;

	});
}

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

function newDataItem_leftboxitemcheck(elemId,editAction) {
	if(editAction==null || editAction=="undefined")
		editAction = false;
	// alert("check "+ elemId);
	var newItem_pointlistleft = document.getElementById("newItem_pointlistleft");
	var newItem_pointlistright = document.getElementById("newItem_pointlistright");
	console.log("elemId="+elemId);
	// 找出目标
	var targetCheck = document.getElementById("check"+elemId);
	if(targetCheck.checked == true || editAction){
		targetCheck.checked = true;
		var targetDiv = document.getElementById("div"+elemId);
		// 左侧删除
		newItem_pointlistleft.removeChild(targetDiv);
		// 右侧添加
		newItem_pointlistright.appendChild(targetDiv);
		selectedPoints.add(serverName+"."+elemId);
	}else {
		targetCheck.checked = false;
		var targetDiv = document.getElementById("div"+elemId);
		// 左侧删除
		newItem_pointlistright.removeChild(targetDiv);
		// 右侧添加
		newItem_pointlistleft.appendChild(targetDiv);
		selectedPoints.delete(serverName+"."+elemId);

	}
}
	function editItem(){
	var item;
	// 编辑
	console.log("data =>"+actionType+"  "+itemID);
	if(actionType!=null && actionType!="undefined" && itemID!=null && itemID!="undefined"){
		switch(actionType){
			case "realTimeData":
				item = userSpace.realTimeData[itemID];
				break;
			case "alertData":
				break;
		}
		console.log("data =>"+JSON.stringify(item));
		if(item!=null && item!="undefined"){
			targetName = item.name;
			var psel = document.getElementById("targetName");
			psel.value = targetName;
			var pseld = document.getElementById("targetDesc");
			targetDesc = item.desc;
			pseld.value = targetDesc;
			
			Object.keys(item.pointList).forEach(function(key){
				var id__= item.pointList[key].tagName;
				console.log(id__);
				selectedPoints.add(serverName+"."+id__);
				newDataItem_leftboxitemcheck(id__,true);
			});
			
		}
	}
	}

