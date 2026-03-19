package dao;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import dto.Schedules;

public interface SchedulesDAO {
	
	 /*
     * 20260317
     * 한상혁
     * TODO: 스케줄 등록
     * */
	void insert(Schedules schedules);
	
	 /*
     * 20260318
     * 한상혁
     * TODO: 영화 예약 가능한지 확인
     * */
	boolean checkOverlap(int roomId, Timestamp start, Timestamp end);
	
	 /*
     * 20260318
     * 한상혁
     * TODO: 영화 ID로 해당 영화의 상영 일정 확인(예약 가능한 스케줄)
     * */
	List<Schedules> selectAvailableByMovieId(int movieId);
	
	 /*
     * 20260318
     * 한상혁
     * TODO: 아이디로 단일 스케줄 조회
     * */
	Schedules selectOneById(Connection conn, int scheduleId);

	/*
	 * 20260319
	 * 김채영
	 * TODO: 아이디로 단일 스케줄 조회
	 * */
	List<Schedules> selectByRoomIdAndDate(int roomId, String date);
}
