package dao;

import dto.Review;
import vo.ReviewVO;

import java.sql.SQLException;
import java.util.List;

public interface ReviewDAO {
	/*
     * 20260312
     * 이동혁
     * TODO: 리뷰 내역 조회 DAO 인터페이스
     */
    List<ReviewVO> selectReviewsBydMemberId(int memberId) throws SQLException;

	/*
	 * 20260312
	 * 이동혁
	 * TODO: 리뷰 등록하기 DAO 인터페이스
	 */
	int insertReview(int memberId, int movieId, int rating, String content) throws SQLException;


}
