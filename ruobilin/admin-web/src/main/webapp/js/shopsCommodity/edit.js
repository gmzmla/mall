var ImgObjSgp=null;	//上传按钮对象
$().ready(function() {
		//超文本
		KindEditor.ready(function(K) {
            	editor= K.create('#content', {
                    uploadJson : basePath+'/product/upload_json',
					fileManagerJson : '$basePath/product/upload',
                    allowFileManager : true,
					formatUploadUrl:false,
					afterUpload : function(url) {
                        alert(url);
                	}
           	 	});
    	});
		//上传
    	$('#imageDivId').diyUpload({
             url:basePath+'/product/upload_json',
             success:function( data ) {
            	 if(ImgObjSgp!=null){
            		 $(ImgObjSgp).parent().parent().find("td").each(function(index){
            			 if(index!=0&&index!=7){
            				 var imgObj= $(this).find("img");
            				 if(imgObj.attr("alt")=="undefined"||imgObj.attr("alt")==null||imgObj.attr("alt")==""){
            					 imgObj.prop("src",data.url);
            					 imgObj.prop("alt",data.url);
                				 $(this).find(".deleteImg").show();
                				 return false; 
            				 }else{
            					 return true; 
            				 }
            				 
            			 }
            		 });
            	 }
             },
             error:function( err ) {
                        		console.info( err );	
             },
             buttonText : '选择图片',
//             sendAsBinary:true,
             chunked:false,
             compress:false,
             // 分片大小
//             chunkSize:512 * 1024,
             //最大上传的文件数量, 总文件大小,单个文件大小(单位字节);
//             fileNumLimit:6,	//限制上传数量
//             fileSizeLimit:500000 * 1024,
//             fileSingleSizeLimit:50000 * 1024,
             duplicate:true,	//是否可重
             accept: {
            	    title: 'Images',
            	    extensions: 'gif,jpg,jpeg,bmp,png',
            	    mimeTypes: 'image/*'
            	}
       });
    	
	   //上传弹出层关闭时触发 
	   $('#uploadDivModal').on('hidden.bs.modal', function () {
		   $("#imageDivId").next(".parentFileBox").remove();	//清空弹出层中的上传信息
	   });
});
/**
 * 添加属性
 */
function addProperty(){
	var uid=UUID.prototype.createUUID();
	var html='';
	html +='<div class="row" id="rowt'+uid+'">';
	html +='<div class="col-md-4">';
	html +='	<input type="text" sgp="propertyInfoName" placeholder="填写属性名字"/>';
	html +='	<input type="hidden" value="'+uid+'">';
	html +='</div>';
	html +='<div class="col-md-12"><hr style="height:1px;border:none;border-top:1px dashed;" /></div>';
	html +='<div class="col-md-12">';
	html +='<div class="col-md-2" onclick="addPropertyValue(this,\''+uid+'\')"><a href="javascript:void(0)">添加属性值</a></div>';
	html +='</div></div><hr>';
	$("#property-panel").append(html);
}
/**
 * 添加属性值
 * @param obj 添加属性值按钮对象
 */
function addPropertyValue(obj,id){
	var uid=UUID.prototype.createUUID();
	var html='<div class="col-md-2">';
	html +='<input type="checkbox" onclick="onCheckbox(this)" sgp="checkbox'+id+'" value="1" />';
	html +='&nbsp;<input type="text" style="width:100px" placeholder="填写属性值"/>';
	html +='<input type="hidden" value="'+uid+'">';
	html +='<input type="hidden" value="'+id+'">';
	html +='</div>';
	$(obj).before(html);
}

/**
 * 选中属性值
 */
