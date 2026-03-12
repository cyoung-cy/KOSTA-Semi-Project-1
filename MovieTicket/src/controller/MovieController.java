package controller;

import dto.Movie;
import service.MovieService;
import view.EndView;
import view.FailView;
import java.util.List;

public class MovieController {
    static MovieService movieService = new MovieService();
    
    /*
     * 0312
     * 김채영
     * TODO: 전체 영화 조회
     * */
    public static void selectAllMovies() {
        try {
            List<Movie> list = movieService.selectAllMovies();
            EndView.printAllMovies(list); // 간략한 목록 출력용
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 영화 상세 조회
     * */
    public static void selectMovieDetail(int movieId) {
        try {
            Movie movie = movieService.selectMovieDetail(movieId);
            EndView.printMovieDetail(movie); // 상세 정보 출력용
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 영화 삭제
     * */
    public static void deleteMovieById(int movieId) {
        try {
            movieService.deleteMovie(movieId);
            EndView.successMessage("영화가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }

    /*
     * 0312
     * 김채영
     * TODO: 전체 영화 조회
     * */
    public static void updateMovie(int updateId, int colName) {
    }
}