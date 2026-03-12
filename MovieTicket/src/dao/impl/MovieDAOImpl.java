package dao.impl;

import dao.MovieDAO;
import dto.Movie;
import util.DbManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {

    @Override
    public List<Movie> selectAllMovies() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Movie> list = new ArrayList<>();
        // 100개 조회 시 시인성을 위해 ID, 제목, 장르, 상영여부만 조회
        String sql = "SELECT MOVIE_ID, MOVIE_TITLE, GENRE, IS_SCREENING FROM MOVIE";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Movie m = new Movie();
                m.setMovieId(rs.getInt("MOVIE_ID"));
                m.setMovieTitle(rs.getString("MOVIE_TITLE"));
                m.setGenre(rs.getString("GENRE"));
                m.setScreeningTime(rs.getInt("IS_SCREENING"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }
        return list;
    }

    @Override
    public Movie selectMovieDetail(int movieId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        String sql = "SELECT * FROM MOVIE WHERE MOVIE_ID = ?";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, movieId);
            rs = ps.executeQuery();
            if(rs.next()){
                m = new Movie(
                        rs.getInt("MOVIE_ID"), rs.getString("MOVIE_TITLE"),
                        rs.getString("ACTOR"), rs.getDate("RELEASE_DATE"),
                        rs.getString("GENRE"), rs.getInt("SCREENING_TIME"),
                        rs.getString("DIRECTOR"), rs.getBoolean("IS_SCREENING")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, rs);
        }
        return m;
    }

    @Override
    public int insertMovie(Movie movie) {
        return 0;
    }

    @Override
    public int updateMovie(Movie movie) {
        return 0;
    }

    @Override
    public int deleteMovie(int movieId) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM MOVIE WHERE MOVIE_ID = ?";
        int result = 0;
        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, movieId);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, null);
        }
        return result;
    }

    // insertMovie, updateMovie 생략 (패턴 동일)
}