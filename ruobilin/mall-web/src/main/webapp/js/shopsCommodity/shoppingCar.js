function onAddShoppingCar(pid,tableName){
	
	var pidCount=$(".spinnerExample").val();	//数量
	pid=pid+":"+pidCount+":"+tableName;
	window.location.href=basePath+"/list/addCart?str="+pid;
}

function queryCommodityId(obj){
	$(obj).siblings().removeClass("selected");
	$(obj).addClass("selected");
	var str='';
	$(".selected").each(function(){
		str = str+$(this).attr("sgp")+',';
	});  
	window.location.href=basePath+"/list/selectCommodityId?str="+str;
}