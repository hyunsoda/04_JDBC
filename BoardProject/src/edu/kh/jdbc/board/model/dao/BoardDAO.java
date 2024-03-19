package edu.kh.jdbc.board.model.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.board.model.dto.Board;
import static edu.kh.jdbc.common.JDBCTemplate.*;

public class BoardDAO {

	// JDBC 객체 참조 변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// XML에 작성된 SQL을 읽어와 저장할 객체를 참조하는 변수
	private Properties prop;
	
	public BoardDAO() { // 기본 생성자
		
		try {
			
			prop = new Properties();
			prop.loadFromXML(new FileInputStream("board-sql.xml"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	
	}

	/** 게시글 목록 조회 SQL 수행 DAO
	 * @param conn
	 * @return boardList
	 */
	public List<Board> selectAllBoard(Connection conn) throws Exception{

		// 결과 저장용 객체 생성
		List<Board> boardList = new ArrayList<Board>();
		
		try {
			// SQL 작성
			String sql = prop.getProperty("selectAllBoard");
			
			// SQL 수행 후 결과 반환받기
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			// 1행씩 접근하여 컬럼값을 얻어와 옮겨담기
			while(rs.next()) {
				int boardNo = rs.getInt("BOARD_NO");
				String boardTitle = rs.getString("BOARD_TITLE");
				String memberName = rs.getString("MEMBER_NM");
				String createDate = rs.getString("CREATE_DT");
				int readCount = rs.getInt("READ_COUNT");
				int commentCount = rs.getInt("COMMENT_COUNT");
				
				Board board = new Board();
				board.setBoardNo(boardNo);
				board.setBoardTitle(boardTitle);
				board.setMemberName(memberName);
				board.setReadCount(readCount);
				board.setCreateDate(createDate);
				board.setCommentCount(commentCount);
				
				boardList.add(board); // list에 추가
				
			}
			
		} finally {
			// JDBC 객체 자원 반환
			close(rs);
			close(stmt);
			
		}
		
		
		return boardList;
	}
	
}
