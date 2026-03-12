package dao;

import dto.Member;
import util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {



    @Override
    public int register(Member member) throws SQLException {

        return 0;
    }

    @Override
    public String deleteUserByName(String name) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "delete from MEMBER where NAME = ?";
        Member member = null;
        int re = 0;
        try {
           con = DbManager.getConnection();
           ps = con.prepareStatement(sql);

           ps.setString(1, name);

           re = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, null);
        }
        if(re == 1){
            return name;
        }
        return null;
    }

    @Override
    public List<Member> selectUserDetail(String name) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from MEMBER where ROLE = 'user' and NAME = ?";
        Member member = null;
        List<Member> list = new ArrayList<>();

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            while(rs.next()){
                member = new Member(rs.getInt("MEMBER_ID"), rs.getString("USER_ID"),rs.getString("PASSWORD"),rs.getString("NAME"),rs.getString("PHONE"),rs.getString("ADDRESS"), rs.getString("BIRTH_DATE"), rs.getString("PREFERRED_GENRE").split(","), rs.getString("CARD_INFO"), rs.getString("ROLE"));
                list.add(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, rs);
        }
        return list;
    }

    @Override
    public List<Member> selectUsers() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select MEMBER_ID, USER_ID, NAME from MEMBER where ROLE = 'user'";
        Member member = null;
        List<Member> list = new ArrayList<>();

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                member = new Member(rs.getInt("MEMBER_ID"), rs.getString("USER_ID"), rs.getString("NAME"));
                list.add(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, rs);
        }
        return list;
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
                member = new Member(rs.getInt("MEMBER_ID"), rs.getString("USER_ID"),rs.getString("PASSWORD"),rs.getString("NAME"),rs.getString("PHONE"),rs.getString("ADDRESS"), rs.getString("BIRTH_DATE"), rs.getString("PREFERRED_GENRE").split(","), rs.getString("CARD_INFO"), rs.getString("ROLE"));
            }
        } finally {
            DbManager.close(con, ps, rs);
        }
        return member;
    }

}