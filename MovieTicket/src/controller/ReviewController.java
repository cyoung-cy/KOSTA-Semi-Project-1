package controller;

import dao.MovieDAO;
import dao.impl.MovieDAOImpl;
import dto.Member;
import dto.Movie;
import dto.Reservation;
import exception.WrongInput;
import service.ReservationService;
import service.ReviewService;
import util.PagingUtil;
import view.EndView;
import view.FailView;

import java.sql.SQLException;
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
            if(content == null || content.length() == 0) {
                FailView.errorMessage("리뷰가 등록되지 않았습니다.");
            }
            reviewService.insertReview(memberId, movieId, rating, content);
            EndView.successMessage("리뷰가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }
}
