var pageCount = 0;
var pageHandler;
var pageSize = 0;
var elementId;
var maxShow = 8;
function getPageString(index) {
	var half = maxShow / 2;
	var str = '';
	if (index != 0)
	{
		str += '<a class="prevPage" href="javascript:void(0)" onclick="changePage('+(index-1)+')"></a>';
	}
	else {
		str += '<a class="noPrevPage" href="javascript:void(0)"></a>';
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
		str += '<a href="javascript:void(0)" onclick="changePage(0)">1</a>';
		if (start != 1)
		{
			str += "<a>...</a>";
		}
	}
	for (var i=start; i<end; i++){
		if (i == index) {
			str += '<a class="currentPage">'+(i+1)+'</a>';
		}
		else {
			str += '<a href="javascript:void(0)" onclick="changePage('+i+')">'+(i+1)+'</a>';
		}
	}
	
	if (end != pageCount) {
		if (end != (pageCount-1))
		{
			str += "<a>...</a>";
		}
		str += '<a href="javascript:void(0)" onclick="changePage('+(pageCount-1)+')">'+pageCount+'</a>';
	}
	if (index < (pageCount-1)){
		str += '<a class="nextPage" href="javascript:void(0)" onclick="changePage('+(index+1)+')"></a>';
	}
	else {
		str += '<a class="noNextPage" href="javascript:void(0)"></a>';
	}
	str += "";
	return str;
}
function changePage(index) {
	if (pageHandler(index) == true) {
		$("#"+elementId).html(getPageString(index));
	}
}
$.extend({
	paging: function(count, size, id, handler) {
		if (count < 1 || size < 1){
			return;
		}

		pageCount = parseInt(count/size);
		if (count % size > 0){
			pageCount ++;
		}
		pageHandler = handler;
		pageSize = size;
		elementId = id;
		$("#"+elementId).html(getPageString(0));
		pageHandler(0);
	}
});