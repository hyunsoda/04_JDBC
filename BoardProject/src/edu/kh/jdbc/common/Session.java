package edu.kh.jdbc.common;

import edu.kh.jdbc.member.model.dto.Member;

public class Session {

	// client와 server간의 지속적인 커뮤니케이션을 가능하게 하는 것
	
	// 로그인 : DB에 기록된 회원 정보를 가지고 오는 것.
	//			-> 로그아웃을 할 때까지 프로그램에서 회원 정보가 유지
	
	public static Member loginMember = null;
	
	// loginMember == null  ->  로그아웃 상태
	// loginMember != null  ->  로그인 상태
	
	
	
}
