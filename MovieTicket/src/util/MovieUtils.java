package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class MovieUtils {

    private MovieUtils() {
    }

    /**
     * 20260312
     * 이예진
     * TODO: yyyyMMdd 형식인지 검사
     */
    public static boolean isValidDate(String yyyymmdd) {
        return yyyymmdd != null && yyyymmdd.matches("\\d{8}");
    }

    /**
     * 20260312
     * 이예진
     * TODO: 장르유형 성인물 여부 검사
     */
    public static boolean containsAdultGenre(String genre) {
        return genre != null && genre.contains("성인물(에로)");
    }

    /**
     * 20260315
     * 이예진
     * TODO: LocalDate를 yyyyMMdd로 변환
     * */
    public static String formatTargetDt(LocalDate date){
        return date.format(DateTimeFormatter.BASIC_ISO_DATE);
    }
    
    
    /**
     * 20260313
     * 이예진
     * TODO: 장르명을 DB용 장르명으로 변환하기 위한 Map 설정
     * 공포 -> 호러, 멜로 -> 로맨스로 통일
     * */
    private static final Map<String, String> genreMap = new LinkedHashMap<>();

    static {
    	genreMap.put("드라마", "드라마");
    	
        genreMap.put("공포", "호러");
        genreMap.put("호러", "호러");
        genreMap.put("멜로", "로맨스");
        genreMap.put("로맨스", "로맨스");

        genreMap.put("액션", "액션");
        genreMap.put("애니메이션", "애니메이션");
        genreMap.put("스릴러", "스릴러");
        genreMap.put("코미디", "코미디");
        genreMap.put("판타지", "판타지");
    }

    /**
     * 20260313
     * 이예진
     * TODO: 상세정보 API에서 받은 장르명을 DB용 장르명으로 변환
     */
    public static String normalizeGenre(String genre) {

        if (genre == null || genre.isBlank()) {
            return null;
        }

        String[] genres = genre.split("[,/|]");

        for (String g : genres) {

            String token = g.trim();

            for (Map.Entry<String, String> entry : genreMap.entrySet()) {

                if (token.contains(entry.getKey())) {
                    return entry.getValue();
                }

            }
        }

        return null;
    }

    /**
     * 20260313
     * 이예진
     * TODO: 장르별 목표 개수 생성(드라마 20개, 나머지 장르 10개, 판타지 장르 추가)
     */
    public static Map<String, Integer> createRemainGenreCount() {
        Map<String, Integer> remainGenreCount = new LinkedHashMap<>();

        remainGenreCount.put("드라마", 30);
        
        remainGenreCount.put("액션", 20);
        remainGenreCount.put("애니메이션", 20);

        remainGenreCount.put("스릴러", 10);
        remainGenreCount.put("호러", 10);
        remainGenreCount.put("코미디", 10);
        remainGenreCount.put("로맨스", 10);
        remainGenreCount.put("판타지", 10);

        return remainGenreCount;
    }


    /**
     * 20260312
     * 이예진
     * TODO: 현재 남은 장르 목표 출력(각 장르 목표 개수 채우기 실패 시 출력)
     */
    public static void printRemainGenreCount(Map<String, Integer> remainGenreCount) {
        System.out.println("===== 남은 장르 개수 =====");
        for (Map.Entry<String, Integer> entry : remainGenreCount.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

}