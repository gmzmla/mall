function onQueryList(categoryPropertyValueId,categoryPropertyId,categoryPropertyValueName,categoryPropertyName){
	
	var param="";
	var propertisParam="";
	if(categoryPropertyValueName!=null&&categoryPropertyValueId!=null){
		propertisParam=categoryPropertyValueId+":"+categoryPropertyName+":"+categoryPropertyValueName+":"+categoryPropertyId;
	}
	$("input[name='propertisParam']").each(function(){ 
		if($(this).val()==propertisParam){
			propertisParam="";
		}else{
			param+=$(this).val()+",";
		}
	});
	propertisParam=propertisParam+","+param;
	// window.location.href ="$basePath/solrSearch/display?categoryParam="+propertisParam+"&categoryPropertyId="+categoryPropertyId+"&keyWord="+keyWord+"&categoryPropertyName="+categoryPropertyName+"&categoryPropertyValueName="+categoryPropertyValueName;
	 
	 $.ajax({
	 type:"POST",
	 url:basePath+"/solrSearch/productList",
	 data:{keyWord:keyWord,categoryParam:propertisParam},
	 dataType:"json",
	 success:function(data){
		 var html='';
		 var p=data.list;
		 for(var i=0;i<p.length;i++){
			 html =html +addli(p[i].id,p[i].images[0].imageUrl,p[i].name,p[i].originalPrice);
		 }
		 $(".gl-warp").html(html);
		 html='';
		 var page=data.pageView;
		 for(var i=0;i<page.length;i++){
			 html +=page[i];
		 }
		 $(".page_b").html(html);
	 }
	 });
	 if(categoryPropertyValueId!=null){
			$("#sl-wrap"+categoryPropertyId).hide();
			$(".selector-set").append(addSelector(categoryPropertyValueId,categoryPropertyName,categoryPropertyValueName,categoryPropertyId)); 
		}
	}

function pageAjax(params){
	 $.ajax({
		 type:"POST",
		 url:basePath+params,
		 dataType:"json",
		 success:function(data){
			 var html='';
			 var p=data.list;
			 for(var i=0;i<p.length;i++){
				 html =html +addli(p[i].id,p[i].images[0].imageUrl,p[i].name,p[i].originalPrice);
			 }
			 $(".gl-warp").html(html);
			 html='';
			 var page=data.pageView;
			 for(var i=0;i<page.length;i++){
				 html +=page[i];
			 }
			 $(".page_b").html(html);
		 }
		 });
}

function addli(pId,imageUrl,pName,pOriginalPrice){
	var html='';
	html +='<li class="gl-item">';
	html +='	<div class="gl-i-wrap j-sku-item">';
	html +='		<div class="p-img">';
	html +='			<a href="'+basePath+'/p/'+pId+'">';
	html +='				<img src="'+imageUrl+'" width="220px" height="220px"/>';
	html +='			</a>';
	html +='		</div>';
	html +='		<div class="p-name">';
	html +='			<a href="'+basePath+'/p/'+pId+'">'+pName+'</a>';
	html +='		</div>';
	html +='		<div class="p-price">';
	html +='			<strong class="J_price">';
	html +='				<em>¥</em><i>'+pOriginalPrice+'</i>';
	html +='			</strong>';
	html +='		</div>';
	html +='    <div class="extra">';
	html +='			<span class="star"><span class="star-white"><span id="star-827069" class="star-yellow h5">&nbsp;</span></span></span>';
	html +='			<a id="comment-827069" target="_blank" href="javascript:void(0)" >已有11961人评价</a>';
	html +='		</div>';
	html +='		<div class="stocklist">';
	html +='			<span class="st33">有货';
	html +='				<b>下单后2-6天发货</b>';
	html +='			</span>';
	html +='		</div>';
	html +='		<div class="btns">';
	html +='			<a  class="btn-buy">加入购物车</a>';
	html +='			<a id="coll827069" class="btn-coll">关注</a>';
	html +='			<a class="btn-compare btn-compare-s" id="comp_827069"><span></span>对比</a>';
	html +='		</div>';
	html +='</div>';
	html +='</li>';
	return html;
}

function addSelector(id,property,value,categoryPropertyId){
	var html='';
	html+='<a href="javascript:void(0)" class="ss-item" onclick="closeQuery(this,\''+categoryPropertyId+'\')">';
	html+='<input type="hidden" name="propertisParam" value="'+id+':'+property+':'+value+':'+categoryPropertyId+'"/>';
	html+='<b>'+property+'：</b><em>'+value+'</em><i></i>';
	html+='</a>';
	return html;
}

function order(orderParam){
	$.ajax({
		type:"POST",
		url:basePath+"/solrSearch/productList",
		data:{keyWord:keyWord,orderParam:orderParam},
		dataType:"json",
		success:function(data){
			var html='';
			var p=data.list;
			for(var i=0;i<p.length;i++){
				 html =html +addli(p[i].id,p[i].images[0].imageUrl,p[i].name,p[i].originalPrice);
			}
			 $(".gl-warp").html(html);
			 html='';
			var page=data.pageView;
			 for(var i=0;i<page.length;i++){
				 html +=page[i];
			 }
			 $(".page_b").html(html);
		}
	});
}

function showList(){
	alert("123");
	$("#shelper").show();
}