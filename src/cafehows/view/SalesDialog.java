package cafehows.view;


import java.awt.BorderLayout;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


import cafehows.model.OrderDTO;
import cafehows.model.CafeDAO;
import cafehows.model.MenuDTO;


public class SalesDialog extends JDialog{

		private JTabbedPane salesTab;
		private JPanel tab1Panel,tab2Panel,tab3Panel,periodPanel,salesChart;
		private JPanel pNorth,pCenter,pSouth;
		private JTable orderListTable,dailySalesTable,menuSalesTable;
		private JTextField startPeriod,endPeriod;
		private JButton enterBtn;
//		private ArrayList<OrderDTO> orderList= new ArrayList<>();
		

		public SalesDialog() {
			this.setTitle("매출관리");					
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setSize(800,800);
			this.getContentPane().add(getJTabbedPane());
			locationCenter();

		}
		private JTabbedPane getJTabbedPane() {
			if(salesTab == null) {
				salesTab = new JTabbedPane();
				salesTab.setTabPlacement(JTabbedPane.TOP);
				salesTab.addTab("매출액", getTab1Panel());
				salesTab.addTab("메뉴별", getTab2Panel());
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
				periodPanel.add(endPeriod);
				periodPanel.add(getEnterBtn());
			}
			return periodPanel;
		}
		
		public JButton getEnterBtn() {
			if(enterBtn==null) {
				enterBtn = new JButton();
				enterBtn.setText("조회");
				enterBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					
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
				orderListTable = new JTable();
				orderListTable.setAutoCreateRowSorter(true);
				
				DefaultTableModel tableModel = (DefaultTableModel)orderListTable.getModel();
				tableModel.addColumn("날짜");
				tableModel.addColumn("주문번호");
				tableModel.addColumn("금액");
				tableModel.addColumn("실결제금액");
				setOrderListTable();
				
//				menuTable1.getColumn("메뉴명").setPreferredWidth(50);
//				menuTable1.getColumn("가격").setPreferredWidth(20);
				
//				CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//				menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//				menuTable.getColumn("가격").setCellRenderer(ctcr);
//				
			}
			return orderListTable;
		}
		
		public JTable getDailySalesTable() {
			if(dailySalesTable == null) {
				dailySalesTable = new JTable();
				dailySalesTable.setAutoCreateRowSorter(true);
				
				DefaultTableModel tableModel = (DefaultTableModel)dailySalesTable.getModel();
				tableModel.addColumn("매출날짜");
				tableModel.addColumn("매출액");
				setDailySalesTable();
				
				
				
				
//				menuTable1.getColumn("메뉴명").setPreferredWidth(50);
//				menuTable1.getColumn("가격").setPreferredWidth(20);
				
//				CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//				menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//				menuTable.getColumn("가격").setCellRenderer(ctcr);
//				
				
			}
			return dailySalesTable;
		}
		
		//탭2 panel
		private JPanel getTab2Panel() {
			if(tab2Panel == null) {
				tab2Panel = new JPanel();
				tab2Panel.setLayout(new BorderLayout());
				tab2Panel.add(new JScrollPane(getMenuSalesTable()),BorderLayout.CENTER);
	//			tab2Panel.add(new JScrollPane(new SalesChart()),BorderLayout.SOUTH);
				
			
			}
			return tab2Panel;
		}
		public JTable getMenuSalesTable() {
			if(menuSalesTable==null) {
				menuSalesTable = new JTable();
				menuSalesTable.setAutoCreateRowSorter(true);
				
				DefaultTableModel tableModel = (DefaultTableModel)menuSalesTable.getModel();
				tableModel.addColumn("메뉴명");
				tableModel.addColumn("판매량");
				setMenuSalesTable();
			}
			return menuSalesTable;
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
					Integer.parseInt(startPeriod.getText()),Integer.parseInt(endPeriod.getText()))
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
					Integer.parseInt(startPeriod.getText()),Integer.parseInt(endPeriod.getText()))
					) {
				Object[] rowData = {dto.getDate(),dto.getFinalprice()};
				tableModel.addRow(rowData);
				
			}
		}
		//메뉴별 탭
		public void setMenuSalesTable() {
			DefaultTableModel tableModel = (DefaultTableModel) menuSalesTable.getModel();
			tableModel.setNumRows(0);
	
			for(MenuDTO dto :CafeDAO.getInstance().getMenuSales()) {
				Object[] rowData = {dto.getMname(),dto.getCumCount()};
				
				tableModel.addRow(rowData);
				
			}
		}

		private void locationCenter() {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Point centerPoint = ge.getCenterPoint();
			int leftTopX = centerPoint.x - this.getWidth()/2;
			int leftTopY = centerPoint.y - this.getHeight()/2;
			this.setLocation(leftTopX, leftTopY);
		}
		

		

	}


