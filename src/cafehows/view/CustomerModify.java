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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;

public class CustomerModify extends JFrame{

	private JPanel customerNum, pCenter, pSouth;
	private JTextField txtCustomerNum;
	private JButton btnOk, btnCancel, btnDelete;
	private CafeDAO cafeDao = new CafeDAO();
	private CustomerDTO cDto = new CustomerDTO();
	private static List<CustomerDTO> customerList = CafeDAO.getInstance().getCustomerItems();
	
	public CustomerModify() {
		this.setTitle("고객-등록/삭제");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getCustomerNum());
		}
		return pCenter;
	}
	
	public JPanel getCustomerNum() {
		if(customerNum==null) {
			customerNum = new JPanel();
			customerNum.add(new JLabel("회원 번호", JLabel.CENTER));
			customerNum.add(getTxtCustomerNum());
		}
		return customerNum;
	}
	
	public JTextField getTxtCustomerNum() {
		if(txtCustomerNum==null) {
			txtCustomerNum = new JTextField(20);
		}
		return txtCustomerNum;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnOk());
			pSouth.add(getBtnDelete());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("등록");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int cno = Integer.parseInt(txtCustomerNum.getText());
					cDto.setCno(cno);
					//cafeDao.insertCustomer(cDto);
					
					DefaultTableModel tableModel = (DefaultTableModel) CustomerDialog.getCustomerTable().getModel();
					int i = CustomerDialog.getCustomerTable().getRowCount();
					Object[] rowData = {customerList.get(i).getCno(), customerList.get(i).getPoint(), customerList.get(i).getRecdate()};
					tableModel.addRow(rowData);
					CustomerModify.this.dispose();
				}
			});
		}
		return btnOk;
	}
	
	public JButton getBtnDelete() {
		if(btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("삭제");
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int cno = Integer.parseInt(txtCustomerNum.getText());
					cafeDao.deleteCustomer(cno);
					CustomerModify.this.dispose();
				}
			});
		}
		return btnDelete;
	}
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("취소");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CustomerModify.this.dispose();
				}
			});
		}
		return btnCancel;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			CustomerModify cM = new CustomerModify();
        	cM.setVisible(true);
	    });

	}

}
