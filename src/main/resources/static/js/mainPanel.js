/**
 * 
 */

    
	if(user == null || user == "undefined"){
		user = JSON.parse(localStorage.user);
		if(user == null || user == "undefined") {
			alert("登录超时，请重新登录。");
			window.location.href="login.html";
		}
	}

	/**
    控制面板悬浮菜单
    */

	var mainPanel = document.getElementById("main");
	var oppertionButton = document.createElement('div');
	oppertionButton.innerHTML = ' <div class="info-nr" id="info-nr"> '+
		'<div id="info-nr-phone" class="info-nr-phone"> '+
	    '  <section id="toMenu"></section> '+
	    '  <div class="menu_01"  data-toggle="modal" data-target="#newItemAction_mid" onclick="newItemAction();"></div> '+
	    '  <div class="menu_02" onclick="editItemAction();"> </div> '+
	    '  <div class="menu_03" onclick="deleteItemAction();"> </div> '+
	    '  <div class="menu_04" onclick="shareItemAction();"> </div>   '+
	    '</div>  '+
		'</div>';
		
    switch(user.role){
    case 1:
    	mainPanel.appendChild(oppertionButton);
        window.addEventListener("DOMContentLoaded", function () {
            $("#toMenu").click(function(){
                $(".info-nr-phone").toggleClass("info-nr-phone2");
                $(".menu_01").toggleClass("to_01");
                $(".menu_02").toggleClass("to_02");
                $(".menu_03").toggleClass("to_03");
                $(".menu_04").toggleClass("to_04");
            });
        }, false);

    	break;
    case 2:
    	mainPanel.appendChild(oppertionButton);
        window.addEventListener("DOMContentLoaded", function () {
            $("#toMenu").click(function(){
                $(".info-nr-phone").toggleClass("info-nr-phone2");
                $(".menu_01").toggleClass("to_01");
                $(".menu_02").toggleClass("to_02");
                $(".menu_03").toggleClass("to_03");
                $(".menu_04").toggleClass("to_04");
            });
        }, false);
    	break;
    case 3:
    	break;

    }
