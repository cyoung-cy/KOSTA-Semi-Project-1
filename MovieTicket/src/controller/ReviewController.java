package controller;

import dto.Member;
import service.ReviewService;
import view.EndView;
import view.FailView;

public class ReviewController {
    static ReviewService reviewService = new ReviewService();
    /*
     * 0314
     * 김채영
     * TODO: 사용자 리뷰 작성
     */
    public static void insertReview(int memberId, int movieId, int rating, String content) {
        try {
            reviewService.insertReview(memberId, movieId, rating, content);
            EndView.successMessage("리뷰가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }
}
