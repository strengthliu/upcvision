/**
 * 
 */

/**
 * 悬浮菜单功能
 */
// 新建
function newItemAction() {
	if(user.id == 2 || user.role <= 2){
	// alert("xyGraphList.newItemAction");
		$('#newItemAction_mid').modal('show');
	}else {
		alert("您没有权限进行新建操作。");
	}
}


var itemID = _routeID;
var actionType = _routeType;

function editItemAction(itemId) {
	// console.log(itemId);
// alert(itemId);
	itemID = itemId;
	actionType = "realTimeData";
	var _realTimeData = userSpace.realTimeData[itemId];
	if(user.id == _realTimeData.creater || user.id == _realTimeData.owner || user.role == 1){
			$('#newItemAction_mid').modal('show');
		}else {
			alert("您没有权限进行新建操作。");
			return;
		}
editItem();
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
			if (data.status == "000"){ //GlobalConsts.ResultCode_SUCCESS) {
				var realTimeDataId = data.data.data;
				fixLocalRealTimeDataList_Delete(realTimeDataId);
				if(data.refresh) routeTo('realtimedataList','');
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

function doShareActionToServer(){
	if (user == null || user == "undefined") {
		user = localStorage.user;
		uid = user.id;
		token = localStorage.token;
	}
	console.log("dataItemId="+dataItemId+"  user:"+JSON.stringify(Array.from(selectedUsers)));
	var data={'uid':uid,'token':token,'id':dataItemId,'userIds':Array.from(selectedUsers),'type':"realTimeData"};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "shareRightRealTimeData",
		// 提交的数据
		data: JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == "000"){ //GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : "+JSON.stringify(data.data.data));
				var realTimeData = data.data.data;
				userSpace.realTimeData[realTimeData.id]=realTimeData;
				$('#shareItemAction_mid').modal('hide');
				updateRealTimeDataListFrame();
			} else {
				alert("失败 ： " + data.msg);
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
	shareType = _routeType;//"realTimeData";
	setParameter(shareType,itemId);
	$('#shareItemAction_mid').modal('show');
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

	console.log("realtimedata.js => submitNewDataItem 1 "+targetName +"  "+targetDesc);
	var selectPointArray = new Array();
	var i__ = 0;
	for (let e of selectedPoints) {
//		console.log("  "+JSON.stringify(e));
		selectPointArray[i__] = e;
		i__++;
		}

	console.log("realtimedata.js => submitNewDataItem 2");
	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc,'id':itemID};
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
			if (data.status == "000"){ //GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : "+JSON.stringify(data.data.data));
				console.log("realtimedata.js => submitNewDataItem 3");
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
			console.log("realtimedata.js => submitNewDataItem 4");
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
			console.log("realtimedata.js => submitNewDataItem 5");
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
		// console.log("realTimeDataId="+JSON.stringify(realTimeDataId));
		// console.log(JSON.stringify(realTimeData[realTimeDataId]));
		delete realTimeData[realTimeDataId];
		delete userSpace.realTimeData[realTimeDataId];
		// _.omit(realTimeData, [realTimeDataId]);
		// console.log(JSON.stringify(realTimeData[realTimeDataId]));
		userSpace.realTimeData = realTimeData;
		// console.log("after delete realTimeData =>
		// "+JSON.stringify(userSpace.realTimeData));
		
		updateRealTimeData();
		updateRealTimeDataListFrame();
		cancel11();
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
	console.log("fixLocalRealTimeDataList 1");
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalRealTimeDataList);
		cancel11();
		return;
	}
	if(realTimeData.owner !=null && realTimeData.owner !="undefined"){
		userSpace.realTimeData[realTimeData.id]=realTimeData;
		// realTimeDataList = realTimeDataList.realTimeData;
		
		updateRealTimeData();
		updateRealTimeDataListFrame();
		cancel11();
		return;
	}
}

updateRealTimeDataListFrame();

