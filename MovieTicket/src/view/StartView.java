package view;

import controller.MemberController;
import controller.ReservationController;
import controller.ReviewController;
import dto.Member;
import dto.Reservation;
import dto.Review;
import exception.WrongInput;
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
        	/*
        	 * 세션Set에 담긴 세션 정보들 조회
        	 */
        	SessionSet sessionSet = SessionSet.getInstance();
        	System.out.println("sessionSet() = " + sessionSet.getSet());
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
                default:
                    new WrongInput();
                    break;
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
        System.out.println("이용하실 서비스 번호를 입력하세요: ");

    }

    /*
     * 기능 : userId='user'일 때 사용자 View
     * @param : userId(String)
     * */
    public static void printUserMenu(Member member) {
    	/*
    	 * 세션Set에 담긴 세션 정보들 조회
    	 */
    	SessionSet sessionSet = SessionSet.getInstance();
    	System.out.println("sessionSet() = " + sessionSet.getSet());

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

            System.out.println("회원 메뉴 번호를 입력하세요 : ");
            int menu =Integer.parseInt(sc.nextLine());
            switch(menu) {
                case 1 :
                    //영화 예매
                case 2 :
                    //영화 추천
                case 3 :
                    //영화 리뷰 작성
                    insertReview(member.getMemberId());
                    printUserMenu(member);
                case 4 :
                    //마이페이지
                case 5 :
                    //문의하기
                case 6 :
                    //로그아웃
                    StartView.logout(member.getMemberId(), member.getUserId());
                    return;
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
    	/*
    	 * 세션Set에 담긴 세션 정보들 조회
    	 */
    	SessionSet sessionSet = SessionSet.getInstance();
    	System.out.println("sessionSet() = " + sessionSet.getSet());

        while(true){
            System.out.println("=============================================================");
            String text = "Hello! " + member.getName() + " Welocme to MOVIE TICKET Admin Page";
            System.out.println(center(text, 60));
            System.out.println("=============================================================");
            System.out.println("                      [1] 회원 관리");
            System.out.println("                      [2] 영화 관리");
            System.out.println("                      [3] 문의 관리");
            System.out.println("                      [4] 로그아웃");
            System.out.println("                      [0] 종료");
            System.out.println("=============================================================");

            System.out.println("관리 메뉴 번호를 입력하세요 : ");
            int menu =Integer.parseInt(sc.nextLine());
            switch(menu) {
                case 1 :
                    //회원 관리
                    MemberController.selectUsers(member);
                    break;
                case 2 :
                    //영화 관리
                    AdminView.moivieManager(member);
                case 3 :
                    //문의 관리
                    AdminView.inquiryManage(member);
                case 4 :
                    //로그아웃
                    StartView.logout(member.getMemberId(), member.getUserId());
                    return;
                case 0 :
                    //종료
                    System.exit(0);
                    break;
                default:
                    new WrongInput();
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
    public static void logout(int memberId, String userId) {
        Session session = new Session(memberId, userId);
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

        System.out.print("전화번호 (ex: 010-xxxx-xxxx) : ");
        String phone = sc.nextLine();

        System.out.print("주소 (ex:서울시 강남구) : ");
        String address = sc.nextLine();

        System.out.print("생일 (ex:2000-01-01) : ");
        String birth = sc.nextLine();

        System.out.print("선호 장르('ACTION', 'ANIMATION', 'THRILLER', 'HORROR', 'COMEDY', 'ROMANCE', 'DOCUMENTARY', 'DRAMA', 'SF' 중에 최대 3개 콤마로 구분해서 입력)\n : ");
        List<String> preferredGenre = Arrays.stream(sc.nextLine().split(","))
        		.map(String::trim).filter(s -> !s.isEmpty())
        		.collect(Collectors.toList());

        System.out.print("결제 정보(ex:1111-1111-1111-1111) : ");
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

    public static void insertReview(int memberId) {

        ReservationController.selectReservationsByMemberId(memberId);

        System.out.print("리뷰를 작성할 영화를 선택하세요 : ");
        int movieId = Integer.parseInt(sc.nextLine());

        System.out.print("평점을 입력하세요(1~5) : ");
        int rating = Integer.parseInt(sc.nextLine());

        System.out.println("리뷰를 작성하세요 : ");
        String content = sc.nextLine();

        Review re = new Review(memberId, movieId, rating, content);
        ReviewController.insertReview(re.getMemberId(), re.getMovieId(), re.getRating(), re.getContent());
    }
    
    public static void main(String[] args) {
    	new StartView();
    }
}



