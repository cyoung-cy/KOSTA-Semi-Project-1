package service;

import dao.TicketDAO;
import dao.impl.TicketDAOImpl;
import exception.NotFoundException;
import vo.Ticket;

import java.sql.SQLException;
import java.util.List;

public class TicketService {
    TicketDAO ticketDao = new TicketDAOImpl();
    /*
     * 0313
     * 이동혁
     * 티켓 서비스
     */
    public List<Ticket> getTicketsByMemberId(int memberId) throws NotFoundException, SQLException {
        List<Ticket> list = ticketDao.selectTicketsByMemberId(memberId);
        if(list.size() == 0) throw new NotFoundException("티켓 정보를 찾을 수 없습니다.");
        return list;
    }
}
