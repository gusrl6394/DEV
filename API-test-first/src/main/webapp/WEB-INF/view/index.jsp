<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>REVIEW REGISTER</title>
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/foodvisor/css/bootstrap.css"/>">
<script type="text/javascript" src="<c:url value="/resources/foodvisor/js/jquery-3.3.1.js"/>"></script>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=m7532gvjoh"></script>
<script type="text/javascript" src="<c:url value="/resources/foodvisor/js/popper.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/foodvisor/js/bootstrap.js"/>"></script>

<script type="text/javascript">
// 아래는 주소 관련 스크립트 
function getAddr(){
	console.log("query:"+$("#query").serialize())
	console.log("query:"+$("#query").val())
	var param = {
		query : $("#query").val(),
		currentPage : 1,
		countPerPage : 10,
		resultType : "JSON"
		}
	// AJAX 주소 검색 요청
	$.ajax({
		url:"<c:url value="/sample/getAddrApi"/>"									// 고객사 API 호출할 Controller URL
		,type:"post"
		,data:param																	// 요청 변수 설정
		,dataType:"JSON"															// 데이터 결과 : JSON
		,success:function(jsonStr){			
			var errorMessage = jsonStr.results.common.errorMessage;
			var totalCount = jsonStr.results.common.totalCount;
			if(jsonStr != null && totalCount != 0 && errorMessage == "정상"){
				makeListJson(jsonStr);				
			}else{
				if(totalCount == 0 && errorMessage == "정상"){
					alert("검색결과 없음");
				}else{
					alert(errorMessage);
				}
			}
		}
		,error:function(request,status,error){
			alert(JSON.stringify(request,status,error));
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    }
	});
}

function makeListJson(jsonStr){	
	//alert("성공");
	$("#zip_codeList").empty();
	var modalhtml = new String();
	$(jsonStr.results.juso).each(function(){
		console.log(this.zipNo);
		console.log(this.roadAddr);
		modalhtml += "<tr>";
		modalhtml += "<td>";
		modalhtml += this.zipNo;
		modalhtml += "</td>";
		modalhtml += "<td>";		
		modalhtml += '<a href="#" onclick="put(\''+(this.roadAddr).trim()+'\',\''+this.zipNo+'\')">'+this.roadAddr+'</a>';
		modalhtml += "</td>";
		modalhtml += "</tr>";
	});
	if(modalhtml==""){
		modalhtml = "<tr><td>None</td><td>None</td></tr>";
	}
	console.log("after-modalhtml:"+modalhtml);
	// 완성된 html(우편번호 list)를 zip_codeList밑에 append
    $("#zip_codeList").append(modalhtml);
}
//원하는 우편번호 선택시 함수 실행
function put(address,zipcode){
    var address = address;
    var zipcode = zipcode;
    // 모달창 닫기
    $("#zip_codeModal").modal("hide");
    $("#zip_code").val(zipcode);
    $("#address1").val(address);
    // Map 호출을 위한 사전체크 함수 호출
    Mapcheck1();
}
function Enter_Check() {
	if (window.event.keyCode == 13) {
		getAddr();
	}
}

// MAP
function Mapcheck1() {
	console.log("change감지");
	var address1 = $("#address1").val();
	console.log(address1);
	if(address1 == "" || address1 == undefined){
		console.log("값없음");
	}else{
		console.log("값있음");
		$.ajax({
			url:"<c:url value="/sample/getGeocoding"/>"					// 고객사 API 호출할 Controller URL
			,type:"post"
			,data: {address1:address1}									// 요청 변수 설정
			,dataType:"text"											// 데이터 결과 : JSON
			,success:function(data){					
				var mapdata = data.split(",")				
				// https://www.w3schools.com/html/html5_geolocation.asp
				
				// x,y 서로 바꾸면 동작됨
				// https://navermaps.github.io/maps.js.ncp/naver.maps.LatLng.html
				// Name	Type	Default	Description
				// lat	number	0		위도
				// lng	number	0		경도
				TargetGeolocation(parseFloat(mapdata[0]), parseFloat(mapdata[1]));
			}
			,error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    }
		});
	}
};
</script>
</head>
<body>
<div>
	<!-- 검색창 -->
	<div class = "row">
	    <div class = "col-xs-12 col-md-12">
	        <div class = "input-group">
	            <span class = "input-group-addon">우편번호</span>
	            <input type = "text" class = "form-control" id = "zip_code" name = "zip_code">
	            <span class = "input-group-addon"><a href = "#" id = "zip_codeBtn" data-toggle="modal" data-target="#zip_codeModal">검색하기</a></span>                
	        </div>
	    </div>
	</div>
	<div class = "row">
	    <div class = "col-xs-12 col-md-12">
	        <div class = "input-group">
	            <span class = "input-group-addon">주소</span>
	            <input type = "text" class = "form-control" id = "address1">                
	        </div>
	    </div>
	</div>        
	<div class = "row">
	    <div class = "col-xs-12 col-md-12">
	        <div class = "input-group">
	            <span class = "input-group-addon">상세주소</span>
	            <input type = "text" class = "form-control" id = "address2">        
	        </div>
	    </div>
	</div>
