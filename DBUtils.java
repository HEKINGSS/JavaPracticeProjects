package com.king.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBUtils {
	private DBUtils(){}//µ¥ÀýÄ£Ê½
	private static String DRIVER;
	private static String URL;
	private static String USERNAME;
	private static String PWD;
	private static ResourceBundle rb=ResourceBundle.getBundle("com.king.jdbc.jdbc");
	
	static {
		DRIVER = rb.getString("driver");
		URL = rb.getString("url");
		USERNAME = rb.getString("name");
		PWD = rb.getString("pwd");
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		Connection conn=null;
		
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	 
	//Statement   PreparedStatement  Call
	public static void close(Connection conn, Statement stmt, ResultSet rs){
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(stmt != null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
