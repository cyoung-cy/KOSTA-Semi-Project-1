package dao.impl;

import dao.ReviewDAO;
import util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
