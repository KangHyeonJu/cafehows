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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import cafehows.model.cafeDAO;
import cafehows.model.menuDTO;


public class Main extends JFrame{
	private Main main;
	private JTabbedPane menuTab;
	private JPanel tab1Panel,tab2Panel,orderPanel,orderBtnPanel,btnPanel;
	private JTable menuTable1,menuTable2,orderTable;
	private JButton initBtn, delBtn, addBtn,modBtn,customBtn,salesBtn,refundBtn,paymentBtn;
	
	public Main() {
		this.main = this;
		this.setTitle("coffeehows");
		this.setSize(1000,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(getJTabbedPane(), BorderLayout.WEST);
		this.getContentPane().add(getOrderPanel(),BorderLayout.CENTER);
		this.getContentPane().add(getBtnPanel(),BorderLayout.EAST);
		locationCenter();
	}
	private JTabbedPane getJTabbedPane() {
		if(menuTab == null) {
			menuTab = new JTabbedPane();
			menuTab.setTabPlacement(JTabbedPane.TOP);
			menuTab.addTab("커피", getTab1Panel());
			menuTab.addTab("스무디", getTab2Panel());
			}
		return menuTab;
		}
	private JPanel getTab1Panel() {
		if(tab1Panel == null) {
			tab1Panel = new JPanel();
			tab1Panel.add(new JScrollPane(getMenuTable1()));
		}
		return tab1Panel;
	}
	private JPanel getTab2Panel() {
		if(tab2Panel == null) {
			tab2Panel = new JPanel();
			tab2Panel.add(new JScrollPane(getMenuTable2()));
		}
		return tab2Panel;
	}
	private JTable getMenuTable1() {
		if(menuTable1 == null) {
			menuTable1 = new JTable();
			menuTable1.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) menuTable1.getModel();
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			refreshMenu(1);
			
			menuTable1.getColumn("메뉴명").setPreferredWidth(50);
			menuTable1.getColumn("가격").setPreferredWidth(20);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
			menuTable1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int rowIndex = menuTable1.getSelectedRow();
					if(rowIndex !=-1) {
						int bno = (int)menuTable1.getValueAt(rowIndex, 0);
						
					}
				}		
			});
		}
			
		return menuTable1;
	}
	private JTable getMenuTable2() {
		if(menuTable2 == null) {
			menuTable2 = new JTable();
			menuTable2.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) menuTable2.getModel();
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			refreshMenu(2);
			
			menuTable2.getColumn("메뉴명").setPreferredWidth(50);
			menuTable2.getColumn("가격").setPreferredWidth(20);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
//			menuTable.addMouseListener(new MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//					int rowIndex = menuTable.getSelectedRow();
//					if(rowIndex !=-1) {
//						int bno = (int)menuTable.getValueAt(rowIndex, 0);
//						
//					}
//				}		
//			});
		}
			
		return menuTable2;
	}
	

