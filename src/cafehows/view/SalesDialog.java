package cafehows.view;


import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CafeDAO;
import cafehows.model.MenuDTO;
import cafehows.model.OrderDTO;
import exceptions.UnsuitableInputException;


public class SalesDialog extends JDialog{

		private JTabbedPane salesTab;
		private JPanel tab1Panel,tab2Panel,tab3Panel,periodPanel,salesChart;
		private JPanel pNorth,pCenter,pSouth;
		private JTable orderListTable,dailySalesTable,menuSalesTable1,menuSalesTable2,menuSalesTable3;
		private JTextField startPeriod,endPeriod;
		private JButton enterBtn;
		private ArrayList<MenuDTO> menuList= new ArrayList<>();
//		private ArrayList<OrderDTO> orderList= new ArrayList<>();
		private static final String REGEXP_DATE = "^[\\d]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		

		public SalesDialog() {
			this.setModal(true);
			this.setTitle("매출관리");					
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setSize(700,700);
			this.setModal(true); //상위 frame 클릭 불가
			this.setResizable(false); //사이즈 고정
			this.getContentPane().add(getJTabbedPane());
			locationCenter();

		}
		private JTabbedPane getJTabbedPane() {
			if(salesTab == null) {
				salesTab = new JTabbedPane();
				salesTab.setTabPlacement(JTabbedPane.TOP);
				salesTab.addTab("매출액", getTab1Panel());
				salesTab.addTab("메뉴별", getJTabbedPane2());
				salesTab.addTab("운영비", getTab3Panel());
				}
			return salesTab;
			}
		private JPanel getTab1Panel() {
			if(tab1Panel == null) {
				tab1Panel = new JPanel();
				tab1Panel.setLayout(new BorderLayout());
				
				tab1Panel.add(getPeriodPanel(),BorderLayout.NORTH);
				tab1Panel.add(new JScrollPane(getOrderListTable()),BorderLayout.CENTER);
				tab1Panel.add(new JScrollPane(getDailySalesTable()), BorderLayout.SOUTH);
			}
			return tab1Panel;
		}
		public JPanel getPeriodPanel() {
			if(periodPanel == null) {
				periodPanel = new JPanel();
				JLabel inputPeriod = new JLabel();
				inputPeriod.setText("조회기간입력");
				startPeriod = new JTextField(10);
				endPeriod = new JTextField(10);
				periodPanel.add(inputPeriod);
				periodPanel.add(startPeriod);
				periodPanel.add(new JLabel("~"));
				periodPanel.add(endPeriod);
				periodPanel.add(getEnterBtn());
			}
			return periodPanel;
		}
		
		public JButton getEnterBtn() {
			if(enterBtn==null) {
				enterBtn = new RoundedButton();
				enterBtn.setText("조회");
				enterBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						validateDate(startPeriod);
						validateDate(endPeriod);
						refreshOrderListTable();
						refreshDailySalesTable();
					//	startPeriod.setText("");
					//	endPeriod.setText("");
						
					}
				});
			}
			return enterBtn;
		}
	
		
		public JTable getOrderListTable() {
			if(orderListTable == null) {
				orderListTable = new JTable() {
					@Override
					public boolean isCellEditable(int row, int col) {
						return false;
					}
				};
				orderListTable.setAutoCreateRowSorter(true);
				orderListTable.getTableHeader().setReorderingAllowed(false);
				orderListTable.getTableHeader().setResizingAllowed(false);
				
				DefaultTableModel tableModel = (DefaultTableModel)orderListTable.getModel();
				tableModel.addColumn("날짜");
				tableModel.addColumn("주문번호");
				tableModel.addColumn("금액");
				tableModel.addColumn("실결제금액");
				setOrderListTable();

//				
			}
			return orderListTable;
		}
		
		public JTable getDailySalesTable() {
			if(dailySalesTable == null) {
				dailySalesTable = new JTable() {
					@Override
					public boolean isCellEditable(int row, int col) {
						return false;
					}
				};
				dailySalesTable.setAutoCreateRowSorter(true);
				dailySalesTable.getTableHeader().setReorderingAllowed(false);
				dailySalesTable.getTableHeader().setResizingAllowed(false);
				DefaultTableModel tableModel = (DefaultTableModel)dailySalesTable.getModel();
				tableModel.addColumn("매출날짜");
				tableModel.addColumn("매출액");
				setDailySalesTable();
	
				
			}
			return dailySalesTable;
		}
		
		//탭2 panel
		private JTabbedPane getJTabbedPane2() {

				JTabbedPane menuSalesTab = new JTabbedPane();
				menuSalesTab.setTabPlacement(JTabbedPane.TOP);
				menuSalesTab.addTab("일간", getTab1Panel1());
				menuSalesTab.addTab("주간", getTab1Panel2());
				menuSalesTab.addTab("월간", getTab1Panel3());
	
			return menuSalesTab;
			}
		private JPanel getTab1Panel1() {
			
			JPanel tab2Panel = new JPanel();
		
			tab2Panel.setLayout(new BorderLayout());
			tab2Panel.add(new JScrollPane(getMenuSalesTable1()),BorderLayout.CENTER);
//			tab2Panel.add(new JScrollPane(new SalesChart()),BorderLayout.SOUTH);
			
		
		
		return tab2Panel;
	}
		private JPanel getTab1Panel2() {
		
				JPanel tab2Panel = new JPanel();
				tab2Panel.setLayout(new BorderLayout());
				tab2Panel.add(new JScrollPane(getMenuSalesTable2()),BorderLayout.CENTER);
	//			tab2Panel.add(new JScrollPane(new SalesChart()),BorderLayout.SOUTH);
				
		
			
			return tab2Panel;
		}
		private JPanel getTab1Panel3() {
			
			JPanel tab2Panel = new JPanel();
		
			tab2Panel.setLayout(new BorderLayout());
			tab2Panel.add(new JScrollPane(getMenuSalesTable3()),BorderLayout.CENTER);
//			tab2Panel.add(new JScrollPane(new SalesChart()),BorderLayout.SOUTH);
			
	
		
		return tab2Panel;
	}
	
