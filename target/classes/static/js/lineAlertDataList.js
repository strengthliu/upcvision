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
function editItemAction(itemId) {
	console.log(itemId);
alert(itemId);
}
function deleteItemAction(itemId) {

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
				//console.log("server info : "+JSON.stringify(data.data.data));
				var realTimeData = data.data.data;
				$('#newItemAction_mid').modal('hide');
				fixLocalRealTimeDataList(realTimeData);
				// 
			} else {
				alert("失败 ： "+data.msg);
			}
			hideLoading();
			//alert("本地存储："+localStorage.user);
			//window.location.href = "index.html";
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			//alert(XMLHttpRequest.responseText);
			//alert(textStatus);
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
function shareItemAction() {

}

/**
 * 添加一个实时数据
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

	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc.value};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "newLineAlertDataGroup",
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
				//console.log("server info : "+JSON.stringify(data.data.data));
				var realTimeData = data.data.data;
				$('#newItemAction_mid').modal('hide');
				fixLocalRealTimeDataList(realTimeData);
				// 
			} else {
				alert("失败 ： "+data.msg);
			}
			hideLoading();
			//alert("本地存储："+localStorage.user);
			//window.location.href = "index.html";
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			//alert(XMLHttpRequest.responseText);
			//alert(textStatus);
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
 * 修改左侧实时数据列表
 * @param realTimeData
 * @returns
 */
function fixLocalRealTimeDataList(lineAlertData){
	//$('#newItemAction_mid').modal('hide');
	cancel();
	
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalRealTimeDataList);
		return;
	}
	if(lineAlertData.owner !=null && lineAlertData.owner !="undefined"){
		userSpace.lineAlertData[lineAlertData.id]=lineAlertData;
		//realTimeDataList = realTimeDataList.realTimeData;
		
		updateRealTimeData();
		return;
	}

}

console.log("_linealertdataListKey = " + _linealertdataListKey);
var _lineAlertDatas;
// TODO: 如果key为空，就是异常，待处理。
if (_linealertdataListKey == null || _linealertdataListKey == "undefined")
	_linealertdataListKey = "";

if (_linealertdataListKey.trim() == 'unclassify') {
	alert(_linealertdataListKey);
	_linealertdataListKey = "";
	_lineAlertDatas = userSpace.lineAlertData[""];
} else
	_lineAlertDatas = userSpace.lineAlertData;
// console.log("graphs => "+JSON.stringify(userSpace));

// console.log("graphs = "+JSON.stringify(userSpace.graphs[""]));

var _lineAlertDatas_ui = document.getElementById("lineAlertDataList_ui");
var realtimeDataList_ui_innerHTML = "";
if (_lineAlertDatas != null && _lineAlertDatas != "undefined")
	Object
			.keys(_lineAlertDatas)
			.forEach(
					function(key) {
						var _realtimeData = _lineAlertDatas[key];
						var realtimeDataList_ui_item_innerHTML = '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12">';

						realtimeDataList_ui_item_innerHTML += '<figure class="effect-text-in">';

						realtimeDataList_ui_item_innerHTML += '<img src="'
								+ _realtimeData.img + '" alt="image" ';
						realtimeDataList_ui_item_innerHTML += 'onclick="routeTo('
							+ "'"
							+ "realtimedataDetail','"
							+ _realtimeData.id + "'" + ');"/>';

						realtimeDataList_ui_item_innerHTML += '<figcaption><h4>'
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
						realtimeDataList_ui_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2"';
						realtimeDataList_ui_item_innerHTML += 'editItemAction(\''+_realtimeData.id+'\')">';
						realtimeDataList_ui_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
						realtimeDataList_ui_item_innerHTML += '</button></div>';

						realtimeDataList_ui_item_innerHTML += '</figure></div>';

						// console.log(diagram_gallery_item_innerHTML);
						realtimeDataList_ui_innerHTML = realtimeDataList_ui_innerHTML
								+ realtimeDataList_ui_item_innerHTML;
					});
_lineAlertDatas_ui.innerHTML = realtimeDataList_ui_innerHTML;

// console.log(" in _gallery.html userSpace="+JSON.stringify(userSpace));