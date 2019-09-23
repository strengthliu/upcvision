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
var actionType = _routeType;//"lineAlertData";

function editItemAction(itemId) {
	// console.log(itemId);
// alert(itemId);
	itemID = itemId;
	actionType = "lineAlertData";
	var _lineAlertData = userSpace.lineAlertData[itemId];
	if(user.id == _lineAlertData.creater || user.id == _lineAlertData.owner || user.role == 1){
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
		url : "deleteLineAlertDataGroup",
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
				var lineAlertDataId = data.data.data;
				fixLocalLineAlertDataList_Delete(lineAlertDataId);
				if(data.refresh) routeTo('linalertdataList','');
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
	shareType = _routeType;//"lineAlertData";
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

	console.log("linalertdata.js => submitNewDataItem 1 "+targetName +"  "+targetDesc);
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
//	rule.relativetime = relativetime;
//	rule.starttime = starttime;
//	rule.terminaltime = terminaltime;
	console.log("otherrule = "+JSON.stringify(rule));
	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc,'id':itemID,'rule':JSON.stringify(rule)};

//	console.log("linalertdata.js => submitNewDataItem 2");
//	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc,'id':itemID};
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
				// console.log("server info : "+JSON.stringify(data.data.data));
				console.log("linalertdata.js => submitNewDataItem 3");
				var lineAlertData = data.data.data;
				$('#newItemAction_mid').modal('hide');
				fixLocalLineAlertDataList(lineAlertData);
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
			console.log("linalertdata.js => submitNewDataItem 4");
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
			console.log("linalertdata.js => submitNewDataItem 5");
			hideLoading();
		}
	});

}


/**
 * 修改左侧实时数据列表,删除一个数据
 * 
 * @param lineAlertData
 * @returns
 */
function fixLocalLineAlertDataList_Delete(lineAlertDataId){
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalLineAlertDataList_Delete);
		return;
	}
	if(lineAlertDataId !=null && lineAlertDataId !="undefined"){
		var lineAlertData = userSpace.lineAlertData;
		// console.log("lineAlertDataId="+JSON.stringify(lineAlertDataId));
		// console.log(JSON.stringify(lineAlertData[lineAlertDataId]));
		delete lineAlertData[lineAlertDataId];
		delete userSpace.lineAlertData[lineAlertDataId];
		// _.omit(lineAlertData, [lineAlertDataId]);
		// console.log(JSON.stringify(lineAlertData[lineAlertDataId]));
		userSpace.lineAlertData = lineAlertData;
		// console.log("after delete lineAlertData =>
		// "+JSON.stringify(userSpace.lineAlertData));
		
		updateLineAlertData();
		updateLineAlertDataListFrame();
		cancel11();
		return;
	}
	
	
}

/**
 * 修改左侧实时数据列表,增加一个新数据
 * 
 * @param lineAlertData
 * @returns
 */
function fixLocalLineAlertDataList(lineAlertData){
	// $('#newItemAction_mid').modal('hide');
	console.log("fixLocalLineAlertDataList 1");
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalLineAlertDataList);
		cancel11();
		return;
	}
	if(lineAlertData.owner !=null && lineAlertData.owner !="undefined"){
		userSpace.lineAlertData[lineAlertData.id]=lineAlertData;
		// lineAlertDataList = lineAlertDataList.lineAlertData;
		
		updateLineAlertData();
		updateLineAlertDataListFrame();
		cancel11();
		return;
	}
}

updateLineAlertDataListFrame();

