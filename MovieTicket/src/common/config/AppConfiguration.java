package common.config;

import cache.CinemaCache;
import dao.impl.RoomDAOImpl;
import dao.impl.SeatDAOImpl;

public class AppConfiguration {
	
	private static final AppConfiguration instance = new AppConfiguration();

	// 생성자에 주입 코드 넣기
	private AppConfiguration() {}

	public static AppConfiguration getInstance() {
		return instance;
	}

	// CinemaCache인스턴스 반환 메소드
	public CinemaCache cinemaCache() {
		return CinemaCache.init(
				SeatDAOImpl.getInstance(), RoomDAOImpl.getInstance());
	}

}
