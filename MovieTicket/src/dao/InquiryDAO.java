package dao;

import dto.Inquiry;
import dto.Member;

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
     * TODO: 사용자 문의 목록 조회 DAO 인터페이스 구현
     * */
    List<Inquiry> selectInquiry();

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 상세 목록 조회 DAO 인터페이스 구현
     * */
    List<Inquiry> selectInquiryDetail(int inquiryId);

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 답변 DAO 인터페이스 구현
     * */
    int insertInquiryreResponse(int inquiryId, String response);



}
