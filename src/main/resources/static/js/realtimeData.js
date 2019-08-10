/**
 * 
 */

var gl = new Array();

console.log(_realtimeDataDetailKey);

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
		var item = '<div class="mb-4" id="point_'+pointList[indpl].tagName+'"></div>';
		innerHtml += item;
	}
	uirealtimeDataPoints.innerHTML = innerHtml;
	console.log(innerHtml);

	
	for(var indpl=0;indpl<gl.length;indpl++) {
		// 对象加一条
		var gt = new JustGage({
		    id: "point"+pointList[indpl].id,
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
		setInterval(function() {
			gt.refresh(getRandomInt(50, 100));
		
		}, 2500);
	}

}

