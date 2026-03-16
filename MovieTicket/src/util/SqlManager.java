package util;

import java.io.InputStream;
import java.util.Properties;

public class SqlManager {

    private static final Properties sql = new Properties();

    static {
        try {
            InputStream is = SqlManager.class.getClassLoader().getResourceAsStream("sql.properties");

            if (is == null) {
                throw new RuntimeException("sql.properties 파일을 찾을 수 없습니다.");
            }

            sql.load(is);
            is.close();

        } catch (Exception e) {
            System.out.println("sql.properties 로드 실패");
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return sql.getProperty(key);
    }
}