package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dto.Reservation;

public interface ReservationDAO {
    /*
     * 20260312 -> 20260314
     * 이동혁 -> 김채영
     * TODO: 예매 내역 조회 DAO 인터페이스
     */
    List<Reservation> selectReservationsByMemberId(int memberId) throws SQLException;
    
    /*
     * 20260318
     * 한상혁
     * TODO: 예매 등록
     */
    int insert(Connection conn, Reservation reservation);
    
}
