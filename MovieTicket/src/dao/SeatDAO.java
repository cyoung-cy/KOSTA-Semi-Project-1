package dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import dto.Seat;

public interface SeatDAO {
    
	 /*
     * 20260312
     * 한상혁
     * TODO: 좌석 생성
     * */
    void insert(Seat seats);

    /*
     * 20260312
     * 한상혁
     * TODO: 특정 상영관의 모든 좌석 조회 (Room 객체의 seatSet와 연결)
     * */
    Set<Seat> selectSeatsByRoomId(int roomId);

    /*
     * 20260312
     * 한상혁
     * TODO: 특정 좌석 상세 정보 조회
     * */
    Seat selectSeatById(int seatId);
    
	 /*
     * 20260318
     * 한상혁
     * TODO: 좌석 상태 변경
     * */
    int updateIsReserved(Connection conn, int seatId, boolean isReserved);
    
    /*
     * 20260318
     * 한상혁
     * TODO: 스케줄 번호를 통해 해당 상영관의 좌석 ID를 조회
     */
    List<Integer> findIdsByNames(Connection conn, int scheduleId, List<String> seatNames);
    
    /*
     * 20260318
     * 한상혁
     * TODO: 스케줄 번호를 통해 해당 상영관의 예약좌석 이름 반환
     * */
    List<String> selectReservedSeatNames(int scheduleId);
    
}
