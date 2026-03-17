package dao;

import dto.Member;
import dto.Movie;
import dto.Reservation;

import java.util.List;

public interface DashboardDAO {

    /*
     * 0316
     * 김채영
     * TODO: 신규 가입자 및 회원 증감 추이
     * */
    List<Member> user();

    /*
     * 0316
     * 김채영
     * TODO: 영화 장르 선호도
     * */
    List<Member> preferGenre();

    /*
     * 0316
     * 김채영
     * TODO: 영화별 누적 예매 순위 (Top 10)
     * */
    List<Movie> movieTopten();
    int getTotalAudiAcc();

    /*
     * 0316
     * 김채영
     * TODO: 주간 요일별 매출 및 예매 분석
     * */
    List<Reservation> reservationMovie();
}
