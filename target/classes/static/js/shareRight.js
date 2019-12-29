/**
 * 
 */
var itemId,shareType;
var dataItem;
var shareURL;

function setParameter(_shareType,_itemId){
	_setParameter(_shareType,_itemId);
	$("#shareItemToUser").jsGrid("loadData", null);
	$("#shareItemToDepart").jsGrid("loadData", null);
}
function _setParameter(_shareType,_itemId){
	itemId = _itemId;
	shareType = _shareType;
	switch(shareType){
	case 'xydiagramlist'.toLowerCase():
		dataItem = userSpace.xyGraph[itemId];
		shareURL = "shareRightXYGraph";
		break;
	case 'diagramList'.toLowerCase():
//		_galleryKey = key;
//		_routeID = key;
//		_graphId = graphId;
		console.log("diagramList 1");
		dataItem = getGraphByID(userSpace.graph,itemId);
		shareURL = "shareRightGraph";
		console.log("diagramList 2");
		break;
	case 'realtimedataList'.toLowerCase():
		dataItem = userSpace.realTimeData[itemId];
		shareURL = "shareRightRealTimeData";
		break;
	case 'alertdataList'.toLowerCase():
		dataItem = userSpace.alertData[itemId];
		shareURL = "shareRightAlertData";
		break;
	case 'historydataList'.toLowerCase():
		dataItem = userSpace.historyData[itemId];
		shareURL = "shareRightHistoryData";
		break;
	case 'lineAlertdataList'.toLowerCase():
		dataItem = userSpace.lineAlertData[itemId];
		shareURL = "shareRightLineAlertData";
		break;
	}
}

(function($) {
	'use strict';
	$(function() {
		var _depart = departdata;
		// basic config
		console.log("222-" + JSON.stringify(departdata));
		if ($("#shareItemToUser").length) {
			$("#shareItemToUser").jsGrid({
								height : "400px",
								width : "100%",
								filtering : true,
								editing : false,
								// inserting : true,
								sorting : true,
								paging : true,
								autoload : true,
								pageSize : 8,
								pageButtonCount : 5,
								rowClick : function(args) {
									updateShareToUser(args.item,dataItem);
								},
								controller : {
									loadData : function(filter) {
										var d = $.Deferred();
										{
											 var uid = localStorage.user.id;
											 var token = localStorage.token;
											$
													.ajax(
															{
																type : "POST",
																url : "getUserList",
																data : {
																		uid : user.id,
																		token : localStorage.token
																	},
																datatype : "json"
															})
													.done(
															function(response) {
//																console.log("===> "+JSON.stringify(response));
																// 加载用户数据后，取当前的分享情况，修改显示数据。
																var _userInfoList = response.data.users;
																var sharedUsers;
																_setParameter(shareType,itemId);
																console.log("dataItem-> "+JSON.stringify(dataItem));
																if(dataItem!=null && dataItem!="undefined"){
																	sharedUsers = dataItem.sharedUsers;
																} else{
																	sharedUsers = [];
																}
//																console.log("userInfoList="+JSON.stringify(userInfoList));
//																if(dataItem!=null&&dataItem!="undefined")
//																	console.log("dataItem.sharedUsers="+JSON.stringify(dataItem.sharedUsers));
																// dataItem.sharedUsers=[]
																var userInfoList = new Array();
																Object.keys(_userInfoList).forEach(function(key){
																	var _user = _userInfoList[key];
//																	console.log("user="+JSON.stringify(_user));
																	var _sharedUser ;
																	_sharedUser = sharedUsers.find(element => element.id==_user.id);
//																	console.log("_sharedUser="+JSON.stringify(_sharedUser));
																	// _sharedUser=undefined
																	if(_sharedUser!=null && _sharedUser!="undefined"){
																		_userInfoList[key]['shared']=true;
																	}else{
																		_userInfoList[key]['shared']=false;
																	}
//																	console.log("user="+JSON.stringify(_user));
																	// user={"id":2,"name":"admin","department":"1","shared":false}
																	userInfoList.push(_user);
																});
																var _userInfoList = $
																		.grep(
																				userInfoList,
																				function(
																						client) {
																					return (!filter.name || client.name
																							.indexOf(filter.name) > -1)
																							&& (!filter.userName || client.userName
																									.indexOf(filter.userName) > -1)
																							&& (!filter.depart || client.depart
																									.indexOf(filter.depart) > -1)
																							&& (!filter.role || client.role === filter.role);
																				});
//																console.log("_userInfoList = "+JSON.stringify(_userInfoList));

																d
																		.resolve(_userInfoList);
															});
										}
										return d.promise();
									},
								},
								fields : [ {
									title : "是否分享",
									editing : false,
									name : "shared",
									type : "checkbox",
									width : 70
								}, {
									title : "用户名",
									editing : false,
									name : "name",
									type : "text",
									width : 70
								}, {
									title : "部门",
									editing : false,
									name : "department",
									type : "select",
									items : departdata,
									valueField : "id",
									textField : "departname",
									width : 100
								} ]
							});
		}
	});
})(jQuery);
var _dialogType;