function onCheckbox(checkboxObj){
//	var propertyName=$(checkboxObj).parent().parent().parent(".row").find(".col-md-4").find("input:first").val();
	var propertyId=$(checkboxObj).parent().parent().parent(".row").find(".col-md-4").find("input:last").val();
	var propertyValue=$(checkboxObj).next().val();
	var checkboxValue=$(checkboxObj).val();
	var propertyValueId=$(checkboxObj).next().next().val();
	checkboxValue=propertyValueId;
	var html='';
	//获取属性table对象
	var obj=$("#property-panel").next();
	//获取图片table对象
	var imgObj=$("#imgTableId");
	
	//取消选中删除table里的数据
	if(!$(checkboxObj).is(':checked')){
		var labelHtm='<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>';
		obj.find("tbody").find("tr").each(function(){
			var tdObj=$(this).find("td:eq(0)");
			var indexs=tdObj.find("label").length;
			if(indexs==1&&tdObj.html().indexOf(labelHtm) !=-1){
				$(this).remove();
			}else if(indexs>1&&tdObj.html().indexOf(labelHtm) !=-1){
				if(propertyIdChecked(propertyId)>0){
					$(this).remove();
				}else if(tdObj.html().indexOf("+"+labelHtm) !=-1){
					tdObj.html(tdObj.html().replace("+"+labelHtm,""));
				}else{
					tdObj.html(tdObj.html().replace(labelHtm+"+",""));
				}
			}
		});
		
		imgObj.find("tr").each(function(){
			var tdObj=$(this).find("td:eq(0)");
			var indexs=tdObj.find("label").length;
			if(indexs==1&&tdObj.html().indexOf(labelHtm) !=-1){
				$(this).remove();
			}else if(indexs>1&&tdObj.html().indexOf(labelHtm) !=-1){
				if(propertyIdChecked(propertyId)>0){
					$(this).remove();
				}else if(tdObj.html().indexOf("+"+labelHtm) !=-1){
					tdObj.html(tdObj.html().replace("+"+labelHtm,""));
				}else{
					tdObj.html(tdObj.html().replace(labelHtm+"+",""));
				}
			}
		});
		return;
	}
	
	//往table里追加属性值数据
	if(obj.length > 0){
		var bool=appendTable(propertyId);
			//一个属性时创建新行
			if(bool=="1"||bool=="0"){
				obj.find("tbody").append(addTd('<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>'));
				imgObj.append(addImgTd('<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>'));
			}else if(bool=="2"){
				var htTr='';
				var imghtTr='';
				obj.find("tbody").find("tr").each(function(){
					var tdObj=$(this).find("td:eq(0)");
					var indexs=tdObj.find("label").length;
					tdObj.find("label").each(function(index){
						if(propertyId!=$(this).attr("for")){
							html +='<label for="'+$(this).attr("for")+'" id="'+$(this).attr("id")+'">'+$(this).html()+'</label>';
						}else{
							html +='<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>';
						}
						if((indexs-1)!=index){
							html +='+';
						}
					});
					if(htTr.indexOf(html)!=-1){
						html='';
					}else{
						htTr +=addTd(html);
						imghtTr +=addImgTd(html);
					}
					html='';
				});
				obj.find("tbody").append(htTr);
				imgObj.append(imghtTr);
			}else if(bool=="3"||bool=="4"){
				obj.find("tbody").find("tr").each(function(){
					var tdObj=$(this).find("td:eq(0)");
					html +='+';
					html +='<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>';
					tdObj.append(html);
					html='';
				});
				imgObj.find("tr").each(function(index){
					if(index!=0){
						var tdObj=$(this).find("td:eq(0)");
						html +='+';
						html +='<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>';
						tdObj.append(html);
						html='';
					}
				});
			}
//			else if(bool=="4"){
//				obj.find("tbody").find("tr").each(function(){
//					var tdObj=$(this).find("td:eq(0)");
//					html +='+';
//					html +='<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>';
//					tdObj.append(html);
//					html='';
//				});
//			}
	}else{
		html +='<table class="table table-striped">';
		html +='<thead>';
		html +='<tr><th>商品属性</th><th>价格</th><th>库存</th><th>商品名称</th><th>商品广告词</th></tr>';
		html +='</thead>';
		html +='<tbody id="shangPinShuXing">';
		html +=addTr(propertyId,propertyValue,checkboxValue);
		html +='</tbody>';
		html +='</table>';
		$("#xiaoshoushuxing").append(html);
		imgObj.append(addImgTd('<label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label>'));
	}
}

