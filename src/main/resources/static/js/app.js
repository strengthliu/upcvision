
//格式化日期
Date.prototype.Format = function (fmt) {
var o = {
  "y+": this.getFullYear(),
  "M+": this.getMonth() + 1,                 //月份
  "d+": this.getDate(),                    //日
  "h+": this.getHours(),                   //小时
  "m+": this.getMinutes(),                 //分
  "s+": this.getSeconds(),                 //秒
  "q+": Math.floor((this.getMonth() + 3) / 3), //季度
  "S+": this.getMilliseconds()             //毫秒
};
for (var k in o) {
  if (new RegExp("(" + k + ")").test(fmt)){
    if(k == "y+"){
      fmt = fmt.replace(RegExp.$1, ("" + o[k]).substr(4 - RegExp.$1.length));
    }
    else if(k=="S+"){
      var lens = RegExp.$1.length;
      lens = lens==1?3:lens;
      fmt = fmt.replace(RegExp.$1, ("00" + o[k]).substr(("" + o[k]).length - 1,lens));
    }
    else{
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    }
  }
}
return fmt;
}
var splitKeyServerPoint = "\\";
var splitKeyServerPre = "\\\\";
function splitServerPoint(tag){
	var ret = new Array();
	if(tag.indexOf(splitKeyServerPre)==0)
		tag = tag.substring(0,2);
	if(tag.indexOf(splitKeyServerPoint)>0){
		ret.push(tag.substring(0,tag.indexOf(splitKeyServerPoint)));
		ret.push(tag.substring(tag.indexOf(splitKeyServerPoint)+1));
		return ret;
	}else {
		ret.push("");
		ret.push(tag);
		return ret;
	}
}

function unicodeSpecialChar(url){
	var rchar = ["%3F","%23","%2B","%20","%26","%3D"];
	url = url.replace( /%/g,rchar[0]);
	url = url.replace(/#/g,rchar[1]);
	url = url.replace(/\+/g,rchar[2]);
	url = url.replace(/\?/g,rchar[3]);
	url = url.replace(/ /g,rchar[4]);
	url = url.replace(/&/g,rchar[5]);
	url = url.replace(/=/g,rchar[6]);
	return url;
}

var userSpace = null;
var serverList = null;
var users = null;
var userInfoList = null;
var token;
var user;
var _galleryKey = null;
var serverList;



/**
 * websocket变量。
 */
var socket;
// alert("websocket connected 1.");
var stompClient;
var subscribe;
var connected;

var socketRetryTimes = 3;

function checkToken(){
	if(localStorage.user != null && localStorage.user != "undefined")
		user = JSON.parse(localStorage.user);
	if(localStorage.token != null && localStorage.token != "undefined")
		token = localStorage.token;
	// 如果没有token或user，通过检查权限，将重新登录。
	if(user == null || token == null)
		checkRight();
}
//避免刷新时
connect();
function connect(callback) {
	if(user==null || user =="undefined")
		return;
	console.log(" app.js connect....");
//	disconnect();
//	if (socket.readyState == 1) {
//		return;
//	}
	socket = new SockJS('/socketServer');
	stompClient = Stomp.over(socket);
	// 停止调试信息
	stompClient.debug = null;
	checkToken();
	sessionStorage.setItem('token', token);// 设置指定session值
	sessionStorage.setItem('uid', user.id);// 设置指定session值

	// const id = localStorage.getItem("chat_id");
	// var socket = new SockJS('ws://localhost:8888/socketServer/');
//	socket = new SockJS('/socketServer');
//	// alert("websocket connected 1.");
//	stompClient = Stomp.over(socket);
	stompClient.heartbeat.outgoing = 10000; // 客户端每20000ms发送一次心跳检测
	stompClient.heartbeat.incoming = 10000; // client接收serever端的心跳检测
	// 连接服务器
	var headers = {
		login : user.id,
		token : token,
		// additional header
		'client-id' : 'my-client-id'
	};
	if(socketRetryTimes>3){
		socketRetryTimes=0;
		alert("连续3次没有连接成功，请检查网络，或与系统管理员联系。");
		return;
	}else
		socketRetryTimes++;
	
	stompClient.connect(headers, function(){
		connected = true;
		if(callback!=null && callback!="undefined")
			callback();
		});
}
function testSocketConnected(){
	// 发送消息给服务器
	try{
	stompClient.send("/app/message", {
		atytopic : "testMessage",
		type : 'testMessage',
		id : '122215432'
	}, JSON.stringify({
		'type' : 'testMessage',
		'id' : 1234567
	}));
	return true;
	}catch(error){
		connected = false;
		return false;
	}

}
function disconnect() {
	console.log('disconnect');
//	if (socket.readyState != 1) {
////		return;
//	}
	
	if (stompClient !== null && stompClient!="undefined") {
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
		
		stompClient.disconnect();
		console.log('do disconnect');
	}
	connected = false;
//	setConnected(false);

	console.log("Disconnected");
}

function unsubscribe(){
//	if(aabbcc == "undefined")
//		console.log("yes");
//	if(stompClient=="undefined" || stompClient==null )
//		return;
	try{
	if (stompClient !== null) {
		if(subscribe!=null && subscribe!="undefined")
			subscribe.unsubscribe();
	}
	}catch(error){
		
	}
	console.log('unsubscribe');
}


function logout(){
	localStorage.user = null;
	user = null;
	localStorage.token = null;
	token = null;
	userSpace = null;
	window.location.href = "login.html";
}

function loginByUserPassWord(uname, pwd) {
	var data = {
		'uname' : uname,
		'pwd' : pwd
	};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "login",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
//			hideLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			//console.log("登录成功返回： " + data);
			if (data.status == "000"){//GlobalConsts.ResultCode_SUCCESS) {
				// us = data.data;
				userSpace = data.data.userSpace;
				user = userSpace.user;
				token = userSpace.token;
				// console.log("登录成功，token="+token);
				// alert("登录成功能 ，用户名为： "+JSON.stringify(user)+" token="+token);
				window.userSpace = userSpace;
				localStorage.user = JSON.stringify(user);
				console.log("登录成功，user=" + JSON.stringify(userSpace));
				localStorage.token = token;
			} else {
				console.log("登录失败 ： " + data.msg);
				window.location.href="login.html"
			}
			hideLoading();

			// alert("本地存储："+localStorage.user);
			window.location.href = "index.html";
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

async function getUserSpace(uid, token, sucessFucn) {
	if(userSpace==null||userSpace=="undefined"){
	await $.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "getUserSpace",
		// 提交的数据
		data : {
			uid : uid,
			token : token
		},
		async: false,
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			// console.log(JSON.stringify(data));
			if (data.status != "000"){//GlobalConsts.ResultCode_SUCCESS) { // 不成功
//				alert(data.msg);
				console.log("getUserSpace-> "+data.msg);
				localStorage.user = null;
				localStorage.token = null;
				userSpace = null;
				window.location.href = "login.html";

			}
			// alert(" getUserSpace : "+JSON.stringify(data));
			if (userSpace == null || userSpace == "undefined") {
				// console.log("getUserSpace -> set userSpace.");
				userSpace = data.data.userSpace;
//				console.log("userspace: "+JSON.stringify(data));
				// return userSpace;
				window.userSpace = userSpace;
			}

			// alert(" getUserSpace : "+JSON.stringify(window.userSpace));
			if(sucessFucn!=null && sucessFucn!="undefined")
				sucessFucn(window.userSpace);
			// localStorage.user = JSON.stringify(data);
			// window.location.href ="index.html";
			hideLoading();
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			showLoading();
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			// 请求出错处理
			
			hideLoading();
		}
	});
	}else{
		sucessFucn(window.userSpace);
	}
}

