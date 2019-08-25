/**
 * 
 */

/**
 * 悬浮菜单功能
 */
// 新建
function newItemAction() {
	// alert("realtimeDataList.newItemAction");
	$('#newItemAction_mid').modal('show');
	
}
var itemID;
var actionType = "realTimeData";
function editItemAction(itemId) {
	console.log(itemId);
// alert(itemId);
	itemID = itemId;
$('#newItemAction_mid').modal('show');
editItem();
}
function deleteItemAction(itemId) {
console.log("deleteItemAction");
	var data={'uid':uid,'token':token,'id':itemId};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "deleteRealTimeDataGroup",
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
			if (data.status == GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : "+JSON.stringify(data.data.data));
				var realTimeDataId = data.data.data;
				// console.log("data.data="+JSON.stringify(data));
				// console.log("realTimeDataId="+realTimeDataId);
				// console.log("data="+JSON.stringify(data,null,2));
				// $('#newItemAction_mid').modal('hide');
				fixLocalRealTimeDataList_Delete(realTimeDataId);
				if(data.refresh) routeTo('realtimedataList','');
				// 
			} else {
				alert("失败 ： "+data.msg);
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

var dataItemId;
function shareItemAction(itemId) {
	dataItemId = itemId;
	shareType = "realTimeData";
	$('#shareItemAction_mid').modal('show');
	loadUsers();
}

/**
 * 添加一个实时数据
 * 
 * @param selectedPoints
 * @param targetName
 * @param targetDesc
 * @returns
 */
function submitNewDataItem(selectedPoints,targetName,targetDesc){
	if(serverList == null || serverList=="undefined"){
		
	}
	console.log("2: "+JSON.stringify(selectedPoints));
	// console.log(JSON.stringify(userSpace));
	var selectPointArray = new Array();
	var i__ = 0;
	for (let e of selectedPoints) {
		selectPointArray[i__] = e;
		i__++;
		}

	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc.value,'id':itemID};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "newRealTimeDataGroup",
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
			if (data.status == GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : "+JSON.stringify(data.data.data));
				var realTimeData = data.data.data;
				$('#newItemAction_mid').modal('hide');
				fixLocalRealTimeDataList(realTimeData);
				// 
			} else {
				alert("失败 ： "+data.msg);
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


/**
 * 修改左侧实时数据列表,删除一个数据
 * 
 * @param realTimeData
 * @returns
 */
function fixLocalRealTimeDataList_Delete(realTimeDataId){
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalRealTimeDataList_Delete);
		return;
	}
	if(realTimeDataId !=null && realTimeDataId !="undefined"){
		var realTimeData = userSpace.realTimeData;
		console.log("realTimeDataId="+JSON.stringify(realTimeDataId));
		console.log(JSON.stringify(realTimeData[realTimeDataId]));
		delete realTimeData[realTimeDataId];
		delete userSpace.realTimeData[realTimeDataId];
		// _.omit(realTimeData, [realTimeDataId]);
		console.log(JSON.stringify(realTimeData[realTimeDataId]));
		userSpace.realTimeData = realTimeData;
		console.log("after delete realTimeData => "+JSON.stringify(userSpace.realTimeData));
		
		updateRealTimeData();
		return;
	}
	
	
}

/**
 * 修改左侧实时数据列表,增加一个新数据
 * 
 * @param realTimeData
 * @returns
 */
function fixLocalRealTimeDataList(realTimeData){
	// $('#newItemAction_mid').modal('hide');
	cancel();
	
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalRealTimeDataList);
		return;
	}
	if(realTimeData.owner !=null && realTimeData.owner !="undefined"){
		userSpace.realTimeData[realTimeData.id]=realTimeData;
		// realTimeDataList = realTimeDataList.realTimeData;
		
		updateRealTimeData();
		return;
	}

}

console.log("_realtimedataListKey = " + _realtimedataListKey);
var _realtimeDatas;
// TODO: 如果key为空，就是异常，待处理。
if (_realtimedataListKey == null || _realtimedataListKey == "undefined")
	_realtimedataListKey = "";

if (_realtimedataListKey.trim() == 'unclassify') {
	alert(_realtimedataListKey);
	_realtimedataListKey = "";
	_realtimeDatas = userSpace.realTimeData[""];
} else
	_realtimeDatas = userSpace.realTimeData;
console.log("realTimeData => "+JSON.stringify(userSpace.realTimeData));

// console.log("graphs = "+JSON.stringify(userSpace.graphs[""]));

var realtimeDataList_ui = document.getElementById("realtimeDataList_ui");
var realtimeDataList_ui_innerHTML = "";
if (_realtimeDatas != null && _realtimeDatas != "undefined")
	Object
			.keys(_realtimeDatas)
			.forEach(
					function(key) {
						var _realtimeData = _realtimeDatas[key];
						var realtimeDataList_ui_item_innerHTML = '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12">';

						realtimeDataList_ui_item_innerHTML += '<figure class="effect-text-in">';

						realtimeDataList_ui_item_innerHTML += '<img src="'
								+ _realtimeData.img + '" alt="image" ';
						realtimeDataList_ui_item_innerHTML += 'onclick="routeTo('
							+ "'"
							+ "realtimedataDetail','"
							+ _realtimeData.id + "'" + ')"/>';

						realtimeDataList_ui_item_innerHTML += '<figcaption onclick="routeTo('
							+ "'"
							+ "realtimedataDetail','"
							+ _realtimeData.id + "'" + ')"><h4>'
								+ _realtimeData.name
								+ '</h4><p>'
								+ _realtimeData.path + '</p></figcaption>';
						realtimeDataList_ui_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
						realtimeDataList_ui_item_innerHTML += '<button type="submit" class="btn btn-success btn-sm" onclick="';
						realtimeDataList_ui_item_innerHTML += 'shareItemAction(\''+_realtimeData.id+'\')">Share</button>';
						realtimeDataList_ui_item_innerHTML += '<button data-repeater-delete type="button" class="btn btn-danger btn-sm icon-btn ml-2" onclick="';
						realtimeDataList_ui_item_innerHTML += 'deleteItemAction(\''+_realtimeData.id+'\')">';
						realtimeDataList_ui_item_innerHTML += '<i class="mdi mdi-delete"></i>';
						realtimeDataList_ui_item_innerHTML += '</button>';
						realtimeDataList_ui_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2" onclick="';
						realtimeDataList_ui_item_innerHTML += 'editItemAction(\''+_realtimeData.id+'\')">';
						realtimeDataList_ui_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
						realtimeDataList_ui_item_innerHTML += '</button></div>';

						realtimeDataList_ui_item_innerHTML += '</figure></div>';
						// console.log(diagram_gallery_item_innerHTML);
						realtimeDataList_ui_innerHTML = realtimeDataList_ui_innerHTML
								+ realtimeDataList_ui_item_innerHTML;
					});
realtimeDataList_ui.innerHTML = realtimeDataList_ui_innerHTML;
console.log(realtimeDataList_ui_innerHTML);

// console.log(" in _gallery.html userSpace="+JSON.stringify(userSpace));