//添加属性tr
function addTr(propertyId,propertyValue,checkboxValue){
	var commodityTableId=UUID.prototype.createUUID();	//商品ID
	var html='';
	html +='<tr val="'+commodityTableId+'">';
	html +='<td><label for="'+propertyId+'" id="'+checkboxValue+'">'+propertyValue+'</label></td>';
	html +='<td><input type="text" ></td>';
	html +='<td><input type="text" ></td>';
	html +='<td><input type="text" ></td>';
	html +='<td><input type="text" ></td>';
	html +='</tr>';
	return html;
}

function addTd(tdhtml){
	var html='';
	html +='<tr>';
	html +='<td>'+tdhtml+'</td>';
	html +='<td><input type="text" ></td>';
	html +='<td><input type="text" ></td>';
	html +='<td><input type="text" ></td>';
	html +='<td><input type="text" ></td>';
	html +='</tr>';
	return html;
}
function addImgTd(tdhtml){
	var html='';
	html +='<tr>';
	html +='<td>'+tdhtml+'</td>';
	html +='<td><img src="'+basePath+'/img/zhu.png" height="72" width="72"/><div class="deleteImg" onclick="closeImg(this)" style="display: none;"></div></td>';
	html +='<td><img src="'+basePath+'/img/xi.png" height="72" width="72"/><div class="deleteImg" onclick="closeImg(this)" style="display: none;"></div></td>';
	html +='<td><img src="'+basePath+'/img/xi.png" height="72" width="72"/><div class="deleteImg" onclick="closeImg(this)" style="display: none;"></div></td>';
	html +='<td><img src="'+basePath+'/img/xi.png" height="72" width="72"/><div class="deleteImg" onclick="closeImg(this)" style="display: none;"></div></td>';
	html +='<td><img src="'+basePath+'/img/xi.png" height="72" width="72"/><div class="deleteImg" onclick="closeImg(this)" style="display: none;"></div></td>';
	html +='<td><img src="'+basePath+'/img/xi.png" height="72" width="72"/><div class="deleteImg" onclick="closeImg(this)" style="display: none;"></div></td>';
	html +='<td><input type="button" onclick="uploadImgClick(this)" value="图片上传"/></td>';
	html +='</tr>';
	return html;
}

//判断用
function appendTable(propertyId){
	var obj=$("#property-panel").next();
	var bool="0";
	obj.find("tbody").find("tr").each(function(){
		var tdObj=$(this).find("td:eq(0)");
		var indexs=tdObj.find("label").length;
		tdObj.find("label").each(function(index){
			if(propertyId==$(this).attr("for")&&indexs==1){	//一个属性时创建新行
				bool= "1";
				return false;
			}else if(propertyId!=$(this).attr("for")&&indexs==1){	//在此行不存在，追加
				bool= "3";
				return false;
			}
		});
		if(bool=="0"){
			tdObj.find("label").each(function(index){
				if(propertyId==$(this).attr("for")&&indexs>1){	//列表中已存在此属性的值,添加多行替换掉原有的值
					bool= "2";
					return false;
				}
			});
		}
		if(bool=="0"){
			tdObj.find("label").each(function(index){
				if(propertyId!=$(this).attr("for")&&indexs>1){	//列表中不存在此属性的值，追加多行
					bool= "4";
					return false;
				}
			});
		}
		return false;
	});
	return bool;
}

//查询同属性被勾选的数量
function propertyIdChecked(propertyId){
	return $("#property-panel").find("#rowt"+propertyId).find('input:checkbox[sgp=checkbox'+propertyId+']:checked').length;
}
//图片上传弹出层
function uploadImgClick(obj){
	ImgObjSgp=obj;
	$("#uploadDivModal").modal('show');
}
//删除已上次的图片
function closeImg(obj){
	var td=$(obj).parent().prevAll().length;	//当前td的位置
	if(td=="1"){
		$(obj).parent().find("img").prop("src",basePath+"/img/zhu.png");
	}else{
		$(obj).parent().find("img").prop("src",basePath+"/img/xi.png");
	}
	$(obj).parent().find("img").removeAttr("alt");
	$(obj).hide();
}

