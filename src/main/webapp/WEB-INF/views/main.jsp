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

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

<link rel="stylesheet" href="${rootPath}/css/main.css" />

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
						<a href="#" id="btn-logout">
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
			<div class="text-center" id="jewon-main-title"><a href="${rootPath}/">모꼬지</a></div>
			<div class="text-center" id="jewon-main-subtitle">축제 행사의 순 우리말 모꼬지 : 오늘은 어디 갈까</div>
		</header>
		<c:choose>
			<c:when test="${TITLE == 'main'}">
				<div id="jewon-search-box">
					<div class="search-text">
						<ul>
							<c:forEach items="${TICKERLIST}" var="vo">
								<li><a href="${rootPath}/tour/detail?contentid=${vo.contentid}&areacode=${vo.areacode}&sigungucode=${vo.sigungucode}">${vo.title}</a></li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<div id="jewon-search-box" style="font-size: 18px;">
					${TITLE}
				</div>
			</c:otherwise>
		</c:choose>
		<article class="jewon-list">
			<c:choose>
				<c:when test="${BASELIST==null}">
        			<div class="text-center">
        				검색 결과가 없습니다.
        			</div>
        		</c:when>
				<c:otherwise>
					<c:forEach items="${BASELIST}" var="vo">
						<div class="jewon-list-item">
							<c:choose>
								<c:when test="${vo.firstimage2 == null}">
									<img src="${rootPath}/img/pastlist.png" class="jewon-list-item-img">
								</c:when>
								<c:otherwise>
									<img src="${vo.firstimage2}" class="jewon-list-item-img">
								</c:otherwise>
							</c:choose>
							<div class="jewon-list-item-textarea">
								<div class="jewon-list-item-title">
									<a href="${rootPath}/tour/detail?contentid=${vo.contentid}&areacode=${vo.areacode}&sigungucode=${vo.sigungucode}">${vo.title}</a>
								</div>
								<div class="jewon-list-item-term">
									<c:choose>
										<c:when test="${vo.eventstartdate == null}">
											전화번호 : ${vo.tel}
										</c:when>
										<c:otherwise>
											${vo.eventstartdate} ~ ${vo.eventenddate}
										</c:otherwise>
									</c:choose>
								</div>
								<div class="jewon-list-item-location">
									위치 : ${vo.addr1}
								</div>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</article>
		<div class="jewon-page">
			<ul class="pagination justify-content-center">
			<li class="page-item"><a class="page-link text-dark" href="${rootPath}/tour/${Tcontroller}?areacode=${areacode}&keyword=${keyword}&currentPageNo=${PAGE.firstPageNo}">처음</a></li>
				<li class="page-item"><a class="page-link text-dark" href="${rootPath}/tour/${Tcontroller}?areacode=${areacode}&keyword=${keyword}&currentPageNo=${PAGE.prePageNo}">이전</a></li>
				<c:forEach begin="${PAGE.startPageNo}" end="${PAGE.endPageNo}" var="pageNo">
					<li class="page-item "><a class="page-link text-dark" href="${rootPath}/tour/${Tcontroller}?areacode=${areacode}&keyword=${keyword}&currentPageNo=${pageNo}">${pageNo}</a></li>
				</c:forEach>
				<li class="page-item"><a class="page-link text-dark" href="${rootPath}/tour/${Tcontroller}?areacode=${areacode}&keyword=${keyword}&currentPageNo=${PAGE.nextPageNo}">다음</a></li>
				<li class="page-item"><a class="page-link text-dark" href="${rootPath}/tour/${Tcontroller}?areacode=${areacode}&keyword=${keyword}&currentPageNo=${PAGE.finalPageNo}">끝</a></li>
			</ul>
		</div>
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
    
    $(function(){
    	
    	$("#btn-logout").click(function(){
    		
    		if(confirm("로그아웃 하시겠습니까?")){
    			document.location.href="${rootPath}/member/logout"
    		}
    		
    	})
    	
    })
    
</script>

</html>