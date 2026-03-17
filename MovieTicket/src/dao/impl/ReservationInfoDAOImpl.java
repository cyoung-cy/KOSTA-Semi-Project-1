package dao.impl;

import common.jdbc.QueryExecutor;
import dao.ReservationInfoDAO;
import dto.ReservationInfo;

public class ReservationInfoDAOImpl implements ReservationInfoDAO {

	private static final QueryExecutor queryExecutor = QueryExecutor.getInstance();

	private static final ReservationInfoDAOImpl instance = new ReservationInfoDAOImpl();

	private ReservationInfoDAOImpl() {
	}

	public static ReservationInfoDAOImpl getInstance() {
		return instance;
	}

	@Override
	public void insert(ReservationInfo reservationInfo) {
		String sql = "insert into RESERVATION_INFO(RESERVATION_ID, SEAT_ID, CATEGORY, PRICE) "
				+ "VALUES (?, ?, ?, ?)";

		Object[] params = { 
				reservationInfo.getReservationId(), reservationInfo.getSeatId(), 
				reservationInfo.getCategory(), reservationInfo.getPrice()
				};

		queryExecutor.update(sql, params);
	}

}
