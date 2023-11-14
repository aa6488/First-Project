package kr.co.green.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.green.member.model.dto.MemberDTO;

public class MemberDAO {
	private PreparedStatement pstmt;
	
	// 회원가입
	public int memberEnroll(Connection con, MemberDTO memberDTO) {
		String query = "INSERT INTO member VALUES(?, ?, ?, SYSDATE)";
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getPwd());
			pstmt.setString(3, memberDTO.getName());
			
			result = pstmt.executeUpdate(); // 실행메소드 실패: 0 성공: 1 반환
			
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return result;
	}
	
	// 로그인
	public MemberDTO memberLogin(Connection con, String id) {
		String query = "SELECT member_id, member_pwd, member_name,"
				+ "		 member_in_date FROM member"
				+ "		WHERE member_id = ?";
		
		MemberDTO result = new MemberDTO();
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			// 위에 쿼리실행시 조건에 해당하는 DB 내용은 한개밖에안나옴 
			// while 안쓸시 첫번째 꺼내고 다음꺼 꺼낼때 안에 코드들 오류발생해서 
			// while문을 넣어서 false 값 나오게 만듦
			//  다음 한줄 꺼냄
			while(rs.next()) {
				String resultId = rs.getString("MEMBER_ID");
				String resultPwd = rs.getString("MEMBER_PWD");
				String resultName = rs.getString("MEMBER_NAME");
				String resultIndate = rs.getString("MEMBER_IN_DATE");
				
				result.setId(resultId);
				result.setPwd(resultPwd);
				result.setName(resultName);
				result.setIndate(resultIndate);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean duplicateId(Connection con, String id) {
		String query = "SELECT member_id FROM MEMBER "
				+ "		WHERE member_id = ? ";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			
			// 쿼리가 조회된 결과(ResultSet)가 있다면 true
			// 없다면 false
			return rs.next();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	
	
	
	}

	public void selectMember(Connection con, MemberDTO memberDTO) {
		String query = "SELECT member_id,"
				+ "				 member_name, "
				+ "				member_in_date "
				+ "				 FROM MEMBER"
				+ "				WHERE member_name = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, memberDTO.getName());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("MEMBER_ID");
				String name = rs.getString("MEMBER_NAME");
				String indate = rs.getString("MEMBER_IN_DATE");
				
				memberDTO.setId(id);
				memberDTO.setName(name);
				memberDTO.setIndate(indate);
				
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public int memberUpdate(Connection con, MemberDTO memberDTO, String beforeName) {
		String query = "UPDATE member"
				+ "		 SET member_id = ?, "
				+ "			member_name = ? "
				+ "		WHERE member_name = ? ";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, memberDTO.getId());
			pstmt.setString(2, memberDTO.getName());
			pstmt.setString(3, beforeName);
			
			int result = pstmt.executeUpdate();
			return result;
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return 0;
	}

	public int memberDelete(Connection con, String name) {
		String query = "DELETE FROM member "
				+ "		WHERE member_name = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, name);
			
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





















