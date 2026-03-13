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

public class CinemaCache {

	private Map<String, Room> roomMap = new HashMap<>();

	private final SeatDAO seatDAO;
    private final RoomDAO roomDAO;

    private CinemaCache(SeatDAO seatDAO, RoomDAO roomDAO) {
        this.seatDAO = seatDAO;
        this.roomDAO = roomDAO;
        init();
    }

    private static CinemaCache instance;

    public static CinemaCache getInstance() {
    	if(instance == null) {
    		instance = 
    				new CinemaCache(new SeatDAOImpl(), RoomDAOImpl.getInstance());
    	}
        return instance;
    }

    public static CinemaCache getInstance(SeatDAO seatDAO, RoomDAO roomDAO) {
    	if(instance == null) {
    		instance = new CinemaCache(seatDAO, roomDAO);
    	}
        return instance;
    }

    public Map<String, Room> getRoomMap() {
		return roomMap;
	}

	public void setRoomMap(Map<String, Room> roomMap) {
		this.roomMap = roomMap;
	}

	public SeatDAO getSeatDAO() {
		return seatDAO;
	}

	public RoomDAO getRoomDAO() {
		return roomDAO;
	}

	private void init() {
        // DB에서 룸 리스트 로드
    	Set<Room> roomSet =  roomDAO.selectAllRooms();    	
    	for(Room room : roomSet) {
    		Set<Seat> seatSet = (seatDAO.selectSeatsByRoomId(room.getRoomId()));

    		room.setSeatsAndBuildLayout(seatSet);

    		roomMap.put(room.getName(), room);
    	}
    	
        System.out.println("상영관 데이터 캐싱 완료!");
    }

    public Room getRoom(String roomName) {
        return roomMap.get(roomName);
    }
    
}
