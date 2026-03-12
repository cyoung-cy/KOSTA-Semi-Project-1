package view;

import controller.MemberController;
import dto.Member;
import session.Session;
import session.SessionSet;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
 * 날짜 : 0311
 * 이름 : 김채영
 * 수정 사항 : StartView(사용자, 관리자)
 * */

public class StartView {
    private static Scanner sc = new Scanner(System.in);

    public StartView () {
        menu();
    }
    public static void menu() {
        while(true) {
            StartView.printMenu();

            int menu = Integer.parseInt(sc.nextLine());
            switch(menu) {
                case 1 :
                    // 로그인
                    StartView.login();
                    break;
                case 2 :
                    // 회원가입
                    StartView.signUp();
                    break;
                case 0 :
                    System.exit(0);
            }
        }

    }

    /*
     * 기능 : 시스템 실행시 제일 처음 보이는 StartView
     * */
    public static void printMenu() {
        System.out.println("=============================================================");
        System.out.println("                Hello! Welcome to MOVIE TICKET                ");
        System.out.println("=============================================================");
        System.out.println("                      [1] 로그인");
        System.out.println("                      [2] 회원가입");
        System.out.println("                      [0] 프로그램 종료");
        System.out.println("=============================================================");
        System.out.println("이용하실 서비스를 선택하세요: ");

    }

    /*
     * 기능 : userId='user'일 때 사용자 View
     * @param : userId(String)
     * */
    public static void printUserMenu(Member member) {
        while(true) {
            System.out.println("=============================================================");
            String text = "Hello! " + member.getUserId() + " Welcome to MOVIE TICKET";
            System.out.println(center(text, 60));
            System.out.println("=============================================================");
            System.out.println("                    [1] 영화 예매");
            System.out.println("                    [2] 영화 추천");
            System.out.println("                    [3] 영화 리뷰 작성");
            System.out.println("                    [4] 마이페이지");
            System.out.println("                    [5] 문의하기");
            System.out.println("                    [6] 로그아웃");
            System.out.println("                    [7] 회원탈퇴");
            System.out.println("                    [0] 종료");
            System.out.println("=============================================================");

            int menu =Integer.parseInt(sc.nextLine());
            switch(menu) {
                case 1 :
                    //영화 예매
                case 2 :
                    //영화 추천
                case 3 :
                    //영화 리뷰 작성
                case 4 :
                    //마이페이지
                case 5 :
                    //문의하기
                case 6 :
                    //로그아웃
                    StartView.logout(member.getUserId());
                    break;
                case 7 :
                    //회원탈퇴
                case 0 :
                    //종료
                    System.exit(0);
                    break;
            }
        }

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

    /*
     * 기능 : userId='admin' or 'subadmin' 일 때 관리자 View
     * @param : userId(String)
     * */
    public static void printAdminMenu(Member member) {
        while(true){
            System.out.println("=============================================================");
            String text = "Hello! " + member.getName() + " Welocme to MOVIE TICKET Admin Page";
            System.out.println(center(text, 60));
            System.out.println("=============================================================");
            System.out.println("                      [1] 회원 관리");
            System.out.println("                      [2] 영화 관리");
            System.out.println("                      [3] 문의 관리");
            System.out.println("                      [0] 프로그램 종료");
            System.out.println("=============================================================");

            System.out.println("관리 메뉴를 선택하세요 : ");
            int menu =Integer.parseInt(sc.nextLine());
            switch(menu) {
                case 1 :
                    //회원 관리
                    MemberController.selectUsers(member);
                    break;
                case 2 :
                    //영화 관리
                case 3 :
                    //문의 관리
                    AdminView.inquiryManage(member);
                case 0 :
                    //종료
                    System.exit(0);
                    break;
            }

        }

    }

    /*
     * 기능 : 로그인
     * */
    public static void login() {
        System.out.print("아이디 : ");
        String userId = sc.nextLine();

        System.out.print("비밀번호 : ");
        String password = sc.nextLine();

        MemberController.login(userId, password);
    }


    /*
     * 기능 : 로그아웃
     * */
    public static void logout(String userId) {
        Session session = new Session(userId);
        SessionSet ss = SessionSet.getInstance();
        ss.remove(session);
    }

    /*
     * 기능 : 회원가입
     * */
    public static void signUp() {
        System.out.print("아이디 : ");
        String userId = sc.nextLine();

        System.out.print("비밀번호 : ");
        String password = sc.nextLine();

        System.out.print("이름 : ");
        String name = sc.nextLine();

        System.out.print("전화번호 : ");
        String phone = sc.nextLine();

        System.out.print("주소 : ");
        String address = sc.nextLine();

        System.out.print("생일 : ");
        String birth = sc.nextLine();

        System.out.print("선호 장르 : ");
        List<String> preferredGenre = Arrays.stream(sc.nextLine().split(","))
        		.map(String::trim).filter(s -> !s.isEmpty())
        		.collect(Collectors.toList());

        System.out.print("결제 정보 : ");
        String cardInfo = sc.nextLine();

        MemberController.register(
    			userId,
    			password,
    			name,
    			phone,
    			address,
    			birth,
    			preferredGenre,
    			cardInfo
    	);
    }
}



