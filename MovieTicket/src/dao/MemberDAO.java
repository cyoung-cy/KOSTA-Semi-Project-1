package dao;


import dto.Member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAO {
    /*
     * 20260311
     * 김채영
     * TODO: 전체 사용자 검색
     * */
    List<Member> goodsSelect() throws SQLException;

    /*
     * 20260311
     * 김채영
     * TODO:사용자 로그인 DAO 인터페이스
     * */
    Member login(String userId, String password) throws SQLException;

    /*
     * 20260311
     * 이동혁
     * TODO:사용자 회원가입 DAO 인터페이스 구현
     * */
    int register(Member member) throws SQLException;

}
