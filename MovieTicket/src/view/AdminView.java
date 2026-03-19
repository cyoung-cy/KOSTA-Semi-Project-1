package view;

import api.UpcomingMovieAPI;
import cache.CinemaCache;
import controller.DashboardController;
import controller.InquiryController;
import controller.MemberController;
import controller.MovieController;
import controller.RoomController;
import controller.SchedulesController;
import dto.*;
import exception.WrongInput;
import java.util.List;
import java.util.Scanner;
import dto.MovieAPI;
import service.MovieService;
import util.PagingUtil;


public class AdminView {
    private static Scanner sc = new Scanner(System.in);

    public static void userManage(Member member) {
        while(true){
            ConsoleUI.printHeader("회원 관리", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

            ConsoleUI.printMenu(new String[]{
                    "[1] 회원 삭제",
                    "[2] 회원 목록 조회",
                    "[3] 회원 상세 조회",
                    "[0] 이전으로 돌아가기"
            }, ConsoleUI.GREEN, 9);

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
                    userManage(member);
                    break;
                case 0 :
                    //이전으로 돌아가기
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    StartView.printAdminMenu(member);
                    break;
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
            ConsoleUI.printHeader("문의 관리", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

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
                    inquiryManage(member);
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
                    break;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
                    break;
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
        while(true){
            ConsoleUI.printHeader("영화 관리", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

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
                    break;
                default:
                    //new WrongInput();
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
                    break;
            }
        }
    }

    private static void AutoOrpassivity(Member member) {
        while (true){
            ConsoleUI.printHeader("영화 등록 방식 선택", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

            ConsoleUI.printMenu(new String[]{
                    "[1] 개봉 예정작 등록",
                    "[2] 수동 등록",
                    "[0] 돌아가기"
            }, ConsoleUI.GREEN);

            int menu = ConsoleUI.promptInt(sc, "등록 방법을 선택하세요");
            switch (menu){
                case 1:
                    ConsoleUI.info("개봉 예정작 목록을 불러옵니다...");
                    MovieinsertView();
                    break;
                case 2:
                    ConsoleUI.info("수동 등록을 진행합니다...");
                    insertMovie();
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

    private static void deleteMovieById() {
        int movieId = ConsoleUI.promptInt(sc, "삭제할 영화 ID를 입력하세요");
        MovieController.deleteMovieById(movieId);
    }

    private static void updateMovie(Member member) {
        while (true){
            ConsoleUI.printHeader("영화 수정", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

            ConsoleUI.printMenu(new String[]{
                    "[1] 상영 종료",
                    "[2] 영화 정보 수정",
                    "[0] 돌아가기"
            }, ConsoleUI.GREEN);

            int menu = ConsoleUI.promptInt(sc, "등록 방법을 선택하세요");
            switch (menu){
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
        int movieId = ConsoleUI.promptInt(sc, " 상영 종료할 영화 ID를 입력하세요");

        String colName = "상영여부";

        String content = "상영종료";

        MovieController.updateMovie(movieId, colName, content);
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
        ConsoleUI.printHeader("개봉 예정작 목록", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

        List<MovieAPI> movies = null;
        try {
            movies = UpcomingMovieAPI.getUpcomingMovies(2026, 2026);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //페이징
        Scanner sc = new Scanner(System.in);
        int pageSize = 10;
        int totalPages = (int) Math.ceil((double) movies.size() / pageSize);
        int currentPage = 0;

        int idW = 12, titleW = 30, genreW = 12, dateW = 12;
        String separator = PagingUtil.makeSeparator(idW, titleW, genreW, dateW, 0)
                .replaceAll("-\\+-$", "");  // statusW 없으므로 끝 제거

        while (true) {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, movies.size());

            // 헤더
            System.out.println(separator);
            System.out.println(
                    PagingUtil.padRight("영화 ID",   idW)    + " | " +
                            PagingUtil.padRight("제목",       titleW) + " | " +
                            PagingUtil.padRight("장르",       genreW) + " | " +
                            PagingUtil.padRight("개봉예정일", dateW)
            );
            System.out.println(separator);

            // 현재 페이지 출력
            for (int i = start; i < end; i++) {
                MovieAPI m = movies.get(i);
                String openDate = m.getOpenDate();
                if (openDate != null && openDate.matches("\\d{8}")) {
                    openDate = openDate.substring(0,4) + "-" + openDate.substring(4,6) + "-" + openDate.substring(6,8);
                }
                System.out.println(
                        PagingUtil.padRight(m.getMovieId(),             idW)    + " | " +
                                PagingUtil.padRight(m.getTitle(),               titleW) + " | " +
                                PagingUtil.padRight(m.getGenre(),               genreW) + " | " +
                                PagingUtil.padRight(openDate != null ? openDate : "", dateW)
                );
            }

            System.out.println(separator);
            System.out.printf("페이지 [%d / %d]  전체 %d건%n", currentPage + 1, totalPages, movies.size());

            String input = ConsoleUI.prompt(sc, "[ < 이전 | > 다음 | 1 상세조회 | 0 이전 ]");
            switch (input) {
                case ">":
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    } else {
                        ConsoleUI.alert("마지막 페이지입니다.");
                    }
                    break;
                case "<":
                    if (currentPage > 0) currentPage--;
                    else ConsoleUI.alert("첫 번째 페이지입니다.");
                    break;
                case "1":
                    try {
                        ConsoleUI.info("개봉 예정작 상세 정보를 조회합니다...");
                        UpcomingMovieDetailAPI.showUpcomingMovieDetail(movies);
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

    public static void insertMovie(){
        String movieTitle = ConsoleUI.prompt(sc, "제목");
        String actor = ConsoleUI.prompt(sc, "배우(000, 000, 000)");
        String releaseDate = ConsoleUI.prompt(sc, "개봉일(0000-00-00)");

        Genre genre = null;
        while (genre == null) {
            String inputGenre = ConsoleUI.prompt(sc, "장르 (액션/애니메이션/스릴러/호러/코미디/로맨스/다큐/드라마/판타지)");
            try {
                genre = Genre.from(inputGenre);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());  // 잘못된 장르면 재입력 요청
            }
        }

        int screeningTime = ConsoleUI.promptInt(sc, "상영시간");
        String director = ConsoleUI.prompt(sc, "감독");
        String status = ConsoleUI.prompt(sc, "상영여부");

        Boolean isScreening = false;
        if(status.equals("상영중")) {
            isScreening = true;
        } else if (status.equals("상영여부")) {
            isScreening = false;

        }

        Movie m = new Movie(movieTitle, actor, releaseDate, genre.name(), screeningTime, director, isScreening);

        MovieController.insertMovie(m);
    }

    public static void insertMovieAuto(String movieTitle, String actor, String releaseDate, String genre, int screeningTime, String director) {
        MovieService movieService = new MovieService();

        String answer = ConsoleUI.prompt(sc, "개봉 예정일 영화를 등록하시겠습니까?(Y/N)");

        List<Movie> list = movieService.selectAllMovies();
        for(Movie m2 : list){
            if(m2.getMovieTitle().equals(movieTitle)){
                FailView.errorMessage("이미 등록된 영화입니다.");
                MovieinsertView();
                break;
            }
        }

        if(answer.toUpperCase().equals("Y")){
            String status = ConsoleUI.prompt(sc, "상영여부를 입력해주세요");
            Boolean isScreening = false;
            if(status.equals("상영중")) {
                isScreening = true;
            } else if (status.equals("상영여부")) {
                isScreening = false;

            }

            Movie m = new Movie(movieTitle, actor, releaseDate, genre, screeningTime, director, isScreening);
            MovieController.insertMovie(m);
        }

    }

    public static void statistics(Member member) {
        while(true){
            ConsoleUI.printHeader("DASHBOARD", null, ConsoleUI.GREEN, ConsoleUI.GREEN);

            ConsoleUI.printMenu(new String[]{
                    "[1] 신규 가입자 및 회원 증감 추이",
                    "[2] 영화 장르 선호도",
                    "[3] 영화별 누적 예매 순위 (Top 10)",
                    "[4] 주간 매출 분석",
                    "[0] 이전으로 돌아가기"
            }, ConsoleUI.GREEN, 14);

            int menu = ConsoleUI.promptInt(sc, "관리 메뉴 번호를 입력하세요");
            switch(menu) {
                case 1 :
                    //신규 가입자 및 회원 증감 추이
                    ConsoleUI.info("신규 가입자 및 회원 증감 추이를 불러옵니다...");
                    DashboardController.user();
                    break;
                case 2 :
                    //영화 장르 선호도
                    ConsoleUI.info("영화 장르 선호도를 불러옵니다...");
                    DashboardController.preferGenre();
                    break;
                case 3 :
                    //영화별 누적 예매 순위 (Top 10)
                    ConsoleUI.info("영화별 누적 예매 순위를 불러옵니다...");
                    DashboardController.movieTopten();
                    break;
                case 4:
                    //주간 요일별 매출 및 예매 분석
                    ConsoleUI.info("주간 매출 분석을 불러옵니다...");
                    DashboardController.reservationMovie();
                    break;
                case 0 :
                    // 이전으로 돌아가기
                    ConsoleUI.info("이전 화면으로 돌아갑니다...");
                    movieManager(member);
                    break;
                default:
                    ConsoleUI.alert("올바른 메뉴 번호를 입력하세요.");
                    break;
            }

        }
    }
    
//    public static void scheduleManager(Member member) {
//        while(true){
//        	System.out.println("=============================================================");
//            System.out.println("                   🗓️  [상영 스케줄 관리]");
//            System.out.println("=============================================================");
//            System.out.println("                [1] 상영관별 스케줄 조회");
//            System.out.println("                [2] 신규 스케줄 등록");
//            System.out.println("                [3] 스케줄 정보 수정 (시간/관 변경)");
//            System.out.println("                [4] 스케줄 삭제");
//            System.out.println("                [0] 이전으로 돌아가기");
//            System.out.println("=============================================================");
//            System.out.print("메뉴를 선택하세요 : ");
//
//            System.out.print("관리 메뉴 번호를 입력하세요 : ");
//            int menu = Integer.parseInt(sc.nextLine());
//            switch (menu) {
//                case 1 -> ScheduleView.viewSchedulesByRoom(member); 
//                case 2 -> insertSchedule();     
//                case 3 -> updateSchedule();     
//                case 4 -> deleteSchedule();     
//                case 0 -> StartView.printAdminMenu(member);
//                default -> new WrongInput();
//            }
//        }
//    }
    
//    private static void viewSchedulesByRoom(Member member) {
//        System.out.println("\n=============================================================");
//        System.out.println("🗓️  [ 상영관별 스케줄 상세 조회 ]");
//        System.out.println("=============================================================");
//
//        RoomController.getInstance().selectAllRoomsFromCache();
//
//        System.out.print("\n👉 조회를 원하는 [상영관 ID]를 입력하세요: ");
//        try {
//            int roomId = Integer.parseInt(sc.nextLine());
//
//            String roomName = "알 수 없는 관"; // 초기값
//            
//            for (Room room : CinemaCache.getInstance().getRoomMap().values()) {
//                if (room.getRoomId() == roomId) {
//                    roomName = room.getName();
//                    break;
//                }
//            }
//            
//            System.out.println("\n-------------------------------------------------------------");
//            System.out.printf("📍 [%s번 상영관]의 오늘 이후 상영 일정\n", roomName);
//            System.out.println("-------------------------------------------------------------");
//
//            SchedulesController.getInstance().selectSchedulesByRoomId(roomId);
//
//            System.out.println("-------------------------------------------------------------\n");
//        } catch (NumberFormatException e) {
//            System.out.println("❌ 숫자만 입력 가능합니다.");
//        } catch (Exception e) {
//            System.out.println("❌ 조회 중 오류 발생: " + e.getMessage());
//        }
//    }
//    
//    
//    private static void insertSchedule() {
//        System.out.println("\n=============================================================");
//        System.out.println("🗓️  [ 신규 상영 스케줄 등록 ]");
//        System.out.println("=============================================================");
//        
//        System.out.println(" [ 등록 가능한 영화 목록 ]");
//        MovieController.selectMovieByIsScreen(); 
//        System.out.print("👉 등록할 [영화 ID]를 입력하세요: ");
//        int movieId = Integer.parseInt(sc.nextLine());
//
//        // 2. 상영관 목록 (1관, 2관 등)
//        System.out.println(" [ 상영관 목록 ]");
//        RoomController.getInstance().selectAllRoomsFromCache();
//        System.out.print("👉 등록할 상영관 ID를 입력하세요");
//        int roomId = Integer.parseInt(sc.nextLine());
//
//        // 3. 상영 시작 시간 (형식 가이드 제공)
//        System.out.print("👉 상영 시작 시간 (형식: YYYY-MM-DD HH:mm): ");
//        String startTimeStr = sc.nextLine();
//        
//        try {
//            SchedulesController.getInstance().insertSchedule(movieId, roomId, startTimeStr);
//            System.out.println("✅ 스케줄이 성공적으로 등록되었습니다!");
//        } catch (Exception e) {
//            System.out.println("❌ 스케줄 등록 실패: " + e.getMessage());
//        }
//    }
}


