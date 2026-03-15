package dao;

import dto.Movie;
import java.util.List;

public interface MovieDAO {
    /*
     * 0312
     * 김채영
     * TODO: 전체 영화 조회
     * */
    List<Movie> selectAllMovies();

    /*
     * 0315
     * 이동혁
     * TODO: 사용자 맞춤형 추천 영화 조회
     * */
    List<Movie> selectAllMoviesByPreferredGenre(List<String> preferredGenre);

    /*
     * 0312
     * 김채영
     * TODO: 영화 상세 조회
     * */
    List<Movie> selectMovieDetail(int movieId);

    /*
     * 0312
     * 김채영
     * TODO: 영화 등록
     * */
    int insertMovie(Movie movie);

    /*
     * 0312
     * 김채영
     * TODO: 영화 수정
     * */
    int updateMovie(int movieId, String colName, String content);

    /*
     * 0312
     * 김채영
     * TODO: 영화 삭제
     * */
    int deleteMovie(int movieId);



}