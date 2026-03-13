package dao;

import java.util.List;

import dto.Reservation;

public interface ReservationDAO {
	
	List<Reservation> selectList();
	
	void insert(Reservation reservation);
	
}