function updateLineAlertDataListFrame(){
	console.log("_routeID = " + _routeID);
	var _lineAlertDatas;
	// TODO: 如果key为空，就是异常，待处理。
	if (_routeID == null || _routeID == "undefined")
		_routeID = "";
	
	if (_routeID.trim() == 'unclassify') {
		alert(_routeID);
		_routeID = "";
		_lineAlertDatas = userSpace.lineAlertData[""];
	} else {
		_lineAlertDatas = userSpace.lineAlertData;
	}
	
	// 如果当前主页面不是实时数据这页，就不刷新了
	var lineAlertDataList_ui = document.getElementById("lineAlertDataList_ui");
	if(lineAlertDataList_ui==null || lineAlertDataList_ui=="undefined") return;

	var lineAlertDataList_ui_innerHTML = "";
	if (_lineAlertDatas != null && _lineAlertDatas != "undefined") {
		Object
				.keys(_lineAlertDatas)
				.forEach(
						function(key) {
							var _lineAlertData = _lineAlertDatas[key];
							if(_lineAlertData!=null && _lineAlertData!="undefined"){
								var lineAlertDataList_ui_item_innerHTML = '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12">';
								lineAlertDataList_ui_item_innerHTML += '<figure class="effect-text-in">';
								lineAlertDataList_ui_item_innerHTML += '<img src="'
										+ _lineAlertData.img + '" alt="image" ';
								
								lineAlertDataList_ui_item_innerHTML += 'onclick="routeTo('
									+ "'"
									+ "linalertdataDetail','"
									+ _lineAlertData.id + "'" + ')"/>';
								lineAlertDataList_ui_item_innerHTML += '<figcaption onclick="routeTo('
									+ "'"
									+ "linalertdataDetail','"
									+ _lineAlertData.id + "'" + ')"><h4>'
										+ _lineAlertData.name
										+ '</h4><div>'
										+'<h5></h5>'
										+'<h5>创建者：'+_lineAlertData.createrUser.name + '</h5>';
								var shareStr = "";
								if(_lineAlertData.sharedUsers.length>0){
									shareStr += '<h6>'+'分享给了 : ';
									var indsharet1 = 3;
									var indsharet2 = _lineAlertData.sharedUsers.length;
									var shareUsers = _lineAlertData.sharedUsers;
									for(var iindshare=0;iindshare<shareUsers.length;iindshare++){
										var indshare = shareUsers[iindshare];
										// console.log(JSON.stringify(indshare));
										shareStr += indshare.name+"、";
										indsharet1--;
										if(indsharet1<0){
											shareStr = shareStr.slice(0,shareStr.length-1);
											lineAlertDataList_ui_item_innerHTML += "等3人、";
										}
									}
									shareStr = shareStr.slice(0,shareStr.length-1);
									lineAlertDataList_ui_item_innerHTML += shareStr;
									lineAlertDataList_ui_item_innerHTML +='</h6>';
									// console.log(lineAlertDataList_ui_item_innerHTML);
								}

								lineAlertDataList_ui_item_innerHTML +='<p>'+_lineAlertData.desc + '</p>'+'</div></figcaption>';
								lineAlertDataList_ui_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
								// TODO: 判断权限
								if(user.id == _lineAlertData.creater || user.id == _lineAlertData.owner || user.role == 1){
									lineAlertDataList_ui_item_innerHTML += '<button type="submit" class="btn btn-success btn-sm" onclick="';
									lineAlertDataList_ui_item_innerHTML += 'shareItemAction(\''+_lineAlertData.id+'\')">Share</button>';
								
									lineAlertDataList_ui_item_innerHTML += '<button data-repeater-delete type="button" class="btn btn-danger btn-sm icon-btn ml-2" onclick="';
									lineAlertDataList_ui_item_innerHTML += 'deleteItemAction(\''+_lineAlertData.id+'\')">';
									lineAlertDataList_ui_item_innerHTML += '<i class="mdi mdi-delete"></i>';
									lineAlertDataList_ui_item_innerHTML += '</button>';
									
									lineAlertDataList_ui_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2" onclick="';
									lineAlertDataList_ui_item_innerHTML += 'editItemAction(\''+_lineAlertData.id+'\')">';
									lineAlertDataList_ui_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
									lineAlertDataList_ui_item_innerHTML += '</button>';
								}

								lineAlertDataList_ui_item_innerHTML += '</div></figure></div>';
								 // console.log(lineAlertDataList_ui_item_innerHTML);
								lineAlertDataList_ui_innerHTML = lineAlertDataList_ui_innerHTML
										+ lineAlertDataList_ui_item_innerHTML;
							}
						});
					}
						
	
	lineAlertDataList_ui.innerHTML = lineAlertDataList_ui_innerHTML;

}
