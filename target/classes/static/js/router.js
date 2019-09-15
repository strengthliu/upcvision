/**
 * 
 */

	//var userSpace = null;
	//var user = null;
	//var token = null;
	var _routeType;
	var _routeID;
	var _graphId;
	var _realtimeDataDetailKey;
    function routeTo(diagram,key,graphId){
    	if(unsubscribe!=null && unsubscribe!="undefined")
    		unsubscribe();
    	// 传递参数
		_routeType = diagram.toLowerCase();
		_routeID = key;
    	switch(diagram.toLowerCase()){
    	case "diagramList".toLowerCase():
    		if(key=='未分类') key = "unclassify";
    	    _galleryKey = key;    		
    	    $("#mainPanel").load("_gallery.html");
    		break;
    	case "diagramDetail".toLowerCase():
    	    _diagramShowKey = key; 
    		_graphId = graphId;
    	    $("#mainPanel").load("_diagramShow.html");
    		break;
    	case "xydiagramList".toLowerCase():
    		_xydiagramListKey = key;
    	    $("#mainPanel").load("_xyGraphList.html");
    		break;
    	case "xyGraphDetail".toLowerCase():
    		_xydiagramListKey = key;
    	    $("#mainPanel").load("_xyGraphChart.html");
    		break;
    	case "realtimedataList".toLowerCase():
    		_realtimedataListKey = key;
    	    $("#mainPanel").load("_realtimedataList.html");
    		break;
    	case "realtimedataDetail".toLowerCase():
    		_realtimeDataDetailKey = key;
    	    $("#mainPanel").load("_realtimeDataChart.html");
    		break;
    	case "alertdataList".toLowerCase():
    		
    	    $("#mainPanel").load("_alertdataList.html");
    		break;
    	case "alertdataDetail".toLowerCase():
    		
    	    $("#mainPanel").load("_alertdataChart.html");
    		break;
    	case "historydataList".toLowerCase():
    	    $("#mainPanel").load("_historDataList.html");
    		break;
    	case "historydatadetail".toLowerCase():
    	    $("#mainPanel").load("_historyDataChart.html");
    		break;
    	case "lineAlertdataList".toLowerCase():
    		_linealertdataListKey = key;
    	    $("#mainPanel").load("_lineAlertDataList.html");
    		break;
    	case "linalertdataDetail".toLowerCase():
    		_linealertdataListKey = key;
    	    $("#mainPanel").load("_lineAlertDataChart.html");
    		break;
   		
    		//     
    	case "userManage".toLowerCase():
    		currentUser = key;
    	    $("#mainPanel").load("_userManage.html");
    		break;
   	}

    }
