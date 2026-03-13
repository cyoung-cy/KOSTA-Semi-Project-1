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
    int updateMemberById(String password, String phone, String address, String[] preferredGenre, String cardInfo) throws SQLException;
    
    /*
     * 20260312
     * 이동혁
     * TODO: 로그아웃
     */
//    void logout(Member member);
    
    /*
     * 20260312
     * 이동혁
     * TODO: 회원탈퇴
     */
//    int deleteByMemberId() throws SQLException;
    
    

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
}
