package controller;

import dto.Member;
import dto.Movie;
import dto.Reservation;
import service.DashboardService;
import view.DashboardView;
import view.FailView;

import java.util.List;

public class DashboardController {
    static DashboardService dashboardService = new DashboardService();
    /*
     * 0316
     * 김채영
     * TODO: 신규 가입자 및 회원 증감 추이
     * */
    public static void user() {
        try {
            List<Member> list = dashboardService.user();  // 반환값 받기
            DashboardView.user(list);                       // View에 전달
        }catch (Exception e){
            FailView.errorMessage(e.getMessage());
        }
    }

    /*
     * 0316
     * 김채영
     * TODO: 영화 장르 선호도
     * */
    public static void preferGenre() {
        try {
            List<Member> list = dashboardService.preferGenre();
            DashboardView.preferGenre(list);
        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
        }
    }

    /*
     * 0316
     * 김채영
     * TODO: 영화별 누적 예매 순위 (Top 10)
     * */
    public static void movieTopten() {
        try {
            List<Movie> topList = dashboardService.movieTopten();
            int totalAudiAcc = dashboardService.getTotalAudiAcc();
            DashboardView.movieTopten(topList, totalAudiAcc);
        }catch (Exception e){
            FailView.errorMessage(e.getMessage());
        }
    }

    /*
     * 0316
     * 김채영
     * TODO: 주간 요일별 매출 및 예매 분석
     * */
    public static void reservationMovie() {
        try {
            List<Reservation> list = dashboardService.reservationMovie();
            DashboardView.reservationMovie(list);
        }catch (Exception e){
            FailView.errorMessage(e.getMessage());
        }
    }
}
