package controller;

import dto.Reservation;
import service.ReservationService;
import view.EndView;
import view.FailView;

import java.util.List;

public class ReservationController {
    static ReservationService reservationService = new ReservationService();


    public static void selectReservationsByMemberId(int memberId) {
        try{
            List<Reservation> list = reservationService.selectReservationsByMemberId(memberId);
            EndView.selectReservationsByMemberId(list, memberId);
        }catch(Exception e){
            FailView.errorMessage(e.getMessage());
        }
    }
}
