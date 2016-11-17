$(function() {
	var menu = $("#categorys-2013"),
		flag = "mc" == menu.children(":eq(1)").attr("class");
	flag && menu.find(".item:last").remove();
	menu.mouseover(function(){
		$(this).addClass("hover");
	}).mouseout(function(){
		$(this).removeClass("hover");
	});
	
	menu.find(".item").each(function(){
		$(this).mouseenter(function(){
			$(this).addClass("hover");
			var sTop = $(document).scrollTop(), 
				mTop = menu.children(":eq(1)").offset().top,
				div = $(this).children(":last");
			if(sTop > mTop){
				div.css("top", sTop - mTop - (flag ? 2 : 3)); 
			}else{
				div.css("top", flag ? 3 : -2); 
			}
			flag && div.css("z-index", 1).css("background-color", "white").css("left", 210); 
		}).mouseleave(function(){
			$(this).removeClass("hover");
		});
	});
});
