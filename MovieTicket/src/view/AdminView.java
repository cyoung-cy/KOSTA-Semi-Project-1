package view;

import api.UpcomingMovieAPI;
import controller.DashboardController;
import controller.InquiryController;
import controller.MemberController;
import controller.MovieController;
import dto.*;
import exception.WrongInput;

import java.util.*;

import dto.MovieAPI;
import service.MovieService;
import util.PagingUtil;

public class AdminView {
    private static Scanner sc = new Scanner(System.in);

    public static void userManage(Member member) {
        while (true) {
            ConsoleUI.printHeader("MEMBER MANAGEMENT", "회원 관련 관리 메뉴입니다", ConsoleUI.GREEN, ConsoleUI.GREEN, 1);
            ConsoleUI.printMenu(new String[]{
                    "[1] 회원 삭제",
                    "[2] 회원 목록 조회",
                    "[3] 회원 상세 조회",
                    "[0] 이전으로 돌아가기"
            }, ConsoleUI.GREEN, 10);

            int menu = ConsoleUI.promptInt(sc, "회원 관리 메뉴 번호를 입력하세요");

            switch (menu){
                case 1 :
                    //회원 삭제
                    ConsoleUI.info("회원 삭제 메뉴로 이동합니다...");
                    deleteUserByName();
                    break;
                case 2 :
                    //회원 목록 조회
                    ConsoleUI.info("회원 목록을 불러옵니다...");
                    MemberController.selectUsers(member);
                case 3 :
                    //회원 상세 조회
                    ConsoleUI.info("회원 상세 정보를 조회합니다...");
                    selectUserDetail();
                    break;
                case 0 :
                    //이전으로 돌아가기
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    StartView.printAdminMenu(member);
                    return;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");

            }
        }

    }

    private static void selectUserDetail() {
        String userId = ConsoleUI.prompt(sc, "상세 검색할 회원의 아이디를 입력하세요");
        MemberController.selectUserDetail(userId);
    }

    private static void deleteUserByName() {
        String name = ConsoleUI.prompt(sc, "삭제할 회원의 이름을 입력해주세요");
        MemberController.deleteUserByName(name);
    }

    public static String center(String text, int width) {
        int padding = (width - text.length()) / 2;
        String format = "%" + (padding + text.length()) + "s";
        return String.format(format, text);
    }

    public static void inquiryManage(Member member) {
        while (true) {
            ConsoleUI.printHeader("INQUIRY MANAGEMENT", "문의 조회 및 답변 관리 메뉴입니다", ConsoleUI.GREEN, ConsoleUI.GREEN, 3);
            ConsoleUI.printMenu(new String[]{
                    "[1] 문의 목록 조회",
                    "[2] 문의 상세 조회",
                    "[3] 문의 답변",
                    "[0] 이전으로 돌아가기"
            }, ConsoleUI.GREEN, 9);

            int menu = ConsoleUI.promptInt(sc, "문의 관리 메뉴를 선택하세요");
            switch (menu){
                case 1 :
                    //문의 목록 조회
                    ConsoleUI.info("전체 문의 정보를 조회합니다...");
                    InquiryController.selectInquiry(member);
                    break;
                case 2 :
                    //문의 상세 조회
                    ConsoleUI.info("문의 상세 정보를 조회합니다...");
                    selectInquiryDetail();
                    break;
                case 3 :
                    //문의 답변
                    ConsoleUI.info("문의 답변 화면으로 이동합니다...");
                    inquiryResponse();
                    break;
                case 0 :
                    //이전으로 돌아가기
                    ConsoleUI.info("이전으로 돌아갑니다...");
                    StartView.printAdminMenu(member);
                    return;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
            }
        }
    }

    private static void selectInquiryDetail() {
        int inquiryId = ConsoleUI.promptInt(sc, "상세 검색할 문의의 번호를 입력하세요");
        InquiryController.selectInquiryDetail(inquiryId);
    }

    private static void inquiryResponse() {
        int inquiryId = ConsoleUI.promptInt(sc, "답변할 문의의 번호를 입력하세요");
        InquiryController.selectInquiryDetail(inquiryId);

        String response = ConsoleUI.prompt(sc, "답변");
        InquiryController.insertInquiryreResponse(inquiryId, response);
    }

