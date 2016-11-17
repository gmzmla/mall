//发货按钮
function onOut(id){
	if(id !=null){
		$("#expressText").html($("#express"+id).val());
		$("#orderId").val(id);
		$("#courierNumber").val('');
		$("#onExpressModal").modal();
	}else{
		id=$("#orderId").val();
		$.ajax({
			type:"post",
			url:basePath+"/adminOrder/updateOrderStatus",
			data:{orderId:id,courierNumber:$("#courierNumber").val(),express:$("#express"+id).val()},
			success:function(data){
				if("1"==data){
					$("#onExpressModal").modal("hide");
					$("#orderStatus"+id).html("已发货");
					$("#expressTd"+id).html($("#express"+id).val());
					$("#caozuoTd"+id).empty();
				}
			}
		});
	}
}