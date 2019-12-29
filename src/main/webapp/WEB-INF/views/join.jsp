<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Document</title>
<!-- 컨테이너 및 main-subject, sub-title 설정 요소 값들-->
<link rel="stylesheet" href="${rootPath}/css/main.css">

<!-- 로그인 페이지 요소 설정 값들-->
<!-- 로그인 요소를 일부 사용하고 아래에서 재설정했습니다. 순서대로 배치해주세요.-->
<link rel="stylesheet" href="${rootPath}/css/login.css">
<link rel="stylesheet" href="${rootPath}/css/join.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">

	$(function() {
		
		$("#id_check").click(function(){
			
			var test = $(".user-form").serialize()
			
			$.ajax({
				method : 'POST',
				url : '${rootPath}/member/idcheck',
				data : test,
				dataType : 'json',
				success : function(result){
					let html = ""
					if(result==true){
						html += "중복된 아이디가 존재합니다."
					}else{
						html += "사용가능한 아이디입니다."
					}
					$(".overlap-check-result").html(html)
				}
				
			})
			
		})
		
		$("#btn-save").click(function() {
			
			var idPattern = /([^a-zA-z0-9]){4,20}/;
			var empPattern = /\s/g;
			
			if ($("#u_id").val() == ""){
				alert("아이디는 반드시 입력해야합니다.")
				$("#u_id").focus();
				return false
			}
			
			if(empPattern.test($("#u_id").val())){
				alert("아이디에 공백을 사용할 수 없습니다.")
				$("#u_id").focus()
				return false
			}
			
			if(idPattern.test($("#u_id").val())){
				alert("아이디는 영문과 숫자로만 입력해주세요.")
				$("#u_id").focus()
				return false
			}
			
			let pass = $("#u_password").val()
			if (pass == "") {
				alert("비밀번호를 입력하세요")
				$("#u_password").focus()
				return false
			}
			let re_pass = $("#re_password").val()
			if (re_pass == "") {
				alert("비밀번호를 한번 더 입력하세요")
				$("#re_password").focus()
				return false
			}

			if (pass != re_pass) {
				alert("비밀번호가 일치하지 않습니다.")
				$("#u_password").val("")
				$("#re_password").val("")
				$("#u_password").focus()
				return false
			}
			
			var test = $(".user-form").serialize()
			
			$.ajax({
				method : 'POST',
				url : '${rootPath}/member/idcheck',
				data : test,
				dataType : 'json',
				success : function(result){
					let html = ""
					if(result==true){
						alert("중복검사를 다시해주세요.")
					}else{
						$("form").submit()
					}
				},
				error : function(error){
					alert("서버로부터 응답이 없습니다.")
				}
				
			})
			
		})
	})
</script>

</head>
<body>
		<div class="container">
			<div class="login-box">
				<div class="main-subject"><a href="${rootPath}/">회원가입</a></div>
				<form:form modelAttribute="userDTO" autocomplete="on"
					class="user-form">
					<div class="text-box">아이디</div>
					<form:input path="u_id" type="text" placeholder="사용할 ID를 입력하세요" /><br/>
					<button id="id_check" class="check-btn" type="button">
						중복확인
					</button>
					<form:errors path="u_id" class="in-error" />
					<span id="u_id_msg"></span>
					<div class="overlap-check-result"></div>
					<div class="text-box">비밀번호</div>
					<div>
					<form:input path="u_password" type="password"
						class="text-box-warning" placeholder="8자리 이상 입력하세요" /><br/>
					<form:errors path="u_password" class="in-error" />
					</div>
					<div>
						<input type="password" id="re_password"
							placeholder="한번 더 입력하세요">
						<form:errors path="re_password" class="in-error" />
					</div>

					<button type="button" id="btn-save" class="check-btn">회원가입</button>

				</form:form>
			</div>
		</div>
</body>
</html>