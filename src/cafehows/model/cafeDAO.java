package cafehows.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class cafeDAO {
	private static final cafeDAO instance = new cafeDAO();
	private final String url = "jdbc:mysql://222.119.100.89:3382/cafehows";
	private final String user = "cafehows";
	private final String password = "codehows213";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	
	public static cafeDAO getInstance() {
		return instance;
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void close() throws SQLException {
		if ( rs != null ) rs.close();
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	}
	
	
}
