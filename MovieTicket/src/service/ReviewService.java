package service;

import dao.ReviewDAO;
import dao.impl.ReviewDAOImpl;
import dto.Member;
import exception.DMLException;

import java.sql.SQLException;

public class ReviewService {
    ReviewDAO reviewDao = new ReviewDAOImpl();
    /*
     * 0314
     * 김채영
     * TODO: 사용자 리뷰 작성 서비스
     */
    public void insertReview(int memberId, int movieId, int rating, String content) throws SQLException {
        int result = reviewDao.insertReview(memberId, movieId, rating, content);
        if(result==0)
            throw new DMLException("리뷰가 등록되지않았습니다.");

    }
}
