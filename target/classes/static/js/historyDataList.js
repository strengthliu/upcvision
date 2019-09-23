/**
 * 
 */

/**
 * 悬浮菜单功能
 */
// 新建
function newItemAction() {
	if(user.id == 2 || user.role == 1){
	// alert("xyGraphList.newItemAction");
		$('#newItemAction_mid').modal('show');
	}else {
		alert("您没有权限进行新建操作。");
	}
}

//_routeType = diagram;
//_routeID = key;

var itemID = _routeID;
var actionType = _routeType;//"historyData";

function editItemAction(itemId) {
	// console.log(itemId);
// alert(itemId);
	itemID = itemId;
	actionType = "historyData";
	var _historyData = userSpace.historyData[itemId];
	if(user.id == _historyData.creater || user.id == _historyData.owner || user.role == 1){
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
		url : "deleteHistoryDataGroup",
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
				var historyDataId = data.data.data;
				fixLocalHistoryDataList_Delete(historyDataId);
				if(data.refresh) routeTo('historydataList','');
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
	shareType = _routeType;//"historyData";
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
function submitNewDataItem(selectedPoints,_selectedPoints, targetName, targetDesc,relativetime, starttime, terminaltime){
	console.log("historydata.js => submitNewDataItem 1 "+targetName +"  "+targetDesc);
	var selectPointArray = new Array();
	var i__ = 0;
	for (let e of selectedPoints) {
		selectPointArray[i__] = e;
		i__++;
	}

	console.log(""+JSON.stringify(_selectedPoints));
	console.log("historydata.js => submitNewDataItem 2");
	var rule = new Object();
	rule._selectedPoints = _selectedPoints;
	rule.relativetime = relativetime;
	rule.starttime = starttime;
	rule.terminaltime = terminaltime;
	console.log("otherrule = "+JSON.stringify(rule));
	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc,'id':itemID,'rule':rule};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "newHistoryDataGroup",
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
				console.log("historydata.js => submitNewDataItem 3");
				var historyData = data.data.data;
				$('#newItemAction_mid').modal('hide');
				fixLocalHistoryDataList(historyData);
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
			console.log("historydata.js => submitNewDataItem 4");
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
			console.log("historydata.js => submitNewDataItem 5");
			hideLoading();
		}
	});

}


/**
 * 修改左侧实时数据列表,删除一个数据
 * 
 * @param historyData
 * @returns
 */
function fixLocalHistoryDataList_Delete(historyDataId){
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalHistoryDataList_Delete);
		return;
	}
	if(historyDataId !=null && historyDataId !="undefined"){
		var historyData = userSpace.historyData;
		// console.log("historyDataId="+JSON.stringify(historyDataId));
		// console.log(JSON.stringify(historyData[historyDataId]));
		delete historyData[historyDataId];
		delete userSpace.historyData[historyDataId];
		// _.omit(historyData, [historyDataId]);
		// console.log(JSON.stringify(historyData[historyDataId]));
		userSpace.historyData = historyData;
		// console.log("after delete historyData =>
		// "+JSON.stringify(userSpace.historyData));
		
		updateHistoryData();
		updateHistoryDataListFrame();
		cancel11();
		return;
	}
	
	
}

/**
 * 修改左侧实时数据列表,增加一个新数据
 * 
 * @param historyData
 * @returns
 */
function fixLocalHistoryDataList(historyData){
	// $('#newItemAction_mid').modal('hide');
	console.log("fixLocalHistoryDataList 1");
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalHistoryDataList);
		cancel11();
		return;
	}
	if(historyData.owner !=null && historyData.owner !="undefined"){
		userSpace.historyData[historyData.id]=historyData;
		// historyDataList = historyDataList.historyData;
		
		updateHistoryData();
		updateHistoryDataListFrame();
		cancel11();
		return;
	}
}

updateHistoryDataListFrame();

