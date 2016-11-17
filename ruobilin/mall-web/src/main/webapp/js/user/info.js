$(function () {
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

function updateUserInfo(){
	$("#userFormId").attr("action",basePath+"/u/updateUserInfo");
	$("#userFormId").submit();
}
