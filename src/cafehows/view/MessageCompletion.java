package cafehows.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class MessageCompletion extends JFrame{
	private JLabel jLabel;
	public MessageCompletion() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.getContentPane().add(getJLabel());
		this.setSize(200,150);
	}
	
	public JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new JLabel();
			jLabel.setText("문자 전송 완료");
			jLabel.setHorizontalAlignment(JLabel.CENTER);
		}
		return jLabel;
	}
}
