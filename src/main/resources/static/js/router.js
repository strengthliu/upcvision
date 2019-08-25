/**
 * 
 */

	//var userSpace = null;
	//var user = null;
	//var token = null;

	var _realtimeDataDetailKey;
    function routeTo(diagram,key){
    	switch(diagram){
    	case "diagramList":
    		if(key=='未分类') key = "unclassify";
    	    _galleryKey = key;    		
    	    $("#mainPanel").load("_gallery.html");
    		break;
    	case "diagramDetail":
    	    _diagramShowKey = key;    		
    	    $("#mainPanel").load("_diagramShow.html");
    		break;
    	case "xydiagramList":
    		_xydiagramListKey = key;
    	    $("#mainPanel").load("_xydiagramList.html");
    		break;
    	case "realtimedataList":
    		_realtimedataListKey = key;
    	    $("#mainPanel").load("_realtimedataList.html");
    		break;
    	case "realtimedataDetail":
    		console.log("realtimedataDetail");
    		_realtimeDataDetailKey = key;
    	    $("#mainPanel").load("_realtimeDataChart.html");
    		break;
    	case "alertdataList":
    		
    	    $("#mainPanel").load("_alertdataList.html");
    		break;
    	case "historydataList":
    		
    	    $("#mainPanel").load("_historydataList.html");
    		break;
    	case "directreportingdataList":
    		_linealertdataListKey = key;
    	    $("#mainPanel").load("_lineAlertDataList.html");
    		break;
    		
    		//     
    	}

    }