function updateRealTimeDataListFrame(){
	console.log("_realtimedataListKey = " + _realtimedataListKey);
	var _realtimeDatas;
	// TODO: 如果key为空，就是异常，待处理。
	if (_realtimedataListKey == null || _realtimedataListKey == "undefined")
		_realtimedataListKey = "";
	
	if (_realtimedataListKey.trim() == 'unclassify') {
		alert(_realtimedataListKey);
		_realtimedataListKey = "";
		_realtimeDatas = userSpace.realTimeData[""];
	} else {
		_realtimeDatas = userSpace.realTimeData;
	}
	
	// 如果当前主页面不是实时数据这页，就不刷新了
	var realtimeDataList_ui = document.getElementById("realtimeDataList_ui");
	if(realtimeDataList_ui==null || realtimeDataList_ui=="undefined") return;
	//console.log("_realtimeDatas: "+JSON.stringify(_realtimeDatas));
	var realtimeDataList_ui_innerHTML = "";
	if (_realtimeDatas != null && _realtimeDatas != "undefined") {
		Object
				.keys(_realtimeDatas)
				.forEach(
						function(key) {
							var _realtimeData = _realtimeDatas[key];
							if(_realtimeData!=null && _realtimeData!="undefined"){
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
										+ '</h4><div>'
										+'<h5></h5>'
										+'<h5>创建者：'+_realtimeData.createrUser.name + '</h5>';
								var shareStr = "";
								if(_realtimeData.sharedUsers.length>0){
									shareStr += '<h6>'+'分享给了 : ';
									var indsharet1 = 3;
									var indsharet2 = _realtimeData.sharedUsers.length;
									var shareUsers = _realtimeData.sharedUsers;
									for(var iindshare=0;iindshare<shareUsers.length;iindshare++){
										var indshare = shareUsers[iindshare];
										// console.log(JSON.stringify(indshare));
										shareStr += indshare.name+"、";
										indsharet1--;
										if(indsharet1<0){
											shareStr = shareStr.slice(0,shareStr.length-1);
											realtimeDataList_ui_item_innerHTML += "等3人、";
										}
									}
									shareStr = shareStr.slice(0,shareStr.length-1);
									realtimeDataList_ui_item_innerHTML += shareStr;
									realtimeDataList_ui_item_innerHTML +='</h6>';
									// console.log(realtimeDataList_ui_item_innerHTML);
								}

								realtimeDataList_ui_item_innerHTML +='<p>'+_realtimeData.desc + '</p>'+'</div></figcaption>';
								realtimeDataList_ui_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
								// TODO: 判断权限
								if(user.id == _realtimeData.creater || user.id == _realtimeData.owner || user.role == 1){
									realtimeDataList_ui_item_innerHTML += '<button type="submit" class="btn btn-success btn-sm" onclick="';
									realtimeDataList_ui_item_innerHTML += 'shareItemAction(\''+_realtimeData.id+'\')">Share</button>';
								
									realtimeDataList_ui_item_innerHTML += '<button data-repeater-delete type="button" class="btn btn-danger btn-sm icon-btn ml-2" onclick="';
									realtimeDataList_ui_item_innerHTML += 'deleteItemAction(\''+_realtimeData.id+'\')">';
									realtimeDataList_ui_item_innerHTML += '<i class="mdi mdi-delete"></i>';
									realtimeDataList_ui_item_innerHTML += '</button>';
									
									realtimeDataList_ui_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2" onclick="';
									realtimeDataList_ui_item_innerHTML += 'editItemAction(\''+_realtimeData.id+'\')">';
									realtimeDataList_ui_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
									realtimeDataList_ui_item_innerHTML += '</button>';
								}

								realtimeDataList_ui_item_innerHTML += '</div></figure></div>';
								 // console.log(realtimeDataList_ui_item_innerHTML);
								realtimeDataList_ui_innerHTML = realtimeDataList_ui_innerHTML
										+ realtimeDataList_ui_item_innerHTML;
							}
						});
					}
						
	
	realtimeDataList_ui.innerHTML = realtimeDataList_ui_innerHTML;
}
