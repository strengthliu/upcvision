/**
 * 
 */

/**
 * 悬浮菜单功能
 */
// 新建

var itemID = _routeID;
var actionType = _routeType;// "graph";

function newItemAction() {
	if(user.id == 2 || user.role == 1){
	// alert("xyGraphList.newItemAction");
		$('#newItemAction_mid').modal('show');
	}else {
		alert("您没有权限进行新建操作。");
	}
}

function editItemAction(itemId) {
	itemID = itemId;
	actionType = "graph";
	var _graph = userSpace.graph[itemId];
	if(user.role == 1 || user.id == _graph.creater || user.id == _graph.owner ){
			$('#newItemAction_mid').modal('show');
		}else {
			alert("您没有权限进行新建操作。");
			return;
		}
	editItem();
}

function editItem(itemId){
	console.log("itemId="+itemId);
}

function doShareActionToServer(){
	if (user == null || user == "undefined") {
		user = localStorage.user;
		uid = user.id;
		token = localStorage.token;
	}
	console.log("dataItemId="+dataItemId+"  user:"+JSON.stringify(Array.from(selectedUsers)));
	var data={'uid':uid,'token':token,'id':dataItemId,'userIds':Array.from(selectedUsers),'type':"graph"};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "shareRightGraph",
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
			if (data.status == "000"){ // GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : "+JSON.stringify(data.data.data));
				var graph = data.data.data;
				console.log("graph:=> "+JSON.stringify(graph));
				var _g = getGraphByID(userSpace.graph,graph.id);
				_g.shared = graph.shared;
				_g.sharedUsers = graph.sharedUsers;
				_g.owner = graph.owner;
				_g.ownerUser = graph.ownerUser;
				_g.creater = graph.creater;
				_g.createrUser = graph.createrUser;
				$('#shareItemAction_mid').modal('hide');
				updateGraphListFrame();
			} else {
				alert("失败 ： " + data.msg);
			}
			hideLoading();
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			hideLoading();
		}
	});
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
			if (data.status == "000"){ // GlobalConsts.ResultCode_SUCCESS) {
				var graphId = data.data.data;
				fixLocalGraphList_Delete(graphId);
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
	shareType = _routeType;// "graph";
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
			if (data.status == "000"){ // GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : "+JSON.stringify(data.data.data));
				console.log("historydata.js => submitNewDataItem 3");
				var graph = data.data.data;
				$('#newItemAction_mid').modal('hide');
				fixLocalGraphList(graph);
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
 * @param graph
 * @returns
 */
function fixLocalGraphList_Delete(graphId){
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalGraphList_Delete);
		return;
	}
	if(graphId !=null && graphId !="undefined"){
		var graph = userSpace.graph;
		// console.log("graphId="+JSON.stringify(graphId));
		// console.log(JSON.stringify(graph[graphId]));
		delete graph[graphId];
		delete userSpace.graph[graphId];
		// _.omit(graph, [graphId]);
		// console.log(JSON.stringify(graph[graphId]));
		userSpace.graph = graph;
		// console.log("after delete graph =>
		// "+JSON.stringify(userSpace.graph));
		
		updateHistoryData();
		updateGraphListFrame();
		cancel11();
		return;
	}
	
	
}

/**
 * 修改左侧实时数据列表,增加一个新数据
 * 
 * @param graph
 * @returns
 */
function fixLocalGraphList(graph){
	// $('#newItemAction_mid').modal('hide');
	console.log("fixLocalGraphList 1");
	if(userSpace==null || userSpace=="undefined"){
		getUserSpace(user.id,token,fixLocalGraphList);
		cancel11();
		return;
	}
	if(graph.owner !=null && graph.owner !="undefined"){
		userSpace.graph[graph.id]=graph;
		// graphList = graphList.graph;
		
		updateHistoryData();
		updateGraphListFrame();
		cancel11();
		return;
	}
}
var _graphs;
var step = 0;

function getGraphByID(graph,id,l){
	if(l==null||l=="undefined")
		l=0;
	l++;
	var level=l;
	step++;
// console.log(" ============ "+step+" === "+step+" === "+step+" === ");
	if(id==null||id=="undefined"){
		_graphs = graph;
		step ++;
// console.log("level="+level+" "+step+" = return path 1 : " + graph.name+"
// id="+graph.id);
		return graph;
	}
	if(graph.id == id) {
		_graphs = graph;
		step ++;
// console.log("level="+level+" "+step+" = 得到了结果 return path 2 : " +
// graph.name+" id="+graph.id);
		return graph;
	} 

	{
		var children = graph.children;
		if(children!=null){
			var ids = new Array();
			Object.keys(children).forEach(function(key){
				ids.push(children[key]);
			});
			
			for(var i=0;i<ids.length;i++){
				var r = getGraphByID(ids[i],id,level);
				if(r!=null) {
					step ++;
// console.log("level="+level+" "+step+" = 得到了结果 return path 3 : " +
// graph.name+" id="+graph.id);
					return r;
				}
			}
			step ++;
// console.log("level="+level+" "+step+" = null null 4 : " + graph.name+"
// id="+graph.id);
			return null;
		} 
		else 
		{
			step ++;
// console.log("level="+level+" "+step+" = null null 5 : " + graph.name+"
// id="+graph.id);
			return null;				
		}
	}
}


updateGraphListFrame();

