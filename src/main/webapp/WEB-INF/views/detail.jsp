<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>

<!-- 카카오맵 API -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=679e813a7d173421907253e756e5a51b"></script>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

<link rel="stylesheet" href="${rootPath}/css/main.css" />

<link rel="stylesheet" href="${rootPath}/css/detail.css">

</head>

<body>

	<section class="container" id="main-container">
		<header class="jewon-main-header">
			<nav class="navbar navbar-expand-sm">
				<i class="fas fa-bars fa-2x" id="btn-ham" onclick="openNav()"></i>
				<form class="form-inline" action="${rootPath}/tour/search">
					<input class="form-control mr-sm-2" type="text" placeholder="Search" name="keyword">
				</form>
				<c:choose>
					<c:when test="${userDTO == null || userDTO.u_id == null}">
						<a href="${rootPath}/member/login" id="btn-login"><i class="fas fa-key fa-2x"></i></a>
					</c:when>
					<c:otherwise>
						<a href="${rootPath}/member/logout" id="btn-login">
							${userDTO.u_id}님 안녕하세요&nbsp;&nbsp;
							<i class="fas fa-lock fa-2x"></i>
						</a>
					</c:otherwise>
				</c:choose>
			</nav>
			<div id="mySidenav" class="sidenav">
				<a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a> 
				<a href="${rootPath}/">메인화면</a> 
				<c:forEach items="${AREALIST}" var="vo">
					<a href="${rootPath}/tour/baselist?areacode=${vo.code}">${vo.name}</a> 
				</c:forEach>
				<a href="${rootPath}/comment/list">댓글 모아보기</a> 
			</div>
			<div class="text-center" id="jewon-main-title">모꼬지</div>
			<div class="text-center" id="jewon-main-subtitle">축제 행사의 순 우리말 모꼬지 : 오늘은 어디 갈까</div>
		</header>
		
		<div class="row justify-content-center">
			<c:choose>
				<c:when test="${detailCommonDTO.firstimage == null}">
					<img class="img-box" src="${rootPath}/img/detail.png">
				</c:when>
				<c:otherwise>
					<img class="img-box" src="${detailCommonDTO.firstimage}">
				</c:otherwise>
			</c:choose>
			<div class="side-title">
				<h1>${detailCommonDTO.title}</h1>
				<h2>${detailIntroDTO.eventstartdate}~${detailIntroDTO.eventenddate}</h2>
				<h2>${detailIntroDTO.sponsor1}</h2>
				<h2>${detailCommonDTO.addr1}</h2>
				<div class="go-list">
					<a href="${rootPath}/tour/baselist?areacode=${AREACODE}">목록보기</a>
				</div>
			</div>
		</div>
		<div class="tab-menu" id="tab-introduce">
			<div class="tab-sub" id="btn-active">
				<a href="#tab-introduce">소개</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-map">지도</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-lodgments">숙박</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-comment">의견</a>
			</div>
		</div>
		<div class="intro">
			관람 가능연령 : ${detailIntroDTO.agelimit}<br /> 행사 종료일 : ${detailIntroDTO.eventenddate}<br /> 행사 홈페이지 : ${detailCommonDTO.homepage}<br /> 행사 장소 : ${detailIntroDTO.eventplace}<br /> 행사 시작일 : ${detailIntroDTO.eventstartdate}<br /> 공연시간 : ${detailIntroDTO.playtime}<br /> 관람 소요시간 : ${detailIntroDTO.spendtimefestival}<br /> 주최자 정보 : ${detailIntroDTO.sponsor1}<br /> 주최자 연락처 :
			${detailIntroDTO.sponsor1tel}<br /> 주관사 정보 : ${detailIntroDTO.sponsor2}<br /> 주관사 연락처 : ${detailIntroDTO.sponsor2tel}<br /> 이용요금 : ${detailIntroDTO.usetimefestival}<br /> 개요 : ${detailCommonDTO.overview}<br />

		</div>
		<div class="tab-menu" id="tab-map">
			<div class="tab-sub">
				<a href="#tab-introduce">소개</a>
			</div>
			<div class="tab-sub" id="btn-active">
				<a href="#tab-map">지도</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-lodgments">숙박</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-comment">의견</a>
			</div>
		</div>
		<div class="map">
			<div id="map" style="width: 100%; height: 450px; margin: 0 auto; margin-top: 20px; border-radius: 10px; box-shadow: 2px 2px 5px gray;"></div>
		</div>
		<div class="tab-menu" id="tab-lodgments">
			<div class="tab-sub">
				<a href="#tab-introduce">소개</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-map">지도</a>
				
			</div>
			<div class="tab-sub" id="btn-active">
				<a href="#tab-lodgments">숙박</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-comment">의견</a>
			</div>
		</div>
		<div class="lodgments">
			<c:choose>
				<c:when test="${stayList != null}">
					<c:forEach items="${stayList}" var="vo">
						<div class="lodgment" data-mapx="${vo.mapx}" data-mapy="${vo.mapy}" data-name="${vo.title}(${vo.tel})">
							<c:choose>
								<c:when test="${vo.firstimage==null}">
									<img class="lodgment-img" src="${rootPath}/img/booker.png"></img>
								</c:when>
								<c:otherwise>
									<img class="lodgment-img" src="${vo.firstimage}"></img>
								</c:otherwise>
							</c:choose>
							<div class="lodgment-introduce">
								<div class="lodgment-title">${vo.title}</div>
								<div class="lodgment-location">${vo.addr1}</div>
							</div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p>인근 숙박업소가 없습니다.</p>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="tab-menu" id="tab-comment">
			<div class="tab-sub">
				<a href="#tab-introduce">소개</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-map">지도</a>
			</div>
			<div class="tab-sub">
				<a href="#tab-lodgments">숙박</a>
			</div>
			<div class="tab-sub" id="btn-active">
				<a href="#tab-comment">의견</a>
			</div>
		</div>
		
		<div class="container" id="comment">
			<%@ include file = "/WEB-INF/views/commentlist.jsp" %>
		</div>

		<c:choose>
			<c:when test="${userDTO == null || userDTO.u_id == null }">
			</c:when>
			<c:otherwise>
				<fieldset>
				<legend>글쓰기</legend>
				<form method="POST" id="sendForm" name="sendForm">
					<div class="comment-sub-box">
							<div class="comment-sub-box-title">작성자</div>
							<div class="comment-sub-box-text">
								<input class="comment-input" type="text" name="writer" value="${userDTO.u_id}" readonly="readonly">
							</div>
						</div>
						<br />
						<div class="comment-sub-box">
							<div class="comment-sub-box-title">글내용</div>
							<div class="comment-sub-box-text">
								<textarea id="comment-textarea" name="text"></textarea>
							</div>
						</div>
						<input class="comment-input" type="hidden" name="contentid" value="${detailCommonDTO.contentid}">
						<input class="comment-input" type="hidden" name="areacode" value="${detailCommonDTO.areacode}"> 
						<input class="comment-input" type="hidden" name="sigungucode" value="${detailCommonDTO.sigungucode}">
						<button class="btn_writecomment" type="button">글쓰기</button>
					</form>
				</fieldset>
			</c:otherwise>
		</c:choose>
		
		<footer>
			<div class="text-center">@CopyRight 20191125-20191214 한국경영원 인재개발원 모꼬지</div>
		</footer>
		
	</section>
		
