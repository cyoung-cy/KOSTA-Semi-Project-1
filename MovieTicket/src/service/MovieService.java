package service;

import dao.MovieDAO;
import dao.impl.MovieDAOImpl;
import dto.Movie;
import exception.NotFoundException;
import exception.DMLException;
import java.util.List;

public class MovieService {
    MovieDAO movieDao = new MovieDAOImpl();

    /*
     * 0312
     * 김채영
     * TODO: 전체 영화 조회 서비스
     * */
    public List<Movie> selectAllMovies() throws NotFoundException {
        List<Movie> list = movieDao.selectAllMovies();
        if(list.isEmpty()) throw new NotFoundException("등록된 영화가 없습니다.");
        return list;
    }

    /*
     * 0315
     * 이동혁
     * TODO: 사용자 맞춤형 추천 영화 조회 서비스
     * */
    public List<Movie> selectAllMoviesByPreferredGenre(List<String> preferredGenre) throws NotFoundException {
        List<Movie> list = movieDao.selectAllMoviesByPreferredGenre(preferredGenre);
        if(list.isEmpty()) throw new NotFoundException("추천할만한 영화가 없습니다.");
        return list;
    }

    /*
     * 0312
     * 김채영
     * TODO: 영화 상세 조회 서비스
     * */
    public List<Movie> selectMovieDetail(int movieId) throws NotFoundException {
        List<Movie> movie = movieDao.selectMovieDetail(movieId);
        if(movie.isEmpty()) throw new NotFoundException("\'" + movieId + "\' 번 영화를 찾을 수 없습니다.");
        return movie;
    }

    /*
     * 0312
     * 김채영
     * TODO: 영화 삭제 서비스
     * */
    public void deleteMovie(int movieId) throws DMLException {
        int result = movieDao.deleteMovie(movieId);
        if(result == 0) throw new DMLException("영화 삭제에 실패했습니다.");
    }

    /*
     * 0313
     * 김채영
     * TODO: 영화 수정 서비스
     * */
    public void updateMovie(int movieId, String colName, String content) throws DMLException{
        String colNameEqual = switch (colName) {
            case "제목" -> "MOVIE_TITLE";
            case "배우" -> "ACTOR";
            case "개봉일" -> "RELEASE_DATE";
            case "장르" -> "GENRE";
            case "상영시간" -> "SCREENING_TIME";
            case "감독" -> "DIRECTOR";
            case "상영여부" -> "IS_SCREENING";
            default -> null;
        };
        int result = movieDao.updateMovie(movieId, colNameEqual, content);
    }

    /*
     * 0313
     * 김채영
     * TODO: 영화 등록 서비스
     * */
    public Movie insertMovie(Movie m) throws DMLException{
        int result = movieDao.insertMovie(m);
        if(result == 0) throw new DMLException("영화가 등록되지 않았습니다.");
        return m;
    }


}