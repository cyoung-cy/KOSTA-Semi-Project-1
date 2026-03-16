package dao.impl;

import dao.MovieDAO;
import dto.Movie;
import exception.NotFoundException;
import util.DbManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieDAOImpl implements MovieDAO {

    @Override
    public List<Movie> selectAllMovies() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Movie> list = new ArrayList<>();
        // 100개 조회 시 시인성을 위해 ID, 제목, 장르, 상영여부만 조회
        String sql = "select MOVIE_ID, MOVIE_TITLE, GENRE, SCREENING_TIME, IS_SCREENING from MOVIE order by MOVIE_ID desc";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                Movie m = new Movie();
                m.setMovieId(rs.getInt("MOVIE_ID"));
                m.setMovieTitle(rs.getString("MOVIE_TITLE"));
                m.setGenre(rs.getString("GENRE"));
                m.setScreeningTime((rs.getInt("SCREENING_TIME")));
                m.setIsScreening(rs.getBoolean("IS_SCREENING"));
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
    public List<Movie> selectAllMoviesByPreferredGenre(List<String> preferredGenre) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Movie> list = new ArrayList<>();

        // 장르 개수만큼 ? 생성하여 SQL문에 담기
        String placeholder = String.join(",", Collections.nCopies(preferredGenre.size(),"?"));
        // 100개 조회 시 시인성을 위해 ID, 제목, 장르, 상영여부만 조회
        String sql = "select MOVIE_ID, MOVIE_TITLE, GENRE, SCREENING_TIME, IS_SCREENING from MOVIE where GENRE in ("+placeholder+") order by MOVIE_ID desc, IS_SCREENING desc";

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            for(int i=0; i<preferredGenre.size(); i++){
                ps.setString(i+1, preferredGenre.get(i));
            }
            rs = ps.executeQuery();

            while(rs.next()){
                Movie m = new Movie();
                m.setMovieId(rs.getInt("MOVIE_ID"));
                m.setMovieTitle(rs.getString("MOVIE_TITLE"));
                m.setGenre(rs.getString("GENRE"));
                m.setScreeningTime((rs.getInt("SCREENING_TIME")));
                m.setIsScreening(rs.getBoolean("IS_SCREENING"));
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
    public List<Movie> selectMovieDetail(int movieId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie m = null;
        String sql = "select * from MOVIE where MOVIE_ID = ?";
        List<Movie> list = new ArrayList<>();
        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, movieId);
            rs = ps.executeQuery();
            while (rs.next()){
                m = new Movie(
                        rs.getInt("MOVIE_ID"), rs.getString("MOVIE_TITLE"),
                        rs.getString("ACTOR"), rs.getString("RELEASE_DATE"),
                        rs.getString("GENRE"), rs.getInt("SCREENING_TIME"),
                        rs.getString("DIRECTOR"), rs.getBoolean("IS_SCREENING"),
                        rs.getInt("AUDI_ACC")
                );
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
    public int insertMovie(Movie movie) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into MOVIE (MOVIE_TITLE, ACTOR, RELEASE_DATE, GENRE, SCREENING_TIME, DIRECTOR, IS_SCREENING) " +
                "values (?, ?, ?, ?, ?, ?, ?)";
        int re = 0;

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, movie.getMovieTitle());
            ps.setString(2, movie.getActor());
            ps.setDate(3, java.sql.Date.valueOf(movie.getReleaseDate()));
            ps.setString(4, movie.getGenre());
            ps.setInt(5, movie.getScreeningTime());
            ps.setString(6, movie.getDirector());
            ps.setBoolean(7, movie.getIsScreening());

            re = ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL 에러 발생!");
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, null);
        }

        return re;
    }

    @Override
    public int updateMovie(int movieId, String colNameEqual, String content) {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "update MOVIE set " + colNameEqual + " = ? WHERE MOVIE_ID = ?";
        int re = 0;

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            if (colNameEqual.equals("IS_SCREENING")) {
                if(content.equals("상영중")){
                    ps.setBoolean(1, true);
                }else if (content.equals("상영종료")){
                    ps.setBoolean(1, false);
                }
            }else{
                ps.setString(1, content);
            }
            ps.setInt(2, movieId);

            re = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DbManager.close(con, ps, null);
        }

        return re;
    }

    @Override
    public int deleteMovie(int movieId) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "delete from MOVIE where MOVIE_ID = ?";
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

    @Override
    public List<Movie> selectMovieByIsScreen() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from MOVIE where IS_SCREENING = true";
        Movie m = null;
        List<Movie> list = new ArrayList<>();

        try {
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                m = new Movie(
                        rs.getInt("MOVIE_ID"), rs.getString("MOVIE_TITLE"),
                        rs.getString("ACTOR"), rs.getString("RELEASE_DATE"),
                        rs.getString("GENRE"), rs.getInt("SCREENING_TIME"),
                        rs.getString("DIRECTOR"), rs.getBoolean("IS_SCREENING"),
                        rs.getInt("AUDI_ACC")
                );
                list.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


}