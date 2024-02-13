package cafehows.view;


import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
		private JTextField startPeriod1,endPeriod1,startPeriod2,endPeriod2;
		private JButton enterBtn;
		private JLabel avgField;
		private ArrayList<MenuDTO> menuList= new ArrayList<>();
//		private ArrayList<OrderDTO> orderList= new ArrayList<>();
		private static final String REGEXP_DATE = "^[\\d]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
		private int monthTemp,yearTemp,totalFinalPrice;
		private double avgFinalPrice;

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
				salesTab.addTab("매출액", getTab1Panel11());
				salesTab.addTab("메뉴별", getJTabbedPane2());
				salesTab.addTab("운영비", getTab3Panel());
				}
			return salesTab;
			}
		private JTabbedPane getJTabbedPane1() {

			JTabbedPane salesTab = new JTabbedPane();
			salesTab.setTabPlacement(JTabbedPane.TOP);
			salesTab.addTab("일간", getTab1Panel11());
			salesTab.addTab("주간", getTab1Panel12());
			salesTab.addTab("월간", getTab1Panel13());

		return salesTab;
		}
		//일간 table 날라감
		private JPanel getTab1Panel11() {
			
			JPanel tab1Panel = new JPanel();
				tab1Panel.setLayout(new BorderLayout());
				
				tab1Panel.add(getPeriodPanel1(),BorderLayout.NORTH);
				tab1Panel.add(new JScrollPane(getOrderListTable()),BorderLayout.CENTER);
				
				JPanel salesField = new JPanel();
				salesField.setLayout(new BoxLayout(salesField,BoxLayout.Y_AXIS));
				
				JPanel avg = new JPanel();
				JLabel avgLabel = new JLabel("일 평균 매출액");
				avgField = new JLabel(Double.toString(avgFinalPrice));
				avg.add(avgLabel);
				avg.add(avgField);
				
				salesField.add(new JScrollPane(getDailySalesTable()));
				salesField.add(avg);
				tab1Panel.add(salesField,BorderLayout.SOUTH);
			
				//tab1Panel.add(new JScrollPane(getDailySalesTable()), BorderLayout.SOUTH);
				
			
			return tab1Panel;
		}
		private JPanel getTab1Panel12() {
			//table refresh 필요
				JPanel tab1Panel = new JPanel();
				tab1Panel.setLayout(new BorderLayout());
				
				tab1Panel.add(getMonth(),BorderLayout.NORTH);
				tab1Panel.add(new JScrollPane(getDailySalesTable()),BorderLayout.CENTER);

				JPanel avg = new JPanel();
				JLabel avgLabel = new JLabel("주 평균 매출액");
				avgField = new JLabel(Double.toString(avgFinalPrice));
				avg.add(avgLabel);
				avg.add(avgField);
	
				tab1Panel.add(avg,BorderLayout.SOUTH);
			
			return tab1Panel;
		}
		private JPanel getTab1Panel13() {
		
			JPanel tab1Panel = new JPanel();
			tab1Panel.setLayout(new BorderLayout());
			
			tab1Panel.add(new JScrollPane(getDailySalesTable()),BorderLayout.CENTER);

			JPanel avg = new JPanel();
			JLabel avgLabel = new JLabel("월 평균 매출액");
			avgField = new JLabel(Double.toString(avgFinalPrice));
			avg.add(avgLabel);
			avg.add(avgField);

			tab1Panel.add(avg,BorderLayout.SOUTH);
			return tab1Panel;
		}
		public JPanel getPeriodPanel1() {
			
				JPanel periodPanel = new JPanel();
				JLabel inputPeriod = new JLabel();
				inputPeriod.setText("조회기간입력");
				startPeriod1 = new JTextField(10);
				endPeriod1 = new JTextField(10);
				periodPanel.add(inputPeriod);
				periodPanel.add(startPeriod1);
				periodPanel.add(new JLabel("~"));
				periodPanel.add(endPeriod1);
				periodPanel.add(getEnterBtn1());
			
			return periodPanel;
		}
		
		public JPanel getPeriodPanel2() {
			
			JPanel periodPanel = new JPanel();
			JLabel inputPeriod = new JLabel();
			inputPeriod.setText("조회기간입력");
			startPeriod2 = new JTextField(10);
			endPeriod2 = new JTextField(10);
			periodPanel.add(inputPeriod);
			periodPanel.add(startPeriod2);
			periodPanel.add(new JLabel("~"));
			periodPanel.add(endPeriod2);
			periodPanel.add(getEnterBtn2());
		
		return periodPanel;
	}
	
		public JButton getEnterBtn1() {
			if(enterBtn==null) {
				enterBtn = new RoundedButton();
				enterBtn.setText("조회");
				enterBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						validateDate(startPeriod1);
						validateDate(endPeriod1);
						refreshOrderListTable();
						refreshDailySalesTable();
						avgField.setText(Double.toString(avgFinalPrice));
//						startPeriod1.setText("");
//						endPeriod1.setText("");
						
					}
				});
			}
			return enterBtn;
		}
	
		public JButton getEnterBtn2() {
			
				JButton enterBtn = new RoundedButton();
				enterBtn.setText("조회");
				enterBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						validateDate(startPeriod2);
						validateDate(endPeriod2);
						refreshDailyMenuSalesTable();
	
					}
				});
			
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
				avgField.setText(Double.toString(avgFinalPrice));
	
				
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
			tab2Panel.add(getPeriodPanel2(),BorderLayout.NORTH);
			tab2Panel.add(new JScrollPane(getMenuSalesTable1()),BorderLayout.CENTER);
