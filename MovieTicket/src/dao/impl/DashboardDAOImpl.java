package dao.impl;

import dao.DashboardDAO;
import dto.Member;
import dto.Movie;
import dto.Reservation;
import util.DbManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAOImpl implements DashboardDAO {

    @Override
    public List<Member> user() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Member> list = new ArrayList<>();
        String sql = "select DATE(CREATE_AT) as reg_date, COUNT(*) as cnt " +
                "from MEMBER " +
                "where CREATE_AT >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
                "group by DATE(CREATE_AT) " +
                "order by reg_date desc";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Member member = new Member();
                member.setCreateAt(rs.getString("reg_date"));
                member.setMemberId(rs.getInt("cnt"));
                list.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("회원 유입 데이터 조회 실패: " + e.getMessage());
        } finally {
            DbManager.close(con, ps, rs);
        }

        return list;
    }

    @Override
    public List<Member> preferGenre() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Member> list = new ArrayList<>();

        // PREFERRED_GENRE가 '액션,애니메이션' 형태로 저장되어 있으므로
        // FIND_IN_SET으로 각 장르별 카운트를 UNION으로 집계
        String sql = "SELECT '액션' AS genre, COUNT(*) AS cnt FROM MEMBER WHERE FIND_IN_SET('액션', PREFERRED_GENRE) " +
                "UNION ALL " +
                "SELECT '애니메이션', COUNT(*) FROM MEMBER WHERE FIND_IN_SET('애니메이션', PREFERRED_GENRE) " +
                "UNION ALL " +
                "SELECT '스릴러', COUNT(*) FROM MEMBER WHERE FIND_IN_SET('스릴러', PREFERRED_GENRE) " +
                "UNION ALL " +
                "SELECT '호러', COUNT(*) FROM MEMBER WHERE FIND_IN_SET('호러', PREFERRED_GENRE) " +
                "UNION ALL " +
                "SELECT '코미디', COUNT(*) FROM MEMBER WHERE FIND_IN_SET('코미디', PREFERRED_GENRE) " +
                "UNION ALL " +
                "SELECT '로맨스', COUNT(*) FROM MEMBER WHERE FIND_IN_SET('로맨스', PREFERRED_GENRE) " +
                "UNION ALL " +
                "SELECT '드라마', COUNT(*) FROM MEMBER WHERE FIND_IN_SET('드라마', PREFERRED_GENRE) " +
                "UNION ALL " +
                "SELECT '판타지', COUNT(*) FROM MEMBER WHERE FIND_IN_SET('판타지', PREFERRED_GENRE) " +
                "ORDER BY cnt DESC";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Member member = new Member();
                member.setRole(rs.getString("genre"));    // genre 임시 저장
                member.setMemberId(rs.getInt("cnt"));     // cnt 임시 저장
                list.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }

        return list;
    }

    @Override
    public List<Movie> movieTopten() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Movie> list = new ArrayList<>();

        String sql = "select MOVIE_ID, MOVIE_TITLE, ACTOR, RELEASE_DATE, GENRE, " +
                "SCREENING_TIME, DIRECTOR, IS_SCREENING, AUDI_ACC " +
                "from MOVIE " +
                "order by AUDI_ACC DESC " +
                "limit 10";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("MOVIE_ID"),
                        rs.getString("MOVIE_TITLE"),
                        rs.getString("ACTOR"),
                        rs.getString("RELEASE_DATE"),
                        rs.getString("GENRE"),
                        rs.getInt("SCREENING_TIME"),
                        rs.getString("DIRECTOR"),
                        rs.getBoolean("IS_SCREENING"),
                        rs.getInt("AUDI_ACC")
                );
                list.add(movie);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }

        return list;
    }

    @Override
    public int getTotalAudiAcc() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int total = 0;

        String sql = "SELECT SUM(AUDI_ACC) AS total FROM MOVIE";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }

        return total;
    }

    @Override
    public List<Reservation> reservationMovie() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Reservation> list = new ArrayList<>();

        // 이번 주 월요일 ~ 일요일 기준, 요일별 예매 수 & 매출 합계
        String sql = "SELECT DAYOFWEEK(r.PAID_AT) AS day_of_week, " +
                "       COUNT(r.RESERVATION_ID) AS reservation_count, " +
                "       SUM(ri.PRICE) AS daily_sales " +
                "FROM RESERVATION r " +
                "JOIN RESERVATION_INFO ri ON r.RESERVATION_ID = ri.RESERVATION_ID " +
                "WHERE YEARWEEK(r.PAID_AT, 1) = YEARWEEK(NOW(), 1) " +
                "GROUP BY DAYOFWEEK(r.PAID_AT) " +
                "ORDER BY DAYOFWEEK(r.PAID_AT)";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setScheduleId(rs.getInt("day_of_week"));   // 요일 임시 저장
                reservation.setCount(rs.getInt("reservation_count"));  // 예매 수 임시 저장
                reservation.setTotalPrice(rs.getInt("daily_sales"));   // 매출 임시 저장
                list.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }

        return list;
    }


}
