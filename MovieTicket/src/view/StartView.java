package view;

import controller.MemberController;
import controller.MovieController;
import controller.ReservationController;
import controller.ReviewController;
import dao.MemberDAO;
import dao.impl.MemberDAOImpl;
import dto.Genre;
import dto.Member;
import dto.Reservation;
import dto.Review;
import exception.WrongInput;
import service.ReservationService;
import session.Session;
import session.SessionSet;
import util.BadWordUtil;
import util.ValidateUtil;

import java.sql.SQLException;
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
        	SessionSet sessionSet = SessionSet.getInstance();

            // 화면 여백
            ConsoleUI.blank();

            printMenu();

            int menu = ConsoleUI.promptInt(sc, "이용하실 서비스 번호를 입력하세요");
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
                    ConsoleUI.info("프로그램을 종료합니다.");
                    System.exit(0);
                default:
                    //new WrongInput();
                    ConsoleUI.alert("잘못된 입력입니다. 메뉴 번호를 다시 입력해주세요.");
                    break;
            }
        }

    }

    /**
     * 20260317
     * TODO: 시스템 실행 시 제일 처음 보이는 StartView
     * */
    public static  void printMenu(){
        ConsoleUI.printMainBanner();

        ConsoleUI.printMenu(new String[]{
                "[1] 로그인",
                "[2] 회원가입",
                "[0] 프로그램 종료"
        }, null);
    }

    /*
     * 기능 : userId='user'일 때 사용자 View
     * @param : userId(String)
     * */
    public static void printUserMenu(Member member) {
    	SessionSet sessionSet = SessionSet.getInstance();

        while(true) {
            //ConsoleUI.blank(2);

            // 사용자 메뉴 전용 헤더 적용
            ConsoleUI.printHeader(
                    "USER LOBBY",
                    "WELCOME, " + member.getUserId(),
                    ConsoleUI.RED,
                    ConsoleUI.YELLOW
            );

            ConsoleUI.printMenu(new String[]{
                    "[1] 영화 예매",
                    "[2] 영화 추천",
                    "[3] 영화 리뷰 작성",
                    "[4] 마이페이지",
                    "[5] 문의하기",
                    "[6] 로그아웃",
                    "[7] 회원탈퇴",
                    "[0] 종료"
            }, null);

            int menu = ConsoleUI.promptInt(sc, "회원 메뉴 번호를 입력하세요");
            switch(menu) {
                case 1 :
                    //영화 예매
                    // 개발완료 후 병합예정
                    ConsoleUI.info("[개발 미완료] 예매 기능 UI는 추후 구현 예정");
                    break;
                case 2 :
                    //영화 추천
                    ConsoleUI.info("영화 목록을 불러옵니다.");
                    MovieController.selectAllMoviesByPreferredGenre(member.getPreferredGenre());
                    break;
                case 3 :
                    //영화 리뷰 작성
                    insertReview(member.getMemberId());
                    //printUserMenu(member);
                    break;
                case 4 :
                    //마이페이지
                	UserView.myPage(member);
                    break;
                case 5 :
                    //문의하기
                    UserView.inquiry(member);
                    break;
                case 6 :
                    //로그아웃
                    StartView.logout(member.getMemberId(), member.getUserId());
                    ConsoleUI.success("로그아웃 되었습니다.");
                    return;
                case 7 :
                    //회원탈퇴
                	StartView.withDrawal(member);
                	break;
                case 0 :
                    //종료
                    ConsoleUI.info("프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    ConsoleUI.alert("잘못된 입력입니다. 메뉴 번호를 다시 입력해주세요.");
                    break;
            }
        }
    }

    /*
     * 기능 : userId='admin' or 'subadmin' 일 때 관리자 View
     * @param : userId(String)
     * */
    public static void printAdminMenu(Member member) {
    	SessionSet sessionSet = SessionSet.getInstance();

        while(true){
            //ConsoleUI.blank(2);

            ConsoleUI.printHeader(
                    "ADMIN CONTROL ROOM",
                    "WELCOME, " + member.getName() + " MANAGER",
                    ConsoleUI.GREEN,
                    ConsoleUI.GREEN
            );

            ConsoleUI.printMenu(new String[]{
                    "[1] 회원 관리",
                    "[2] 영화 관리",
                    "[3] 문의 관리",
                    "[4] 통계보기",
                    "[5] 로그아웃",
                    "[0] 종료"
            }, ConsoleUI.GREEN);

            int menu = ConsoleUI.promptInt(sc, "관리 메뉴 번호를 입력하세요");
            switch(menu) {
                case 1 :
                    //회원 관리
                    ConsoleUI.info("전체 회원 정보를 불러옵니다...");
                    MemberController.selectUsers(member);
                    break;
                case 2 :ConsoleUI.info("문의 관리 메뉴로 이동합니다...");
                    //영화 관리
                    ConsoleUI.info("영화 관리 메뉴로 이동합니다...");
                    AdminView.movieManager(member);
                    break;
                case 3 :
                    //문의 관리
                    ConsoleUI.info("문의 관리 메뉴로 이동합니다...");
                    AdminView.inquiryManage(member);
                    break;
                case 4:
                    //통계보기
                    ConsoleUI.info("통계 화면을 불러옵니다...");
                    AdminView.statistics(member);
                    break;
                case 5 :
                    //로그아웃
                    ConsoleUI.info("로그아웃을 진행합니다...");
                    StartView.logout(member.getMemberId(), member.getUserId());
                    ConsoleUI.success("로그아웃 되었습니다.");
                    return;
                case 0 :
                    //종료
                    ConsoleUI.info("프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                default:
                    ConsoleUI.alert("잘못된 입력입니다. 메뉴 번호를 다시 입력해주세요.");
                    break;
            }

        }

    }

    /*
     * 기능 : 로그인
     * */
    public static void login() {
        ConsoleUI.blank(1);

        ConsoleUI.printHeader("LOGIN", "MOVIE TICKET ACCESS", ConsoleUI.RED, ConsoleUI.YELLOW);

        String userId = ConsoleUI.prompt(sc, "아이디");
        String password = ConsoleUI.prompt(sc, "비밀번호");

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
     * 2026-03-13
     * 이동혁
     * 기능 : 회원탈퇴
     */
    public static void withDrawal(Member member) {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("MEMBERSHIP CANCEL", "ACCOUNT WITHDRAWAL", ConsoleUI.RED, ConsoleUI.YELLOW);

        String select = ConsoleUI.prompt(sc, "정말 탈퇴하시겠습니까? (Y/N)").trim().toUpperCase();

        if(select.equals("Y")) {
            MemberController.deleteUserByMemberId(member);
        } else if(select.equals("N")) {
            ConsoleUI.info("회원탈퇴가 취소되었습니다.");
        } else {
            ConsoleUI.alert("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
        }
    }

    /*
     * 기능 : 회원가입
     * */
    public static void signUp() {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("SIGN UP", "NEW MEMBER REGISTRATION", ConsoleUI.RED, ConsoleUI.YELLOW);

        String userId = ConsoleUI.prompt(sc, "아이디");
        String password = null;
        String phone = null;
        String birth = null;
        String cardInfo = null;

        while(true) {
            password = ConsoleUI.prompt(sc, "비밀번호 (8자 이상)");
            if(!ValidateUtil.isValidPassword(password)) {
                ConsoleUI.alert("비밀번호는 8자 이상 입력하세요.");
                continue;
            }
            break;
        }

        String name = ConsoleUI.prompt(sc, "이름");

        while(true) {
            phone = ConsoleUI.prompt(sc, "전화번호 (ex: 010-xxxx-xxxx)");
            if(!ValidateUtil.isValidPhone(phone)) {
                ConsoleUI.alert("전화번호 양식이 올바르지 않습니다. 예시) 010-1234-5678");
                continue;
            }
            break;
        }

        String address = ConsoleUI.prompt(sc, "주소 (ex: 서울시 강남구)");

        while(true) {
            birth = ConsoleUI.prompt(sc, "생일 (ex: 2000-01-01)");
            if(!ValidateUtil.isValidBirth(birth)) {
                ConsoleUI.alert("생일 양식이 올바르지 않습니다. 예시) 2000-01-01");
                continue;
            }
            break;
        }


        /*
         * 20260313
         * 이동혁
         * 선호 장르 한글 입력으로 수정
         * 20260315 추가: ENUM Genre의 valueOf() 메서드를 활용하여 입력된 장르가 유효한지 검증
         */
        List<String> preferredGenre = null;
        while(true) {
            System.out.print("선호 장르('액션', '애니메이션', '스릴러', '호러', '코미디', '로맨스', '다큐', '드라마', '판타지' 중에 최대 3개 콤마로 구분해서 입력)\n : ");
            try {
                preferredGenre = Arrays.stream(sc.nextLine().split(","))
                        .map(String::trim)
                        .map(Genre::validate)
                        .collect(Collectors.toList());
                break;
            } catch (IllegalArgumentException e) {
                ConsoleUI.alert(e.getMessage());
            }
        }

        while(true) {
            cardInfo = ConsoleUI.prompt(sc, "결제 정보 (ex: 1111-1111-1111-1111)");
            if(!ValidateUtil.isValidCardInfo(cardInfo)) {
                ConsoleUI.alert("카드 정보 양식이 올바르지 않습니다. 예시) 1234-5678-9012-3456");
                continue;
            }
            break;
        }

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

        ConsoleUI.success("회원가입 요청이 완료되었습니다.");
    }

    public static void insertReview(int memberId) {
        ReservationService reservationService = new ReservationService();
        MemberDAO memberDAO = new MemberDAOImpl();

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("WRITE REVIEW", "TICKET HOLDER ONLY", ConsoleUI.RED, ConsoleUI.YELLOW);

        ReservationController.selectReservationsByMemberId(memberId);
        List<Member> m = memberDAO.selectUsers();

        String name = null;

        for(Member mem : m){
            if (mem.getMemberId() == memberId) {
                name = mem.getName();
                break;
            }
        }

        boolean sta = true;
        int movieId = 0;
        while (sta){
            movieId = ConsoleUI.promptInt(sc, "리뷰를 작성할 영화 ID를 선택하세요");

            try {
                List<Reservation> list = reservationService.selectReservationsByMemberId(memberId);

                boolean found = false;
                for (Reservation r : list) {
                    if (r.getMovieId() == movieId) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    sta = false;
                } else {
                    ConsoleUI.alert("'" + name + "' 님이 예매한 영화가 아닙니다.");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        sta = true;
        int rating = 0;
        while(sta){
            rating = ConsoleUI.promptInt(sc, "평점을 입력하세요 (1~5)");
            if(rating >= 1 && rating <= 5){
                sta = false;
            } else {
                ConsoleUI.alert("평점은 1~5 사이 정수를 입력해주세요.");
            }
        }

        sta = true;
        String content = null;
        while(sta){
            content = ConsoleUI.prompt(sc, "리뷰를 작성하세요");

            if (BadWordUtil.containsBadWord(content)) {
                ConsoleUI.alert("욕설이 포함되어 있어 등록 불가합니다.");
            } else {
                sta = false;
            }
        }

        //욕설을 *로 필터링
        //String filtered = BadWordUtil.filter(content);
        Review re = new Review(memberId, movieId, rating, content);
        ReviewController.insertReview(re.getMemberId(), re.getMovieId(), re.getRating(), re.getContent());

        ConsoleUI.success("리뷰가 등록되었습니다.");
    }

    public static void main(String[] args) {
        new StartView();
    }
}



