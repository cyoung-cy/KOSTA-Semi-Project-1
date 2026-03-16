package common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import exception.DataAccessException;
import util.DbManager;

public abstract class BaseDAO {

	protected Connection getConnection() throws SQLException {
		return DbManager.getConnection();
	}

	// 쿼리와 파라미터만 던지면 알아서 실행하고 영향받은 행 수를 돌려줌
	protected int update(String sql, Object... params) {
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
	protected int update(Connection conn, String sql, Object... params) {
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
	protected int insertAndGetPk(String sql, Object... params) {
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
	        throw new DataAccessException("업데이트v2 실행 중 DB 접근 오류 발생! " + e);
	    }
	    return generatedId;
	}
	
	protected <T> List<T> query(String sql, RowMapper<T> mapper, Object... params) {
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