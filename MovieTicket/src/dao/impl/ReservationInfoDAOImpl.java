package dao.impl;

import java.sql.Connection;

import common.jdbc.QueryExecutor;
import dao.ReservationInfoDAO;
import dto.ReservationInfo;
import mapper.ReservationInfoMapper;

public class ReservationInfoDAOImpl implements ReservationInfoDAO {

	private static final QueryExecutor queryExecutor = QueryExecutor.getInstance();

	private static final ReservationInfoMapper reservationInfoMapper = ReservationInfoMapper.getInstance();
	
	private static final ReservationInfoDAOImpl instance = new ReservationInfoDAOImpl();

	private ReservationInfoDAOImpl() {
	}

	public static ReservationInfoDAOImpl getInstance() {
		return instance;
	}

	@Override
	public int insert(Connection conn, ReservationInfo reservationInfo) {
		String sql = "insert into RESERVATION_INFO(RESERVATION_ID, SEAT_ID, CATEGORY, PRICE) "
				+ "VALUES (?, ?, ?, ?)";

		Object[] params = { 
				reservationInfo.getReservationId(), reservationInfo.getSeatId(), 
				reservationInfo.getCategory(), reservationInfo.getPrice()
				};

		return queryExecutor.update(conn, sql, params);
	}

}
