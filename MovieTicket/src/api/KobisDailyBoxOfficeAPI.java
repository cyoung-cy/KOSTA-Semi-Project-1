package api;

import dto.MovieAPI;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.JSONArray;
import org.json.JSONObject;
import util.MovieUtils;

import java.util.ArrayList;
import java.util.List;

public class KobisDailyBoxOfficeAPI {

    // KOBIS 오픈 API 인증키
    // api.properties에 키 값 저장
	private static final String KEY = APIKeyConfig.getKey();

    // API 기본 URL
    private static final String BASE_URL =
            "https://kobis.or.kr/kobisopenapi/webservice/rest";

    // 일별 박스오피스 조회 API URL
    private static final String DAILY_BOX_OFFICE_URL =
            BASE_URL + "/boxoffice/searchDailyBoxOfficeList.json";

    // API 한 번 호출 시 가져오는 개수
    private static final int ITEM_PER_PAGE = 10;

    /**
     * 20260315
     * 이예진
     * TODO: targetDt 기준 일별 박스오피스 상위 10개 조회 (개봉일 없는 영화 제외)
     */
    public static List<MovieAPI> getDailyTop10(String targetDt) throws Exception {

        List<MovieAPI> result = new ArrayList<>();

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

            MovieAPI boxOfficeMovie = new MovieAPI(movieId, title, openDt, null, audiAcc);
            result.add(boxOfficeMovie);
        }

        return result;
    }

    private KobisDailyBoxOfficeAPI() {
    }
}