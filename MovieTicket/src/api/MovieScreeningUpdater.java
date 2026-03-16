package api;

import dto.MovieAPI;
import util.DbManager;
import util.MovieUtils;
import util.SqlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;


public class MovieScreeningUpdater {

    /**
     * 20260315
     * 이예진
     * TODO:실행일 기준 하루 전 일별 박스오피스 Top20 영화 중 DB에 존재하는 영화의 IS_SCREENING 값을 TRUE로 업데이트
     * */
    public static void updateScreeningMovies() {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            String sql = SqlManager.get("movie.update.is_screening");

            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);

            LocalDate yesterday = LocalDate.now().minusDays(1);
            String targetDt = MovieUtils.formatTargetDt(yesterday);

            System.out.println();
            System.out.println("======================================");
            System.out.println("[상영중 업데이트] 기준일자: " + targetDt);
            System.out.println("======================================");

            List<MovieAPI> dailyMovies = KobisDailyBoxOfficeAPI.getDailyTop20(targetDt);

            int updateCount = 0;

            for (MovieAPI movie : dailyMovies) {
                String movieId = movie.getMovieId();

                ps.setInt(1, Integer.parseInt(movieId));

                int result = ps.executeUpdate();

                if (result > 0) {
                    updateCount++;
                    System.out.println("[상영중] " + movieId + " | " + movie.getTitle());
                }
            }

            System.out.println("--------------------------------------");
            System.out.println("[완료] 상영중 업데이트 영화 수: " + updateCount);

        } catch (Exception e) {
            System.out.println("[오류] 상영중 업데이트 실패");
            e.printStackTrace();
        } finally {
            DbManager.close(con, ps, null);
        }
    }

    private MovieScreeningUpdater() {
    }
}