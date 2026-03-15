package enums;

public enum MovieCategory {
    액션, 애니메이션, 스릴러, 호러, 코미디, 로맨스, 다큐, 드라마, 판타지;

    // 카테고리 String을 받아서 검증하는 메소드
    public static String validate(String s) {
        String formatted = null;
        try {
            // 입력이 null이거나 빈 문자열인 경우 예외 처리
            if (s == null || s.isBlank()) {
                throw new IllegalArgumentException();
            }
            formatted = s.trim().toUpperCase();
            MovieCategory.valueOf(formatted); // 유효한 카테고리인지 검증
            return formatted;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("["+formatted+"](은)는 "+"유효하지 않은 장르입니다. 다시 입력해주세요.");
        }
    }
}
