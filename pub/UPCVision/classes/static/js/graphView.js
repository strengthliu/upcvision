/**
 * 
 */




var svg1;
var svg ;// svg = document.getElementsByTagName("svg");
console.log("=====1");
var width = 0;// svg.attributes.width.value;
var height = 0;// svg.attributes.height.value;
var zoom ;

function zoomed() {
	console.log("zoomed() zoom.translate()="+zoom.translate()+" zoom.scale()="+zoom.scale());
	svg.attr("transform",
	    "translate(" + zoom.translate() + ")" +
	    // "translate(0,0)" +
	    "scale(" + zoom.scale() + ")"
	);
	// svg.attr("transform", "translate(" + d3.event.translate + ")scale(" +
	// d3.event.scale + ")");
}

function interpolateZoom (translate, scale) {
	var self = this;
	return d3.transition().duration(350).tween("zoom", function () {
	    var iTranslate = d3.interpolate(zoom.translate(), translate),
	        iScale = d3.interpolate(zoom.scale(), scale);
		console.log("interpolateZoom translate="+translate+" scale="+scale);
		console.log("interpolateZoom iTranslate="+iTranslate+" iScale="+iScale);
	    return function (t) {
	    	console.log("t="+t);
	    	console.log("interpolateZoom iTranslate(t)="+iTranslate(t)+" iScale(t)="+iScale(t));
	    	console.log("interpolateZoom iTranslate="+iTranslate+" iScale="+iScale);
	       zoom
	            .scale(iScale(t))
	            .translate(iTranslate(t));
	       zoomed();
	    };
	});
}

function zoomClick() {
	var clicked = d3.event.target,
	    direction = 1,
	    factor = 0.2,
	    target_zoom = 1,
	    center = [width / 2, height / 2],
	    extent = zoom.scaleExtent(),
	    translate = zoom.translate(),
	    translate0 = [],
	    l = [],
	    view = {x: translate[0], y: translate[1], k: zoom.scale()};
	
	d3.event.preventDefault();
	
	switch(this.id){
	case 'zoom_up':
		console.log("=================== zoom_up");
		interpolateZoom([view.x, view.y-10], view.k)
		return;
	case 'zoom_down':
		interpolateZoom([view.x, view.y+10], view.k)
		return;
	case 'zoom_left':
		interpolateZoom([view.x-10, view.y], view.k)
		return;
	case 'zoom_right':
		interpolateZoom([view.x+10, view.y], view.k)
		return;
	case 'zoom_fullScreen':
		fullScreen();
		return;
	case 'zoom_in':
		direction = 1;
		break;
	case 'zoom_out':
		direction = -1;
		break;
		default:
			return;
	}
	// direction = (this.id === 'zoom_in') ? 1 : -1;
	target_zoom = zoom.scale() * (1 + factor * direction);
	
	if (target_zoom < extent[0] || target_zoom > extent[1]) { return false; }
	
	translate0 = [(center[0] - view.x) / view.k, (center[1] - view.y) / view.k];
	view.k = target_zoom;
	l = [translate0[0] * view.k + view.x, translate0[1] * view.k + view.y];
	
	// view.x += center[0] - l[0];
	// view.y += center[1] - l[1];
	view.x += l[0] - center[0];
	view.y += l[1] - center[1];
	
	interpolateZoom([view.x, view.y], view.k);
}

var fullScreenEnable = true;

function fullScreen(){
	console.log("fullScreen()");
	if(!fullScreenEnable)
		fullScreenEnable = !fullScreenEnable;
	fullScreenFn();
}

function fullScreenFn(){
	if(fullScreenEnable){
		// 取当前页面宽度和高度
		var _svgP = svg1.parentNode;
		var mainToolsBarRect = _svgP.getBoundingClientRect();
		// console.log("
		// y="+JSON.stringify(mainToolsBar.getBoundingClientRect()));
		// 取当前图形宽度和高度
		console.log(" width="+width +" heigth="+height);
		// mainPanel屏幕左上角坐标
		var y = mainToolsBarRect.y ;// + mainToolsBarRect.height;
		var x = mainToolsBarRect.x;
		var mainPanelHeigth = window.innerHeight;
		var mainPanelWidth = mainToolsBarRect.width;
		
		var scale1 = mainPanelHeigth / mainPanelWidth;
		var _diagramShow = document.getElementById("svgt").getBoundingClientRect();
		var scale2 = _diagramShow.height / _diagramShow.width;
		var scale ;// = scale1 > scale2 ? scale1 : scale2;
		if(scale1 > scale2){ // 图比屏幕扁，以图的宽为限
			scale = mainPanelWidth / width ;
		} else{
			scale = mainPanelHeigth / height;
		}
		
		console.log(" _diagramShow="+JSON.stringify(_diagramShow));
		console.log("window.innerHeight="+window.innerHeight);
		// console.log("document.documentElement.clientHeight =
		// "+document.documentElement.clientHeight);
		// console.log("document.body.clientHeight =
		// "+document.body.clientHeight);
		
		// 计算translate和scale,调用 function interpolateZoom (translate, scale)
		var tx = _diagramShow.x + _diagramShow.width/2 * (1 - scale);
		var ty = _diagramShow.y + _diagramShow.height/2 * (1 - scale);
		// var trans = {_diagramShow.x - x,_diagramShow.y - y};
		console.log("scale="+scale+" tx="+tx+" ty="+ty+" x="+x+" y="+y+" x-ty="+(x-tx)+" y-ty="+(y-ty));
        // zoom
        // .scale(scale)
        // .translate([x- tx + 10, y- ty]);

		// svg.attr("transform",
	    // "translate(" + [x- tx + 10, y- ty] + ")" +
	    // "scale(" + scale + ")"
	    // );

		interpolateZoom([x- tx, y- ty], scale);
		
		// 计算缩放比
// var
// _y={"x":266,"y":1,"width":840,"height":58,"top":1,"right":1106,"bottom":59,"left":266}
// width=1062 heigth=669
// _diagramShow={"x":285.390625,"y":76,"width":801.21875,"height":674,"top":76,"right":1086.609375,"bottom":750,"left":285.390625}
		// 计算左上角坐标
		
		// 移动缩放图形
		
	}
}

