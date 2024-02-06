package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.OrderDTO;


public class UsePoints extends JFrame{
	private JPanel pCenter, pCono, pPoint, pUsePoint, pSouth, pConoIn;
	private JTextField txtCono, txtPoint, txtUsePoint;
	private JButton btnOk, btnCancel;
	private CustomerDTO cDto = new CustomerDTO();
	private CafeDAO dao = new CafeDAO();
	private static List<OrderDTO> orderList = CafeDAO.getInstance().getOrderItems();
	private static List<CustomerDTO> customerList = CafeDAO.getInstance().getCustomerItems();
	
	public UsePoints() {
		this.setTitle("회원 포인트 사용");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);

		//this.getContentPane().setLayout(null).add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getPCono());
			pCenter.add(getPPoint());
			pCenter.add(getPUsePoint());
			locationCenter();
			//pCenter.setBounds(100, 50, 70, 60);
		}
		return pCenter;
	}
	
	public JPanel getPCono() {
		if(pCono==null) {
			pCono = new JPanel();
			pConoIn = new JPanel();
			pConoIn.add(new JLabel("회원 번호", JLabel.CENTER));
			pCono.add(pConoIn);
			pCono.add(getTxtCono());
		}
		return pCono;
	}	

	public JPanel getPPoint() {
		if(pPoint==null) {
			pPoint = new JPanel();
			pPoint.add(new JLabel("보유 포인트", JLabel.CENTER));
			pPoint.add(getTxtPoint());
		}
		return pPoint;
	}		
	
	public JPanel getPUsePoint() {
		if(pUsePoint == null) {
			pUsePoint = new JPanel();
			pUsePoint.add(new JLabel("포인트 사용", JLabel.CENTER));
			pUsePoint.add(getTxtUsePoint());
		}
		return pUsePoint;
	}
	
	public JTextField getTxtCono() {
		if(txtCono == null) {
			txtCono = new JTextField(20);
			txtCono.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int customerNum = Integer.parseInt(txtCono.getText());
					int customerPoint = 0;
					
					if(txtCono.getText().length() == 8) {
						for(int i=0;i<customerList.size();i++) {
							if(customerNum == customerList.get(i).getCno()){
								customerPoint = customerList.get(i).getPoint();
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "8자리를 입력해주세요.","오류",JOptionPane.ERROR_MESSAGE);
					}
					
					getTxtPoint().setText(Integer.toString(customerPoint));
				}
			});
		}
		return txtCono;
	}
	
	public JTextField getTxtPoint() {
		if(txtPoint == null) {
			txtPoint = new JTextField(20);
		}
		return txtPoint;
	}
	
	public JTextField getTxtUsePoint() {
		if(txtUsePoint == null) {
			txtUsePoint = new JTextField(20);
		}
		return txtUsePoint;
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
			btnOk.setText("결제");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int usePoint = Integer.parseInt(getTxtUsePoint().getText());
					int point = Integer.parseInt(getTxtPoint().getText());
					int cno = Integer.parseInt(getTxtCono().getText());
					cDto.setPoint(point-usePoint);
					dao.updatePoint(cDto, cno);
					
					UsePoints.this.dispose();
					txtCono.setText("");
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
					UsePoints.this.dispose();
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
