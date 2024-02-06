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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.CellEditorListener; 
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import cafehows.model.CafeDAO;
import cafehows.model.MenuDTO;


public class Main extends JFrame{
	private Main main;
//	private static final Main instance = new Main();
	private JTabbedPane menuTab;
	private JPanel tab1Panel,tab2Panel,orderPanel,selectPanel,orderBtnPanel,btnPanel;
	private JTable menuTable1,menuTable2,orderTable;
	private JButton initBtn, delBtn, addBtn,modBtn,customBtn,salesBtn,refundBtn,paymentBtn;
	private ArrayList<MenuDTO> orderList= new ArrayList<>();
	private String temp;
	private int totalPrice=0;
	private JLabel priceField;


	
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
	
	
	public int getTotalPrice() {
		return totalPrice;
	}
	public ArrayList<MenuDTO> getOrderList(){
		return orderList;
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
			refreshMenu(1, menuTable1);
			
			menuTable1.getColumn("메뉴명").setPreferredWidth(50);
			menuTable1.getColumn("가격").setPreferredWidth(20);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
			//더블클릭하면 수량 선택 창 구현해야함
			menuTable1.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					
					int rowIndex = menuTable1.getSelectedRow();
					if(rowIndex !=-1) {
						String mname =(String) menuTable1.getValueAt(rowIndex, 0);
						MenuDTO dto = CafeDAO.getInstance().getMenuByName(mname);
					for(MenuDTO dto2 : orderList) {
						if(dto2.getMname().equals(dto.getMname())) {
							dto2.setCount(dto2.getCount()+1);
							refreshOrderList();
							return;
						}
					}
						orderList.add(dto);
						refreshOrderList();

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
			refreshMenu(2, menuTable2);
			
			menuTable2.getColumn("메뉴명").setPreferredWidth(50);
			menuTable2.getColumn("가격").setPreferredWidth(20);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
			menuTable2.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					
					int rowIndex = menuTable2.getSelectedRow();
					if(rowIndex !=-1) {
						String mname =(String) menuTable2.getValueAt(rowIndex, 0);
						MenuDTO dto = CafeDAO.getInstance().getMenuByName(mname);
					for(MenuDTO dto2 : orderList) {
						if(dto2.getMname().equals(dto.getMname())) {
							dto2.setCount(dto2.getCount()+1);
							refreshOrderList();
							return;
						}
					}
						orderList.add(dto);
						refreshOrderList();

					}
				}		
			});
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
			
	//		orderPanel.add(new JScrollPane(getSelectPanel()),BorderLayout.EAST);)
		//	orderPanel.add(getOrderBtnPanel(),BorderLayout.SOUTH);
			
			JPanel totalField = new JPanel();
			totalField.setLayout(new BoxLayout(totalField,BoxLayout.Y_AXIS));
			
			JPanel price = new JPanel();
			JLabel priceLabel = new JLabel("총 가격");
			priceField = new JLabel(Integer.toString(totalPrice));
			price.add(priceLabel);
			price.add(priceField);
			
			totalField.add(getOrderBtnPanel());
			totalField.add(price);
			
		
			
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
			tableModel.addColumn("가격");
			tableModel.addColumn("수량");
			tableModel.addColumn("아이스/핫");
			
			orderTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					
					int rowIndex = orderTable.getSelectedRow();
					if(rowIndex !=-1) {
					temp =(String)orderTable.getValueAt(rowIndex, 0);
						

					}
				}		
			});
			
