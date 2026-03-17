package service;

import dao.ReservationDAO;
import dao.impl.ReservationDAOImpl;
import dto.Movie;
import dto.Reservation;
import exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public class ReservationService {
    ReservationDAO reservationDao = new ReservationDAOImpl();

    /*
     * 0314
     * 김채영
     * TODO: 사용자 예매 조회 서비스
     */
    public List<Reservation> selectReservationsByMemberId(int memberId) throws NotFoundException, SQLException {
        List<Reservation> list = reservationDao.selectReservationsByMemberId(memberId);
        if(list.isEmpty()) throw new NotFoundException("\'" + memberId + "\' 이(가) 얘매한 영화가 없습니다.");
        return list;
    }
}
