/**
 * 
 */

/**
 * 悬浮菜单功能
 */
// 新建
var itemID = _routeID;
var actionType = _routeType;// "graph";

function doSearchAction(value) {
	if (value == null || value == "undefined") {
		var input_serachbox_topToolsBar = document
				.getElementById("input_serachbox_topToolsBar");
		value = input_serachbox_topToolsBar.value;
	}
	alert(value);

	input_serachbox_topToolsBar.value = null;
}
function newItemAction() {
	init();
	if (user.id == 2 || user.role == 1) {
		// alert("xyGraphList.newItemAction");
		document.getElementById("divUpLoadGraphFile").style.display = "block";// 显示
		$('#newItemAction_mid').modal('show');
	} else {
		alert("您没有权限进行新建操作。");
	}
	$("#graphFile")[0] = null;
	// console.log("ssws- "+JSON.stringify(userSpace.graph));
}

function editItemAction(itemId) {
	console.log("itemId11=" + itemId);
	// itemID = itemId;
	actionType = "graph";
	init();
	if (itemId != null && itemId != "undefined") {
		if (user.role == 1 || user.id == _graph.creater
				|| user.id == _graph.owner) {
			console.log();
			var _graph = getGraphByID(userSpace.graph, itemId);
			document.getElementById("divUpLoadGraphFile").style.display = "none";// 隐藏
			console.log("itemId=" + JSON.stringify(_graph));
			// 设置初始值。
			document.getElementById("_updateGraphTitle").value = "修改图形";

			// var gf = document.getElementById("graphFile");
			// gf.value = "";
			// gf.outerHTML = gf.outerHTML;
			var gt = document.getElementById("graphThumbnail");
			gt.value = "";
			gt.outerHTML = gt.outerHTML;
			var gd = document.getElementById("_graphDesc");
			// if(_graph.desc!=null && _graph.desc!="undefined")
			gd.value = _graph.desc;
			// else
			// gd.value = "";
			var gn = document.getElementById("_graphName");

			if (_graph.nickName != null && _graph.nickName != "undefined")
				gn.value = _graph.nickName;
			else
				gn.value = _graph.name;

			$('#newItemAction_mid').modal('show');
			console.log("editItemAction(itemId) itemId=" + itemId
					+ " _graphId=" + _graphId);
		} else {
			alert("您没有权限进行新建操作。");
			return;
		}
		editItem(itemId);

	} else {
		alert("没有指定的图形。");
	}

}

function editItem(itemId) {

}

function doShareActionToServer() {
	if (user == null || user == "undefined") {
		user = localStorage.user;
		uid = user.id;
		token = localStorage.token;
	}
	console.log("dataItemId=" + dataItemId + "  user:"
			+ JSON.stringify(Array.from(selectedUsers)));
	var data = {
		'uid' : uid,
		'token' : token,
		'id' : dataItemId,
		'userIds' : Array.from(selectedUsers),
		'type' : "graph"
	};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "shareRightGraph",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
				// console.log("server info : "+JSON.stringify(data.data.data));
				var graph = data.data.data;
				console.log("graph:=> " + JSON.stringify(graph));
				var _g = getGraphByID(userSpace.graph, graph.id);
				_g.shared = graph.shared;
				_g.sharedUsers = graph.sharedUsers;
				_g.owner = graph.owner;
				_g.ownerUser = graph.ownerUser;
				_g.creater = graph.creater;
				_g.createrUser = graph.createrUser;
				$('#shareItemAction_mid').modal('hide');
				updateGraphListFrame();
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
}

