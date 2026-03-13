package dao.impl;

import java.util.Set;

import dao.SeatDAO;
import dto.Seat;

public class SeatDAOImpl implements SeatDAO{

	@Override
	public void insertSeats(Seat seats) {
		
	}

	@Override
	public Set<Seat> selectSeatsByRoomId(int roomId) {
		return null;
	}

	@Override
	public void updateSeatAvailability(int seatId, boolean isAvailable) {
		
	}

	@Override
	public Seat selectSeatById(int seatId) {
		return null;
	}
	
}
