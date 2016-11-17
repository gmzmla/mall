$(function () {
	$.ajax({
		type:"post",
		url:basePath+"/area/queryProvinces",
		data:{},
		success:function(data){
			var html="";
			for(var i=0;i<data.length;i++){
				html+=addOption(data[i].id,data[i].cnName);
			}
			$("#consignee_province").append(html);
		}
	});
	
	$(".ui-switchable-panel").click(function(){
		$(".ui-switchable-panel").each(function(){
			$(this).find(".item-selected").parent().removeAttr("selected");
			$(this).find(".item-selected").removeClass("item-selected");
		});
		$(this).find(".consignee-item").addClass("item-selected");
		$(this).attr("selected","selected");
	});
	$(".payment-item").click(function(){
		$(".payment-list").find(".payment-item").each(function(){
			$(this).removeClass("item-selected");
		});
		$(this).addClass("item-selected");
	});
});
function addOption(value,txt){
	return "<option value='"+value+"'>"+txt+"</option>";
}
function loadCitys(){
	var value= $("#consignee_province  option:selected").val();
	$.ajax({
		type:"post",
		url:basePath+"/area/queryCities",
		data:{provinceId:value},
		success:function(data){
			var html="";
			for(var i=0;i<data.length;i++){
				html+=addOption(data[i].id,data[i].cnName);
			}
			$("#consignee_city").append(html);
			$("#consignee_city").attr("onchange","loadAreas()");
		}
	});
}
function loadAreas(){
	var value= $("#consignee_city  option:selected").val();
	$.ajax({
		type:"post",
		url:basePath+"/area/queryAreas",
		data:{cityId:value},
		success:function(data){
			var html="";
			for(var i=0;i<data.length;i++){
				html+=addOption(data[i].id,data[i].cnName);
			}
			$("#consignee_county").append(html);
		}
	});
}
function show_ConsigneeAll(){
	$("#consignee1").removeClass("consignee-off");
	$("#consignee1").attr("style","position: relative; height: auto;");
	$("#consigneeItemAllClick").addClass("hide");
	$("#consigneeItemHideClick").removeClass("hide");
}
function hide_ConsigneeAll(){
	$("#consignee1").addClass("consignee-off");
	$("#consignee1").attr("style","position: relative; height: 40px;");
	$("#consigneeItemHideClick").addClass("hide");
	$("#consigneeItemAllClick").removeClass("hide");
	
	var obj=$(".ui-switchable-panel").find(".item-selected").parent();
//	obj.remove();
	$("#consignee-list li:first").before(obj);
}


function submit_Order(basePath,commodityPrice){
	//获取收货人信息
	var consigneeObj=$("#consignee-list").find('li[selected="selected"]').find(".addr-detail");
	//收货人
	var consignee=consigneeObj.find(".addr-name").html();
	//收货人地址
	var consigneeAddress=consigneeObj.find(".addr-info").html();
	//收货人手机号码
	var phoneNumber=consigneeObj.find(".addr-tel").html();
	if(consignee==null){
		alert("请添加收货人信息");
		return ;
	}
	//获取支付方式
	var modePayment=$("#payment-list").find(".item-selected").attr("payid");
	if(modePayment==null){
		alert("请选择支付方式");
		return ;
	}
	//获取配送信息
	
	var data="&consignee="+consignee+"&consigneeAddress="+consigneeAddress+"&phoneNumber="+phoneNumber+"&modePayment="+modePayment;
	
	document.location.href=basePath+"/o/cartPay?cartTotal="+commodityPrice+data;
}
