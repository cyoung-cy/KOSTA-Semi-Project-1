package common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import exception.DataAccessException;
import util.DbManager;

public class QueryExecutor {
	
	// 이른 초기화
	// DB 조회가 없을 수 없음. 미리 인스턴스 정적 바인딩.
	private static final QueryExecutor instance = new QueryExecutor();
	
	private QueryExecutor() {}
	
	public static QueryExecutor getInstance() { 
		return instance;
	}

	private Connection getConnection() throws SQLException {
		return DbManager.getConnection();
	}

	// 쿼리와 파라미터만 던지면 알아서 실행하고 영향받은 행 수를 돌려줌
	public int update(String sql, Object... params) {
		int result = 0;
		
		try (Connection conn = getConnection(); 
				PreparedStatement ps = conn.prepareStatement(sql)) {
		
			setParameters(ps, params);

			result = ps.executeUpdate();

		} catch (SQLException e) {
			throw new DataAccessException("업데이트 실행 중 DB 접근 오류발생!", e);
		}

		return result;
	}

	// 트랜잭션용 (외부에서 Connection을 전달받음)
	// Connection을 닫지 않음
	public int update(Connection conn, String sql, Object... params) {
		int result = 0;

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			
			setParameters(ps, params);
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			throw new DataAccessException("트랜젝션 업데이트 실행 중 DB 접근 오류발생!", e);
		}

		return result;
	}
	
	// 생성된 PK를 돌려주는 버전(insert 전용)
	public int insertAndGetPk(String sql, Object... params) {
	    int generatedId = 0;
	    
	    // RETURN_GENERATED_KEYS 옵션을 추가
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        setParameters(ps, params);
	        
	        ps.executeUpdate();
	        
	        // 생성된 키를 결과셋으로 받음
	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                generatedId = rs.getInt(1);
	            }
	        }
	    } catch (SQLException e) {
	        throw new DataAccessException("PK 생성 중 DB 접근 오류 발생! " + e);
	    }
	    return generatedId;
	}
	
	// 트랜잭션용 insertAndGetPk (외부 Connection 사용)
	public int insertAndGetPk(Connection conn, String sql, Object... params) {
	    int generatedId = 0;
	    
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	    	
	        setParameters(ps, params);
	        
	        ps.executeUpdate();
	        
	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                generatedId = rs.getInt(1);
	            }
	        }
	    } catch (SQLException e) {
	        throw new DataAccessException("트랜잭션 PK 생성 중 오류 발생!", e);
	    }
	    return generatedId;
	}
	
	public <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) {
	    List<T> list = new ArrayList<>();
	    
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	       setParameters(ps, params);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                list.add(mapper.mapRow(rs));
	            }
	        }
	    } catch (SQLException e) {
	    	throw new DataAccessException("조회 중 DB 접근 오류 발생! " + e);
	    }
	    return list;
	} 
	
	// 트랜잭션용 다건 조회 (Connection 닫지 않음)
	public <T> List<T> query(Connection conn, String sql, RowMapper<T> mapper, Object... params) {
	    List<T> list = new ArrayList<>();
	    
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	    	
	        setParameters(ps, params);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                list.add(mapper.mapRow(rs));
	            }
	        }
	    } catch (SQLException e) {
	        throw new DataAccessException("트랜잭션 조회 중 DB 접근 오류 발생! " + e);
	    }
	    return list;
	}
	
	public <T> T queryForObject(String sql, RowMapper<T> mapper, Object... params) {
	    List<T> results = query(sql, mapper, params);

	    if (results == null || results.isEmpty()) {
	        throw new DataAccessException("조회 결과가 없습니다.");
	    }

	    if (results.size() > 1) {
	        throw new DataAccessException("조회 결과가 너무 많습니다.");
	    }

	    return results.get(0);
	}
	
	// 트랜잭션용 단건 조회
	public <T> T queryForObject(Connection conn, String sql, RowMapper<T> mapper, Object... params) {
	    List<T> results = query(conn, sql, mapper, params); 

	    if (results == null || results.isEmpty()) {
	        throw new DataAccessException("조회 결과가 없습니다.");
	    }
	    if (results.size() > 1) {
	        throw new DataAccessException("조회 결과가 너무 많습니다.");
	    }

	    return results.get(0);
	}
	
	// 진입메소드 역할(원시형을 반환받기 위한)
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... params) {
	    RowMapper<T> scalarMapper = rs -> {
	        // 결과셋의 첫 번째 컬럼(index 1) 데이터.
	        Object value = rs.getObject(1);
	        
	        if (value == null) return null;

	        // 인자 타입(requiredType)으로 형변환.
	        return requiredType.cast(value);
	    };

	    return queryForObject(sql, scalarMapper, params);
	}
	
	// 트랜잭션용 단건 조회
	public <T> T queryForObject(Connection conn, String sql, Class<T> requiredType, Object... params) {
	    RowMapper<T> scalarMapper = rs -> {
	        Object value = rs.getObject(1);
	        
	        if (value == null) return null;
	        
	        return requiredType.cast(value);
	    };

	    // 위에서 만든 커넥션 받는 queryForObject를 호출!
	    return queryForObject(conn, sql, scalarMapper, params);
	}
	
	// 전체 조회(Set)
	public <T> Set<T> queryForSet(String sql, RowMapper<T> rowMapper, Object... params) {
	    List<T> list = query(sql, rowMapper, params);
	    
	    return new HashSet<>(list);
	}
	
	// 전체 조회(List)
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... params) {
	    List<T> list = query(sql, rowMapper, params);
	    
	    return new ArrayList<>(list);
	}
	
	/**
	 * 파라미터를 홀더에 매핑해주는 메소드
	 */
	private void setParameters(PreparedStatement ps, Object... params) throws SQLException {
	    if (params != null) {
	        for (int i = 0; i < params.length; i++) {
	            ps.setObject(i + 1, params[i]);
	        }
	    }
	}
	
}