package view;

import controller.InquiryController;
import controller.MemberController;
import controller.MovieController;
import dto.Inquiry;
import dto.Member;
import dto.Movie;
import exception.WrongInput;

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
            System.out.println("회원 관리 메뉴 번호를 입력하세요 : ");

            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1 :
                    //회원 삭제
                    deleteUserByName();
                    break;
                case 2 :
                    //회원 목록 조회
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
                default:
                    new WrongInput();
                    break;

            }
        }

    }

    private static void selectUserDetail() {
        System.out.print("상세 검색할 회원의 아이디를 입력하세요 : ");
        String userId = sc.nextLine();
        MemberController.selectUserDetail(userId);
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
                default:
                    new WrongInput();
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

    public static void moivieManager(Member member) {
        while(true){
            System.out.println("=============================================================");
            System.out.println("                       [영화 관리]");
            System.out.println("=============================================================");
            System.out.println("                      [1] 영화 목록 조회");
            System.out.println("                      [2] 영화 목록 상세 조회");
            System.out.println("                      [3] 새로운 영화 등록");
            System.out.println("                      [4] 영화 정보 수정");
            System.out.println("                      [5] 영화 삭제");
            System.out.println("                      [0] 이전으로 돌아가기");
            System.out.println("=============================================================");
            System.out.println("영화 관리 메뉴를 선택하세요 : ");

            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1 :
                    //영화 목록 조회
                    MovieController.selectAllMovies();
                    break;
                case 2:
                    //영화 목록 상세 조회
                    selectMovieDetail();
                    break;
                case 3 :
                    //새로운 영화 등록
                    insertMovie();
                    break;
                case 4 :
                    //영화 정보 수정
                    updateMovie();
                    break;
                case 5 :
                    //영화 삭제
                    deleteMovieById();
                    break;
                case 0 :
                    //이전으로 돌아가기
                    StartView.printAdminMenu(member);
                    break;
                default:
                    new WrongInput();
                    break;
            }
        }
    }

    private static void deleteMovieById() {
        System.out.print("삭제할 영화 ID를 입력하세요 : ");
        int movieId = Integer.parseInt(sc.nextLine());
        MovieController.deleteMovieById(movieId);
    }

    private static void updateMovie() {
        System.out.print("수정할 영화 ID를 입력하세요 : ");
        int movieId = Integer.parseInt(sc.nextLine());

        System.out.print("수정할 칼럼명을 입력하세요 : ");
        String colName = sc.nextLine();

        System.out.print("내용을 입력하세요 : ");
        String content = sc.nextLine();

        MovieController.updateMovie(movieId, colName, content);
    }

    private static void selectMovieDetail() {
        System.out.print("상세 조회할 영화 ID를 입력하세요 : ");
        int movieId = Integer.parseInt(sc.nextLine());
        MovieController.selectMovieDetail(movieId);
    }

    private static void insertMovie(){
        System.out.print("제목 : ");
        String movieTitle = sc.nextLine();

        System.out.print("배우(000, 000, 000) : ");
        String actor = sc.nextLine();

        System.out.print("개봉일(0000-00-00) : ");
        String releaseDate = sc.nextLine();

        System.out.print("장르 : ");
        String genre = sc.nextLine();
//        String inputGenre = sc.nextLine();
//        if(genre.equals("액션") | genre.equals("애니메이션") | genre.equals("스릴러") | genre.equals("호러") | genre.equals("코미디") | genre.equals("로맨스") |
//                genre.equals("다큐") | genre.equals("드라마") | genre.equals("판타지")){
//
//        }

        System.out.print("상영시간 : ");
        int screeningTime = Integer.parseInt(sc.nextLine());

        System.out.print("감독 : ");
        String director = sc.nextLine();

        System.out.print("상영여부 : ");
        String status = sc.nextLine();
        Boolean isScreening = false;
        if(status.equals("상영중")) {
            isScreening = true;
        } else if (status.equals("상영여부")) {
            isScreening = false;

        }

        Movie m = new Movie(movieTitle, actor, releaseDate, genre, screeningTime, director, isScreening);

        MovieController.insertMovie(m);
    };
}
