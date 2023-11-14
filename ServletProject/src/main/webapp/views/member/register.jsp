<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

	<script src="/resources/js/member/register.js"></script>
   <%@ include file="../common/head.jsp" %>
</head>
<body>
   <%@ include file="../common/header.jsp" %>
   <%@ include file="../common/nav.jsp" %>

  <section>
    <div class="signup-form">
        <h2>회원가입</h2>
        <form action= "/register.do" method="POST">
          <div class="input-container2">
        </div>
        <div class="input-container2">
          <label for="new-username">이름:</label>
          <input type="text" id="new-username" name="new-username" onkeyup="validateEmail();" required>
          <span id="nameMsg"></span>
        </div>

        <br>
        <div class="input-container2">
          <label for="new-userid">아이디:</label>
          <input type="text" id="new-userid" name="new-userid" onkeyup="duplicateId();" required>
          <span id="idCheck"></span>
        </div>
        <br>
        <div class="input-container2">
          <label for="new-password">비밀번호:</label>
          <input type="password" id="new-password" name="new-password" onkeyup="validatePassword();" required>
          </div>
          <span id="passwordMsg"></span>
          <br>
          <div class="input-container2">
            <label for="confirm-password">비밀번호 확인:</label>
            <input type="password" id="confirm-password" name="confirm-password" onkeyup="validatePassword();" required>
          </div>
          <span id="pwdChkMsg"></span>
  <br>
          <button type="submit">회원가입</button>
        </form>
      </div>
  </section>

   <%@ include file="../common/footer.jsp" %>
</body>
</html>

<script>

	// 아이디 중복확인
	function duplicateId(){
		const id = document.getElementById("new-userid").value;
		let idCheck = document.getElementById("idCheck");
		$.ajax({
			type: "POST",
			url: "/duplicateId.do",
			data: { id : id },
			success : function(response) { // 문자열로 들어옴
				if(response === 'true'){
					idCheck.style.color = "red";
					idCheck.innerHTML = "중복된 아이디입니다";
				}else{
					idCheck.style.color = "green";
					idCheck.innerHTML = "사용가능한 아이디입니다.";
					
				}
				console.log("성공 : " + response);
			},
			error : function(response){
				
				console.log("실패 : " + response);
			}
		})
	}

	// 이름 유효성검사
	
        function validateEmail() {   
           // ^[a-zA-Z0-9]+@  : 시작 문자열인데, 영어숫자 가능, 1개이상 무조건 있어야 함, @는 문자열
           // [a-zA-Z0-9]+\.  : 영어숫자 가능, 1개 이상 무조건 있어야 함, .는 문자열
           // [a-zA-Z]{2,}$   : 마지막 문자열이 영어로 끝나야 함, 2글자 이상

           // test@naver.com
           const nameRegex = /^[가-힣]{2,}$/;
           const name = document.getElementById("new-username").value;
           const nameMsg = document.getElementById("nameMsg");

           if(name == "") {
        	   nameMsg.innerHTML = "이름을 입력해주세요.";
        	   nameMsg.style.color = "red";
           } else if(nameRegex.test(name)) {
        	   nameMsg.innerHTML = "사용 가능한 이름입니다.";
        	   nameMsg.style.color = "green";
           } else {
        	   nameMsg.innerHTML = "정책에 맞게 입력해주세요.";
        	   nameMsg.style.color = "red";
           }

       }
	
		
	// 비밀번호 유효성 검사 + 비밀번호 확인하고 같은지
	
	        function validatePassword() {
            // 소문자 또는 대문자 최소 1개 이상
            // 특수문자 최소 1개 이상
            // 6자리 이상 20자리 이하
            const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[@$!%*?&\#])[A-Za-z\d@$!%*?&\#]{6,20}$/;
            const password = document.getElementById("new-password").value;
            const msg = document.getElementById("passwordMsg");
            
            if(password == "") {
                msg.innerHTML = "비밀번호를 입력하세요.";
                msg.style.color = "red";
            } else if(passwordRegex.test(password)) {
                msg.innerHTML = "사용 가능한 비밀번호 입니다.";
                msg.style.color = "green";
            } else {
                msg.innerHTML = "패스워드 정책에 맞지 않습니다.";
                msg.style.color = "red";
            }

            const passwordChk = document.getElementById("confirm-password").value;
            const msg2 = document.getElementById("pwdChkMsg");

            if(password === passwordChk) {
                msg2.innerHTML = "패스워드가 동일합니다.";
                msg2.style.color = "green";
            } else {
                msg2.innerHTML = "패스워드가 동일하지 않습니다..";
                msg2.style.color = "red";
            }
        }
	
</script>























