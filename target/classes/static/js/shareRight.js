/**
 * 
 */
var selectedUsers;
function cancel(){
	selectedUsers = new Set();
	$('#shareItemAction_mid').modal('hide');
}
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
			getUserList(fillUserUI);
		} else {
			fillUserUI(users);
		}
	}
}

function doselectUser(id){
	var sid = "user_"+id;
	var uitem = document.getElementById(sid);
	var checked = uitem.checked;
	console.log("select "+id+"  "+checked);
	if(checked)
		selectedUsers.add(id);
	else
		selectedUsers.delete(id);
	console.log("dataItemId="+dataItemId+"  user:"+JSON.stringify(Array.from(selectedUsers)));
	
}
function fillUserUI(users) {
	if (userSpace == null || userSpace == "undefined") {
		console.log("设置共享时用户空间为空。");
	}
	var shareItemGroupUI = document.getElementById("shareItemGroupUI");
	var sharedUsers;
	console.log("shareRight.js -> fillUserUI =>  shareType="+shareType);
	switch (shareType) {
	case "realtimedatalist".toLowerCase():
		var dataItem = userSpace.realTimeData[dataItemId];
		if (dataItem == null || dataItem == "undefined") {
			alert("没有这个实时数据，请联系系统管理员。");
			return;
		}
		sharedUsers = dataItem.sharedUsers;

		break;
	case "alertData".toLowerCase():
		break;
	}
	var shareItemGroupUI_innerHTML = "";
	Object
			.keys(users)
			.forEach(
					function(key) {
						if(key!=user.id && key!=2) {
							console.log("sharedUsers = " + JSON.stringify(sharedUsers, null, 2)+"  =   key="+key);
							
						var shareItemGroupUI_item = "";
						// console.log(key,obj[key]);
						shareItemGroupUI_item += '<div class="icheck-line">';
						shareItemGroupUI_item += '<input tabindex="5" type="checkbox" id="user_'
								+ users[key].id + '"';
						if (hasProperty(sharedUsers,key)){
							shareItemGroupUI_item += 'checked="checked" onclick="doselectUser(\''+users[key].id +'\');">';
							selectedUsers.add(users[key].id+"");
						}
						else{
							shareItemGroupUI_item += ' onclick="doselectUser(\''+users[key].id +'\');">';
						}
						shareItemGroupUI_item += '<label for="line-checkbox-1">'
								+ users[key].name + '</label>';
						shareItemGroupUI_item += '</div>';
						shareItemGroupUI_innerHTML += shareItemGroupUI_item;
						}
					});
	shareItemGroupUI.innerHTML = shareItemGroupUI_innerHTML;

}
function hasProperty(arr,key1){
	console.log("hasProperty:"+key1);
	if(arr==null||arr=="undefined"){
		console.log("arr==null||arr==undefined");
		return false
	}
	for(var i=0;i<arr.length;i++){
		console.log("hasProperty:"+key1);
		
		if(arr[i].id==key1) return true;
		/*
		Object.keys(arr[i]).forEach(
				function(key){
					console.log(arr[i][key]+" "+key1);
					if(arr[i][key]==key1) {
						console.log(arr[i][key]+" "+key1);
						return true;
						//break;
					}
				});
				*/
	}
	return false;
}
// dataItem
/**
 * 
 * @param itemId
 * @returns
 */
function getUserList(fillUserUI) {
	if (user == null || user == "undefined") {
		user = localStorage.user;
		uid = user.id;
		token = localStorage.token;
	}
	// var data={'uid':uid,'token':token};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getUserList",
		// 提交的数据
		data : {
			uid : uid,
			token : token
		},
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
				var userList = data.data.users;
				// console.log("data.data="+JSON.stringify(data));
				// console.log("realTimeDataId="+realTimeDataId);
				console.log("data=" + JSON.stringify(data, null, 2));
				users = userList;
				fillUserUI(userList);
				// $('#newItemAction_mid').modal('hide');
				// fixLocalRealTimeDataList_Delete(realTimeDataId);
				// if(data.refresh) routeTo('realtimedataList','');
				// 
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
