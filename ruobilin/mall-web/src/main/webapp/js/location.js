!function ($) {
	$.extend({
		_jsonp : {
			scripts : {},
			counter : 1,
			charset : "Utf-8",
			head : document.getElementsByTagName("head")[0],
			name : function (callback) {
				var name = "_jsonp_" + (new Date).getTime() + "_" + this.counter;
				this.counter++;
				var cb = function (json) {
					eval("delete " + name),
					callback(json),
					$._jsonp.head.removeChild($._jsonp.scripts[name]),
					delete $._jsonp.scripts[name]
				};
				return eval(name + " = cb"),
				name
			},
			load : function (a, b) {
				var c = document.createElement("script");
				c.type = "text/javascript",
				c.charset = this.charset,
				c.src = a,
				this.head.appendChild(c),
				this.scripts[b] = c
			}
		},
		getJSONP : function (a, b) {
			var c = $._jsonp.name(b),
			a = a.replace(/{callback};/, c);
			return $._jsonp.load(a, c),
			this
		}
	})
}
(jQuery);
var countryId=1;
var currentLocation = "北京";
var currentProvinceId = 1;

function provinceHtml(){
	var provinceHtml= '<div class="content"><div data-widget="tabs" class="m JD-stock" id="JD-stock">'
		+'<div class="mt">'
		+'    <ul class="tab">'
		+'        <li data-index="0" data-widget="tab-item" class="curr"><a href="javascript:void(0)" class="hover"><em>请选择</em><i></i></a></li>'
		+'        <li data-index="1" data-widget="tab-item" style="display:none;"><a href="javascript:void(0)" class=""><em>请选择</em><i></i></a></li>'
		+'        <li data-index="2" data-widget="tab-item" style="display:none;"><a href="javascript:void(0)" class=""><em>请选择</em><i></i></a></li>'
		+'        <li data-index="3" data-widget="tab-item" style="display:none;"><a href="javascript:void(0)" class=""><em>请选择</em><i></i></a></li>'
		+'    </ul>'
		+'    <div class="stock-line"></div>'
		+'</div>'
		+'<div class="mc" data-area="0" data-widget="tab-content" id="stock_province_item">'
	//	+'    <ul class="area-list">';
	
	//provinceHtml+=' <li><a href="javascript:void(0)" data-value="1">北京</a></li><li><a href="javascript:void(0)" data-value="2">上海</a></li><li><a href="javascript:void(0)" data-value="3">天津</a></li><li><a href="javascript:void(0)" data-value="4">重庆</a></li><li><a href="javascript:void(0)" data-value="5">河北</a></li><li><a href="javascript:void(0)" data-value="6">山西</a></li><li><a href="javascript:void(0)" data-value="7">河南</a></li><li><a href="javascript:void(0)" data-value="8">辽宁</a></li><li><a href="javascript:void(0)" data-value="9">吉林</a></li><li><a href="javascript:void(0)" data-value="10">黑龙江</a></li><li><a href="javascript:void(0)" data-value="11">内蒙古</a></li><li><a href="javascript:void(0)" data-value="12">江苏</a></li><li><a href="javascript:void(0)" data-value="13">山东</a></li><li><a href="javascript:void(0)" data-value="14">安徽</a></li><li><a href="javascript:void(0)" data-value="15">浙江</a></li><li><a href="javascript:void(0)" data-value="16">福建</a></li><li><a href="javascript:void(0)" data-value="17">湖北</a></li><li><a href="javascript:void(0)" data-value="18">湖南</a></li><li><a href="javascript:void(0)" data-value="19">广东</a></li><li><a href="javascript:void(0)" data-value="20">广西</a></li><li><a href="javascript:void(0)" data-value="21">江西</a></li><li><a href="javascript:void(0)" data-value="22">四川</a></li><li><a href="javascript:void(0)" data-value="23">海南</a></li><li><a href="javascript:void(0)" data-value="24">贵州</a></li><li><a href="javascript:void(0)" data-value="25">云南</a></li><li><a href="javascript:void(0)" data-value="26">西藏</a></li><li><a href="javascript:void(0)" data-value="27">陕西</a></li><li><a href="javascript:void(0)" data-value="28">甘肃</a></li><li><a href="javascript:void(0)" data-value="29">青海</a></li><li><a href="javascript:void(0)" data-value="30">宁夏</a></li><li><a href="javascript:void(0)" data-value="31">新疆</a></li><li><a href="javascript:void(0)" data-value="32">台湾</a></li><li><a href="javascript:void(0)" data-value="42">香港</a></li><li><a href="javascript:void(0)" data-value="43">澳门</a></li><li><a href="javascript:void(0)" data-value="84">钓鱼岛</a></li>';
	
	provinceHtml+=
		//'</ul>'
		'</div>'
		+'<div class="mc" data-area="1" data-widget="tab-content" id="stock_city_item"></div>'
		+'<div class="mc" data-area="2" data-widget="tab-content" id="stock_area_item"></div>'
		+'<div class="mc" data-area="3" data-widget="tab-content" id="stock_town_item"></div>'
		+'</div></div>';
	return provinceHtml;
}
function getAreaList(result){
	var html = ["<ul class='area-list'>"];
	var longhtml = [];
	var longerhtml = [];
	if (result&&result.length > 0){
		for (var i=0,j=result.length;i<j ;i++ ){
			result[i].name = result[i].name.replace(" ","");
			if(result[i].name.length > 12){
				longerhtml.push("<li class='longer-area'><a href='javascript:void(0)' data-value='"+result[i].id+"'>"+result[i].name+"</a></li>");
			}
			else if(result[i].name.length > 5){
				longhtml.push("<li class='long-area'><a href='javascript:void(0)' data-value='"+result[i].id+"'>"+result[i].name+"</a></li>");
			}
			else{
				html.push("<li><a href='javascript:void(0)' data-value='"+result[i].id+"'>"+result[i].name+"</a></li>");
			}
		}
	}
	else{
		html.push("<li><a href='javascript:void(0)' data-value='"+currentAreaInfo.currentFid+"'> </a></li>");
	}
	html.push(longhtml.join(""));
	html.push(longerhtml.join(""));
	html.push("</ul>");
	return html.join("");
}
function cleanKuohao(str){
	if(str&&str.indexOf("(")>0){
		str = str.substring(0,str.indexOf("("));
	}
	if(str&&str.indexOf("（")>0){
		str = str.substring(0,str.indexOf("（"));
	}
	return str;
}

