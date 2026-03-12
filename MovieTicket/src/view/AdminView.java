package view;

import controller.InquiryController;
import controller.MemberController;
import dto.Inquiry;
import dto.Member;

import java.util.Scanner;

public class AdminView {
    private static Scanner sc = new Scanner(System.in);

    public static void userManage(Member member) {
        while(true){
            System.out.println("=============================================================");
            System.out.println("                       [회원 관리]");
            System.out.println("=============================================================");
            System.out.println("                      [1] 회원 삭제");
            System.out.println("                      [2] 회원 목록 조회");
            System.out.println("                      [3] 회원 상세 조회");
            System.out.println("                      [0] 이전으로 돌아가기");
            System.out.println("=============================================================");

            System.out.println("회원 관리 메뉴를 선택하세요 : ");
            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1 :
                    //회원 삭제
                    deleteUserByName();
                    break;
                case 2 :
                    MemberController.selectUsers(member);
                case 3 :
                    //회원 상세 조회
                    selectUserDetail();
                    userManage(member);
                    break;
                case 0 :
                    //이전으로 돌아가기
                    StartView.printAdminMenu(member);
                    break;

            }
        }

    }

    private static void selectUserDetail() {
        System.out.print("상세 검색할 회원의 이름을 입력하세요 : ");
        String name = sc.nextLine();
        MemberController.selectUserDetail(name);
    }

    private static void deleteUserByName() {
        System.out.print("삭제할 회원의 이름을 입력해주세요 : ");
        String name = sc.nextLine();
        MemberController.deleteUserByName(name);
    }

    /*
     * 기능 : userId에 길이에 따라 중간 정렬
     * @param : userId(String), 너비(int)
     * @return : String
     * */
    public static String center(String text, int width) {
        int padding = (width - text.length()) / 2;
        String format = "%" + (padding + text.length()) + "s";
        return String.format(format, text);
    }

    public static void inquiryManage(Member member) {
        while(true){
            System.out.println("=============================================================");
            System.out.println("                       [문의 관리]");
            System.out.println("=============================================================");
            System.out.println("                      [1] 문의 목록 조회");
            System.out.println("                      [2] 문의 상세 조회");
            System.out.println("                      [3] 문의 답변");
            System.out.println("                      [0] 이전으로 돌아가기");
            System.out.println("=============================================================");

            System.out.println("문의 관리 메뉴를 선택하세요 : ");
            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1 :
                    //문의 목록 조회
                    InquiryController.selectInquiry(member);
                    break;
                case 2 :
                    //문의 상세 조회
                    selectInquiryDetail();
                    inquiryManage(member);
                    break;
                case 3 :
                    //문의 답변
                    inquiryResponse();
                    break;
                case 0 :
                    //이전으로 돌아가기
                    StartView.printAdminMenu(member);
                    break;
            }
        }
    }

    private static void selectInquiryDetail() {
        System.out.print("상세 검색할 문의의 번호를 입력하세요 : ");
        String num = sc.nextLine();
        int inquiryId = Integer.parseInt(num);
        InquiryController.selectInquiryDetail(inquiryId);
    }

    private static void inquiryResponse() {
        System.out.print("답변할 문의의 번호를 입력하세요 : ");
        String num = sc.nextLine();
        int inquiryId = Integer.parseInt(num);
        InquiryController.selectInquiryDetail(inquiryId);

        System.out.print("답변 : ");
        String response = sc.nextLine();
        InquiryController.insertInquiryreResponse(inquiryId, response);

    }
}
