package vo;

import java.sql.Timestamp;

public class ReviewVO {

    private int reviewId;
    private int memberId;
    private int movieId;
    private int rating;
    private String content;
    private Timestamp createdAt;
    private String movieTitle;

    public ReviewVO() {

    }

    public ReviewVO(int memberId, int movieId, int rating, String content) {
        this.memberId = memberId;
        this.movieId = movieId;
        this.rating = rating;
        this.content = content;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public ReviewVO(int reviewId, int memberId, int movieId, int rating, String content, Timestamp createdAt, String movieTitle) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.movieId = movieId;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.movieTitle = movieTitle;
    }


    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

}