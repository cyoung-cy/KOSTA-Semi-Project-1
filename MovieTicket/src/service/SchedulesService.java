package service;

import java.sql.Timestamp;
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
	
}
