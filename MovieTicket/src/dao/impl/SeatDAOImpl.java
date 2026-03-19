package dao.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import common.jdbc.QueryExecutor;
import dao.SeatDAO;
import dto.Seat;
import mapper.SeatMapper;

public class SeatDAOImpl implements SeatDAO{
	
	private static final QueryExecutor queryExecutor = QueryExecutor.getInstance();
	
	private static final SeatMapper seatMapper = SeatMapper.getInstance();
	
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
	    String sql = "select * from SEAT where ROOM_ID = ?"; 
	    
	    // QueryExecutor를 통해 리스트를 가져오고 Set으로 변환하는 로직 확인
	    return queryExecutor.queryForSet(sql, SeatMapper.getInstance(), roomId);
	}

	@Override
	public Seat selectSeatById(int seatId) {
		return null;
	}
	
	@Override
	public int updateIsReserved(Connection conn, int seatId, boolean isReserved) {
		String sql = "update SEAT set IS_RESERVED = ? where SEAT_ID = ? and IS_RESERVED = false";
		
		Object[] params = { isReserved, seatId };
		
		return queryExecutor.update(conn, sql,params);
	}
	
	@Override
	public List<Integer> findIdsByNames(Connection conn, int scheduleId, List<String> seatNames) {
		String sql = "select s.SEAT_ID from SEAT s " +
	             "join SCHEDULES sc on s.ROOM_ID = sc.ROOM_ID " +
	             "where sc.SCHEDULE_ID = ? and s.NAME = ?";
	    
 	    List<Integer> ids = new ArrayList<>();
	    for (String name : seatNames) {
	    	Object[] params = {scheduleId, name};
	        Integer id = queryExecutor.queryForObject(conn, sql, Integer.class, params);
	        if (id != null) ids.add(id);
	    }
	    return ids;
	}
	
	@Override
	public List<String> selectReservedSeatNames(int scheduleId) {
		String sql = "select S.NAME " +
                "from SEAT S " +
                "join RESERVATION_INFO RI on S.SEAT_ID = RI.SEAT_ID " +
                "join RESERVATION R on RI.RESERVATION_ID = R.RESERVATION_ID " +
                "where R.SCHEDULE_ID = ?";

	    Object[] params = { scheduleId };
	    return queryExecutor.queryForList(sql, rs -> rs.getString("NAME"), params);
	}
	
}
