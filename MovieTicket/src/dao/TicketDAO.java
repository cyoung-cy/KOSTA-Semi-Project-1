package dao;

import vo.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface TicketDAO {
    /*
     * 0313
     * 이동혁
     * TODO:영화 예매 정보 담는 DAO
     */

    // 티켓 정보 조회 DAO
    List<Ticket> selectTicketsByMemberId(int memberId) throws SQLException;

}
