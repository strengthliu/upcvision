/**
 * 
 */
console.log("userSpace => "+JSON.stringify(userSpace));
var itemId,shareType;
var dataItem;
function setParameter(_shareType,_itemId){
	_setParameter(_shareType,_itemId);
//	console.log("dataItem -> "+JSON.stringify(dataItem));
	$("#shareItemToUser").jsGrid("loadData", null);
	$("#shareItemToDepart").jsGrid("loadData", null);
}
function _setParameter(_shareType,_itemId){
	itemId = _itemId;
	shareType = _shareType;
	switch(shareType){
	case 'xydiagramlist'.toLowerCase():
		dataItem = userSpace.xyGraph[itemId];
		break;
	case 'diagramList'.toLowerCase():
		_galleryKey = key;
		_routeID = key;
		_graphId = graphId;

// dataItem = userSpace.xyGraph[itemId];
		break;
	case 'realtimedataList'.toLowerCase():
		dataItem = userSpace.realTimeData[itemId];
		break;
	case 'alertdataList'.toLowerCase():
		dataItem = userSpace.alertData[itemId];
		break;
	case 'historydataList'.toLowerCase():
		dataItem = userSpace.historyData[itemId];
		break;
	case 'lineAlertdataList'.toLowerCase():
		dataItem = userSpace.lineAlertData[itemId];
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
																					console.log("filter => "+JSON.stringify(filter));
																					console.log("client => "+JSON.stringify(client));

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
									console.log("rowClick args ->"+JSON.stringify(args));
									updateShareToDepart(args.item,dataItem);
								},
								controller : {
									loadData : function(filter) {
										console.log("loadData : function(filter) -filter = "+JSON.stringify(filter));

										console.log("shareItemToDepart -> loadData");
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
																console.log("===> "+JSON.stringify(response));
																// 加载用户数据后，取当前的分享情况，修改显示数据。
																var _userInfoList = response.data.departs;
																var sharedUsers;
																_setParameter(shareType,itemId);
																if(dataItem!=null && dataItem!="undefined"){
																	sharedUsers = dataItem.sharedDepartment;
																} else{
																	sharedUsers = [];
																}
																console.log("userInfoList="+JSON.stringify(_userInfoList));
																if(dataItem!=null&&dataItem!="undefined")
																	console.log("dataItem.sharedDepartment="+JSON.stringify(dataItem.sharedDepartment));
																// dataItem.sharedUsers=[]
																var userInfoList = new Array();
																Object.keys(_userInfoList).forEach(function(key){
																	var _user = _userInfoList[key];
																	console.log("user1="+JSON.stringify(_user));
																	var _sharedUser;
																	if(sharedUsers!=null&&sharedUsers!="undefined")
																	_sharedUser = sharedUsers.find(element => element.id==_user.id);
																	console.log("_sharedUser="+JSON.stringify(_sharedUser));
																	// _sharedUser=undefined
																	if(_sharedUser!=null && _sharedUser!="undefined"){
																		_userInfoList[key]['shared']=true;
																	}else{
																		_userInfoList[key]['shared']=false;
																	}
																	console.log("user2="+JSON.stringify(_user));
																	// user={"id":2,"name":"admin","department":"1","shared":false}
																	userInfoList.push(_user);
																});
																console.log("userInfoList => "+JSON.stringify(userInfoList));
																// 修改结果，将
																var _departList = $
																		.grep(
																				userInfoList,
																				function(client) {
																					console.log("filter => "+JSON.stringify(filter));
																					console.log("client => "+JSON.stringify(client));

																					return (!filter.departname || client.departname
																									.indexOf(filter.departname) > -1);
																				});
																console.log("_departList => "+JSON.stringify(_departList));
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

function loadUsers() {
	console.log(0);
	selectedUsers = new Set();
	// 传入变量
	if (dataItemId == null || dataItemId == "undefined") {
		console.log(1);
		alert("请选择要分享的内容。");
		// return;
	} else {

		if (users == null || users == "undefined") {
			console.log(2);
			// getUserList(fillUserUI);
		} else {
			// fillUserUI(users);
		}
	}
}
//
//
// var selectedUsers;
// function cancel(){
// selectedUsers = new Set();
// $('#shareItemAction_mid').modal('hide');
// }
// function loadUsers() {
// console.log(0);
// selectedUsers = new Set();
// // 传入变量
// if (dataItemId == null || dataItemId == "undefined") {
// console.log(1);
// alert("请选择要分享的内容。");
// // return;
// } else {
//
// if (users == null || users == "undefined") {
// console.log(2);
// getUserList(fillUserUI);
// } else {
// fillUserUI(users);
// }
// }
// }
//
// function doselectUser(id){
// var sid = "user_"+id;
// var uitem = document.getElementById(sid);
// var checked = uitem.checked;
// console.log("select "+id+" "+checked);
// if(checked)
// selectedUsers.add(id);
// else
// selectedUsers.delete(id);
// console.log("dataItemId="+dataItemId+"
// user:"+JSON.stringify(Array.from(selectedUsers)));
// }
//
// function fillUserUI(users) {
// if (userSpace == null || userSpace == "undefined") {
// console.log("设置共享时用户空间为空。");
// }
// var sharedUsers;
// console.log("shareRight.js -> fillUserUI => shareType="+shareType);
// switch (shareType) {
// case "realtimedatalist".toLowerCase():
// var dataItem = userSpace.realTimeData[dataItemId];
// if (dataItem == null || dataItem == "undefined") {
// alert("没有这个实时数据，请联系系统管理员。");
// return;
// }
// sharedUsers = dataItem.sharedUsers;
//
// break;
// case "alertdataList".toLowerCase():
// var dataItem = userSpace.alertData[dataItemId];
// if (dataItem == null || dataItem == "undefined") {
// alert("没有这个报警数据，请联系系统管理员。");
// return;
// }
// sharedUsers = dataItem.sharedUsers;
//
// break;
// case "diagramList".toLowerCase():
// var dataItem = getGraphByID(userSpace.graph,dataItemId);
// if (dataItem == null || dataItem == "undefined") {
// alert("没有这个图形，请联系系统管理员。");
// return;
// }
// sharedUsers = dataItem.sharedUsers;
//
// break;
// case "xydiagramList".toLowerCase():
// var dataItem = userSpace.xyGraph[dataItemId];
// if (dataItem == null || dataItem == "undefined") {
// alert("没有这个XY图数据，请联系系统管理员。");
// return;
// }
// sharedUsers = dataItem.sharedUsers;
//
// break;
// case "historydataList".toLowerCase():
// var dataItem = userSpace.historyData[dataItemId];
// if (dataItem == null || dataItem == "undefined") {
// alert("没有这个历史数据，请联系系统管理员。");
// return;
// }
// sharedUsers = dataItem.sharedUsers;
//
// break;
// case "lineAlertdataList".toLowerCase():
// var dataItem = userSpace.lineAlertData[dataItemId];
// if (dataItem == null || dataItem == "undefined") {
// alert("没有这个直线报警数据，请联系系统管理员。");
// return;
// }
// sharedUsers = dataItem.sharedUsers;
//
// break;
// }
// var shareItemGroupUI_innerHTML = "";
// Object
// .keys(users)
// .forEach(
// function(key) {
// if(key!=user.id && key!=2) {
// console.log("sharedUsers = " + JSON.stringify(sharedUsers, null, 2)+" =
// key="+key);
//							
// var shareItemGroupUI_item = "";
// // console.log(key,obj[key]);
// shareItemGroupUI_item += '<div class="icheck-line">';
// shareItemGroupUI_item += '<input tabindex="5" type="checkbox" id="user_'
// + users[key].id + '"';
// if (hasProperty(sharedUsers,key)){
// shareItemGroupUI_item += 'checked="checked"
// onclick="doselectUser(\''+users[key].id +'\');">';
// selectedUsers.add(users[key].id+"");
// }
// else{
// shareItemGroupUI_item += ' onclick="doselectUser(\''+users[key].id +'\');">';
// }
// shareItemGroupUI_item += '<label for="line-checkbox-1">'
// + users[key].name + '</label>';
// shareItemGroupUI_item += '</div>';
// shareItemGroupUI_innerHTML += shareItemGroupUI_item;
// }
// });
// // var test=document.getElementsByTagName('html')[0].innerHTML;
// // console.log(test);
// var shareItemGroupUI = document.getElementById("shareItemGroupUI");
// if(shareItemGroupUI!=null&&shareItemGroupUI!=undefined){
// shareItemGroupUI.innerHTML = shareItemGroupUI_innerHTML;
// console.log("got shareItemGroupUI");
// }else{
// console.log("can not get shareItemGroupUI");
// }
//
// }
// function hasProperty(arr,key1){
// console.log("hasProperty:"+key1);
// if(arr==null||arr=="undefined"){
// console.log("arr==null||arr==undefined");
// return false
// }
// for(var i=0;i<arr.length;i++){
// console.log("hasProperty:"+key1);
//		
// if(arr[i].id==key1) return true;
// /*
// Object.keys(arr[i]).forEach(
// function(key){
// console.log(arr[i][key]+" "+key1);
// if(arr[i][key]==key1) {
// console.log(arr[i][key]+" "+key1);
// return true;
// //break;
// }
// });
// */
// }
// return false;
// }
// // dataItem
// /**
// *
// * @param itemId
// * @returns
// */
// function getUserList(fillUserUI) {
// if (user == null || user == "undefined") {
// user = localStorage.user;
// uid = user.id;
// token = localStorage.token;
// }
// // var data={'uid':uid,'token':token};
// $.ajax({
// // 提交数据的类型 POST GET
// type : "POST",
// // 提交的网址
// url : "getUserList",
// // 提交的数据
// data : {
// uid : uid,
// token : token
// },
// // 返回数据的格式
// datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
// // 在请求之前调用的函数
// beforeSend : function() {
// showLoading();
// },
// // 成功返回之后调用的函数
// success : function(data) {
// if (data.status == "000"){ //GlobalConsts.ResultCode_SUCCESS) {
// // console.log("server info : "+JSON.stringify(data.data.data));
// var userList = data.data.users;
// // console.log("data.data="+JSON.stringify(data));
// // console.log("realTimeDataId="+realTimeDataId);
// console.log("data=" + JSON.stringify(data, null, 2));
// users = userList;
// fillUserUI(userList);
// // $('#newItemAction_mid').modal('hide');
// // fixLocalRealTimeDataList_Delete(realTimeDataId);
// // if(data.refresh) routeTo('realtimedataList','');
// //
// } else {
// alert("失败 ： " + data.msg);
// }
// hideLoading();
// // alert("本地存储："+localStorage.user);
// // window.location.href = "index.html";
// },
// // 调用执行后调用的函数
// complete : function(XMLHttpRequest, textStatus) {
// // alert(XMLHttpRequest.responseText);
// // alert(textStatus);
// hideLoading();
//
// },
// // 调用出错执行的函数
// error : function(jqXHR, textStatus, errorThrown) {
// /* 弹出jqXHR对象的信息 */
// // alert(jqXHR.responseText);
// // alert(jqXHR.status);
// // alert(jqXHR.readyState);
// // alert(jqXHR.statusText);
// /* 弹出其他两个参数的信息 */
// // alert(textStatus);
// // alert(errorThrown);
// hideLoading();
// }
// });
// }
