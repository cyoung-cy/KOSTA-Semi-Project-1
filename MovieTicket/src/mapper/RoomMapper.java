package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Room;

public class RoomMapper implements RowMapper<Room> {
	
	private static final RoomMapper instance = new RoomMapper();
	
	private RoomMapper() {};
	
	public static RoomMapper getInstance() {
		return instance;
	}
	
	@Override
    public Room mapRow(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("ROOM_ID"));
        room.setName(rs.getString("NAME"));
        return room;
    }
	
}
