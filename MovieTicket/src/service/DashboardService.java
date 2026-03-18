package service;

import dao.DashboardDAO;
import dao.impl.DashboardDAOImpl;
import dto.Member;
import dto.Movie;
import dto.Reservation;
import dto.WeeklyStat;

import java.util.List;

public class DashboardService {
    DashboardDAO dashboardDAO = new DashboardDAOImpl();

    /*
     * 0316
     * 김채영
     * TODO: 신규 가입자 및 회원 증감 추이 서비스
     * */
    public List<Member> user() {
        return dashboardDAO.user();
    }

    /*
     * 0316
     * 김채영
     * TODO: 영화 장르 선호도 통계 서비스
     * */
    public List<Member> preferGenre() {
        return dashboardDAO.preferGenre();
    }

    /*
     * 0316
     * 김채영
     * TODO: 영화별 누적 예매 순위 (Top 10) 서비스
     * */
    public List<Movie> movieTopten() {
        return dashboardDAO.movieTopten();
    }

    public int getTotalAudiAcc() {
        return dashboardDAO.getTotalAudiAcc();
    }

    /*
     * 0316
     * 김채영
     * TODO: 주간 요일별 매출 및 예매 분석 서비스
     * */
    public List<WeeklyStat> reservationMovie() {
        return dashboardDAO.reservationMovie();
    }
}
