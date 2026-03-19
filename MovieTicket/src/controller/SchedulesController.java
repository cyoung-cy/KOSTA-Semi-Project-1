package controller;

import java.sql.Timestamp;
import java.util.Scanner;

import dto.Schedules;
import exception.ScheduleConflictException;
import service.SchedulesService;
import session.Session;
import view.EndView;
import view.FailView;

public class SchedulesController {
	
    private static final SchedulesController instance = new SchedulesController();
    
    private final SchedulesService schedulesService = SchedulesService.getInstance();

    private SchedulesController() {}

    public static SchedulesController getInstance() {
        return instance;
    }
	
	public void Schedule(int movieId, int roomId, String inputTime, Session session) {
        try {
            String userRole = (String) session.getAttribute("role");

            if (userRole == null || (!userRole.equals("admin") && !userRole.equals("subAdmin"))) {
                FailView.errorMessage("권한이 없습니다. 관리자만 스케줄을 등록할 수 있습니다.");
                return;
            }
            
            if (inputTime == null || inputTime.length() == 0) {
                FailView.errorMessage("상영 시간이 입력되지 않았습니다.");
                return;
            }
            
            // Timestamp 변환
            Timestamp startTime = Timestamp.valueOf(inputTime + ":00");

            Schedules schedules = new Schedules();
            schedules.setMovieId(movieId);
            schedules.setRoomId(roomId);
            schedules.setStartTime(startTime);

            schedulesService.registerSchedule(schedules);

            EndView.successMessage("새로운 상영 일정이 성공적으로 등록되었습니다.");

        } catch (ScheduleConflictException e) {
            FailView.errorMessage(e.getMessage());
        } catch (IllegalArgumentException e) {
            FailView.errorMessage("날짜 형식이 잘못되었습니다. (yyyy-MM-dd HH:mm)");
        } catch (Exception e) {
            FailView.errorMessage("스케줄 등록 중 오류 발생: " + e.getMessage());
        }
    }
}
