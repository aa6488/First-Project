<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <%@ include file="../common/head.jsp" %>


</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <%@ include file="../common/nav.jsp" %>
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



  <section>
    <div class="login-form">
        <h2>로그인</h2>
        <form action="/login.do" method="post">
        <input type="hidden" name="hiddenTest" value="10">
        
          <label for="username">아이디</label>
          <input type="text" id="username" name="username" required>
  
          <label for="password">비밀번호:</label>
          <input type="password" id="password" name="password" required>
  
          <button type="submit">로그인</button>
        </form>
      </div>
  </section>
  
  <footer>
        <%@ include file="../common/footer.jsp" %>
  </footer>
</body>
</html>