package controller;

import service.TicketService;
import view.EndView;
import view.FailView;
import vo.Ticket;

import java.util.List;

public class TicketController {
    /*
    * 0313
    * 이동혁
    * 티켓 컨트롤러
    */
    static TicketService ticketService = new TicketService();

    public static void getTicketsInfo(int memberId) {
        try {
            List<Ticket> list = ticketService.getTicketsByMemberId(memberId);
            EndView.printTickets(list);
        } catch (Exception e) {
            e.printStackTrace();
            FailView.errorMessage(e.getMessage());
        }
    }
}
