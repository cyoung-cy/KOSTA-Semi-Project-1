package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Seat;

public class SeatMapper implements RowMapper<Seat> {
	
	private SeatMapper instance;
	
	private SeatMapper() {};
	
	public SeatMapper getInstance() {
		if(instance == null) {
			return new SeatMapper();
		}
		return instance;
	}
	
	@Override
    public Seat mapRow(ResultSet rs) throws SQLException {
        Seat seat = new Seat();
        seat.setSeatId(rs.getInt("SEAT_ID"));
        seat.setRoomId(rs.getInt("ROOM_ID"));
        seat.setReserved(rs.getBoolean("IS_REVERSED"));
        seat.setName(rs.getString("NAME"));
        seat.setColNum(rs.getInt("COL_NUM"));
        seat.setRowNum(rs.getInt("ROW_NUM"));
        return seat;
    }
}
