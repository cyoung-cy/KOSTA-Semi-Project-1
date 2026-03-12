package dto;

import java.sql.Date;

public class Movie {
    private int movieId;
    private String movieTitle;
    private String actor;
    private Date releaseDate;
    private String genre;
    private int screeningTime;
    private String director;
    private boolean isScreening;

    public Movie() {}

    public Movie(int movieId, String movieTitle, String actor, Date releaseDate, String genre, int screeningTime, String director, boolean isScreening) {
        this.movieId = movieId;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
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

    public boolean isScreening() {
        return isScreening;
    }

    public void setScreening(boolean screening) {
        isScreening = screening;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Movie{");
        sb.append("movieId=").append(movieId);
        sb.append(", movieTitle='").append(movieTitle).append('\'');
        sb.append(", actor='").append(actor).append('\'');
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", screeningTime=").append(screeningTime);
        sb.append(", director='").append(director).append('\'');
        sb.append(", isScreening=").append(isScreening);
        sb.append('}');
        return sb.toString();
    }
}