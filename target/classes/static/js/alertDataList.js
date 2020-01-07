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

//_routeType = diagram;
//_routeID = key;

var itemID = _routeID;
var actionType = _routeType;//"alertData";

function editItemAction(itemId) {
	// console.log(itemId);
// alert(itemId);
	itemID = itemId;
	actionType = "alertData";
	var _alertData = userSpace.alertData[itemId];
	if(user.id == _alertData.creater || user.id == _alertData.owner || user.role == 1){
		$('#newItemAction_mid').modal('show');
	}else {
		alert("您没有权限进行新建操作。");
		return;
	}
	editItem();
}

function deleteItemAction(itemId) {
	console.log("deleteItemAction");
	var data={'uid':uid,'token':token,'id':itemId};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "deleteAlertDataGroup",
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
				var alertDataId = data.data.data;
				fixLocalAlertDataList_Delete(alertDataId);
				if(data.refresh) routeTo('alertDataList','');
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

var dataItemId;
function shareItemAction(itemId) {
	dataItemId = itemId;
	shareType = _routeType;
	setParameter(shareType,itemId);
	$('#shareItemAction_mid').modal('show');
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
		url : "shareRightAlertData",
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
				var alertData = data.data.data;
				userSpace.alertData[alertData.id]=alertData;
				$('#shareItemAction_mid').modal('hide');
				updateAlertDataListFrame();
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

/**
 * 添加一个报警数据
 * 
 * @param selectedPoints
 * @param targetName
 * @param targetDesc
 * @returns
 */
function submitNewDataItem(selectedPoints,targetName,targetDesc){
	console.log("alertData.js => submitNewDataItem 1 "+targetName +"  "+targetDesc);
	var selectPointArray = new Array();
	var i__ = 0;
	for (let e of selectedPoints) {
		selectPointArray[i__] = e;
		i__++;
		}

	console.log("alertData.js => submitNewDataItem 2 itemid="+itemID);
	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc,'id':itemID,'rule':_selectedPoints};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "newAlertDataGroup",
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
				console.log("alertData.js => submitNewDataItem 3");
				var alertData = data.data.data;
				$('#newItemAction_mid').modal('hide');
				console.log("NewAlertData before fixLocalAlertDataList.");
				fixLocalAlertDataList(alertData);
				console.log("NewAlertData finished.");
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
			console.log("alertData.js => submitNewDataItem 4");
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
			console.log("alertData.js => submitNewDataItem 5");
			hideLoading();
		}
	});

}


/**
 * 修改左侧实时数据列表,删除一个数据
 * 
 * @param alertData
 * @returns
 */
function fixLocalAlertDataList_Delete(alertDataId){
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalAlertDataList_Delete);
		return;
	}
	if(alertDataId !=null && alertDataId !="undefined"){
		var alertData = userSpace.alertData;
		// console.log("alertDataId="+JSON.stringify(alertDataId));
		// console.log(JSON.stringify(alertData[alertDataId]));
		delete alertData[alertDataId];
		delete userSpace.alertData[alertDataId];
		// _.omit(alertData, [alertDataId]);
		// console.log(JSON.stringify(alertData[alertDataId]));
		userSpace.alertData = alertData;
		// console.log("after delete alertData =>
		// "+JSON.stringify(userSpace.alertData));
		
		updateAlertData();
		updateAlertDataListFrame();
		cancel11();
		return;
	}
	
	
}

/**
 * 修改左侧实时数据列表,增加一个新数据
 * 
 * @param alertData
 * @returns
 */
function fixLocalAlertDataList(alertData){
	// $('#newItemAction_mid').modal('hide');
	console.log("fixLocalAlertDataList 1");
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalAlertDataList);
		cancel11();
		return;
	}
	if(alertData.owner !=null && alertData.owner !="undefined"){
		userSpace.alertData[alertData.id]=alertData;
		// alertDataList = alertDataList.alertData;
		
		updateAlertData();
		updateAlertDataListFrame();
		cancel11();
		return;
	}
}

updateAlertDataListFrame();