function checkRight(uid, token, loginPage,sucessPage) {
	// console.log("checkRight token="+token);
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "checkAuthorcation",
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
			if (data.status != "000"){//GlobalConsts.ResultCode_SUCCESS) { // 不成功
				// alert(data.msg);
				localStorage.user = null;
				localStorage.token = null;
				user = null;
				token = null;
				userSpace = null;
				if (loginPage == null || loginPage == "undefined")
					showLoading();
					console.log("checkright -> goto login.html");
					window.location.href = "login.html";
				
//				if (loginPage != "login.html")
//					window.location.href = loginPage;
			} else {
				if(sucessPage!=null && sucessPage!="undefined")
					window.location.href = sucessPage;
			}
			hideLoading();
		},
		// 调用执行后调用的函数
		complete : function(XMLHttpRequest, textStatus) {
			hideLoading();
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			// 请求出错处理
			if (loginPage != null && loginPage != "undefined" && loginPage !="login.html")
				window.location.href = "login.html";
			hideLoading();
		}
	});
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

var loadStartTime;
function showLoading() {
	loadStartTime = Date.now();
	setTimeout(function() {
		_showLoading();
	}, 500);
}

function _showLoading() {
	if (loadStartTime != 0)
		$("#loading").show();
}

function hideLoading() {
	var currentTime = Date.now();
	var difference = currentTime - loadStartTime;
	//console.log(difference);
	if (difference < 500)
		loadStartTime = 0;
	else {
		if (difference < 1000) {
			setTimeout(function() {
				$("#loading").hide();
			}, 1000);
		} else
			$("#loading").hide();
	}
}

//addLoadListener(unsubscribe);
//addLoadListener(disconnect);

function addLoadListener(fn){
    if (typeof window.addEventListener != 'undefined'){
        window.addEventListener('load',fn,false);
    }else if(typeof document.addEventListener != 'undefined'){
        document.addEventListener('load',fn,false);
    }else if (typeof window.attachEvent != 'undefined'){
        window.attachEvent('onload',fn);
    }else{
        var oldfn = window.onload;
        if(typeof window.onload != 'function'){
            window.onload = fn;
        }else{
            window.onload = function(){
                oldfn();
                fn();
            };
        }
    }
}


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


function addLoadListener(fn){
    if (typeof window.addEventListener != 'undefined'){
        window.addEventListener('load',fn,false);
    }else if(typeof document.addEventListener != 'undefined'){
        document.addEventListener('load',fn,false);
    }else if (typeof window.attachEvent != 'undefined'){
        window.attachEvent('onload',fn);
    }else{
        var oldfn = window.onload;
        if(typeof window.onload != 'function'){
            window.onload = fn;
        }else{
            window.onload = function(){
                oldfn();
                fn();
            };
        }
    }
}