package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.OrderDTO;

public class Refund extends JDialog{
	private JPanel orderNum, pSouth, pNorth, pCenter;
	private JTextField txtOrderNum;
	private JButton btnOk, btnCancel, btnOrderList, btnreload;
	private static JTable OrderListTable;
//	private CafeDAO cafeDao = new CafeDAO();
	private static List<OrderDTO> orderList = CafeDAO.getInstance().getOrderItems();
	private static List<CustomerDTO> customerList = CafeDAO.getInstance().getCustomerItems();
	private CustomerDTO cDto = new CustomerDTO();
	
	public Refund() {
		this.setTitle("환불-주문번호입력");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 300);
		this.setModal(true);
		this.getContentPane().add(getPNorth(), BorderLayout.NORTH);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		locationCenter();
	}

	public JPanel getPNorth() {
		if(pNorth==null) {
			pNorth = new JPanel();
			pNorth.add(getOrderNum());
			//pNorth.add(getOrderNumBtn());
			pNorth.add(getReloadBtn());
		}
		return pNorth;
	}
	
	public JPanel getOrderNum() {
		if(orderNum==null) {
			orderNum = new JPanel();
			orderNum.add(new JLabel("주문 번호", JLabel.CENTER));
			orderNum.add(getTxtOrderNum());
		}
		return orderNum;
	}
	
	public JTextField getTxtOrderNum() {
		if(txtOrderNum==null) {
			txtOrderNum = new JTextField(20);
			txtOrderNum.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					searchOrderKeyword(txtOrderNum.getText());
				}
			});
		}
		return txtOrderNum;
	}
	
	public JButton getReloadBtn() {
		if (btnreload == null) {
			btnreload = new JButton();
			btnreload.setText("초기화");
			btnreload.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					refreshTable();
					txtOrderNum.setText("");
				}
			});
		}
		return btnreload;
	}
	
	
//	public JButton getOrderNumBtn() {
//		if(btnOrderList == null) {
//			btnOrderList = new JButton();
//			btnOrderList.setText("검색");
//			btnOrderList.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					searchOrderKeyword(txtOrderNum.getText());
//				}
//			});
//		}
//		return btnOrderList;
//	}
	
	public void searchOrderKeyword(String keyword) {
		DefaultTableModel tableModel = (DefaultTableModel) OrderListTable.getModel();
		tableModel.setNumRows(0);
		for (OrderDTO dto : CafeDAO.getInstance().searchOrderKeyword(keyword)) {
			Object[] rowData = { dto.getOno(), dto.getDate(), dto.getPrice(), dto.getFinalprice() };
			tableModel.addRow(rowData);
		}
	}
	
	
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnOk());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("환불");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int ono = Integer.parseInt(txtOrderNum.getText());
//					int customerPoint = 0;
//					for(int i=0;i<orderList.size();i++) {
//						if(ono == orderList.get(i).getOno()){
//							customerPoint = orderList.get(i).getPrice() - orderList.get(i).getFinalprice();
//							for(int j=0; j<customerList.size(); j++) {
//								if(orderList.get(i).getCno() == customerList.get(j).getCno()) {
//									cDto.setPoint(customerList.get(j).getPoint() + customerPoint);
//									cafeDao.updatePoint(cDto, customerList.get(j).getCno());
//								}
//							}
//						}
//					}
//					
//					cafeDao.deleteOrder(ono);
					
					OrderDTO orderDTO = CafeDAO.getInstance().getOrderItembyOno(ono);
			
					CafeDAO.getInstance().deleteMenuSales(ono);
					CafeDAO.getInstance().deleteOrder(ono);
					int cno = orderDTO.getCno();
					int price = orderDTO.getPrice();
					int finalPrice = orderDTO.getFinalprice();
					CustomerDTO cDTO = CafeDAO.getInstance().getCustomerItemByCno(cno);
					cDTO.setPoint(cDTO.getPoint()+(price-finalPrice)-(int)(finalPrice*0.05));
					CafeDAO.getInstance().updatePoint(cDTO, cno);
					
					dispose();
					
				}	
			});
		}
		return btnOk;
	}

//	//결제하면 menusales count++, ono 저장,mno
//	for(MenuDTO m : main.getOrderList()) {
//		m.setOno(ono);
//		m.setCumCount(m.getCumCount()+m.getCount());
//		System.out.println(m);
//		CafeDAO.getInstance().insertMenuSales(m);
//	}
//	
//	//customer point 차감, recdate 갱신
//	CustomerDTO cDTO = new CustomerDTO();
//	cDTO.setPoint(point-usePoint);
//	CafeDAO.getInstance().updatePoint(cDTO, cno);
//	main.getOrderList().clear();
//	main.refreshOrderList();
//	dispose();
	
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("취소");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Refund.this.dispose();
				}
			});
		}
		return btnCancel;
	}

	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			JScrollPane jScrollPane = new JScrollPane(orderTable());
			jScrollPane.setPreferredSize(new Dimension(450,180));
			pCenter.add(jScrollPane);
			refreshTable();
		}
		return pCenter;
	}
	public static JTable orderTable() {
		if(OrderListTable == null) {
			OrderListTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			DefaultTableModel tableModel = (DefaultTableModel) OrderListTable.getModel();
			
			tableModel.addColumn("주문번호");
			tableModel.addColumn("날짜");
			tableModel.addColumn("금액");
			tableModel.addColumn("최종금액");
			
			OrderListTable.getColumn("주문번호").setPreferredWidth(50);
			OrderListTable.getColumn("날짜").setPreferredWidth(100);
			OrderListTable.getColumn("금액").setPreferredWidth(50);
			OrderListTable.getColumn("최종금액").setPreferredWidth(50);
			
			for(OrderDTO dto : CafeDAO.getInstance().getOrderItems()) {
				Object[] rowData = { dto.getOno(), dto.getDate(), dto.getPrice(), dto.getFinalprice() };
				tableModel.addRow(rowData);
			}
			
			//테이블 내부 중앙정렬
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = OrderListTable.getColumnModel();
			for (int i = 0; i < 4; i++)
				tcm.getColumn(i).setCellRenderer(dtcr);
		}
		return OrderListTable;
	}
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) OrderListTable.getModel();
		tableModel.setNumRows(0);
		for (OrderDTO dto : CafeDAO.getInstance().getOrderItems()) {
			Object[] rowData = { dto.getOno(), dto.getDate(), dto.getPrice(), dto.getFinalprice() };
			tableModel.addRow(rowData);
		}
	}
}
