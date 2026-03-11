package dao;


import dto.Member;

import java.sql.SQLException;

public interface MemberDAO {

    /*
     * 기능 : 시스템 실행시 제일 처음 보이는 StartView
     * */
    Member login(String userId, String userPwd)throws SQLException;
}
