package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;






public class DBConnection {
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 3306;
	private static final String DB_NAME = "tiguera";
	
	public static Connection connect() throws SQLException {
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://" + HOST +":"+ PORT +"/"+DB_NAME, USERNAME, PASSWORD);
			return conn;
			
		} catch (SQLException e) {
			
			return null;
			
		}
		
		
		
	}
	
	public static int check_username(String new_cin) {
		int st=0;				
		try {
			String sql = "SELECT username FROM tiguera WHERE cin=?";
			
			Connection con = DBConnection.connect();
			PreparedStatement stat ;
			stat = con.prepareStatement(sql);
			stat.setString(1 , new_cin);
			st = stat.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return st;
	}
	
	
	
	
	
	
	
	
	
	

}
