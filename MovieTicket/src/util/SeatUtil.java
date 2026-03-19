package util;

import records.SeatPos;

public class SeatUtil {

    /**
     * "A-10" 형식의 좌석 번호를 2차원 배열 인덱스로 변환
     * @param seatName 예: "A10"
     * @return SeatPos {row: 0, col: 9}
     */
    public static SeatPos convertToIndices(String seatName) {
    	// 1. 입력값 검증 
    	if (seatName == null || seatName.length() < 2) {
            return new SeatPos(-1, -1); 
        }
    	
    	// 2. 행 변환: 첫 번째 글자 ('A' -> 0)
        char rowChar = Character.toUpperCase(seatName.charAt(0));
        int rowIndex = rowChar - 'A'; 
        
        // 3. 열 변환: 두 번째 글자부터 끝까지 ("10" -> 9)
        int colIndex = Integer.parseInt(seatName.substring(1)) - 1;
        
        return new SeatPos(rowIndex, colIndex);
    }

    /**
     * 인덱스를 다시 "A10" 형식으로 변환
     */
    public static String convertToSeatName(int rowIdx, int colIdx) {
        char rowChar = (char) ('A' + rowIdx);
        int col = colIdx + 1;
        return String.valueOf(rowChar) + col; // "A" + 10 -> "A10"
    }
    
    // 레코드 받을수 있게 오버로딩
    public static String convertToSeatName(SeatPos seatPos) {
    	return convertToSeatName(seatPos.row(), seatPos.col());
    }
}