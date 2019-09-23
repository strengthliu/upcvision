/**
 * 
 */

		console.log("--------debug 1");

initLeftList();
setTUserInfoUI(null,false);

var _userInfoList;

function filterUI(){
	userInfoList = null;
	initLeftList();
	setTUserInfoUI(null,false);
}

function initLeftList() {
	console.log("=======initLeftList==============");

	if (userInfoList == null || userInfoList == "undefined") {
		var data = {
			'uid' : user.id,
			'token' : token
		};
		$.ajax({
			// 提交数据的类型 POST GET
			type : "POST",
			// 提交的网址
			url : "getUserInfoList",
			// 提交的数据
			data : JSON.stringify(data),
			contentType : "application/json",
			// 返回数据的格式
			datatype : "json",// "xml", "html", "script", "json", "jsonp",
			// "text".
			// 在请求之前调用的函数
			beforeSend : function() {
				showLoading();
			},
			// 成功返回之后调用的函数
			success : function(data) {
				// console.log("登录成功返回： " + data);
				if (data.status == GlobalConsts.ResultCode_SUCCESS) {
					// us = data.data;
					userInfoList = data.data.users;
					_userInfoList = userInfoList;
					initUserInfoList(userInfoList);
					console.log("users=" + JSON.stringify(data.data.users));
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
	} else {
		initUserInfoList(userInfoList);
	}
}

function initUserInfoList(_userInfoList) {
	var _userManage_userList = document
			.getElementById("_userManage_userListUI");
	var _userManage_userList_InnerHtml = "";
//	int index_userInfo = 0;
	
	for ( var key =0;key< userInfoList.length;key++) {
		console.log(JSON.stringify(userInfoList[key]));
		var _userManage_userList_InnerHtml_item = "";
//		console.log(JSON.stringify(key));
		_userManage_userList_InnerHtml_item += '<div class="card-body">';
		_userManage_userList_InnerHtml_item += '<div class="list d-flex align-items-center border-bottom py-3" id=useritem_"'
				+ userInfoList[key].id
				+ '"  onclick="clickPersion('
				+ key + ')">';
		_userManage_userList_InnerHtml_item += '<img class="img-sm rounded-circle"';
		_userManage_userList_InnerHtml_item += '	src="'
				+ userInfoList[key].photo + '" alt=""/>';
		_userManage_userList_InnerHtml_item += '<div class="wrapper w-100 ml-3">';
		_userManage_userList_InnerHtml_item += '	<p class="mb-0">';
		_userManage_userList_InnerHtml_item += '		<b>姓名：'
				+ userInfoList[key].name + '</b> 。'+userInfoList[key].desc;
		_userManage_userList_InnerHtml_item += '	</p>';
		_userManage_userList_InnerHtml_item += '	<p class="mb-0">';
		var rolestr = "";
		switch(userInfoList[key].role){
		case 1:
			rolestr="管理员";
			break;
		case 2:
			rolestr="组态人员";
			break;
		case 3:
			rolestr="普通用户";
			break;
		}
		_userManage_userList_InnerHtml_item += '		' + rolestr;
		_userManage_userList_InnerHtml_item += '	</p>';
		_userManage_userList_InnerHtml_item += '	<div class="d-flex justify-content-between align-items-center">';
		_userManage_userList_InnerHtml_item += '		<div class="d-flex align-items-center">';
		_userManage_userList_InnerHtml_item += '			<p class="mt-2 text-success font-weight-bold">'
				+ rolestr + '</p>';
		_userManage_userList_InnerHtml_item += '			<i class="mdi mdi-clock text-muted mr-1"></i>';
		_userManage_userList_InnerHtml_item += '			<p class="mb-0"></p>';
		_userManage_userList_InnerHtml_item += '		</div>';
		_userManage_userList_InnerHtml_item += '		</div>';
		_userManage_userList_InnerHtml_item += '	</div>';
		_userManage_userList_InnerHtml_item += '</div></div>';
		_userManage_userList_InnerHtml += _userManage_userList_InnerHtml_item;
	}
//	console.log(_userManage_userList_InnerHtml);
	_userManage_userList.innerHTML = _userManage_userList_InnerHtml;
	
	//
	var _userManage_ToolsBar_UI = document.getElementById("_userManage_ToolsBar_UI");
	var _userManage_ToolsBar_UI_InnerHtml = "";
		_userManage_ToolsBar_UI_InnerHtml+= '<div class="btn-group">';
		_userManage_ToolsBar_UI_InnerHtml+= '<button type="button" class="btn btn-sm btn-outline-secondary" onclick="createUserUI();">';
		_userManage_ToolsBar_UI_InnerHtml+= '	<i class="mdi mdi-attachment text-primary"></i>新建用户';
		_userManage_ToolsBar_UI_InnerHtml+= '</button>';
		_userManage_ToolsBar_UI_InnerHtml+= '<button type="button" class="btn btn-sm btn-outline-secondary" onclick="deleteCurrentUser();">';
		_userManage_ToolsBar_UI_InnerHtml+= '	<i class="mdi mdi-delete text-primary"></i>删除当前用户';
		_userManage_ToolsBar_UI_InnerHtml+= '</button>';
		_userManage_ToolsBar_UI_InnerHtml+= '	</div>';
	if(user.role == 1)
		_userManage_ToolsBar_UI.innerHTML = _userManage_ToolsBar_UI_InnerHtml;
	else
		_userManage_ToolsBar_UI.innerHTML = "";
}

var tuserInfo;

function setTUserInfo(k, v) {
//	tuserInfo = userInfoList[2];
	if (tuserInfo == null || tuserInfo == "undefined") {
		alert("请先选择一个用户。");
		return;
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

function setTUserInfoUI(userInfo,editable) {
	if(editable==null||editable=="undefined")
		editable = true;
	
	if(editable==true){
		document.getElementById("_userrole").removeAttribute("disabled");
		document.getElementById("_username").removeAttribute("disabled");
		document.getElementById("_userdesc").removeAttribute("disabled");
		document.getElementById("_usermobile").removeAttribute("disabled");
		document.getElementById("_useremail").removeAttribute("disabled");
		document.getElementById("change-password1").removeAttribute("disabled");
		document.getElementById("change-password2").removeAttribute("disabled");
	}else{
		document.getElementById("_userrole").setAttribute("disabled","disabled");
		document.getElementById("_username").setAttribute("disabled","disabled");
		document.getElementById("_userdesc").setAttribute("disabled","disabled");
		document.getElementById("_usermobile").setAttribute("disabled","disabled");
		document.getElementById("_useremail").setAttribute("disabled","disabled");
		document.getElementById("change-password1").setAttribute("disabled","disabled");
		document.getElementById("change-password2").setAttribute("disabled","disabled");
	}
	
	if(userInfo==null || userInfo=="undefined"){
		document.getElementById("_userrole").value = "";
		document.getElementById("_username").value = "";
		document.getElementById("_userdesc").value = "";
		document.getElementById("_usermobile").value = "";
		document.getElementById("_useremail").value = "";
		document.getElementById("change-password1").value = "";
		document.getElementById("change-password2").value = "";
		return;
	}
	for ( var key in userInfo) {
		if (key == "id") {

		} else if (key == "pwd") {

		} else if (key == "photo") {

		} else if (key == "role") {
			var _droprole = document.getElementById("_userrole");
			switch (userInfo[key]) {
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
		} else {
			console.log("_user" + key);
			var _inputElement = document.getElementById("_user" + key);

			_inputElement.value = userInfo[key];
		}
		document.getElementById("_userrole");
	}

}

function clickPersion(id) {
//	console.log("clicked id="+id+" info=>");
	tuserInfo = userInfoList[id];
//	console.log(" info=>"+JSON.stringify(tuserInfo));
//	console.log(" info=>"+JSON.stringify(userInfoList));
	setTUserInfoUI(tuserInfo);
}

function checkPassword(value){
	if(tuserInfo == null || tuserInfo=="undefined"){
		alert("请先选择一个用户修改，或新建一个用户。");
		return false;
	}
	var changepassword1 = document.getElementById("change-password1");
	var changepassword2 = document.getElementById("change-password2");
	var ret = true;
	if(changepassword1.value==""||changepassword1.value==null||changepassword1.value=="undefined"){
		alert("上面的密码框需要输入密码");
		ret = false;
	}
	if(changepassword2.value==""||changepassword2.value==null||changepassword2.value=="undefined"){
		alert("下面的密码框需要输入密码");
		ret = false;
	}
	if(!ret) return ret;
	if(changepassword1.value == changepassword2.value){
		tuserInfo.pwd = changepassword1.value;
		return true;
	}else{
		alert("两次输入的密码不一致。");
		return false;
	}
	return ret;
}

function createUserUI(){
	// TODO:
	tuserInfo = new Object();
	tuserInfo.name = "";
//	tuserInfo.id = "";
	tuserInfo.pwd = "";
	tuserInfo.email = "";
	tuserInfo.role = 0;
	tuserInfo.photo="",
	tuserInfo.mobile = "";
	tuserInfo.desc = "";
//	tuserInfo.depart = "";
	console.log(" info=>"+JSON.stringify(tuserInfo));
	setTUserInfoUI(null,true);

}

function deleteCurrentUser(){
	var id ;
	if(tuserInfo == null || tuserInfo=="undefined"){
		alert("请先选择一个用户。");
		return false;
	}else{
		id = tuserInfo.id;
	}
	var data = {
			'id':id,
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
			if (data.status == GlobalConsts.ResultCode_SUCCESS) {
				
				console.log(userInfoList);
				var _userInfoList = new Array();
				var j=0;
				for(var i=0;i<userInfoList.length;i++){
					if(userInfoList[i].id+"" != data.data.data){
						_userInfoList[j] = userInfoList[i];
						j++
					}
				}
				userInfoList = _userInfoList;
				createUserUI();
				tuserInfo = null;
				console.log("data:"+JSON.stringify(userInfoList));
				initUserInfoList(userInfoList);
				setTUserInfoUI(null,false);
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

function updateUserInfoToServer(){
	if(tuserInfo == null || tuserInfo=="undefined"){
		alert("请先选择一个用户。");
		return false;
	}
	
	var data = {
			'uid' : user.id,
			'token' : token,
			'name' : tuserInfo.name,
			'pwd' : tuserInfo.pwd,
			'email' : tuserInfo.email,
			'role' : tuserInfo.role,
			'photo' : tuserInfo.photo,
			'mobile' : tuserInfo.mobile,
			'desc' : tuserInfo.desc,
			'id' : tuserInfo.id
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
			if (data.status == GlobalConsts.ResultCode_SUCCESS) {
				var d = data.data.data;
				userInfoList.push(d);
//				console.log(JSON.stringify(userInfoList));
				tuserInfo = null;
				initUserInfoList(userInfoList);
				setTUserInfoUI(null,false);
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

function submitOnUserInfo(){
	if(tuserInfo == null || tuserInfo=="undefined"){
		alert("请先选择一个用户。");
		return false;
	}
	if(tuserInfo.id==null || tuserInfo.id=="undefined"){
		// 新建用户
		
		// 检查密码
		if(!checkPassword())
			return;
		else{
			var changepassword2 = document.getElementById("change-password2");
			tuserInfo.pwd = changepassword2.value;
		}
		updateUserInfoToServer();
		
	}else{
		// 更新用户基本信息
		updateUserInfoToServer();
	}
	
}
//function ch