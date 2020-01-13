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
		}, {
			Name : "guest",
			Id : 4
		} ];
		var _depart = departdata;
		// basic config
		console.log("222-"+JSON.stringify(departdata));
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
																				function(client) {
																					console.log("filter= "+JSON.stringify(filter));
																					console.log("client= "+JSON.stringify(client));
																					console.log("response.data= "+JSON.stringify(response.data));
																					return (!filter.name || client.name
																							.indexOf(filter.name) > -1)
																							&& (!client || !client.userName || !filter.userName || client.userName
																									.indexOf(filter.userName) > -1)
																							&& (!client || !client.depart || !filter.depart || client.depart
																									.indexOf(filter.depart) > -1)
																							&& (!client || !filter.role || client.role === filter.role);
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
											type : "select",
											items : departdata,
											valueField : "id",
											textField : "departname",
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
		function showDialoag(){
			fillDepartmentSelect();
			$('#userManager_edit_modal').modal('show');
		}

		var showDetailsDialog = function(dialogType, client) {
			_dialogType = dialogType;
			//清空头像file框
			var gf = document.getElementById("_userPortrait");
			gf.value = "";
			gf.outerHTML = gf.outerHTML;

			fillDepartmentData(showDialoag)
//			$('#userManager_edit_modal').modal('show');
			switch(dialogType){
			case "Edit":
				tuserInfo = client;
				 console.log("Edit = "+JSON.stringify(client));
				$("#_name").val(client.name);
				$("#_username").val(client.username);
				$("#_useraddress").val(client.address);
//				$("#_userdepart").val(client.depart);
				setTUserInfo('depart',client.depart);
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
	if(!checkPassword()){
		alert("您输入了新密码，但两次输入的不一致。请重新输入。");
		return;
	}
	if(tuserInfo.depart==null || tuserInfo.depart=="undefined"){
		alert("您必须为用户选择部门。");
		return;
	}
	if(tuserInfo.name==null || tuserInfo.name=="undefined"){
		alert("您必须为用户起一个登录用的用户名。");
		return;
	}
	if(tuserInfo.role==null || tuserInfo.role=="undefined"){
		alert("您必须为用户设定一个角色。");
		return;
	}
	if(tuserInfo.pwd==null || tuserInfo.pwd=="undefined"){
		alert("您还没有为用户设定密码。");
		return;
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


var tuserInfo;
function setTUserInfo(k, v) {
	// tuserInfo = userInfoList[2];
	if (tuserInfo == null || tuserInfo == "undefined") {
		tuserInfo = new Object();
		// alert("请先选择一个用户。");
		// return;
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
	if (k == "depart") {
//		console.log("depart -> "+v);
		var _dropdepart = document.getElementById("_userdeparttext");
		for(var inddep=0;inddep<departdata.length;inddep++){
			var dep = departdata[inddep];
			if(dep.id == v){
				_dropdepart.innerHTML = dep.departname;
				break;
			}
		}
	}
	if(k == "mobile"){
		var labelmobile = document.getElementById("labelmobile");
		var _usermobile = document.getElementById("_usermobile");
		
		const reg = /^1[3456789]\d{9}$/;
		if(!reg.test(_usermobile.value)){
			labelmobile.innerHTML="手机号码格式不正确";
			labelmobile.style.color="red";
		}else{
			labelmobile.innerHTML="手机号码";
			labelmobile.style.color="black";			
		}
		
	}
	if (k == "email"){
		var labelemail = document.getElementById("labelemail");
		var _useremail = document.getElementById("_useremail");
		var _val = _useremail.value;
		const reg= /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		console.log(reg.test("12962039@qq.com"));
		if(!reg.test(_val)){
			labelemail.innerHTML="电子邮件格式不正确";
			labelemail.style.color="red";
		}else{
			labelemail.innerHTML="电子邮件";
			labelemail.style.color="black";			
		}
	}

	console.log(tuserInfo[k]);
}

function checkPassword(value) {
	if (tuserInfo == null || tuserInfo == "undefined") {
		alert("请先选择一个用户修改，或新建一个用户。");
		return false;
	}
	var notice = document.getElementById("passwordNitice");
	var changepassword1 = document.getElementById("change-password1");
	var changepassword2 = document.getElementById("change-password2");
	if(changepassword1.value == "" &&changepassword2.value == "" )
		return true;

	var ret = true;
	if (changepassword1.value == "" || changepassword1.value == null
			|| changepassword1.value == "undefined") {
		// alert("上面的密码框需要输入密码");
		notice.innerHTML = "上面的密码框需要输入密码";
		ret = false;
	}
	if (changepassword2.value == "" || changepassword2.value == null
			|| changepassword2.value == "undefined") {
		// alert("下面的密码框需要输入密码");
		notice.innerHTML = "下面的密码框需要输入密码";
		ret = false;
	}
	if (!ret)
		return ret;
	if (changepassword1.value == changepassword2.value) {
		tuserInfo.pwd = changepassword1.value;
		notice.innerHTML = "";
		return true;
	} else {
//		alert("两次输入的密码不一致。");
		notice.innerHTML = "两次输入的密码不一致。";
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


fillDepartmentSelect();
function fillDepartmentSelect() {
	var _dropdepart = document.getElementById("_departSelectItems");
	_dropdepart.innerHTML = "";
	var _dropdepartinnerHTML = "";
//	console.log("departdata.length= "+departdata.length);
	for(var inddep=0;inddep<departdata.length;inddep++){
		var dep = departdata[inddep];
		var itemstr = '<a class="dropdown-item" onclick="setTUserInfo(\'depart\','+dep.id+');">'+dep.departname+'</a>';
		_dropdepartinnerHTML += itemstr;
	}
	_dropdepart.innerHTML = _dropdepartinnerHTML;
}
var departdata ;
fillDepartmentData();
function fillDepartmentData(func) {
	var data = {
		'uid' : 2,
		'token' : token
	};
	console.log("fdsfdsafdsa");
	$.ajax({
		// 提交数据的类型 POST
		// GET
		type : "POST",
		// 提交的网址
		url : "getDepartList",
		// 提交的数据
		data : JSON.stringify(data),
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
		success : function(data) {
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS)
				console.log("department => " + JSON.stringify(data));
				var departs = data.data.departs;
				departdata = new Array();
				for(var inddep=0;inddep<departs.length;inddep++){
					var dep = new Object();
					var depdata = departs[inddep];
					Object.keys(depdata).forEach(function(key){
						var value = depdata[key];
						if(key=="id") value = value+"";
						dep[key] = value;
					});
					departdata.push(dep);
				}
				if(func!=null&&func!="undefined")
					func();
			} else {
				console.log("department => " + JSON.stringify(data));
				alert("失败11 ： " + data.msg);
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
