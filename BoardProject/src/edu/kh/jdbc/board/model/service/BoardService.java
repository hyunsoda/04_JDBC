package edu.kh.jdbc.board.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.board.model.dao.BoardDAO;
import edu.kh.jdbc.board.model.dto.Board;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardService {

	private BoardDAO dao = new BoardDAO();

	/** 게시글 목록 조회 서비스
	 * @return boardList
	 */
	public List<Board> selectAllBoard() throws Exception{

		// 커넥션 생성
		Connection conn = getConnection();
		
		// DAO 메서드 호출
		List<Board> boardList = dao.selectAllBoard(conn);
		
		// 커넥션 반환
		close(conn);
		
		// 결과 반환
		return boardList;
	}
	
	
	
}
