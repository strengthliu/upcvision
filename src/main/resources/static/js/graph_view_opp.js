/**
 * 
 */

// ********************* 对话框拖拽 ********
var mouseX = 0;
var mouseY = 0;
var Dragging = function(validateHandler) {
	// 参数为验证点击区域是否为可移动区域，如果是返回欲移动元素，负责返回null
	var draggingObj = null; // dragging Dialog
	var diffX = 0;
	var diffY = 0;

	function mouseHandler(e) {
		switch (e.type) {
		case 'mousedown':
			draggingObj = validateHandler(e);// 验证是否为可点击移动区域
			if (draggingObj != null) {
				diffX = e.clientX - draggingObj.offsetLeft;
				diffY = e.clientY - draggingObj.offsetTop;
			}
			break;

		case 'mousemove':
			mouseX = e.clientX;
			mouseY = e.clientY;
			if (draggingObj) {
				draggingObj.style.left = (e.clientX - diffX) + 'px';
				draggingObj.style.top = (e.clientY - diffY) + 'px';
			}
			break;

		case 'mouseup':
			draggingObj = null;
			diffX = 0;
			diffY = 0;
			break;
		}
		return {
			enable : function() {
				document.addEventListener('mousedown', mouseHandler);
				document.addEventListener('mousemove', mouseHandler);
				document.addEventListener('mouseup', mouseHandler);
			},
			disable : function() {
				document.removeEventListener('mousedown', mouseHandler);
				document.removeEventListener('mousemove', mouseHandler);
				document.removeEventListener('mouseup', mouseHandler);
			}
		}
	}
}

	function getDraggingDialog(e) {
		var target = e.target;
		if (target.nodeName != "DIV")
			return;
		while (target
				&& (target.className.indexOf('dialog-title') == -1
						|| target.getAttribute("id") != "toMenu" || target.nodeName != "svg")) {
			target = target.offsetParent;
		}
		if (target != null && target.nodeName == "svg")
			return target;
		else if (target != null) {
			return target.offsetParent;
		} else {
			return null;
		}
	}


// Dragging(getDraggingDialog).enable();
function _IsNaN(value) {
	if(value == null || value == "undefined")
		return false;
	return typeof value === 'number' && !isNaN(value);
}
	  
