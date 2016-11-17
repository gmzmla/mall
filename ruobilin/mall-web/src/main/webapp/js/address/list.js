$(function () {
	
	$(".ui-dialog-close").click(function(){
		$(".ui-dialog").hide();
	});
	
	//加载省下拉
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
});
//新增收货人信息弹出层
function use_NewConsignee(){
	$("#consignee_id").removeAttr("value");
	$("#consignee_name").removeAttr("value");
	$("#consignee_province").val("");
	$("#consignee_city").val("");
	$("#consignee_county").val("");
	$("#consignee_address").removeAttr("value");
	$("#consignee_mobile").removeAttr("value");
	$("#consignee_phone").removeAttr("value");
	$("#consignee_email").removeAttr("value");
	$(".ui-dialog").show();
}
//保存收货人信息
function save_Consignee(saveType){
	var checkConsignee = true;
	// 验证收货人信息是否正确
	if (!check_Consignee("name_div"))
		checkConsignee = false;
	// 验证地区是否正确
	if (!check_Consignee("area_div"))
		checkConsignee = false;
	// 验证收货人地址是否正确
	if (!check_Consignee("address_div"))
		checkConsignee = false;
	// 验证手机号码是否正确
	if (!check_Consignee("call_mobile_div"))
		checkConsignee = false;
	// 验证电话是否正确
	if (!check_Consignee("call_phone_div"))
		checkConsignee = false;
	// 验证邮箱是否正确
	if (!check_Consignee("email_div"))
		checkConsignee = false;
	if (!checkConsignee)
		return;
	
	if(checkConsignee){
		$("#area").val($("#consignee_province").find("option:selected").text()+$("#consignee_city").find("option:selected").text());
		$("#saveType").val(saveType);
		$("#consigneeForm").prop("action",$("#consigneeForm").attr("action")+"/address/saveMailingAddress");
		$("#consigneeForm").submit();
	}

}
//新增收货人信息表单验证
function check_Consignee(divId) {
	var errorFlag = false;
	var errorMessage = null;
	var value = null;
	// 验证收货人名称
	if (divId == "name_div") {
		value = $("#consignee_name").val();
		if (isEmpty(value)) {
			errorFlag = true;
			errorMessage = "请您填写收货人姓名";
		}
		if (value.length > 25) {
			errorFlag = true;
			errorMessage = "收货人姓名不能大于25位";
		}
		if (!is_forbid(value)) {
			errorFlag = true;
			errorMessage = "收货人姓名中含有非法字符";
		}
	}
	// 验证邮箱格式
	else if (divId == "email_div") {
		value = $("#consignee_email").val();
		if (!isEmpty(value)) {
			if (!check_email(value)) {
				errorFlag = true;
				errorMessage = "邮箱格式不正确";
			}
		} else {
			if (value.length > 50) {
				errorFlag = true;
				errorMessage = "邮箱长度不能大于50位";
			}
		}
	}
	// 验证地区是否完整
	else if (divId == "area_div") {
		var provinceId = $("#consignee_province").find("option:selected").val();
		var cityId = $("#consignee_city").find("option:selected").val();
		var countyId = $("#consignee_county").find("option:selected").val();
		var townId = $("#consignee_town").find("option:selected").val();
		// 验证地区是否正确
		if (isEmpty(provinceId) || isEmpty(cityId) || isEmpty(countyId)
				|| ($("#span_town").html() != null && $("#span_town").html() != "" && !$("#span_town").is(":hidden") && isEmpty(townId))) {
			errorFlag = true;
			errorMessage = "请您填写完整的地区信息";
		}
	}
	// 验证收货人地址
	else if (divId == "address_div") {
		value = $("#consignee_address").val();
		if (isEmpty(value)) {
			errorFlag = true;
			errorMessage = "请您填写收货人详细地址";
		}
		if (!is_forbid(value)) {
			errorFlag = true;
			errorMessage = "收货人详细地址中含有非法字符";
		}
		if (value.length > 50) {
			errorFlag = true;
			errorMessage = "收货人详细地址过长";
		}
	}
	// 验证手机号码
	else if (divId == "call_mobile_div") {
		value = $("#consignee_mobile").val();
		divId = "call_div";
		if (isEmpty(value)) {
			errorFlag = true;
			errorMessage = "请您填写收货人手机号码";
		} else {
			if (!check_mobile(value)) {
				errorFlag = true;
				errorMessage = "手机号码格式不正确";
			}
		}
		if (!errorFlag) {
			value = $("#consignee_phone").val();
			if (!isEmpty(value)) {
				if (!is_forbid(value)) {
					errorFlag = true;
					errorMessage = "固定电话号码中含有非法字符";
				}
				if (!checkPhone(value)) {
					errorFlag = true;
					errorMessage = "固定电话号码格式不正确";
				}
			}
		}
	}
	// 验证电话号码
	else if (divId == "call_phone_div") {
		value = $("#consignee_phone").val();
		divId = "call_div";
		if (!isEmpty(value)) {
			if (!is_forbid(value)) {
				errorFlag = true;
				errorMessage = "固定电话号码中含有非法字符";
			}
			if (!checkPhone(value)) {
				errorFlag = true;
				errorMessage = "固定电话号码格式不正确";
			}
		}
		if (true) {
			value = $("#consignee_mobile").val();
			if (isEmpty(value)) {
				errorFlag = true;
				errorMessage = "请您填写收货人手机号码";
			} else {
				if (!check_mobile(value)) {
					errorFlag = true;
					errorMessage = "手机号码格式不正确";
				}
			}
		}
	}
	if (errorFlag) {
		$("#" + divId + "_error").html(errorMessage);
		$("#" + divId + "_error").addClass("message");
		return false;
	} else {
		$("#" + divId + "_error").removeClass("message");
		$("#" + divId + "_error").html("");
	}
	return true;
};

