/**
 * 
 */
	/* 刷新流程图目录结构*/
	function updateDiagram() {
		console.log("updateDiagram");
		if (userSpace != null && userSpace != "undefined") {
			// console.log("userSpace is not null");
			doUpdateDiagram(userSpace);
		} else {
			getUserSpace(
					user.id,
					token,
					doUpdateDiagram);
		}
	}

	function doUpdateDiagram(userSpace){
		if (userSpace == null || userSpace == "undefined") {
			// console.log("userSpace is null");
			return;
		}
		
		//// console.log(JSON.stringify(userSpace));
		/* 刷新流程图目录结构*/
		var diagrams = userSpace.graph.children;
//		console.log("diagrams = "+JSON.stringify(diagrams))
		if(diagrams == null || diagrams == "undefined") diagrams = {};
		var uiDiagram = document.getElementById("ui-diagram");

		//if (uiDiagram != null && uiDiagram != "undefined")
		//	// console.log(uiDiagram.innerHTML);
//		var itemsHtml = '<ul class="nav flex-column sub-menu">';
//		Object
//				.keys(diagrams)
//				.forEach(
//						function(key) {
//// console.log("======= debug 1");
//							if (key != null 
//									&& key != "undefined") {
//								var diagram = diagrams[key];
//								if(key == "") key = "未分类";
//								var item = '<li class="nav-item"><text class="nav-link" onclick="routeTo('+"'diagramList','"+key+"','"+diagram.id+"');"+'">'
//										+ key + '</text></li>';
//								itemsHtml = itemsHtml + item;
//								// // console.log(key,obj[key]);
//							}
//						});
//		// console.log("======= debug 2");
//
//		itemsHtml = itemsHtml + "</ul>";
//		uiDiagram.innerHTML = itemsHtml;//"<ul></ul>";
//		//// console.log(uiDiagram.innerHTML);


	}
	
	/* 刷新XY图结构*/
	function updateXYDiagram() {
		if (userSpace != null && userSpace != "undefined") {
			// console.log("userSpace is not null");
			doUpdateXYDiagram(userSpace);
		} else {
			console.log(" navagator.updatXYDiagram : userSpace为空，调用getUserSpace。");
			getUserSpace(
					user.id,
					token,
					doUpdateXYDiagram);
		}
	}

	function doUpdateXYDiagram(userSpace){
		if (userSpace == null || userSpace == "undefined") {
			// console.log("userSpace is null");
			return;
		}
		/* 刷新XY图结构*/
		var xydiagrams = userSpace.xyGraph;
		if(xydiagrams == null || xydiagrams == "undefined") xydiagrams = {};
		var uixydiagram = document.getElementById("ui-xydiagram");

		//if (uixydiagram != null && uixydiagram != "undefined")
		//	// console.log(uixydiagram.innerHTML);
		var itemsHtml = '<ul class="nav flex-column sub-menu">';
		
		Object
				.keys(xydiagrams)
				.forEach(
						function(key) {
							// console.log("======= debug 3");
							if (key != null 
									&& key != "undefined") {
								var xydiagram = xydiagrams[key];
								if(key == "") key = "未分类";

								var item = '<li class="nav-item" onclick="routeTo('+"'xyGraphDetail','"+key+"');"+'"><text class="nav-link">'
										+ xydiagram.name + '</text></li>';
								itemsHtml = itemsHtml + item;
								// // console.log(key,obj[key]);
							}
						});
		// console.log("======= debug 4");

		itemsHtml = itemsHtml + "</ul>";
		uixydiagram.innerHTML = itemsHtml;//"<ul></ul>";
		//// console.log(uixydiagram.innerHTML);

		

	}
	
	
	
	/* 刷新实时数据结构*/
	function updateRealTimeData() {
		if (userSpace != null && userSpace != "undefined") {
			// console.log("userSpace is not null");
			// console.log("userSpace "+JSON.stringify(userSpace.realTimeData));
			doUpdateRealTimeData(userSpace);
		} else {
			// console.log("userSpace is null.");
		getUserSpace(
				user.id,
				token,
				doUpdateRealTimeData);
		}
	}

	function doUpdateRealTimeData(userSpace){
		if (userSpace == null || userSpace == "undefined") {
			// console.log("userSpace is null");
			return;
		}
		/* 刷新实时数据结构*/
		var realtimedatas = userSpace.realTimeData;
		if(realtimedatas == null || realtimedatas == "undefined") realtimedatas = {};
		var uirealtimedata = document.getElementById("ui-realtimedata");

		//if (uirealtimedata != null && uirealtimedata != "undefined")
		//	// console.log(uirealtimedata.innerHTML);
		var itemsHtml = '<ul class="nav flex-column sub-menu">';
		Object
				.keys(realtimedatas)
				.forEach(
						function(key) {
							// console.log("======= debug 5");
							if (key != null 
									&& key != "undefined") {
								var realtimedata = realtimedatas[key];
								if(key == "") key = "未分类";

								var item = '<li class="nav-item"><text class="nav-link" onclick="routeTo('+"'realtimedataDetail','"+key+"');"+'">'
										+ realtimedata.name + '</text></li>';
								itemsHtml = itemsHtml + item;
								//// console.log(key,realtimedatas[key]);
							}
						});
		// console.log("======= debug -5");

		itemsHtml = itemsHtml + "</ul>";
		uirealtimedata.innerHTML = itemsHtml;//"<ul></ul>";
		//// console.log("debug");
		//// console.log(uirealtimedata.innerHTML);
	}
	
	
	/* 刷新报警结构*/
	function updateAlertData() {
		if (userSpace != null && userSpace != "undefined") {
			// console.log("userSpace is not null");
			doUpdateAlertData(userSpace);
		} else 
		getUserSpace(
				user.id,
				token,
				doUpdateAlertData);
	}

	function doUpdateAlertData(userSpace){
		if (userSpace == null || userSpace == "undefined") {
			// console.log("userSpace is null");
			return;
		}

		/* 刷新报警结构*/
		var alertdatas = userSpace.alertData;
		if(alertdatas == null || alertdatas == "undefined") alertdatas = {};
		var uialertdata = document.getElementById("ui-alertdata");

		//if (uialertdata != null && uialertdata != "undefined")
		//	// console.log(uialertdata.innerHTML);
		var itemsHtml = '<ul class="nav flex-column sub-menu">';
		Object
				.keys(alertdatas)
				.forEach(
						function(key) {
							// console.log("======= debug 6");

							if (key != null 
									&& key != "undefined") {
								var alertdata = alertdatas[key];
								if(key == "") key = "未分类";

								var item = '<li class="nav-item"><text class="nav-link" onclick="routeTo('+"'alertdataDetail','"+key+"');"+'">'
										+ alertdata.name + '</text></li>';
								itemsHtml = itemsHtml + item;
								// // console.log(key,obj[key]);
							}
						});
		// console.log("======= debug 7");

		itemsHtml = itemsHtml + "</ul>";
		uialertdata.innerHTML = itemsHtml;//"<ul></ul>";
		//// console.log(uialertdata.innerHTML);

	}
	
	
	/* 刷新历史数据结构*/
	function updateHistoryData() {
		if (userSpace != null && userSpace != "undefined") {
			// console.log("userSpace is not null");
			doUpdateHistorData(userSpace);
		} else 
		getUserSpace(
				user.id,
				token,
				doUpdateHistorData);
	}

	function doUpdateHistorData(userSpace){
		if (userSpace == null || userSpace == "undefined") {
			// console.log("userSpace is null");
			return;
		}
		/* 刷新历史数据结构*/
		var historydatas = userSpace.historyData;
		if(historydatas == null || historydatas == "undefined") historydatas = {};
		var uidatahistory = document.getElementById("ui-datahistory");

		//if (uidatahistory != null && uidatahistory != "undefined")
		//	// console.log(uidatahistory.innerHTML);
		var itemsHtml = '<ul class="nav flex-column sub-menu">';
		Object
				.keys(historydatas)
				.forEach(
						function(key) {
							// console.log("======= debug 8");

							if (key != null 
									&& key != "undefined") {
								var historydata = historydatas[key];
								if(key == "") key = "未分类";

								var item = '<li class="nav-item" onclick="routeTo('+"'historydatadetail','"+key+"');"+'"><text class="nav-link">'
										+ historydata.name + '</text></li>';
								itemsHtml = itemsHtml + item;
								// // console.log(key,obj[key]);
							}
						});
		// console.log("======= debug 9");

		itemsHtml = itemsHtml + "</ul>";
		uidatahistory.innerHTML = itemsHtml;//"<ul></ul>";
		//// console.log(uidatahistory.innerHTML);

	}
	

	/* 刷新直线报警结构*/
	function updateLineAlertData() {
		if (userSpace != null && userSpace != "undefined") {
			// console.log("userSpace is not null");
			doUpdateLineAlertData(userSpace);
		} else 
		getUserSpace(
				user.id,
				token,
				doUpdateLineAlertData);
	}

	function doUpdateLineAlertData(userSpace){
		if (userSpace == null || userSpace == "undefined") {
			// console.log("userSpace is null");
			return;
		}
		/* 刷新直线报警结构*/
		var directreportingdatas = userSpace.lineAlertData;
		if(directreportingdatas == null || directreportingdatas == "undefined") directreportingdatas = {};
		var uidirectreporting = document.getElementById("ui-directreporting");

		//if (uidirectreporting != null && uidirectreporting != "undefined")
		//	// console.log(uidirectreporting.innerHTML);
		var itemsHtml = '<ul class="nav flex-column sub-menu">';
		Object
				.keys(directreportingdatas)
				.forEach(
						function(key) {
							if (key != null 
									&& key != "undefined") {
								// console.log("======= debug 10");

								var directreportingdata = directreportingdatas[key];
								if(key == "") key = "未分类";

								var item = '<li class="nav-item" onclick="routeTo('+"'linalertdataDetail','"+key+"');"+'"><text class="nav-link">'
										+ directreportingdata.name + '</text></li>';
								itemsHtml = itemsHtml + item;
								// // console.log(key,obj[key]);
							}
						});
		// console.log("======= debug 11");

		itemsHtml = itemsHtml + "</ul>";
		uidirectreporting.innerHTML = itemsHtml;//"<ul></ul>";
		//// console.log(uidirectreporting.innerHTML);

	}
	
	function getPath(diagram) {
		var path = "";
		if (!diagram.file) {
			path = path + name;
		}

	}