function initView() {
// console.log("graph_view_opp.js ---> initView ---> 1");
	// 获取图形宽高
	
	var _diagramShow = document.getElementsByTagName("_diagramShow");
// console.log("graph_view_opp.js -> 加载的图形文件内容为： "+_diagramShow.innerHTML);
	var __svg1 = document.getElementsByTagName("svg");
// console.log("graph_view_opp.js ---> initView ---> 2");
// console.log("graph_view_opp.js->initView => svg1= "+__svg1+"
// asJson="+JSON.stringify(__svg1)+" length="+__svg1.length);
// console.log("graph_view_opp.js ---> initView ---> 3");
	svg1 = __svg1[0];
// console.log("----------------------------");
// console.log(svg1);
// console.log("----------------------------");
// console.log("graph_view_opp.js ---> initView ---> 4");
// console.log("graph_view_opp.js->initView => 2 svg1= "+svg1+"
// asJson="+JSON.stringify(svg1));
	width = svg1.attributes.width.value;
// console.log("graph_view_opp.js ---> initView ---> 5");
	height = svg1.attributes.height.value;
// console.log("graph_view_opp.js ---> initView ---> 6");
	var mainToolsBarRect = svg1.getBoundingClientRect();
// console.log("initView -> mainPanel BarRect -> "+ mainToolsBarRect.top+" "+
// mainToolsBarRect.left+" "+ mainToolsBarRect.width+" "+
// mainToolsBarRect.height+" ");
	if(!_IsNaN(width))
		width = mainToolsBarRect.width;
	if(!_IsNaN(height))
		height = mainToolsBarRect.height;

	// console.log("===>svg x="+mainToolsBarRect.left+"
	// y="+mainToolsBarRect.top+"
	// width="+mainToolsBarRect.attributes.width.value+"
	// height="+mainToolsBarRect.attributes.height.value);
	var trans;
	// D3操作对象
	zoom = d3.behavior.zoom().scaleExtent([ 0.5, 8 ]).on("zoom", null);
	svg = d3.select("svg").call(zoom);
	drag = d3.behavior.drag().origin(function() {
		var t = d3.select(this);
		return {
			// 返回被拖拽对象的cx和cy。
			x : t.attr("cx"),
			y : t.attr("cy")
		};
	}).on("drag", draged).on("dragstart", function() {
		d3.event.sourceEvent.stopPropagation(); // silence other listeners
	}).on("dragend", function() {
		translate = trans; // 拖拽结束时，更新translate
	});

	var trans;
	svg = d3.select("svg").call(drag);// .call(zoom);
	function draged(d) {
		// zoom.translate(translate);
		// d3.select(this).attr("cx", d3.event.x).attr("cy", d3.event.y);
		if (translate != null && translate != "undefined") {
// console.log("-------------------------------");
// console.log("translate-> " + translate);
// console.log("translate event -> " + [ d3.event.x, d3.event.y ]);
			trans = [ d3.event.x, d3.event.y ];
// console.log();
			// 增加translate[0]的偏移
			trans = [ d3.event.x + translate[0], d3.event.y + translate[1] ];
		} else {
			trans = [ d3.event.x, d3.event.y ];
		}
// console.log("translate target -> " + trans);
		svg.attr("transform", "translate(" + trans + ")" + "scale(" + scale
				+ ")");

		// translate = trans;
		// zoom.translate(trans);
		// interpolateZoom(translate,scale);
	}
	var diff = Math.round(((scale - 1) * 1000 + 600) / 100);
	// Range Slider
	if ($("#value-range").length) {
		var bigValueSlider = document.getElementById('value-range'), bigValueSpan = document
				.getElementById('huge-value');
		noUiSlider.create(bigValueSlider, {
			start : 7,
			step : 0,
			range : {
				min : 0,
				max : 14
			}
		});

		var range = [ '0.3', '0.4', '0.5', '0.6', '0.77', '0.8', '0.9', '1',
				'1.1', '1.2', '1.3', '1.4', '1.5', '1.6', '1.7' ];
		bigValueSlider.noUiSlider.on('update', function(values, handle) {

			// console.log(" noUiSlider.on('update' ->
			// "+range[Math.floor(values)]+"
			// bigValueSpan.innerHTML="+bigValueSpan.innerHTML);
			if (bigValueSpan.innerHTML == ""
					|| bigValueSpan.innerHTML == "undefined")
				bigValueSpan.innerHTML = 1;
			// 之前的倍数
			var _num = parseFloat(bigValueSpan.innerHTML);
			var _num2 = 1 + (values - 7)/10; // 新倍数
			_num2 = _num2.toFixed(2);
			if (_num != _num2) {
// console.log("_num=" + _num + " values=" + values + " _num2="
// + _num2 + " scale=" + scale);
				bigValueSpan.innerHTML = scale;// range[Math.floor(values)];
				scale = _num2;
// console.log();
				interpolateZoom(translate, scale);
// interpolateZoom([ 0, 0 ], scale);
// console.log("_num=" + _num + " values=" + values + " _num2="
// + _num2 + " scale=" + scale + " ------");
			}
			// console.log("number = " + number1);
			// svg.attr("transform", "translate(" + [ 0, 0 ] + ")" + "scale(" +
			// scale+ ")");

		});

// console.log("set _slider.");
		_slider = bigValueSlider.noUiSlider;
		// _slider.set(5.32);
	}

	fullScreen();
// addOnClickEvent();
	
}

function scrollTo(lab) {
// console.log("lab=" + lab);
// console.log("scale=" + scale);
	if (lab == null || lab == "undefined") {
		// lab = scale;
		lab = (scale - 1) * 10 + 7;
		lab = lab.toFixed(2);
		// lab = Math.round(((scale - 1) * 1000 + 600) / 100);
	}
// interpolateZoom(translate,scale);
// console.log("lab=" + lab);
	var bigValueSpan = document.getElementById('huge-value');
	if (bigValueSpan!=null && (bigValueSpan.innerHTML == "" || bigValueSpan.innerHTML == "undefined"))
		bigValueSpan.innerHTML = 1;
	var _num = parseFloat(bigValueSpan.innerHTML);
// console.log("scrollTo-> " + lab + " scale=" + scale);
	var _num2 = lab;
	if (_num != _num2) {
		_slider.set(lab);
	}

}

