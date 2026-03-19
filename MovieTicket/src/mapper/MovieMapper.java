package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Movie;

public class MovieMapper implements RowMapper<Movie> {
    
    private static final MovieMapper instance = new MovieMapper();
    
    private MovieMapper() {}
    
    public static MovieMapper getInstance() {
        return instance;
    }

    @Override
    public Movie mapRow(ResultSet rs) throws SQLException {
    	
        Movie movie = new Movie();
        
        movie.setMovieId(rs.getInt("MOVIE_ID"));
        movie.setMovieTitle(rs.getString("MOVIE_TITLE"));
        movie.setActor(rs.getString("ACTOR"));
        movie.setReleaseDate(rs.getString("RELEASE_DATE"));
        movie.setGenre(rs.getString("GENRE"));
        movie.setScreeningTime(rs.getInt("SCREENING_TIME"));
        movie.setDirector(rs.getString("DIRECTOR"));
        movie.setIsScreening(rs.getBoolean("IS_SCREENING"));
        movie.setAudiAcc(rs.getInt("AUDI_ACC"));
        
        return movie;
    }
}