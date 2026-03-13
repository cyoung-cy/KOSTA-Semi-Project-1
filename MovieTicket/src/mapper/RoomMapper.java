package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;

public class RoomMapper implements RowMapper<RoomDto> {
	
	@Override
    public SeatDTO mapRow(ResultSet rs) throws SQLException {
        SeatDTO dto = new SeatDTO();
        dto.setSeatNo(rs.getInt("seat_no"));
        dto.setSeatRow(rs.getString("seat_row"));
        dto.setSeatCol(rs.getInt("seat_col"));
        dto.setIsAvailable(rs.getString("is_available"));
        return dto;
    }
}
