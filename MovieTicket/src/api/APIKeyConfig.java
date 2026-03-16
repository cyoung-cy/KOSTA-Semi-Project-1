package api;

import java.io.InputStream;
import java.util.Properties;

public class APIKeyConfig {

    private static final String KEY;

    static {
        try {
            Properties prop = new Properties();
            InputStream is = APIKeyConfig.class.getClassLoader().getResourceAsStream("api.properties");

            if (is == null) {
                throw new RuntimeException("api.properties 파일을 찾을 수 없습니다.");
            }

            prop.load(is);
            is.close();

            String key = prop.getProperty("kobis.key");
            if (key == null || key.isBlank()) {
                throw new RuntimeException("kobis.key 값을 확인하세요.");
            }

            KEY = key;

        } catch (Exception e) {
            throw new RuntimeException("KOBIS API 설정 로드 실패", e);
        }
    }

    public static String getKey() {
        return KEY;
    }

    private APIKeyConfig() {
    }
}