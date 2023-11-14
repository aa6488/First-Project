package kr.co.green.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.green.board.model.dto.BoardDTO;
import kr.co.green.common.PageInfo;

public class FreeDAO {
	private  PreparedStatement pstmt;
	
	public  ArrayList<BoardDTO> boardList(Connection con, PageInfo pi, String searchText) {
		// posts 변수명도 많이 사용됨
		ArrayList<BoardDTO> list = new ArrayList<>();
				
		// 1. 쿼리 작성
		// MySQL offset 페이징  뒤로갈수록 성능이 떨어짐
//		String query = "SELECT fb_idx,"
//				+ "				fb_title,"
//				+ "				fb_in_date,"
//				+ "				fb_views,"
//				+ "				fb_writer"
//				+ "		FROM free_board"
//				+ "		LIMIT ? OFFSET ?";
		
		// MySQL cursor 페이징
//		String query = "SELECT fb_idx,"
//				+ "				fb_title,"
//				+ "				fb_in_date,"
//				+ "				fb_views,"
//				+ "				fb_writer"
//				+ "		FROM free_board"
//				+ "		WHERE fb_idx > ? LIMIT ?";
		
		
//		String query = "SELECT fb2.*"
//	            + "      FROM (SELECT rownum AS rnum, fb.* "
//	            + "           FROM (SELECT fb_idx,"
//	         + "                         fb_title,"
//	         + "                         fb_in_date,"
//	         + "                          fb_views,"
//	         + "                         fb_writer"
//	         + "                    FROM free_board"
//	         + "                    ORDER BY fb_idx ASC) fb) fb2"
//	         + "         WHERE rnum BETWEEN ? AND ?";
		
		String query = "SELECT fb_idx,"
				+ "				fb_title,"
				+ "				fb_in_date,"
				+ "				fb_views,"
				+ "				fb_writer"
				+ "		FROM free_board"
				+ "		WHERE fb_delete_date IS NULL"
				+ "		AND fb_title LIKE '%' || ? || '%'"
				+ "		ORDER BY fb_in_date DESC"
				+ "		OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
		
		
		
		
		
		
		try {
			// 2. 쿼리 사용할 준비
			pstmt = con.prepareStatement(query);
			
		// 3. 물음표 있으면 값 삽입
			pstmt.setString(1, searchText);
			pstmt.setInt(2, pi.getOffset());  // 0, 10, 20
			pstmt.setInt(3, pi.getBoardLimit()); // +5
			
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
			 	int idx = rs.getInt("FB_IDX");
			 	String title = rs.getString("FB_TITLE");
			 	String inDate = rs.getString("FB_IN_DATE");
			 	int views = rs.getInt("FB_VIEWS");
			 	String writer = rs.getString("FB_WRITER");
			 	
			 	BoardDTO board = new BoardDTO();
			 	board.setIdx(idx);
			 	board.setTitle(title);
			 	board.setInDate(inDate);
			 	board.setViews(views);
			 	board.setWriter(writer);
			 	
			 	list.add(board);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 3. 물음표 있으면 값 삽입
		
		
		// 4. 쿼리 실행
		
		return list;
	}

	// 전체 게시글 카운트
	public int boardListCount(Connection con, String searchText) {
		String query = "SELECT count(*) AS cnt FROM free_board"
				+ "			WHERE fb_delete_date IS NULL"
				+ "			AND fb_title LIKE '%' || ? || '%'";
		//                                 
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, searchText);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int result = rs.getInt("CNT");
				return result;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int boardEnroll(Connection con, BoardDTO board) {
	      // 1. 쿼리 작성
	      String query = "INSERT INTO free_board"
	            + "      VALUES(FB_IDX_SEQ.nextval," // 회원번호
	            + "            ?,"  // 제목
	            + "            ?,"  // 내용
	            + "            SYSDATE," // 작성일
	            + "            NULL, " // 수정일
	            + "            NULL, " // 삭제일
	            + "            0,"  // 조회수
	            + "            ?," // 작성자
	            + "            ?," // 파일 경로
	            + "            ?)"; // 파일 이름
	      
	      try {
	         // 2. 쿼리 사용할 준비
	         pstmt = con.prepareStatement(query);
	         
	         // 3. 물음표에 값 삽입
	         // 첫번째 : 제목, 두번째 : 내용, 세번째 : 작성자
	         pstmt.setString(1, board.getTitle());
	         pstmt.setString(2, board.getContent());
	         pstmt.setString(3, board.getWriter());
	         pstmt.setString(4, board.getFilePath());
	         pstmt.setString(5, board.getFileName());
	         
	         // 4. 쿼리 실행
	         int result = pstmt.executeUpdate();
	         
	         // 5. DB 연결 종료
	         pstmt.close();
	         con.close();
	         
	         return result;
	      } catch (SQLException e) {
	         e.printStackTrace();
	      }
	      return 0;
	   }



//	public int boardEnroll(Connection con, String title, String content, String name) {
//		
//		String query = "INSERT INTO free_board(FB_IDX,"
//				+ "							FB_TITLE,"
//				+ "							FB_CONTENT,"
//				+ "							FB_IN_DATE,"
//				+ "							FB_VIEWS,"
//				+ "							FB_WRITER)"
//				+ "		values(fb_idx_seq.nextval, ?, ?, sysdate, 0, ?)";
//		
//		try {
//			pstmt = con.prepareStatement(query);
//			
//			pstmt.setString(1, title);
//			pstmt.setString(2, content);
//			pstmt.setString(3, name);
//			
//			int result = pstmt.executeUpdate();
//			
//			pstmt.close();
//			con.close();
//			
//			return result;
//			
//		} catch (SQLException e) {
//			
//			e.printStackTrace();
//		}
//		
//		return 0;
//	}

	public int boardView(Connection con, int idx) {
		String query = "UPDATE free_board SET fb_views = fb_views+1"
				+ "			WHERE fb_idx = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, idx);
			
			int result = pstmt.executeUpdate();
			
			// Controller 에서 서비스연결 ServiceImpl 객체생성을 한번밖에안했는데 
			// 닫아버리면 그후 Select 게시글 조회할때 닫은상태여서 열어놓아야함
			// 만약 Select 할때 ServiceImpl 객체생성을 한번더하면 닫아놓아도 가능?
			
//			pstmt.close();
//			con.close();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			
		}
		
		
		return 0;
	}