//遍历成ztree所需的 Array(JSON)
function convertNodes(json) {
	var nodes = new Array();
	for (var i=0; i<json.length; i++) {
		nodes[i] = new Object();
		nodes[i].id = json[i].id;
		nodes[i].name = json[i].name;
		nodes[i].pId = json[i].parentId;
		nodes[i].open = true;
	}
	return nodes;
}
//打开商品类目
function categoryModel() {
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onCategoryClick	//设置点击事件
		}
	};

	jQuery.getJSON(basePath+"/category/list/json", function(json) {
		var zNodes = convertNodes(json);
		$.fn.zTree.init($("#category"), setting, zNodes);	//初始化生产ztree
		$('#categoryModal').modal();
	});
}
//点击类目树 触发的事件
function onCategoryClick(event, treeId, treeNode, clickFlag) {
	$('#categoryModal').modal('hide');	//隐藏弹出层
	$("#categoryName").val(treeNode.name);	//给“所属类目”文本框赋值
	$("#categoryId").val(treeNode.id);	//给所属类目ID 隐藏文本赋值
	
	//查询该类目下的属性 ，生产文本框体
	jQuery.getJSON(basePath+"/category/item/" + treeNode.id, function(json) {
		if (json.categoryProperties == null || json.categoryProperties.length < 1)
			return;
		
		var html=''; 
		for (var i=0; i<json.categoryProperties.length; i++) {
			var p = json.categoryProperties[i];
			html +=addInput(p.id,p.property,i);
		}
		$("#dynamicText").html(html);
	});
}

function onPvalues(cpid){
	$("#tableTRId").empty();
	$.ajax({
		url:basePath+"/category/queryCategoryPropertyValueList",
		type:"POST",
		data:{propertyPId:cpid},
		success:function(data){
			var tr="";
			for(var i=0;i<data.length;i++){
				tr+="<tr onclick=onTr('"+data[i].name+"','"+cpid+"','"+data[i].id+"') ><td>";
				tr+=data[i].name;
                tr+="</td></tr>";
			}
			$("#tableTRId").append(tr);
		}
	});
	$('#onPvaluesModal').modal();
}
function onTr(valueName,pid,valueId){
	$("#value"+pid).val(valueName);
	$("#valueId"+pid).val(valueId);
	$('#onPvaluesModal').modal('hide');
}
function addInput(propertyId,propertyName,index){
	var html='';
	html +='<div class="form-group">';
	html +='	<input type="hidden" id="propertyId" name="listCategoryBasicProperty['+index+'].propertyId" value="'+propertyId+'" >';
	html +='	<label for="name" class="col-sm-2 control-label">'+propertyName+'</label>';
	html +='	<div class="col-sm-6">';
	html +='		<input type="hidden" name="listCategoryBasicProperty['+index+'].valueId" id="valueId'+propertyId+'">';
	html +='		<input type="text" class="form-control" id="value'+propertyId+'" onClick="onPvalues('+propertyId+')">';
	html +='	</div>';
	html +='</div>';
  	return html;
}

