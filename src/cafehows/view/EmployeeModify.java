package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
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

public class EmployeeModify extends JDialog{

	private JPanel customerNum, pCenter, pSouth, pNotice;
	private JTextField txtCustomerNum,txtEName;
	private JButton btnOk, btnCancel, btnDelete;
	
	
	public EmployeeModify() {
		this.setTitle("직원-등록/수정");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		this.setModal(true); //상위 frame 클릭 불가
		this.getContentPane().add(getTab1Panel(), BorderLayout.CENTER);

		locationCenter();
	}
	private JPanel getTab1Panel() {	
		JPanel tab1Panel = new JPanel(new BorderLayout());
		JPanel pCenter = new JPanel(new GridLayout(3,1));
		pCenter.add(getTab1ENo());
		pCenter.add(getEName());
		pCenter.add(getEStatus());
		tab1Panel.add(pCenter,BorderLayout.CENTER);
		tab1Panel.add(getTab1PSouth(),BorderLayout.SOUTH);
	
	
	return tab1Panel;
	}

	public JPanel getTab1ENo() {
		JPanel pENo = new JPanel();
		JLabel label = new JLabel("직원번호",JLabel.CENTER);
		label.setPreferredSize(new Dimension(50,30));
	    pENo.add(label);
	    JLabel tab1TxtENo = new JLabel();
	    tab1TxtENo.setPreferredSize(new Dimension(50,30));
		pENo.add(tab1TxtENo);
		pENo.add(getGenerateNoBtn());
		//번호 데이터에서 받아와야함 자동생성

	return pENo;
}
	public JButton getGenerateNoBtn() {
		JButton generateNoBtn = new RoundedButton();
		generateNoBtn.setText("생성");
		generateNoBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					
			}
		});
	
	return generateNoBtn;
		
	}
	
	public JPanel getEName() {
		JPanel pEName = new JPanel();
		JLabel label = new JLabel("직원이름",JLabel.CENTER);
		label.setPreferredSize(new Dimension(50,30));
	    pEName.add(label);
	    txtEName = new JTextField();
		txtEName.setPreferredSize(new Dimension(200,30));
		pEName.add(txtEName);

	return pEName;
}
	
	public JPanel getEStatus() {
		JPanel pEStatus = new JPanel();
		JLabel label = new JLabel("상태",JLabel.CENTER);
		label.setPreferredSize(new Dimension(50,30));
	    pEStatus.add(label);
	    //콤보박스 추가
	    return pEStatus;
	}

	public JPanel getTab1PSouth() {
		
		JPanel pSouth = new JPanel();
			pSouth.add(getTab1BtnOk());
			pSouth.add(getBtnModify());
			pSouth.add(getBtnCancel());
			
		
		return pSouth;
	}

	
	public JButton getTab1BtnOk() {
	
			JButton btnOk = new RoundedButton();
			btnOk.setText("등록");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						
				}
			});
		
		return btnOk;
	}
	
	public JButton getBtnModify() {
		
		JButton btnModify = new RoundedButton();
		btnModify.setText("수정");
		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			

			}
		});

	return btnModify;
}
	
	public JButton getBtnCancel() {
		
			JButton btnCancel = new RoundedButton();
			btnCancel.setText("취소");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
				

				}
			});
	
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
