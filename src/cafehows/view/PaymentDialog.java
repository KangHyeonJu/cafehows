//package cafehows.view;
//
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.GraphicsEnvironment;
//import java.awt.Point;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//
//import cafehows.model.MenuDTO;
//
//
//
//public class PaymentDialog extends JDialog{
//	private Main main;
//	private JPanel orderPanel;
//	private JLabel priceField,totalPrice;
//	
//
//	public PaymentDialog(Main main) {
//		this.main = main;
//	//	this.menuDTO = menuDTO;
//		this.setTitle("결제");
//		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//
//		this.setSize(300, 200);
//
//		this.getContentPane().add(main.getOrderPanel(), BorderLayout.CENTER);
//		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
//		locationCenter();
//	}
//	
//	public JPanel getOrderPanel() {
//		if(orderPanel==null) {
//			orderPanel = new JPanel();
//			orderPanel.setLayout(new BorderLayout());
//			JLabel label = new JLabel("주문 목록");
//			label.setAlignmentX(JLabel.CENTER);
//			label.setPreferredSize(new Dimension(70, 30));
//			label.setHorizontalAlignment(JLabel.CENTER);
//			orderPanel.add(label, BorderLayout.NORTH);
//			orderPanel.add(new JScrollPane(getOrderTable()),BorderLayout.CENTER);
//			
//	//		orderPanel.add(new JScrollPane(getSelectPanel()),BorderLayout.EAST);)
//		//	orderPanel.add(getOrderBtnPanel(),BorderLayout.SOUTH);
//			
//			JPanel totalField = new JPanel();
//			totalField.setLayout(new BoxLayout(totalField,BoxLayout.Y_AXIS));
//			
//			JPanel price = new JPanel();
//			JLabel priceLabel = new JLabel("총 가격");
//			priceField = new JLabel(Integer.toString(totalPrice));
//			price.add(priceLabel);
//			price.add(priceField);
//			
//			totalField.add(getOrderBtnPanel());
//			totalField.add(price);
//			
//		
//			
//			orderPanel.add(totalField,BorderLayout.SOUTH);
//			
//		}
//		return orderPanel;
//	}
//	
//	private JTable getOrderTable() {
//		if(orderTable == null) {
//			orderTable = new JTable() {
//				@Override
//				public boolean isCellEditable(int row, int col) {
//					return false;
//				}
//			};
//			orderTable.setAutoCreateRowSorter(true);
//			
//			DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
//			tableModel.addColumn("메뉴명");
//			tableModel.addColumn("가격");
//			tableModel.addColumn("수량");
//			tableModel.addColumn("아이스/핫");
//			
//			orderTable.addMouseListener(new MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//					
//					int rowIndex = orderTable.getSelectedRow();
//					if(rowIndex !=-1) {
//					temp =(String)orderTable.getValueAt(rowIndex, 0);
//						
//
//					}
//				}		
//			});
//			
////			TableCellEditor editor = new DefaultCellEditor(new JTextField());
////			editor.addCellEditorListener(new CellEditorListener() {
////			@Override
////			public void editingStopped(ChangeEvent e) {
////			// 수정을 끝내고 enter 를 입력하면 ChangeEvent가 도착한다.
////			String value = (String) editor.getCellEditorValue();
////			TableModel model = orderTable.getModel();
////			int rowIdx = orderTable.getSelectedRow();
////			int colIdx =orderTable.getSelectedColumn();
////
////			//현재 선택된 셀에서 편집이 이루어졌으므로 모델의 값을 갱신해준다.
////			model.setValueAt(value, rowIdx, colIdx);
////			}
////
////			@Override
////			public void editingCanceled(ChangeEvent e) {
////			// TODO Auto-generated method stub
////
////			}
////			});
////	
////			}
//			
//		//	refreshMenu();
//			
////			orderTable.getColumn("메뉴명").setPreferredWidth(50);
////			orderTable.getColumn("수량").setPreferredWidth(50);
////			orderTable.getColumn("가격").setPreferredWidth(20);
////			orderTable.getColumn("아이스/핫").setPreferredWidth(20);
//			
////			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
////			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
////			menuTable.getColumn("가격").setCellRenderer(ctcr);
////			
////			menuTable.addMouseListener(new MouseAdapter() {
////				public void mouseClicked(MouseEvent e) {
////					int rowIndex = menuTable.getSelectedRow();
////					if(rowIndex !=-1) {
////						int bno = (int)menuTable.getValueAt(rowIndex, 0);
////						
////					}
////				}		
////			});
//		}
//			
//		return orderTable;
//	}
////	public JPanel getSelectPanel() {
////		if(selectPanel == null) {
////			selectPanel = new JPanel();
////			selectPanel.setLayout(new BoxLayout(selectPanel,BoxLayout.Y_AXIS));
////			
////		}
////		return selectPanel;
////	}
//	
//	
//	public JPanel getOrderBtnPanel() {
//		if(orderBtnPanel == null) {
//			orderBtnPanel = new JPanel();
//	//		orderBtnPanel.setLayout(new BorderLayout());
//			orderBtnPanel.add(getDelBtn());
//			orderBtnPanel.add(getInitBtn());
//		}
//		return orderBtnPanel;
//	}
//	
//	public JButton getDelBtn() {
//		if(delBtn==null) {
//			delBtn = new JButton();
//			delBtn.setText("항목 삭제");
//			delBtn.addActionListener(e->{
//				
//				for(MenuDTO dto2 : orderList) {
//					if(dto2.getMname().equals(temp)) {
//						orderList.remove(dto2);
//						refreshOrderList();
//						return;
//					}
//				}
//
//			});
//		}
//		return delBtn;
//	}
//	
//	
//	public JButton getInitBtn() {
//		if(initBtn==null) {
//			initBtn = new JButton();
//			initBtn.setText("초기화");
//			initBtn.addActionListener(e->{
//			orderList.clear();
//			refreshOrderList();
//			});
//		}
//		return initBtn;
//	}
//	
//	private void locationCenter() {
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		Point centerPoint = ge.getCenterPoint();
//		int leftTopX = centerPoint.x - this.getWidth() / 2;
//		int leftTopY = centerPoint.y - this.getHeight() / 2;
//		this.setLocation(leftTopX, leftTopY);
//	}
//}
