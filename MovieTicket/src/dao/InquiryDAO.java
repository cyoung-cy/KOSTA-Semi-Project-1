package dao;

import dto.Inquiry;
import dto.Member;

import java.sql.SQLException;
import java.util.List;

public interface InquiryDAO {
    /*
     * 20260312
     * 이동혁
     * TODO: 내가 등록한 문의 조회 (답변이 있으면 답변 조회 까지)
     */
    //List<Inquiry> selectInquirysByMemberId(int memberId) throws SQLException;


    /*
     * 20260312
     * 이동혁
     * TODO: 문의 등록
     */
    //int insertInquiry() throws SQLException;

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 목록 조회 DAO 인터페이스 구현 (관리자용)
     * */
    List<Inquiry> selectInquiry();

    /*
     * 20260314
     * 이동혁
     * TODO: 사용자 문의 목록 조회 DAO 인터페이스 구현 (사용자용)
     */
    List<Inquiry> selectInquiryByMemberId(int memberId);

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 상세 목록 조회 DAO 인터페이스 구현
     * */
    List<Inquiry> selectInquiryDetail(int inquiryId);

    /*
    * 20260314
    * 이동혁
    * TODO: 사용자 문의 상세 조회 DAO 인터페이스 (사용자용)
     */
    List<Inquiry> selectInquiryDetailByMemberId(int inquiryId, int memberId);

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 답변 DAO 인터페이스 구현
     * */
    int insertInquiryreResponse(int inquiryId, String response);

    /*
     * 20260314
     * 이동혁
     * TODO: 사용자 문의 등록 DAO 인터페이스
     */
    int insertInquiry(Member member, String content, String category, String title) throws SQLException;


}
