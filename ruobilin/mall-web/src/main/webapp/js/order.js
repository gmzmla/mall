function buy() {
	addToCart(1,function() {
		document.location.href="/o/order";
	});
}

function addToCart(count,success) {
	if (!canBuy) {
		alert("请先选择。");
		return;
	}
	var id = $("#productId").val();
	var pg = "";
	if (priceGroups != null) {
		for (var i=0; i<priceGroups.length; i++) {
			pg += priceGroups[i] + ",";
		}
	} else {
		pg = "0";
	}
	$.post("/o/addToCart", 
			{id:id, count:count, pg:pg, efee:expressFee},
			function(data) {
				if (data == "0") {
					if (success != null) {
						success();
					}
					else {
//						alert("已添加。");
					}
					return;
				}
				
				alert("错误，代码：" + data);
			});
}

function removeToCart(index, obj) {
	$.post("/o/removeToCart", 
			{index:index},
			function(data) {
				if (data == "0") {
					$(obj).parent().parent().remove();
					return;
				}
				
				alert("错误，代码：" + data);
			});
}

function order() {
	document.location.href="/o/order";
}
function createOrder(cid, callback) {
	$.post("/o/create", {cid:cid}, function(json) {
		if (json.success) {
			callback(json.data);
		} else {
			alert("保存失败。");
		}
	}, "json");
}
function pay() {
	var cid = $("#contacts input[name='contact']:checked").val();
	if (cid == null) {
		alert("先选择配送地址。");
		return;
	}
	createOrder(cid, function(id) {
		document.location.href="/o/finish?id=" + id;
	});
}

function finish() {
	
}