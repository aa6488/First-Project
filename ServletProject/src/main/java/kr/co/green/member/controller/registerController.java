package kr.co.green.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import kr.co.green.member.model.dto.MemberDTO;
import kr.co.green.member.model.service.MemberServiceImpl;

@WebServlet("/register.do")
public class registerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public registerController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// INSERT
		// 값이 비어있는지 확인
		// new-username, new-userid, new-password
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		
		String id = request.getParameter("new-userid");
		String name = request.getParameter("new-username");
		String pwd = request.getParameter("new-password");
		
		// 패스워드 암호화 ********************************************************************** 
		// 임의의 문자열 생성             숫자 : 강도 나타낸다고 생각 높을수록 세짐
		String salt = BCrypt.gensalt(12);
		
		// 사용자가 입력한 비밀번호 + salt 암호화
		String hashedPassword = BCrypt.hashpw(pwd, salt);
		
		MemberDTO memberDTO = new MemberDTO(id, name, hashedPassword);
		
		// 이름 유효성 검사
		String namePattern = "^[가-힣]+$";   // 패턴생성 / 없이
		Pattern pattern = Pattern.compile(namePattern); // 자바 api 패턴사용
		Matcher matcher = pattern.matcher(name);  // jsp 에서 가져온값과 매칭
		
		// 패스워드 유효성 검사
		String pwdPattern = "^(?=.*[a-zA-Z])(?=.*[@$!%*?&\\#])[A-Za-z\\d@$!%*?&\\#]{6,20}$";
		Pattern passwordPattern = Pattern.compile(pwdPattern);
		Matcher passwordMatcher = passwordPattern.matcher(pwd);
		
		// 이름, 비번 유효성 검사 통과하면 회원가입 가능하게
		// 이름 틀렷을 때 : alert '이름은 한글만 가능합니다'
		// 비번 틀렷을 때 : alert '비밀번호가 정책에 맞지 않습니다.'
		if(matcher.matches() && passwordMatcher.matches()) {
			// 회원가입 진행
			MemberServiceImpl memberService = new MemberServiceImpl();
			int result = memberService.memberEnroll(memberDTO);
			
			if(result == 0) {
				validationAlert(response, "회원가입에 실패했습니다.");
//				response.sendRedirect("/views/common/error.jsp");
			}else {
				request.setAttribute("memberDTO", memberDTO);
				
			RequestDispatcher view = request.getRequestDispatcher("/views/member/registerResult.jsp");
			view.forward(request, response);
			}
		}else if(!matcher.matches()){
			validationAlert(response, "이름은 한글만 가능합니다.");
			
			return; // 코드를끝냄 
//			System.out.println("이름이 한글이 아닙니다.");
		}else if(!passwordMatcher.matches()) {
			validationAlert(response, "비밀번호가 정책에 맞지않습니다.");
			
			return;
		}
		
		
//		// 회원가입 진행
//		MemberServiceImpl memberService = new MemberServiceImpl();
//		int result = memberService.memberEnroll(memberDTO);
//		
//		if(result == 0) {
//			response.sendRedirect("/views/common/error.jsp");
//		}else {
//			request.setAttribute("memberDTO", memberDTO);
//			
//			RequestDispatcher view = request.getRequestDispatcher("/views/member/registerResult.jsp");
//			view.forward(request, response);
//		}
		
	}

	private void validationAlert(HttpServletResponse response, String msg) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<script>"
				+ "location.href='/register.do';"
				+ "alert('"+ msg + "')"
				+ "</script>");
		out.flush(); // 안닫으면 PrintWriter 가 계속생성되고 열려있음 
		out.close();
	}
	
	
	
	
	
}















