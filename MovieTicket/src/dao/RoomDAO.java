package dao;

import java.util.Set;

import dto.Room;
import dto.request.RoomCreateRequest;

public interface RoomDAO {
	
    /*
     * 20260312
     * 한상혁
     * TODO:  상영관 등록
     * */
	int insert(RoomCreateRequest roomRequest);

	 /*
     * 20260312
     * 한상혁
     * TODO:  전체 상영관 목록 조회 (관리자용)
     * */
	Set<Room> selectAllRooms();
	
	 /*
     * 20260312
     * 한상혁
     * TODO:  특정 상영관 상세 조회 (ID 기준)
     * */
	Room selectRoomById(int roomId);
	
	 /*
     * 20260312
     * 한상혁
     * TODO:  특정 상영관 상세 조회 (영화 ID 기준)
     * */
	Room selectRoomByMovieId(int movieId);
	
	 /*
     * 20260312
     * 한상혁
     * TODO:  현재 상영 중인 상영관만 조회 (사용자 예매용)
     * */
	Set<Room> selectRoomsByShowing(boolean isShowing);
	
	 /*
     * 20260312
     * 한상혁
     * TODO:  상영관 정보 수정 (이름 변경이나 상영 상태 변경)
     * */
	void updateRoom(Room room);
	
	 /*
     * 20260312
     * 한상혁
     * TODO:  상영관 삭제
     * */
	void deleteRoom(int roomId);
	
	
}
