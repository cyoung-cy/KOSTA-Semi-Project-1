package api;

import dto.Movie;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.JSONArray;
import org.json.JSONObject;
import util.MovieUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class KobisMovieInfoAPI {

    // KOBIS 오픈 API 인증키
    // api.properties에 키 값 저장
    private static String KEY;

    static {
        try {
            Properties prop = new Properties();

            InputStream is = KobisMovieInfoAPI.class
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

    // 영화 상세정보 조회 API URL
    private static final String MOVIE_INFO_URL =
            BASE_URL + "/movie/searchMovieInfo.json";

    /**
     * 20260312
     * 이예진
     * TODO: 영화 상세정보 조회
     */
    public static Movie getMovieDetail(String movieId) {

        Movie detail = new Movie();

        try {
            String url = MOVIE_INFO_URL
                    + "?key=" + KEY
                    + "&movieCd=" + movieId;

            Connection.Response response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .execute();

            JSONObject root = new JSONObject(response.body());
            JSONObject movieInfoResult = root.optJSONObject("movieInfoResult");
            if (movieInfoResult == null) {
                return detail;
            }

            JSONObject movieInfo = movieInfoResult.optJSONObject("movieInfo");
            if (movieInfo == null) {
                return detail;
            }

            detail.setMovieId(movieId);

            //String openDt = movieInfo.optString("openDt", "").trim();
            String openDt = movieInfo.optString("openDt", "").trim().replace("-", "");
            if (MovieUtils.isValidDate(openDt)) {
                detail.setOpenDate(openDt);
            }

            String showTmStr = movieInfo.optString("showTm", "").trim();
            if (!showTmStr.isBlank() && showTmStr.matches("\\d+")) {
                detail.setShowTime(Integer.parseInt(showTmStr));
            }

            /*String genreAlt = movieInfo.optString("genreAlt", "").trim();
            if (!genreAlt.isBlank() && !MovieUtils.containsAdultGenre(genreAlt)) {
                detail.setGenre(genreAlt);
            }*/

            JSONArray genres = movieInfo.optJSONArray("genres");
            if (genres != null && !genres.isEmpty()) {
                StringBuilder genreText = new StringBuilder();

                for (int i = 0; i < genres.length(); i++) {
                    JSONObject genreObj = genres.optJSONObject(i);
                    if (genreObj == null) {
                        continue;
                    }

                    String genreNm = genreObj.optString("genreNm", "").trim();
                    if (genreNm.isBlank()) {
                        continue;
                    }

                    if (genreText.length() > 0) {
                        genreText.append(", ");
                    }
                    genreText.append(genreNm);
                }

                String genreValue = genreText.toString();
                if (!genreValue.isBlank() && !MovieUtils.containsAdultGenre(genreValue)) {
                    detail.setGenre(genreValue);
                }
            }


            JSONArray directors = movieInfo.optJSONArray("directors");
            if (directors != null && !directors.isEmpty()) {
                JSONObject directorObj = directors.optJSONObject(0);
                if (directorObj != null) {
                    detail.setDirector(directorObj.optString("peopleNm", "").trim());
                }
            }

            JSONArray actors = movieInfo.optJSONArray("actors");
            if (actors != null) {
                Set<String> actorSet = new LinkedHashSet<>();

                for (int i = 0; i < actors.length(); i++) {
                    JSONObject actorObj = actors.optJSONObject(i);
                    if (actorObj == null) {
                        continue;
                    }

                    String actorName = actorObj.optString("peopleNm", "").trim();
                    if (!actorName.isBlank()) {
                        actorSet.add(actorName);
                    }

                    if (actorSet.size() >= 3) {
                        break;
                    }
                }

                List<String> actorList = new ArrayList<>(actorSet);
                if (actorList.size() >= 3) {
                    detail.setActor1(actorList.get(0));
                    detail.setActor2(actorList.get(1));
                    detail.setActor3(actorList.get(2));
                }
            }

        } catch (Exception e) {
            System.out.println("상세조회 실패 movieCd = " + movieId);
        }

        return detail;
    }

    private KobisMovieInfoAPI() {
    }
}