function deleteItemAction(itemId) {
	console.log("deleteItemAction uid=" + uid + " token=" + token + " id="
			+ itemId);
	var data = {
		'uid' : uid,
		'token' : token,
		'id' : itemId + ""
	};
	$.ajax({
		// 提交数据的类型 POST GET
		type : "POST",
		// 提交的网址
		url : "deleteGraphFile",
		// 提交的数据
		data : JSON.stringify(data),
		contentType : "application/json",
		// 返回数据的格式
		datatype : "json",// "xml", "html", "script", "json", "jsonp", "text".
		// 在请求之前调用的函数
		beforeSend : function() {
			showLoading();
		},
		// 成功返回之后调用的函数
		success : function(data) {
			if (data.status == "000") { // GlobalConsts.ResultCode_SUCCESS) {
				var graph = data.data.graph;
				var pg = getGraphByPath(userSpace.graph, graph.path);
				console.log("pg: " + JSON.stringify(pg));
				delete pg.children[graph.wholePath];
				console.log(graph.wholePath);
				console.log("pg: " + JSON.stringify(pg));
				updateGraphListFrame();
				// fixLocalGraphList_Delete(graphId);
				// if(data.refresh) routeTo('historydataList','');
				// 
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
			/* 弹出jqXHR对象的信息 */
			hideLoading();
		}
	});
}
// console.log("graph-> "+JSON.stringify(userSpace.graph));
var dataItemId;
function shareItemAction(itemId) {
	dataItemId = itemId;
	shareType = _routeType;// "graph";
	setParameter(shareType, itemId);
	$('#shareItemAction_mid').modal('show');
}
var _graphs;
var step = 0;

function getGraphByID(graph, id, l) {
	if (l == null || l == "undefined")
		l = 0;
	l++;
	var level = l;
	step++;
	// console.log(" ============ "+step+" === "+step+" === "+step+" === ");
	if (id == null || id == "undefined") {
		_graphs = graph;
		step++;
		// console.log("level="+level+" "+step+" = return path 1 : " +
		// graph.name+"
		// id="+graph.id);
		return graph;
	}
	if (graph.id == id) {
		_graphs = graph;
		step++;
		// console.log("level="+level+" "+step+" = 得到了结果 return path 2 : " +
		// graph.name+" id="+graph.id);
		return graph;
	}

	{
		var children = graph.children;
		if (children != null) {
			var ids = new Array();
			Object.keys(children).forEach(function(key) {
				ids.push(children[key]);
			});

			for (var i = 0; i < ids.length; i++) {
				var r = getGraphByID(ids[i], id, level);
				if (r != null) {
					step++;
					// console.log("level="+level+" "+step+" = 得到了结果 return path
					// 3 : " +
					// graph.name+" id="+graph.id);
					return r;
				}
			}
			step++;
			// console.log("level="+level+" "+step+" = null null 4 : " +
			// graph.name+"
			// id="+graph.id);
			return null;
		} else {
			step++;
			// console.log("level="+level+" "+step+" = null null 5 : " +
			// graph.name+"
			// id="+graph.id);
			return null;
		}
	}
}

// 默认图形目录主页。当存在主页时，直接打开主页。
var defaultSVG = [ "main", "index" ];
// 如果目录内只有一个页面，是否直接打开。默认是。
var loadOnlyGraph = true; // TODO: 设置参数
var isloadDefaultGraph = true; // TODO: 设置参数

/**
 * 加载默认主页
 * 
 * @param path
 *            当前路径
 * @returns
 */
function loadDefaultGraph() {
	if (!isloadDefaultGraph) {
		return;
	}
	var _galleryKey;
	// console.log("graph => "+JSON.stringify(userSpace.graph));
	if (_routeID == null || _routeID == "undefined" || _routeID == "") {
		_graph = userSpace.graph;
	} else {
		_graph = getGraphByID(userSpace.graph, _routeID);
	}

	var defaultG;
	var gCount = 0;
	// 取指定路径下的所有文件图形
	if (_graph != null && _graph.children != null
			&& _graph.children != "undefined") {
		for ( let key in _graph.children) {
			var _g = _graph.children[key];
			// console.log(" AAA1 ->
			// _g.name.toLowerCase()="+_g.name.toLowerCase() + "
			// _g.file="+_g.file +" _g.svg="+_g.svg);
			if (_g.file && _g.svg) {
				gCount++;
				defaultG = _g;
				for (var indDefaultSvg = 0; indDefaultSvg < defaultSVG.length; indDefaultSvg++) {
					// 是否存在defaultSVG里的主页，如果存在，就routeto
					// console.log(" AAA1 ->
					// _g.name.toLowerCase()="+_g.name.toLowerCase()+"
					// defaultSVG[indDefaultSvg].toLowerCase()="+defaultSVG[indDefaultSvg].toLowerCase());
					if (_g.name.toLowerCase() == defaultSVG[indDefaultSvg]
							.toLowerCase()) {
						routeTo('diagramDetail', _g.urlPath, _g.id);
						return;
					}
				}
			}
		}
		// 如果只有一个，是否loadOnlyGraph，如果是，就routto

		if (loadOnlyGraph && gCount == 1)
			routeTo('diagramDetail', defaultG.urlPath, defaultG.id);
	}
}
loadDefaultGraph();

updateGraphListFrame();

function updateGraphListFrame() {
	var _galleryKey = _routeID;
	// console.log("updateGraphListFrame_routeID= " + _routeID);
	// console.log("updateGraphListFrame_graphId= " + _graphId);
	// alert();

	// // TODO: 如果key为空，就是异常，待处理。
	// if (_galleryKey == null || _galleryKey == "undefined")
	// _galleryKey = "";
	//
	// if (_galleryKey.trim() == 'unclassify') {
	// alert(_galleryKey);
	// _galleryKey = "";
	// }

	if (_graphId == null || _graphId == "undefined") {
		_graphs = userSpace.graph;
	} else {
		_graphs = getGraphByID(userSpace.graph, _graphId);
		// console.log("debug graph => " + JSON.stringify(_g_));
	}

	var children = _graphs.children;

	var diagram_gallery = document.getElementById("diagram_gallery");
	var diagram_gallery_innerHTML = "";
	if (children == null || children == "undefined") {

	} else {
		Object
				.keys(children)
				.forEach(
						function(key) {
							// console.log(key+" =>
							// "+JSON.stringify(children[key]));
							var _graph = children[key];
							var diagram_gallery_item_innerHTML = '<div class="col-xl-2 col-lg-4 col-md-4 col-sm-6 col-12"';
							diagram_gallery_item_innerHTML += '>'; // 1
							if (_graph.file && _graph.svg) {
								diagram_gallery_item_innerHTML += '<figure class="effect-text-in">'; // 2
								diagram_gallery_item_innerHTML += '<div></div>';
								// - // 如果图的宽度大于可预览值，显示预览图形,不显示缩略图。
								// - // TODO: if()
								// - // diagram_gallery_item_innerHTML +=
								// '<button type="submit"
								// - // class="btn btn-info btn-sm icon-btn
								// ml-2" onclick="';
								// - // diagram_gallery_item_innerHTML +=
								// - // 'previewGraph(\''+_graph.id+'\')">';
								// - // diagram_gallery_item_innerHTML += '<i
								// class="mdi
								// - // mdi-edit">Preview</i>';
								// - // diagram_gallery_item_innerHTML +=
								// '</button>';

								diagram_gallery_item_innerHTML += '<img src="'
										+ _graph.img + '" alt="image" '; // 3
								diagram_gallery_item_innerHTML += ' onclick="routeTo(\'diagramDetail\',\''
										+ _graph.urlPath
										+ '\',\''
										+ _graph.id
										+ '\');"/>'; // 4
								diagram_gallery_item_innerHTML += '<figcaption';
								diagram_gallery_item_innerHTML += ' onclick="routeTo(\'diagramDetail\',\''
										+ _graph.urlPath
										+ '\',\''
										+ _graph.id
										+ '\');">'; // 5
								var desc = "";
								if (_graph.desc != null
										&& _graph.desc != "undefined") {
									desc = _graph.desc;
								} else {
									desc = _graph.path;
								}
								var nickName = "";
								if (_graph.nickName != null
										&& _graph.nickName != "undefined") {
									nickName = _graph.nickName;
								} else {
									nickName = _graph.name;
								}
								diagram_gallery_item_innerHTML += '<h4 style="color:#000000;font-weight:bold">'
										+ nickName
										+ "  "
										+ '</h4><p>'
										+ desc
										+ '</p></figcaption>';

								// if(_graph.nickName!=null&&_graph.nickName!="undefined"){
								// diagram_gallery_item_innerHTML += '<h4
								// style="color:#FFFF00;background-color=#556B2F">'
								// + _graph.nickName +" "+ '</h4><p>' + desc
								// + '</p></figcaption>';
								// }else{
								// diagram_gallery_item_innerHTML += '<h4
								// style="color:#FFFF00;background-color=#556B2F">'
								// + _graph.name +" "+ '</h4><p>' + desc
								// + '</p></figcaption>';
								// }
								diagram_gallery_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
								// 判断权限
								if (user.id == _graph.creater
										|| user.id == _graph.owner
										|| user.role == 1) {
									// diagram_gallery_item_innerHTML += '<i
									// class="icon-people icon-md"><i
									// class="icon-trash"></i><i
									// class="icon-note"></i><i
									// class="icon-share"></i>';
									diagram_gallery_item_innerHTML += '<div class="card-header">';
									diagram_gallery_item_innerHTML += '<i class="icon-people icon-md" onclick="';
									diagram_gallery_item_innerHTML += 'shareItemAction(\''
											+ _graph.id + '\')"> </i>';
									diagram_gallery_item_innerHTML += '<i class="icon-trash icon-md" data-toggle="modal" data-target="#exampleModal-3"';
									diagram_gallery_item_innerHTML += ' data-whatever=\''
											+ _graph.id + '\'>';
									diagram_gallery_item_innerHTML += '</i>';
									diagram_gallery_item_innerHTML += '<i class="icon-note icon-md" onclick="';
									diagram_gallery_item_innerHTML += 'editItemAction(\''
											+ _graph.id + '\')">';
									diagram_gallery_item_innerHTML += '</i></div>';
								}
								diagram_gallery_item_innerHTML += '</figure>';
								diagram_gallery_item_innerHTML += '</div>';

							} else {// 目录的处理
								diagram_gallery_item_innerHTML += '<figure class="effect-text-in">'; // 2
								diagram_gallery_item_innerHTML += '<div></div>';
								diagram_gallery_item_innerHTML += '<img src="'
										+ _graph.img + '" alt="image" '; // 3
								diagram_gallery_item_innerHTML += ' onclick="routeTo(\'diagramList\',\''
										+ _graph
										+ "'"
										+ ',\''
										+ _graph.id
										+ '\');">'; // 4
								diagram_gallery_item_innerHTML += '<figcaption';
								diagram_gallery_item_innerHTML += ' onclick="routeTo(\'diagramList\',\''
										+ _graph
										+ '\',\''
										+ _graph.id
										+ '\');">'; // 5
								var desc = "";
								if (_graph.desc != null
										&& _graph.desc != "undefined") {
									desc = _graph.desc;
								} else {
									desc = _graph.path;
								}
								var nickName = "";
								if (_graph.nickName != null
										&& _graph.nickName != "undefined") {
									nickName = _graph.nickName;
								} else {
									nickName = _graph.name;
								}
								diagram_gallery_item_innerHTML += '<h4 style="color:#000000;font-weight:bold">'
										+ nickName
										+ "  "
										+ '</h4><p>'
										+ desc
										+ '</p></figcaption>';
								diagram_gallery_item_innerHTML += '<div style="position: absolute;left: 10px; top: 10px;opacity:1;">';
								// 判断权限
								if (user.id == _graph.creater
										|| user.id == _graph.owner
										|| user.role == 1) {
									diagram_gallery_item_innerHTML += '<div class="card-header">';
									diagram_gallery_item_innerHTML += '<i class="icon-people icon-md" onclick="';
									diagram_gallery_item_innerHTML += 'shareItemAction(\''
											+ _graph.id + '\')">&nbsp;</i>';
									diagram_gallery_item_innerHTML += '<i class="icon-note icon-md" onclick="';
									diagram_gallery_item_innerHTML += 'editItemAction(\''
											+ _graph.id + '\')">';
									diagram_gallery_item_innerHTML += '</i></div>';
								}
								diagram_gallery_item_innerHTML += '</figure></div>';
								// diagram_gallery_item_innerHTML +=
								// '</figure>';

							}
							diagram_gallery_item_innerHTML += '</div></div>';
							diagram_gallery_innerHTML = diagram_gallery_innerHTML
									+ diagram_gallery_item_innerHTML;

						});
	}
	diagram_gallery.innerHTML = diagram_gallery_innerHTML;
}

function ajaxGraphFile(url, callback, reject) {
	var formData1 = new FormData();
	var file1 = $("#graphFile")[0].files[0];
	formData1.append("file", file1);
	formData1.append("path", encodeURI(_graphs.path));
	console.log("_graphs.path1" + _graphs.path);
	formData1.append("url", url);
	formData1.append("uid", uid);
	formData1.append("token", token);
	var name = document.getElementById("_graphName").value;
	formData1.append("name", name);
	var desc = $("#_graphDesc").val();// document.getElementById("_graphDesc").innerHTML;//不知道为什么，用原生的取不到值。
	formData1.append("desc", desc);
	console.log(name + " - " + desc + " - " + _graphs.path + " - " + uid
			+ " - " + token + " - " + url);
	// consoel.log(aa);
	console.log("formData1-> " + JSON.stringify(formData1));
	$.ajax({
		url : 'uploadGraphFile',
		type : 'POST',
		cache : false,
		processData : false,// 不处理数据
		data : formData1,// 直接把formData这个对象传过去
		dataType : 'json',
		contentType : false,// 主要设置你发送给服务器的格式,设为false代表不设置内容类型
		success : function(data) {
			if (data.status == "000") {
				var g = data.data.graph
				// console.log("return g = "+JSON.stringify(g));
				callback(data.data.graph);
			} else {
				alert(data.msg);
			}
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			reject(textStatus);
			hideLoading();
		}

	});
	console.log("doUpLoad 3");
}

function ajaxGraphThumbnail(callback, reject) {
	var formData = new FormData();
	var file = $("#graphThumbnail")[0].files[0];
	formData.append("file", file);
	$.ajax({
		url : 'uploadThumbnail',
		type : 'POST',
		cache : false,
		processData : false,// 不处理数据
		data : formData,// 直接把formData这个对象传过去
		dataType : 'json',
		contentType : false,// 主要设置你发送给服务器的格式,设为false代表不设置内容类型
		success : function(data) {
			callback(data);
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			reject(textStatus);
		}

	});
	console.log("doUpLoad 2");
}

function ajaxUpdateGraphInfo(url, callback, reject) {
	var data = new FormData();
	data.append("url", url);
	data.append("uid", uid);
	data.append("token", token);
	var name = document.getElementById("_graphName").value;
	data.append("name", name);
	var desc = $("#_graphDesc").val();// document.getElementById("_graphDesc").innerHTML;//不知道为什么，用原生的取不到值。
	data.append("desc", desc);
	data.append("id", _graphs.id);
	console.log("desc=" + desc);
	$.ajax({
		url : 'updateGraphInfo',
		type : 'POST',
		cache : false,
		processData : false,// 不处理数据
		data : data,// 直接把formData这个对象传过去
		dataType : 'json',
		contentType : false,// 主要设置你发送给服务器的格式,设为false代表不设置内容类型
		success : function(rdata) {
			// console.log("return data = "+JSON.stringify(rdata));

			callback(rdata);
		},
		// 调用出错执行的函数
		error : function(jqXHR, textStatus, errorThrown) {
			reject(textStatus);
		}
	});
}

function doUpLoad() {
	var file1 = $("#graphFile")[0].files[0];
	// 如果没有图形文件，就是编辑
	if (file1 == "null" || (file1 + "") == "undefined") {
		// 名称、描述、缩略图都不限制
		// 如果有缩略图，就上传。如果没有
		var file = $("#graphThumbnail")[0].files[0];
		if (file != "null" && (file + "") != "undefined") {
			var promise = new Promise(function(resolve, reject) {
				showLoading();
				// 发送ajax 获取第一份数据 (我们需要这份数据里面的key值 要带着这个key去请求pay.json)
				ajaxGraphThumbnail(resolve, reject);
			});
			promise.then(function(value) {
				ajaxUpdateGraphInfo(value.data.url, function(value) {
					// console.log("v="+JSON.stringify(value));
					console.log("doUpLoad _routeID= " + _routeID);
					console.log("doUpLoad _graphId= " + _graphId);
					console.log(" AA-> " + JSON.stringify(value.data.graph));
					fixGraphList(value.data.graph);

				}, function(err) {

				});
			}, function(err) {

			});

			console.log("doUpLoad 1 _routeID= " + _routeID);
			console.log("doUpLoad 1 _graphId= " + _graphId);
			hideLoading();
			return;
		} else { // 如果没有图形，就是只改名字、描述。
			console.log("没有图形，只修改名字描述。");
			ajaxUpdateGraphInfo(null, function(value) {
				// console.log("v="+JSON.stringify(value));
				// alert();
				console.log("doUpLoad 2 _routeID= " + _routeID);
				console.log("doUpLoad 3 _graphId= " + _graphId);
				console.log(" AA-> " + JSON.stringify(value.data.graph));
				fixGraphList(value.data.graph);
				// console.log("1");
				// alert();
			}, function(err) {

			});
			return;
		}
	} else {
		console.log("file1不为空，继续执行上传图形文件。");
		console.log("" + file1);
	}
	console.log("2");
	// alert();
	var wholePath = _graphs.path + "\\" + file1.name;
	console.log("wholePath = " + wholePath);
	var og = getGraphByPath(userSpace.graph, wholePath);
	if (og != null && og != "undefined") {
		alert("当前目录下存在同名文件，请重新选择其他文件，或删除该文件后再重新上传。");
		console.log("当前目录下存在同名文件:" + JSON.stringify(og));
		return;
	}
	var graphThumbnailFile = $("#graphThumbnail")[0].files[0];
	if (graphThumbnailFile != null && graphThumbnailFile != "undefined") {
		var promise = new Promise(function(resolve, reject) {
			showLoading();
			// 发送ajax 获取第一份数据 (我们需要这份数据里面的key值 要带着这个key去请求pay.json)
			ajaxGraphThumbnail(resolve, reject);
		});
		promise
				.then(function(value) {
					// 这里 可以接收到上一步 用 resolve处理的数据
					// console.log('第二步接收到的key:', response)
					// 发送ajax 带上这个key 去请求另一个接口 pay.json
					// console.log("then..."+JSON.stringify(value));
					return new Promise(function(resolve, reject) {
						ajaxGraphFile(value.data.url, resolve, reject);
					})
				}, function(err) {
					console.log("err=" + err);
					hideLoading()
				})
				.then(
						function(response) {
							// 这里 可以接收到上一步 用 resolve处理的数据
							console.log('第三步接收到的数据:', response);
							// console.log("ssws-
							// "+JSON.stringify(userSpace.graph));

							var g = getGraphByPath(userSpace.graph,
									_graphs.path);
							g.children[response.wholePath] = response;
							updateGraphListFrame();
							init();
							hideLoading();
							$('#newItemAction_mid').modal('hide');
						},
						function(err) {
							// 这里 可以接收到上一步 用 resolve处理的数据
							console.log('err:', err)
							hideLoading();
							$('#newItemAction_mid').modal('hide');
							document.getElementById("divUpLoadGraphFile").style.display = "block";// 显示

						});
	} else { // 如果没有缩略图
		var promise = new Promise(function(resolve, reject) {
			showLoading();
			// 发送ajax 获取第一份数据 (我们需要这份数据里面的key值 要带着这个key去请求pay.json)
			ajaxGraphFile('', resolve, reject);
		});
		promise
				.then(
						function(response) {
							// 这里 可以接收到上一步 用 resolve处理的数据
							console.log('第三步接收到的数据:', response);
							// console.log("ssws-
							// "+JSON.stringify(userSpace.graph));

							var g = getGraphByPath(userSpace.graph,
									_graphs.path);
							g.children[response.wholePath] = response;
							updateGraphListFrame();
							init();
							hideLoading();
							$('#newItemAction_mid').modal('hide');
						},
						function(err) {
							console.log('err:', err)
							hideLoading();
							$('#newItemAction_mid').modal('hide');
							document.getElementById("divUpLoadGraphFile").style.display = "block";// 显示
						});
	}

}

/**
 * 把graph中，把_graph的ID或目录所指定的图，换成_graph。
 * 
 * @param graph
 * @param _graph
 * @returns
 */
function setGraph(graphs, _graph) {
	// TODO:
}

function fixGraphList(response) {
	// console.log("fixGraphList 1 _routeID= "+_routeID);
	// console.log("fixGraphList 1 _graphId= "+_graphId);
	$('#newItemAction_mid').modal('hide');
	// console.log("fixGraphList graph= "+JSON.stringify(userSpace.graph));
	// console.log("fixGraphList response= "+JSON.stringify(response));
	// TODO: 新增一个方法，setGraph。现在下面这部分是有问题的，没有更新userSpace.graph。需要修改。

	var g = null;
	// 如果是文件，下面的正确
	g = getParentGraphByPath(userSpace.graph, response.wholePath);
	// 如果是目录

	console.log("AA -> fixGraphList g= " + JSON.stringify(g));
	console.log("AA -> 0 fixGraphList response.wholePath= "
			+ response.wholePath);
	console.log("AA -> 1 fixGraphList g.children[response.wholePath]= "
			+ JSON.stringify(g.children[response.wholePath]));
	g.children[response.wholePath] = response;
	console.log("AA -> 2 fixGraphList g.children[response.wholePath]= "
			+ JSON.stringify(g.children[response.wholePath]));
	console.log("AA -> fixGraphList response= " + JSON.stringify(response));
	console.log("AA -> fixGraphList g= " + JSON.stringify(g));
	// console.log("fixGraphList 2 _routeID= "+_routeID);
	// console.log("fixGraphList 2 _graphId= "+_graphId);
	// 更新主窗口列表
	updateGraphListFrame();
	// 更新导航栏
	updateDiagram();
	init();
	hideLoading();
}

function init() {
	document.getElementById("_updateGraphTitle").value = "上传图形";
	var gf = document.getElementById("graphFile");
	gf.value = "";
	gf.outerHTML = gf.outerHTML;
	var gt = document.getElementById("graphThumbnail");
	gt.value = "";
	gt.outerHTML = gt.outerHTML;
	var gd = document.getElementById("_graphDesc");
	gd.value = "";
	var gn = document.getElementById("_graphName");
	gn.value = "";
}

function getGraphByPath(graph, path) {
	if (path == null || path == "undefined")
		return graph;
	// console.log("graph.wholePath="+graph.wholePath.toLowerCase()+" ==
	// "+"path="+path.toLowerCase());
	if (graph.wholePath.toLowerCase().indexOf(path.toLowerCase()) != -1) {
		// console.log("找到了");
		// _graphId = graph.id;
		return graph;
	} else {
		// console.log(graph.wholePath.toLowerCase()+" 3");
		var children = graph.children;
		if (children != null) {
			var keys = new Array();
			Object.keys(children).forEach(function(key) {
				// console.log("4"+key);
				keys.push(key);
				children[key]
			});
			for (var i = 0; i < keys.length; i++) {
				var child = children[keys[i]];
				var t = getGraphByPath(child, path);
				if (t != null)
					return t;
			}
		}
		return null;
	}
}

function getParentGraphByPath(graph, path) {
	if (path == null || path == "undefined")
		return graph;
	// console.log("graph.wholePath="+graph.wholePath.toLowerCase()+" ==
	// "+"path="+path.toLowerCase());
	{
		// console.log(graph.wholePath.toLowerCase()+" 3");
		var children = graph.children;
		if (children != null) {
			var keys = new Array();
			Object.keys(children).forEach(function(key) {
				// console.log("4"+key);
				keys.push(key);
				children[key]
			});
			for (var i = 0; i < keys.length; i++) {
				var child = children[keys[i]];
				if (child.wholePath.toLowerCase() == path.toLowerCase())
					return graph;

				if (path.toLowerCase().indexOf(child.wholePath.toLowerCase()) != -1) {
					var t = getParentGraphByPath(child, path);
					if (t != null)
						return t;
				}
			}
		}
		return null;
	}
}
