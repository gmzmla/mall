var currentYear,currentMonth,currentDay;
function getPart(date, part) {
	return date.split("-")[part];
}
function isLeap(year) {
	var y = parseInt(year);
	var t = parseInt(y / 4);
	if (t*4 != y)
		return 0;
	return 1;
}
function createYears(currentDate) {
	var dt = new Date();
	var y = dt.getFullYear();
	var max = 100;
	
	var ys = "";
	var iy = parseInt(y);
	if (currentYear == null){
		currentYear = iy;
	}
	if (currentDate != null && currentDate != "undefined") {
		currentYear = getPart(currentDate, 0);
	}
	for (var i=0; i<max; i++) {
		var crt = iy - i;
		ys += "<option value='"+crt+"' ";
		if (currentYear == crt){
			ys += "selected='selected' "
		}
		ys += ">"+crt+"</option>";
	}
	
	return ys;
}
function createMonths(currentDate) {
	var m = "1";
	var ms = "";
	var im = parseInt(m);
	if (currentMonth == null){
		currentMonth = im;
	}
	if (currentDate != null && currentDate != "undefined") {
		currentMonth = getPart(currentDate, 1);
	}
	for (var i=0; i<12; i++) {
		var crt = im + i;
		ms += "<option value='"+crt+"' ";
		if (currentMonth == crt){
			ms += "selected='selected' "
		}
		ms += ">"+crt+"</option>";
	}
	
	return ms;
}
function createDays(currentDate) {
	if (currentDay == null){
		currentDay = 1;
	}
	if (currentDate != null && currentDate != "undefined") {
		currentDay = getPart(currentDate, 2);
	}

	var leap = isLeap(currentYear);
	var max = 30;
	var m = parseInt(currentMonth);
	if (m == 2) {
		max = leap == 1 ? 29 : 28;
	}
	else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12){
		max = 31;
	}

	var ds = "";
	for (var i=1; i<=max; i++) {
		ds += "<option value='"+i+"' ";
		if (currentDay == i){
			ds += "selected='selected' "
		}
		ds += ">"+i+"</option>";
	}
	
	return ds;
}
$.extend({
	date: function(year, month, day, currentDate) {
		$("#"+year).html(createYears(currentDate));
		$("#"+year).change(function() {
			currentYear = $(this).val();
			$("#"+day).html(createDays());
		});
		$("#"+month).html(createMonths(currentDate));
		$("#"+month).change(function() {
			currentMonth = $(this).val();
			$("#"+day).html(createDays());
		});
		$("#"+day).html(createDays(currentDate));
	}
});
$.extend({
	dateValue: function(year, month, day) {
		return $("#"+year).val() + "-" + $("#"+month).val() + "-" + $("#"+day).val();
	}
});