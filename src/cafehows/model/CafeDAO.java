package cafehows.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CafeDAO {
	private static final CafeDAO instance = new CafeDAO();
	private final String url = "jdbc:mysql://222.119.100.89:3382/cafehows";
	private final String user = "cafehows";
	private final String password = "codehows213";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String sql;
	
	public static CafeDAO getInstance() {
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


	public List<MenuDTO> getItems(int cano) {
		connect();
		sql = "select * from menu where cano = ? order by mno ";
		List<MenuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cano);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO item = new MenuDTO();
				item.setMname(rs.getString(2));
				item.setPrice(rs.getInt(3));
				item.setCano(rs.getInt(5));
				
				String sql2 = "select * from category where cano = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(5));
				
				item.setKind(rs.getString(1));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public List<MenuDTO> getMDSItems() {
		connect();
		sql = "select * from menu  order by mno ";
		List<MenuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO item = new MenuDTO();
				item.setMname(rs.getString(2));
				item.setPrice(rs.getInt(3));
				item.setCano(rs.getInt(5));
				
				String sql2 = "select kind from category where cano = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(5));
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setKind(rs2.getString(1));}
				else {}
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	
	public List<CategoryDTO> getCategoryItems() {
		connect();
		sql = "select * from category order by cano ";
		List<CategoryDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CategoryDTO item = new CategoryDTO();
				item.setCano(rs.getInt(1));
				item.setKind(rs.getString(2));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	public List<CustomerDTO> getCustomerItems() {
		connect();
		sql = "select * from customer order by cno ";
		List<CustomerDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDTO item = new CustomerDTO();
				item.setCno(rs.getInt(1));
				item.setPoint(rs.getInt(2));
				item.setRecdate(rs.getDate(3));
				item.setVisibility(rs.getInt(4));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public List<OrderDTO> getOrderItems() {
		connect();
		sql = "select * from orderlist order by ono ";
		List<OrderDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO item = new OrderDTO();
				item.setOno(rs.getInt(1));
				item.setCno(rs.getInt(2));
				item.setDate(rs.getDate(3));
				item.setPrice(rs.getInt(4));
				item.setFinalprice(rs.getInt(5));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public void updatePoint(CustomerDTO board, int cno) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE customer SET ")
					.append("point=? ")
					.append("WHERE cno=?;")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getPoint());
			pstmt.setInt(2, cno);
			
			pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteOrder(int ono) {
		connect();
		try {
			sql = "DELETE FROM orderlist WHERE ono=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ono);
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void deleteCustomer(int cno) {
		connect();
		try {
			sql = "DELETE FROM customer WHERE cno=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
