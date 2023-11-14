package kr.co.green.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.green.member.model.service.MemberServiceImpl;

@WebServlet("/duplicateId.do")
public class DuplicateIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DuplicateIdController() {
    	
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");

		// 중복 검사 로직
		MemberServiceImpl memberService = new MemberServiceImpl();
		
		boolean isIdDuplicate = memberService.duplicateId(id);
		System.out.println(isIdDuplicate);
		PrintWriter out = response.getWriter();
		
		// isIdDuplicate 변수가 true 중복, false 사용 가능
		
		out.print(isIdDuplicate); //  ajax 에 response 로 값이들어감
		
//		if(isIdDuplicate == true) {
//			out.print("중복된 아이디입니다.");
//			
////			out.print("{'msg' : 사용불가}");
////			out.print("{'isDuplicate' : " + isIdDuplicate + "}" );
//		}else {
//			out.print("사용 가능한 아이디입니다.");
////			out.print("{'msg' : 사용가능}");
//		}
		
//		out.print("{ 'isDuplicate' : true }");
		out.flush();
		out.close();
		
		
	}

}













