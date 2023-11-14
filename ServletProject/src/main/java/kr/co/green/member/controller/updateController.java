  package kr.co.green.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.green.member.model.dto.MemberDTO;
import kr.co.green.member.model.service.MemberServiceImpl;

@WebServlet("/memberUpdate.do")
public class updateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public updateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		// 값 받아오기(이름, 아이디, 비밀번호)
		
		String name = request.getParameter("username");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String beforeName = (String) session.getAttribute("name");
		
		
		// DTO 객체 생성
		
		MemberDTO memberDTO = new MemberDTO(id, name, password, beforeName);
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// 서비스 객체 생성
		
		
		MemberServiceImpl memberService = new MemberServiceImpl();
		
		int result = memberService.memberUpdate(memberDTO, beforeName);
		
		if(result == 0) {
			updateAlert(response, "정보 수정에 실패했습니다.");
		}else {
			
			session.removeAttribute("name");   // 지금 쓰고있는 세션 삭제
			
			session.setAttribute("name", memberDTO.getName());
			
			updateAlert(response, "회원 정보가 수정되었습니다.");
		}
		
		
	}
	
	private void updateAlert(HttpServletResponse response, String msg) throws IOException {
		PrintWriter out = response.getWriter();
		out.println("<script>"
				+ "location.href='/form/updateForm.do';"
				+ "alert('"+ msg + "')"
				+ "</script>");
		out.flush(); // 안닫으면 PrintWriter 가 계속생성되고 열려있음 
		out.close();
	}
	
	
	
	
	
	
	
	
	

}
