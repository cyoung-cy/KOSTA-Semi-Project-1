package dao.impl;

import dao.MovieDAO;
import dao.ReviewDAO;
import dto.Movie;
import dto.Review;
import util.DbManager;
import vo.ReviewVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements ReviewDAO {
    @Override
    public int insertReview(int memberId, int movieId, int rating, String content) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into REVIEW (MEMBER_ID, MOVIE_ID, RATING, CONTENT, CREATED_AT)" +
                "values (?, ?, ?, ?, now())";
        int re = 0;

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, memberId);
            ps.setInt(2, movieId);
            ps.setInt(3, rating);
            ps.setString(4, content);

            re = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return re;
    }

    /**
     * 0314
     * 이동혁
     * TODO: 리뷰 내역 조회 DAO 구현
     * @param memberId
     * @return 리뷰 리스트
     * @throws SQLException
     */
    @Override
    public List<ReviewVO> selectReviewsBydMemberId(int memberId) throws SQLException {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from REVIEW join MOVIE using(MOVIE_ID) where MEMBER_ID = ? order by CREATED_AT desc";
        List<ReviewVO> list = new ArrayList<>();
        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, memberId);
            rs = ps.executeQuery();
            while(rs.next()){
                ReviewVO review = new ReviewVO(
                        rs.getInt("REVIEW_ID"),
                        rs.getInt("MEMBER_ID"),
                        rs.getInt("MOVIE_ID"),
                        rs.getInt("RATING"),
                        rs.getString("CONTENT"),
                        rs.getTimestamp("CREATED_AT"),
                        rs.getString("MOVIE_TITLE")
                );
                list.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbManager.close(con, ps, rs);
        }

        return list;
    }

    @Override
    public List<ReviewVO> selectReviewsByMovie(int movieId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from REVIEW join MOVIE using(MOVIE_ID) where MOVIE_ID = ? order by CREATED_AT desc";
        List<ReviewVO> list = new ArrayList<>();
        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, movieId);
            rs = ps.executeQuery();
            while(rs.next()){
                ReviewVO review = new ReviewVO(
                        rs.getInt("REVIEW_ID"),
                        rs.getInt("MEMBER_ID"),
                        rs.getInt("MOVIE_ID"),
                        rs.getInt("RATING"),
                        rs.getString("CONTENT"),
                        rs.getTimestamp("CREATED_AT"),
                        rs.getString("MOVIE_TITLE")
                );
                list.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbManager.close(con, ps, rs);
        }

        return list;
    }
}
