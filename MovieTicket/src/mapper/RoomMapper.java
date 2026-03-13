package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Room;
import dto.Seat;

public class RoomMapper implements RowMapper<Room> {
	
	private static RoomMapper instance;
	
	private RoomMapper() {};
	
	public static RoomMapper getInstance() {
		if(instance == null) {
			instance = new RoomMapper();
		}
		return instance;
	}
	
	@Override
    public Room mapRow(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setRoomId(rs.getInt("ROOM_ID"));
        room.setMovieId(rs.getInt("MOVIE_ID"));
        room.setShowing(rs.getBoolean("IS_SHOWING"));
        room.setName(rs.getString("NAME"));
        return room;
    }
}
