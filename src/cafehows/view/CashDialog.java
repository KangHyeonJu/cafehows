package cafehows.view;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;
import cafehows.model.OrderDTO;

public class CashDialog extends JDialog{
	private Main main;
	private JPanel pCenter, pSouth, pAmount, pReceived, pChange;
	private JButton btnOk, btnCancel;
	private JTextField txtReceived, txtChange;
	private int ono;
	private JLabel onoField;
	
	public CashDialog() {
		this.setTitle("현금결제");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel(new GridLayout(3,1));
			pCenter.add(getPAmount());
			pCenter.add(getPReceived());
			pCenter.add(getPChange());
			
		}
		return pCenter;
	}
	
	public JPanel getPAmount() {
		if(pAmount==null) {
			pAmount = new JPanel();
			pAmount.add(new JLabel("결제 금액", JLabel.CENTER));
			JLabel finalPriceField = new JLabel(Integer.toString(main.getTotalPrice()-PaymentDialog.getUsePoint()));
			pAmount.add(finalPriceField);
		}
		return pAmount;
	}
	
	public JPanel getPReceived() {
		if(pReceived==null) {
			pReceived = new JPanel();
			pReceived.add(new JLabel("받은 금액", JLabel.CENTER));
			pReceived.add(getTxtReceived());
		}
		return pReceived;
	}
	
	public JPanel getPChange() {
		if(pChange==null) {
			pChange = new JPanel();
			pChange.add(new JLabel("거스름돈", JLabel.CENTER));
			pChange.add(getTxtChange());
			
		}
		return pChange;
	}
	
	public JTextField getTxtReceived() {
		if(txtReceived==null) {
			txtReceived = new JTextField(10);
			txtReceived.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int received = Integer.parseInt(getTxtReceived().getText());
					String change = Integer.toString(received-main.getTotalPrice()-PaymentDialog.getUsePoint());
					getTxtChange().setText(change);
				}
			});
			
		}
		return txtReceived;
	}
	
	public JTextField getTxtChange() {
		if(txtChange==null) {
			txtChange = new JTextField(10);
			
		}
		return txtChange;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			//pSouth.setBackground(Color.WHITE);
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
					// orderlist date 저장, ono 생성, cno,price, finalprice 저장
					OrderDTO orderDTO= new OrderDTO();
					orderDTO.setCno(PaymentDialog.getCno());
					orderDTO.setPrice(main.getTotalPrice());
					orderDTO.setFinalprice(main.getTotalPrice()-PaymentDialog.getUsePoint());
					ono = CafeDAO.getInstance().insertOrderList(orderDTO);
					onoField.setText(Integer.toString(ono));
					
					//결제하면 menusales count++, ono 저장,mno
					for(MenuDTO m : main.getOrderList()) {
						m.setOno(ono);
						m.setCumCount(m.getCumCount()+m.getCount());
						System.out.println(m);
						CafeDAO.getInstance().insertMenuSales(m);
					}
					
					//customer point 차감, recdate 갱신
					CustomerDTO cDTO = new CustomerDTO();
					cDTO.setPoint(PaymentDialog.getPoint()-PaymentDialog.getUsePoint());
					CafeDAO.getInstance().updatePoint(cDTO, PaymentDialog.getCno());
					main.getOrderList().clear();
					main.refreshOrderList();
					CashDialog.this.dispose();
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
					CashDialog.this.dispose();
				}
			});
		}
		return btnCancel;
	}
}
