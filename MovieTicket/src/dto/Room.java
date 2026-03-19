package dto;

import java.util.List;
import java.util.Set;

public class Room {
	
	private int roomId;
	private String name;
	private Set<Seat> seatSet;
	private Seat[][] layout;
	
	public Room() {}
	
	public Room(int roomId, String name, Set<Seat> seatSet) {
		this.roomId = roomId;
		this.name = name;
		this.seatSet = seatSet;
		this.layout = buildLayout(seatSet);
	}

	// totalSeat 필드 대신 메서드로 제공
    public int getTotalSeat() {
        // seatSet이 null일 경우를 대비한 방어 코드
        return (seatSet == null) ? 0 : seatSet.size();
    }
    
    public void setSeatsAndBuildLayout(Set<Seat> seatSet) {
    	if(seatSet == null || seatSet.isEmpty()) {
    		throw new IllegalArgumentException(this.name + " 상영관에 배치할 좌석 데이터가 없습니다!");
    	}
    	this.seatSet = seatSet;
    	this.layout = buildLayout(seatSet);
    }
	
    // 2차원 배열 레이아웃 빌드 메소드(내부 Set 사용으로 데이터 일관성 유지(동기화), 확장성, 메모리 효율 유리)
    // 내부 메소드
    private Seat[][] buildLayout(Set<Seat> seatSet) {
        if (seatSet == null || seatSet.isEmpty()) return null;

        // 1. 상영관의 최대 행(row)과 열(col) 사이즈 확인
        int maxRow = 0;
        int maxCol = 0;
        for (Seat seat : seatSet) {
            maxRow = Math.max(maxRow, seat.getRowNum());
            maxCol = Math.max(maxCol, seat.getColNum());
        }

        // 2. 알아낸 사이즈로 배열 생성
        Seat[][] newLayout = new Seat[maxRow][maxCol];

        // 3. 배열 자리에 좌석 배정
        for (Seat seat : seatSet) {
            newLayout[seat.getRowNum()-1][seat.getColNum()-1] = seat;
        }

        return newLayout;
    }
    
//    public void displayLayout() {
//    	// 1. 열 번호 가이드 (1, 2, 3...)
//        System.out.print("   ");  // 시작지점 비우는 역할
//        for (int j = 0; j < layout[0].length; j++) {
//            System.out.printf("%3d", j + 1);  // 좌석당 띄어씌기 3칸 확보
//        }
//        System.out.println("\n-------------------------");
//        // 행 탐색
//        for (int i = 0; i < layout.length; i++) {
//        	System.out.print((char)('A' + i) + " |"); // 행 가이드 (A, B...)
//            // 해당 행의 열(col) 탐색
//            for (int j = 0; j < layout[i].length; j++) {
//                
//                Seat seat = layout[i][j]; 
//
//                if (seat == null) {
//                    // 통로 출력역할인데 아직 통로 미존재
//                    System.out.print("   "); 
//                } else {
//                    // 예약 상태에 따라 표시
//                    String mark = seat.isReserved() ? "[X]" : "[O]";
//                    System.out.print(mark);
//                }
//            }
//            System.out.println(); // 줄바꿈
//        }
//    }
    
    // 실시간 예약 반영되는 레이아웃
    public void displaySeatLayout(List<String> reservedNames) {
        System.out.println("\n        		 [ SCREEN ] ");
        System.out.println("    	1  2  3     4  5  6  7     8  9  10"); 
        System.out.println("  --------------------------------");

        for (int row = 1; row <= 10; row++) {
            char rowChar = (char) ('A' + row - 1);
            System.out.print(rowChar + " "); 

            for (int col = 1; col <= 10; col++) {
                String seatName = rowChar + String.valueOf(col);
                String status = reservedNames.contains(seatName) ? "[X]" : "[O]";

                System.out.print(status);

                // 통로 만들기 (3번, 7번 뒤에 공백 추가)
                if (col == 3 || col == 7) {
                    System.out.print("   "); 
                }
            }
            System.out.println(); // 줄바꿈

            // 중간 가로 통로 (E열 뒤에 한 줄 띄우기)
            if (row == 5) {
                System.out.println(); 
            }
        }
    }
    
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Seat> getSeatSet() {
		return seatSet;
	}

	public void setSeatSet(Set<Seat> seatSet) {
		this.seatSet = seatSet;
	}

	public Seat[][] getLayout() {
		return layout;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Room [roomId=");
		builder.append(roomId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", seatSet=");
		builder.append(seatSet);
		builder.append("]");
		return builder.toString();
	} 
    
}