var number1 = 0;
// function shownumber1() {
// // console.log("number = " + number1);
// scale = 1 + (number1 - 600) / 1000;
// interpolateZoom([ 0, 0 ], scale);
// // interpolateZoom(translate, scale);
// // svg.attr("transform", "translate(" + [ 0, 0 ] + ")" + "scale(" + scale+
// // ")");
// }

var svg1;
var svg;// svg = document.getElementsByTagName("svg");
var width = 0;// svg.attributes.width.value;
var height = 0;// svg.attributes.height.value;

// 上面在加载后，还有一句设置，应该是那里设置才最合理。
// 结果我在这里也设置了，加载后的设置值把这个给替换了。
// 调试了4个小时才找到，这里值得记录一下。
var zoom;// = d3.behavior.zoom().scaleExtent([0.1, 8]).on("zoom", zoomed);

/**
 * 真实执行缩放和移动
 * 
 * @returns
 */
function zoomed() {
	if(translate==null||translate=="undefined")
		translate=[0,0];
	svg.attr("transform", "translate(" + translate + ")" + "scale(" + scale
			+ ")");
	scrollTo(); // 高速缩放拖拽轴
}

/**
 * 移动和缩放指定值。
 * 
 * @param _translate
 * @param _scale
 * @returns
 */
function interpolateZoom(_translate, _scale) {
	if (_scale != null && _scale != "undefined")
		scale = _scale;
	if (_translate != null && _translate != "undefined")
		translate = _translate;
	if(translate==null||translate=="undefined")
		translate=[0,0];
// console.log("translate="+JSON.stringify(translate));
	zoomed();
}

/**
 * 点击事件处理
 * 
 * @returns
 */
