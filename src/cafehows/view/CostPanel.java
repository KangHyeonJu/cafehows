package cafehows.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CostPanel extends JPanel{

	//test
	private CostPanel board;
	private JPanel pSouth, pCenter, prent, pPayrollCost, pMaterialCost, pProfit;
	private JButton btnInsert, btnCancel, btnCal;
	private JTextField txtRent, txtPayrollCost, txtMaterialCost, resultField;
	
	
	public CostPanel() {
		this.board = this;

		this.add(getPCenter(),BorderLayout.CENTER);
		this.add(getPSouth(), BorderLayout.SOUTH);
		this.setSize(600, 450);
	}
	
	//임대료 : rent 인건비 : payrollCost 재료비 : materialCost 이윤 : profit
	
	
	//화면 구성
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel(new GridLayout(4,1));
			pCenter.add(getRent());
			pCenter.add(getpayrollCost());
			pCenter.add(getMaterialCost());
			pCenter.add(getProfit());
		}
		return pCenter;
	}
	
	//임대료 입력
	public JPanel getRent() {
		if(prent == null) {
			prent = new JPanel();
			JLabel label = new JLabel("임대료",JLabel.CENTER);
			label.setPreferredSize(new Dimension(50,30));
			prent.add(label);
			if(txtRent == null) {
				txtRent = new JTextField();
				txtRent.setPreferredSize(new Dimension(250,30));
			}
			prent.add(txtRent);
		}
		return prent;
	}
	
	//인건비 입력
	public JPanel getpayrollCost() {
		if(pPayrollCost == null) {
			pPayrollCost = new JPanel();
			JLabel label = new JLabel("인건비",JLabel.CENTER);
			label.setPreferredSize(new Dimension(50,30));
			pPayrollCost.add(label);
			if(txtPayrollCost == null) {
				txtPayrollCost = new JTextField();
				txtPayrollCost.setPreferredSize(new Dimension(250,30));
			}
			pPayrollCost.add(txtPayrollCost);
		}
		return pPayrollCost;
	}
	
	//재료비 입력
	public JPanel getMaterialCost() {
		if(pMaterialCost == null) {
			pMaterialCost = new JPanel();
			JLabel label = new JLabel("재료비",JLabel.CENTER);
			label.setPreferredSize(new Dimension(50,30));
			pMaterialCost.add(label);
			if(txtMaterialCost == null) {
				txtMaterialCost = new JTextField();
				txtMaterialCost.setPreferredSize(new Dimension(250,30));
			}
			pMaterialCost.add(txtMaterialCost);
		}
		return pMaterialCost;
	}
	
	//이윤
	public JPanel getProfit() {
		if(pProfit == null) {
			pProfit = new JPanel();
			JLabel label = new JLabel("이윤",JLabel.CENTER);
			label.setPreferredSize(new Dimension(50,30));
			pProfit.add(label);
			if(resultField == null) {
				resultField = new JTextField();
				resultField.setPreferredSize(new Dimension(250,30));
			}
			pProfit.add(resultField);
		}
		return pProfit;
	}
	
	//이윤 계산
	private void calProfit() {
		try {
			double rent = Double.parseDouble(txtRent.getText());
			double material = Double.parseDouble(txtMaterialCost.getText());
			double payroll = Double.parseDouble(txtPayrollCost.getText());
			//총액 받아오기
			double result = rent - payroll - material ; //임시
			resultField.setText(Double.toString(result));
		}catch (NumberFormatException e) {
			
		}
	}
	
	
	//하단
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnCal());
			pSouth.add(getBtnInsert());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	//계산 버튼
	public JButton getBtnCal() {
		if(btnCal == null) {
			btnCal = new JButton();
			btnCal.setText("계산");
			btnCal.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					calProfit();
				}
			});
		}
		return btnCal;
	}
	
	
	//등록 버튼
	public JButton getBtnInsert() {
		if(btnInsert == null) {
			btnInsert = new JButton();
			btnInsert.setText("등록");
			btnInsert.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//DB에 이윤 저장
					
				}
			});
		}
		return btnInsert;
	}
	
	
	
	//취소 버튼
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
	
	
	//실행

}