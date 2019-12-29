<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Document</title>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

<!-- 컨테이너 및 main-subject, sub-title 설정 요소 값들-->
<link rel="stylesheet" href="${rootPath}/css/main.css">

<!-- 로그인 페이지 요소 설정 값들-->
<link rel="stylesheet" href="${rootPath}/css/login.css">

</head>
<body>
	<form method="POST" action="${rootPath}/member/login" class="container">
		<div class="login-box">
			<div class="main-subject">
				<a href="${rootPath}/">모꼬지</a>
			</div>
			<c:if test="${LOGIN_MSG == 'FAIL'}">
				<h3>아이디 또는 패스워드가 잘못되었습니다.</h3>
			</c:if>
	
			<input type="text" name="u_id" placeholder="ID를 입력하세요"><br/> 
			<input type="password" name="u_password" placeholder="Password"><br/>
			<button id="key">
				<i class="fas fa-key fa-2x"></i>
			</button>
			<div class="login-util-box">
			<!-- 모꼬지에서 제공하지 않는 페이지 팝업창 -->
				<div class="search">
					<a href="#">아이디 찾기</a>
					<a href="#">비밀번호 찾기</a>
				</div>
				<div class="join">
					<a href="${rootPath}/member/join">회원가입</a>
				</div>
			</div>
		</div>
	</form>
</body>

<script>
	
</script>
</html>