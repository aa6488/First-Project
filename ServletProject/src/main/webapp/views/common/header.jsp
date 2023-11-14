<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<header>
	<h1>나의 홈페이지</h1>
	<div class="login-signup">
		<c:choose>
			<c:when test="${sessionScope.name != null}">
				<a href="/form/updateForm.do">정보수정</a>
				<a href="/logout.do">로그아웃</a>
			</c:when>
			<c:otherwise>
				<a href="/form/loginform.do">로그인</a> 
				<a href="/form/registerForm.do">회원가입</a>
			</c:otherwise>
		</c:choose>
	</div>
</header>