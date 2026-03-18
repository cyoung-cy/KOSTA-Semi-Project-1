package dao;

import java.sql.Connection;

import dto.ReservationInfo;

public interface ReservationInfoDAO {
	
	int insert(Connection conn, ReservationInfo reservationInfo);
}
