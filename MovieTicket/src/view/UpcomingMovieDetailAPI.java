package view;

import api.APIKeyConfig;
import dto.Movie;
import dto.MovieAPI;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Scanner;

public class UpcomingMovieDetailAPI {

	// KOBIS 오픈 API 인증키
    // api.properties에 키 값 저장
	private static final String KEY = APIKeyConfig.getKey();

    private static final String BASE_URL =
            "https://kobis.or.kr/kobisopenapi/webservice/rest";

    private static final String MOVIE_INFO_URL =
            BASE_URL + "/movie/searchMovieInfo.json";

    /**
     * 20260316
     * 이예진
     * TODO: 개봉예정영화 세부정보 조회
     */
    public static MovieAPI getUpcomingMovieDetail(MovieAPI selectedMovie) throws Exception {

        String url = MOVIE_INFO_URL
                + "?key=" + KEY
                + "&movieCd=" + selectedMovie.getMovieId();

        Connection.Response response = Jsoup.connect(url)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .execute();

        JSONObject root = new JSONObject(response.body());

        JSONObject movieInfoResult = root.optJSONObject("movieInfoResult");
        if (movieInfoResult == null) {
            throw new RuntimeException("movieInfoResult가 없습니다.");
        }

        JSONObject movieInfo = movieInfoResult.optJSONObject("movieInfo");
        if (movieInfo == null) {
            throw new RuntimeException("movieInfo가 없습니다.");
        }

        MovieAPI movie = new MovieAPI();

        // UpcomingMovieAPI에서 가져온 기본정보
        movie.setMovieId(selectedMovie.getMovieId());
        movie.setTitle(selectedMovie.getTitle());
        movie.setOpenDate(selectedMovie.getOpenDate());
        movie.setGenre(selectedMovie.getGenre());

        // 감독 1명
        JSONArray directors = movieInfo.optJSONArray("directors");
        if (directors != null && directors.length() > 0) {
            JSONObject directorObj = directors.optJSONObject(0);
            if (directorObj != null) {
                movie.setDirector(directorObj.optString("peopleNm", "").trim());
            }
        }

        // 배우 3명
        JSONArray actors = movieInfo.optJSONArray("actors");
        if (actors != null) {
            if (actors.length() > 0 && actors.optJSONObject(0) != null) {
                movie.setActor1(actors.optJSONObject(0).optString("peopleNm", "").trim());
            }
            if (actors.length() > 1 && actors.optJSONObject(1) != null) {
                movie.setActor2(actors.optJSONObject(1).optString("peopleNm", "").trim());
            }
            if (actors.length() > 2 && actors.optJSONObject(2) != null) {
                movie.setActor3(actors.optJSONObject(2).optString("peopleNm", "").trim());
            }
        }

        // 상영시간
        String showTm = movieInfo.optString("showTm", "0").trim();
        if (showTm.matches("\\d+")) {
            movie.setShowTime(Integer.parseInt(showTm));
        }

        return movie;
    }

    public static Movie showUpcomingMovieDetail(List<MovieAPI> movies) throws Exception {

        Scanner sc = new Scanner(System.in);
        ConsoleUI.blank(1);
        ConsoleUI.printHeader("개봉예정작 상세조회", null, ConsoleUI.RED, ConsoleUI.YELLOW);

        String inputId = ConsoleUI.prompt(sc, "상세조회할 영화 ID를 입력하세요").trim();

        MovieAPI selectedMovie = null;
        for (MovieAPI movie : movies) {
            if (movie.getMovieId().equals(inputId)) {
                selectedMovie = movie;
                break;
            }
        }

        if (selectedMovie == null) {
            ConsoleUI.alert("목록에 없는 영화 ID입니다.");
            return null;
        }

        ConsoleUI.info("상세정보를 불러옵니다...");
        MovieAPI detailMovie = getUpcomingMovieDetail(selectedMovie);

        ConsoleUI.blank(1);
        ConsoleUI.printHeader("개봉예정작 상세정보", null, ConsoleUI.RED, ConsoleUI.YELLOW);

        // Movie 객체 반환받도록 수정
        return printUpcomingMovieDetail(detailMovie);
    }

    public static Movie printUpcomingMovieDetail(MovieAPI movie) {
        System.out.println("제목 : " + nullToBlank(movie.getTitle()));
        System.out.println("배우 : " + movie.getActorText());
        System.out.println("개봉일 : " + formatDate(movie.getOpenDate()));
        System.out.println("장르 : " + nullToBlank(movie.getGenre()));
        System.out.println("상영시간 : " + (movie.getShowTime() == null ? 0 : movie.getShowTime()));
        System.out.println("감독 : " + nullToBlank(movie.getDirector()));

        String openDate  = formatDate(movie.getOpenDate());
        String genre     = nullToBlank(movie.getGenre());
        int showTime     = (movie.getShowTime() == null ? 0 : movie.getShowTime());
        String director  = nullToBlank(movie.getDirector());

        // Movie 객체 반환받도록 수정
        return AdminView.insertMovieAuto(
                movie.getTitle(), movie.getActorText(), openDate, genre, showTime, director);
    }

    private static String formatDate(String openDate) {
        if (openDate == null || !openDate.matches("\\d{8}")) {
            return "";
        }

        return openDate.substring(0, 4) + "-"
                + openDate.substring(4, 6) + "-"
                + openDate.substring(6, 8);
    }

    private static String nullToBlank(String str) {
        return str == null ? "" : str;
    }

    private UpcomingMovieDetailAPI() {
    }
}