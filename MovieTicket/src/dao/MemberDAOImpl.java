package dao;

import dto.Member;

import java.sql.SQLException;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {

    @Override
    public List<Member> goodsSelect() throws SQLException {
        return List.of();
    }

    @Override
    public Member login(String userId, String password) throws SQLException {
        //로그인 로직
        return null;
    }

}
