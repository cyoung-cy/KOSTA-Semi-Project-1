package service;

import dao.InquiryDAO;
import dao.impl.InquiryDAOImpl;
import dto.Inquiry;
import exception.DMLException;
import exception.NotFoundException;

import java.util.List;

public class InquiryService {
    InquiryDAO inquiryDao = new InquiryDAOImpl();

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 목록 조회 서비스
     * */
    public List<Inquiry> selectInquiry() throws NotFoundException {
        List<Inquiry> list = inquiryDao.selectInquiry();
        if(list.size()==0)throw new NotFoundException("현재 상품이 없습니다.");
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
     * 20260312
     * 김채영
     * TODO: 사용자 문의 답변 서비스
     * */
    public void insertInquiryreResponse(int inquiryId, String response) {
        int result = inquiryDao.insertInquiryreResponse(inquiryId, response);
        if(result==0)
            throw new DMLException("등록되지않았습니다.");

    }
}
