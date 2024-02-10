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
import javax.swing.JComboBox;
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
import cafehows.model.EmployeeDTO;
import cafehows.model.MenuDTO;

public class EmployeeModify extends JDialog{

	private JPanel customerNum, pCenter, pSouth, pNotice;
	private JTextField txtCustomerNum,txtEName,txtENo;
	private JButton btnOk, btnCancel, btnDelete;
	private String statusTemp = "재직";
	private PayrollCostDialog payrollCostDialog;
	
	public EmployeeModify(PayrollCostDialog payrollCostDialog) {
		this.payrollCostDialog = payrollCostDialog;
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
	    txtENo = new JTextField();
	    txtENo.setPreferredSize(new Dimension(50,30));
		pENo.add(txtENo);

	return pENo;
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
	    pEStatus.add(getComboStatus());
	    
	    //콤보박스 추가
	    return pEStatus;
	}
	
	public JComboBox getComboStatus() {
		

			String[] statusString = {"재직","휴직","퇴직"};
			JComboBox comboStatus = new JComboBox(statusString);
			
			
			comboStatus.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					statusTemp = comboStatus.getSelectedItem().toString();
				}
			});
		
		return comboStatus;
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
					EmployeeDTO employee = new EmployeeDTO();
					employee.setEname(txtEName.getText());
					System.out.println(employee.getEname());
					int status = 0;
					if(statusTemp.equals("재직")) status=1;
					else if(statusTemp.equals("휴직")) status=2;
					else if(statusTemp.equals("퇴직")) status=3;
					employee.setStatus(status);
					
					int eno = CafeDAO.getInstance().insertEmployee(employee);
					txtENo.setText(Integer.toString(eno));
					payrollCostDialog.refreshEmployeeTable();
					dispose();
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
				EmployeeDTO employee = new EmployeeDTO();
				employee.setEno(Integer.parseInt(txtENo.getText()));
				employee.setEname(txtEName.getText());
				System.out.println(employee.getEname());
				int status = 0;
				if(statusTemp.equals("재직")) status=1;
				else if(statusTemp.equals("휴직")) status=2;
				else if(statusTemp.equals("퇴직")) status=3;
				employee.setStatus(status);
				
				CafeDAO.getInstance().updateEmployee(employee);
				payrollCostDialog.refreshEmployeeTable();
				dispose();

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
					dispose();

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
