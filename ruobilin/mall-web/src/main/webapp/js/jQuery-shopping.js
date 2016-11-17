;(function($){
	$.extend($.fn,{
		shoping:function(options){
			var self=this,
				$shop=$('#settleup-2013'),
				$num=$('#shopping-amount');
			var S={
				init:function(){
					$(self).click(this.addShoping);
				},
				addShoping:function(e){
					e.stopPropagation();
					var $target=$(e.target),
					    x = $target.offset().left + 30,
						y = $target.offset().top + 10,
						X = $shop.offset().left+$shop.width()/2-$target.width()/2+10,
						Y = $shop.offset().top;
						if ($('#floatOrder').length <= 0) {
							$('body').append('<div id="floatOrder"><img src="'+options+'" width="50" height="50" /></div>');
						};
						var $obj=$('#floatOrder');
						$obj.css({'left': x,'top': y}).animate({'left': X,'top': Y-80},500,
								function() {
									$obj.stop(false, false).animate({'top': Y-20,'opacity':0},500,false,
									function(){
										$obj.remove();	
										num=Number($num.text());
										$num.text(num+1);
									});
						});	
				}
			};
			S.init(); 
		}
	});	
})(jQuery);

