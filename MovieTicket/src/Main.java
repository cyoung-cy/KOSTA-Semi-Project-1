import java.util.List;

import common.config.AppConfiguration;
import dto.ReservationRequest;
import service.ReservationService;
import view.StartView;

public class Main {
    public static void main(String[] args) {
    	
    	AppConfiguration appConfiguration = AppConfiguration.getInstance();
    	
//    	Schedules schedules = new Schedules();
//    	
//    	schedules.setMovieId(20179281);
//    	schedules.setRoomId(1);
//    	schedules.setStartTime(Timestamp.valueOf("2026-03-20 20:00:00"));
//    	
//    	SchedulesService.getInstance().registerSchedule(schedules);
    	
//    	ReservationRequest req = new ReservationRequest();
//    	req.setMemberId(13);
//    	req.setCardInfo("1512-5912-1392-3912");
//    	req.setScheduleId(1);
//    	req.setAdultCount(1);
//    	req.setTeenCount(1);
//    	req.setSelectSeats(List.of("A1","A2"));
//    	
//    	ReservationService.getInstance().executeReservation(req);
    	
//    	Map<String, Room> roomSet = CinemaCache.getInstance().getRoomMap();  
//    	System.out.println(roomSet);
//    	Seat seat = new Seat();
//    	SeatPos seatPos = SeatUtil.convertToIndices("b-1");
//    	seat.setColNum(seatPos.col());
//    	seat.setRowNum(seatPos.row());
//    	SeatService.getInstance().insertSeat("4관", seat);
    	
        StartView.menu();
//    	System.out.println(SeatUtil.convertToIndices("b-10"));
    }
}