</div>

<!-- 모달창 --> 
<div class="modal fade" id="zip_codeModal">				  
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header text-center">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h3 class="modal-title" id="myModalLabel">우편번호</h3>
            </div>
            <span class="label label-primary center-block">
           		<strong style="font-size:2em;">엔터 및 검색 버튼을 눌러야 조회가 됩니다.</strong>
            </span>    
            <div class="modal-body text-center">
                 <div id = "zip_codeForm" onsubmit="return false;">
                         <div class = "input-group">
                            <span class = "input-group-addon">주소 입력</span>
                            <input type="text" class = "form-control" name="query" id="query" onkeydown="JavaScript:Enter_Check();">
                            <span class = "input-group-btn">                   
                                <input type="button" class = "btn btn-warning" value="검색" name="searchBtn" id="searchBtn" onClick="getAddr()">                         
                            </span>
                        </div>
                </div>
                <p>
                </p>
                <div>
                <div style="width:100%; height:200px; overflow:auto">
                       <table class = "table text-center">
                        <thead>
                            <tr>
                                <th style="width:150px;">우편번호</th>
                                <th style="width:600px;">주소</th>
                                </tr>
                        </thead>
                        <tbody id="zip_codeList"></tbody>
                    </table>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div>
	<div id="map" style="width:100%;height:400px;"></div>
</div>
		
</body>
<script>
var map = new naver.maps.Map('map', {
	// 기본좌표
    center: new naver.maps.LatLng(37.5666805, 126.9784147),
    zoom: 5,
    mapTypeId: naver.maps.MapTypeId.NORMAL
});

var infowindow = new naver.maps.InfoWindow();
// 검색된 결과를 MAP으로 다시 호출
function TargetGeolocation(x,y) {
    var location = new naver.maps.LatLng(y,x);
    map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
    map.setZoom(14); // 지도의 줌 레벨을 변경합니다.
    
    infowindow.close();
    infowindow = new naver.maps.InfoWindow();
    infowindow.setContent('<div style="padding:20px;">' + 'Target 위치' + '</div>');
    infowindow.open(map, location);
    console.log('Coordinates: ' + location.toString());
}

function onSuccessGeolocation(position) {
    var location = new naver.maps.LatLng(position.coords.latitude,
                                         position.coords.longitude);
    map.setCenter(location); // 얻은 좌표를 지도의 중심으로 설정합니다.
    map.setZoom(10); // 지도의 줌 레벨을 변경합니다.
    
    infowindow.setContent('<div style="padding:20px;">' + 'geolocation.getCurrentPosition() 위치' + '</div>');

    infowindow.open(map, location);
    console.log('Coordinates: ' + location.toString());
}

function onErrorGeolocation() {
    var center = map.getCenter();

    infowindow.setContent('<div style="padding:20px;">' +
        '<h5 style="margin-bottom:5px;color:#f00;">Geolocation failed!</h5>'+ "latitude: "+ center.lat() +"<br />longitude: "+ center.lng() +'</div>');

    infowindow.open(map, center);
}

$(window).on("load", function() {
    if (navigator.geolocation) {
    	// 현재접속된 주소 반환
        navigator.geolocation.getCurrentPosition(onSuccessGeolocation, onErrorGeolocation);
    } else {
        var center = map.getCenter();
        infowindow.setContent('<div style="padding:20px;"><h5 style="margin-bottom:5px;color:#f00;">Geolocation not supported</h5></div>');
        infowindow.open(map, center);
    }
});
</script>
</html>