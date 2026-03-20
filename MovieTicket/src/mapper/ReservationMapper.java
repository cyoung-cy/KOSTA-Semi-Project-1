package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Reservation;

public class ReservationMapper implements RowMapper<Reservation> {
    
    private static final ReservationMapper instance = new ReservationMapper();
    
    private ReservationMapper() {}
    
    public static ReservationMapper getInstance() {
        return instance;
    }

    @Override
    public Reservation mapRow(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        
        reservation.setReservationId(rs.getInt("RESERVATION_ID"));
        reservation.setMemberId(rs.getInt("MEMBER_ID"));
        reservation.setScheduleId(rs.getInt("SCHEDULE_ID"));
        reservation.setMovieId(rs.getInt("MOVIE_ID"));
        reservation.setCardInfo(rs.getString("CARD_INFO"));
        reservation.setPaidAt(rs.getTimestamp("PAID_AT"));
        
        return reservation;
    }
}