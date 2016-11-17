function changePage(index) {
	pageHandler(index);
}
(function($) {
	$.fn.extend({
		paging: function(count, size, current, url) {
			var getPageString = function(index) {
				var half = maxShow / 2;
				var str = '<ul class="pagination center">';
				if (index != 0) {
					str += '<li><a href="javascript:void(0)" onclick="changePage('+(index-1)+')">«</a></li>';
				}
				else {
					str += '<li class="disabled"><a href="javascript:void(0)">«</a></li>';
				}
				var start = index - half;
				var end = index + half;
				if (start < 0) {
					end += 0 - start;
					start = 0;
				}
				if (end > pageCount) {
					start -= end - pageCount;
					if (start < 0) {
						start = 0;
					}
					end = pageCount;
				}
				if (start != 0) {
					if (index == 0) {
						str += '<li class="active"><a href="javascript:void(0)" onclick="changePage(0)">1</a></li>';
					}else {
						str += '<li><a href="javascript:void(0)" onclick="changePage(0)">1</a></li>';
					}
					if (start != 1)
					{
						str += "...";
					}
				}
				for (var i=start; i<end; i++){
					if (i == index) {
						str += '<li class="active"><a href="javascript:void(0)" onclick="changePage('+i+')">'+(i+1)+'</a></li>';
					}
					else {
						str += '<li><a href="javascript:void(0)" onclick="changePage('+i+')">'+(i+1)+'</a></li>';
					}
				}
				
				if (end != pageCount) {
					if (end != (pageCount-1))
					{
						str += "...";
					}
					str += '<li><a href="javascript:void(0)" onclick="changePage('+(pageCount-1)+')">'+pageCount+'</a></li>';
				}
				if (index < (pageCount-1)){
					str += '<li><a href="javascript:void(0)" onclick="changePage('+(index+1)+')">»</a></li>';
				}
				else {
					str += '<li class="disabled"><a href="javascript:void(0)">»</a></li>';
				}
				str += "</ul>";
				return str;
			};
			
			var pageCount = 0;
			var pageHandler;
			var pageSize = 0;
			var elementId;
			var maxShow = 8;
			if (count < 1 || size < 1){
				return;
			}
	
			pageCount = parseInt(count/size);
			if (count % size > 0){
				pageCount ++;
			}
			pageHandler = handler;
			pageSize = size;
			$(this).html(getPageString(current));
			//pageHandler(currentPage);
		}
	});
})(jQuery);