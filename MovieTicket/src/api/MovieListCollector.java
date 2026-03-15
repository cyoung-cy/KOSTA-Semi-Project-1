package api;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dto.Movie;
import util.DbManager;
import util.MovieUtils;
import util.SqlManager;

public class MovieListCollector {
	
	/**
     * 20260315
     * 이예진
     * TODO: 일별 박스오피스 + 상세정보 API를 이용해서 영화 120개 저장
     */

    public static void main(String[] args) {

        java.sql.Connection con = null;
        PreparedStatement ps = null;

        // 배치 오류 발생 시 확인하기 위한 변수 추가
        String lastGenre = null;
        String lastTitle = null;
        String lastTargetDt = null;
        String lastMovieId = null;

        try {
            String sql = SqlManager.get("movie.insert");
            System.out.println("실행 SQL: " + sql);

            // 장르별 남은 목표 개수
            Map<String, Integer> remainGenreCount = MovieUtils.createRemainGenreCount();

            // 중복된 MovieId인지 확인하기 위한 Set
            Set<String> usedMovieIds = new HashSet<>();

            con = DbManager.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql);

            System.out.println("DB URL = " + DbManager.getProFile().getProperty("url"));
            System.out.println("DB USER = " + DbManager.getProFile().getProperty("userName"));

            int savedCount = 0;
            int batchCount = 0;

            // 실행일 기준 하루 전 날짜를 조회 기준일로 설정
            LocalDate searchDate = LocalDate.now().minusDays(1);

            // 영화 개수가 120개이고, 남은 장르가 없을 때까지 루프
            while (savedCount < 120 && !remainGenreCount.isEmpty()) {
                //String targetDt = "20250313";
                String targetDt = MovieUtils.formatTargetDt(searchDate);
                lastTargetDt = targetDt;

                System.out.println();
                System.out.println("==================================================================");
                System.out.println("[조회 시작] 기준 일자: " + targetDt);
                System.out.println("==================================================================");

                List<Movie> dailyMovies = KobisDailyBoxOfficeAPI.getDailyTop20(targetDt);

                for (Movie boxOfficeMovie : dailyMovies) {

                    // 목표 개수를 채우면 종료
                    if (savedCount >= 120 || remainGenreCount.isEmpty()) {
                        break;
                    }

                    if (!boxOfficeMovie.isValidBasic()) {
                        continue;
                    }

                    String movieId = boxOfficeMovie.getMovieId();

                    // 중복된 영화면 continue
                    if (usedMovieIds.contains(movieId)) {
                        continue;
                    }

                    lastMovieId = movieId;
                    lastTitle = boxOfficeMovie.getTitle();

                    Movie detailMovie = KobisMovieInfoAPI.getMovieDetail(movieId);

                    if (!detailMovie.isValidDetail()) {
                        continue;
                    }

                    String genreEnum = MovieUtils.normalizeGenre(detailMovie.getGenre());
                    if (genreEnum == null) {
                        continue;
                    }

                    Integer remainCount = remainGenreCount.get(genreEnum);
                    if (remainCount == null) {
                        continue;
                    }

                    Movie mergedMovie = mergeMovie(boxOfficeMovie, detailMovie);

                    ps.setInt(1, Integer.parseInt(mergedMovie.getMovieId()));
                    ps.setString(2, mergedMovie.getTitle());
                    ps.setString(3, mergedMovie.getActorText());
                    ps.setDate(4, java.sql.Date.valueOf(mergedMovie.getReleaseDate()));
                    ps.setString(5, genreEnum);
                    ps.setInt(6, mergedMovie.getShowTime());
                    ps.setString(7, mergedMovie.getDirector());
                    ps.setInt(8, mergedMovie.getAudiAcc());

                    lastGenre = genreEnum;

                    ps.addBatch();
                    batchCount++;

                    usedMovieIds.add(movieId);
                    savedCount++;

                    System.out.println("[배치 추가] 누적 " + savedCount + "건 | "
                                        + mergedMovie.getTitle() + " | " + genreEnum);

                    remainCount--;

                    if (remainCount == 0) {
                        remainGenreCount.remove(genreEnum);
                        System.out.println("[장르 완료] " + genreEnum + "목표 개수 달성");
                    } else {
                        remainGenreCount.put(genreEnum, remainCount);
                        System.out.println("[장르 현황] " + genreEnum + " 남은 개수: " + remainCount);
                    }

                    // 20건마다 저장
                    if (batchCount == 20) {
                        ps.executeBatch();
                        ps.clearBatch();
                        con.commit();
                        System.out.println("[DB 저장] 20건 완료");

                        batchCount = 0;
                    }


                    System.out.println("[진행] 영화 수집 진행: " + savedCount + " / 120");

                    Thread.sleep(100);
                }

                searchDate = searchDate.minusMonths(1);
            }

            // 남은 배치 처리 추가
            if (batchCount > 0) {
                ps.executeBatch();
                ps.clearBatch();
                con.commit();
                System.out.println("[DB 저장] 남은 " + batchCount + " 건 완료");
            }

            System.out.println("==================================");
            System.out.println("영화 저장 완료");
            System.out.println("[완료] 최종 저장 건수: " + savedCount);

            if (remainGenreCount.isEmpty()) {
                System.out.println("모든 장르 목표 개수 달성");
            } else {
                System.out.println("일부 장르 목표 개수 채우지 못함");
                MovieUtils.printRemainGenreCount(remainGenreCount);
            }

            MovieScreeningUpdater.updateScreeningMovies();

        } catch (Exception e) {
            System.out.println("==================================");
            System.out.println("오류 발생 데이터 확인");
            System.out.println("targetDt: " + lastTargetDt);
            System.out.println("movieId: " + lastMovieId);
            System.out.println("장르: " + lastGenre);
            System.out.println("영화명: " + lastTitle);

            if (con != null) {
                try {
                    con.rollback();
                    System.out.println("오류 발생으로 롤백 처리");
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }

            e.printStackTrace();

        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            DbManager.close(con, ps, null);
        }
    }

    /**
     * 20260312
     * 이예진
     * TODO: 영화 기본 정보와 세부 정보 merge
     */
    private static Movie mergeMovie(Movie boxOfficeMovie, Movie detailMovie) {
        Movie mergedMovie = new Movie(
                boxOfficeMovie.getMovieId(),
                boxOfficeMovie.getTitle(),
                boxOfficeMovie.getOpenDate(),
                detailMovie.getGenre(),
                boxOfficeMovie.getAudiAcc()
        );

        mergedMovie.setDirector(detailMovie.getDirector());
        mergedMovie.setShowTime(detailMovie.getShowTime());
        mergedMovie.setActor1(detailMovie.getActor1());
        mergedMovie.setActor2(detailMovie.getActor2());
        mergedMovie.setActor3(detailMovie.getActor3());

        return mergedMovie;
    }
}