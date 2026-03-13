package util;

import records.SeatPos;

public class SeatUtil {

    /**
     * "A-10" 형식의 좌석 번호를 2차원 배열 인덱스로 변환
     * @param seatName 예: "A-10"
     * @return int[] {행 인덱스, 열 인덱스} (예: {0, 9})
     */
    public static SeatPos convertToIndices(String seatName) {
    	// 1. 입력값 검증 
    	if (seatName == null || !seatName.contains("-")) {
            return new SeatPos(-1, -1); 
        }
    	
        // 2. 하이픈(-)을 기준으로 분리
        String[] parts = seatName.split("-");
        
        // 3. 행 변환 ('A' -> 0, 'B' -> 1 ...)
        // 알파벳 대문자로 통일 후 - 'A'(ASCII 65).
        char rowChar = parts[0].toUpperCase().charAt(0);
        int rowIndex = rowChar - 'A'; 
        
        // 4. 열 변환 ("10" -> 9)
        int colIndex = Integer.parseInt(parts[1]) - 1;
        
        return new SeatPos(rowIndex, colIndex);
    }

    /**
     * 인덱스를 다시 "A-10" 형식으로 변환
     */
    public static String convertToSeatName(int rowIdx, int colIdx) {
        char rowChar = (char) ('A' + rowIdx);
        int col = colIdx + 1;
        return rowChar + "-" + col;
    }
    
    // 레코드 받을수 있게 오버로딩
    public static String convertToSeatName(SeatPos seatPos) {
        char rowChar = (char) ('A' + seatPos.row());
        int col = seatPos.col() + 1;
        return rowChar + "-" + col;
    }
}