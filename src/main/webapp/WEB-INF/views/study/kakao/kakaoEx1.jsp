<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>kakaoEx1.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		
		function addressSave(latitude, longitude) {
			let address = myform.address.value;
			if(address == "") {
				alert("선택한 지점의 장소명을 입력하세요");
				myform.address.focus();
				return false;
			}
			
			let query = {
					address : address,
					latitude : latitude,
					longitude : longitude
			}
			
			$.ajax({
				type : "post",
				url	 : "${ctp}/study/kakao/kakaoEx1",
				data : query,
				success : function(res) {
					if(res == "1") alert("선택한 지점이 DB에 저장되었습니다.")
					else alert("저장실패~~(같은 지점명이 있습니다. 이름을 변경해서 다시 등록하세요)");
				},
				error : function() {
					alert("전송오류!");
				}
			});
	}
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
	<h2>클릭한 지점에 마커 표시하기</h2>
	<hr/>
	<div id="map" style="width:100%;height:500px;"></div>
	<p><b>마커를 표시할 지도의 위치를 클릭해 주세요</b></p>
	<form name="myform">
		<div id="clickLatlng"></div>
	</form>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a0f384719a78c29635600b4d86d4f4df"></script>
	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = { 
	        center: new kakao.maps.LatLng(36.623830850235436 , 127.48177420970804 ), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };
	
	var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
	
	// 지도를 클릭한 위치에 표출할 마커입니다
	var marker = new kakao.maps.Marker({ 
	    // 지도 중심좌표에 마커를 생성합니다 
	    position: map.getCenter() 
	}); 
	// 지도에 마커를 표시합니다
	marker.setMap(map);
	
	// 지도에 클릭 이벤트를 등록합니다
	// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
	kakao.maps.event.addListener(map, 'click', function(mouseEvent) {        
	    
	    // 클릭한 위도, 경도 정보를 가져옵니다 
	    var latlng = mouseEvent.latLng; 
	    
	    // 마커 위치를 클릭한 위치로 옮깁니다
	    marker.setPosition(latlng);
	    
	    var message = '클릭한 위치의 위도는 ' + latlng.getLat() + ' 이고, ';
	    message += '경도는 ' + latlng.getLng() + ' 입니다 &nbsp';
		message += '<input type="button" value="처음위치로복귀" onclick="location.reload();"/>';
		message += '<p>선택한 지점의 장소명 : <input type="text" name="address"/> &nbsp;';
		message += '<input type="button" value="장소저장" onclick="addressSave('+latlng.getLat()+','+latlng.getLng()+')" class="btn btn-success btn-sm"/></p>';
		message += '';
		message += '';
		message += '';
	    
	    var resultDiv = document.getElementById('clickLatlng'); 
	    resultDiv.innerHTML = message;
	    
	});
	</script>
	<hr/>
	<jsp:include page="kakaoMenu.jsp"></jsp:include>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>