package dao;


import dto.Member;

import java.sql.SQLException;

public interface MemberDAO {
<<<<<<< HEAD
	/*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 로그인 DAO 인터페이스 구현
	 * */
	Member login (String memberId)

	/*
	 * 20260311
	 * 이동혁
	 * TODO:사용자 회원가입 DAO 인터페이스 구현
	 * */

=======

    /*
     * 기능 : 시스템 실행시 제일 처음 보이는 StartView
     * */
    Member login(String userId, String userPwd)throws SQLException;
>>>>>>> main
}
