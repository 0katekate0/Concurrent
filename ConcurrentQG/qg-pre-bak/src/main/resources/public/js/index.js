/****
 * created By 北大课工场 kgc
 * @param a
 * @param b
 * @param c
 * @param d
 */
var goodsId=1;
var timer2;
//加载百分比
function loadPercent(width,total,discountPrice,price){
    $('.ec_jinDu').width(width);
    $('.bfb').html(width);
    $('.sz').html(total);
    $('.x_jiaGe').html(discountPrice);
    $('.d_jiaGe').html(price);
}
//初始化加载
$(function () {
   loadGoodInfo();
});
function qgGoods() {
    var token=getCookie("token");
    $.ajax({
        url : ReqUrl.GoodsReqUrl()+"v/getGoods",
        type : "post",
        dataType : "json",
        data : {
            goodsId : goodsId,
            token : token
        },
        success: function(response, status, xhr){
            if(response.code==0){
                dialog.show({
                    'message':"排队成功，抢购中...",
                    'isCountDown':false
                });
                timer2=window.setInterval(function(){
                    flushGoodsIsGet(goodsId)
                },1000);
            }else{
                dialog.show({
                    'message':response.message,
                    'isCountDown':true,
                    'seconds':2
                });
            }
        }
    });
};
//s
function flushGoodsIsGet(id){
    $.ajax({
        url:ReqUrl.GoodsReqUrl()+'/v/flushResult',
        type: 'post',
        dataType: 'json',
        data: {
            goodsId:id,
            token:getCookie("token")
        },
        success: function(result){
            if(result.code==0){ //抢购成功
                //关闭窗口，跳转至订单页
                window.clearInterval(timer2);
                dialog.close();
                dialog.show({
                    'message':"抢购成功,将跳转至订单页..",
                    'isCountDown':true,
                    callback:function () {
                        window.location.href="orderList.html";
                    }
                });
            }else if(result.code==1103){

            }else if(result.code==1102){
                window.clearInterval(timer2);
                dialog.show({
                    'message':"库存不足，抢购失败..",
                    'isCountDown':true,
                    callback:function () {
                        window.location.reload();
                    }
                });
            }else if(result.code==1){
                window.location.href="login.html";
            }
        },
        error: function(){
            alert({"title":"提示","content":"系统异常"});
        }
    })
}