$(window).resize(function(){
	fullScreenFn();
});

// 设置resize事件
addResizeEvent(fullScreenFn);
function addResizeEvent(func) {
    // 把现有的 window.onload 事件处理函数的值存入变量
    var oldResize = window.resize;
    if (typeof window.resize != "function") {
      // 如果这个处理函数还没有绑定任何函数，就像平时那样添加新函数
      window.onload = func;
    } else {
      // 如果处理函数已经绑定了一些函数，就把新函数添加到末尾
      window.resize = function() {
    	  oldResize();
        func();
      }
    }
}

d3.selectAll('button').on('click', zoomClick);


//// ********************* 对话框拖拽 ********
//var mouseX = 0;
//var mouseY = 0;
//var Dragging = function(validateHandler) { // 参数为验证点击区域是否为可移动区域，如果是返回欲移动元素，负责返回null
//	var draggingObj = null; // dragging Dialog
//	var diffX = 0;
//	var diffY = 0;
//
//	function mouseHandler(e) {
//		switch (e.type) {
//		case 'mousedown':
//			console.log("mousedown");
//			draggingObj = validateHandler(e);// 验证是否为可点击移动区域
//			if (draggingObj != null) {
//				diffX = e.clientX - draggingObj.offsetLeft;
//				diffY = e.clientY - draggingObj.offsetTop;
//			}
//			break;
//
//		case 'mousemove':
//			// console.log("e.clientX="+e.clientX+" e.clientY="+e.clientY);
//			mouseX = e.clientX;
//			mouseY = e.clientY;
//			if (draggingObj) {
//				draggingObj.style.left = (e.clientX - diffX) + 'px';
//				draggingObj.style.top = (e.clientY - diffY) + 'px';
//				if(draggingObj.nodeName =="svg")
//					console.log(" x,y => "+e.clientX+" "+e.clientY);
//			}
//
//			break;
//
//		case 'mouseup':
//			draggingObj = null;
//			diffX = 0;
//			diffY = 0;
//			break;
//		}
//	}
//	;
//
//	return {
//		enable : function() {
//			document.addEventListener('mousedown', mouseHandler);
//			document.addEventListener('mousemove', mouseHandler);
//			document.addEventListener('mouseup', mouseHandler);
//		},
//		disable : function() {
//			document.removeEventListener('mousedown', mouseHandler);
//			document.removeEventListener('mousemove', mouseHandler);
//			document.removeEventListener('mouseup', mouseHandler);
//		}
//	}
//}
//
//function getDraggingDialog(e) {
//	var target = e.target;
//	console.log(" 1 ->"+target);
//	console.log(" 2 ->"+target.nodeName);
//	console.log(" 3 ->"+target.nodeType + " " + target);
//	console.log(" 4 ->"+target.getAttribute("id"));
//	 if (target.nodeName != "DIV") return;
//	while (target && (target.className.indexOf('dialog-title')== -1 || 
//			target.getAttribute("id")!="toMenu" ||
//			target.nodeName !="svg"
//			))
//	{
//		target = target.offsetParent;
//		if(target.nodeName =="svg")
//			console.log("svg");
//	}
//	if(target!=null && target.nodeName =="svg")
//		return target;
//	else if (target != null) {
//		return target.offsetParent;
//	} else {
//		return null;
//	}
//}
//
//Dragging(getDraggingDialog).enable();
//
//
//function _addPointToXY(tagName) {
//	var pointsul = document.getElementById("xygraph_points_ui");
//	var pointli = document.createElement("li");
//	pointli.setAttribute("id", "pointli_" + tagName);
//	var lihtml = "";
//	lihtml += '<div class="form-check form-check-flat"><label class="form-check-label">';
//	lihtml += ' <input class="checkbox" type="checkbox">' + tagName;
//	lihtml += '</label></div> ';
//	lihtml += '<i class="remove mdi mdi-close-circle-outline" onclick="_removePointFromXY(\''
//			+ tagName + '\')"></i>';
//	pointli.innerHTML = lihtml;
//	pointsul.appendChild(pointli);
//
//	// addPointToXYGraph(tagName);
//}
//function _removePointFromXY(tagName) {
//	var pointsul = document.getElementById("pointli_" + tagName);
//	pointsul.parentNode.removeChild(pointsul);
//	removePointFromXYGraph(tagName);
//}
