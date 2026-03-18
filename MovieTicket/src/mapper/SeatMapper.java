package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Seat;

public class SeatMapper implements RowMapper<Seat> {
	
	private static final SeatMapper instance = new SeatMapper();
	
	private SeatMapper() {};
	
	public static SeatMapper getInstance() {
		return instance;
	}
	
	@Override
    public Seat mapRow(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatId(rs.getInt("SEAT_ID"));
        seat.setRoomId(rs.getInt("ROOM_ID"));
        seat.setReserved(rs.getBoolean("IS_REVSERED"));
        seat.setName(rs.getString("NAME"));
        seat.setColNum(rs.getInt("COL_NUM"));
        seat.setRowNum(rs.getInt("ROW_NUM"));
        return seat;
    }
}
