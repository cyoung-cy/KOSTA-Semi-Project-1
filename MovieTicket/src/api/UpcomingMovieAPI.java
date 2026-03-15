package api;

import dto.Movie;
import util.MovieUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Comparator;

public class UpcomingMovieAPI {

    private static String KEY;

    static {
        try {
            Properties prop = new Properties();

            InputStream is = api.UpcomingMovieAPI.class
                    .getClassLoader()
                    .getResourceAsStream("api.properties");

            if (is == null) {
                throw new RuntimeException("api.properties 파일을 찾을 수 없습니다.");
            }

            prop.load(is);
            is.close();

            KEY = prop.getProperty("kobis.key");

            if (KEY == null || KEY.isBlank()) {
                throw new RuntimeException("kobis.key 값을 확인하세요.");
            }

        } catch (Exception e) {
            System.out.println("api.properties 로드 실패");
            e.printStackTrace();
        }
    }

    private static final String BASE_URL =
            "https://kobis.or.kr/kobisopenapi/webservice/rest";

    private static final String MOVIE_LIST_URL =
            BASE_URL + "/movie/searchMovieList.json";

    /**
     * 20260316
     * 이예진
     * TODO: 개봉예정 영화 목록 조회
     * prdtStatNm == "개봉예정" 인 영화만 반환
     */
    public static List<Movie> getUpcomingMovies(int startYear, int endYear) throws Exception {

        List<Movie> result = new ArrayList<>();
        int curPage = 1;
        int itemPerPage = 100;

        while (true) {
            String url = MOVIE_LIST_URL
                    + "?key=" + KEY
                    + "&curPage=" + curPage
                    + "&itemPerPage=" + itemPerPage
                    + "&openStartDt=" + startYear
                    + "&openEndDt=" + endYear;

            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            JSONObject root = new JSONObject(response.body());
            JSONObject movieListResult = root.optJSONObject("movieListResult");
            if (movieListResult == null) {
                break;
            }

            JSONArray movieArray = movieListResult.optJSONArray("movieList");
            if (movieArray == null || movieArray.isEmpty()) {
                break;
            }

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movieObj = movieArray.optJSONObject(i);
                if (movieObj == null) {
                    continue;
                }

                String movieId = movieObj.optString("movieCd", "").trim();
                String title = movieObj.optString("movieNm", "").trim();
                String openDt = movieObj.optString("openDt", "").trim().replace("-", "");
                String prdtStatNm = movieObj.optString("prdtStatNm", "").trim();
                String genreAlt = movieObj.optString("genreAlt", "").trim();

                String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);

                String genreEnum = MovieUtils.normalizeGenre(genreAlt);
                if (genreEnum == null) {
                    continue;
                }

                if (movieId.isBlank() || !movieId.matches("\\d+")) {
                    continue;
                }
                if (title.isBlank()) {
                    continue;
                }

                // 개봉예정작만 필터링
                if (!"개봉예정".equals(prdtStatNm)) {
                    continue;
                }

                // 개봉예정일이 과거로 되어있는 데이터 필터링
                if (!openDt.isBlank() && openDt.compareTo(today) < 0){
                    continue;
                }

                //Movie movie = new Movie(movieId, title, openDt, genreAlt, null);
                Movie movie = new Movie(movieId, title, openDt, genreEnum, null);
                result.add(movie);
            }

            if (movieArray.length() < itemPerPage) {
                break;
            }

            curPage++;
            Thread.sleep(100);
        }

        // 개봉일 빠른순 정렬
        result.sort(Comparator.comparing(Movie::getOpenDate));

        return result;
    }

    private UpcomingMovieAPI() {
    }
}