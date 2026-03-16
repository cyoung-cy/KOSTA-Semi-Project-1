package service;

import dao.InquiryDAO;
import dao.impl.InquiryDAOImpl;
import dto.Inquiry;
import dto.Member;
import exception.DMLException;
import exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public class InquiryService {
    InquiryDAO inquiryDao = new InquiryDAOImpl();

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 목록 조회 서비스 (관리자용)
     * */
    public List<Inquiry> selectInquiry() throws NotFoundException {
        List<Inquiry> list = inquiryDao.selectInquiry();
        if(list.size()==0)throw new NotFoundException("현재 문의가 없습니다.");
        return list;
    }

    /*
     * 20260314
     * 이동혁
     * TODO: 사용자 문의 목록 조회 서비스 (사용자용)
     * */
    public List<Inquiry> selectInquiryByMemberId(int memberId) throws NotFoundException {
        List<Inquiry> list = inquiryDao.selectInquiryByMemberId(memberId);
        if(list.size()==0)throw new NotFoundException("현재 문의가 없습니다.");
        return list;
    }

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 목록 상세 조회 서비스
     * */
    public List<Inquiry> selectInquiryDetail(int inquiryId) throws NotFoundException {
        List<Inquiry> list = inquiryDao.selectInquiryDetail(inquiryId);
        if(list.size()==0) throw new NotFoundException("현재 상품이 없습니다.");
        return list;
    }

    /*
     * 20260314
     * 이동혁
     * TODO: 사용자 문의 목록 상세 조회 서비스 (사용자용)
     * */
    public List<Inquiry> selectInquiryDetailByMember(int inquiryId, Member member) throws NotFoundException {
        List<Inquiry> list = inquiryDao.selectInquiryDetailByMemberId(inquiryId, member.getMemberId());
        if(list.size()==0) throw new NotFoundException("조회할 수 없는 문의입니다.");
        return list;
    }

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 답변 서비스
     * */
    public void insertInquiryreResponse(int inquiryId, String response) {
        int result = inquiryDao.insertInquiryreResponse(inquiryId, response);
        if(result==0)
            throw new DMLException("등록되지않았습니다.");

    }

    /*
    * 20260314
    * 이동혁
    * TODO: 사용자 문의 등록 서비스
     */
    public void insertInquiry(Member member, String content, String category, String title) throws SQLException {
        int result = inquiryDao.insertInquiry(member, content, category, title);
        if(result==0)
            throw new DMLException("문의가 등록되지않았습니다.");
    }
}
