package controller;

import java.util.List;

import cache.CinemaCache;
import dto.Member;
import dto.Movie;
import dto.Reservation;
import dto.ReservationRequest;
import dto.Room;
import dto.Schedules;
import exception.NotFoundException;
import service.MovieService;
import service.ReservationService;
import service.SchedulesService;
import service.SeatService;
import view.EndView;
import view.FailView;
import view.ReservationView;

public class ReservationController {

	private final ReservationView reservationView = new ReservationView();

	private final ReservationService reservationService = ReservationService.getInstance();

	private final SchedulesService schedulesService = SchedulesService.getInstance();
	
	private final SeatService seatService = SeatService.getInstance();
	
	private static final ReservationController instance = new ReservationController();
	
	private ReservationController() {
	}

	public static ReservationController getInstance() {
		return instance;
	}

	public void selectReservationsByMemberId(int memberId) {
		try {
			List<Reservation> list = reservationService.selectReservationsByMemberId(memberId);
            EndView.selectReservationsByMemberId(list, memberId);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public void manageReservation(Member member) {
		ReservationRequest req = new ReservationRequest();
		MovieService movieService = new MovieService();
		try {
            System.out.println("\n=== 🎬 영화 예매 시스템을 시작합니다 ===");

            // Step 1: 영화 선택 (Movie 객체로 받음)
            List<Movie> movieList = movieService.selectMovieByIsScreen();
            Movie selectedMovie = reservationView.askMovieId(movieList);  // Movie 객체 반환
            int movieId = selectedMovie.getMovieId(); // movieId 추출

            // Step 2: 상영 일정 선택 (선택된 영화 ID로 스케줄 조회 후 뷰에 전달)
            List<Schedules> scheduleList = schedulesService.getAvailableSchedules(movieId);
            Schedules selectedSchedule = null;
            int scheduleId = 0;
            
            while (true) {
                try {
                    int choice = reservationView.askScheduleId(scheduleList);
                    
                    // 사용자가 입력한 번호(인덱스+1)가 리스트 범위 안에 있는지 체크
                    if (choice >= 1 && choice <= scheduleList.size()) {
                        selectedSchedule = scheduleList.get(choice - 1);
                        
                        scheduleId = selectedSchedule.getScheduleId();
                        
                        req.setScheduleId(selectedSchedule.getScheduleId());
                        req.setMovieId(selectedSchedule.getMovieId()); 
                        
                        break; // 성공했으니 루프 탈출!
                    } else {
                        System.out.println("❌ 목록에 있는 번호를 선택해주세요. (1 ~ " + scheduleList.size() + ")");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ 숫자만 입력 가능합니다.");
                } catch (Exception e) {
                    System.out.println("❌ 알 수 없는 오류: " + e.getMessage());
                }
            }
            
            // Step 3: 인원 설정
            req.setAdultCount(reservationView.askAdultCount());
            req.setTeenCount(reservationView.askTeenCount());
            req.setBabyCount(reservationView.askBabyCount());

            // Step 4: 좌석 선택
            // 실시간 예약 현황 가져오기 (DB 실시간 조회)
            List<String> reservedNames = seatService.getReservedSeatNames(selectedSchedule.getRoomId());
            
	         // 캐시에서 해당 일정의 룸 객체 꺼내기 (메모리 조회)
	         Room room = CinemaCache.getInstance().getRoomById(selectedSchedule.getRoomId());
	         
	         List<String> selectedSeats = reservationView.askSeats(room, reservedNames);
            req.setSelectSeats(selectedSeats);

            // Step 5: 결제 및 사용자 정보 결합
            req.setMemberId(member.getMemberId());
            req.setCardInfo(reservationView.askCardInfo());

            reservationService.executeReservation(req);

            // 성공 시 결과 렌더링
            reservationView.displaySuccess(req);

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }
}