function updateAlertDataListFrame(){
//	_routeType = diagram.toLowerCase();
//	_routeID = key;

	console.log("_routeID = " + _routeID);
	var _alertDatas;
	// TODO: 如果key为空，就是异常，待处理。
	if (_routeID == null || _routeID == "undefined")
		_routeID = "";
	
	if (_routeID.trim() == 'unclassify') {
		alert(_routeID);
		_routeID = "";
		_alertDatas = userSpace.alertData[""];
	} else {
		_alertDatas = userSpace.alertData;
	}
	
	// 如果当前主页面不是实时数据这页，就不刷新了
	var alertDataList_ui = document.getElementById("alertDataList_ui");
	if(alertDataList_ui==null || alertDataList_ui=="undefined") return;

	var alertDataList_ui_innerHTML = "";
	if (_alertDatas != null && _alertDatas != "undefined") {
		Object
				.keys(_alertDatas)
				.forEach(
						function(key) {
							var _alertData = _alertDatas[key];
							if(_alertData!=null && _alertData!="undefined"){
								var alertDataList_ui_item_innerHTML = '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12">';
								alertDataList_ui_item_innerHTML += '<figure class="effect-text-in">';
								alertDataList_ui_item_innerHTML += '<img src="'
										+ _alertData.img + '" alt="image" ';
								
								alertDataList_ui_item_innerHTML += 'onclick="routeTo('
									+ "'"
									+ "alertDataDetail','"
									+ _alertData.id + "'" + ')"/>';
								alertDataList_ui_item_innerHTML += '<figcaption onclick="routeTo('
									+ "'"
									+ "alertDataDetail','"
									+ _alertData.id + "'" + ')"><h4 style="color:#000000;font-weight:bold">'
										+ _alertData.name
										+ '</h4><div>'
										+'<h5></h5>'
										+'<h5>创建者：'+_alertData.createrUser.name + '</h5>';
								var shareStr = "";
								if(_alertData.sharedUsers.length>0){
									shareStr += '<h6>'+'分享给了 : ';
									var indsharet1 = 3;
									var indsharet2 = _alertData.sharedUsers.length;
									var shareUsers = _alertData.sharedUsers;
									for(var iindshare=0;iindshare<shareUsers.length;iindshare++){
										var indshare = shareUsers[iindshare];
										// console.log(JSON.stringify(indshare));
										shareStr += indshare.name+"、";
										indsharet1--;
										if(indsharet1<0){
											shareStr = shareStr.slice(0,shareStr.length-1);
											alertDataList_ui_item_innerHTML += "等3人、";
										}
									}
									shareStr = shareStr.slice(0,shareStr.length-1);
									alertDataList_ui_item_innerHTML += shareStr;
									alertDataList_ui_item_innerHTML +='</h6>';
									// console.log(alertDataList_ui_item_innerHTML);
								}

								alertDataList_ui_item_innerHTML +='<p>'+_alertData.desc + '</p>'+'</div></figcaption>';
								alertDataList_ui_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
								// TODO: 判断权限
								if(user.id == _alertData.creater || user.id == _alertData.owner || user.role == 1){
									alertDataList_ui_item_innerHTML += '<button type="submit" class="btn btn-success btn-sm" onclick="';
									alertDataList_ui_item_innerHTML += 'shareItemAction(\''+_alertData.id+'\')">Share</button>';
								
									alertDataList_ui_item_innerHTML += '<button data-repeater-delete type="button" class="btn btn-danger btn-sm icon-btn ml-2" onclick="';
									alertDataList_ui_item_innerHTML += 'deleteItemAction(\''+_alertData.id+'\')">';
									alertDataList_ui_item_innerHTML += 'Delete';//'<i class="mdi mdi-delete"></i>';
									alertDataList_ui_item_innerHTML += '</button>';
									
									alertDataList_ui_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2" onclick="';
									alertDataList_ui_item_innerHTML += 'editItemAction(\''+_alertData.id+'\')">';
									alertDataList_ui_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
									alertDataList_ui_item_innerHTML += '</button>';
								}

								alertDataList_ui_item_innerHTML += '</div></figure></div>';
								 // console.log(alertDataList_ui_item_innerHTML);
								alertDataList_ui_innerHTML = alertDataList_ui_innerHTML
										+ alertDataList_ui_item_innerHTML;
							}
						});
					}
						
	
	alertDataList_ui.innerHTML = alertDataList_ui_innerHTML;

}
