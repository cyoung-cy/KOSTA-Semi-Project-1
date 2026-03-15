package api;

import dto.Movie;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.JSONArray;
import org.json.JSONObject;
import util.MovieUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class KobisDailyBoxOfficeAPI {

    // KOBIS 오픈 API 인증키
    // api.properties에 키 값 저장
    private static String KEY;

    static {
        try {
            Properties prop = new Properties();

            InputStream is = KobisDailyBoxOfficeAPI.class
                    .getClassLoader()
                    .getResourceAsStream("api.properties");

            if (is == null) {
                throw new RuntimeException("api.properties 파일을 찾을 수 없습니다.");
            }

            prop.load(is);
            is.close();

            KEY = prop.getProperty("kobis.key");

            if (KEY == null || KEY.isBlank()) {
                System.out.println("KOBIS API KEY 로드 실패: api.properties의 kobis.key 값을 확인하세요.");
            }
        } catch (Exception e) {
            System.out.println("api.properties 파일 로드 실패");
            e.printStackTrace();
        }
    }

    // API 기본 URL
    private static final String BASE_URL =
            "https://kobis.or.kr/kobisopenapi/webservice/rest";

    // 일별 박스오피스 조회 API URL
    private static final String DAILY_BOX_OFFICE_URL =
            BASE_URL + "/boxoffice/searchDailyBoxOfficeList.json";

    // API 한 번 호출 시 가져오는 개수
    private static final int ITEM_PER_PAGE = 20;

    /**
     * 20260315
     * 이예진
     * TODO: targetDt 기준 일별 박스오피스 상위 20개 조회 (개봉일 없는 영화 제외)
     */
    public static List<Movie> getDailyTop20(String targetDt) throws Exception {

        List<Movie> result = new ArrayList<>();

        String url = DAILY_BOX_OFFICE_URL
                + "?key=" + KEY
                + "&targetDt=" + targetDt
                + "&itemPerPage=" + ITEM_PER_PAGE;

        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();

        //System.out.println("요청 URL: " + url);
        //System.out.println("응답: " + response.body());

        JSONObject root = new JSONObject(response.body());
        JSONObject boxOfficeResult = root.optJSONObject("boxOfficeResult");

        if (boxOfficeResult == null) {
            System.out.println("boxOfficeResult가 null입니다.");
            return result;
        }

        JSONArray dailyList = boxOfficeResult.optJSONArray("dailyBoxOfficeList");
        if (dailyList == null || dailyList.isEmpty()) {
            System.out.println("dailyBoxOffice가 비어 있습니다.");
            return result;
        }

        for (int i = 0; i < dailyList.length(); i++) {
            JSONObject movie = dailyList.optJSONObject(i);
            if (movie == null) {
                continue;
            }

            String movieId = movie.optString("movieCd", "").trim();
            String title = movie.optString("movieNm", "").trim();
            //String openDt = movie.optString("openDt", "").trim(); // RELEASE_DATE
            String openDt = movie.optString("openDt", "").trim().replace("-", "");
            String audiAccStr = movie.optString("audiAcc", "").trim();

            if (movieId.isBlank() || !movieId.matches("\\d+")) {
                continue;
            }

            if (title.isBlank()) {
                continue;
            }

            if (!MovieUtils.isValidDate(openDt)) {
                continue;
            }

            if (audiAccStr.isBlank() || !audiAccStr.matches("\\d+")) {
                continue;
            }

            int audiAcc = Integer.parseInt(audiAccStr);

            Movie boxOfficeMovie = new Movie(movieId, title, openDt, null, audiAcc);
            result.add(boxOfficeMovie);
        }

        return result;
    }

    private KobisDailyBoxOfficeAPI() {
    }
}