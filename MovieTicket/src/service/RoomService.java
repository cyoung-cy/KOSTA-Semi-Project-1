package service;

import dao.RoomDAO;
import dao.SeatDAO;
import exception.AppConfigException;

public class RoomService {
	
	private final SeatDAO seatDAO;
	
	private final RoomDAO roomDAO;

	private static RoomService instance;

	private RoomService(SeatDAO seatDAO, RoomDAO roomDAO) {
		this.seatDAO = seatDAO;
		this.roomDAO = roomDAO;
	}

	public static RoomService getInstance() {
		if (instance == null)
			throw new AppConfigException("RoomService 초기화 안 됨");

		return instance;
	}
	
	public static RoomService init(SeatDAO seatDAO, RoomDAO roomDAO) {
		if (instance == null)
			instance = new RoomService(seatDAO, roomDAO);
		
		return instance;
	}
	
}
