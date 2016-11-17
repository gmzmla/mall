function loading(obj) {
	if (obj == null) {
		$("#loadingModal").modal({
			keyboard : false
		});
	}
	else {
		obj.show().html('<img src="/static/img/loading.gif"');
	}
}

function unloading(obj) {
	if (obj == null) {
		$("#loadingModal").modal('hide')
	}
	else {
		obj.hide()
	}
}

function followIt(faid, fid, type) {
	if (type == null)
		type = 1;

	$.post("/follow", {fid:fid, type:type}, function(data) {
		var ret = parseInt(data);
		if (ret == -4) {
			alert("不能关注自己。");
			return;
		}
		if (ret < 1) {
			alert("关注失败。" + ret);
			return;
		}
		if (ret == 1) {
			alert("关注成功。");
			$("a[fid='"+faid+"']").html("取消关注");
		}
		if (ret == 2) {
			alert("已取消关注。");
			$("a[fid='"+faid+"']").html("关注");
		}
	});
}