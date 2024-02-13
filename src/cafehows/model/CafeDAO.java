package cafehows.model;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.exceptions.RSAException;


public class CafeDAO {
	private static final CafeDAO instance = new CafeDAO();
	//private final String url = "jdbc:mysql://222.119.100.89:3382/cafehows?autoReconnect=true";
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
		sql = "select * from menu where visibility=1 && cano = ? order by mno ";
		List<MenuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cano);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO item = new MenuDTO();
				item.setMname(rs.getString(2));
				item.setPrice(rs.getInt(3));
				item.setVisibility(rs.getInt(4));
				item.setCano(rs.getInt(5));
				item.setIce(rs.getInt(6));
				item.setIceChangeable(rs.getInt(7));
				
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
	
	public MenuDTO getMenuByName(String mname) {
		connect();
		MenuDTO item = new MenuDTO();
		sql = "select * from menu where mname = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mname);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				item.setMno(rs.getInt(1));
				item.setMname(rs.getString(2));
				item.setPrice(rs.getInt(3));
				item.setVisibility(rs.getInt(4));
				item.setCano(rs.getInt(5));
				item.setIce(rs.getInt(6));
				item.setIceChangeable(rs.getInt(7));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return item;
	}
	
	public List<MenuDTO> getMenuSales() {
		connect();
		sql = """
				select mno,sum(count) 
				from menusales 
				group by mno
				""";
		List<MenuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO item = new MenuDTO();
				item.setMno(rs.getInt(1));
				item.setCumCount(rs.getInt(2));
				String sql2 = "select mname from menu where mno = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(1));
				
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setMname(rs2.getString(1));}
				else {}
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	public List<MenuDTO> getDailyMenuSales() {
		connect();
		sql = """
				select date(date),mno,sum(count) 
				from menusales 
				group by mno,date;
		
				""";
		List<MenuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO item = new MenuDTO();
				item.setDate(rs.getDate(1));
				item.setMno(rs.getInt(2));
				item.setCumCount(rs.getInt(3));
				String sql2 = "select mname from menu where mno = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(2));
				
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setMname(rs2.getString(1));}
				else {}
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	public List<MenuDTO> getWeeklyMenuSales() {
		connect();
		sql = """
				SELECT DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-1) DAY), '%Y-%m-%d') as start,
				DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-7) DAY), '%Y-%m-%d') as end,
				DATE_FORMAT(date, '%Y%U') AS date,
				mno,
				sum(count)
				FROM menusales
				GROUP BY date,mno;
		
				""";
		List<MenuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
		//	pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO item = new MenuDTO();
				item.setStartdate(rs.getDate(1));
				item.setEnddate(rs.getDate(2));
				item.setMno(rs.getInt(4));
				item.setCumCount(rs.getInt(5));
				String sql2 = "select mname from menu where mno = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(4));
				
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setMname(rs2.getString(1));}
				else {}
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}	public List<MenuDTO> getMonthlyMenuSales() {
		connect();
		sql = """
				SELECT MONTH(date) AS date,mno,
				sum(count)
				FROM menusales
				GROUP BY date,mno
				""";
		List<MenuDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
		//	pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO item = new MenuDTO();
				item.setMonth(rs.getString(1));
				item.setMno(rs.getInt(2));
				item.setCumCount(rs.getInt(3));
				String sql2 = "select mname from menu where mno = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(2));
				
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setMname(rs2.getString(1));}
				else {}
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}
//	public List<MenuDTO> getDailyMenuSales() {
//		connect();
//		sql = """
//				select date(date),mno,sum(count) 
//				from menusales 
//				group by mno,date;
//		
//				""";
//		List<MenuDTO> items = new ArrayList<>();
//		try {
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				MenuDTO item = new MenuDTO();
//				item.setDate(rs.getDate(1));
//				item.setMno(rs.getInt(2));
//				item.setCumCount(rs.getInt(3));
//				String sql2 = "select mname from menu where mno = ? ";
//				pstmt = conn.prepareStatement(sql2);
//				pstmt.setInt(1, rs.getInt(2));
//				
//				ResultSet rs2 = pstmt.executeQuery();
//				if(rs2.next()) {
//				item.setMname(rs2.getString(1));}
//				else {}
//				items.add(item);
//			}
//			close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return items;
//	}
	
	
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
				item.setVisibility(rs.getInt(4));
				item.setCano(rs.getInt(5));
								
				String sql2 = "select kind from category where cano = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(5));
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setKind(rs2.getString(1));}
				
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
				item.setVisibility(rs.getInt(3));
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
		sql = "select * from customer where visibility=1 order by cno ";
		List<CustomerDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDTO item = new CustomerDTO();
				item.setCno(rs.getInt(1));
				item.setPhoneNumber(rs.getString(2));
				item.setPoint(rs.getInt(3));
				item.setRecdate(rs.getDate(4));
				item.setVisibility(rs.getInt(5));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	

	public CustomerDTO getCustomerItemByCno(String phone) {
		connect();
		CustomerDTO item = new CustomerDTO();
		sql = "select * from customer where phonenumber=? and visibility=1";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phone);
			rs = pstmt.executeQuery();
			int rows = pstmt.executeUpdate();
			if(rs.next()) {
				item.setCno(rs.getInt(1));
				item.setPhoneNumber(rs.getString(2));
				item.setPoint(rs.getInt(3));
				item.setRecdate(rs.getDate(4));
				item.setVisibility(rs.getInt(5));
				
			}else {
				JOptionPane.showMessageDialog(null,"존재하지 않는 회원입니다.","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public CustomerDTO getCustomerItemByCnoAI(int cno) {
		connect();
		CustomerDTO item = new CustomerDTO();
		sql = "select * from customer where cno=? and visibility=1";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, cno);
			rs = pstmt.executeQuery();
			int rows = pstmt.executeUpdate();
			if(rs.next()) {
				item.setCno(rs.getInt(1));
				item.setPhoneNumber(rs.getString(2));
				item.setPoint(rs.getInt(3));
				item.setRecdate(rs.getDate(4));
				item.setVisibility(rs.getInt(5));
				
			}else {
				JOptionPane.showMessageDialog(null,"존재하지 않는 회원입니다.","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	
	public List<CustomerDTO> getCustomerState() {
		connect();
		sql = "select * from customer order by cno ";
		List<CustomerDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDTO item = new CustomerDTO();
				item.setCno(rs.getInt(1));
				item.setPhoneNumber(rs.getString(2));
				item.setPoint(rs.getInt(3));
				item.setRecdate(rs.getDate(4));
				item.setVisibility(rs.getInt(5));

				items.add(item);

			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return items;
	}

	public List<CustomerDTO> getRdcDate() {
		connect();
		sql = "select * from customer where visibility=1 && datediff(now(), recdate) >= 365 order by cno ";
		List<CustomerDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDTO item = new CustomerDTO();
				item.setCno(rs.getInt(1));
				item.setPhoneNumber(rs.getString(2));
				item.setPoint(rs.getInt(3));
				item.setRecdate(rs.getDate(4));
				item.setVisibility(rs.getInt(5));
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
	public OrderDTO getOrderItembyOno(int ono) {
		connect();
		OrderDTO item = new OrderDTO();
		sql = "select * from orderlist where ono=? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ono);
			rs = pstmt.executeQuery();
			if(rs.next()) {
			
				item.setOno(rs.getInt(1));
				item.setCno(rs.getInt(2));
				item.setDate(rs.getDate(3));
				item.setPrice(rs.getInt(4));
				item.setFinalprice(rs.getInt(5));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	public List<OrderDTO> getOrderItemsbyPeriod(String start,String end){
		connect();
		sql="""
			SELECT *
				FROM orderlist
				WHERE DATE(date) >= STR_TO_DATE(?, '%Y-%m-%d')
				AND DATE(date) <= STR_TO_DATE(?, '%Y-%m-%d')
				""";
		List<OrderDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
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
	//DailySalesTable
	public List<OrderDTO> getDailySales(){
		connect();
		sql = """
				select ono,date,sum(price),sum(finalprice) 
				from orderlist 
				group by date;
				""";
		List<OrderDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO item = new OrderDTO();
				item.setOno(rs.getInt(1));
				item.setDate(rs.getDate(2));
				item.setPrice(rs.getInt(3));
				item.setFinalprice(rs.getInt(4));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
		
	}
	//DailySalesTable
	public int getMonthlySales(int year,int month){
		connect();
		int monthlySales=0;
		sql = """
			select sum(finalprice) from orderlist 
			where year(date)=? and month(date)=? 
			group by date_format(date,'%Y-%m')
				""";
		List<OrderDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				monthlySales =rs.getInt(1);
				
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return monthlySales;
		
	}
	
	
//	public int getWeeklyPayrollCost(int year,int month){
//		connect();
//		int monthlyPayrollCost=0;
//		sql = """
//				SELECT DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-2) DAY), '%Y/%m/%d') as start,
//				DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-8) DAY), '%Y/%m/%d') as end,
//				DATE_FORMAT(date, '%Y%U') AS date,
//				sum(hour*wage)
//				FROM employeehour
//				GROUP BY eno
//				;
//				""";
//		List<OrderDTO> items = new ArrayList<>();
//		try {
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, year);
//			pstmt.setInt(2, month);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				monthlySales =rs.getInt(1);
//				
//			}
//			close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return monthlySales;
//		
//	}
//	
//	
	public int getMonthlyPayrollCost(int year,int month){
		connect();
		int monthlyPayrollCost=0;
		sql="""
			select 
			sum(totalmoney)+sum(e.forty+e.underforty) 
				 from 
				   (SELECT   eno,
				     DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-1) DAY), '%Y-%m-%d') as start,
				      DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-7) DAY), '%Y-%m-%d') as end,
				      DATE_FORMAT(date, '%Y%U') AS datecolumn,
				      sum(hour*wage) as totalMoney,
				      sum(hour) as totalHour,
				      if(sum(hour) >= 40, 8*wage, 0) as forty,
				      if(sum(hour)<15,0,if(sum(hour)<40,sum(hour)/5*wage,0)) as underforty
				 FROM employeehour
				 GROUP BY  DATE_FORMAT(date, '%Y%U'),eno) as e
				where year(e.end)=? and month(e.end)=?
                GROUP BY datecolumn,eno
				with rollup;
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, month);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				monthlyPayrollCost =rs.getInt(1);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return monthlyPayrollCost;
		
	}

	
	
	public List<OrderDTO> getDailySalesbyPeriod(String start,String end){
		connect();
		
		sql = """
				select date,sum(price),sum(finalprice) 
				from orderlist
				WHERE DATE(date) >= STR_TO_DATE(?, '%Y-%m-%d')
				AND DATE(date) <= STR_TO_DATE(?, '%Y-%m-%d')
                group by date;
				""";
		List<OrderDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO item = new OrderDTO();
				item.setDate(rs.getDate(1));
				item.setPrice(rs.getInt(2));
				item.setFinalprice(rs.getInt(3));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
		
	}
	//Employee
	
	public List<EmployeeDTO> getEmployeeItems() {
		connect();
		sql = "select * from employee order by eno ";
		List<EmployeeDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				EmployeeDTO item = new EmployeeDTO();
				item.setEno(rs.getInt(1));
				item.setEname(rs.getString(2));
				item.setStatus(rs.getInt(3));
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public EmployeeDTO getEmployeeByEno(int eno) {
		connect();
		EmployeeDTO item = new EmployeeDTO();
		sql = "select *  from employee where eno = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, eno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				
				item.setEno(rs.getInt(1));
				item.setEname(rs.getString(2));
				item.setStatus(rs.getInt(3));
				
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return item;
	}
	
	public List<EmployeeDTO> getEmployeeHourItems() {
		connect();
		sql = "select * from employeehour order by eno ";
		List<EmployeeDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				EmployeeDTO item = new EmployeeDTO();
				item.setEno(rs.getInt(1));
				item.setDate(rs.getDate(2));
				item.setHour(rs.getInt(3));
				item.setWage(rs.getInt(4));
				String sql2 = "select ename from employee where eno=? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(1));
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setEname(rs2.getString(1));}
				
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public List<EmployeeDTO> getEmployeeHourItemsbyPeriod(String start, String end){
		connect();
		sql="""
				select * 
				from employeehour 
			    WHERE DATE(date) >= STR_TO_DATE(?, '%Y-%m-%d')
				AND DATE(date) <= STR_TO_DATE(?, '%Y-%m-%d')
				""";
		
		sql = "select * from employeehour where date between ? and ?";
		List<EmployeeDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				EmployeeDTO item = new EmployeeDTO();
				item.setEno(rs.getInt(1));
				item.setDate(rs.getDate(2));
				item.setHour(rs.getInt(3));
				item.setWage(rs.getInt(4));
				String sql2 = "select ename from employee where eno=? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(1));
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setEname(rs2.getString(1));}
				
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
//	public List<EmployeeDTO> getEmployeeWageItems() {
//		connect();
//		sql = """
//				select eno,startdate,enddate,sum(wage),sum(holidaypay)
//				from employeewage
//				group by startdate,eno
//				with rollup
//				""";
//	
//		List<EmployeeDTO> items = new ArrayList<>();
//		try {
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				EmployeeDTO item = new EmployeeDTO();
//				item.setEno(rs.getInt(1));
//				item.setStartDate(rs.getDate(2));
//				item.setEndDate(rs.getDate(3));
//				item.setWage(rs.getInt(4));
//				item.setHolidayPay(rs.getInt(5));
//				item.setTotalSalary(item.getWage()+item.getHolidayPay());
//				
//				String sql2 = "select ename from employee where eno=? ";
//				pstmt = conn.prepareStatement(sql2);
//				pstmt.setInt(1, rs.getInt(1));
//				ResultSet rs2 = pstmt.executeQuery();
//				if(rs2.next()) {
//				item.setEname(rs2.getString(1));}
//				
//				items.add(item);
//			}
//			close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return items;
//	}
	
	public List<EmployeeDTO> getEmployeeWageItems() {
		connect();
		sql = """
				select e.eno, e.start, e.end, 
				sum(totalmoney),sum(e.forty+e.underforty) as addMoney 
				 from 
				   (SELECT   eno,
				     DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-1) DAY), '%Y-%m-%d') as start,
				      DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-7) DAY), '%Y-%m-%d') as end,
				      DATE_FORMAT(date, '%Y%U') AS datecolumn,
				      sum(hour*wage) as totalMoney,
				      sum(hour) as totalHour,
				      if(sum(hour) >= 40, 8*wage, 0) as forty,
				      if(sum(hour)<15,0,if(sum(hour)<40,sum(hour)/5*wage,0)) as underforty
				 FROM employeehour
				 GROUP BY  DATE_FORMAT(date, '%Y%U'),eno) as e
				GROUP BY datecolumn,eno
				with rollup;
				""";
	
		List<EmployeeDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				EmployeeDTO item = new EmployeeDTO();
				item.setEno(rs.getInt(1));
				item.setStartDate(rs.getDate(2));
				item.setEndDate(rs.getDate(3));
				item.setWage(rs.getInt(4));
				item.setHolidayPay(rs.getInt(5));
				item.setTotalSalary(item.getWage()+item.getHolidayPay());
				
				String sql2 = "select ename from employee where eno=? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(1));
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setEname(rs2.getString(1));}
				
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
//	public List<EmployeeDTO> getEmployeeWageItems() {
//		connect();
//		sql = """
//				SELECT eno,
//				DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-1) DAY), '%Y-%m-%d') as start,
//				DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-7) DAY), '%Y-%m-%d') as end,
//				DATE_FORMAT(date, '%Y%U') AS datecolumn,
//				sum(hour*wage),sum(hour),8*wage,sum(hour)/5*wage
//				FROM employeehour
//				GROUP BY datecolumn,eno
//				with rollup
//				""";
//	
//		List<EmployeeDTO> items = new ArrayList<>();
//		try {
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				if(rs.getInt(1)==0) continue;
//				EmployeeDTO item = new EmployeeDTO();
//				item.setEno(rs.getInt(1));
//				item.setStartDate(rs.getDate(2));
//				item.setEndDate(rs.getDate(3));
//				item.setWage(rs.getInt(5));
//				item.setHour(rs.getInt(6));
//				if(rs.getInt(6)>=40) item.setHolidayPay(rs.getInt(7));
//				else {item.setHolidayPay(rs.getInt(8));}
//				item.setTotalSalary(item.getWage()+item.getHolidayPay());
//				
//				String sql2 = "select ename from employee where eno=? ";
//				pstmt = conn.prepareStatement(sql2);
//				pstmt.setInt(1, rs.getInt(1));
//				ResultSet rs2 = pstmt.executeQuery();
//				if(rs2.next()) {
//				item.setEname(rs2.getString(1));}
//				
//				items.add(item);
//			}
//			close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return items;
//	}
//	
	
	
	public List<EmployeeDTO> getEmployeeWageItemsbyPeriod(String start, String end) {
		connect();
//		
		sql = """
				select e.eno, e.start, e.end, 
				sum(totalmoney),sum(e.forty+e.underforty) as addMoney 
				 from 
				  (SELECT   eno,
				    DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-1) DAY), '%Y-%m-%d') as start,
				     DATE_FORMAT(DATE_SUB(date, INTERVAL (DAYOFWEEK(date)-7) DAY), '%Y-%m-%d') as end,
				     DATE_FORMAT(date, '%Y%U') AS datecolumn,
				     sum(hour*wage) as totalMoney,
				     sum(hour) as totalHour,
				     if(sum(hour) >= 40, 8*wage, 0) as forty,
				     if(sum(hour)<15,0,if(sum(hour)<40,sum(hour)/5*wage,0)) as underforty
				 FROM employeehour
				 GROUP BY  DATE_FORMAT(date, '%Y%U'),eno) as e
                 WHERE (STR_TO_DATE(?,'%Y-%m-%d') between DATE(e.start) and DATE(e.end)) 
				or (STR_TO_DATE(?,'%Y-%m-%d') between DATE(e.start) and DATE(e.end)) 
				GROUP BY datecolumn,eno
				with rollup;
				""";

		List<EmployeeDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, start);
			pstmt.setString(2, end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				EmployeeDTO item = new EmployeeDTO();
				item.setEno(rs.getInt(1));
				item.setStartDate(rs.getDate(2));
				item.setEndDate(rs.getDate(3));
				item.setWage(rs.getInt(4));
				item.setHolidayPay(rs.getInt(5));
				item.setTotalSalary(item.getWage()+item.getHolidayPay());
				
				String sql2 = "select ename from employee where eno=? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(1));
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) {
				item.setEname(rs2.getString(1));}
				
				items.add(item);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	
	
	public void updateEmployee(EmployeeDTO employee) {
		connect();
		sql = """
				update employee 
				set  ename=?, status=?  
				where eno = ? 
				""";
		try {
			pstmt.setString(1, employee.getEname());
			pstmt.setInt(2, employee.getStatus());
			pstmt.setInt(3, employee.getEno());
			int rows = pstmt.executeUpdate();
	
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"근로자가 수정되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"근로자를 수정할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"근로자를 수정할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			
		}
	}
	public int insertEmployee(EmployeeDTO employee) {
		int eno = 0;
		connect();
		sql = """
				insert into employee (ename,status) 
				values (?,?)
				""";
		try {
			pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, employee.getEname());
			pstmt.setInt(2, employee.getStatus());
			int rows = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			if(rs.next()) {
				eno = rs.getBigDecimal(1).intValue();
				
			}
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"직원번호"+eno+"인 근로자가 추가되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"근로자를 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
			return eno;
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"근로자를 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			return eno;
		}
	}

	
	public void insertEmployeeHour(EmployeeDTO employee) {
		connect();
		sql = """
				insert into employeehour (eno,date,hour,wage)
				values (?,?,?,?)
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, employee.getEno());
			pstmt.setDate(2, employee.getDate());
			pstmt.setInt(3, employee.getHour());
			pstmt.setInt(4, employee.getWage());
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"근로시간이 추가되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"근로시간을 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"근로시간을 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void updateEmployeeHour(EmployeeDTO employee) {
		connect();
		sql = """
				update employeehour 
				set  hour=?, wage=?  
				where eno = ? and date =? 
				""";
		try {
			pstmt.setInt(1, employee.getHour());
			pstmt.setInt(2, employee.getWage());
			pstmt.setInt(3, employee.getEno());
			pstmt.setDate(4, employee.getDate());
			
			int rows = pstmt.executeUpdate();
	
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"근로시간이 수정되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"근로시간을 수정할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
			
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"근로시간을 수정할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			
		}
	}
	
	public void updatePoint(CustomerDTO board, String phone) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE customer SET ")
					.append("recdate = now(),")
					.append("point=? ")
					.append("WHERE phonenumber=? and visibility=1")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getPoint());
			pstmt.setString(2, phone);
			
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
	
	
	//refund

	public void deleteOrder(int ono) {
		connect();
		sql = "DELETE FROM orderlist WHERE ono=? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ono);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"주문이 삭제되었습니다","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"주문을 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"주문을 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
		}
	}
	

	public void deleteMenuSales(int ono) {
		connect();
		sql = "DELETE FROM menusales WHERE ono=? ";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ono);
			pstmt.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"판매량을 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
		//	int rows = pstmt.executeUpdate();
//			if(rows == 1) {
//				JOptionPane.showMessageDialog(null,"판매량이 삭제되었습니다","확인",JOptionPane.PLAIN_MESSAGE);
//			}else {
//				JOptionPane.showMessageDialog(null,"판매량 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
//			}
		}
	}
	
	
	
	public void deleteCustomer(String phonenumber) {
		connect();
		try {
			sql = "update customer set"
					+"visibility = 0"
					+"where phonenumber=? and visibility =1";
			
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setString(1, phonenumber);
			
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
					"INSERT INTO customer(phonenumber, recdate, visibility) " + 
					"VALUES(?, now(), 1);";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, customer.getPhoneNumber());
			
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
	//전화번호 변경
	public void updateCustomer(CustomerDTO customer,String phoneNumber) {
		try {
			connect();
			sql="""
					update customer
					set phonenumber=?
					where phonenumber=?
					and visibility=1
					""";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, phoneNumber);
			pstmt.setString(2, customer.getPhoneNumber());

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
		sql = "DELETE FROM menu WHERE mname=?;";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, menuName);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"메뉴가 삭제되었습니다","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"메뉴를 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"메뉴를 삭제할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	

	
	public void visibilityMenu0(String menuName) {
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
	
	public void visibilityMenu1(String menuName) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE menu SET ")
					.append("visibility=? ")
					.append("WHERE mname=?;")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
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
	
	public void hideCategory(String kind) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE category SET ")
					.append("visibility=0 ")
					.append("WHERE kind=?;")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			
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
	
	public void showCategory(String kind) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE category SET ")
					.append("visibility=1 ")
					.append("WHERE kind=?;")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kind);
			
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
	
	public void updateMenu(MenuDTO menu, String mname) {
		connect();
		try {
			sql = new StringBuilder()
					.append("UPDATE menu SET ")
					.append("mname=?, ")
					.append("price=? ")
					.append("WHERE mname=?;")
					.toString();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, menu.getMname());
			pstmt.setInt(2, menu.getPrice());
			pstmt.setString(3, mname);
			
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
	//검색


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
				insert into menu (mname,price,cano,ice,icechangeable)
				values (?,?,?,?,?)
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, menu.getMname());
			pstmt.setInt(2, menu.getPrice());
			pstmt.setInt(3, menu.getCano());
			pstmt.setInt(4, menu.getIce());
			pstmt.setInt(5, menu.getIceChangeable());
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
	public int insertOrderList(OrderDTO order) {
		int ono=0;
		connect();
		sql = """
				insert into orderlist (cno,date,price,finalprice)
				values (?,now(),?,?)
				""";
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, order.getCno());
			pstmt.setInt(2, order.getPrice());
			pstmt.setInt(3, order.getFinalprice());
			int rows = pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			
			if(rs.next()) {
				ono = rs.getBigDecimal(1).intValue();
				
			}
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"주문번호"+ono+"인 주문 결제되었습니다","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"주문목록에 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
			return ono;
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"주문목록에 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			return ono;
		}
	}
	public void insertMenuSales(MenuDTO menu) {
		connect();
//		sql = """
//				insert menusales 
//				set ono =?, count=? 
//				where mno = ? 
//				""";
		sql = """
				insert into menusales (ono,count,mno,date)
				values (?,?,?,now())
				""";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, menu.getOno());
			pstmt.setInt(2, menu.getCount());
			pstmt.setInt(3, menu.getMno());
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				//JOptionPane.showMessageDialog(null,"판매량이 저장되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"판매량을 저장할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"판매량을 저장할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//카테고리 추가
	public void addCategory(CategoryDTO category) {
		connect();
		sql = """
				insert into category (kind, visibility)
				values (?,1)
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category.getKind());
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "추가되었습니다.", "확인", JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"종류를 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"종류를 추가할 수 없습니다","확인",JOptionPane.WARNING_MESSAGE);
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
	


	public List<MenuDTO> searchKeyword(String keyword){
		connect();
		sql = "select * from menu where mname like ?";
		List<MenuDTO> menuboard = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+keyword+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MenuDTO board = new MenuDTO();
				board.setVisibility(rs.getInt("visibility"));
				board.setMname(rs.getString(2));
				board.setPrice(rs.getInt(3));
				board.setCano(rs.getInt(5));
				
				String sql2 = "select kind from category where cano = ? ";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, rs.getInt(5));
				ResultSet rs2 = pstmt.executeQuery();
				if(rs2.next()) board.setKind(rs2.getString(1));
				menuboard.add(board);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return menuboard;
	}
	//고객 재가입
	public void reSign(String phoneNumber) {
		connect();
		sql = """
				update customer
				set visibility =1, point = 0
				where phonenumber=? and visibility=0;
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,phoneNumber);
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null,"재등록이 되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"재등록이 불가능 합니다.","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"재등록이 불가능 합니다.","확인",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//고객 검색창
	public List<CustomerDTO> searchKeywordCustomer(String phonenumber) {
		connect();
		sql = "select *  from customer where phonenumber=?";
		List<CustomerDTO> items = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phonenumber);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CustomerDTO item = new CustomerDTO();
				item.setCno(rs.getInt(1));
				item.setPhoneNumber(rs.getString(2));
				item.setPoint(rs.getInt(3));
				item.setRecdate(rs.getDate(4));
				item.setVisibility(rs.getInt(5));
				items.add(item);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return items;
	}

	//주문번호 검색
	public List<OrderDTO> searchOrderKeyword(String keyword){
		connect();
		sql = "select ono, date, price, finalprice from orderlist where ono like ?";
		List<OrderDTO> orderList = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql);
			System.out.println(keyword);
			pstmt.setString(1, "%"+keyword+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderDTO board = new OrderDTO();
				board.setOno(rs.getInt("ono"));
				board.setDate(rs.getDate("date"));
				board.setPrice(rs.getInt("price"));
				board.setFinalprice(rs.getInt("finalprice"));
				orderList.add(board);
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
	



}