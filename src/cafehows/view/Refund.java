package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.OrderDTO;

public class Refund extends JFrame{
	private JPanel orderNum, pSouth, pNorth;
	private JTextField txtOrderNum;
	private JButton btnOk, btnCancel;
	private static JTable jTable;
	private CafeDAO cafeDao = new CafeDAO();
	private static List<OrderDTO> orderList = CafeDAO.getInstance().getOrderItems();
	private static List<CustomerDTO> customerList = CafeDAO.getInstance().getCustomerItems();
	private CustomerDTO cDto = new CustomerDTO();
	
	public Refund() {
		this.setTitle("환불-주문번호입력");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		
		this.getContentPane().add(getPNorth(), BorderLayout.NORTH);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.getContentPane().add(new JScrollPane(orderTable()), BorderLayout.CENTER);
	}

	public JPanel getPNorth() {
		if(pNorth==null) {
			pNorth = new JPanel();
			pNorth.add(getOrderNum());
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
			txtOrderNum.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int ono = Integer.parseInt(txtOrderNum.getText());
					for(int i=0;i<orderList.size();i++) {
						if(ono == orderList.get(i).getOno()){
							final DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
							Object[] rowData = {orderList.get(i).getOno(), orderList.get(i).getDate(), orderList.get(i).getPrice(), orderList.get(i).getFinalprice()};
							tableModel.addRow(rowData);
						}
					}
				}
			});
		}
		return txtOrderNum;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.setBackground(Color.WHITE);
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
					int customerPoint = 0;
					for(int i=0;i<orderList.size();i++) {
						if(ono == orderList.get(i).getOno()){
							customerPoint = orderList.get(i).getPrice() - orderList.get(i).getFinalprice();
							for(int j=0; j<customerList.size(); j++) {
								if(orderList.get(i).getCno() == customerList.get(j).getCno()) {
									cDto.setPoint(customerList.get(j).getPoint() + customerPoint);
									cafeDao.updatePoint(cDto, customerList.get(j).getCno());
								}
							}
						}
					}
					
					cafeDao.deleteOrder(ono);
					Refund.this.dispose();
				}
			});
		}
		return btnOk;
	}
	
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

	public static JTable orderTable() {
		if(jTable == null) {
			jTable = new JTable();
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
			
			tableModel.addColumn("주문번호");
			tableModel.addColumn("날짜");
			tableModel.addColumn("금액");
			tableModel.addColumn("최종금액");
			
			jTable.getColumn("주문번호").setPreferredWidth(50);
			jTable.getColumn("날짜").setPreferredWidth(100);
			jTable.getColumn("금액").setPreferredWidth(50);
			jTable.getColumn("최종금액").setPreferredWidth(50);
		}
		return jTable;
	}
}
