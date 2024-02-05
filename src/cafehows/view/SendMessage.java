package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class SendMessage extends JFrame{
	private JPanel pCenter, pSouth;
	private JTextArea txtMessage;
	private JButton btnOk, btnCancel;
	
	public SendMessage() {
		this.setTitle("문자 전송");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getTxtMessage());
		}
		return pCenter;
	}

	public JTextArea getTxtMessage() {
		if(txtMessage==null) {
			txtMessage = new JTextArea(10, 20);
		}
		return txtMessage;
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
			btnOk.setText("전송");
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
			SendMessage sM = new SendMessage();
        	sM.setVisible(true);
	    });

	}

}
