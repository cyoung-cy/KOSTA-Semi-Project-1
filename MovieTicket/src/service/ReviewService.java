package service;

import dao.ReviewDAO;
import dao.impl.ReviewDAOImpl;
import dto.Member;
import exception.DMLException;
import exception.NotFoundException;
import vo.ReviewVO;

import java.sql.SQLException;
import java.util.List;

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

    /*
    * 0315
    * 이동혁
    * TODO: 특정 영화 리뷰 리스트 조회 서비스
     */
    public List<ReviewVO> selectReviewsByMovie(int movieId) throws SQLException, NotFoundException {
        List<ReviewVO> list = reviewDao.selectReviewsByMovie(movieId);
        if(list == null) {
            throw new NotFoundException("리뷰 내역이 존재하지 않습니다.");
        }
        return list;
    }

    /*
     *  20260314
     * 이동혁
     * TODO: 사용자 리뷰 조회 서비스
     */
    public List<ReviewVO> selectReviews(Member member) throws SQLException, NotFoundException {
        List<ReviewVO> list = reviewDao.selectReviewsBydMemberId(member.getMemberId());
        if(list == null) {
            throw new NotFoundException("리뷰 내역이 존재하지 않습니다.");
        }

        return list;
    }
}