//删除收货人信息
function delAddress(id){
	if(confirm("您确定要删除该收货地址吗？")){
		$.ajax({
			type:"post",
			url:basePath+"/address/delMailingAddress",
			data:{id:id},
			success:function(){
				$("#addresssDiv-"+id).remove();
				$("#addressNum_top").html($(".easebuy-m").length);
			}
		});
	}
}

//加载市
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
//加载县
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
function addOption(value,txt){
	return "<option value='"+value+"'>"+txt+"</option>";
}

//将收货地址设为默认
function makeAddressAllDefault(id){
	$.ajax({
		type:"post",
		url:basePath+"/address/updateMark",
		data:{id:id},
		success:function(data){
			$(".easebuy-m").find(".smt").find("h3").find("span").remove();
			$("#addresssDiv-"+id).find(".smt").find("h3").append('<span class="ftx-04 ml10">默认地址</span>');
			$("#addresssDivDiv").prepend($("#addresssDiv-"+id));
		}
	});
}
//编辑收货地址
function alertUpdateAddressDiag(id){
	$("#consignee_id").val(id);
	$("#addresssDiv-"+id).find(".smc").find(".item-lcol").find(".item").each(function(index){
		if(index==0){
			$("#consignee_name").val($(this).find(".fl").html());
		}else if(index==1){
			$("#consignee_province").val($(this).find(".fl").find("#provinceId"));
			$("#consignee_city").val($(this).find(".fl").find("#cityId"));
			$("#consignee_county").val($(this).find(".fl").find("#countyId"));
		}else if(index==2){
			$("#consignee_address").val($(this).find(".fl").html());
		}else if(index==3){
			$("#consignee_mobile").val($(this).find(".fl").html());
		}else if(index==4){
			$("#consignee_phone").val($(this).find(".fl").html());
		}else if(index==5){
			$("#consignee_email").val($(this).find(".fl").html());
		}
	});
	$(".ui-dialog").show();
}