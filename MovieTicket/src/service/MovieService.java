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
     * 0312
     * 김채영
     * TODO: 영화 상세 조회 서비스
     * */
    public Movie selectMovieDetail(int movieId) throws NotFoundException {
        Movie movie = movieDao.selectMovieDetail(movieId);
        if(movie == null) throw new NotFoundException(movieId + "번 영화를 찾을 수 없습니다.");
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
     * 0312
     * 김채영
     * TODO: 영화 수정 서비스
     * */
}