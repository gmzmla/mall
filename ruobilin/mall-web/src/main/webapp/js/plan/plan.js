var map;

var flightPath;

var flightPlanCoordinates = new Array();

var stations = new Array();

var markers = new Array();

/**
 * 站点信息
 */
function Station(address, longitude, latitude, beginTime, endTime) {
	this.address = address;// 地址
	this.longitude = longitude;// 经度
	this.latitude = latitude// 纬度
	this.beginTime = beginTime;
	this.endTime = endTime;

}

function pushDate(station,id) {
	

		var str = '<ul id="'+id+'">'
				+ '<li>地点名称：<input type="text"  id="place_name_'+id+'" name="place_name" value="'
				+ station.address
				+ '"><a href="javascript:removeStation('
				+ station.longitude
				+ ','
				+ station.latitude
				+ ')">删除</a></li>'
				+ '<li>停留时间：<input type="text" id="beginTime_'+id+'" name="beginTime" style="width:95px">~<input type="text" id="endTime_'+id+'" name="endTime" style="width:95px"></li>'
				+ '</ul>';

		$('#list').prepend(str);
		   $('#beginTime_'+id).datepicker({changeMonth:true,changeYear:true});  
                   $('#endTime_'+id).datepicker({changeMonth:true,changeYear:true}); 

	}


function initialize() {

	var myLatLng = new google.maps.LatLng(39.917, 116.397);

	var myOptions = {

		zoom : 10,

		center : myLatLng,
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		mapTypeControl : true,
		mapTypeControlOptions : {
			mapTypeIds : [google.maps.MapTypeId.SATELLITE,
					google.maps.MapTypeId.ROADMAP]
		},
		navigationControl : true,
		scrollwheel : true,
		streetViewControl : false

	};

	map = new google.maps.Map(document.getElementById("planMap"), myOptions);

	
	google.maps.event.addListener(map, "click", function(event) {
		flightPlanCoordinates.push(event.latLng);
		if (flightPath != undefined && flightPath != null) {
			flightPath.setMap(null);
		}

		flightPath = new google.maps.Polyline({

			path : flightPlanCoordinates,
			strokeColor : "#f30", // 线条颜色 红色
			strokeOpacity : 0.7, // 透明度 70%
			strokeWeight : 5
				// 宽度 5像素

			});

		// 获得折线路径
		var path = flightPath.getPath();
		var marker = new google.maps.Marker();
		marker.setMap(map);
		marker.setPosition(event.latLng);
		marker.setTitle(event.latLng.toUrlValue());
		marker.setDraggable(false);
		markers.push(marker);

		flightPath.setMap(map);

		var geocoder = new google.maps.Geocoder();
		if (geocoder) {
			geocoder.geocode({
						'location' : event.latLng
					}, function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							if (results[0]) {

								var sta = new Station(
										results[0].address_components[1].long_name,
										event.latLng.lng(), event.latLng.lat(),
										null,null);
								stations.push(sta);

								pushDate(sta,stations.length-1);

								
								// alert(results[0].formatted_address);
							}
						} else {
							alert("Geocoder failed due to: " + status);
						}
					});
		}

	});

}

function addlistener(map, flightPath, marker) {
	google.maps.event.addListener(marker, "dragend", function(event) {

				// 获得折线路径
				var path = flightPath.getPath();
				path.push(event.latLng);
			});

}

function removeStation(lng, lat) {

	var removeMarker;
	for (var i = 0; i < stations.length; i++) {
		/**
		 * 清理列表
		 */
		if (stations[i].longitude == lng && stations[i].latitude == lat) {
			stations.splice(i, 1);
			flightPlanCoordinates.splice(i, 1);
			removeMarker = markers[i]
			markers.splice(i, 1);
			$('#'+i).remove();

		}
	}

//	pushDate(stations,stations.length-1);

	removeMarker.setMap(null)

	flightPath.setMap(null);

	flightPath = new google.maps.Polyline({
		path : flightPlanCoordinates,
		strokeColor : "#f30", // 线条颜色 红色
		strokeOpacity : 0.7, // 透明度 70%
		strokeWeight : 5
			// 宽度 5像素
		});

	flightPath.setMap(map);

}

function loadData(){
	
    var sts = $('#list ul')
    $.each(sts, function(i, item){
     var place_name = $(item).find('input[name="place_name"]').val();
     var beginTime = $(item).find('input[name="beginTime"]').val();   
     var endTime =  $(item).find('input[name="endTime"]').val();
     stations[i].address=place_name;
     stations[i].beginTime=beginTime;
     stations[i].endTime=endTime;
　　});  
  var sts = $.toJSON(stations);
  $('#plans').val(sts);	

}

$(function() {

   initialize();

	})