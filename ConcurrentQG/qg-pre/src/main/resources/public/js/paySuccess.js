$(function() {
    var orderId=GetQueryString("orderId");
    $.ajax({
        url: ReqUrl.OrdeReqUrl()+"/v/prepay/",
        type:"POST",
        data:{"orderId":orderId,"token":getCookie("token")},
        dataType: "json",
        success: function (response, status, xhr) {
            if(response.code==0){
                var order=response.data;
                $('#orderNo').html(order.orderNo);
                $('#amount').html(order.amount);
            }
        }
    });
});