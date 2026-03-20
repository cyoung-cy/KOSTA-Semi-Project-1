package common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ResultSet의 한 행(Row)을 객체 T로 매핑하기 위한 인터페이스
 */
@FunctionalInterface
public interface RowMapper<T> {
    // ResultSet에서 데이터를 꺼내 T 객체에 담아 리턴하는 추상 메서드
    T mapRow(ResultSet rs) throws SQLException;
}
