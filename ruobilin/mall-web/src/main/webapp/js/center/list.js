$(function () {
	$(".search-btn").click(function(){
		window.location.href=basePath+"/center/list?searchParam="+$("#ip_keyword").val();
	});
});