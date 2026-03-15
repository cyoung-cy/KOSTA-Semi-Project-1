package controller;

import dto.Member;
import dto.Review;
import service.ReviewService;
import view.EndView;
import view.FailView;
import vo.ReviewVO;

import java.util.List;

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

    /*
    * 0315
    * 이동혁
    * TODO: 특정 영화 리뷰 리스트 조회
     */
    public static void selectReviewsByMovie(int movieId) {
        try {
            List<ReviewVO> list = reviewService.selectReviewsByMovie(movieId);
            EndView.reviewList(list);
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }

    /*
     * 20260312
     * 이동혁
     * TODO: 리뷰 내역 조회
     */
    public static void selectReviews(Member member) {
        try {
            List<ReviewVO> list = reviewService.selectReviews(member);
            EndView.reviewList(list);
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }
}
