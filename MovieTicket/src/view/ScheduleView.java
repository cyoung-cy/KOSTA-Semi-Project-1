//package view;
//
//import java.util.Scanner;
//
//import controller.RoomController;
//
//public class ScheduleView {
//
//	private static Scanner sc = new Scanner(System.in);
//
//	public static void viewSchedulesByRoom() {
//		System.out.println("\n=============================================================");
//		System.out.println("🗓️  [ 상영관별 스케줄 상세 조회 ]");
//		System.out.println("=============================================================");
//
//		RoomController.getInstance().selectAllRoomsFromCache();
//
//		System.out.print("\n👉 조회를 원하는 [상영관 ID]를 입력하세요: ");
//		try {
//			int roomId = Integer.parseInt(sc.nextLine());
//
//			String roomName = "알 수 없는 관";
//			for (Room r : CinemaCache.getInstance().getRoomMap().values()) {
//				if (r.getRoomId() == roomId) {
//					roomName = r.getName();
//					break;
//				}
//			}
//
//			List<ScheduleDTO> list = SchedulesController.getInstance().getSchedulesListByRoom(roomId);
//
//			printScheduleList(roomName, list);
//
//		} catch (Exception e) {
//			System.out.println("❌ 에러: " + e.getMessage());
//		}
//	}
//
//	private static void printScheduleList(String roomName, List<ScheduleDTO> list) {
//		System.out.println("\n📍 [" + roomName + "]의 상영 일정");
//		System.out.println("-------------------------------------------------------------");
//		if (list.isEmpty()) {
//			System.out.println("   등록된 일정이 없습니다.");
//		} else {
//			System.out.println("ID\t영화 제목\t\t시작 시간\t\t가격");
//			for (ScheduleDTO s : list) {
//				System.out.printf("%d\t%-15s\t%s\t%d\n", s.getScheduleId(), s.getMovieTitle(), s.getStartTime(),
//						s.getPrice());
//			}
//		}
//		System.out.println("-------------------------------------------------------------");
//	}
//}
