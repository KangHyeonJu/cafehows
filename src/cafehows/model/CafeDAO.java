package cafehows.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class CafeDAO {
	private static final CafeDAO instance = new CafeDAO();
	private final String url = "jdbc:mysql://222.119.100.89:3382/cafehows";
	private final String user = "cafehows";
	private final String password = "codehows213";
	
	private static Connection conn;
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

	public void deleteCustomer(CustomerDTO board, int cno) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE customer SET ")
					.append("visibility=? ")
					.append("WHERE cno=?;")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
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
	
	public void insertCustomer(CustomerDTO customer) {
		try {
			connect();
			sql = "" + 
					"INSERT INTO customer(cno, recdate, visibility) " + 
					"VALUES(?, now(), 1);";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, customer.getCno());
			
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
	
	public void deleteMenu(String menuName) {
		connect();
		try {
			sql = "DELETE FROM menu WHERE mname=?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, menuName);
			pstmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void visibilityMenu(String menuName) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE menu SET ")
					.append("visibility=? ")
					.append("WHERE mname=?;")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setString(2, menuName);
			
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
	
	public MenuDTO getMenuByName(String mname) {
		connect();
		MenuDTO item = new MenuDTO();
		sql = "select * from menu where mname = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mname);
			rs = pstmt.executeQuery();
			if(rs.next()) {
			
				item.setMname(rs.getString(2));
				item.setPrice(rs.getInt(3));			
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return item;
	}
	public CategoryDTO getCategoryBykind(String kind) {
		connect();
		CategoryDTO item = new CategoryDTO();
		sql = "select * from category where kind = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			rs = pstmt.executeQuery();
			if(rs.next()) {
			
				item.setCano(rs.getInt(1));
				item.setKind(rs.getString(2));			
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return item;
	}

	public CategoryDTO getCategoryByCano(int cano) {
		connect();
		CategoryDTO item = new CategoryDTO();
		sql = "select * from category where cano = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cano);
			rs = pstmt.executeQuery();
			if(rs.next()) {
			
				item.setCano(rs.getInt(1));
				item.setKind(rs.getString(2));			
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return item;
	}
	
	public void insertMenu(MenuDTO menu) {
		connect();
		sql = """
				insert into menu (mname,price,cano)
				values (?,?,?)
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, menu.getMname());
			pstmt.setInt(2, menu.getPrice());
			pstmt.setInt(3, menu.getCano());
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"메뉴가 추가되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"메뉴를 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"메뉴를 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
		}
	}
	public void updateCategory(CategoryDTO category) {
		connect();
		sql = """
				update category 
				set kind =? 
				where cano = ? 
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category.getKind());
			pstmt.setInt(2, category.getCano());
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"종류가 수정되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"종류를 수정할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"종류를 수정할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
		}
	}
	public void deleteCategory(int cano) {
		connect();
		sql = "DELETE FROM category WHERE cano=?;";
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cano);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"종류가 삭제되었습니다","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"종류를 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"종류를 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//고객 검색창
	public List<CustomerDTO> searchKeyword(String cno) {
		System.out.println(cno);
		
		connect();
		sql = "select cno, point, recdate from customer where cno like ?";
		List<CustomerDTO> boards = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+cno+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDTO cDTO = new CustomerDTO();
				cDTO.setCno(rs.getInt("cno"));
				cDTO.setPoint(rs.getInt("point"));
				cDTO.setRecdate(rs.getDate("recdate"));
				
				boards.add(cDTO);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return boards;
	}
	
}
