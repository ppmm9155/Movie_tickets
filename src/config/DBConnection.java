package config;

import java.io.InputStream;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";

	String id = "";
	String pw = "";

	public DBConnection() {
		try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
			Properties prop = new Properties();
			if (input != null) {
				prop.load(input);
				this.id = prop.getProperty("db.id");
				this.pw = prop.getProperty("db.pw");

			} else {
				System.err.println("db.properties 파일없음");
			}
		} catch (Exception e) {
			System.err.println("파일 읽기 실패");
			e.printStackTrace();
		}
		
		try {
			Class.forName(driver);
//			System.out.println("드라이버 로드");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}



	public Connection getConniction() throws SQLException {
		return DriverManager.getConnection(url, id, pw);
	}
		

}
