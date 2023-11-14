package kr.co.green.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.green.board.model.service.BoardServiceImpl;

@WebServlet("/boardDelete.do")
public class FreeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FreeDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// 1. 값 받기 (idx, 제목, 내용)
		int idx = Integer.parseInt(request.getParameter("idx"));
		
		BoardServiceImpl boardService = new BoardServiceImpl();
		int result = boardService.boardDelete(idx);
		
		if(result == 0) {
			RequestDispatcher view = request.getRequestDispatcher("/views/common/error.jsp");
			view.forward(request, response);
		}else {
			response.sendRedirect("/freeList.do?cpage=1");
		}
		
	}

}