//		private JPanel getTab2Panel() {
//			if(tab2Panel == null) {
//				tab2Panel = new JPanel();
//				tab2Panel.setLayout(new BorderLayout());
//				tab2Panel.add(new JScrollPane(getMenuSalesTable()),BorderLayout.CENTER);
//	//			tab2Panel.add(new JScrollPane(new SalesChart()),BorderLayout.SOUTH);
//				
//			
//			}
//			return tab2Panel;
//		}
		public JTable getMenuSalesTable1() {
			if(menuSalesTable1==null) {
				menuSalesTable1 = new JTable() {
					@Override
					public boolean isCellEditable(int row, int col) {
						return false;
					}
				};
				menuSalesTable1.setAutoCreateRowSorter(true);
				menuSalesTable1.getTableHeader().setReorderingAllowed(false);
				menuSalesTable1.getTableHeader().setResizingAllowed(false);
				DefaultTableModel tableModel = (DefaultTableModel)menuSalesTable1.getModel();
				tableModel.addColumn("기간");
				tableModel.addColumn("메뉴명");
				tableModel.addColumn("판매량");
				 setDailyMenuSalesTable();

			}
			return menuSalesTable1;
		}
		public JTable getMenuSalesTable2() {
			if(menuSalesTable2==null) {
				menuSalesTable2 = new JTable() {
					@Override
					public boolean isCellEditable(int row, int col) {
						return false;
					}
				};
				menuSalesTable2.setAutoCreateRowSorter(true);
				menuSalesTable2.getTableHeader().setReorderingAllowed(false);
				menuSalesTable2.getTableHeader().setResizingAllowed(false);
				DefaultTableModel tableModel = (DefaultTableModel)menuSalesTable2.getModel();
				tableModel.addColumn("기간");
				tableModel.addColumn("메뉴명");
				tableModel.addColumn("판매량");
	
				setWeeklyMenuSalesTable();
	
			}
			return menuSalesTable2;
		}
		public JTable getMenuSalesTable3() {
			if(menuSalesTable3==null) {
				menuSalesTable3 = new JTable() {
					@Override
					public boolean isCellEditable(int row, int col) {
						return false;
					}
				};
				menuSalesTable3.setAutoCreateRowSorter(true);
				menuSalesTable3.getTableHeader().setReorderingAllowed(false);
				menuSalesTable3.getTableHeader().setResizingAllowed(false);
				DefaultTableModel tableModel = (DefaultTableModel)menuSalesTable3.getModel();
				tableModel.addColumn("기간");
				tableModel.addColumn("메뉴명");
				tableModel.addColumn("판매량");
	
				setMonthlyMenuSalesTable();
	
			}
			return menuSalesTable3;
		}
		
		private JPanel getTab3Panel() {
			if(tab3Panel == null) {
				tab3Panel = new CostPanel();
			
			}
			return tab3Panel;
		}
		
		