//			tab2Panel.add(new JScrollPane(new SalesChart()),BorderLayout.SOUTH);
			
		
		
		return tab2Panel;
	}
		
		public JPanel getMonth() {
			JPanel pMonth = new JPanel();
			pMonth.add(getComboYear());
			pMonth.add(getComboMonth());
		return pMonth;
		}
		
		public JComboBox getComboYear() {
			
			LocalDate now = LocalDate.now();
			int startYear = 2023;
			int year = now.getYear();
			Vector arrYear = new Vector();
			
			for(int i=startYear;i<=year;i++) {
				String yearString = i+"년";
				arrYear.add(yearString);
				System.out.println(yearString);
			}
			
			
			JComboBox comboYear = new JComboBox(arrYear);
			yearTemp = comboYear.getSelectedIndex()+startYear;

			comboYear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					yearTemp = comboYear.getSelectedIndex()+startYear;
				}
			});
		
		return comboYear;

	}
		
		public JComboBox getComboMonth() {
			
				String[] arrMonth = {"1월","2월","3월","4월","5월","6월",
						"7월","8월","9월","10월","11월","12월"};
				
				JComboBox<String> comboMonth = new JComboBox<String>(arrMonth);
				monthTemp = comboMonth.getSelectedIndex()+1;
				comboMonth.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						monthTemp = comboMonth.getSelectedIndex()+1;
						refreshWeeklyMenuSalesTable();
						
						
					}
				});
			
			return comboMonth;
		
		}
		
		private JPanel getTab1Panel2() {
		
				JPanel tab2Panel = new JPanel();
				tab2Panel.setLayout(new BorderLayout());
				tab2Panel.add(getMonth(),BorderLayout.NORTH);
				tab2Panel.add(new JScrollPane(getMenuSalesTable2()),BorderLayout.CENTER);
	//			tab2Panel.add(new JScrollPane(new SalesChart()),BorderLayout.SOUTH);
				
		
			
			return tab2Panel;
		}
		private JPanel getTab1Panel3() {
			
			JPanel tab2Panel = new JPanel();
		
			tab2Panel.setLayout(new BorderLayout());
			tab2Panel.add(getMonth(),BorderLayout.NORTH);
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
				tableModel.addColumn("일자");
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
				tableModel.addColumn("월");
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
				startPeriod1.getText(),endPeriod1.getText())
					) {
				Object[] rowData = {dto.getDate(), dto.getOno(),dto.getPrice(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				
			}
		}
		
		public void setDailySalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) dailySalesTable.getModel();
			tableModel.setNumRows(0);
			totalFinalPrice=0;
			avgFinalPrice=0;
			for(OrderDTO dto : CafeDAO.getInstance().getDailySales()) {
				Object[] rowData = {dto.getDate(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				totalFinalPrice+=dto.getFinalprice();
				
			}
			avgFinalPrice=(double)totalFinalPrice/CafeDAO.getInstance().getDailySales().size();
			
		}
		
		public void refreshDailySalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) dailySalesTable.getModel();
			tableModel.setNumRows(0);
			totalFinalPrice=0;
			avgFinalPrice=0;
			for(OrderDTO dto :CafeDAO.getInstance().getDailySalesbyPeriod(
					startPeriod1.getText(),endPeriod1.getText())
					) {
				Object[] rowData = {dto.getDate(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				totalFinalPrice+=dto.getFinalprice();
			}
			avgFinalPrice=(double)totalFinalPrice/CafeDAO.getInstance().getDailySalesbyPeriod(
					startPeriod1.getText(),endPeriod1.getText()).size();
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
		public void refreshDailyMenuSalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable1.getModel();
			tableModel.setNumRows(0);
	
			for(MenuDTO dto :CafeDAO.getInstance().getDailyMenuSales(
					startPeriod2.getText(),endPeriod2.getText())) {
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
		public void refreshWeeklyMenuSalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable2.getModel();
			tableModel.setNumRows(0);
	
			for(MenuDTO dto :CafeDAO.getInstance().getWeeklyMenuSales(yearTemp,monthTemp)) {
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
		public void refreshMonthlyMenuSalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable3.getModel();
			tableModel.setNumRows(0);
	
			for(MenuDTO dto :CafeDAO.getInstance().getMonthlyMenuSales(yearTemp,monthTemp)) {
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


