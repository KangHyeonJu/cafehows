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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;
import cafehows.model.OrderDTO;

public class CashDialog extends JDialog{
	private Main main;
	private PaymentDialog paymentDialog;
	private JPanel pCenter, pSouth, pAmount, pReceived, pChange;
	private JButton btnOk, btnCancel;
	private JTextField txtReceived, txtChange;
	private int ono,received;
	private JLabel onoField;
	
	
	public CashDialog( Main main,PaymentDialog paymentDialog) {
		this.paymentDialog = paymentDialog;
		this.main = main;
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
			JLabel finalPriceField = new JLabel(Integer.toString(paymentDialog.getFinalPrice()));
			pAmount.add(finalPriceField);
		}
		return pAmount;
	}
	
	public JPanel getPReceived() {
		if(pReceived==null) {
			pReceived = new JPanel();
			pReceived.add(new JLabel("받은 금액", JLabel.CENTER));
			pReceived.add(getTxtReceived());
			pReceived.add(getBtn());
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
	public JButton getBtn() {
		JButton btn = new JButton("입력");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				received = Integer.parseInt(getTxtReceived().getText());
				if(	paymentDialog.getFinalPrice()<=received) {
					//received = Integer.parseInt(getTxtReceived().getText());

					String change = Integer.toString(received-paymentDialog.getFinalPrice());

					getTxtChange().setText(change);
				}
				else {
					JOptionPane.showMessageDialog(null, "금액이 부족합니다.","오류",JOptionPane.ERROR_MESSAGE);
				}	
				
			}
		});
		return btn;
	}
	
	public JTextField getTxtReceived() {
		if(txtReceived==null) {
			txtReceived = new JTextField(10);
			txtReceived.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					received = Integer.parseInt(getTxtReceived().getText());
//
//					String change = Integer.toString(received-paymentDialog.getFinalPrice());
//
//					getTxtChange().setText(change);
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
			btnOk = new RoundedButton();
			btnOk.setText("결제");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// orderlist date 저장, ono 생성, cno,price, finalprice 저장
					OrderDTO orderDTO= new OrderDTO();
					orderDTO.setCno(paymentDialog.getCno());
					orderDTO.setPrice(main.getTotalPrice());
					orderDTO.setFinalprice(main.getTotalPrice()-paymentDialog.getUsePoint());
					ono = CafeDAO.getInstance().insertOrderList(orderDTO);
					
					paymentDialog.getOnoField().setText(Integer.toString(ono));
					
					//결제하면 menusales count++, ono 저장,mno
					for(MenuDTO m : main.getOrderList()) {
						m.setOno(ono);
						m.setCumCount(m.getCumCount()+m.getCount());
						System.out.println(m);
						CafeDAO.getInstance().insertMenuSales(m);
					}
					
					
					//customer point 차감, recdate 갱신
					if(paymentDialog.getCno()!=0 && paymentDialog.getCno()!= -1) {
					CustomerDTO cDTO = new CustomerDTO();

					cDTO.setPoint(paymentDialog.getPoint()-paymentDialog.getUsePoint()+(int)(paymentDialog.getFinalPrice()*0.03));

					CafeDAO.getInstance().updatePoint(cDTO, paymentDialog.getPhonenumber());
		
					}
					main.getOrderList().clear();
					main.refreshOrderList();
					dispose();

					paymentDialog.dispose();
				
		
				}
			});
		}
		return btnOk;
	}
	
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new RoundedButton();
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