//商品保存
function addCommodity(){
	
	//删除js生产的标签
	$("input[sgp='removeInput']").remove();
	var pIndex=0;
	//销售属性重置name 用于后台参数接收
	$("input[sgp='propertyInfoName']").each(function(i){
		$(this).attr("name","listPropertyInfo["+i+"].name");		//修改name用于后台接受
		$(this).next("input").attr("name","listPropertyInfo["+i+"].id");
		
		//修改 value
		var parentObj=$(this).parent().siblings(":last").find(".col-md-2");//获得所有属性值父层div对象
		if(parentObj.length>1){
			parentObj.each(function(j,pvObj){
				if(parentObj.length-1==j){
					return false;
				}
				var bool=false;
				//遍历属性值的标签，修改name,第一个input是选择框，第二个为属性值，第三个是值ID，第四个是属性ID
				$(pvObj).find("input").each(function(index,inputObj){
					if(index ==0){
						$(inputObj).attr("name","listPropertyValueInfo["+pIndex+"].mark");
					}else if(index==1){
						$(inputObj).attr("name","listPropertyValueInfo["+pIndex+"].value");
					}else if(index==2){
						$(inputObj).attr("name","listPropertyValueInfo["+pIndex+"].id");
					}else if(index==3){
						$(inputObj).attr("name","listPropertyValueInfo["+pIndex+"].propertyId");
					}
					bool=true;
				});
				bool && pIndex++;
			});
		}
	});
	//商品基本信息图片
	$("#imgTableId").find("tbody").find("tr:first").find("td").each(function(index){
		if(index==1){
			var imageUrl=$(this).find("img:first").attr("alt");
			if(imageUrl!=null&&imageUrl!=""){
				$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityBasicImage[0].imageUrl" value="'+imageUrl+'">');
				$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityBasicImage[0].type" value="0">');
				$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityBasicImage[0].smallUrl" value="'+imageUrl+'">');
			}
		}else if(index !=0){
			var imageUrl=$(this).find("img:first").attr("alt");
			if(imageUrl!=null&&imageUrl!=""){
				$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityBasicImage['+(index-1)+'].imageUrl" value="'+imageUrl+'">');
				$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityBasicImage['+(index-1)+'].type" value="1">');
				$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityBasicImage['+(index-1)+'].smallUrl" value="'+imageUrl+'">');
			}
		}
	});
	var i=0;
	var imgIndex=0;
	//商品属性重置name
	$("#shangPinShuXing").find("tr").each(function(aindex,trObj){
		var commodityTableId=UUID.prototype.createUUID();	//商品ID
		$(trObj).append('<input type="hidden" sgp="removeInput" name="listCommodityTable['+aindex+'].id" value="'+commodityTableId+'">');
		$(trObj).find("td").each(function(bindex,tdObj){
			if(bindex ==0){
				//商品属性信息
				$(tdObj).find("label").each(function(cindex,labelObj){
					var checkboxValue=$(labelObj).attr("id");
					var propertyId=$(labelObj).attr("for");
					$(labelObj).append('<input type="hidden" sgp="removeInput" name="listCommodityProperty['+i+'].propertyId" value="'+propertyId+'">');
					$(labelObj).append('<input type="hidden" sgp="removeInput" name="listCommodityProperty['+i+'].propertyValue" value="'+checkboxValue+'">');
					$(labelObj).append('<input type="hidden" sgp="removeInput" name="listCommodityProperty['+i+'].commodityId" value="'+commodityTableId+'">');
					i++;
				});
			}else if(bindex==1){
				$(tdObj).find("input").attr("name","listCommodityTable["+aindex+"].price");
			}else if(bindex==2){
				$(tdObj).find("input").attr("name","listCommodityTable["+aindex+"].stock");
			}else if(bindex==3){
				$(tdObj).find("input").attr("name","listCommodityTable["+aindex+"].name");
			}else if(bindex==4){
				$(tdObj).find("input").attr("name","listCommodityTable["+aindex+"].promotionWord");
			}
		});
		
		var eqTrIndex=aindex+1;
		//商品图片信息
		$("#imgTableId").find("tbody").find("tr:eq("+eqTrIndex+")").find("td").each(function(index){
			if(index==1){
				var imageUrl=$(this).find("img:first").attr("alt");
				if(imageUrl!=null&&imageUrl!=""){
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].imageUrl" value="'+imageUrl+'">');
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].type" value="0">');
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].smallUrl" value="'+imageUrl+'">');
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].commodityId" value="'+commodityTableId+'">');
					imgIndex =imgIndex+1;
				}
			}else if(index !=0){
				var imageUrl=$(this).find("img:first").attr("alt");
				if(imageUrl!=null&&imageUrl!=""){
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].imageUrl" value="'+imageUrl+'">');
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].type" value="1">');
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].smallUrl" value="'+imageUrl+'">');
					$(this).append('<input type="hidden" sgp="removeInput" name="listCommodityImage['+imgIndex+'].commodityId" value="'+commodityTableId+'">');
					imgIndex =imgIndex+1;
				}
			}
		});
	});
	
	$(".form-horizontal").submit();
}
