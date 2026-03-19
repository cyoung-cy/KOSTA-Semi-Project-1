package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Movie;
import dto.Room;
import dto.Schedules;
import dto.ReservationRequest;

public class ReservationView {

    private static final Scanner sc = new Scanner(System.in);

    // ──────────────────────────────────────────────
    // Step 1 : 영화 선택
    // ──────────────────────────────────────────────
    public Movie askMovieId(List<Movie> movieList) {
        while (true) {
            printMovieList(movieList);

            try {
                int choice = ConsoleUI.promptInt(sc, "예매하실 영화 번호를 입력하세요");

                if (choice == 0) {
                    throw new RuntimeException("뒤로가기 실행");
                }

                if (choice >= 1 && choice <= movieList.size()) {
                    Movie selected = movieList.get(choice - 1);
                    printMovieDetail(selected);
                    return selected;
                }

                ConsoleUI.alert("목록에 있는 번호를 선택해주세요. (1 ~ " + movieList.size() + ")");
            } catch (NumberFormatException e) {
                ConsoleUI.alert("숫자만 입력 가능합니다.");
            }
        }
    }

    private void printMovieList(List<Movie> movieList) {
        for (int i = 0; i < movieList.size(); i++) {
            Movie movie = movieList.get(i);
            System.out.println(
                    (i + 1) + " [상영중]"
            );
            System.out.println(
                    "영화 ID : " + movie.getMovieId() +
                            " | 제목 : " + ConsoleUI.CYAN + shortenTitle(movie.getMovieTitle(), 28) + ConsoleUI.RESET
            );

            printDashLine();
        }
        System.out.println("[0] 뒤로가기");
        ConsoleUI.printLine(ConsoleUI.RED);
    }

    // ──────────────────────────────────────────────
    // Step 1-2 : 영화 상세 출력
    // ──────────────────────────────────────────────
    public void printMovieDetail(Movie movie) {
        String subtitle = "[" + movie.getMovieTitle() + "] 상세 정보";
        ConsoleUI.printHeader("선택한 영화 정보", subtitle, ConsoleUI.RED, ConsoleUI.YELLOW, -2);

        printDetailItem("영화 번호", String.valueOf(movie.getMovieId()));
        printDetailItem("배우", movie.getActor());
        printDetailItem("개봉일", movie.getReleaseDate());
        printDetailItem("장르", movie.getGenre());
        printDetailItem("상영시간", movie.getScreeningTime() + "분");
        printDetailItem("감독", movie.getDirector());
        printDetailItem("상영여부", movie.getIsScreening() ? "상영중" : "상영종료");
    }

    // ──────────────────────────────────────────────
    // Step 2 : 스케줄 선택
    // ──────────────────────────────────────────────
    public int askScheduleId(List<Schedules> scheduleList) {
        ConsoleUI.printHeader("상영 스케줄 선택", "당일 15분 후 상영 스케줄부터 예매가 가능합니다", ConsoleUI.RED, ConsoleUI.YELLOW, 2);

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedules s = scheduleList.get(i);

            System.out.println((i + 1) + " [상영관 " +
                    s.getRoomId() + "관" + "]");

            System.out.println("상영 시작 : " + ConsoleUI.CYAN + s.getStartTime() + ConsoleUI.RESET);
            printDashLine();
        }

        System.out.println("[0] 이전 단계로");
        ConsoleUI.printLine(ConsoleUI.RED);

        return ConsoleUI.promptInt(sc, "스케줄 번호를 선택하세요");
    }

    // ──────────────────────────────────────────────
    // Step 3 : 인원 입력
    // ──────────────────────────────────────────────
    public int askAdultCount() {
        return askCount("성인 인원을 입력하세요 (최대 5명)");
    }

    public int askTeenCount() {
        return askCount("청소년 인원을 입력하세요 (최대 5명)");
    }

    public int askBabyCount() {
        return askCount("영유아 인원을 입력하세요 (최대 5명)");
    }

    private int askCount(String label) {
        while (true) {
            try {
                int count = ConsoleUI.promptInt(sc, label);
                if (count >= 0 && count <= 5) return count;
                ConsoleUI.alert("0~5 사이의 숫자를 입력하세요.");
            } catch (NumberFormatException e) {
                ConsoleUI.alert("숫자만 입력 가능합니다.");
            }
        }
    }

    // ──────────────────────────────────────────────
    // Step 4 : 좌석 선택
    // ──────────────────────────────────────────────
