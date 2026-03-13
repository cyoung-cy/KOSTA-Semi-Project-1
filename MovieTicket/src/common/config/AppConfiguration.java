package common.config;

import cache.CinemaCache;
import dao.impl.RoomDAOImpl;
import dao.impl.SeatDAOImpl;

public class AppConfiguration {
	
	private static AppConfiguration instance;

	private AppConfiguration() {}

	public static AppConfiguration getInstance() {
		if (instance == null) {
			instance = new AppConfiguration();
		}
		return instance;
	}

	// CinemaCache인스턴스 반환 메소드
	public CinemaCache cinemaCache() {
		return CinemaCache.getInstance(new SeatDAOImpl(), RoomDAOImpl.getInstance());
	}

}
