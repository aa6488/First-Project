package kr.co.green.board.model.service;

import java.util.ArrayList;

import kr.co.green.board.model.dto.BoardDTO;
import kr.co.green.common.PageInfo;

public interface BoardService {

	// 게시글 목록 조회
	public ArrayList<BoardDTO> boardList(PageInfo pi, String searchText);
	
	// 카운트
	public int boardListCount(String searchText);
	
	// 게시글 작성
	public int boardEnroll(BoardDTO board);
	
	// 조회수 늘리기
	public int boardView(int idx);
	
	// 상세조회
	public void boardSelect(BoardDTO board);
	
	// 게시글 수정
	public int boardUpdate(BoardDTO board);
	
	// 게시글 삭제
	public int boardDelete(int idx);
}
