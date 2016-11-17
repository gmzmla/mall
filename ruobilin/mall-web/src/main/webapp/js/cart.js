$(function () {
	$("#toggle-checkboxes_up").click(function(){
		$("input[name='checkItem']").each(function(){
			$(this).prop("checked",$("#toggle-checkboxes_up").is(':checked'));
		});
	});
	//加载购物车数据
	$.ajax({
		type:"post",
		url:basePath+"/listCart",
		data:{},
		success:function(data){
			var html='';
			for(var i=0;i<data.cartP.length;i++){
				var p=data.cartP[i];
				html=html+addUl(p.id,p.imageUrl,p.name,p.price,p.productCount,p.id,p.tableName);
			}
			if(html!=''){
				$("#shopping-amount").html(data.cartP.length);
				$("#munCart").html(data.cartP.length);
				$("#smc").html(html);
				onSumPrice();
			}
		}
	});
});

//计算商品总费用
function onSumPrice(){
	var sumPrice=new Number(0);
	$(".p-sum").find("strong").each(function(){
		var price=new Number($(this).html());
		sumPrice=numberround(sumPrice+price,"2");
	});
	$(".sumPrice").find("em").html(sumPrice);
}
//减商品数量
function onDecrement(id,priceObj,cartProductId){
	//清空事件
	$("#decrement_"+id).removeAttr("onclick");
	$("#decrement_"+id).attr("class","decrement disabled");
	
	var changeQuantity=new Number($("#changeQuantity_"+id).val());
	var munCart=new Number($("#munCart").html());	//商品总数量
	if(changeQuantity>1){
		$("#changeQuantity_"+id).val(changeQuantity - 1);
		$("#munCart").html(munCart - 1);
		var priceSumObj=$("#priceSum_"+id).find("strong");
    	var priceSum=new Number(priceSumObj.html());
    	var price=new Number(priceObj);
    	priceSumObj.html(numberround(priceSum-price,'2'));
    	var sumPrice=new Number($(".sumPrice").find("em").html());
    	$(".sumPrice").find("em").html(numberround(sumPrice-price,'2'));

		//修改Cookie中的商品数量
		$.ajax({
			url:basePath+"/list/updateCartProduct?date="+new Date().getTime(),
			type:"POST",
			data:{productCount:changeQuantity - 1,id:cartProductId},
			success:function(data){
				$("#decrement_"+id).attr("onclick","onDecrement('"+id+"','"+price+"','"+cartProductId+"')");
				$("#decrement_"+id).attr("class","decrement");
			}
		});
	}
	if(changeQuantity-1<2){
		$("#decrement_"+id).attr("class","decrement disabled");
	}
	
}
//加商品数量
function onIncrement(id,priceObj,cartProductId){
	//清空事件
	$("#increment_"+id).removeAttr("onclick");
	$("#increment_"+id).attr("class","increment disabled");
	
	var changeQuantity=new Number($("#changeQuantity_"+id).val());
	$("#changeQuantity_"+id).val(changeQuantity + 1);
	if(changeQuantity>=1){
		$("#decrement_"+id).attr("class","decrement");
	}
	var priceSumObj=$("#priceSum_"+id).find("strong");
    var priceSum=new Number(priceSumObj.html());
    var price=new Number(priceObj);
    priceSumObj.html(numberround(priceSum+price,'2'));
    	
    var sumPrice=new Number($(".sumPrice").find("em").html());
    $(".sumPrice").find("em").html(numberround(sumPrice+price,'2'));
    
    var munCart=new Number($("#munCart").html());	//商品总数量
    $("#munCart").html(munCart + 1);
    //修改Cookie中的商品数量
	$.ajax({
		url:basePath+"/list/updateCartProduct?date="+new Date().getTime(),
		type:"POST",
		data:{munCart:changeQuantity + 1,id:cartProductId},
		success:function(data){
			$("#increment_"+id).attr("onclick","onIncrement('"+id+"','"+price+"','"+cartProductId+"')");
			$("#increment_"+id).attr("class","increment");
		}
	});
}
//删除商品
function onDeleteP(obj,cartProductId){
	$.ajax({
		url:basePath+"/p/delCart",
		type:"POST",
		data:{cartProductId:cartProductId},
		success:function(data){
			if(data=='-1'){
				alert("删除失败，请刷新页面");
			}else{
				$(obj).parent().parent().parent().parent().remove();
//				$("#munCart").html(data);
				onSumPrice();
			}
			
		}
	});
}

/**
 * 格式化数字
 * @param number 要格式的数字
 * @param fractionDigits 保留小数位数
 */
function numberround(number,fractionDigits){   
	with(Math){   
		return round(number*pow(10,fractionDigits))/pow(10,fractionDigits);   
	}   
}
//购物车动态展示效果
function addUl(productId,imageUrl,productName,ppPrice,pCount,pId,tableName){
	var html='';
	html +='<ul id="mcart-mz">';
	html +='	<li>';
	html +='		<div>';
	html +='			<div class="p-img fl">';
	html +='				<a href="'+basePath+'/'+tableName+'/'+productId+'" target="_blank">';
	html +='					<img src="'+imageUrl+'" alt="'+productName+'" height="50" width="50">';
	html +='				</a>';
	html +='			</div>';
	html +='		<div class="p-name fl">';
	html +='			<span></span>';
	html +='			<a href="'+basePath+'/'+tableName+'/'+productId+'" title="" target="_blank">';
	html +='				'+productName+'';
	html +='			</a>';
	html +='		</div>';
	html +='		<div class="p-detail fr ar">';
	html +='			<span class="p-price p-sum">';
	html +='				<strong style="display:none">'+ppPrice*pCount+'</strong>￥'+ppPrice+'×'+pCount+'';
	html +='			</span><br>';
	html +='			<a onclick="onDeleteP(this,\''+basePath+'\')" data-type="RemoveSuit" href="javascript:void(0)">';
	html +='				删除';
	html +='			</a>';
	html +='		</div>';
	html +='	</div>';
	html +='	</li>';
	html +='</ul>';
	return html;
}
