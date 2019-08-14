/**
 * 
 */

/**
 * 悬浮菜单功能
 */
 // 新建
function newItemAction(){
	// alert("realtimeDataList.newItemAction");
	$('#newItemAction_mid').modal('show');

	
}
function editItemAction(){
	
}
function deleteItemAction(){
	
}
function shareItemAction(){
	
}


  console.log("_realtimedataListKey = "+_realtimedataListKey);
  var _realtimeDatas ;
  // TODO:  如果key为空，就是异常，待处理。
  if(_realtimedataListKey == null || _realtimedataListKey=="undefined")
    _realtimedataListKey = "";


  if(_realtimedataListKey.trim() == 'unclassify'){
    alert(_realtimedataListKey);
    _realtimedataListKey="";
    _realtimeDatas = userSpace.graphs[""];
  }else
    _realtimeDatas = userSpace.realTimeData;
  //console.log("graphs => "+JSON.stringify(userSpace));

  // console.log("graphs = "+JSON.stringify(userSpace.graphs[""]));

  var realtimeDataList_ui =document.getElementById("realtimeDataList_ui");
  var realtimeDataList_ui_innerHTML = "";
  if(_realtimeDatas != null && _realtimeDatas !="undefined")
    Object.keys(_realtimeDatas).forEach(function(key){
      var _realtimeData = _realtimeDatas[key];
      var realtimeDataList_ui_item_innerHTML= '<div class="col-xl-4 col-lg-4 col-md-4 col-sm-6 col-12"';
      realtimeDataList_ui_item_innerHTML += 'onclick="routeTo('+"'"+"realtimedataDetail','"+_realtimeData.id+"'"+');">';
      realtimeDataList_ui_item_innerHTML += '<figure class="effect-text-in">';
      realtimeDataList_ui_item_innerHTML += '<img src="' + _realtimeData.img + '" alt="image"/>';
      realtimeDataList_ui_item_innerHTML += '<figcaption><h4>'+ _realtimeData.name + '</h4><p>' + _realtimeData.path + '</p></figcaption>';
      realtimeDataList_ui_item_innerHTML += '</figure></div>';

      //console.log(diagram_gallery_item_innerHTML);
      realtimeDataList_ui_innerHTML = realtimeDataList_ui_innerHTML + realtimeDataList_ui_item_innerHTML;
    });
  realtimeDataList_ui.innerHTML = realtimeDataList_ui_innerHTML;

  //console.log(" in _gallery.html userSpace="+JSON.stringify(userSpace));