//			TableCellEditor editor = new DefaultCellEditor(new JTextField());
//			editor.addCellEditorListener(new CellEditorListener() {
//			@Override
//			public void editingStopped(ChangeEvent e) {
//			// 수정을 끝내고 enter 를 입력하면 ChangeEvent가 도착한다.
//			String value = (String) editor.getCellEditorValue();
//			TableModel model = orderTable.getModel();
//			int rowIdx = orderTable.getSelectedRow();
//			int colIdx =orderTable.getSelectedColumn();
//
//			//현재 선택된 셀에서 편집이 이루어졌으므로 모델의 값을 갱신해준다.
//			model.setValueAt(value, rowIdx, colIdx);
//			}
//
//			@Override
//			public void editingCanceled(ChangeEvent e) {
//			// TODO Auto-generated method stub
//
//			}
//			});
//	
//			}
			
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
//	public JPanel getSelectPanel() {
//		if(selectPanel == null) {
//			selectPanel = new JPanel();
//			selectPanel.setLayout(new BoxLayout(selectPanel,BoxLayout.Y_AXIS));
//			
//		}
//		return selectPanel;
//	}
	
	
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
			delBtn.addActionListener(e->{
				
				for(MenuDTO dto2 : orderList) {
					if(dto2.getMname().equals(temp)) {
						orderList.remove(dto2);
						refreshOrderList();
						return;
					}
				}

			});
		}
		return delBtn;
	}
	
	
	public JButton getInitBtn() {
		if(initBtn==null) {
			initBtn = new JButton();
			initBtn.setText("초기화");
			initBtn.addActionListener(e->{
			orderList.clear();
			refreshOrderList();
			});
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
			addBtn.addActionListener(e->{
				AddMenu addMenu = new AddMenu(main);
				addMenu.setVisible(true);
			});
	
		}
		return addBtn;
	}
	public JButton getModBtn() {
		if(modBtn==null) {
			modBtn = new JButton();
			modBtn.setText("메뉴 수정/삭제/숨김");
			modBtn.addActionListener(e->{
				MenuMDS menuMDS= new MenuMDS();
				menuMDS.setVisible(true);
			});
	
		}
		return modBtn;
	}
	public JButton getCustomBtn() {
		if(customBtn==null) {
			customBtn = new JButton();
			customBtn.setText("고객 관리");
			customBtn.addActionListener(e->{
				CustomerDialog customerDialog = new CustomerDialog();
				customerDialog.setVisible(true);
			});
		
		}
		return customBtn;
	}
	
	public JButton getSalesBtn() {
		if(salesBtn==null) {
			salesBtn = new JButton();
			salesBtn.setText("매출 관리");
			salesBtn.addActionListener(e->{
				SalesDialog salesDialog = new SalesDialog();
				salesDialog.setVisible(true);
			});
		}
		return salesBtn;
	}
	
	public JButton getRefundBtn() {
		if(refundBtn==null) {
			refundBtn = new JButton();
			refundBtn.setText("환불");
			refundBtn.addActionListener(e->{
				Refund refund= new Refund();
				refund.setVisible(true);
			});
		
		
		}
		return refundBtn;
	}
	public JButton getPaymentBtn() {
		if(paymentBtn==null) {
			paymentBtn = new JButton();
			paymentBtn.setText("결제");
			paymentBtn.addActionListener(e->{
				UsePoints usePoints= new UsePoints(main);
				usePoints.setVisible(true);
			});
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
	
	public void refreshMenu(int cano, JTable menuTable) {
		DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
		tableModel.setNumRows(0);
		for(MenuDTO dto : CafeDAO.getInstance().getItems(cano)) {
			Object[] rowData = {dto.getMname(), dto.getPrice()};
			tableModel.addRow(rowData);
			
		}
	}
	
	public void refreshOrderList() {
		DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
		tableModel.setNumRows(0);
		totalPrice = 0;
		for(MenuDTO dto : orderList) {
//			JTextField inputCount = new JTextField(4);
//			JTextField inputIce = new JTextField(4);
			Object[] rowData = {dto.getMname(), dto.getPrice(),dto.getCount(),dto.getIce()};
			tableModel.addRow(rowData);
			totalPrice+=dto.getPrice()*dto.getCount();
			System.out.println(totalPrice);

		}
		priceField.setText(Integer.toString(totalPrice));
	
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Main main = new Main();
			main.setVisible(true);
		});
}
}
