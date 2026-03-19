package dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import common.jdbc.QueryExecutor;
import common.jdbc.RowMapper;
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
		String sql = "UPDATE SEAT SET IS_RESERVED = ? WHERE SEAT_ID = ? AND (IS_RESERVED = FALSE OR IS_RESERVED IS NULL)";
		return queryExecutor.update(conn, sql, isReserved, seatId);
	}

	@Override
	public List<Integer> findIdsByNames(Connection conn, int scheduleId, List<String> seatNames) {
		StringBuilder sql = new StringBuilder(
				"SELECT DISTINCT s.SEAT_ID FROM SEAT s "
						+ "JOIN SCHEDULES sc ON s.ROOM_ID = sc.ROOM_ID "  // SCHEDULES → SCHEDULE
						+ "WHERE sc.SCHEDULE_ID = ? AND s.NAME IN (");

		for (int i = 0; i < seatNames.size(); i++) {
			sql.append(i == 0 ? "?" : ",?");
		}
		sql.append(")");

		Object[] params = new Object[1 + seatNames.size()];
		params[0] = scheduleId;
		for (int i = 0; i < seatNames.size(); i++) {
			params[i + 1] = seatNames.get(i);
		}

		// queryForList(conn, ...) 없음 → query(conn, ...) 직접 호출
		return queryExecutor.query(conn, sql.toString(),
				new RowMapper<Integer>() {
					@Override
					public Integer mapRow(java.sql.ResultSet rs) throws java.sql.SQLException {
						return rs.getInt(1);
					}
				},
				params);
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

	@Override
	public List<String> findReservedSeatNamesByScheduleId(int scheduleId) {
		String sql = "SELECT s.NAME FROM SEAT s "
				+ "JOIN RESERVATION_INFO ri ON s.SEAT_ID = ri.SEAT_ID "
				+ "JOIN RESERVATION r ON ri.RESERVATION_ID = r.RESERVATION_ID "
				+ "WHERE r.SCHEDULE_ID = ?";

		return queryExecutor.queryForList(sql,
				rs -> rs.getString("NAME"),
				scheduleId);
	}

}