    public static void movieManager(Member member) {
        while (true) {
            ConsoleUI.printHeader("MOVIE MANAGEMENT", "영화 관리 기능을 선택하세요", ConsoleUI.GREEN, ConsoleUI.GREEN , 3);
            ConsoleUI.printMenu(new String[]{
                    "[1] 영화 목록 조회",
                    "[2] 영화 상세 조회",
                    "[3] 영화 등록",
                    "[4] 영화 수정",
                    "[5] 영화 삭제",
                    "[0] 이전으로 돌아가기"
            }, ConsoleUI.GREEN, 9);

            int menu = ConsoleUI.promptInt(sc, "영화 관리 메뉴를 선택하세요");
            switch (menu){
                case 1 :
                    //영화 목록 조회
                    ConsoleUI.info("전체 영화 정보를 조회합니다...");
                    MovieController.selectAllMovies();
                    break;
                case 2:
                    //영화 목록 상세 조회
                    ConsoleUI.info("영화 상세 정보를 조회합니다...");
                    selectMovieDetail();
                    break;
                case 3 :
                    //새로운 영화 등록
                    ConsoleUI.info("영화 등록 메뉴로 이동합니다...");
                    AutoOrpassivity(member);
                    //insertMovie();
                    break;
                case 4 :
                    //영화 정보 수정
                    ConsoleUI.info("영화 수정 메뉴로 이동합니다...");
                    updateMovie(member);
                    break;
                case 5 :
                    //영화 삭제
                    ConsoleUI.info("영화 삭제 메뉴로 이동합니다...");
                    deleteMovieById();
                    break;
                case 0 :
                    //이전으로 돌아가기
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    StartView.printAdminMenu(member);
                    return;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
            }
        }
    }

    private static void AutoOrpassivity(Member member) {
        while (true) {
            ConsoleUI.printHeader("MOVIE REGISTRATION", null, ConsoleUI.GREEN, ConsoleUI.GREEN);
            ConsoleUI.printMenu(new String[]{
                    "[1] 개봉 예정작 등록",
                    "[2] 수동 등록",
                    "[0] 돌아가기"
            }, ConsoleUI.GREEN, 10);

            int menu = ConsoleUI.promptInt(sc, "등록 방법을 선택하세요");
            switch (menu){
                case 1:
                    ConsoleUI.info("개봉 예정작 목록을 불러옵니다...");
                    MovieinsertView();
                    break;
                case 2:
                    ConsoleUI.info("수동 등록을 진행합니다...");
                    // 수동 등록 → 스케줄 등록 연결
                    Movie inserted = insertMovieAndReturn();
                    if (inserted != null) {
                        // ScheduleView로 분리
                        ScheduleView.askAndInsertSchedule(inserted);
                    }
                    break;
                case 0:
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    movieManager(member);
                    break;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
                    break;
            }
        }
    }