//    public List<String> askSeats(Room room, List<String> reservedNames) {
//        room.displaySeatLayout(reservedNames);
//
//        ConsoleUI.blank(1);
//        System.out.println("[ ] : Available (예매 가능)");
//        System.out.println("[X] : Booked (예매 완료)");
//        ConsoleUI.printLine(ConsoleUI.RED);
//
//        List<String> selectedSeats = new ArrayList<>();
//
//        while (true) {
//            String input = ConsoleUI.prompt(sc, "좌석 선택 (예: A-10, A-9)");
//
//            if (input.isBlank()) {
//                ConsoleUI.alert("좌석을 입력해주세요.");
//                continue;
//            }
//
//            String[] parts = input.split("[,\\s]+");
//            selectedSeats.clear();
//            boolean valid = true;
//
//            for (String part : parts) {
//                String normalized = part.trim().replace("-", "").toUpperCase();
//
//                if (reservedNames.contains(normalized)) {
//                    ConsoleUI.alert(normalized + " 은(는) 이미 예약된 좌석입니다.");
//                    valid = false;
//                    break;
//                }
//
//                if (!normalized.matches("[A-J][1-9]|[A-J]10")) {
//                    ConsoleUI.alert("잘못된 좌석 형식입니다: " + part);
//                    valid = false;
//                    break;
//                }
//
//                if (selectedSeats.contains(normalized)) {
//                    ConsoleUI.alert("중복 좌석이 포함되어 있습니다: " + normalized);
//                    valid = false;
//                    break;
//                }
//
//                selectedSeats.add(normalized);
//            }
//
//            if (valid && !selectedSeats.isEmpty()) {
//                return selectedSeats;
//            }
//        }
//    }
    public List<String> askSeats(Room room, List<String> reservedNames) {
        room.displaySeatLayout(reservedNames);

        ConsoleUI.blank(1);
        System.out.println("   [O] : 예매 가능 좌석");
        System.out.println("   " + ConsoleUI.GRAY + ConsoleUI.BOLD + "[X]" + ConsoleUI.RESET + " : 예매 완료 좌석");
        System.out.println("   입력 예시 : A-10, A-9 / B1, B2");
        ConsoleUI.printLine(ConsoleUI.RED);

        List<String> selectedSeats = new ArrayList<>();

        while (true) {
            String input = ConsoleUI.prompt(sc, "좌석 선택");

            if (input.isBlank()) {
                ConsoleUI.alert("좌석을 입력해주세요.");
                continue;
            }

            String[] parts = input.split("[,\\s]+");
            selectedSeats.clear();
            boolean valid = true;

            for (String part : parts) {
                String normalized = part.trim().replace("-", "").toUpperCase();

                if (!normalized.matches("[A-J](10|[1-9])")) {
                    ConsoleUI.alert("잘못된 좌석 형식입니다 : " + part);
                    valid = false;
                    break;
                }

                if (reservedNames.contains(normalized)) {
                    ConsoleUI.alert(normalized + " 좌석은 이미 예약된 좌석입니다.");
                    valid = false;
                    break;
                }

                if (selectedSeats.contains(normalized)) {
                    ConsoleUI.alert("중복 좌석이 포함되어 있습니다 : " + normalized);
                    valid = false;
                    break;
                }

                selectedSeats.add(normalized);
            }

            if (valid && !selectedSeats.isEmpty()) {
                ConsoleUI.success("선택 좌석 : " + String.join(", ", selectedSeats));
                return selectedSeats;
            }
        }
    }

    // ──────────────────────────────────────────────
    // Step 5 : 카드 정보 입력
    // ──────────────────────────────────────────────
    public String askCardInfo() {
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("결제 정보 확인", "등록된 카드 정보를 입력하세요", ConsoleUI.RED, ConsoleUI.YELLOW);

        int choice = ConsoleUI.promptInt(sc, "결제를 진행하시겠습니까? (1: 결제승인 / 2: 취소)");
        if (choice != 1) throw new RuntimeException("결제를 취소하였습니다.");

        return ConsoleUI.prompt(sc, "등록하신 카드번호를 입력하세요 (예: 1234-5678-9012-3456)").trim();
    }

    // ──────────────────────────────────────────────
    // Step 6 : 성공 화면
    // ──────────────────────────────────────────────
    public void displaySuccess(ReservationRequest req) {
        int total = (req.getAdultCount() * 15000)
                + (req.getTeenCount() * 10000)
                + (req.getBabyCount() * 0);

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("예매 완료", "예매가 완료되었습니다! 즐거운 관람 되세요 🎬", ConsoleUI.RED, ConsoleUI.YELLOW);

        printDetailItem("티켓 번호", String.valueOf(System.currentTimeMillis()));
        printDetailItem("좌석", String.valueOf(req.getSelectSeats()));
        printDetailItem("인원",
                "성인 " + req.getAdultCount() +
                        " / 청소년 " + req.getTeenCount() +
                        " / 영유아 " + req.getBabyCount());
        printDetailItem("금액", total + "원");
        printDetailItem("예매자 ID", String.valueOf(req.getMemberId()));

        printDashLine();
        ConsoleUI.info("Enter(엔터)를 누르면 회원 메인 메뉴로 돌아갑니다. ");
        sc.nextLine();
    }

    // ──────────────────────────────────────────────
    // 내부 보조 메서드
    // ──────────────────────────────────────────────
    private void printDetailItem(String label, String value) {
        System.out.println("■ " + label);
        System.out.println("  " + ConsoleUI.CYAN + (value == null || value.isBlank() ? "-" : value) + ConsoleUI.RESET);
    }

    private void printDashLine() {
        System.out.println("-".repeat(ConsoleUI.WIDTH));
    }

    private String shortenTitle(String title, int maxLength) {
        if (title == null || title.isBlank()) return "-";
        if (title.length() <= maxLength) return title;
        return title.substring(0, maxLength - 3) + "...";
    }
}