function onAddShoppingCar(basePath,pid){
	if ($('input:radio').size()>0&&$('input:radio:checked').val()==null) {
		alert("请先选择。");
		return;
	}
	var pidCount=$(".spinnerExample").val();
	var ppid="";
	$(':input:radio:checked').each(
		function(){
			ppid+=$(this).val()+",";
		}
	);
	pid=pid+":"+pidCount+":"+ppid;
	window.location.href=basePath+"/p/shopitm?pid="+pid;
}