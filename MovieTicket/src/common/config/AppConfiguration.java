package common.config;

import cache.CinemaCache;
import controller.SchedulesController;
import dao.ReservationDAO;
import dao.ReservationInfoDAO;
import dao.impl.MemberDAOImpl;
import dao.impl.MovieDAOImpl;
import dao.impl.ReservationDAOImpl;
import dao.impl.ReservationInfoDAOImpl;
import dao.impl.RoomDAOImpl;
import dao.impl.SchedulesDAOImpl;
import dao.impl.SeatDAOImpl;
import service.ReservationService;
import service.RoomService;
import service.SchedulesService;
import service.SeatService;

public class AppConfiguration {
	
	private static final AppConfiguration instance = new AppConfiguration();

	// 생성자에 주입 코드 넣기
	private AppConfiguration() {
		SeatService.init(SeatDAOImpl.getInstance(), RoomDAOImpl.getInstance());
		RoomService.init(SeatDAOImpl.getInstance(), RoomDAOImpl.getInstance());
		SchedulesService.init(SchedulesDAOImpl.getInstance(), new MovieDAOImpl(), RoomDAOImpl.getInstance());
		ReservationService.init(ReservationDAOImpl.getInstance(), ReservationInfoDAOImpl.getInstance(),
    			SchedulesDAOImpl.getInstance(), new MemberDAOImpl(), SeatDAOImpl.getInstance());
		CinemaCache.init(
				SeatDAOImpl.getInstance(), RoomDAOImpl.getInstance());
//		System.out.println("서비스 싱글톤 인스턴스 주입 완료");
	}

	public static AppConfiguration getInstance() {
		return instance;
	}

//	// CinemaCache인스턴스 반환 메소드
//	public CinemaCache cinemaCache() {
//		return CinemaCache.init(
//				SeatDAOImpl.getInstance(), RoomDAOImpl.getInstance());
//	}

}
