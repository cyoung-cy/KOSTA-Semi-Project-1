package service;

import java.util.List;

import cache.CinemaCache;
import dao.RoomDAO;
import dao.SeatDAO;
import dto.Room;
import dto.Seat;
import exception.AppConfigException;
import exception.NotFoundException;
import exception.WrongInput;

public class SeatService {

	private final SeatDAO seatDAO;
	
	private final RoomDAO roomDAO;

	private static SeatService instance;

	private SeatService(SeatDAO seatDAO, RoomDAO roomDAO) {
		this.seatDAO = seatDAO;
		this.roomDAO = roomDAO;
	}

	public static SeatService getInstance() {
		if (instance == null)
			throw new AppConfigException("SeatService 초기화 안 됨");

		return instance;
	}

	public static SeatService init(SeatDAO seatDAO, RoomDAO roomDAO) {
		if (instance == null)
			instance = new SeatService(seatDAO, roomDAO);
		
		return instance;
	}
	
	  /*
     * 20260316
     * 한상혁
     * TODO: 좌석 등록 (관리자용)
     * */
	public void insertSeat(String roomName, Seat seat) {
		if(roomName == null || seat == null)
			throw new WrongInput("입력값이 잘못되었습니다."); 
		
		Room target = CinemaCache.getInstance().getRoom(roomName);
		
		if(target == null)
			throw new NotFoundException("해당 상영관은 존재하지 않습니다.");
		
		seat.setRoomId(target.getRoomId());
		seatDAO.insert(seat);
	}
	
	 /*
     * 20260318
     * 한상혁
     * TODO: 예약좌석 이름 리스트
     * */
	public List<String> getReservedSeatNames(int scheduleId) {
        return seatDAO.selectReservedSeatNames(scheduleId);
    }
	
}
