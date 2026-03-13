package dto;

import java.util.Set;

public class Room {
	
	private int roomId;
	private int movieId;
	private boolean isShowing;
	private String name;
	private Set<Seat> seatSet;
	private Seat[][] layout;
	
	public Room() {}
	
	public Room(int roomId, int movieId, boolean isShowing, String name, Set<Seat> seatSet) {
		this.roomId = roomId;
		this.movieId = movieId;
		this.isShowing = isShowing;
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
            newLayout[seat.getRowNum()][seat.getColNum()] = seat;
        }

        return newLayout;
    }
    
    public void displayLayout() {
    	// 1. 열 번호 가이드 (1, 2, 3...)
        System.out.print("   ");  // 시작지점 비우는 역할
        for (int j = 0; j < layout[0].length; j++) {
            System.out.printf("%3d", j + 1);  // 좌석당 띄어씌기 3칸 확보
        }
        System.out.println("\n-------------------------");
        // 행 탐색
        for (int i = 0; i < layout.length; i++) {
        	System.out.print((char)('A' + i) + " |"); // 행 가이드 (A, B...)
            // 해당 행의 열(col) 탐색
            for (int j = 0; j < layout[i].length; j++) {
                
                Seat seat = layout[i][j]; 

                if (seat == null) {
                    // 통로 출력역할인데 아직 통로 미존재
                    System.out.print("   "); 
                } else {
                    // 예약 상태에 따라 표시
                    String mark = seat.isReserved() ? "[X]" : "[O]";
                    System.out.print(mark);
                }
            }
            System.out.println(); // 줄바꿈
        }
    }
    
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(Boolean isShowing) {
		this.isShowing = isShowing;
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
		builder.append(", movieId=");
		builder.append(movieId);
		builder.append(", isShowing=");
		builder.append(isShowing);
		builder.append(", Name=");
		builder.append(name);
		builder.append(", seatSet=");
		builder.append(seatSet);
		builder.append("]");
		return builder.toString();
	} 
    
}