function zoomClick() {
	switch (this.id) {
	case 'zoom_up':
		translate = [ translate[0], translate[1] - 10 ];
		zoomed();
		return;
	case 'zoom_down':
		translate = [ translate[0], translate[1] + 10 ];
		zoomed();
		return;
	case 'zoom_left':
		translate = [ translate[0] - 10, translate[1] ];
		zoomed();
		return;
	case 'zoom_right':
		translate = [ translate[0] + 10, translate[1] ];
		zoomed();
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

}

var scale;
var translate = [0,0]; // 当前位移，相对原图原点
var fullScreenEnable = true;
function fullScreen() {
	if (!fullScreenEnable)
		fullScreenEnable = !fullScreenEnable;
	fullScreenFn();
	fullScreenFn();
	fullScreenFn();
}

function fullScreenFn() {
	if (fullScreenEnable) {
		// 取当前页面宽度和高度
		var _topToolsBar = document.getElementById("topToolsBar");
		var topToolsBarRect = _topToolsBar.getBoundingClientRect();
		// console.log("topToolsBarRect -> "+ topToolsBarRect.top+" "+
		// topToolsBarRect.left+" "+ topToolsBarRect.width+" "+
		// topToolsBarRect.height+" ");
		// console.log("topToolsBarRect leftButton -> x="+
		// topToolsBarRect.left+" y="+ (topToolsBarRect.top +
		// topToolsBarRect.height)+" ");

		var _svgP = document.getElementById("mainPanel");// svg1.parentNode;
		var mainToolsBarRect = _svgP.getBoundingClientRect();
// console.log("mainPanel BarRect -> "+ mainToolsBarRect.top+" "+
// mainToolsBarRect.left+" "+ mainToolsBarRect.width+" "+
// mainToolsBarRect.height+" ");
		// 取当前图形宽度和高度
		// mainPanel屏幕左上角坐标
		var y = mainToolsBarRect.top;// + mainToolsBarRect.height;
		var x = mainToolsBarRect.left;
		var mainPanelHeight = window.innerHeight - topToolsBarRect.height;
		var mainPanelWidth = mainToolsBarRect.width;

		var scale1 = mainPanelHeight / mainPanelWidth;
// console.log("fullScreen => mainPanelHeight= "+ mainPanelHeight+"
// mainPanelWidth= "+ mainPanelWidth);
		var scale2 = height / width;
// console.log("fullScreen => svg height= "+ height+" width= "+ width);
		if (scale1 > scale2) { // 图比屏幕扁，以图的宽为限
			scale = mainPanelWidth / width;
		} else {
			scale = mainPanelHeight / height;
		}

		var svgx = svg1.getBoundingClientRect().left;
		var svgy = svg1.getBoundingClientRect().top;
// console.log("fullScreen => svgx= "+ svgx+" svgy= "+ svgy+" scale="+scale);

		// 计算translate和scale,调用 function interpolateZoom (translate, scale)
		var tx = svg.attr("width") / 2 * (1 - scale);
		var ty = svg.attr("height") / 2 * (1 - scale);
		if (translate == null || translate == "undefined")
			translate = zoom.translate();

		var _ttx = mainToolsBarRect.left - svgx + translate[0];// +
		var _tty = mainToolsBarRect.top - svgy + translate[1];// +
// console.log("translate ->"+[ _ttx, _tty ]);
// console.log("scale -> "+scale);
		// console.log("fullScreen => _tty-> "+_tty+"
		// mainToolsBarRect.left="+mainToolsBarRect.left+" svgx="+svgx+"
		// translate[0]="+translate[0]);
		interpolateZoom([ _ttx, _tty ], scale);

		svg.attr("preserveAspectRatio", "none");
		svg1.removeAttribute("width");
		svg1.removeAttribute("heigth");
		svg1.setAttribute("width", mainPanelWidth / scale);
		svg1.setAttribute("heigth", mainPanelHeight / scale);

		svgx = svg1.getBoundingClientRect().left;
		svgy = svg1.getBoundingClientRect().top;
	}
}

function adjustToFreezeWidth(rootSvg) {
	var windowWidth = $(window).width();

	var viewBoxVal = rootSvg.getAttribute("viewBox");
	var viewBoxWidth = viewBoxVal.split(",")[2];
	var viewBoxHeight = viewBoxVal.split(",")[3];
	rootSvg.removeAttribute("width");
	rootSvg.removeAttribute("height");

	var setWidth = windowWidth;
	var setHeight = (setWidth * viewBoxHeight) / viewBoxWidth;
	rootSvg.setAttribute("width", setWidth);
	rootSvg.setAttribute("height", setHeight);
}

function adjustToNone(rootSvg) {

	var viewBoxVal = rootSvg.getAttribute("viewBox");
	var viewBoxWidth = viewBoxVal.split(",")[2];
	var viewBoxHeight = viewBoxVal.split(",")[3];
	rootSvg.removeAttribute("width");
	rootSvg.removeAttribute("height");

	rootSvg.setAttribute("width", viewBoxWidth);
	rootSvg.setAttribute("height", viewBoxHeight);

}

function adjustToFreezeHeight(rootSvg) {

	var windowHeight = $(window).height();

	var viewBoxVal = rootSvg.getAttribute("viewBox");
	var viewBoxWidth = viewBoxVal.split(",")[2];
	var viewBoxHeight = viewBoxVal.split(",")[3];
	rootSvg.removeAttribute("width");
	rootSvg.removeAttribute("height");

	var setHeight = windowHeight;
	var setWidth = (setHeight * viewBoxWidth) / viewBoxHeight;
	rootSvg.setAttribute("width", setWidth);
	rootSvg.setAttribute("height", setHeight);
}

function adjustToFreezeAll() {

	var windowHeight = $(window).height();
	var windowWidth = $(window).width();

	rootSvg.removeAttribute("width");
	rootSvg.removeAttribute("height");

	rootSvg.setAttribute("width", windowWidth);
	rootSvg.setAttribute("height", windowHeight);

}

$(window).resize(function() {
// console.log("fullScreen on window.resize(). ");
	fullScreen();
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
