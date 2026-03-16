package dao.impl;

import java.util.Set;

import common.jdbc.BaseDAO;
import dao.SeatDAO;
import dto.Seat;

public class SeatDAOImpl extends BaseDAO implements SeatDAO{
	
	private static SeatDAOImpl instance;

	private SeatDAOImpl() {}
	
	public static SeatDAOImpl getInstance() {
		if(instance == null) {
			instance = new SeatDAOImpl();
		}
		return instance;
	}
	
	@Override
	public void insertSeats(Seat seat) {
		String sql = "insert into SEAT(ROOM_ID, NAME, ROW_NUM, COL_NUM"
				+ ") values(?, ?, ?, ?)";
		
		Object[] params = { 
				seat.getRoomId(), seat.getName(), seat.getRowNum(), seat.getColNum()
				};
		
		update(sql,params);
	}

	@Override
	public Set<Seat> selectSeatsByRoomId(int roomId) {
		return null;
	}

	@Override
	public void updateSeatAvailability(int seatId, boolean isAvailable) {
		
	}

	@Override
	public Seat selectSeatById(int seatId) {
		return null;
	}
	
}