    private static Movie insertMovieAndReturn() {
        String movieTitle  = ConsoleUI.prompt(sc, "제목");
        String actor       = ConsoleUI.prompt(sc, "배우(000, 000, 000)");
        String releaseDate = ConsoleUI.prompt(sc, "개봉일(0000-00-00)");

        Genre genre = null;
        while (genre == null) {

            String inputGenre = ConsoleUI.prompt(sc, "장르 (액션/애니메이션/스릴러/호러/코미디/로맨스/드라마/판타지)");
            try {
                genre = Genre.from(inputGenre);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        int screeningTime = ConsoleUI.promptInt(sc, "상영시간(분)");
        String director   = ConsoleUI.prompt(sc, "감독");

        String status = ConsoleUI.prompt(sc, "상영여부 (상영중/상영종료)");
        boolean isScreening = status.equals("상영중");

        Movie m = new Movie(movieTitle, actor, releaseDate, genre.name(), screeningTime, director, isScreening);
        MovieController.insertMovie(m);
        ConsoleUI.success("영화가 등록되었습니다.");
        return m;
    }

    private static void deleteMovieById() {
        int movieId = ConsoleUI.promptInt(sc, "삭제할 영화 ID를 입력하세요");
        MovieController.deleteMovieById(movieId);
    }

    private static void updateMovie(Member member) {
        while (true){
            ConsoleUI.printHeader("MOVIE UPDATE", "수정할 작업을 선택하세요", ConsoleUI.GREEN, ConsoleUI.GREEN, 3);
            ConsoleUI.printMenu(new String[]{
                    "[1] 상영 종료",
                    "[2] 영화 정보 수정",
                    "[0] 돌아가기"
            }, ConsoleUI.GREEN);

            int menu = ConsoleUI.promptInt(sc, "수정 방법을 선택하세요");
            switch (menu) {
                case 1:
                    //상영 종료
                    ConsoleUI.info("상영 종료 처리할 영화 목록을 불러옵니다...");
                    MovieController.selectMovieByIsScreen();
                    updateMovieIsScreen();
                    break;
                case 2:
                    ConsoleUI.info("영화 정보 수정을 진행합니다...");
                    updateMovieNormal();
                    break;
                case 0:
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    movieManager(member);
                    break;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
                    break;
            }

        }
    }

    private static void updateMovieIsScreen() {
        int movieId = ConsoleUI.promptInt(sc, "상영 종료할 영화 ID를 입력하세요");
        MovieController.updateMovie(movieId, "상영여부", "상영종료");
    }

    private static void updateMovieNormal() {
        int movieId = ConsoleUI.promptInt(sc, "수정할 영화 ID를 입력하세요");
        String colName = ConsoleUI.prompt(sc, "수정할 칼럼명을 입력하세요");
        String content = ConsoleUI.prompt(sc, "내용을 입력하세요");
        MovieController.updateMovie(movieId, colName, content);
    }

    private static void selectMovieDetail() {
        int movieId = ConsoleUI.promptInt(sc, "상세 조회할 영화 ID를 입력하세요");
        MovieController.selectMovieDetail(movieId);
    }

    public static void MovieinsertView() {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("UPCOMING MOVIES", "등록할 개봉 예정작을 선택하세요", ConsoleUI.GREEN, ConsoleUI.GREEN);

        List<MovieAPI> movies;
        try {
            movies = UpcomingMovieAPI.getUpcomingMovies(2026, 2026);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        MovieService movieService = new MovieService();
        Set<String> registeredTitles = new HashSet<>();
        try {
            List<Movie> registeredMovies = movieService.selectAllMovies();
            for (Movie m : registeredMovies) {
                registeredTitles.add(m.getMovieTitle());
            }
        } catch (Exception e) {
            // 조회 실패해도 목록은 계속 보여줌
        }

        movies.removeIf(m -> registeredTitles.contains(m.getTitle()));

        if (movies.isEmpty()) {
            ConsoleUI.alert("등록 가능한 개봉 예정작이 없습니다. (모두 이미 등록됨)");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) movies.size() / pageSize);
        int currentPage = 0;

        while (true) {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, movies.size());

            ConsoleUI.blank(1);
            System.out.println("[페이지 " + (currentPage + 1) + " / " + totalPages + "]  등록 가능 " + movies.size() + "건");
            System.out.println("-".repeat(ConsoleUI.WIDTH));

            for (int i = start; i < end; i++) {
                MovieAPI m = movies.get(i);
                String openDate = formatOpenDate(m.getOpenDate());

                System.out.println(ConsoleUI.CYAN + "[#" + (i - start + 1) + "]" + ConsoleUI.RESET);
                System.out.println("영화 ID    : " + safe(m.getMovieId()));
                System.out.println("제목       : " + fitTitle(safe(m.getTitle()), 40));
                System.out.println("장르       : " + safe(m.getGenre()));
                System.out.println("개봉예정일  : " + safe(openDate));
                System.out.println("-".repeat(ConsoleUI.WIDTH));
            }

            System.out.println("[ < ] 이전 페이지    [ > ] 다음 페이지    [ 1 ] 상세조회    [ 0 ] 이전");
            ConsoleUI.printLine(ConsoleUI.GREEN);

            String input = ConsoleUI.prompt(sc, "이동/조회 메뉴 입력");
            switch (input) {
                case ">":
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    } else {
                        ConsoleUI.alert("마지막 페이지입니다.");
                    }
                    break;

                case "<":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        ConsoleUI.alert("첫 번째 페이지입니다.");
                    }
                    break;

                case "1":
                    try {
                        ConsoleUI.info("개봉 예정작 상세 정보를 조회합니다...");
                        Movie registeredMovie = UpcomingMovieDetailAPI.showUpcomingMovieDetail(movies);
                        if (registeredMovie != null) {
                            ScheduleView.askAndInsertSchedule(registeredMovie);
                            movies.removeIf(m -> m.getTitle().equals(registeredMovie.getMovieTitle()));

                            if (movies.isEmpty()) {
                                ConsoleUI.success("모든 등록 가능한 개봉 예정작이 처리되었습니다.");
                                return;
                            }

                            totalPages = (int) Math.ceil((double) movies.size() / pageSize);
                            if (totalPages == 0) totalPages = 1;
                            if (currentPage >= totalPages) currentPage = totalPages - 1;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "0":
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    return;

                default:
                    ConsoleUI.alert("올바른 메뉴를 입력하세요.");
            }
        }
    }

    public static Movie insertMovieAuto(String movieTitle, String actor, String releaseDate,
                                        String genre, int screeningTime, String director) {
        MovieService movieService = new MovieService();

        ConsoleUI.blank(1);
        String answer = ConsoleUI.prompt(sc, "개봉 예정일 영화를 등록하시겠습니까?(Y/N)");

        // 중복 체크
        List<Movie> list = movieService.selectAllMovies();
        for (Movie m2 : list) {
            if (m2.getMovieTitle().equals(movieTitle)) {
                FailView.errorMessage("이미 등록된 영화입니다.");
                return null;  // 중복이면 null 반환
            }
        }

        if (answer.equalsIgnoreCase("Y")) {
            String status = ConsoleUI.prompt(sc, "상영여부를 입력해주세요");
            boolean isScreening = status.equals("상영중");

            Movie m = new Movie(movieTitle, actor, releaseDate, genre, screeningTime, director, isScreening);
            MovieController.insertMovie(m);

            // movieId가 제대로 들어왔는지 확인
            if (m.getMovieId() == 0) {
                FailView.errorMessage("영화 ID를 가져오지 못했습니다.");
                return null;
            }

            ConsoleUI.success("영화가 등록되었습니다.");
            return m;  // 등록된 Movie 반환
        }

        return null;  // Y가 아니면 null 반환
    }

    public static void statistics(Member member) {
        while (true) {
            ConsoleUI.printHeader("DASHBOARD", "통계 및 분석 메뉴입니다", ConsoleUI.GREEN, ConsoleUI.GREEN, 2);
            ConsoleUI.printMenu(new String[]{
                    "[1] 신규 가입자 및 회원 증감 추이",
                    "[2] 영화 장르 선호도",
                    "[3] 영화별 누적 예매 순위 (Top 10)",
                    "[4] 주간 매출 분석",
                    "[0] 이전으로 돌아가기"
            }, ConsoleUI.GREEN, 13);

            int menu = ConsoleUI.promptInt(sc, "관리 메뉴 번호를 입력하세요");
            switch (menu) {
                case 1:
                    ConsoleUI.info("신규 가입자 및 회원 증감 추이를 불러옵니다...");
                    DashboardController.user();
                    break;
                case 2:
                    ConsoleUI.info("영화 장르 선호도를 불러옵니다...");
                    DashboardController.preferGenre();
                    break;
                case 3:
                    ConsoleUI.info("영화별 누적 예매 순위를 불러옵니다...");
                    DashboardController.movieTopten();
                    break;
                case 4:
                    DashboardController.reservationMovie();
                    break;
                case 0:
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    return;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
            }
        }
    }

    private static String formatOpenDate(String openDate) {
        if (openDate == null || openDate.isBlank()) return "-";

        if (openDate.matches("\\d{8}")) {
            return openDate.substring(0, 4) + "-"
                    + openDate.substring(4, 6) + "-"
                    + openDate.substring(6, 8);
        }

        return openDate;
    }

    private static String safe(String text) {
        return (text == null || text.isBlank()) ? "-" : text;
    }

    private static String fitTitle(String text, int maxLength) {
        if (text == null) return "-";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}