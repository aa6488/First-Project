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

  <section id="post-form">
    <h2>ì ê¸ ìì±</h2>
    <form >
        <label for="title">제목:</label>
        <input type="text" id="title" name="title" required>
        
        <label for="author">ê¸ì´ì´:</label>
        <input type="text" id="author" name="author" required>
        
        <label for="content">내용:</label>
        <textarea id="content" name="content" rows="4" required></textarea>
        
        <!--  파일 업로드 필드 추가 -->
        <input type="file" id="file" name="file">
        <br><br>
        
        <button type="submit">ìì±</button>
    </form>
</section>

  <footer>
        <%@ include file="../common/footer.jsp" %>
  </footer>
</body>
</html>