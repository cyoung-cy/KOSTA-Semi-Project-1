package service;

import dao.SeatDAO;
import exception.ConfigException;

public class SeatService {

	private final SeatDAO seatDAO;

	private static SeatService instance;

	private SeatService(SeatDAO seatDAO) {
		this.seatDAO = seatDAO;
	}

	public static SeatService getInstance() {
		if (instance == null)
			throw new ConfigException("SeatService 초기화 안 됨");

		return instance;
	}

	public static SeatService init(SeatDAO seatDAO) {
		if (seatDAO == null)
			throw new ConfigException("SeatDAO는 null일 수 없습니다.");
		if (instance == null)
			instance = new SeatService(seatDAO);
		
		return instance;
	}

}
