package cafehows.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import cafehows.model.CafeDAO;

public class CostPanel extends JPanel{

	//test
	private CostPanel board;
	private JPanel pSouth, pCenter, prent, pPayrollCost, pMaterialCost, pProfit;
	private JButton btnInsert, btnCancel, btnCal;
	private JTextField txtRevenue= new JTextField();
	private JTextField txtRent, txtPayrollCost, txtMaterialCost, resultField;
	private int monthTemp,yearTemp,monthlySales;
	
	
	public CostPanel() {
		this.board = this;

		this.add(getPCenter(),BorderLayout.CENTER);
		this.setSize(600, 450);
	}
	
	//임대료 : rent 인건비 : payrollCost 재료비 : materialCost 이윤 : profit
	
	
	//화면 구성
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel(new GridLayout(7,1));
			pCenter.add(getMonth());
			pCenter.add(getRevenue());
			pCenter.add(getRent());
			pCenter.add(getpayrollCost());
			pCenter.add(getMaterialCost());
			pCenter.add(getProfit());
			pCenter.add(getPSouth());
		}
		return pCenter;
	}
	public int getMonthTemp() {
		return monthTemp;
	}

	public void setMonthTemp(int monthTemp) {
		this.monthTemp = monthTemp;
	}

	public int getYearTemp() {
		return yearTemp;
	}

	public void setYearTemp(int yearTemp) {
		this.yearTemp = yearTemp;
	}

	//월 선택
	public JPanel getMonth() {
		JPanel pMonth = new JPanel();
		pMonth.add(getComboYear());
		pMonth.add(getComboMonth());
	
		

	return pMonth;
	}
	
	public JComboBox getComboYear() {
		
		String[] arrYear = {"2024년"};
		
		JComboBox<String> comboYear = new JComboBox<String>(arrYear);
		yearTemp = Integer.parseInt(comboYear.getSelectedItem().toString().substring(0,4));
		comboYear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				yearTemp = Integer.parseInt(comboYear.getSelectedItem().toString().substring(0,4));
				
			}
		});
	
	return comboYear;

}
	
	public JComboBox getComboMonth() {
		
			String[] arrMonth = {"1월","2월","3월","4월","5월","6월",
					"7월","8월","9월","10월","11월","12월"};
			
			JComboBox<String> comboMonth = new JComboBox<String>(arrMonth);
			monthTemp = comboMonth.getSelectedIndex()+1;
			comboMonth.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					monthTemp = comboMonth.getSelectedIndex()+1;
					txtRevenue.setText(Integer.toString(CafeDAO.getInstance().getMonthlySales(yearTemp, monthTemp)));
					monthlySales = CafeDAO.getInstance().getMonthlySales(yearTemp, monthTemp);
				//	System.out.println(monthlySales);
//					System.out.println(yearTemp);
//					System.out.println(monthTemp);
				}
			});
		
		return comboMonth;
	
	}
	//매출 불러오기
	public JPanel getRevenue() {
			JPanel pRevenue = new JPanel();
			JLabel label = new JLabel("매출액",JLabel.CENTER);
			label.setPreferredSize(new Dimension(50,30));
			pRevenue.add(label);
	
			txtRevenue.setPreferredSize(new Dimension(200,30));
			pRevenue.add(txtRevenue);
	
		return pRevenue;
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
				txtRent.setPreferredSize(new Dimension(200,30));
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
				txtPayrollCost.setPreferredSize(new Dimension(200,30));
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
				txtMaterialCost.setPreferredSize(new Dimension(200,30));
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
				resultField.setPreferredSize(new Dimension(200,30));
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
			double result = monthlySales- rent - payroll - material ;
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
			btnCal = new RoundedButton();
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
			btnInsert = new RoundedButton();
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
			btnCancel = new RoundedButton();
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