package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.ReservationInfo;

public class ReservationInfoMapper implements RowMapper<ReservationInfo> {
    
    private static final ReservationInfoMapper instance = new ReservationInfoMapper();
    
    private ReservationInfoMapper() {}
    
    public static ReservationInfoMapper getInstance() {
        return instance;
    }

    @Override
    public ReservationInfo mapRow(ResultSet rs) throws SQLException {
        ReservationInfo info = new ReservationInfo();
        
        info.setReservationInfoId(rs.getInt("RESERVATION_INFO_ID"));
        info.setReservationId(rs.getInt("RESERVATION_ID"));
        info.setSeatId(rs.getInt("SEAT_ID"));
        info.setCategory(rs.getString("CATEGORY"));
        info.setPrice(rs.getInt("PRICE"));
        
        return info;
    }
}