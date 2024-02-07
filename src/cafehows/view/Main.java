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
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.CellEditorListener; 
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;
import cafehows.model.MenuDTO;


public class Main extends JFrame{
	private Main main;
//	private static final Main instance = new Main();
	private JTabbedPane menuTab;
	private JPanel tab1Panel,tab2Panel,orderPanel,selectPanel,orderBtnPanel,btnPanel;
	private JTable menuTable1,menuTable2,orderTable;
	private ArrayList<JPanel> tabPanelList = new ArrayList<>();
	private ArrayList<JTable> menuTableList = new ArrayList<>();
	private List<CategoryDTO> category= new ArrayList<>();
	private JButton initBtn, delBtn, addBtn,modBtn,customBtn,salesBtn,refundBtn,paymentBtn;
	private ArrayList<MenuDTO> orderList= new ArrayList<>();
	private String temp;
	private int totalPrice=0;
	private JLabel priceField;
	private MenuDTO menuDTO ;


	
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
	



	public MenuDTO getMenuDTO() {
		return menuDTO;
	}



	private JTabbedPane getJTabbedPane() {
		
			menuTab = new JTabbedPane();
			menuTab.setTabPlacement(JTabbedPane.TOP);
			
			
			category = CafeDAO.getInstance().getCategoryItems();			
	
			for(CategoryDTO dto : category) {
				if(dto.getVisibility()==0) continue;
				menuTab.addTab(dto.getKind(), getTabPanel());
			}
			
		return menuTab;
		}
	
	private JPanel getTabPanel() {	
			JPanel tabPanel = new JPanel();
			tabPanel.add(new JScrollPane(getMenuTable()));
			tabPanelList.add(tabPanel);
		
		return tabPanel;
	}
	
	private JTable getMenuTable() {
		
			//Table 수정 불가
			JTable menuTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			menuTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			refreshMenu(tabPanelList.size()+1, menuTable);
			
			menuTable.getColumn("메뉴명").setPreferredWidth(50);
			menuTable.getColumn("가격").setPreferredWidth(20);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
			//오른쪽클릭하면 수량 선택 창 구현해야함
			menuTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					
					//클릭한 셀의 행 인덱스 받아옴
					int rowIndex = menuTable.getSelectedRow();
					//menuTable 의 선택한 행 첫번째 칼럼에 있는 mname 받아와서
					//Menu 에 있는 데이터 찾기
					if(rowIndex !=-1) {
						String mname =(String) menuTable.getValueAt(rowIndex, 0);
						menuDTO = CafeDAO.getInstance().getMenuByName(mname);
						
						//더블 클릭시
						if(e.getClickCount()==2) {
							//CountDialog에서 menuDTO 의 수량 변경
							
							CountDialog countDialog = new CountDialog(main);
							countDialog.setVisible(true);
							//orderList에 원래 menuDTO 가 있었으면 수량만 변경하고 종료
//							for(MenuDTO dto2 : orderList) {
//								if(dto2.getMname().equals(menuDTO.getMname())) {
//									dto2.setCount(menuDTO.getCount());
//									System.out.println("main에서 출력"+dto2);
//									refreshOrderList();
//									return;
//								}}
							//없었으면 orderList에 추가
							System.out.println("더블클릭");
						}
						
						if(e.getClickCount()==1) {
							for(MenuDTO dto2 : orderList) {
								if(dto2.getMname().equals(menuDTO.getMname())) {
									dto2.setCount(dto2.getCount()+1);
									refreshOrderList();
									return;
								}
						}
							orderList.add(menuDTO);
							refreshOrderList();
							System.out.println("한번클릭");
						
					
					}
						//menuDTO의 수량 기본값은 1이지만 오른쪽클릭시 countDialog에서 수량 바꿨음
				//		orderList.add(menuDTO);
				//		refreshOrderList();

					}
				}		
			});
			menuTableList.add(menuTable);
		
			
		return menuTable;
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
			orderTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
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
				
		//		PaymentDialog paymentdialog = new PaymentDialog(main);
		//		paymentdialog.setVisible(true);
				
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
