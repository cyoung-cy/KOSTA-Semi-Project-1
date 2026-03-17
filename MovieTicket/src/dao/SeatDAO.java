package dao;

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

    // 3. 특정 좌석의 예약 가능 상태 변경 (중요!)
    // 예매 성공 시 true -> false, 취소 시 false -> true
    /*
     * 20260312
     * 한상혁
     * TODO: 특정 좌석의 예약 가능 상태 변경 (중요!)
      				예매 성공 시 true -> false, 취소 시 false -> true
     * */
    void updateSeatAvailability(int seatId, boolean isAvailable);

    /*
     * 20260312
     * 한상혁
     * TODO: 특정 좌석 상세 정보 조회
     * */
    Seat selectSeatById(int seatId);
}
