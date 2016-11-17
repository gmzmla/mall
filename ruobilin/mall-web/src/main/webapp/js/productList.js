function onQueryList(obj,propertisValues,propertisIds,propertisNames,selectObj,resultSearchTxt){
	var param="";
	var propertisParam="";
	if(propertisValues!=null&&propertisIds!=null){
		propertisParam=propertisValues+":"+propertisIds+":"+propertisNames;
	}
	$("input[name='propertisParam']").each(function(){ 
		if($(this).val()==propertisParam){
			propertisParam="";
		}else{
			param+=$(this).val()+",";
		}
	});
	var url=basePath+"/p/list/"+obj+"";
	var cat="?cat=";
	var data="";
		data+="orderSelect="+selectObj;
		data+="&propertisParam="+param+","+propertisParam;
		data+="&resultSearchTxt="+resultSearchTxt;
	data=encode64(encode64(data));	//双层加密
	url+=cat+data;
	window.location.href =url;
}

function closeQuery(obj,propertisValues,propertisIds,propertisNames){
	onQueryList(obj,propertisValues,propertisIds,propertisNames);
	$(this).remove();
}

function onSelectQueryList(obj,code,selectObj){
	var className=$(obj).attr("class");
	if(className!=""){
		$(obj).attr("class","");
	}else{
		$(obj).attr("class","curr");
	}
	onQueryList(code,null,null,null,selectObj);
}
function onTxtFocus(obj){
	if($(obj).val()=="在结果中搜索"){
		$(obj).val("");
	}
}
function onTxtBlur(obj){
	if($(obj).val()==""){
		$(obj).val("在结果中搜索");
	}
}
function onResultSearchBtn(code){
	onQueryList(code,null,null,null,null,$("#resultSearchTxt").val());
}

