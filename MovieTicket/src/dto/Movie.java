package dto;

import java.sql.Date;

public class Movie {
    private int movieId;
    private String movieTitle;
    private String actor;
    private String releaseDate;
    private String genre;
    private int screeningTime;
    private String director;
    private boolean isScreening;

    public Movie() {}

    public Movie(int movieId, String movieTitle, String actor, String releaseDate, String genre, int screeningTime, String director, boolean isScreening) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.actor = actor;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.screeningTime = screeningTime;
        this.director = director;
        this.isScreening = isScreening;
    }

    public Movie(String movieTitle, String actor, String releaseDate, String genre, int screeningTime, String director, boolean isScreening) {
        this.movieTitle = movieTitle;
        this.actor = actor;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.screeningTime = screeningTime;
        this.director = director;
        this.isScreening = isScreening;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(int screeningTime) {
        this.screeningTime = screeningTime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public boolean getIsScreening() {
        return isScreening;
    }

    public void setIsScreening(boolean isScreening) {
        this.isScreening = isScreening;
    }

    @Override
    public String toString() {
        return "\n===== 영화 정보 =====\n" +
                "영화 번호        : " + movieId + "\n" +
                "제목            : " + movieTitle + "\n" +
                "배우            : " + actor + "\n" +
                "개봉일          : " + releaseDate + "\n" +
                "장르            : " + genre + "\n" +
                "상영시간         : " + screeningTime + " 분\n" +
                "감독            : " + director + "\n" +
                "상영여부         : " + (isScreening ? "상영중" : "상영종료") + "\n" +
                "======================";
    }
}