(function($) {
	'use strict';
	$(function() {
		// basic config
		if ($("#shareItemToDepart").length) {
			$("#shareItemToDepart")
					.jsGrid(
							{
								height : "400px",
								width : "100%",
								filtering : true,
								editing : false,
								// inserting : true,
								deleting : false,
								sorting : true,
								paging : true,
								autoload : true,
								rowClick : function(args) {
									updateShareToDepart(args.item,dataItem);
								},
								controller : {
									loadData : function(filter) {
										var d = $.Deferred();
										{
											var data = {
												'uid' : user.id + "",
												'token' : token
											};
											$
													.ajax(
															{
																type : "POST",
																url : "getDepartList",
																data : JSON
																		.stringify(data),
																contentType : "application/json",
																// 返回数据的格式
																datatype : "json",
															})
													.done(
															function(response) {
																var departList = response.data.departs;
																// 加载用户数据后，取当前的分享情况，修改显示数据。
																var _userInfoList = response.data.departs;
																var sharedUsers;
																_setParameter(shareType,itemId);
																console.log("dataItem-> "+JSON.stringify(dataItem));

																if(dataItem!=null && dataItem!="undefined"){
																	sharedUsers = dataItem.sharedDepartment;
																} else{
																	sharedUsers = [];
																}
																var userInfoList = new Array();
																Object.keys(_userInfoList).forEach(function(key){
																	var _user = _userInfoList[key];
																	var _sharedUser;
																	if(sharedUsers!=null&&sharedUsers!="undefined")
																	_sharedUser = sharedUsers.find(element => element.id==_user.id);
																	if(_sharedUser!=null && _sharedUser!="undefined"){
																		_userInfoList[key]['shared']=true;
																	}else{
																		_userInfoList[key]['shared']=false;
																	}
																	userInfoList.push(_user);
																});
																// 修改结果，将
																var _departList = $
																		.grep(
																				userInfoList,
																				function(client) {
																					return (!filter.departname || client.departname
																									.indexOf(filter.departname) > -1);
																				});
																d.resolve(_departList);
															});
										}
										return d.promise();
									}
								},
								fields : [
										{
											title : "是否分享",
											editing : false,
											type: "checkbox",
											name : "shared",
											width : 30
										}, {
											title : "部门名称",
											editing : false,
											name : "departname",
											type : "text",
											width : 70
										}
								]
							});
		}

	});
})(jQuery);