//	public class CenterTableCellRenderer extends JLabel implements TableCellRenderer {
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//			setText(value.toString());
//			setFont(new Font(null, Font.PLAIN, 12));
//			setHorizontalAlignment(JLabel.CENTER);
//			setOpaque(true);
//			if(isSelected) { setBackground(Color.YELLOW); } 
//			else { setBackground(Color.WHITE); }
//			return this;
//		}
//	}	
	
	public JPanel getOrderPanel() {
		if(orderPanel==null) {
			orderPanel = new JPanel();
			orderPanel.setLayout(new BorderLayout());
			JLabel label = new JLabel("주문 목록");
			label.setAlignmentX(JLabel.CENTER);
			label.setPreferredSize(new Dimension(70, 30));
			label.setHorizontalAlignment(JLabel.CENTER);
			orderPanel.add(label, BorderLayout.NORTH);
			orderPanel.add(new JScrollPane(getOrderTable()),BorderLayout.CENTER);
		//	orderPanel.add(getOrderBtnPanel(),BorderLayout.SOUTH);
			JLabel priceLabel = new JLabel("총 가격");
			JTextField priceField = new JTextField(10);
			
			JPanel totalField = new JPanel();
			totalField.add(getOrderBtnPanel());
			totalField.add(priceLabel);
			totalField.add(priceField);
			orderPanel.add(totalField,BorderLayout.SOUTH);
			
		}
		return orderPanel;
	}
	
	private JTable getOrderTable() {
		if(orderTable == null) {
			orderTable= new JTable();
			orderTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("수량");
			tableModel.addColumn("가격");
			tableModel.addColumn("아이스/핫");
		//	refreshMenu();
			
//			orderTable.getColumn("메뉴명").setPreferredWidth(50);
//			orderTable.getColumn("수량").setPreferredWidth(50);
//			orderTable.getColumn("가격").setPreferredWidth(20);
//			orderTable.getColumn("아이스/핫").setPreferredWidth(20);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
//			menuTable.addMouseListener(new MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//					int rowIndex = menuTable.getSelectedRow();
//					if(rowIndex !=-1) {
//						int bno = (int)menuTable.getValueAt(rowIndex, 0);
//						
//					}
//				}		
//			});
		}
			
		return orderTable;
	}
	
	public JPanel getOrderBtnPanel() {
		if(orderBtnPanel == null) {
			orderBtnPanel = new JPanel();
	//		orderBtnPanel.setLayout(new BorderLayout());
			orderBtnPanel.add(getDelBtn());
			orderBtnPanel.add(getInitBtn());
		}
		return orderBtnPanel;
	}
	
	public JButton getDelBtn() {
		if(delBtn==null) {
			delBtn = new JButton();
			delBtn.setText("항목 삭제");
		
		}
		return delBtn;
	}
	
	
	public JButton getInitBtn() {
		if(initBtn==null) {
			initBtn = new JButton();
			initBtn.setText("초기화");
		
		}
		return initBtn;
	}
	public JPanel getBtnPanel() {
		if(btnPanel== null) {
			btnPanel = new JPanel();
			btnPanel.setLayout(new GridLayout(8,1));
			btnPanel.add(getAddBtn());
			btnPanel.add(getModBtn());
			btnPanel.add(getCustomBtn());
			btnPanel.add(getSalesBtn());
			btnPanel.add(getSalesBtn());
			btnPanel.add(new JPanel());
			btnPanel.add(new JPanel());
			btnPanel.add(getRefundBtn());
			btnPanel.add(getPaymentBtn());
			
		}
		return btnPanel;
	}
	
	public JButton getAddBtn() {
		if(addBtn==null) {
			addBtn = new JButton();
			addBtn.setText("메뉴 추가");
	
		}
		return addBtn;
	}
	public JButton getModBtn() {
		if(modBtn==null) {
			modBtn = new JButton();
			modBtn.setText("메뉴 수정/삭제/숨김");
	
		}
		return modBtn;
	}
	public JButton getCustomBtn() {
		if(customBtn==null) {
			customBtn = new JButton();
			customBtn.setText("고객 관리");
		
		}
		return customBtn;
	}
	
	public JButton getSalesBtn() {
		if(salesBtn==null) {
			salesBtn = new JButton();
			salesBtn.setText("매출 관리");
	
		}
		return salesBtn;
	}
	
	public JButton getRefundBtn() {
		if(refundBtn==null) {
			refundBtn = new JButton();
			refundBtn.setText("환불");
	
		
		}
		return refundBtn;
	}
	public JButton getPaymentBtn() {
		if(paymentBtn==null) {
			paymentBtn = new JButton();
			paymentBtn.setText("결제");
		}
		return paymentBtn;
	}
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
	public void refreshMenu(int cano) {
		DefaultTableModel tableModel = (DefaultTableModel) menuTable1.getModel();
		tableModel.setNumRows(0);
		for(menuDTO dto : cafeDAO.getInstance().getItems(cano)) {
			Object[] rowData = {dto.getMname(), dto.getPrice()};
			tableModel.addRow(rowData);
		}
	}
	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Main main = new Main();
			main.setVisible(true);
		});
}
}
