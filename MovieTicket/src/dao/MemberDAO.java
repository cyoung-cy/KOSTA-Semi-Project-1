package dao;


import dto.Member;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MemberDAO {
    /*
     * 20260311
     * 김채영
     * TODO: 전체 사용자 검색
     * */
    List<Member> selectUsers();

    /*
     * 20260311
     * 김채영
     * TODO:사용자 로그인 DAO 인터페이스
     * */
    Member login(String userId, String password) throws SQLException;

    /*
     * 20260311
     * 이동혁
     * TODO:사용자 회원가입 DAO 인터페이스
     * */
    int register(Member member) throws SQLException;
    
    /*
     * 20260312
     * 이동혁
     * TODO: 사용자 정보 수정 DAO 인터페이스
     */
    int updateMember(Member member) throws SQLException;
    

    /*
     * 20260312
     * 이동혁
     * TODO: 회원탈퇴
     */
    int deleteByMemberId(int memberId) throws SQLException;
    
    

    /*
     * 20260311
     * 김채영
     * TODO:사용자 삭제 DAO 인터페이스
     * */
    String deleteUserByName(String name);

    /*
     * 20260311
     * 김채영
     * TODO: 전체 사용자 상세검색
     * */
    List<Member> selectUserDetail(String userId);
    
    /*
     * 20260318
     * 한상혁
     * TODO: 특정 사용자 검색(사용자ID로 조회)
     * */
    Member selectOneById(Connection conn, int memberId);
}
