package dao;

import dto.Member;
import util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemberDAOImpl implements MemberDAO {

    @Override
    public List<Member> goodsSelect() throws SQLException {
        return List.of();
    }


    @Override
    public int register(Member member) throws SQLException {

        return 0;
    }

    @Override
    public Member login(String userId, String password)  throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from MEMBER where USER_ID = ? and PASSWORD = ?";
        Member member = null;

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setString(2,password);
            rs = ps.executeQuery();
            
            
            if(rs.next()) {
            	List<String> genreList = Stream.of(rs.getString("PREFERRED_GENRE")
            			.split(","))
            			.map(String::trim)
            			.collect(Collectors.toList());
                member = new Member(
                		rs.getInt("MEMBER_ID"),
                		rs.getString("USER_ID"),
                		rs.getString("PASSWORD"),
                		rs.getString("NAME"),
                		rs.getString("PHONE"),
                		rs.getString("ADDRESS"),
                		rs.getString("BIRTH_DATE"),
                		genreList,
                		rs.getString("CARD_INFO"),
                		rs.getString("ROLE")
                );
            }
        } finally {
            DbManager.close(con, ps, rs);
        }
        return member;
    }


    /*
     * 20260312
     * 이동혁
     * TODO: 회원 정보를 받아서 업데이트 하는 DAO 구현
     */
	@Override
	public int updateMemberById(String password, String phone, String address, String[] preferredGenre, String cardInfo)
			throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
