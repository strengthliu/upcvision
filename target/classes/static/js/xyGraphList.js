/**
 * 
 */

/**
 * 悬浮菜单功能
 */
// 新建
function newItemAction() {
	if(user.id == _realtimeData.creater || user.id == _realtimeData.owner || user.role == 1){

	// alert("xyGraphList.newItemAction");
		$('#newItemAction_mid').modal('show');
	}else {
		alert("您没有权限进行新建操作。");
	}
	
}

//_routeType = diagram;
//_routeID = key;

var itemID = _routeID;
var actionType = _routeType;//"xyGraph";

function editItemAction(itemId) {
	// console.log(itemId);
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
		url : "deleteXYGraphGroup",
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
				var xyGraphId = data.data.data;
				fixLocalXYGraphList_Delete(xyGraphId);
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

var dataItemId;
function shareItemAction(itemId) {
	dataItemId = itemId;
	shareType = _routeType;//"xyGraph";
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

	console.log("realtimedata.js => submitNewDataItem 1 "+targetName +"  "+targetDesc);
	var selectPointArray = new Array();
	var i__ = 0;
	for (let e of selectedPoints) {
		selectPointArray[i__] = e;
		i__++;
		}

	console.log("realtimedata.js => submitNewDataItem 2");
	var data={'uid':uid,'token':token,'points':selectPointArray,'name':targetName,'desc':targetDesc,'id':itemID};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "newXYGraphGroup",
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
				console.log("realtimedata.js => submitNewDataItem 3");
				var xyGraph = data.data.data;
				$('#newItemAction_mid').modal('hide');
				fixLocalXYGraphList(xyGraph);
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
 * @param xyGraph
 * @returns
 */
function fixLocalXYGraphList_Delete(xyGraphId){
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalXYGraphList_Delete);
		return;
	}
	if(xyGraphId !=null && xyGraphId !="undefined"){
		var xyGraph = userSpace.xyGraph;
		// console.log("xyGraphId="+JSON.stringify(xyGraphId));
		// console.log(JSON.stringify(xyGraph[xyGraphId]));
		delete xyGraph[xyGraphId];
		delete userSpace.xyGraph[xyGraphId];
		// _.omit(xyGraph, [xyGraphId]);
		// console.log(JSON.stringify(xyGraph[xyGraphId]));
		userSpace.xyGraph = xyGraph;
		// console.log("after delete xyGraph =>
		// "+JSON.stringify(userSpace.xyGraph));
		
		updateXYGraph();
		updateXYGraphListFrame();
		cancel11();
		return;
	}
	
	
}

/**
 * 修改左侧实时数据列表,增加一个新数据
 * 
 * @param xyGraph
 * @returns
 */
function fixLocalXYGraphList(xyGraph){
	// $('#newItemAction_mid').modal('hide');
	console.log("fixLocalXYGraphList 1");
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalXYGraphList);
		cancel11();
		return;
	}
	if(xyGraph.owner !=null && xyGraph.owner !="undefined"){
		userSpace.xyGraph[xyGraph.id]=xyGraph;
		// xyGraphList = xyGraphList.xyGraph;
		
		updateXYGraph();
		updateXYGraphListFrame();
		cancel11();
		return;
	}
}

updateXYGraphListFrame();

function updateXYGraphListFrame(){
	console.log("_routeID = " + _routeID);
	var _realtimeDatas;
	// TODO: 如果key为空，就是异常，待处理。
	if (_routeID == null || _routeID == "undefined")
		_routeID = "";
	
	if (_routeID.trim() == 'unclassify') {
		alert(_routeID);
		_routeID = "";
		_realtimeDatas = userSpace.xyGraph[""];
	} else {
		_realtimeDatas = userSpace.xyGraph;
	}
	
	// 如果当前主页面不是实时数据这页，就不刷新了
	var xyGraphList_ui = document.getElementById("xyGraphList_ui");
	if(xyGraphList_ui==null || xyGraphList_ui=="undefined") return;

	var xyGraphList_ui_innerHTML = "";
	if (_realtimeDatas != null && _realtimeDatas != "undefined") {
		Object
				.keys(_realtimeDatas)
				.forEach(
						function(key) {
							var _realtimeData = _realtimeDatas[key];
							if(_realtimeData!=null && _realtimeData!="undefined"){
								var xyGraphList_ui_item_innerHTML = '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12">';
								xyGraphList_ui_item_innerHTML += '<figure class="effect-text-in">';
								xyGraphList_ui_item_innerHTML += '<img src="'
										+ _realtimeData.img + '" alt="image" ';
								
								xyGraphList_ui_item_innerHTML += 'onclick="routeTo('
									+ "'"
									+ "realtimedataDetail','"
									+ _realtimeData.id + "'" + ')"/>';
								xyGraphList_ui_item_innerHTML += '<figcaption onclick="routeTo('
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
											xyGraphList_ui_item_innerHTML += "等3人、";
										}
									}
									shareStr = shareStr.slice(0,shareStr.length-1);
									xyGraphList_ui_item_innerHTML += shareStr;
									xyGraphList_ui_item_innerHTML +='</h6>';
									// console.log(xyGraphList_ui_item_innerHTML);
								}

								xyGraphList_ui_item_innerHTML +='<p>'+_realtimeData.desc + '</p>'+'</div></figcaption>';
								xyGraphList_ui_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
								// TODO: 判断权限
								if(user.id == _realtimeData.creater || user.id == _realtimeData.owner || user.role == 1){
									xyGraphList_ui_item_innerHTML += '<button type="submit" class="btn btn-success btn-sm" onclick="';
									xyGraphList_ui_item_innerHTML += 'shareItemAction(\''+_realtimeData.id+'\')">Share</button>';
								
									xyGraphList_ui_item_innerHTML += '<button data-repeater-delete type="button" class="btn btn-danger btn-sm icon-btn ml-2" onclick="';
									xyGraphList_ui_item_innerHTML += 'deleteItemAction(\''+_realtimeData.id+'\')">';
									xyGraphList_ui_item_innerHTML += '<i class="mdi mdi-delete"></i>';
									xyGraphList_ui_item_innerHTML += '</button>';
									
									xyGraphList_ui_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2" onclick="';
									xyGraphList_ui_item_innerHTML += 'editItemAction(\''+_realtimeData.id+'\')">';
									xyGraphList_ui_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
									xyGraphList_ui_item_innerHTML += '</button>';
								}

								xyGraphList_ui_item_innerHTML += '</div></figure></div>';
								 // console.log(xyGraphList_ui_item_innerHTML);
								xyGraphList_ui_innerHTML = xyGraphList_ui_innerHTML
										+ xyGraphList_ui_item_innerHTML;
							}
						});
					}
						
	
	xyGraphList_ui.innerHTML = xyGraphList_ui_innerHTML;

}
