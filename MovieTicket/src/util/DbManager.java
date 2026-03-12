package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbManager {
    private static Properties proFile = new Properties();

    /**
     * 로드
     */
    static {
        try {
            // 1. ClassLoader를 사용하여 Classpath(Resources Root) 내의 파일을 찾음
            // 맥, 윈도우 상관없이 'resources' 폴더가 노란색이면 무조건 읽어온다.
        	InputStream is = DbManager.class.getClassLoader().getResourceAsStream("dbinfo.properties");

//        	 proFile.load(new FileInputStream("resources/dbinfo.properties"));
//        	 
//        	 Class.forName(proFile.getProperty("driverName"));
        	 
            		
            if (is == null) {
                // 파일이 없을 경우 예외를 명시적으로 던져서 초기 로딩 실패를 알림
                throw new FileNotFoundException("dbinfo.properties 파일을 찾을 수 없습니다. (Resources Root 설정 확인)");
            }

            proFile.load(is);
            is.close(); // 스트림은 닫아주는 게 예의다.

            // 2. 드라이버 로딩
            Class.forName(proFile.getProperty("driverName"));
            System.out.println("DB 드라이버 로딩 성공!");

        } catch (Exception e) {
            System.err.println("DB 설정 로드 중 치명적 에러 발생!");
            e.printStackTrace();
        }
    }


    public static Properties getProFile() {
        return proFile;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                proFile.getProperty("url"),
                proFile.getProperty("userName"),
                proFile.getProperty("userPass"));
    }


    public static void close(Connection con, Statement st, ResultSet rs) {
        try {
            if(rs != null) rs.close();
            if(st != null) st.close();
            if(con != null) con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