function updateHistoryDataListFrame(){
	console.log("_routeID = " + _routeID);
	var _historyDatas;
	// TODO: 如果key为空，就是异常，待处理。
	if (_routeID == null || _routeID == "undefined")
		_routeID = "";
	
	if (_routeID.trim() == 'unclassify') {
		alert(_routeID);
		_routeID = "";
		_historyDatas = userSpace.historyData[""];
	} else {
		_historyDatas = userSpace.historyData;
	}
	
	// 如果当前主页面不是实时数据这页，就不刷新了
	var historyDataList_ui = document.getElementById("historyDataList_ui");
	if(historyDataList_ui==null || historyDataList_ui=="undefined") return;

	var historyDataList_ui_innerHTML = "";
	if (_historyDatas != null && _historyDatas != "undefined") {
		Object
				.keys(_historyDatas)
				.forEach(
						function(key) {
							var _historyData = _historyDatas[key];
							if(_historyData!=null && _historyData!="undefined"){
								var historyDataList_ui_item_innerHTML = '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12">';
								historyDataList_ui_item_innerHTML += '<figure class="effect-text-in">';
								historyDataList_ui_item_innerHTML += '<img src="'
										+ _historyData.img + '" alt="image" ';
								
								historyDataList_ui_item_innerHTML += 'onclick="routeTo('
									+ "'"
									+ "historydataDetail','"
									+ _historyData.id + "'" + ')"/>';
								historyDataList_ui_item_innerHTML += '<figcaption onclick="routeTo('
									+ "'"
									+ "historydataDetail','"
									+ _historyData.id + "'" + ')"><h4>'
										+ _historyData.name
										+ '</h4><div>'
										+'<h5></h5>'
										+'<h5>创建者：'+_historyData.createrUser.name + '</h5>';
								var shareStr = "";
								if(_historyData.sharedUsers.length>0){
									shareStr += '<h6>'+'分享给了 : ';
									var indsharet1 = 3;
									var indsharet2 = _historyData.sharedUsers.length;
									var shareUsers = _historyData.sharedUsers;
									for(var iindshare=0;iindshare<shareUsers.length;iindshare++){
										var indshare = shareUsers[iindshare];
										// console.log(JSON.stringify(indshare));
										shareStr += indshare.name+"、";
										indsharet1--;
										if(indsharet1<0){
											shareStr = shareStr.slice(0,shareStr.length-1);
											historyDataList_ui_item_innerHTML += "等3人、";
										}
									}
									shareStr = shareStr.slice(0,shareStr.length-1);
									historyDataList_ui_item_innerHTML += shareStr;
									historyDataList_ui_item_innerHTML +='</h6>';
									// console.log(historyDataList_ui_item_innerHTML);
								}

								historyDataList_ui_item_innerHTML +='<p>'+_historyData.desc + '</p>'+'</div></figcaption>';
								historyDataList_ui_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
								// TODO: 判断权限
								if(user.id == _historyData.creater || user.id == _historyData.owner || user.role == 1){
									historyDataList_ui_item_innerHTML += '<button type="submit" class="btn btn-success btn-sm" onclick="';
									historyDataList_ui_item_innerHTML += 'shareItemAction(\''+_historyData.id+'\')">Share</button>';
								
									historyDataList_ui_item_innerHTML += '<button data-repeater-delete type="button" class="btn btn-danger btn-sm icon-btn ml-2" onclick="';
									historyDataList_ui_item_innerHTML += 'deleteItemAction(\''+_historyData.id+'\')">';
									historyDataList_ui_item_innerHTML += '<i class="mdi mdi-delete"></i>';
									historyDataList_ui_item_innerHTML += '</button>';
									
									historyDataList_ui_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2" onclick="';
									historyDataList_ui_item_innerHTML += 'editItemAction(\''+_historyData.id+'\')">';
									historyDataList_ui_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
									historyDataList_ui_item_innerHTML += '</button>';
								}

								historyDataList_ui_item_innerHTML += '</div></figure></div>';
								 // console.log(historyDataList_ui_item_innerHTML);
								historyDataList_ui_innerHTML = historyDataList_ui_innerHTML
										+ historyDataList_ui_item_innerHTML;
							}
						});
					}
						
	
	historyDataList_ui.innerHTML = historyDataList_ui_innerHTML;

}
