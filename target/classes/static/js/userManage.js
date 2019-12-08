/**
 * 
 */

// var _userInfoList;
// initLeftList();
(function($) {
	'use strict';
	$(function() {

		var roledata = [ {
			Name : "",
			Id : 0
		}, {
			Name : "管理员",
			Id : 1
		}, {
			Name : "组态人员",
			Id : 2
		}, {
			Name : "普通用户",
			Id : 3
		} ];
		// basic config
		if ($("#userInfoList").length) {
			$("#userInfoList")
					.jsGrid(
							{
								height : "700px",
								width : "100%",
								filtering : true,
								editing : true,
								// inserting : true,
								sorting : true,
								paging : true,
								autoload : true,
								pageSize : 15,
								pageButtonCount : 5,
								deleteConfirm : function(item) {
									// console.log("1- "+JSON.stringify(item));
									return "您确定要删除用户名为： \"" + item.name
											+ "\"的这个用户的数据么?";
								},
								rowClick : function(args) {
									showDetailsDialog("Edit", args.item);
								},
								controller : {
									loadData : function(filter) {
										var d = $.Deferred();
										// if (userInfoList == null ||
										// userInfoList == "undefined")
										{
											var data = {
												'uid' : user.id,
												'token' : token
											};
											$
													.ajax(
															{
																// 提交数据的类型 POST
																// GET
																type : "POST",
																// 提交的网址
																url : "getUserInfoList",
																// 提交的数据
																data : JSON
																		.stringify(data),
																contentType : "application/json",
																// 返回数据的格式
																datatype : "json",// "xml",
																					// "html",
																					// "script",
																					// "json",
																					// "jsonp",
																// "text".
																// 在请求之前调用的函数
																beforeSend : function() {
																	showLoading();
																},
																// 成功返回之后调用的函数
																success : function(
																		data) {
																	if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS)
																	} else {
																		alert("失败 ： "
																				+ data.msg);
																	}
																	hideLoading();
																},
																// 调用执行后调用的函数
																complete : function(
																		XMLHttpRequest,
																		textStatus) {
																	hideLoading();
																},
																// 调用出错执行的函数
																error : function(
																		jqXHR,
																		textStatus,
																		errorThrown) {
																	hideLoading();
																}
															})
													.done(
															function(response) {
																// console.log("response
																// ->
																// "+JSON.stringify(response));
																var userInfoList = response.data.users;
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

																d.resolve(_userInfoList);
															});
										}
										return d.promise();
									},
									// 插入数据
									insertItem : function(item) {
										var data = {
											'uid' : uid,
											'token' : token,
											'username':item.username,
											'name' : item.name,
											'address' : item.address,
											'depart' : item.depart,
											'desc' : item.desc,
											'mobile' : item.mobile,
											'email' : item.email,
											'role' : item.role,
											'photo' : item.photo,
											'pwd' : item.pwd,
											'id' : item.id
										};
										console.log("insertItem-> item-> "+JSON.stringify(item));
										var d = $.Deferred();
										$.ajax({
											// 提交数据的类型 POST GET
											type : "POST",
											// 提交的网址
											url : "newUser",
											// 提交的数据
											data : JSON.stringify(data),
											contentType : "application/json",
											// 返回数据的格式
											datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
											// 在请求之前调用的函数
											beforeSend : function() {
											},
											// 成功返回之后调用的函数
											success : function(data) {
												if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
													console.log("insertItem in $.->"+JSON.stringify(data));
													return data;
												} else {
													alert("删除失败 ： " + data.msg);
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
										}).done(function(data) {
											console.log("insert done _>"+JSON.stringify(data));
											d.resolve(data.data.data); // 有这个，才能回调promise
										}).fail(function(msg) {
										});
										
										return d.promise();
										// return item;									
									},
									// 更新数据
									updateItem : function(item) {
										console.log("updateItem");
										var data = {
												'uid' : uid,
												'token' : token,
												'username':item.username,
												'name' : item.name,
												'address' : item.address,
												'depart' : item.depart,
												'desc' : item.desc,
												'mobile' : item.mobile,
												'email' : item.email,
												'role' : item.role,
												'photo' : item.photo,
												'pwd' : item.pwd,
												'id' : item.id
											};
											console.log("updateItem-> item-> "+JSON.stringify(item));
											var d = $.Deferred();
											$.ajax({
												// 提交数据的类型 POST GET
												type : "POST",
												// 提交的网址
												url : "newUser",
												// 提交的数据
												data : JSON.stringify(data),
												contentType : "application/json",
												// 返回数据的格式
												datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
												// 在请求之前调用的函数
												beforeSend : function() {
												},
												// 成功返回之后调用的函数
												success : function(data) {
													if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
														console.log("updateItem in $.->"+JSON.stringify(data));
														return data;
													} else {
														alert("删除失败 ： " + data.msg);
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
											}).done(function(data) {
												console.log("update done _>"+JSON.stringify(data));
												d.resolve(data.data.data); // 有这个，才能回调promise
											}).fail(function(msg) {
											});
											
											return d.promise();
											// return item;									
									},
									// 删除数据
									deleteItem : function(item) {
										console.log("updateItem");
										var data = {
												'id' : item.id,
												'uid' : user.id,
												'token' : token
											};
											console.log("updateItem-> item-> "+JSON.stringify(item));
											var d = $.Deferred();
											$.ajax({
												// 提交数据的类型 POST GET
												type : "POST",
												// 提交的网址
												url : "delUser",
												// 提交的数据
												data : JSON.stringify(data),
												contentType : "application/json",
												// 返回数据的格式
												datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
												// 在请求之前调用的函数
												beforeSend : function() {
												},
												// 成功返回之后调用的函数
												success : function(data) {
													if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
														console.log("updateItem in $.->"+JSON.stringify(data));
														return data;
													} else {
														alert("删除失败 ： " + data.msg);
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
											}).done(function(data) {
												console.log("update done _>"+JSON.stringify(data));
												d.resolve(data.data.data); // 有这个，才能回调promise
											}).fail(function(msg) {
											});
											return d.promise();
									}
								},
								fields : [
										{
											title : "用户名",
											editing : false,
											name : "username",
											type : "text",
											width : 70
										},
										{
											title : "登录名",
											editing : false,
											name : "name",
											type : "text",
											width : 70
										},
										{
											title : "部门",
											editing : false,
											name : "depart",
											type : "text",
											width : 100
										},
										{
											title : "角色",
											editing : false,
											name : "role",
											type : "select",
											items : roledata,
											valueField : "Id",
											textField : "Name",
											width : 100
										},
										{
											type : "control",
											modeSwitchButton : false,
											editButton : false,
											headerTemplate : function() {
												return $("<button>").attr(
														"type", "button").text(
														"Add").on(
														"click",
														function() {
															showDetailsDialog(
																	"Add", {});
														});
											}
										} ]
							});
		}

//		var formSubmitHandler = $.noop;

		var showDetailsDialog = function(dialogType, client) {
			_dialogType = dialogType;
			//清空头像file框
			var gf = document.getElementById("_userPortrait");
			gf.value = "";
			gf.outerHTML = gf.outerHTML;

			$('#userManager_edit_modal').modal('show');
			switch(dialogType){
			case "Edit":
				tuserInfo = client;
				 console.log("Edit = "+JSON.stringify(client));
				$("#_name").val(client.name);
				$("#_username").val(client.username);
				$("#_useraddress").val(client.address);
				$("#_userdepart").val(client.depart);
				$("#_userdesc").val(client.desc);
				$("#_usermobile").val(client.mobile);
				$("#_useremail").val(client.email);
				$("#_userrole").val(client.role);
				setTUserInfo('role',client.role);
				// 头像预览
				if(client.photo!=null&&client.photo!="undefined"){
					console.log("client.photo = "+client.photo);
					var gf1 = document.getElementById("_userportrait1");
					gf1.src = client.photo;
					gf1.style.display="";
					var gf2 = document.getElementById("_userportrait2");
					gf2.src = client.photo;
					gf2.style.display="";
					var gf3 = document.getElementById("_userportrait3");
					gf3.src = client.photo;
					gf3.style.display="";
				}else{
					console.log("client.photo = "+client.photo);
				}
				break;
			case "Add":
				
				tuserInfo = client;
				 console.log("Add  = "+JSON.stringify(client));
				$("#_name").val(null);
				$("#_username").val(null);
				$("#_useraddress").val(null);
				$("#_userdepart").val(null);
				$("#_userdesc").val(null);
				$("#_usermobile").val(null);
				$("#_useremail").val(null);
				$("#_userrole").val(null);
				// 头像预览
				var gf1 = document.getElementById("_userportrait1");
				gf1.src = "";
				gf1.style.display="none";
				var gf2 = document.getElementById("_userportrait2");
				gf2.src = "";
				gf2.style.display="none";
				var gf3 = document.getElementById("_userportrait3");
				gf3.src = "";
				gf3.style.display="none";

				break;
			}

		};
	});
})(jQuery);
var _dialogType;
/**
 * 用户详情页面调用，插入或更改一条数据。
 * @param item
 * @returns
 */
function _insertItem(item) {
	console.log("_insertItem -> "+ JSON.stringify(item));
	// 下面这段是为了兼容弹出新建对话框时，确定所执行的插入数据。
	if (tuserInfo == null || tuserInfo == "undefined") {
		alert("请先选择一个用户。1");
		return false;
	}
    // 如果有头像，要先上传头像
	var file=$("#_userPortrait")[0].files[0];
	if(file!="null" && (file+"")!="undefined"){
	    var promise = new Promise(function(resolve, reject) {
	    	showLoading();
	        // 发送ajax 获取第一份数据 (我们需要这份数据里面的key值 要带着这个key去请求pay.json)
	    	ajaxUploadPortrait(resolve,reject);
	    });
	    promise.then(function(value) {
	    	
	    	// 如果数据都正确，就执行上传。
	    	item.photo = value;
	    	console.log("_insertItem 1-> "+JSON.stringify(item));
	    	if(_dialogType=="Add")
	    		$("#userInfoList").jsGrid("insertItem", item);
	    	if(_dialogType=="Edit")
	    		$("#userInfoList").jsGrid("updateItem", item);
	        $('#userManager_edit_modal').modal('hide');

//	    	ajaxUpdateUserInfo(value,function(value){
//	    		console.log("v="+JSON.stringify(value));
////	    		fixGraphList(value.data.graph);
//	    	},function(err){
//	    		console.log("err -> "+JSON.stringify(err));
//	    	});
	    },function(err){
	    	
	    });	
	    hideLoading();
		return;
	}else{ // 如果没有图形，就是只改名字、描述。
		console.log("没有图形，只修改名字描述。");
		
		// 如果数据都正确，就执行上传。
    	if(_dialogType=="Add")
    		$("#userInfoList").jsGrid("insertItem", item);
    	if(_dialogType=="Edit")
    		$("#userInfoList").jsGrid("updateItem", item);
        $('#userManager_edit_modal').modal('hide');

//		ajaxUpdateUserInfo(null,function(value){
//    		console.log("v="+JSON.stringify(value));
////    		fixGraphList(value.data.graph);
//		},function(err){
//    		console.log("err -> "+JSON.stringify(err));			
//		});
		return;
	}

}

function ajaxUploadPortrait(callback,reject){
    var formData=new FormData();
	var file=$("#_userPortrait")[0].files[0];
	formData.append("file",file);
	$.ajax({
	        url: 'uploadPortrait',
	        type: 'POST',
	        cache:false,
	        processData: false,// 不处理数据
	        data: formData,// 直接把formData这个对象传过去
	        dataType : 'json',
	        contentType:false,// 主要设置你发送给服务器的格式,设为false代表不设置内容类型
	        success: function(data){
	        	callback(data.data.url);
	        },
			// 调用出错执行的函数
			error : function(jqXHR, textStatus, errorThrown) {
				reject(textStatus);
			}

	});
	console.log("doUpLoad 2");
}

function ajaxUpdateUserInfo(callback,reject){
	
}

function updateItem(item) {
	console.log(JSON.stringify(item));
	alert("updateItem");
	return item;

}
function deleteItem(item) {
	alert("deleteItem");
	return item;

}

var tuserInfo;

function setTUserInfo(k, v) {
	// tuserInfo = userInfoList[2];
	if (tuserInfo == null || tuserInfo == "undefined") {
		tuserInfo = new Object();
		// alert("请先选择一个用户。");
		// return;
	}
	
	if(k=="labelmobile"){
		
	}
	if(k=="labelemail"){
		
	}
	tuserInfo[k] = v;
	if (k == "role") {
		var _droprole = document.getElementById("_userrole");
		switch (v) {
		case 1:
			_droprole.innerHTML = "管理员";
			break;
		case 2:
			_droprole.innerHTML = "组态人员";
			break;
		case 3:
			_droprole.innerHTML = "普通用户";
			break;
		}
	}

	console.log(tuserInfo[k]);
}

function checkPassword(value) {
	if (tuserInfo == null || tuserInfo == "undefined") {
		alert("请先选择一个用户修改，或新建一个用户。");
		return false;
	}
	var changepassword1 = document.getElementById("change-password1");
	var changepassword2 = document.getElementById("change-password2");
	var ret = true;
	if (changepassword1.value == "" || changepassword1.value == null
			|| changepassword1.value == "undefined") {
		// alert("上面的密码框需要输入密码");
		ret = false;
	}
	if (changepassword2.value == "" || changepassword2.value == null
			|| changepassword2.value == "undefined") {
		// alert("下面的密码框需要输入密码");
		ret = false;
	}
	if (!ret)
		return ret;
	if (changepassword1.value == changepassword2.value) {
		tuserInfo.pwd = changepassword1.value;
		return true;
	} else {
		alert("两次输入的密码不一致。");
		return false;
	}
	return ret;
}

function changePortrait(){
	var file=$("#_userPortrait")[0].files[0];
	if(file!="null" && (file+"")!="undefined"){
	    var promise = new Promise(function(resolve, reject) {
	    	showLoading();
	        // 发送ajax 获取第一份数据 (我们需要这份数据里面的key值 要带着这个key去请求pay.json)
	    	ajaxUploadPortrait(resolve,reject);
	    });
	    promise.then(function(value) {
	    	ajaxUpdateUserInfo(value.data.url,function(value){
	    		console.log("v="+JSON.stringify(value));
	    		fixGraphList(value.data.graph);
	    	},function(err){
	    		
	    	});
	    },function(err){
	    	
	    });	
	    hideLoading();
		return;
	}else{ // 如果没有图形，就是只改名字、描述。
		console.log("没有图形，只修改名字描述。");
		ajaxUpdateGraphInfo(null,function(value){
    		console.log("v="+JSON.stringify(value));
    		fixGraphList(value.data.graph);
		},function(err){
			
		});
		return;
	}
}

function deleteCurrentUser() {
	var id;
	if (tuserInfo == null || tuserInfo == "undefined") {
		alert("请先选择一个用户。");
		return false;
	} else {
		id = tuserInfo.id;
	}
	var data = {
		'id' : id,
		'uid' : user.id,
		'token' : token
	};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "delUser",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {

				console.log(userInfoList);
				var _userInfoList = new Array();
				var j = 0;
				for (var i = 0; i < userInfoList.length; i++) {
					if (userInfoList[i].id + "" != data.data.data) {
						_userInfoList[j] = userInfoList[i];
						j++
					}
				}
				userInfoList = _userInfoList;
				createUserUI();
				tuserInfo = null;
				console.log("data:" + JSON.stringify(userInfoList));
				initUserInfoList(userInfoList);
				setTUserInfoUI(null, false);
			} else {
				alert("删除失败 ： " + data.msg);
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

function updateUserInfoToServer() {
	if (tuserInfo == null || tuserInfo == "undefined") {
		alert("请先选择一个用户。");
		return false;
	}

	var data = {
			'uid' : uid,
			'token' : token,
			'username':item.userName,
			'name' : item.name,
			'address' : item.address,
			'depart' : item.depart,
			'desc' : item.desc,
			'mobile' : item.mobile,
			'email' : item.email,
			'role' : item.role,
			'photo' : item.photo,
			'pwd' : item.pwd,
			'id' : item.id
		};
	
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "newUser",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
				var d = data.data.data;
				return d;
			} else {
				alert("删除失败 ： " + data.msg);
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
//
//function submitOnUserInfo() {
//	if (tuserInfo == null || tuserInfo == "undefined") {
//		alert("请先选择一个用户。");
//		return false;
//	}
//	if (tuserInfo.id == null || tuserInfo.id == "undefined") {
//		// 新建用户
//
//		// 检查密码
//		if (!checkPassword())
//			return;
//		else {
//			var changepassword2 = document.getElementById("change-password2");
//			tuserInfo.pwd = changepassword2.value;
//		}
//		updateUserInfoToServer();
//
//	} else {
//		// 更新用户基本信息
//		updateUserInfoToServer();
//	}
//
//}

// function changePassword(){
// // 检查密码
// if(!checkPassword())
// return;
// else{
// var changepassword2 = document.getElementById("change-password2");
// tuserInfo.pwd = changepassword2.value;
// }
// }


//function createUserUI() {
//	// TODO:
//	tuserInfo = new Object();
//	tuserInfo.name = "";
//	// tuserInfo.id = "";
//	tuserInfo.pwd = "";
//	tuserInfo.email = "";
//	tuserInfo.role = 0;
//	tuserInfo.photo = "", tuserInfo.mobile = "";
//	tuserInfo.desc = "";
//	// tuserInfo.depart = "";
//	console.log(" info=>" + JSON.stringify(tuserInfo));
//	setTUserInfoUI(null, true);
//
//}


//initLeftList();
//setTUserInfoUI(null,false);

//function filterUI() {
//userInfoList = null;
//initLeftList();
//setTUserInfoUI(null, false);
//}
//
//function initLeftList() {
//console.log("=======initLeftList==============");
//
//if (userInfoList == null || userInfoList == "undefined") {
//var data = {
//'uid' : user.id,
//'token' : token
//};
//$.ajax({
//// 提交数据的类型 POST GET
//type : "POST",
//// 提交的网址
//url : "getUserInfoList",
//// 提交的数据
//data : JSON.stringify(data),
//contentType : "application/json",
//// 返回数据的格式
//datatype : "json",// "xml", "html", "script", "json", "jsonp",
//// "text".
//// 在请求之前调用的函数
//beforeSend : function() {
//showLoading();
//},
//// 成功返回之后调用的函数
//success : function(data) {
//// console.log("登录成功返回： " + data);
//if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS)
//// {
//// us = data.data;
//userInfoList = data.data.users;
//_userInfoList = userInfoList;
//// initUserInfoList(userInfoList);
//// console.log("users=" + JSON.stringify(data.data.users));
//} else {
//alert("失败 ： " + data.msg);
//}
//hideLoading();
//},
//// 调用执行后调用的函数
//complete : function(XMLHttpRequest, textStatus) {
//hideLoading();
//},
//// 调用出错执行的函数
//error : function(jqXHR, textStatus, errorThrown) {
//hideLoading();
//}
//});
//} else {
//// initUserInfoList(userInfoList);
//}
//}
//
//function initUserInfoList(_userInfoList) {
//var _userManage_userList = document
//.getElementById("_userManage_userListUI");
//var _userManage_userList_InnerHtml = "";
//// int index_userInfo = 0;
//
//for (var key = 0; key < userInfoList.length; key++) {
//console.log(JSON.stringify(userInfoList[key]));
//var _userManage_userList_InnerHtml_item = "";
//// console.log(JSON.stringify(key));
//_userManage_userList_InnerHtml_item += '<div class="card-body">';
//_userManage_userList_InnerHtml_item += '<div class="list d-flex
//align-items-center border-bottom py-3" id=useritem_"'
//+ userInfoList[key].id
//+ '" onclick="clickPersion('
//+ key
//+ ')">';
//_userManage_userList_InnerHtml_item += '<img class="img-sm rounded-circle"';
//_userManage_userList_InnerHtml_item += ' src="';
//
//switch (userInfoList[key].role) {
//case 1:
//_userManage_userList_InnerHtml_item += "images/faces/user1.png";
//break;
//case 2:
//_userManage_userList_InnerHtml_item += "images/faces/user2.jpeg";
//break;
//case 3:
//_userManage_userList_InnerHtml_item += "images/faces/user3.jpeg";
//break;
//}
//
//_userManage_userList_InnerHtml_item += '" alt=""/>';
//_userManage_userList_InnerHtml_item += '<div class="wrapper w-100 ml-3">';
//_userManage_userList_InnerHtml_item += ' <p class="mb-0">';
//_userManage_userList_InnerHtml_item += ' <b>姓名：'
//+ userInfoList[key].name + '</b> 。' + userInfoList[key].desc;
//_userManage_userList_InnerHtml_item += ' </p>';
//_userManage_userList_InnerHtml_item += ' <p class="mb-0">';
//var rolestr = "";
//switch (userInfoList[key].role) {
//case 1:
//rolestr = "管理员";
//break;
//case 2:
//rolestr = "组态人员";
//break;
//case 3:
//rolestr = "普通用户";
//break;
//}
//_userManage_userList_InnerHtml_item += ' ' + rolestr;
//_userManage_userList_InnerHtml_item += ' </p>';
//_userManage_userList_InnerHtml_item += ' <div class="d-flex
//justify-content-between align-items-center">';
//_userManage_userList_InnerHtml_item += ' <div class="d-flex
//align-items-center">';
//_userManage_userList_InnerHtml_item += ' <p class="mt-2 text-success
//font-weight-bold">'
//+ rolestr + '</p>';
//_userManage_userList_InnerHtml_item += ' <i class="mdi mdi-clock text-muted
//mr-1"></i>';
//_userManage_userList_InnerHtml_item += ' <p class="mb-0"></p>';
//_userManage_userList_InnerHtml_item += ' </div>';
//_userManage_userList_InnerHtml_item += ' </div>';
//_userManage_userList_InnerHtml_item += ' </div>';
//_userManage_userList_InnerHtml_item += '</div></div>';
//_userManage_userList_InnerHtml += _userManage_userList_InnerHtml_item;
//}
//// console.log(_userManage_userList_InnerHtml);
//_userManage_userList.innerHTML = _userManage_userList_InnerHtml;
//
////
//var _userManage_ToolsBar_UI = document
//.getElementById("_userManage_ToolsBar_UI");
//var _userManage_ToolsBar_UI_InnerHtml = "";
//_userManage_ToolsBar_UI_InnerHtml += '<div class="btn-group">';
//_userManage_ToolsBar_UI_InnerHtml += '<button type="button" class="btn btn-sm
//btn-outline-secondary" onclick="createUserUI();">';
//_userManage_ToolsBar_UI_InnerHtml += ' <i class="mdi mdi-attachment
//text-primary"></i>新建用户';
//_userManage_ToolsBar_UI_InnerHtml += '</button>';
//_userManage_ToolsBar_UI_InnerHtml += '<button type="button" class="btn btn-sm
//btn-outline-secondary" onclick="deleteCurrentUser();">';
//_userManage_ToolsBar_UI_InnerHtml += ' <i class="mdi mdi-delete
//text-primary"></i>删除当前用户';
//_userManage_ToolsBar_UI_InnerHtml += '</button>';
//_userManage_ToolsBar_UI_InnerHtml += ' </div>';
//if (user.role == 1)
//_userManage_ToolsBar_UI.innerHTML = _userManage_ToolsBar_UI_InnerHtml;
//else
//_userManage_ToolsBar_UI.innerHTML = "";
//}

//
//function setTUserInfoUI(userInfo, editable) {
//	if (editable == null || editable == "undefined")
//		editable = true;
//	if (editable == true) {
//		if (user.role == 1)
//			document.getElementById("_userrole").removeAttribute("disabled");
//		document.getElementById("_username").removeAttribute("disabled");
//		document.getElementById("_userdesc").removeAttribute("disabled");
//		document.getElementById("_usermobile").removeAttribute("disabled");
//		document.getElementById("_useremail").removeAttribute("disabled");
//		document.getElementById("change-password1").removeAttribute("disabled");
//		document.getElementById("change-password2").removeAttribute("disabled");
//	} else {
//		document.getElementById("_userrole").setAttribute("disabled",
//				"disabled");
//		document.getElementById("_username").setAttribute("disabled",
//				"disabled");
//		document.getElementById("_userdesc").setAttribute("disabled",
//				"disabled");
//		document.getElementById("_usermobile").setAttribute("disabled",
//				"disabled");
//		document.getElementById("_useremail").setAttribute("disabled",
//				"disabled");
//		document.getElementById("change-password1").setAttribute("disabled",
//				"disabled");
//		document.getElementById("change-password2").setAttribute("disabled",
//				"disabled");
//	}
//
//	if (userInfo == null || userInfo == "undefined") {
//		document.getElementById("_userrole").value = "";
//		document.getElementById("_username").value = "";
//		document.getElementById("_userdesc").value = "";
//		document.getElementById("_usermobile").value = "";
//		document.getElementById("_useremail").value = "";
//		document.getElementById("change-password1").value = "";
//		document.getElementById("change-password2").value = "";
//		return;
//	}
//	for ( var key in userInfo) {
//		if (key == "id") {
//
//		} else if (key == "pwd") {
//
//		} else if (key == "photo") {
//
//		} else if (key == "role") {
//			var _droprole = document.getElementById("_userrole");
//			switch (userInfo[key]) {
//			case 1:
//				_droprole.innerHTML = "管理员";
//				break;
//			case 2:
//				_droprole.innerHTML = "组态人员";
//				break;
//			case 3:
//				_droprole.innerHTML = "普通用户";
//				break;
//			}
//		} else {
//			console.log("_user" + key);
//			var _inputElement = document.getElementById("_user" + key);
//
//			_inputElement.value = userInfo[key];
//		}
//		document.getElementById("_userrole");
//	}
//
//}
//
//function clickPersion(id) {
//	// console.log("clicked id="+id+" info=>");
//	tuserInfo = userInfoList[id];
//	// console.log(" info=>"+JSON.stringify(tuserInfo));
//	// console.log(" info=>"+JSON.stringify(userInfoList));
//	setTUserInfoUI(tuserInfo);
//}

