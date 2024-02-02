package cafehows.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BoardDTO;



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
	public List<menuDTO> getItems(int cano) {
		connect();
		sql = "select * from menu where cano = ? order by mno ";
		List<menuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cano);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				menuDTO item = new menuDTO();
				item.setMname(rs.getString(2));
				item.setPrice(rs.getInt(3));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	
	
}
