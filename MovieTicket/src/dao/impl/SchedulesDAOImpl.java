package dao.impl;

import common.jdbc.QueryExecutor;
import dao.SchedulesDAO;
import dto.Schedules;

public class SchedulesDAOImpl implements SchedulesDAO{

	private static final QueryExecutor queryExecutor = QueryExecutor.getInstance();

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
	
	
	
}
