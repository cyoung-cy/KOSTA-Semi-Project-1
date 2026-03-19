package dao.impl;

import java.util.Set;

import common.jdbc.QueryExecutor;
import dao.RoomDAO;
import dto.Room;
import mapper.RoomMapper;

public class RoomDAOImpl implements RoomDAO{
	
	private static final QueryExecutor queryExecutor = QueryExecutor.getInstance();
	
	private static final RoomMapper roomMapper = RoomMapper.getInstance();
	
	private static final RoomDAOImpl instance = new RoomDAOImpl();
	
	private RoomDAOImpl() {}
	
	public static RoomDAOImpl getInstance() {
		return instance;
	}

	@Override
	public int insert(String roomName) {
		String sql = "insert into ROOM(NAME) values( ? )";
		
		Object[] params = { roomName };
		
		return queryExecutor.insertAndGetPk(sql,params);
	}
	
	

	@Override
	public Set<Room> selectAllRooms() {
		String sql = "select * from ROOM";
		
		return queryExecutor.queryForSet(sql, roomMapper);
	}

	@Override
	public Room selectRoomById(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public int selectIdByRoomName(String roomName) {
//		String sql = "select ROOM_ID where ROOM_NAME = ?";
//		
//		Object[] params = { roomName};
//		
//		return queryExecutor.queryForObject(sql, int.class, params);
//	}

	@Override
	public Set<Room> selectRoomsByShowing(boolean isShowing) {
		return null;
	}

	@Override
	public void updateRoom(Room room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int roomId) {
		
	}

}
