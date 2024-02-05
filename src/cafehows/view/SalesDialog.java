package cafehows.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
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


public class SalesDialog extends JDialog{

		private JTabbedPane salesTab;
		private JPanel tab1Panel,tab2Panel,tab3Panel,periodPanel;
		private JPanel pNorth,pCenter,pSouth;
		private JTable orderListTable,dailySalesTable;

		public SalesDialog() {
			this.setTitle("매출관리");					
			this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setSize(300, 200);
			this.getContentPane().add(getJTabbedPane());

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
				
				tab1Panel.add(getPeriodPanel(),BorderLayout.SOUTH);
				tab1Panel.add(new JScrollPane(getOrderListTable()),BorderLayout.CENTER);
				tab1Panel.add(new JScrollPane(getDailySalesTable()), BorderLayout.SOUTH);
			}
			return tab1Panel;
		}
		public JPanel getPeriodPanel() {
			if(periodPanel == null) {
				periodPanel = new JPanel();
				JLabel inputPeriod = new JLabel();
				JTextField startPeriod = new JTextField(10);
				JTextField endPeriod = new JTextField(10);
				periodPanel.add(inputPeriod);
				periodPanel.add(startPeriod);
				periodPanel.add(endPeriod);
			}
			return periodPanel;
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
				refreshTable();
				
//				menuTable1.getColumn("메뉴명").setPreferredWidth(50);
//				menuTable1.getColumn("가격").setPreferredWidth(20);
				
//				CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//				menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//				menuTable.getColumn("가격").setCellRenderer(ctcr);
//				
				orderListTable.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						int rowIndex = orderListTable.getSelectedRow();
						if(rowIndex !=-1) {
							int bno = (int)orderListTable.getValueAt(rowIndex, 0);
							
						}
					}		
				});
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

				refreshTable();
				
//				menuTable1.getColumn("메뉴명").setPreferredWidth(50);
//				menuTable1.getColumn("가격").setPreferredWidth(20);
				
//				CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//				menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//				menuTable.getColumn("가격").setCellRenderer(ctcr);
//				
				dailySalesTable.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						int rowIndex = dailySalesTable.getSelectedRow();
						if(rowIndex !=-1) {
							int bno = (int)dailySalesTable.getValueAt(rowIndex, 0);
							
						}
					}		
				});
			}
			return dailySalesTable;
		}
		
		
		private JPanel getTab2Panel() {
			if(tab2Panel == null) {
				tab2Panel = new JPanel();
			
			}
			return tab2Panel;
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
		
		
		public void refreshTable() {
			DefaultTableModel tableModel = (DefaultTableModel) orderListTable.getModel();
			tableModel.setNumRows(0);
			for(OrderDTO dto : CafeDAO.getInstance().getOrderItems()) {
				Object[] rowData = {dto.getDate(), dto.getOno(),dto.getPrice(),dto.getFinalprice()};
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


