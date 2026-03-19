package dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import common.jdbc.QueryExecutor;
import dao.SchedulesDAO;
import dto.Schedules;
import mapper.SchedulesMapper;

public class SchedulesDAOImpl implements SchedulesDAO{

	private static final QueryExecutor queryExecutor = QueryExecutor.getInstance();
	
	private static final SchedulesMapper schedulesMapper = SchedulesMapper.getInstance();

	private static final SchedulesDAOImpl instance = new SchedulesDAOImpl();

	private SchedulesDAOImpl() {}

	public static SchedulesDAOImpl getInstance() {
		return instance;
	}

	@Override
    public void insert(Schedules schedules) {
        String sql = "insert into SCHEDULES(ROOM_ID, MOVIE_ID, START_TIME) VALUES (?, ?, ?)";
        
        Object[] params = { 
        		schedules.getRoomId(), schedules.getMovieId(), schedules.getStartTime()
				};
        
        queryExecutor.update(sql, params);
    }
	
	@Override
	public boolean checkOverlap(int roomId, Timestamp start, Timestamp end) {
	    String sql = "select count(*) " +
	                 "from SCHEDULES " +
	                 "where ROOM_ID = ? " +
	                 "  and ? < date_add(START_TIME, interval (select SCREENING_TIME + 20 from MOVIE where MOVIE_ID = SCHEDULES.MOVIE_ID) minute) " +
	                 "  and ? > START_TIME";
	    
	    Long count = queryExecutor.queryForObject(sql, Long.class, roomId, start, end);
	    
	    // count가 null이 아니고 0보다 크면 중복 발생
	    return count != null && count > 0;
	}
	
	@Override
	public List<Schedules> selectAvailableByMovieId(int movieId) {
		String sql = "select * from SCHEDULES " +
                "where MOVIE_ID = ? and START_TIME > current_timestamp + interval 15 minute " +
                "order by START_TIME asc";
                
		Object[] params = { movieId };
		
		return queryExecutor.query(sql, schedulesMapper, params);
	}
	
	@Override
	public Schedules selectOneById(Connection conn, int scheduleId) {
	    String sql = "select * from SCHEDULES where SCHEDULE_ID = ?";
	    
	    Object[] params = { scheduleId };
	    return queryExecutor.queryForObject(conn, sql, schedulesMapper, params);
	}
	
}
