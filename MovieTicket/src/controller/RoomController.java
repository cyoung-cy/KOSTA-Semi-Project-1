package controller;

import java.util.Map;

import cache.CinemaCache;
import dto.Room;
import service.RoomService;

public class RoomController {
	
	private static final RoomController instance = new RoomController();
    
    private final RoomService roomService = RoomService.getInstance();

    private RoomController() {}

    public static RoomController getInstance() {
        return instance;
    }
    
    public void selectAllRoomsFromCache() {
        // 캐시에서 데이터 꺼내기
        Map<String, Room> roomMap = CinemaCache.getInstance().getRoomMap();

        if (roomMap == null || roomMap.isEmpty()) {
            System.out.println("등록된 상영관 정보가 없습니다.");
            return;
        }

        System.out.println("\n[ 상영관 목록 ]");
        for (Room room : roomMap.values()) {
            System.out.printf("📍 ID: %d | 이름: %-5s | 총 좌석: %d석\n", 
                room.getRoomId(), room.getName(), room.getSeatSet().size());
        }
    }
    
}
