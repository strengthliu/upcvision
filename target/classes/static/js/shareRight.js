/**
 * 
 */

// 传入变量
// dataItem
getUserList();
/**
 * 
 * @param itemId
 * @returns
 */
function getUserList() {
	if(user==null || user=="undefined"){
		user = localStorage.user;
		uid = user.id;
		token = localStorage.token;
	}
	//var data={'uid':uid,'token':token};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getUserList",
		// 提交的数据
        data:{uid:uid,token:token},
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == GlobalConsts.ResultCode_SUCCESS) {
				//console.log("server info : "+JSON.stringify(data.data.data));
				var userList = data.data.users;
				//console.log("data.data="+JSON.stringify(data));
				//console.log("realTimeDataId="+realTimeDataId);
				console.log("data="+JSON.stringify(data,null,2));
				//$('#newItemAction_mid').modal('hide');
				//fixLocalRealTimeDataList_Delete(realTimeDataId);
				//if(data.refresh) routeTo('realtimedataList','');
				// 
			} else {
				alert("失败 ： "+data.msg);
			}
			hideLoading();
			//alert("本地存储："+localStorage.user);
			//window.location.href = "index.html";
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			//alert(XMLHttpRequest.responseText);
			//alert(textStatus);
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