function updateGraphListFrame(){
	var _galleryKey = _routeID;
//	console.log("_graphId= "+_graphId);

//	// TODO: 如果key为空，就是异常，待处理。
//	if (_galleryKey == null || _galleryKey == "undefined")
//		_galleryKey = "";
//
//	if (_galleryKey.trim() == 'unclassify') {
//		alert(_galleryKey);
//		_galleryKey = "";
//	}
	
	if(_graphId==null||_graphId=="undefined"){
		_graphs = userSpace.graph;
	} else{
		_graphs = getGraphByID(userSpace.graph,_graphId);
//		console.log("debug graph => " + JSON.stringify(_g_));
	}

	var children = _graphs.children;

	var diagram_gallery = document.getElementById("diagram_gallery");
	var diagram_gallery_innerHTML = "";
	if(children==null||children=="undefined"){
		
	}else{
		Object.keys(children).forEach(function(key){
//			console.log(key+" => "+JSON.stringify(children[key]));
			var _graph=children[key];
			var diagram_gallery_item_innerHTML = '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12"';
			if(_graph.file && _graph.svg){
				diagram_gallery_item_innerHTML += '>';
				diagram_gallery_item_innerHTML += '<figure class="effect-text-in">';
				diagram_gallery_item_innerHTML += '<div></div>';				
				diagram_gallery_item_innerHTML += '<img src="' + _graph.img + '" alt="image" ';
				diagram_gallery_item_innerHTML += ' onclick="routeTo(\'diagramDetail\',\'' + _graph.urlPath + '\',\''+_graph.id+'\');"/>';
// alert("_graph.urlPath = "+_graph.urlPath);
				diagram_gallery_item_innerHTML += '<figcaption';
				diagram_gallery_item_innerHTML += ' onclick="routeTo(\'diagramDetail\',\'' + _graph.urlPath + '\',\''+_graph.id+'\');">';

				diagram_gallery_item_innerHTML += '<h4 style="color:#FFFF00;background-color=#556B2F">'
						+ _graph.name +"  "+ '</h4><p>' + _graph.path
						+ '</p></figcaption>';
				diagram_gallery_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
				// TODO: 判断权限
				if(user.id == _graph.creater || user.id == _graph.owner || user.role == 1){
					diagram_gallery_item_innerHTML += '<button type="submit" class="btn btn-success btn-sm" onclick="';
					diagram_gallery_item_innerHTML += 'shareItemAction(\''+_graph.id+'\')">Share</button>';
					
//					diagram_gallery_item_innerHTML += '<button data-repeater-delete type="button" class="btn btn-danger btn-sm icon-btn ml-2" onclick="';
//					diagram_gallery_item_innerHTML += 'deleteItemAction(\''+_graph.id+'\')">';
//					diagram_gallery_item_innerHTML += '<i class="mdi mdi-delete"></i>';
//					diagram_gallery_item_innerHTML += '</button>';
					
					diagram_gallery_item_innerHTML += '<button data-repeater-create type="button" class="btn btn-info btn-sm icon-btn ml-2" onclick="';
					diagram_gallery_item_innerHTML += 'editItemAction(\''+_graph.id+'\')">';
					diagram_gallery_item_innerHTML += '<i class="mdi mdi-edit">Edit</i>';
					diagram_gallery_item_innerHTML += '</button>';
				}
				// 如果图的宽度大于可预览值，显示预览图形。
				// TODO: if()
				//diagram_gallery_item_innerHTML += '<button type="submit"  class="btn btn-info btn-sm icon-btn ml-2" onclick="';
				//diagram_gallery_item_innerHTML += 'previewGraph(\''+_graph.id+'\')">';
				//diagram_gallery_item_innerHTML += '<i class="mdi mdi-edit">Preview</i>';
				//diagram_gallery_item_innerHTML += '</button>';
				
				// --------------一个图形div结束
				diagram_gallery_item_innerHTML += '</div>';

			} else {
				diagram_gallery_item_innerHTML += ' onclick="routeTo(' + "'"
						+ "diagramList','" + _graph + "'" + ',\''+_graph.id+'\');">';
				diagram_gallery_item_innerHTML += '<figure class="effect-text-in">';
				diagram_gallery_item_innerHTML += '<img src="' + _graph.img + '" alt="image"/>';
				diagram_gallery_item_innerHTML += '<figcaption><h4 style="color:#FFFF00">'
						+ _graph.name +"  " + '</h4><p>' + _graph.path
						+ '</p></figcaption>';
						// console.log(diagram_gallery_item_innerHTML);
				
			}
			diagram_gallery_item_innerHTML += '</figure></div>';
	
			// console.log(diagram_gallery_item_innerHTML);
			diagram_gallery_innerHTML = diagram_gallery_innerHTML
					+ diagram_gallery_item_innerHTML;
	
		});
	}
	diagram_gallery.innerHTML = diagram_gallery_innerHTML;
}

function doUpLoad(){

	var formData=new FormData();
	var file=$("#graphThumbnail")[0].files[0];
	formData.append("file",file1);
	$.ajax({
	        url: 'uploadThumbnail',
	        type: 'POST',
	        cache:false,
	        processData: false,//不处理数据
	        data: formData,//直接把formData这个对象传过去
	        dataType : 'json',
	        contentType:false,// 主要设置你发送给服务器的格式,设为false代表不设置内容类型
	        success: function(data){
	          if(data.result=="success"){
	            alert("success");
	          }else{
	            alert("error");
	          }
	        }
	});
	console.log("doUpLoad 2");
	
	var formData1=new FormData();
	var file1=$("#graphFile")[0].files[0];
	formData1.append("file",file1);
	$.ajax({
	        url: 'uploadGraphFile',
	        type: 'POST',
	        cache:false,
	        processData: false,//不处理数据
	        data: formData1,//直接把formData这个对象传过去
	        dataType : 'json',
	        contentType:false,// 主要设置你发送给服务器的格式,设为false代表不设置内容类型
	        success: function(data){
	          if(data.result=="success"){
	            alert("success");
	          }else{
	            alert("error");
	          }
	        }
	});
	console.log("doUpLoad 3");

}
