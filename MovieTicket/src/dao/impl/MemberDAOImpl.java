package dao.impl;

import dao.MemberDAO;
import dto.Member;
import exception.NotFoundException;
import util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dao.MemberDAO;

public class MemberDAOImpl implements MemberDAO {



    @Override
    public int register(Member member) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into MEMBER(USER_ID, PASSWORD, NAME, PHONE, ADDRESS, BIRTH_DATE, PREFERRED_GENRE, CARD_INFO, ROLE) values(?,?,?,?,?,?,?,?,'user')";
        int re = 0;
        try {
        	con = DbManager.getConnection();
        	ps = con.prepareStatement(sql);
        	
        	ps.setString(1, member.getUserId());
        	ps.setString(2, member.getPassword());
        	ps.setString(3, member.getName());
        	ps.setString(4, member.getPhone());
        	ps.setString(5, member.getAddress());
        	ps.setString(6, member.getBirthDate());
        	ps.setString(7, String.join(",", member.getPreferredGenre()));
        	ps.setString(8, member.getCardInfo());
        	
        	re = ps.executeUpdate();
        } catch (SQLException e) {
        	e.printStackTrace();
            throw new RuntimeException();
        } finally {
            DbManager.close(con, ps, null);
        }
        
        return re;
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
    public List<Member> selectUserDetail(String userId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from MEMBER where (ROLE = 'user' or ROLE is null) and USER_ID = ?";
        Member member = null;
        List<Member> list = new ArrayList<>();

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();

            while(rs.next()){
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
        String sql = "select MEMBER_ID, USER_ID, NAME from MEMBER where ROLE = 'user' or ROLE is null";
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
	public int updateMember(Member member)
			throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update MEMBER set PHONE = ?, PASSWORD = ?, CARD_INFO = ? where member_id = ?";
		int result = 0;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setString(1,member.getPhone());
			ps.setString(2,member.getPassword());
			ps.setString(3,member.getCardInfo());
			ps.setInt(4, member.getMemberId());
			
			result = ps.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			DbManager.close(con, ps, null);
		}
		return result;
	}

	/*
	 * 20260313
	 * 이동혁
	 * TODO: MemberId로 회원 탈퇴
	 */
	@Override
	public int deleteByMemberId(int memberId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "delete from MEMBER where MEMBER_ID = ?";
        int re = 0;
        try {
           con = DbManager.getConnection();
           ps = con.prepareStatement(sql);

           ps.setInt(1, memberId);

           re = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, null);
        }
        return re;
	}
	

	

}
