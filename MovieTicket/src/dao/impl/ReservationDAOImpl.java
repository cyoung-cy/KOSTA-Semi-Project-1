package dao.impl;

import dao.ReservationDAO;
import dto.Movie;
import dto.Reservation;
import util.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public List<Reservation> selectReservationsByMemberId(int memberId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Reservation> list = new ArrayList<>();
        // 100개 조회 시 시인성을 위해 ID, 제목, 장르, 상영여부만 조회
        String sql = "select * from RESERVATION where MEMBER_ID=?";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1,memberId);

            rs = ps.executeQuery();

            while(rs.next()){
                Reservation r = new Reservation();
                r.setMemberId(rs.getInt("MEMBER_ID"));
                r.setReservationId(rs.getInt("RESERVATION_ID"));
                r.setMovieId(rs.getInt("MOVIE_ID"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }
        return list;
    }
}
