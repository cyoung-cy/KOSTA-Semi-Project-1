package dao.impl;

import java.util.Set;

import common.jdbc.QueryExecutor;
import dao.SeatDAO;
import dto.Seat;

public class SeatDAOImpl implements SeatDAO{
	
	private static final QueryExecutor queryExecutor = QueryExecutor.getInstance();
	
	private static final SeatDAOImpl instance = new SeatDAOImpl();

	private SeatDAOImpl() {}
	
	public static SeatDAOImpl getInstance() {
		return instance;
	}
	
	@Override
	public void insert(Seat seat) {
		String sql = "insert into SEAT(ROOM_ID, NAME, ROW_NUM, COL_NUM"
				+ ") values(?, ?, ?, ?)";
		
		Object[] params = { 
				seat.getRoomId(), seat.getName(), seat.getRowNum(), seat.getColNum()
				};
		
		queryExecutor.update(sql,params);
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
