package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Refund extends JFrame{
	private JPanel orderNum, pCenter, pSouth;
	private JTextField txtOrderNum;
	private JButton btnOk, btnCancel;
	
	public Refund() {
		this.setTitle("환불-주문번호입력");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getOrderNum());
		}
		return pCenter;
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
					
				}
			});
		}
		return btnCancel;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			Refund rF = new Refund();
        	rF.setVisible(true);
	    });

	}

}
