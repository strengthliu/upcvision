var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}





function connect() {
    const id=localStorage.getItem("chat_id");
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings/'+id, function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}
function login() {
	console.log("debug 1");
    const chat_id=document.getElementById("chat_id").value;
    const chat_name=document.getElementById("chat_name").value;
    localStorage.setItem("chat_id",chat_id);
    localStorage.setItem("chat_name",chat_name);
    document.getElementById("main-content").hidden=null;
    document.getElementById("login_div").hidden="hidden";
    setTimeout(connect(),1000)
	console.log("debug 2");
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    const chat_id=localStorage.getItem("chat_id");
    const chat_name=localStorage.getItem("chat_name")
    const content=document.getElementById("content").value;
    stompClient.send("/app/hello/"+chat_id, {}, JSON.stringify({'name': chat_name,'content':content}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

