package common.config;

import cache.CinemaCache;
import dao.impl.RoomDAOImpl;
import dao.impl.SeatDAOImpl;

public class AppConfiguration {
	
	private static AppConfiguration instance;

	// 외부에서 마음대로 공장을 못 차리게 막음
	private AppConfiguration() {}

	public static AppConfiguration getInstance() {
		if (instance == null) {
			instance = new AppConfiguration();
		}
		return instance;
	}

	public CinemaCache cinemaCache() {
		return CinemaCache.getInstance(new SeatDAOImpl(), new RoomDAOImpl());
	}

}
