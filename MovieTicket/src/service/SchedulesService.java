package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.MovieDAO;
import dao.RoomDAO;
import dao.SchedulesDAO;
import dto.Movie;
import dto.Schedules;
import exception.AppConfigException;
import exception.NotFoundException;
import exception.ScheduleConflictException;

public class SchedulesService {

	private final SchedulesDAO schedulesDAO;

	private final MovieDAO movieDAO;

	private final RoomDAO roomDAO;

	private static SchedulesService instance;

	private SchedulesService(SchedulesDAO schedulesDAO, MovieDAO movieDAO, RoomDAO roomDAO) {
		this.schedulesDAO = schedulesDAO;
		this.movieDAO = movieDAO;
		this.roomDAO = roomDAO;
	}

	public static SchedulesService getInstance() {
		if (instance == null)
			throw new AppConfigException("SchedulesService 초기화 안 됨");

		return instance;
	}

	public static SchedulesService init(SchedulesDAO schedulesDAO, MovieDAO movieDAO, RoomDAO roomDAO) {
		if (instance == null)
			instance = new SchedulesService(schedulesDAO, movieDAO, roomDAO);
		
		return instance;
	}
	
	public void registerSchedule(Schedules schedules) {
		
	    Movie movie = movieDAO.selectOne(schedules.getMovieId());
	    if(movie == null) 
	    	throw new NotFoundException("영화를 찾을 수 없습니다.");
	    // 등록할 영화의 총 시간(청소시간 30분 추가)
	    int totalMinutes = movie.getScreeningTime() + 30;

	    long startMs = schedules.getStartTime().getTime();
	    long endMs = startMs + (totalMinutes * 60 * 1000L); // 분 -> 밀리초 변환
	    Timestamp endTime = new Timestamp(endMs);

	    // 중복 체크
	    boolean isReserved = schedulesDAO.checkOverlap(
	        schedules.getRoomId(), 
	        schedules.getStartTime(), 
	        endTime
	    );

	    if (isReserved) {
	        throw new ScheduleConflictException("해당 시간에 이미 상영 일정이 있습니다");
	    }

	    schedulesDAO.insert(schedules);
	}
	
	public List<Schedules> getAvailableSchedules(int movieId) {
        List<Schedules> list = schedulesDAO.selectAvailableByMovieId(movieId);
        
        if (list == null || list.isEmpty()) {
            throw new NotFoundException("해당 영화는 현재 상영 일정이 없습니다.");
        }
        
        return list;
    }

	// 영화 러닝타임 기준으로 하루 시간대 슬롯 생성 (9:00 ~ 24:00)
	public List<String[]> generateTimeSlots(int screeningTime) {
		List<String[]> slots = new ArrayList<>();
		int startHour = 9;
		int startMin = 0;

		// 청소 시간 30분 포함
		int totalMin = screeningTime + 30;

		while (true) {
			int endHour = startHour + (startMin + screeningTime) / 60;
			int endMin = (startMin + screeningTime) % 60;

			// 24시 초과 시 종료
			if (endHour >= 24) break;

			String start = String.format("%02d:%02d", startHour, startMin);
			String end   = String.format("%02d:%02d", endHour, endMin);
			slots.add(new String[]{ start, end });

			// 다음 슬롯 시작 = 현재 종료 + 청소 30분
			startMin += totalMin;
			startHour += startMin / 60;
			startMin  = startMin % 60;
		}
		return slots;
	}

	// 특정 날짜, 상영관에서 사용 중인 시간대 조회
	public List<Schedules> getSchedulesByRoomAndDate(int roomId, String date) {
		return schedulesDAO.selectByRoomIdAndDate(roomId, date);
	}
}
