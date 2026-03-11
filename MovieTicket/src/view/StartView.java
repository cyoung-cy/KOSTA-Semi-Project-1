package view;

import session.Session;
import session.SessionSet;

import java.util.Scanner;

public class StartView {
    private static Scanner sc = new Scanner(System.in);

    public static void menu() {
        while(true) {
            SessionSet ss = SessionSet.getInstance();
            System.out.println("ss.getSet() = "+ss.getSet());

            StartView.printMenu();

            int menu = Integer.parseInt(sc.nextLine());
            switch(menu) {
                case 1 :
                    StartView.login();// 로그인
                    break;
                case 2 :
                    // 회원가입
                    break;
                case 0 :
                    System.exit(0);
            }
        }

    }


    public static void printMenu() {
        System.out.println("=============================================================");
        System.out.println("                 Hello! Welcome MOVIE TICKET                 ");
        System.out.println("=============================================================");
        System.out.println("                      [1] 로그인");
        System.out.println("                      [2] 회원가입");
        System.out.println("                      [0] 프로그램 종료");
        System.out.println("=============================================================");
        System.out.println("이용하실 서비스를 선택하세요: ");

    }


    public static void printUserMenu(String userId) {
        while(true) {
            SessionSet ss = SessionSet.getInstance();

            System.out.println("=============================================================");
            String text = "Hello! " + userId + " Welcome MOVIE TICKET";
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

            int menu =Integer.parseInt( sc.nextLine());
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
                case 7 :
                    //회원탈퇴
                case 0 :
                    //종료
                    System.exit(0);
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

    public static void printAdminMenu(String userId) {
        System.out.println("=============================================================");
        String text = "Hello! " + userId + " Welocme MOVIE TICKET";
        System.out.println(center(text, 60));
        System.out.println("=============================================================");

    }

    /**
     * 로그인 메뉴
     * */
    public static void login() {
        System.out.print("아이디 : ");
        String userId = sc.nextLine();

        System.out.print("비밀번호 : ");
        String userPwd = sc.nextLine();

    }

    /**
     * 로그아웃
     * */
    public static void logout(String userId) {
        Session session = new Session(userId);
        SessionSet ss = SessionSet.getInstance();
        ss.remove(session);
    }
}



