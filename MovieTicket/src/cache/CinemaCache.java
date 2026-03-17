package cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.RoomDAO;
import dao.SeatDAO;
import dao.impl.RoomDAOImpl;
import dao.impl.SeatDAOImpl;
import dto.Room;
import dto.Seat;
import exception.AppConfigException;

public class CinemaCache {

	private Map<String, Room> roomMap = new HashMap<>();

	private final SeatDAO seatDAO;
    private final RoomDAO roomDAO;

    private CinemaCache(SeatDAO seatDAO, RoomDAO roomDAO) {
        this.seatDAO = seatDAO;
        this.roomDAO = roomDAO;
        reloadData();
    }

    private static CinemaCache instance;
    
    // init을 통해서만 생성 (Config용)
    public static CinemaCache init(SeatDAO seatDAO, RoomDAO roomDAO) {
        if (instance == null) 
            instance = new CinemaCache(seatDAO, roomDAO);
        
        return instance;
    }

    // 이미 생성된 인스턴스를 가져오는 용도
    public static CinemaCache getInstance() {
        if (instance == null) 
            throw new AppConfigException("CinemaCache가 아직 초기화되지 않았습니다! AppConfiguration을 먼저 확인하세요.");
        
        return instance;
    }
    
	private void reloadData() {
        // DB에서 룸 리스트 로드
    	Set<Room> roomSet =  roomDAO.selectAllRooms();    	
    	for(Room room : roomSet) {
    		Set<Seat> seatSet = (seatDAO.selectSeatsByRoomId(room.getRoomId()));

    		room.setSeatsAndBuildLayout(seatSet);

    		roomMap.put(room.getName(), room);
    	}
    	
        System.out.println("상영관 데이터 캐싱 완료!");
    }

    public Map<String, Room> getRoomMap() {
		return roomMap;
	}

	public void setRoomMap(Map<String, Room> roomMap) {
		this.roomMap = roomMap;
	}

    public Room getRoom(String roomName) {
        return roomMap.get(roomName);
    }
    
}
