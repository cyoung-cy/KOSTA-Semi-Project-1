package view;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import dto.Movie;
import dto.ReservationRequest;
import dto.Room;
import dto.Schedules;

public class ReservationView {
    
    private static Scanner sc = new Scanner(System.in);

    // 1. 영화 선택 폼
    public int askMovieId(List<Movie> movieList) {
        System.out.println("\n🎬 [STEP 1] 관람하실 영화를 선택해주세요.");
        System.out.println("-------------------------------------------------------------");
        for (Movie movie : movieList) {
            System.out.printf("[%d] %-15s | %-10s | %d분\n", 
                movie.getMovieId(), movie.getMovieTitle(), movie.getGenre(), movie.getScreeningTime());
        }
        System.out.println("-------------------------------------------------------------");
        System.out.print("👉 영화 번호(ID) 입력: ");
        return Integer.parseInt(sc.nextLine());
    }

    // 2. 상영 일정 선택 폼
    public int askScheduleId(List<Schedules> scheduleList) {
    	System.out.println("\n🎬 [STEP 2] 상영 일정을 선택해주세요.");
        System.out.println("-------------------------------------------------------------");
        
        // 날짜 포맷팅 (03/20 11:00 형식)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 HH:mm");

        for (int i = 0; i < scheduleList.size(); i++) {
            Schedules s = scheduleList.get(i);
            String formattedTime = s.getStartTime().toLocalDateTime().format(formatter);
            
            System.out.printf("[%d] %s | %s\n", 
                (i + 1),               // pk대신 인덱스로 보여주기
                formattedTime, 
                s.getRoomName()
            );
        }
        System.out.println("-------------------------------------------------------------");
        System.out.print("👉 선택할 번호 입력: ");
        return Integer.parseInt(sc.nextLine());
    }

    // 3. 인원 설정 (성인)
    public int askAdultCount() {
        System.out.print("\n👨‍💼 성인 관람객 수는 몇 명인가요? (없으면 0): ");
        return Integer.parseInt(sc.nextLine());
    }

    // 4. 인원 설정 (청소년)
    public int askTeenCount() {
        System.out.print("🧑‍🎓 청소년 관람객 수는 몇 명인가요? (없으면 0): ");
        return Integer.parseInt(sc.nextLine());
    }
    
    // 5. 인원 설정 (어린이/영유아)
    public int askBabyCount() {
        System.out.print("👶 어린이/영유아 관람객 수는 몇 명인가요? (없으면 0): ");
        return Integer.parseInt(sc.nextLine());
    }

    // 5. 좌석 선택 폼
    public List<String> askSeats(Room room, List<String> reservedNames) {
        System.out.println("\n💺 [STEP 3] 좌석을 선택해주세요.");
        System.out.println("---------------------------------------------------------------");
        
        // 실시간 반영 displayLayout 호출
        room.displaySeatLayout(reservedNames); 
        
        System.out.println("\n-------------------------------------------------------------");
        
        System.out.print("👉 좌석 번호를 입력하세요 (예: A1,A2): ");
        String input = sc.nextLine();

        return Arrays.stream(input.split(","))
                     .map(String::trim)
                     .map(String::toUpperCase)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toList());
    }

    // 6. 결제 정보 입력
    public String askCardInfo() {
        System.out.println("\n💳 [STEP 4] 결제를 진행합니다.");
        System.out.print("👉 결제하실 카드 번호를 입력해주세요: ");
        return sc.nextLine();
    }
    
    // 7. 성공 결과 출력
    public void displaySuccess(ReservationRequest req) {
    	System.out.println("\n✨ 예매가 완료되었습니다! 즐거운 관람 되세요! ✨");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("📽️ 선택 좌석: %s\n", req.getSelectSeats());
        System.out.printf("👥 관람 인원: 성인 %d명, 청소년 %d명, 어린이 %d명\n", 
        	    req.getAdultCount(), 
        	    req.getTeenCount(), 
        	    req.getBabyCount()
        	);
        System.out.println("-------------------------------------------------------------");
    }
}