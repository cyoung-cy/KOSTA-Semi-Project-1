package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dto.Movie;
import dto.Room;
import dto.Schedules;
import dto.ReservationRequest;
import util.SeatUtil;
import records.SeatPos;

public class ReservationView {

    private static final Scanner sc = new Scanner(System.in);

    // ──────────────────────────────────────────────
    // Step 1 : 영화 선택
    // ──────────────────────────────────────────────
    // 기존 반환타입 int → Movie로 변경
    public Movie askMovieId(List<Movie> movieList) {
        System.out.println("\n=================== [ 영화 목록 조회 ] ===================");
        for (int i = 0; i < movieList.size(); i++) {
            Movie m = movieList.get(i);
            System.out.printf("[%d] ID:%-10d | %-20s | %s%n",
                    i + 1,
                    m.getMovieId(),
                    m.getMovieTitle(),
                    m.getIsScreening() ? "상영중" : "상영종료");
        }
        System.out.println("[0] 뒤로가기");
        System.out.println("----------------------------------------------------------");
        System.out.println("         < : 이전/다음 : >");
        System.out.println("----------------------------------------------------------");

        while (true) {
            System.out.print("예매하실 영화 번호를 입력하세요: ");
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                if (choice == 0) throw new RuntimeException("뒤로가기");
                if (choice >= 1 && choice <= movieList.size()) {
                    Movie selected = movieList.get(choice - 1);
                    // 선택한 영화 디테일 바로 출력
                    printMovieDetail(selected);
                    return selected;
                }
                System.out.println("❌ 목록에 있는 번호를 선택해주세요. (1 ~ " + movieList.size() + ")");
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자만 입력 가능합니다.");
            }
        }
    }

    // ──────────────────────────────────────────────
    // Step 1-2 : 영화 상세 출력 (선택 후)
    // ──────────────────────────────────────────────
    public void printMovieDetail(Movie movie) {
        System.out.println("\n===== 영화 정보 =====");
        System.out.println("번호: " + movie.getMovieId());
        System.out.println("제목: " + movie.getMovieTitle());
        System.out.println("배우: " + movie.getActor());
        System.out.println("개봉일: " + movie.getReleaseDate());
        System.out.println("장르: " + movie.getGenre());
        System.out.println("상영시간: " + movie.getScreeningTime() + "분");
        System.out.println("감독: " + movie.getDirector());
        System.out.println("상영여부: " + (movie.getIsScreening() ? "상영중" : "상영종료"));
    }

    // ──────────────────────────────────────────────
    // Step 2 : 스케줄 선택
    // ──────────────────────────────────────────────
    public int askScheduleId(List<Schedules> scheduleList) {
        System.out.println("\n[ 상영 스케줄 선택 ]");
        System.out.println("※ 당일 15분 후 상영 스케줄은 예매가 불가합니다.");

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedules s = scheduleList.get(i);
            System.out.printf("[%d] %s | %d관%n",
                    i + 1,
                    s.getStartTime(),
                    s.getRoomId());   // getRoomName() → getRoomId() 로 변경
        }
        System.out.println("[0] 이전 단계로");
        System.out.println("------------------------------------------");
        System.out.print("스케줄 번호를 선택하세요: ");

        int choice = Integer.parseInt(sc.nextLine().trim());
        if (choice == 0) throw new RuntimeException("뒤로가기");
        return choice;
    }

    // ──────────────────────────────────────────────
    // Step 3 : 인원 입력
    // ──────────────────────────────────────────────
    public int askAdultCount() {
        return askCount("▶ 관람하실 인원수를 입력하세요 (최대 5명): 성인");
    }

    public int askTeenCount() {
        return askCount("  청소년");
    }

    public int askBabyCount() {
        return askCount("  영유아(48개월 미만)");
    }

    private int askCount(String label) {
        while (true) {
            System.out.print(label + ": ");
            try {
                int count = Integer.parseInt(sc.nextLine().trim());
                if (count >= 0 && count <= 5) return count;
                System.out.println("❌ 0~5 사이의 숫자를 입력하세요.");
            } catch (NumberFormatException e) {
                System.out.println("❌ 숫자만 입력 가능합니다.");
            }
        }
    }

    // ──────────────────────────────────────────────
    // Step 4 : 좌석 선택
    // ──────────────────────────────────────────────
    public List<String> askSeats(Room room, List<String> reservedNames) {
        // 좌석 배치도 출력
        room.displaySeatLayout(reservedNames);

        System.out.println("\n[ ] : Available (예매 가능)");
        System.out.println("[X] : Booked (예매 완료)");
        System.out.println("==================================================");

        List<String> selectedSeats = new ArrayList<>();

        while (true) {
            System.out.print("좌석 선택 (예: A-10, A-9, A-10): ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("❌ 좌석을 입력해주세요.");
                continue;
            }

            // "A-10, A-9" 또는 "A10 A9" 형태 모두 허용
            String[] parts = input.split("[,\\s]+");
            selectedSeats.clear();
            boolean valid = true;

            for (String part : parts) {
                // "A-10" -> "A10" 정규화
                String normalized = part.trim().replace("-", "").toUpperCase();

                if (reservedNames.contains(normalized)) {
                    System.out.println("❌ " + normalized + " 은(는) 이미 예약된 좌석입니다.");
                    valid = false;
                    break;
                }

                // 유효성: 첫글자 알파벳, 나머지 숫자
                if (!normalized.matches("[A-J][1-9]|[A-J]10")) {
                    System.out.println("❌ 잘못된 좌석 형식입니다: " + part);
                    valid = false;
                    break;
                }

                if (selectedSeats.contains(normalized)) {
                    System.out.println("❌ 중복 좌석이 포함되어 있습니다: " + normalized);
                    valid = false;
                    break;
                }

                selectedSeats.add(normalized);
            }

            if (valid && !selectedSeats.isEmpty()) {
                return selectedSeats;
            }
        }
    }

    // ──────────────────────────────────────────────
    // Step 5 : 카드 정보 입력
    // ──────────────────────────────────────────────
    public String askCardInfo() {
        System.out.print("\n결제하시겠습니까? (1: 결제승인 / 2: 취소): ");
        String choice = sc.nextLine().trim();
        if (!"1".equals(choice)) throw new RuntimeException("결제를 취소하였습니다.");

        System.out.print("등록하신 카드번호를 입력하세요 (예: 1234-5678-9012-3456): ");
        return sc.nextLine().trim();  // 입력한 카드번호를 CARD_INFO와 비교
    }

    // ──────────────────────────────────────────────
    // Step 6 : 성공 화면 (티켓)
    // ──────────────────────────────────────────────
    public void displaySuccess(ReservationRequest req) {
        System.out.println("\n[ 예매가 정상적으로 완료되었습니다! ]");
        System.out.println();
        System.out.println("TICKET No. " + System.currentTimeMillis());
        System.out.println("좌석: " + req.getSelectSeats());

        int total = (req.getAdultCount() * 15000)
                + (req.getTeenCount()  * 10000)
                + (req.getBabyCount()  * 0);
        System.out.printf("인원: 성인 %d / 청소년 %d / 영유아 %d%n",
                req.getAdultCount(), req.getTeenCount(), req.getBabyCount());
        System.out.println("금액: " + total + "원");
        System.out.println("예매자 ID: " + req.getMemberId());
        System.out.println("\nEnter(엔터)를 누르면 회원 메인 메뉴로 돌아갑니다.");
        sc.nextLine();
    }
}