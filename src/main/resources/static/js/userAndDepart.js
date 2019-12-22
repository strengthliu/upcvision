/**
 * 
 */

console.log("--"+JSON.stringify(user));
//if(user.role==1)
{
	var usermanagetitle = document.getElementById("userManagePart_Title");
	usermanagetitle.style.display="block";
	var usermanagetitle = document.getElementById("_departManager");
	usermanagetitle.style.display="block";
	var usermanagetitle = document.getElementById("_userManager");
	usermanagetitle.style.display="block";
}	 
//**************************************** 修改个人信息 begin
function showUserInfoDialog(dialogType, client) {
	console.log("function-> user = "+JSON.stringify(user));
	$('#index_userManager_edit_modal').modal('show');
	if(client==null || client=="undefined")
		client = user;
	tuserInfo = user;
	console.log("Edit = "+JSON.stringify(client));
	$("#_name").val(client.name);
	$("#_username").val(client.username);
	$("#_useraddress").val(client.address);
	$("#_userdepart").val(client.depart);
	$("#_userdesc").val(client.desc);
	$("#_usermobile").val(client.mobile);
	$("#_useremail").val(client.email);
	// 角色不能选择。
	var roleName="";
	switch(client.role){
		case 1:
			roleName = "管理员"
		break;
		case 2:
			roleName = "组态人员"
		break;
		case 3:
			roleName = "用户"
		break;
		case 4:
			roleName = "查看用户"
		break;
		
	}
	$("#_userrole").val(roleName);
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
}

var tuserInfo;
function setTUserInfo(k, v) {
	if (tuserInfo == null || tuserInfo == "undefined") {
		tuserInfo = new Object();
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
}


function checkPassword(value) {
	if (tuserInfo == null || tuserInfo == "undefined") {
//		alert("请先选择一个用户修改，或新建一个用户。");
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
function updateUserInfo(item) {
	console.log("_insertItem -> "+ JSON.stringify(item));
	// 下面这段是为了兼容弹出新建对话框时，确定所执行的插入数据。
	if (tuserInfo == null || tuserInfo == "undefined") {
//		alert("请先选择一个用户。1");
		return false;
	}
	if(!checkPassword(this.value)){
		alert("您输入了新密码，但两次输入的不一致。请重新输入。");
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
//	    	console.log("_insertItem 1-> "+JSON.stringify(item));
//	    	if(_dialogType=="Add")
//	    		$("#userInfoList").jsGrid("insertItem", item);
//	    	if(_dialogType=="Edit")
//	    		$("#userInfoList").jsGrid("updateItem", item);

	    	ajaxUpdateUserInfo(item,function(value){
	    		console.log("v="+JSON.stringify(value));
//	    		fixGraphList(value.data.graph);
	    	},function(err){
	    		console.log("err -> "+JSON.stringify(err));
	    	});
	    },function(err){
	    	
	    });	
        $('#index_userManager_edit_modal').modal('hide');
	    hideLoading();
		return;
	}else{ // 如果没有图形，就是只改名字、描述。
		console.log("没有图形，只修改名字描述。");
		// 如果数据都正确，就执行上传。
    	ajaxUpdateUserInfo(item,null,null);
		
        $('#index_userManager_edit_modal').modal('hide');
		return;
	}
}
	
function ajaxUpdateUserInfo(item,sucess,fail){
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
						user = data.data.data;
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
			}).fail(function(msg) {
			});
}
//**************************************** 修改个人信息 end
