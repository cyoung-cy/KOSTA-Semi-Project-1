package dto;

import util.MovieUtils;

import java.time.LocalDate;

public class MovieAPI {

    private String movieId; // movieCd
    private String title; // movieNm
    private String openDate; // yyyyMMdd
    private String genre; // API상의 원본 장르명
    private Integer audiAcc; // 누적관객수

    private String director = ""; // API상의 감독명 중 첫 번째 값
    private Integer showTime = null; // 상영시간(러닝타임)
    private String actor1 = "";
    private String actor2 = "";
    private String actor3 = "";

    public MovieAPI() {
    }

    public MovieAPI(String movieId, String title, String openDate, String genre, Integer audiAcc) {
        this.movieId = movieId;
        this.title = title;
        this.openDate = openDate;
        this.genre = genre;
        this.audiAcc = audiAcc;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getAudiAcc() {
        return audiAcc;
    }

    public void setAudiAcc(Integer audiAcc) {
        this.audiAcc = audiAcc;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getShowTime() {
        return showTime;
    }

    public void setShowTime(Integer showTime) {
        this.showTime = showTime;
    }

    public String getActor1() {
        return actor1;
    }

    public void setActor1(String actor1) {
        this.actor1 = actor1;
    }

    public String getActor2() {
        return actor2;
    }

    public void setActor2(String actor2) {
        this.actor2 = actor2;
    }

    public String getActor3() {
        return actor3;
    }

    public void setActor3(String actor3) {
        this.actor3 = actor3;
    }

    public LocalDate getReleaseDate() {
        if (openDate == null || !openDate.matches("\\d{8}")) {
            return null;
        }

        try {
            return LocalDate.of(
                    Integer.parseInt(openDate.substring(0, 4)),
                    Integer.parseInt(openDate.substring(4, 6)),
                    Integer.parseInt(openDate.substring(6, 8))
            );
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isValidMovie() {
        return movieId != null && !movieId.isBlank()
                && title != null && !title.isBlank()
                && MovieUtils.isValidDate(openDate)
                && audiAcc != null;
    }

    public boolean isValidDetail() {
        return director != null && !director.isBlank()
                && showTime != null && showTime > 0
                && actor1 != null && !actor1.isBlank()
                && actor2 != null && !actor2.isBlank()
                && actor3 != null && !actor3.isBlank();
    }


    /**
     * 20260316
     * 이예진
     * TODO: actor1 ~ actor3 배우 정보를 ", "로 연결한 문자열 생성
     * */
    public String getActorText() {
        StringBuilder sb = new StringBuilder();

        if (actor1 != null && !actor1.isBlank()) {
            sb.append(actor1);
        }

        if (actor2 != null && !actor2.isBlank()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(actor2);
        }

        if (actor3 != null && !actor3.isBlank()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(actor3);
        }

        return sb.toString();
    }
}