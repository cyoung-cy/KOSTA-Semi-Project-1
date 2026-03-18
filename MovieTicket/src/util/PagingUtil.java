package util;

public class PagingUtil {

    /*
     * 0314
     * 김채영
     * TODO: 문자열을 지정한 출력 너비(칸 수)에 맞게 오른쪽을 공백으로 채워 반환
     *       출력 너비 초과 시 truncateToWidth()로 잘라낸 뒤 패딩 없이 반환
     */
//    public static String padRight(String text, int targetWidth) {
//        if (text == null) text = "";
//        int displayWidth = getDisplayWidth(text);
//        int padding = targetWidth - displayWidth;
//        if (padding < 0) {
//            text = truncateToWidth(text, targetWidth);
//            padding = 0;
//        }
//        return text + " ".repeat(padding);
//    }

    /**
     * 출력 폭 기준으로 오른쪽 패딩
     * */
    // 출력 폭 초과 시 잘라내고, 아니면 오른쪽 공백 채움
    public static String padRight(String text, int width) {
        if (text == null) {
            text = "";
        }

        // 문자 길이 기준으로 잘라냄
        if (text.length() > width) {
            return truncateToWidth(text, width);
        }

        int padding = width - text.length();
        return text + " ".repeat(padding);
    }

    /*
     * 0314
     * 김채영
     * TODO: 문자열의 실제 출력 너비(칸 수)를 계산하여 반환
     *       한글·전각 문자는 2칸, 영문·숫자·특수문자는 1칸으로 집계
     */
//    public static int getDisplayWidth(String text) {
//        if (text == null) return 0;
//
//        int width = 0;
//        for (char ch : text.toCharArray()) {
//            width += isFullWidth(ch) ? 2 : 1;
//        }
//        return width;
//    }


    /*
     * 0314
     * 김채영
     * TODO: 문자가 전각 문자인지 여부를 판별하여 반환
     *       한글 완성형 / 한글 자모 / 한글 호환 자모 / CJK 통합 한자 /
     *       전각 ASCII / 전각 기호 범위에 속하면 true 반환
     */
    private static boolean isFullWidth(char c) {
        return (c >= '\uAC00' && c <= '\uD7A3')
                || (c >= '\u1100' && c <= '\u11FF')
                || (c >= '\u3130' && c <= '\u318F')
                || (c >= '\u4E00' && c <= '\u9FFF')
                || (c >= '\uFF01' && c <= '\uFF60')
                || (c >= '\uFFE0' && c <= '\uFFE6');
    }

    /*
     * 0314
     * 김채영
     * TODO: 문자열이 지정한 출력 너비를 초과할 경우 너비에 맞게 잘라낸 뒤 반환
     *       전각 문자 삽입 시 너비가 홀수 칸 남으면 공백 1개를 추가해 칸 수를 맞춤
     */
    private static String truncateToWidth(String text, int targetWidth) {
        if (text == null) return "";
        if (text.length() <= targetWidth) return text;
        return text.substring(0, targetWidth);
    }

    /*
     * 0314
     * 김채영
     * TODO: 각 컬럼 너비를 받아 테이블 구분선 문자열을 생성하여 반환
     *       컬럼 사이는 "-+-" 로 연결하며 헤더,푸터 구분선으로 사용
     */
    public static String makeSeparator(int idW, int titleW, int genreW, int timeW, int statusW) {
        return "-".repeat(idW) + "-+-" +
                "-".repeat(titleW) + "-+-" +
                "-".repeat(genreW) + "-+-" +
                "-".repeat(timeW) + "-+-" +
                "-".repeat(statusW);
    }
}