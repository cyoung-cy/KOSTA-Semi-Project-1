package dao;

import dto.Movie;
import dto.Reservation;
import dto.Seat;

import java.sql.SQLException;
import java.util.List;

public interface ReservationDAO {
    /*
     * 20260312 -> 20260314
     * 이동혁 -> 김채영
     * TODO: 예매 내역 조회 DAO 인터페이스
     */
    List<Reservation> selectReservationsByMemberId(int memberId) throws SQLException;
    
    void insert(Reservation reservation);
}
