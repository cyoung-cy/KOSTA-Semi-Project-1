package common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DbManager;

public abstract class BaseDAO {

	protected Connection getConnection() throws SQLException {
		return DbManager.getConnection();
	}

	// 쿼리와 파라미터만 던지면 알아서 실행하고 영향받은 행 수를 돌려줌
	protected int executeUpdate(String sql, Object... params) {
		int result = 0;

		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// 파라미터 바인딩 노가다 탈출!
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}

			result = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 트랜잭션용 (외부에서 Connection을 전달받음)
	// Connection을 닫지 않음
	protected int executeUpdate(Connection conn, String sql, Object... params) {
		int result = 0;

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	
}