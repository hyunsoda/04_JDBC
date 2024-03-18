package edu.kh.jdbc.main.model.service;

import java.sql.Connection;

import static edu.kh.jdbc.common.JDBCTemplate.*;
import edu.kh.jdbc.main.model.dao.MainDAO;
import edu.kh.jdbc.member.model.dto.Member;

public class MainService {

	private MainDAO dao = new MainDAO();

	/** 로그인 서비스
	 * @param memberId
	 * @param memberPw
	 * @return member
	 */
	public Member login(String memberId, String memberPw) throws Exception{
		
		// 1. Connection 생성
		Connection conn = getConnection();
		
		// 2. DAO 호출
		Member member = dao.login(conn, memberId, memberPw);
		
		// 3. Connection 반환
		close(conn);
		
		// 4.결과 반환
		return member;
	}

	/** 아이디 중복 검사 서비스
	 * @param memberId
	 * @return
	 */
	public int idDuplicationCheck(String memberId) throws Exception{
		
		Connection conn = getConnection();
		
		int result = dao.idDuplicationCheck(conn, memberId); //select하는 건데 임의로 1아니면 0으로 받도록 설정하는 것 / DML아님!
		
		close(conn);
		
		return result;
	}

	/** 회원 가입 서비스
	 * @param member
	 * @return
	 */
	public int signUp(Member member) throws Exception{
		
		Connection conn = getConnection();
		
		// DAO 호출
		int result = dao.signUp(conn, member); // INSERT 수행
		
		// 트랜잭션 처리
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		
		return result;
	}
	
}
