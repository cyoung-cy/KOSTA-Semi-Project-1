package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import common.jdbc.RowMapper;
import dto.Schedules;

public class SchedulesMapper implements RowMapper<Schedules> {
    
    private static final SchedulesMapper instance = new SchedulesMapper();
    
    private SchedulesMapper() {}
    
    public static SchedulesMapper getInstance() {
        return instance;
    }

    @Override
    public Schedules mapRow(ResultSet rs) throws SQLException {
        Schedules schedules = new Schedules();
        
        schedules.setScheduleId(rs.getInt("SCHEDULE_ID"));
        schedules.setStartTime(rs.getTimestamp("START_TIME"));
        schedules.setRoomId(rs.getInt("ROOM_ID"));
        schedules.setMovieId(rs.getInt("MOVIE_ID"));
        
        return schedules;
    }
}