function updateShareToUser(item,dataItem) {
	var value = item.shared;
	// 根据value，把item加入或减出dataItem
	var data;
	var sharedUsers = dataItem.sharedUsers;
	var sharedDepartment = dataItem.sharedDepartment;
	var userIds = new Array();
	var departIds = new Array();
	// 提交分享数据
	if(!value){ // 增加一个分享用户
		for(var ind=0;ind<sharedUsers.length;ind++){
			var sharedUser = sharedUsers[ind];
			userIds.push(sharedUser.id);
		}
		userIds.push(item.id);
		if(sharedDepartment!=null&& sharedDepartment!="undefined")
		for(var ind=0;ind<sharedDepartment.length;ind++){
			departIds.push(sharedDepartment[ind].id);
		}
		data={'uid':uid,'token':token,'id':dataItemId,'userIds':userIds,'departIds':departIds};
	} else {
		for(var ind=0;ind<sharedUsers.length;ind++){
			var sharedUser = sharedUsers[ind];
			if(sharedUser.id!=item.id)
				userIds.push(sharedUser.id);
		}
		if(sharedDepartment!=null&& sharedDepartment!="undefined")
		for(var ind=0;ind<sharedDepartment.length;ind++){
			var sharedDepartmentItem = sharedDepartment[ind];
//			if(sharedDepartmentItem.id != item.id)
			departIds.push(sharedDepartmentItem.id);
		}
		data={'uid':uid,'token':token,'id':dataItemId,'userIds':userIds,'departIds':departIds};
	}

	$.ajax({
				type : "POST",
				url : shareURL,
				data: JSON.stringify(data),
				contentType : "application/json",
				datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
				beforeSend : function() {
					showLoading();
				},
				success : function(data) {
					if (data.status == "000"){ //GlobalConsts.ResultCode_SUCCESS) {
						var ddd = data.data.data;
						switch(shareType){
						case 'xydiagramlist'.toLowerCase():
							userSpace.xyGraph[ddd.id]=ddd;
							break;
						case 'diagramList'.toLowerCase():
						case 'diagramList'.toLowerCase():
//							console.log("updateRight return -> "+JSON.stringify(ddd));
//							console.log("updateRight userSpace.graph -> "+JSON.stringify(userSpace.graph));
							var _g = getGraphByID(userSpace.graph,ddd.id);
//							console.log("target graph-> "+JSON.stringify(_g));
							_g.shareddepart = ddd.shareddepart;
							_g.sharedDepartment = ddd.sharedDepartment;
//							userSpace.graph[ddd.id]=ddd;
							break;
						case 'realtimedataList'.toLowerCase():
							userSpace.realTimeData[ddd.id]=ddd;
							break;
						case 'alertdataList'.toLowerCase():
							userSpace.alertData[ddd.id]=ddd;
							break;
						case 'historydataList'.toLowerCase():
							userSpace.historyData[ddd.id]=ddd;
							break;
						case 'lineAlertdataList'.toLowerCase():
							userSpace.lineAlertData[ddd.id]=ddd;
							break;
						}
						$("#shareItemToUser").jsGrid("loadData", {});
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

function updateShareToDepart(item,dataItem) {
	var value = item.shared;
	console.log("updateShareToUser");
	// 根据value，把item加入或减出dataItem
//	console.log("updateShart -》 "+JSON.stringify(item)+"  value="+value+" dataItem-> "+JSON.stringify(dataItem));
	var data;
	var sharedUsers = dataItem.sharedUsers;
	var sharedDepartment = dataItem.sharedDepartment;
	var userIds = new Array();
	var departIds = new Array();
	// 提交分享数据
	if(!value){
		for(var ind=0;ind<sharedUsers.length;ind++){
			var sharedUser = sharedUsers[ind];
			userIds.push(sharedUser.id);
		}
		 // 增加一个分享部门
		if(sharedDepartment!=null&& sharedDepartment!="undefined")
		for(var ind=0;ind<sharedDepartment.length;ind++){
			departIds.push(sharedDepartment[ind].id);
		}
		departIds.push(item.id);
		data={'uid':uid,'token':token,'id':dataItemId,'userIds':userIds,'departIds':departIds};
	} else {
		for(var ind=0;ind<sharedDepartment.length;ind++){
			var _sharedDepartment = sharedDepartment[ind];
			if(_sharedDepartment.id!=item.id)
				departIds.push(_sharedDepartment.id);
		}
		if(sharedUsers!=null&& sharedUsers!="undefined")
		for(var ind=0;ind<sharedUsers.length;ind++){
			var sharedUser = sharedUsers[ind];
//			if(sharedDepartmentItem.id != item.id)
			userIds.push(sharedUser.id);
		}
		data={'uid':uid,'token':token,'id':dataItemId,'userIds':userIds,'departIds':departIds};
	}
	$.ajax({
				type : "POST",
				url : shareURL,
				data: JSON.stringify(data),
				contentType : "application/json",
				datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
				beforeSend : function() {
					showLoading();
				},
				success : function(data) {
					if (data.status == "000"){ //GlobalConsts.ResultCode_SUCCESS) {
//						 console.log("server info : "+JSON.stringify(data.data.data));
						var ddd = data.data.data;
						
						switch(shareType){
						case 'xydiagramlist'.toLowerCase():
							userSpace.xyGraph[ddd.id]=ddd;
							break;
						case 'diagramList'.toLowerCase():
							console.log("updateRight return -> "+JSON.stringify(ddd));
							console.log("updateRight userSpace.graph -> "+JSON.stringify(userSpace.graph));
							var _g = getGraphByID(userSpace.graph,ddd.id);
							console.log("target graph-> "+JSON.stringify(_g));
							_g.shareddepart = ddd.shareddepart;
							_g.sharedDepartment = ddd.sharedDepartment;
//							userSpace.graph[ddd.id]=ddd;
							break;
						case 'realtimedataList'.toLowerCase():
							userSpace.realTimeData[ddd.id]=ddd;
							break;
						case 'alertdataList'.toLowerCase():
							userSpace.alertData[ddd.id]=ddd;
							break;
						case 'historydataList'.toLowerCase():
							userSpace.historyData[ddd.id]=ddd;
							break;
						case 'lineAlertdataList'.toLowerCase():
							userSpace.lineAlertData[ddd.id]=ddd;
							break;
						}
						console.log("updateRight loadData...");
						$("#shareItemToDepart").jsGrid("loadData", {});
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

//function loadUsers() {
//	console.log(0);
//	selectedUsers = new Set();
//	// 传入变量
//	if (dataItemId == null || dataItemId == "undefined") {
//		console.log(1);
//		alert("请选择要分享的内容。");
//		// return;
//	} else {
//		if (users == null || users == "undefined") {
//			console.log(2);
//			// getUserList(fillUserUI);
//		} else {
//			// fillUserUI(users);
//		}
//	}
//}
