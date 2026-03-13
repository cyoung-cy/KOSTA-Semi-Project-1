package dao.impl;

import dao.InquiryDAO;
import dto.Inquiry;
import util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryDAOImpl implements InquiryDAO {


    @Override
    public List<Inquiry> selectInquiry() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select INQUIRY_ID, MEMBER_ID, TITLE, CATEGORY, PROCESSED from INQUIRY";
        Inquiry inquiry = null;
        List<Inquiry> list = new ArrayList<>();

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                inquiry = new Inquiry(rs.getInt("INQUIRY_ID"), rs.getInt("MEMBER_ID"), rs.getString("TITLE"),
                        rs.getString("CATEGORY"), rs.getBoolean("PROCESSED"));
                list.add(inquiry);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, rs);
        }
        return list;
    }

    @Override
    public List<Inquiry> selectInquiryDetail(int inquiryId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from INQUIRY where INQUIRY_ID = ?";
        Inquiry inquiry = null;
        List<Inquiry> list = new ArrayList<>();

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, inquiryId);

            rs = ps.executeQuery();

            while(rs.next()){
                inquiry = new Inquiry(rs.getInt("INQUIRY_ID"), rs.getInt("MEMBER_ID"), rs.getString("TITLE"),
                        rs.getString("CONTENT") ,rs.getString("CATEGORY"), rs.getTimestamp("CREATED_AT"),
                        rs.getBoolean("PROCESSED"), rs.getString("RESPONSE"));
                list.add(inquiry);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, rs);
        }
        return list;
    }

    @Override
    public int insertInquiryreResponse(int inquiryId, String response) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "update INQUIRY SET RESPONSE = ? WHERE INQUIRY_ID = ?";
        String sql2 = "update INQUIRY SET PROCESSED = true WHERE INQUIRY_ID = ?";
        int re = 0;
        int re2 = 0;

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, response);
            ps.setInt(2, inquiryId);

            re = ps.executeUpdate();

            if(re == 1){
                ps = con.prepareStatement(sql2);
                ps.setInt(1, inquiryId);

                re2 = ps.executeUpdate();

                if(re2==0){
                    //예외처리 필요
                    System.out.println("상태 변경이 되지 않았습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, null);
        }


        return 0;
    }


}
