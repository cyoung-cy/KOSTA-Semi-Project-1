package dao.impl;

import java.util.Set;

import common.jdbc.BaseDAO;
import dao.RoomDAO;
import dto.Room;
import dto.request.RoomCreateRequest;

public class RoomDAOImpl extends BaseDAO implements RoomDAO{
	
	private static RoomDAOImpl instance;
	
	private RoomDAOImpl() {}
	
	public static RoomDAOImpl getInstance() {
		if(instance == null) {
			instance = new RoomDAOImpl();
		}
		return instance;
	}

	@Override
	public int insert(RoomCreateRequest roomRequest) {
		String sql = "insert into ROOM(NAME, IS_SHOWING"
				+ ") values(?, ?)";
		
		Object[] params = { 
				roomRequest.getName(), roomRequest.isShowing()
				};
		
		return insertAndGetPk(sql,params);
	}

	@Override
	public Set<Room> selectAllRooms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room selectRoomById(int roomId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Room selectRoomByMovieId(int movieId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Room> selectRoomsByShowing(boolean isShowing) {
		return null;
	}

	@Override
	public void updateRoom(Room room) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoom(int roomId) {
		// TODO Auto-generated method stub
		
	}

}
