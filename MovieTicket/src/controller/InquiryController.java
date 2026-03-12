package controller;

import dto.Inquiry;
import dto.Member;
import service.InquiryService;
import view.AdminView;
import view.EndView;
import view.FailView;

import java.io.File;
import java.util.List;

public class InquiryController {
    static InquiryService inquiryService = new InquiryService();

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 목록 조회
     * */
    public static void selectInquiry(Member member) {
        try{
            List<Inquiry> list = inquiryService.selectInquiry();
            EndView.printInquiryShort(list);
            AdminView.inquiryManage(member);
        }catch (Exception e){
            e.printStackTrace();
            //startview 이동
        }
    }

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 목록 상세 조회
     * */
    public static void selectInquiryDetail(int inquiryId) {
        try{
            List<Inquiry> list = inquiryService.selectInquiryDetail(inquiryId);
            EndView.printInquiryDetail(list);
        }catch (Exception e){
            e.printStackTrace();
            //startview 이동
        }
    }

    /*
     * 20260312
     * 김채영
     * TODO: 사용자 문의 답변
     * */
    public static void insertInquiryreResponse(int inquiryId, String response) {
        try {
            inquiryService.insertInquiryreResponse(inquiryId, response);
        }catch (Exception e){

        }
    }
}