function getStockOpt(id,name){
	if(currentAreaInfo.currentLevel==3){
		currentAreaInfo.currentAreaId = id;
		currentAreaInfo.currentAreaName = name;
		if(!page_load){
			currentAreaInfo.currentTownId = 0;
			currentAreaInfo.currentTownName = "";
		}
	}
	else if(currentAreaInfo.currentLevel==4){
		currentAreaInfo.currentTownId = id;
		currentAreaInfo.currentTownName = name;
	}else if(currentAreaInfo.currentLevel==1){
		currentAreaInfo.currentProvinceId=id;
		currentAreaInfo.currentProvinceName=name;
	}
	//添加20140224
//	$('#store-selector').removeClass('hover');
	if(page_load){
		page_load = false;
	}
	//替换gSC
	var address = currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+currentAreaInfo.currentTownName;
	$("#store-selector .text div").html(currentAreaInfo.currentProvinceName+cleanKuohao(currentAreaInfo.currentCityName)+cleanKuohao(currentAreaInfo.currentAreaName)+cleanKuohao(currentAreaInfo.currentTownName)).attr("title",address);
}
function getAreaListcallback(r){
	currentDom.html(getAreaList(r));
	if (currentAreaInfo.currentLevel >= 1){
		currentDom.find("a").click(function(){
			if(page_load){
				page_load = false;
			}
			var id=$(this).parent().parent().parent("div").attr("id");
			if(id=="stock_area_item"){
				currentAreaInfo.currentLevel=3;
				$('.close').click();
//				chooseArea($(this).attr("data-value"),$(this).html());
			}
			else if(id=="stock_town_item"){
				currentAreaInfo.currentLevel=1;
				$('.close').click();
			}
			if(id=="stock_province_item"){
				chooseProvince($(this).attr("data-value"),$(this).html());
			}else if(id=="stock_city_item"){
				chooseCity($(this).attr("data-value"),$(this).html());
			}else{
				getStockOpt($(this).attr("data-value"),$(this).html());
			}
		});
		if(page_load){ //初始化加载
//			currentAreaInfo.currentLevel = currentAreaInfo.currentLevel==1?2:3;
			if(currentAreaInfo.currentAreaId&&new Number(currentAreaInfo.currentAreaId)>0){
				getStockOpt(currentAreaInfo.currentAreaId,currentDom.find("a[data-value='"+currentAreaInfo.currentAreaId+"']").html());
			}
			else{
				getStockOpt(currentDom.find("a").eq(0).attr("data-value"),currentDom.find("a").eq(0).html());
			}
		}
	}
}
function chooseProvince(provinceId,provinceName){
	provinceContainer.hide();
	currentAreaInfo.currentLevel = 2;
	currentAreaInfo.currentProvinceId = provinceId;
	currentAreaInfo.currentProvinceName = provinceName;
	if(!page_load){
		currentAreaInfo.currentCityId = 0;
		currentAreaInfo.currentCityName = "";
		currentAreaInfo.currentAreaId = 0;
		currentAreaInfo.currentAreaName = "";
		currentAreaInfo.currentTownId = 0;
		currentAreaInfo.currentTownName = "";
	}
	areaTabContainer.eq(0).removeClass("curr").find("em").html(provinceName);
	areaTabContainer.eq(1).addClass("curr").show().find("em").html("请选择");
	areaTabContainer.eq(2).hide();
	areaTabContainer.eq(3).hide();
	cityContainer.show();
	areaContainer.hide();
	townaContainer.hide();
	currentDom = cityContainer;
//	$.getJSONP(basePath+"/area/findCities?provinceId="+provinceId+"&callback=getAreaListcallback");
	$.getJSONP("http://d.jd.com/area/get?fid="+provinceId+"&callback=getAreaListcallback");
}
function chooseCity(cityId,cityName){
	provinceContainer.hide();
	cityContainer.hide();
	currentAreaInfo.currentLevel = 3;
	currentAreaInfo.currentCityId = cityId;
	currentAreaInfo.currentCityName = cityName;
	if(!page_load){
		currentAreaInfo.currentAreaId = 0;
		currentAreaInfo.currentAreaName = "";
		currentAreaInfo.currentTownId = 0;
		currentAreaInfo.currentTownName = "";
	}
	areaTabContainer.eq(1).removeClass("curr").find("em").html(cityName);
	areaTabContainer.eq(2).addClass("curr").show().find("em").html("请选择");
	areaTabContainer.eq(3).hide();
	areaContainer.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
	townaContainer.hide();
	currentDom = areaContainer;
//	$.getJSONP(basePath+"/area/findAreas?cityId="+cityId+"&callback=getAreaListcallback");
	$.getJSONP("http://d.jd.com/area/get?fid="+cityId+"&callback=getAreaListcallback");
}
function chooseArea(areaId,areaName){
	provinceContainer.hide();
	cityContainer.hide();
	areaContainer.hide();
	currentAreaInfo.currentLevel = 4;
	currentAreaInfo.currentAreaId = areaId;
	currentAreaInfo.currentAreaName = areaName;
	if(!page_load){
		currentAreaInfo.currentTownId = 0;
		currentAreaInfo.currentTownName = "";
	}
	areaTabContainer.eq(2).removeClass("curr").find("em").html(areaName);
	areaTabContainer.eq(3).addClass("curr").show().find("em").html("请选择");
	townaContainer.show().html("<div class='iloading'>正在加载中，请稍候...</div>");
	currentDom = townaContainer;
	$.getJSONP("http://d.jd.com/area/get?fid="+areaId+"&callback=getAreaListcallback");
}
$("#store-selector .text").after(provinceHtml());
//$.getJSONP(basePath+"/area/provinces?countryId="+countryId+"&callback=getAreaListcallback");
$.getJSONP("http://d.jd.com/area/get?fid=0&callback=getAreaListcallback");
var areaTabContainer=$("#JD-stock .tab li");
var provinceContainer=$("#stock_province_item");
var cityContainer=$("#stock_city_item");
var areaContainer=$("#stock_area_item");
var townaContainer=$("#stock_town_item");
var currentDom = provinceContainer;
//当前地域信息
var currentAreaInfo;
//初始化当前地域信息
function CurrentAreaInfoInit(){
	currentAreaInfo =  {"currentLevel": 1,"currentProvinceId": 1,"currentProvinceName":"","currentCityId": 0,"currentCityName":"","currentAreaId": 0,"currentAreaName":"","currentTownId":0,"currentTownName":""};
	var ipLoc = getCookie("ipLoc-djd");
	ipLoc = ipLoc?ipLoc.split("-"):[1,72,0,0];
	if(ipLoc.length>0&&ipLoc[0]){
		currentAreaInfo.currentProvinceId = ipLoc[0];
	}
	if(ipLoc.length>1&&ipLoc[1]){
		currentAreaInfo.currentCityId = ipLoc[1];
	}
	if(ipLoc.length>2&&ipLoc[2]){
		currentAreaInfo.currentAreaId = ipLoc[2];
	}
	if(ipLoc.length>3&&ipLoc[3]){
		currentAreaInfo.currentTownId = ipLoc[3];
	}
}
var page_load = true;
(function(){
	$("#store-selector").unbind("mouseover").bind("mouseover",function(){
		$('#store-selector').addClass('hover');
		$("#store-selector .content,#JD-stock").show();
	}).find("dl").remove();
	CurrentAreaInfoInit();
	areaTabContainer.eq(0).find("a").click(function(){
		areaTabContainer.removeClass("curr");
		areaTabContainer.eq(0).addClass("curr").show();
		provinceContainer.show();
		cityContainer.hide();
		areaContainer.hide();
		townaContainer.hide();
		areaTabContainer.eq(1).hide();
		areaTabContainer.eq(2).hide();
		areaTabContainer.eq(3).hide();
//		var address = currentAreaInfo.currentProvinceName+currentAreaInfo.currentCityName+currentAreaInfo.currentAreaName+currentAreaInfo.currentTownName;
//		$("#store-selector .text div").html(currentAreaInfo.currentProvinceName+cleanKuohao(currentAreaInfo.currentCityName)+cleanKuohao(currentAreaInfo.currentAreaName)+cleanKuohao(currentAreaInfo.currentTownName))
//		.attr("title",address);
	});
	areaTabContainer.eq(1).find("a").click(function(){
		areaTabContainer.removeClass("curr");
		areaTabContainer.eq(1).addClass("curr").show();
		provinceContainer.hide();
		cityContainer.show();
		areaContainer.hide();
		townaContainer.hide();
		areaTabContainer.eq(2).hide();
		areaTabContainer.eq(3).hide();
	});
	areaTabContainer.eq(2).find("a").click(function(){
		areaTabContainer.removeClass("curr");
		areaTabContainer.eq(2).addClass("curr").show();
		provinceContainer.hide();
		cityContainer.hide();
		areaContainer.show();
		townaContainer.hide();
		areaTabContainer.eq(3).hide();
	});
//	provinceContainer.find("a").click(function() {
//		if(page_load){
//			page_load = false;
//		}
//		$("#store-selector").unbind("mouseout");
//		
//	});
})();

function getCookie(name) {
	var start = document.cookie.indexOf(name + "=");
	var len = start + name.length + 1;
	if ((!start) && (name != document.cookie.substring(0, name.length))) {
		return null;
	}
	if (start == -1) return null;
	var end = document.cookie.indexOf(';', len);
	if (end == -1) end = document.cookie.length;
	return unescape(document.cookie.substring(len, end));
};