	public void boardSelect(Connection con, BoardDTO board) {
		// 제목 작성자 조회수 작성일 내용
		String query = "SELECT fb_idx ,"
				+ "				 fb_title,"
				+ "				 fb_writer,"
				+ "				 fb_views,"
				+ "				 fb_in_date,"
				+ "				 fb_content,"
				+ "				 file_name"
				+ "			FROM free_board"
				+ "			WHERE fb_idx = ?";
		
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, board.getIdx());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board.setIdx(rs.getInt("FB_IDX"));
				board.setTitle(rs.getString("FB_TITLE"));
				board.setWriter(rs.getString("FB_WRITER"));
				board.setViews(rs.getInt("FB_VIEWS"));
				board.setInDate(rs.getString("FB_IN_DATE"));
				board.setContent(rs.getString("FB_CONTENT"));
				board.setFileName(rs.getString("FILE_NAME"));
				
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	public int boardUpdate(Connection con, BoardDTO board) {
		String query = "UPDATE free_board SET FB_TITLE = ?,"
				+ "							FB_CONTENT = ?,"
				+ "							FB_UPDATE_DATE = SYSDATE"
				+ "				WHERE FB_IDX = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getIdx());
			
		 	int result = pstmt.executeUpdate();
		 	
		 	pstmt.close();
		 	con.close();
		 	
		 	return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int boardDelete(Connection con, int idx) {
		String query = "UPDATE free_board"
				+ "		SET fb_delete_date = sysdate"
				+ "		WHERE fb_idx = ?";	
		
		
//		String query = "DELETE free_board WHERE fb_idx = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, idx);
			
			int result = pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}


}



















