package controller;

import dto.Movie;
import service.MovieService;
import view.EndView;
import view.FailView;
import view.UserView;

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
     * 0315
     * 이동혁
     * TODO: 사용자 추천 영화 조회
     * */
    public static void selectAllMoviesByPreferredGenre(List<String> preferredGenre) {
        try {
            List<Movie> list = movieService.selectAllMoviesByPreferredGenre(preferredGenre);
            UserView.recommendationMovie(list);
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
            List<Movie> movie = movieService.selectMovieDetail(movieId);
            EndView.printMovieDetail(movie); // 상세 정보 출력용
        } catch (Exception e) {
            FailView.errorMessage(movieId + "번 영화를 찾을 수 없습니다.");
        }
    }

    /*
     * 0313
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
     * 0313
     * 김채영
     * TODO: 영화 수정
     * */
    public static void updateMovie(int movieId, String colName, String content) {
        try{
            movieService.updateMovie(movieId, colName, content);
            selectMovieDetail(movieId);
            EndView.successMessage("영화가 수정되었습니다.");
        }catch (Exception e){

        }
    }

    /*
     * 0313
     * 김채영
     * TODO: 영화 등록
     * */
    public static void insertMovie(Movie m) {
        try{
            Movie movie = movieService.insertMovie(m);
            selectMovieDetail(movie.getMovieId());
            System.out.println("영화가 등록되었습니다.");
        }catch (Exception e){

        }
    }
}