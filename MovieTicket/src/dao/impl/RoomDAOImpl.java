package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;

import dao.RoomDAO;
import dto.Room;
import dto.request.RoomCreateRequest;

public class RoomDAOImpl implements RoomDAO{

	@Override
	public void insert(RoomCreateRequest roomRequest) {
		Connection con = null;
		PreparedStatement ps = null;
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