</body>

<script>

    function openNav() {
      document.getElementById("mySidenav").style.left = "0";
    }
    
    function closeNav() {
      document.getElementById("mySidenav").style.left = "-250px";
    }
    
	var container = document.getElementById('map');
	var options = {
			center: new kakao.maps.LatLng(${detailCommonDTO.mapy}, ${detailCommonDTO.mapx}),
			level: 3
		};
	// 마커가 표시될 위치입니다 
	var markerPosition  = new kakao.maps.LatLng(${detailCommonDTO.mapy}, ${detailCommonDTO.mapx}); 

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});
	
	var map = new kakao.maps.Map(container, options);
	
	marker.setMap(map);
	
	// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
	var zoomControl = new kakao.maps.ZoomControl();
	map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	
	var iwContent = '<div style="width:200px; padding:10px">${detailCommonDTO.addr1}</div>'
    iwPosition = new kakao.maps.LatLng(${detailCommonDTO.mapy}, ${detailCommonDTO.mapx}); //인포윈도우 표시 위치입니다

	// 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({
    position : iwPosition, 
    content : iwContent 
	});
  
	// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
	infowindow.open(map, marker);
	
	$(function(){
		
		$(".lodgment").click(function(){
			
			let mapx = $(this).attr("data-mapx")
			let mapy = $(this).attr("data-mapy")
			let name = $(this).attr("data-name")
			
			let url = "https://map.kakao.com/link/map/"+mapy+","+mapx
			
			window.open(url)
			
		})
		
		$(document).on("click","#comment-box",function(){
			
			let contentid = $(this).attr("data-id")
			let seq = $(this).attr("data-seq")
			let writer = $(this).attr("data-writer")
			let u_id = "${userDTO.u_id}"
			
			if(u_id === writer){
				if(confirm("삭제하시겠습니까?")){

					$.ajax({
						method : 'POST',
						url : '${rootPath}/comment/delete',
						data : {contentid : contentid, seq : seq},
						success : function(result){
					        $("#comment").html(result)
						},
						error: function(error){
							alert("댓글 삭제에 실패하였습니다.")
						}
					})
			}
				
			}else{
				alert("자신이 작성한 댓글만 삭제할 수 있습니다")
			}
			
			
			
		})
		
		$(".btn_writecomment").click(function(){
			
			var test = $("#sendForm").serialize()
			
			$.ajax({
				method : 'POST',
				url : '${rootPath}/comment/write',
				data : test,
				success : function(result){
		            
					$("#comment-textarea").val("");
		            $("#comment").html(result)
		                
				},
				error : function(error){
					alert("실패")
				}
			})
			
		})
		
	})
    
</script>

</html>