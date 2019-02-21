/****
 * created By 北大课工场 kgc
 */
var login={};
/***
 * 执行登录的方法
 */
login.doLogin=function () {
    var phone=$("#userName").val();
    var password=$("#password").val();
    var token = getCookie("token");
    $.ajax({
        url : ReqUrl.LoginReqUrl()+"doLogin",
        type : "post",
        dataType : "json",
        data : {
            phone : phone,
            password : password,
            token : token
        },
        success: function(response){
            if(response.code==0){
                setCookie("token",response.data.token,1);
                window.location.href="index.html";
            }else if(response.code==-1){
                alert(response.message);
            }else{
                alert(response.message);
            }
        }
    });
};
login.wxLogin=function () {
    window.location.href=ReqUrl.LoginReqUrl()+"/wx/toLogin";
};
