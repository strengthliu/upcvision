/**
 * 
 */

if (user == null || user == "undefined") {
	user = JSON.parse(localStorage.user);
	if (user == null || user == "undefined") {
		alert("登录超时，请重新登录。");
		window.location.href = "login.html";
	}
}

/**
 * 控制面板悬浮菜单
 */
// var _routeType;
// var _routeID;
var mainPanel = document.getElementById("main");
// TODO: 增加权限
// if(user.id == _realtimeData.creater || user.id == _realtimeData.owner ||
// user.role == 1){
var oppertionButton = document.createElement('div');
oppertionButton.setAttribute("id","mainInfoNr");
var oppertionButton_innerHTML = "";
oppertionButton_innerHTML = ' <div class="info-nr " id="info-nr"> '
		+ '<div id="info-nr-phone" class="info-nr-phone dialog-title"> ';
oppertionButton_innerHTML += '  <section id="toMenu"></section> ';
if (user.role <= 2) {
	oppertionButton_innerHTML += '  <div class="menu_01"  data-toggle="modal" data-target="#newItemAction_mid" onclick="newItemAction();"></div> ';
}
// if(user.id == _realtimeData.creater || user.id == _realtimeData.owner ||
// user.role == 1){
// if(false)// 先停掉其他菜单
{
	oppertionButton_innerHTML += '  <div class="menu_02"> </div> ';
	oppertionButton_innerHTML += '  <div class="menu_03"> </div> ';
	oppertionButton_innerHTML += '  <div class="menu_04"> </div>   ';
//	oppertionButton_innerHTML += '  <div class="menu_02" onclick="editItemAction();"> </div> ';
//	oppertionButton_innerHTML += '  <div class="menu_03" onclick="deleteItemAction();"> </div> ';
//	oppertionButton_innerHTML += '  <div class="menu_04" onclick="shareItemAction();"> </div>   ';
}
oppertionButton_innerHTML += '</div>  ' + '</div>';
oppertionButton.innerHTML = oppertionButton_innerHTML;
//console.log(oppertionButton.innerHTML);
//console.log("mainPanel.js => user.role="+user.role);
switch (user.role) {
case 1: // 管理员什么都有
	mainPanel.appendChild(oppertionButton);
	$("#toMenu").click(function() {
		if(newItemAction!="undefined"){
			$(".info-nr-phone").toggleClass("info-nr-phone2");
			$(".menu_01").toggleClass("to_01");
			$(".menu_02").toggleClass("to_02");
			$(".menu_03").toggleClass("to_03");
			$(".menu_04").toggleClass("to_04");
		}
	});

//	window.addEventListener("load", function() {
//		$("#toMenu").click(function() {
//			$(".info-nr-phone").toggleClass("info-nr-phone2");
//			$(".menu_01").toggleClass("to_01");
//			$(".menu_02").toggleClass("to_02");
//			$(".menu_03").toggleClass("to_03");
//			$(".menu_04").toggleClass("to_04");
//		});
//	}, false);

	break;
case 2: // 组态用户
	mainPanel.appendChild(oppertionButton);
	$("#toMenu").click(function() {
		if(newItemAction!="undefined"){
			$(".info-nr-phone").toggleClass("info-nr-phone2");
			$(".menu_01").toggleClass("to_01");
			$(".menu_02").toggleClass("to_02");
			$(".menu_03").toggleClass("to_03");
			$(".menu_04").toggleClass("to_04");
		}
	});

//	window.addEventListener("DOMContentLoaded", function() {
//		$("#toMenu").click(function() {
//			$(".info-nr-phone").toggleClass("info-nr-phone2");
//			$(".menu_01").toggleClass("to_01");
//			$(".menu_02").toggleClass("to_02");
//			$(".menu_03").toggleClass("to_03");
//			$(".menu_04").toggleClass("to_04");
//		});
//	}, false);
	break;
case 3: // 其他用户
	$("#toMenu").click(function() {
		$(".info-nr-phone").toggleClass("info-nr-phone2");
	});
	break;

}
