package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;
import cafehows.model.MenuDTO;
import lombok.Data;


public class Main extends JFrame{
	private Main main;
//	private static final Main instance = new Main();
	private JTabbedPane menuTab;
	private JPanel tab1Panel,tab2Panel,orderPanel,selectPanel,orderBtnPanel,btnPanel;

	private JTable menuTable1,menuTable2,orderTable;
	private static JTable menuTable;
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
	
	
	public void setOrderList(ArrayList<MenuDTO> orderList) {
		this.orderList = orderList;
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
				menuTab.addTab(dto.getKind(), getTabPanel(dto.getCano()));
			}
			
		return menuTab;
		}
	
	private JPanel getTabPanel(int cano) {	
			JPanel tabPanel = new JPanel();
			tabPanel.add(new JScrollPane(getMenuTable(cano)));
			tabPanelList.add(tabPanel);
		
		return tabPanel;
	}
	

	private JTable getMenuTable(int cano) {

			//Table 수정 불가
			JTable menuTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			menuTable.setAutoCreateRowSorter(true);
			menuTable.getTableHeader().setReorderingAllowed(false);
			menuTable.getTableHeader().setResizingAllowed(false);
			
			DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			

			
			refreshMenu(cano, menuTable);
			
			menuTable.getColumn("메뉴명").setPreferredWidth(50);
			menuTable.getColumn("가격").setPreferredWidth(20);

//			
			//오른쪽클릭하면 수량 선택 창 구현해야함
			menuTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					
					if(SwingUtilities.isRightMouseButton(e)) {
						int rowIndex = menuTable.rowAtPoint(e.getPoint());
						String mname =(String) menuTable.getValueAt(rowIndex, 0);
						menuDTO = CafeDAO.getInstance().getMenuByName(mname);
						System.out.println(rowIndex);
						CountDialog countDialog = new CountDialog(main);
						countDialog.setVisible(true);
						return;
					}
		
					//클릭한 셀의 행 인덱스 받아옴
					int rowIndex = menuTable.getSelectedRow();
					//menuTable 의 선택한 행 첫번째 칼럼에 있는 mname 받아와서
					//Menu 에 있는 데이터 찾기
					if(rowIndex !=-1) {
						String mname =(String) menuTable.getValueAt(rowIndex, 0);
						menuDTO = CafeDAO.getInstance().getMenuByName(mname);
						
						
						if(e.getClickCount()==1) {
							for(MenuDTO dto2 : orderList) {
								if(dto2.getMname().equals(menuDTO.getMname()
										)&&dto2.getIce()==menuDTO.getIce()) {
									dto2.setCount(dto2.getCount()+1);
									refreshOrderList();
									return;
								}
							}
							orderList.add(menuDTO);
							refreshOrderList();
					
								
						}

						}
						
						
						
				
				menuTableList.add(menuTable);
			}
			
		});
		return menuTable;
	}

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
	
	public JTable getOrderTable() {
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
			delBtn = new RoundedButton();
			delBtn.setText("항목 삭제");
			//delBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
			initBtn = new RoundedButton();
			initBtn.setText("초기화");
			initBtn.addActionListener(e->{
			orderList.clear();
			refreshOrderList();
			refreshTab();
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
			addBtn.setBackground(new Color(200, 221, 242));
			addBtn.setBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)));
			addBtn.setPreferredSize(new Dimension(130,130));
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
			modBtn.setText("메뉴 수정/숨김");
			modBtn.setBackground(new Color(200, 221, 242));
			modBtn.setBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)));
			modBtn.setPreferredSize(new Dimension(130,130));
			modBtn.addActionListener(e->{
				MenuMDS menuMDS= new MenuMDS(main);
				menuMDS.setVisible(true);
			});
	
		}
		return modBtn;
	}
	public JButton getCustomBtn() {
		if(customBtn==null) {
			customBtn = new JButton();
			customBtn.setText("고객 관리");
			customBtn.setBackground(new Color(200, 221, 242));
			customBtn.setBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)));
			customBtn.setPreferredSize(new Dimension(130,130));
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
			salesBtn.setBackground(new Color(200, 221, 242));
			salesBtn.setBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)));
			salesBtn.setPreferredSize(new Dimension(130,130));
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
			refundBtn.setBackground(new Color(200, 221, 242));
			refundBtn.setBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)));
			refundBtn.setPreferredSize(new Dimension(130,130));
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
			paymentBtn.setBackground(new Color(200, 221, 242));
			paymentBtn.setBorder(BorderFactory.createLineBorder(new Color(99, 130, 191)));
			paymentBtn.setPreferredSize(new Dimension(130,130));
			paymentBtn.addActionListener(e->{
				
				PaymentDialog paymentDialog = new PaymentDialog(main);
				paymentDialog.setVisible(true);

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
	
	public void refreshTab() {
		Main.this.menuTab.removeAll();
		Main.this.remove(menuTab);
		tabPanelList.clear();
		menuTableList.clear();
		category.clear();
		this.getContentPane().add(getJTabbedPane(), BorderLayout.WEST);
		Main.this.revalidate();
	}
	
	public void refreshOrderList() {
		DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
		tableModel.setNumRows(0);
		totalPrice = 0;
		for(MenuDTO dto : orderList) {
//			JTextField inputCount = new JTextField(4);
//			JTextField inputIce = new JTextField(4);
			String ice = dto.getIce()==1 ? "ICE" : "HOT";
			Object[] rowData = {dto.getMname(), dto.getPrice(),dto.getCount(),ice};
			tableModel.addRow(rowData);
			totalPrice+=dto.getPrice()*dto.getCount();
			System.out.println(totalPrice);
		}
		main.revalidate();
		priceField.setText(Integer.toString(totalPrice));
	
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Main main = new Main();
			main.setVisible(true);
		
		});
	}
}