
package cafehows.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;
import cafehows.model.OrderDTO;

public class  PayrollCostDialog extends JDialog{
	private JPanel pCenter, pSouth, searchPanel, searchCno;
	private JTextField tab2TxtENo,txtEName,txtEHour,txtEWage,txtEDate ;
	private JButton btnAD,btnSend, btnCancel, initBtn;
	private JTextField searchInput;
	private JTextField startPeriod,endPeriod;
	private JTable employeeHourTable;

	public PayrollCostDialog() {
		this.setTitle("인건비");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		this.setModal(true); //상위 frame 클릭 불가
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getJTabbedPane());
		locationCenter();
	}
	
	private JTabbedPane getJTabbedPane() {
		JTabbedPane employeeTab = new JTabbedPane();
		employeeTab.setTabPlacement(JTabbedPane.TOP);
		employeeTab.addTab("직원등록",getTab1Panel());
		employeeTab.add("시간등록",getTab2Panel());
		employeeTab.add("근로시간조회",getTab3Panel());

		return employeeTab;
	}
	//탭1


	//tab2Panel
	private JPanel getTab2Panel() {
		JPanel tab2Panel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel(new GridLayout(4,1));
		centerPanel.add(getTab2ENo());
		centerPanel.add(getEDate());
		centerPanel.add(getEHour());
		centerPanel.add(getEWage());
		tab2Panel.add(centerPanel,BorderLayout.CENTER);
		tab2Panel.add(getTab2PSouth(),BorderLayout.SOUTH);
		
		
		return tab2Panel;
	}
	

	public JPanel getTab2ENo() {
		JPanel pENo = new JPanel();
		JLabel label = new JLabel("직원번호",JLabel.CENTER);
		label.setPreferredSize(new Dimension(50,30));
		pENo.add(label);
	    JTextField txtENo = new JTextField();
		txtENo.setPreferredSize(new Dimension(200,30));
		pENo.add(txtENo);

	return pENo;
}

	//일한 날짜
	public JPanel getEDate() {
		JPanel pEDate = new JPanel();
		JLabel label = new JLabel("날짜",JLabel.CENTER);
		label.setPreferredSize(new Dimension(50,30));
		pEDate.add(label);
	    txtEDate = new JTextField();
		txtEDate.setPreferredSize(new Dimension(200,30));
		pEDate.add(txtEDate);

	return pEDate;
}

	//일한 시간
	public JPanel getEHour() {
		JPanel pEHour = new JPanel();
		JLabel label = new JLabel("근로시간",JLabel.CENTER);
		label.setPreferredSize(new Dimension(50,30));
		pEHour.add(label);
	    txtEHour = new JTextField();
		txtEHour.setPreferredSize(new Dimension(200,30));
		pEHour.add(txtEHour);

	return pEHour;
}
	
	
	//시급 입력
	public JPanel getEWage() {
		JPanel pEWage = new JPanel();
		JLabel label = new JLabel("시급",JLabel.CENTER);
		label.setPreferredSize(new Dimension(50,30));
		pEWage.add(label);
	    txtEWage = new JTextField();
		txtEWage.setPreferredSize(new Dimension(200,30));
		pEWage.add(txtEWage);

	return pEWage;
}
	
	public JPanel getTab2PSouth() {
		
		JPanel pSouth = new JPanel();
			pSouth.add(getTab2BtnOk());
			pSouth.add(getBtnCancel());
			
		
		return pSouth;
	}
	public JButton getTab2BtnOk() {
		
		JButton btnOk = new RoundedButton();
		btnOk.setText("등록");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					
			}
		});
	
	return btnOk;
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
	
	private JPanel getTab3Panel() {
	
			JPanel tab3Panel = new JPanel();
			tab3Panel.setLayout(new BorderLayout());	
			tab3Panel.add(getPeriodPanel(),BorderLayout.NORTH);
			tab3Panel.add(new JScrollPane(getEmployeeHourTable()),BorderLayout.CENTER);
		
		return tab3Panel;
	}
	public JPanel getPeriodPanel() {

			JPanel periodPanel = new JPanel();
			JLabel inputPeriod = new JLabel();
			inputPeriod.setText("조회기간입력");
			startPeriod = new JTextField(10);
			endPeriod = new JTextField(10);
			periodPanel.add(inputPeriod);
			periodPanel.add(startPeriod);
			periodPanel.add(new JLabel("~"));
			periodPanel.add(endPeriod);
			periodPanel.add(getEnterBtn());
		
		return periodPanel;
	}
	
	public JButton getEnterBtn() {
	
			JButton enterBtn = new RoundedButton();
			enterBtn.setText("조회");
			enterBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					refreshEmployeeHourTable();

			}
			});
		
		return enterBtn;
	}

	
	public JTable getEmployeeHourTable() {
		if(employeeHourTable == null) {
			employeeHourTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			employeeHourTable.setAutoCreateRowSorter(true);
			employeeHourTable.getTableHeader().setReorderingAllowed(false);
			employeeHourTable.getTableHeader().setResizingAllowed(false);
			
			DefaultTableModel tableModel = (DefaultTableModel)employeeHourTable.getModel();
			tableModel.addColumn("직원번호");
			tableModel.addColumn("직원이름");
			tableModel.addColumn("날짜");
			tableModel.addColumn("근로시간");
			tableModel.addColumn("시급");
			setEmployeeHourTable();
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm =employeeHourTable.getColumnModel();
			for(int i=0; i<4; i++) tcm.getColumn(i).setCellRenderer(dtcr);

//			
		}
		return employeeHourTable;
	}
	
	
	public void setEmployeeHourTable() {
		DefaultTableModel tableModel = (DefaultTableModel) employeeHourTable.getModel();
		tableModel.setNumRows(0);
		for(OrderDTO dto : CafeDAO.getInstance().getOrderItems()) {
			Object[] rowData = {dto.getDate(), dto.getOno(),dto.getPrice(),dto.getFinalprice()};
			tableModel.addRow(rowData);
			
		}
	}
	

	public void refreshEmployeeHourTable() {
		DefaultTableModel tableModel = (DefaultTableModel) employeeHourTable.getModel();
		tableModel.setNumRows(0);

		for(OrderDTO dto :CafeDAO.getInstance().getDailySalesbyPeriod(
				Integer.parseInt(startPeriod.getText()),Integer.parseInt(endPeriod.getText()))
				) {
			//String visibility = dto.getVisibility()==1 ? "회원" : "탈퇴";
			Object[] rowData = {dto.getDate(),dto.getFinalprice()};
			tableModel.addRow(rowData);
			
		}
	}
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
}

