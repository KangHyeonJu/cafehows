package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;

public class CustomerModify extends JDialog{

	private JPanel customerNum, pCenter, pSouth, pNotice;
	private JTextField txtCustomerNum;
	private JButton btnOk, btnCancel, btnDelete;
	private CafeDAO cafeDao = new CafeDAO();
	private CustomerDTO cDto = new CustomerDTO();
	private static List<CustomerDTO> customerList = CafeDAO.getInstance().getCustomerItems();
	private static List<CustomerDTO> customerList2 = CafeDAO.getInstance().getCustomerState();
	
	public CustomerModify() {
		this.setTitle("고객-등록/삭제");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		this.setModal(true); //상위 frame 클릭 불가
		this.getContentPane().add(getNotice(), BorderLayout.NORTH);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	
	public JPanel getNotice() {
		if(pNotice == null){
			pNotice = new JPanel();
			pNotice.add(new JLabel("010을 제외한 번호 8자리를 입력해주세요."));
		}
		return pNotice;
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
			txtCustomerNum = new JTextField(19);
		}
		return txtCustomerNum;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			//pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnOk());
			pSouth.add(getBtnDelete());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new RoundedButton();
			btnOk.setText("등록");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//입력제한
					if(txtCustomerNum.getText().length() == 8) {
						boolean flag = false;
						for(CustomerDTO c : customerList2) {
							if(c.getCno() == Integer.parseInt(txtCustomerNum.getText()))
								flag = true;
						}
						if(flag) {
							cDto.setCno(Integer.parseInt(txtCustomerNum.getText()));
							cafeDao.reSign(cDto);
						}else {
							cDto.setCno(Integer.parseInt(txtCustomerNum.getText()));
							cafeDao.insertCustomer(cDto);
						}
						CustomerDialog.refreshTable();
						CustomerModify.this.dispose();
					}else {
						JOptionPane.showMessageDialog(null, "다시 입력해 주세요.","오류",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return btnOk;
	}
	
	public JButton getBtnDelete() {
		if(btnDelete == null) {
			btnDelete = new RoundedButton();
			btnDelete.setText("삭제");
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int cno = Integer.parseInt(txtCustomerNum.getText());
					DefaultTableModel tableModel = (DefaultTableModel) CustomerDialog.getCustomerTable().getModel();
					for(int i=0; i<customerList.size(); i++) {
						if(cno == customerList.get(i).getCno()) {
							tableModel.removeRow(i);
						}
					}
					cafeDao.deleteCustomer(cDto, cno);
					CustomerDialog.refreshTable();
					CustomerModify.this.dispose();
				}
			});
		}
		return btnDelete;
	}
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new RoundedButton();
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
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
}
