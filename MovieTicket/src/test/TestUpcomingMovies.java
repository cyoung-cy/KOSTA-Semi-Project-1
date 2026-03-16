package test;

import api.UpcomingMovieAPI;
import api.UpcomingMovieDetailAPI;
import dto.MovieAPI;

import java.util.List;

public class TestUpcomingMovies {
    public static void main(String[] args) throws Exception {
    	System.out.println("=======================================================");
        System.out.println("		개봉예정작 리스트 조회");
        System.out.println("=======================================================");
        System.out.println("MOVIE_ID | 제목    | 장르     | 개봉예정일");
        System.out.println("-------------------------------------------------------");
        
        List<MovieAPI> movies = UpcomingMovieAPI.getUpcomingMovies(2026, 2026);
        for (MovieAPI movie : movies) {
            System.out.println(movie.getMovieId() + " | " + movie.getTitle() + " | " + movie.getGenre() + " | " + movie.getOpenDate());
        }

        UpcomingMovieDetailAPI.showUpcomingMovieDetail(movies);
        
    }
}