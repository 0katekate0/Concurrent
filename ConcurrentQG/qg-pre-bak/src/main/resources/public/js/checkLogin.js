window.checkLogin=function () {
    var token = getCookie("token");
    if(token==null || token==""){
        window.location.href="login.html";
    }
};
//保存token
function saveToken() {
    var tokenTemp=GetQueryString("token");
    if(tokenTemp!=null && tokenTemp !=""){
        setCookie("token",tokenTemp);
        window.location.href="index.html";
    }
}
//默认启动
$(function () {
    saveToken();
    checkLogin();
});

function loginOut() {
    var token = getCookie("token");
    $.ajax({
        url : ReqUrl.LoginReqUrl()+"/v/loginOut",
        type : "post",
        dataType : "json",
        data:{"token":token},
        success: function(response){
            if(response.code==0){
                delCookie("token");
                window.location.href="login.html";
            }else{
                alert(response.message);
                window.location.href="login.html";
            }
        }
    });
}