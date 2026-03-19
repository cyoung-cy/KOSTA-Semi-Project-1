package dto;

import java.sql.Date;

public class Movie {
    private int movieId; //String movieId
    private String movieTitle; //String title
    private String actor;
    private String releaseDate; //String openDate
    private String genre;
    private int screeningTime; //int showTime
    private String director;
    private boolean isScreening;
    private int audiAcc;

    public Movie() {}

    public Movie(int movieId, String movieTitle, String actor, String releaseDate, String genre, int screeningTime, String director, boolean isScreening, int audiAcc) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.actor = actor;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.screeningTime = screeningTime;
        this.director = director;
        this.isScreening = isScreening;
        this.audiAcc = audiAcc;
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

    public int getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(int audiAcc) {
        this.audiAcc = audiAcc;
    }

    public boolean isScreening() {
        return isScreening;
    }
}