//		public class CenterTableCellRenderer extends JLabel implements TableCellRenderer {
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//				setText(value.toString());
//				setFont(new Font(null, Font.PLAIN, 12));
//				setHorizontalAlignment(JLabel.CENTER);
//				setOpaque(true);
//				if(isSelected) { setBackground(Color.YELLOW); } 
//				else { setBackground(Color.WHITE); }
//				return this;
//			}
//		}	
		
//		public JPanel getSalesChart() {
//			if(salesChart==null) {
//				salesChart = new JPanel();
//				List<MenuDTO> menuSales= CafeDAO.getInstance().getMenuSales();
//				
//			
//				for(MenuDTO m : menuSales) {
//					
//				}
//			}
//			return salesChart;
//		}
//		
		
		public void setOrderListTable() {
			DefaultTableModel tableModel = (DefaultTableModel) orderListTable.getModel();
			tableModel.setNumRows(0);
			for(OrderDTO dto : CafeDAO.getInstance().getOrderItems()) {
				Object[] rowData = {dto.getDate(), dto.getOno(),dto.getPrice(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				
			}
		}
		
	
		
		public void refreshOrderListTable() {
			DefaultTableModel tableModel = (DefaultTableModel) orderListTable.getModel();
			tableModel.setNumRows(0);
	
			for(OrderDTO dto :CafeDAO.getInstance().getOrderItemsbyPeriod(
				startPeriod.getText(),endPeriod.getText())
					) {
				Object[] rowData = {dto.getDate(), dto.getOno(),dto.getPrice(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				
			}
		}
		
		public void setDailySalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) dailySalesTable.getModel();
			tableModel.setNumRows(0);
			
			for(OrderDTO dto : CafeDAO.getInstance().getDailySales()) {
				Object[] rowData = {dto.getDate(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				
			}
		}
		
		public void refreshDailySalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) dailySalesTable.getModel();
			tableModel.setNumRows(0);
	
			for(OrderDTO dto :CafeDAO.getInstance().getDailySalesbyPeriod(
					startPeriod.getText(),endPeriod.getText())
					) {
				Object[] rowData = {dto.getDate(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				
			}
		}
		//메뉴별 탭
		
//		public void setMenuSalesTable() {
//			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable.getModel();
//			tableModel.setNumRows(0);
//	
//			for(MenuDTO dto :CafeDAO.getInstance().getDailyMenuSales()) {
//			//	menuList.add(dto);
//				Object[] rowData = {dto.getMname(),dto.getCumCount()};			
//				tableModel.addRow(rowData);
//				
//			}
		public void setDailyMenuSalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable1.getModel();
			tableModel.setNumRows(0);
	
			for(MenuDTO dto :CafeDAO.getInstance().getDailyMenuSales()) {
			//	menuList.add(dto);
				Object[] rowData = {dto.getDate(),dto.getMname(),dto.getCumCount()};			
				tableModel.addRow(rowData);
				
			}
		}
		public void setWeeklyMenuSalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable2.getModel();
			tableModel.setNumRows(0);
	
			for(MenuDTO dto :CafeDAO.getInstance().getWeeklyMenuSales()) {
			//	menuList.add(dto);
				Object[] rowData = {dto.getStartdate()+"~"+dto.getEnddate(),dto.getMname(),dto.getCumCount()};			
				tableModel.addRow(rowData);
				
			}
		}
		public void setMonthlyMenuSalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable3.getModel();
			tableModel.setNumRows(0);
	
			for(MenuDTO dto :CafeDAO.getInstance().getMonthlyMenuSales()) {
			//	menuList.add(dto);
				Object[] rowData = {dto.getMonth(),dto.getMname(),dto.getCumCount()};			
				tableModel.addRow(rowData);
				
			}
		}
		private void validateDate(JTextField period) throws  UnsuitableInputException {
			
			if(!Pattern.matches(REGEXP_DATE,period.getText().trim()))
				throw new UnsuitableInputException("yyyy-MM-dd 형식이 잘못 입력되었습니다");
		
		
		}

		private void locationCenter() {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Point centerPoint = ge.getCenterPoint();
			int leftTopX = centerPoint.x - this.getWidth()/2;
			int leftTopY = centerPoint.y - this.getHeight()/2;
			this.setLocation(leftTopX, leftTopY);
		}
		

		

	}


