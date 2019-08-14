/**
 * 
 */

var gl = new Array();

console.log("_realtimeDataDetailKey: "+_realtimeDataDetailKey);

function newItemAction(){
	alert("realtimeData.js newItemAction。 从mainPanel中调用的。");
}

if(userSpace==null || userSpace == "undefined") { 
	console.log("userSpace 为空，重新到服务器去取。");
	getUserSpace(user.id,token,updateRealTimeDataChart);
} else
	updateRealTimeDataChart(userSpace);
	
function updateRealTimeDataChart(ruserSpace){
	var pointGroup = ruserSpace.realTimeData[_realtimeDataDetailKey];
	var uirealtimeDataPoints = document.getElementById("ui-realtimeDataPoints");
	// console.log(" updateRealTimeDataChart => "+JSON.stringify(pointGroup));
	if(pointGroup ==null || pointGroup == "undefined") return;
	var pointList = pointGroup.pointList;
	var innerHtml = "";
	for(var indpl=0;indpl<pointList.length;indpl++){
		// console.log(" updateRealTimeDataChart=>  "+JSON.stringify(pointList[indpl]));
		
		// 页面加一块
		var item = '<div class="box col-lg-3"><div class="gauge" id="point_'+pointList[indpl].tagName+'"></div></div>';
		innerHtml += item;
	}
	uirealtimeDataPoints.innerHTML = innerHtml;
	console.log(uirealtimeDataPoints.innerHTML);

	
	for(var indpl=0;indpl<pointList.length;indpl++) {
		console.log();
		// 对象加一条
		var gt = new JustGage({
		    id: "point_"+pointList[indpl].tagName,
		    value: getRandomInt(0, 100),
		    min: 0,
		    max: 100,
		    title: "一级电脱盐混合阀压差",
		    label: "度",
		    donut: true,
		    gaugeWidthScale: 0.6,
		    counter: true,
		    hideInnerShadow: true
		});
		gl[indpl] = gt;	
	}
}
/**
 * 刷新数据
 * @returns
 */
function refreshGage(){
	for(var indpl=0;indpl<gl.length;indpl++) {
			gl[indpl].refresh(getRandomInt(50, 100));
	}
}
setInterval(refreshGage, 1000);

/**
 * 右键菜单
 * @param $
 * @returns
 */
function menuFunc(key,options){
	switch(key){
	case "edit":
		alert(key);
		break;
	}
//	alert(JSON.stringify(options));
}

(function($) {
	if(user == null || user == "undefined"){
		user = JSON.parse(localStorage.user);
		if(user == null || user == "undefined") {
			alert("登录超时，请重新登录。");
			window.location.href="login.html";
		}
	}
	if(user.role<3)
	  $.contextMenu({
	    selector: '#ui-realtimeDataPoints',
	    callback: menuFunc,
	    items: {
	      "share": {
		        name: "分享",
		        icon: "share"
		      },
	      "edit": {
	        name: "修改",
	        icon: "edit"
	      },
	      "delete": {
	        name: "删除",
	        icon: "delete"
	      },
	      "sep1": "---------",
	      "quit": {
	        name: "Quit",
	        icon: function() {
	          return 'context-menu-icon context-menu-icon-quit';
	        }
	      }
	    }
	  });
})(jQuery);


