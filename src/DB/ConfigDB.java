package DB;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConfigDB {

	private static Connection conn = null;

	public static Connection startDataBase() {

		try {
			if (conn == null) {
				Properties prop = loadProperties();
				String url = prop.getProperty("dburl");

				conn = DriverManager.getConnection(url, prop);
			}
			return conn;
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeDataBase(Connection conn) {
		
		try {
			if (conn != null) {
				conn.close();
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		
		try {
			if (rs != null) {
				rs.close();
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		
		try {
			if (st != null) {
				st.close();
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closePreparedStatement(PreparedStatement ps) {
		
		try {
			if (ps != null) {
				ps.close();
			}
		}
		catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	private static Properties loadProperties() {

		try (FileInputStream fs = new FileInputStream("db.properties")) {

			Properties promp = new Properties();
			promp.load(fs);

			return promp;
		} catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}
}
