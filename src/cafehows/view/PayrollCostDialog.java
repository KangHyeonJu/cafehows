
package cafehows.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;
import cafehows.model.OrderDTO;
import exceptions.UnsuitableInputException;
import cafehows.model.EmployeeDTO;

public class  PayrollCostDialog extends JDialog{
	private JPanel pCenter, pSouth, searchPanel, searchCno;
	private JTextField txtENo,txtEName,txtEHour,txtEWage,txtEDate ;
	private JButton btnAD,btnSend, btnCancel, initBtn;
	private JTextField searchInput;
	private JTextField startPeriod,endPeriod;
	private JTable employeeTable, employeeHourTable,employeeWageTable;
	private PayrollCostDialog payrollCostDialog;
	private static final String REGEXP_DATE = "^[\\d]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

	public PayrollCostDialog() {
		this.payrollCostDialog = this;
		this.setTitle("인건비");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(700,700);
		this.setModal(true); //상위 frame 클릭 불가
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getJTabbedPane());
		locationCenter();
	}
	
	private JTabbedPane getJTabbedPane() {
		JTabbedPane employeeTab = new JTabbedPane();
		employeeTab.setTabPlacement(JTabbedPane.TOP);
		employeeTab.addTab("직원조회",getTab1Panel());
		employeeTab.add("시간등록",getTab2Panel());
		employeeTab.add("근로시간조회",getTab3Panel());

		return employeeTab;
	}
	//탭1

	
	public JPanel getTab1Panel() {
		JPanel tab1Panel = new JPanel(new BorderLayout());
	
		tab1Panel.add(getSearchPanel(),BorderLayout.NORTH);
		tab1Panel.add(getPCenter(), BorderLayout.CENTER);
		tab1Panel.add(getPSouth(), BorderLayout.SOUTH);
		
		
		
		return tab1Panel;
	}
	
	private JPanel getSearchPanel() {
		
			JPanel searchPanel = new JPanel();
			searchPanel.add(getSearchENo());
			searchPanel.add(getSearchBar());
			searchPanel.add(getSearchBtn());
			searchPanel.add(getInitBtn());
	
		return searchPanel;
	}
	
	private JPanel getSearchENo() {
		
			JPanel searchCno = new JPanel();
			searchCno.add(new JLabel("직원 번호"));
		
		return searchCno;
	}
	
	private JTextField getSearchBar() {
		
			searchInput = new JTextField(15);
			searchInput.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					String enoText = searchInput.getText();
					//키를 떼었을 때
					searchKeyword(enoText);
				}
			});
		
		return searchInput;
	}
	
	public JButton getSearchBtn() {
	
			JButton searchBtn = new RoundedButton();
			searchBtn.setText("검색");
			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					searchKeyword(searchInput.getText());
				}
			});
			
		
		return searchBtn;
	}

	
	public JButton getInitBtn() {
		if(initBtn==null) {
			initBtn = new RoundedButton();
			initBtn.setText("초기화");
			initBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchInput.setText("");
					refreshEmployeeTable();
				}
			});
		}
		return initBtn;
	}
	public void refreshEmployeeTable() {
		DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
		tableModel.setNumRows(0);
		for(EmployeeDTO dto : CafeDAO.getInstance().getEmployeeItems()) {
			String status ="";
			if(dto.getStatus()==1) {status ="재직";}
			else if(dto.getStatus()==2) {status="휴직";}
			else if(dto.getStatus()==3) {status="퇴직";}
			Object[] rowData = { dto.getEno(), dto.getEname(),status };
			tableModel.addRow(rowData);
		}
	}
	
	public void searchKeyword(String eno) {
		DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
		tableModel.setNumRows(0);
		EmployeeDTO dto = CafeDAO.getInstance().getEmployeeByEno(Integer.parseInt(eno)); 
			String status ="";
			if(dto.getStatus()==1) {status ="재직";}
			else if(dto.getStatus()==2) {status="휴직";}
			else if(dto.getStatus()==3) {status="퇴직";}
			Object[] rowData = { dto.getEno(), dto.getEname(), status };
			tableModel.addRow(rowData);
		
	}

	
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			JScrollPane jScrollPane = new JScrollPane(getEmployeeTable());
			jScrollPane.setPreferredSize(new Dimension(450,380));
			pCenter.add(jScrollPane);
			refreshEmployeeTable();
		}
		return pCenter;
	}
	public JTable getEmployeeTable() {
		if(employeeTable == null) {
			employeeTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			employeeTable.setAutoCreateRowSorter(true);
			employeeTable.getTableHeader().setReorderingAllowed(false);
			employeeTable.getTableHeader().setResizingAllowed(false);
			
			DefaultTableModel tableModel = (DefaultTableModel) employeeTable.getModel();
			tableModel.addColumn("직원번호");
			tableModel.addColumn("직원이름");
			tableModel.addColumn("상태");
			
			refreshEmployeeTable();
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = employeeTable.getColumnModel();
			for(int i=0; i<3; i++) tcm.getColumn(i).setCellRenderer(dtcr);
		}
		return employeeTable;
	}
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnAD());
		}
		return pSouth;
	}

	
	public JButton getBtnAD() {
		if(btnAD == null) {
			btnAD = new RoundedButton();
			btnAD.setText("등록/수정");
			btnAD.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					EmployeeModify employeemodify = new EmployeeModify(payrollCostDialog);
					employeemodify.setVisible(true);
				}
			});
		}
		return btnAD;
	}

	//tab2Panel
	public JPanel getTab2Panel() {
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
		txtENo = new JTextField();
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
			pSouth.add(getBtnModify());
			pSouth.add(getBtnCancel());
			
		
		return pSouth;
	}
	public JButton getTab2BtnOk() {
		
		JButton btnOk = new RoundedButton();
		btnOk.setText("등록");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EmployeeDTO employee = new EmployeeDTO();
				employee.setEno(Integer.parseInt(txtENo.getText()));
				employee.setDate(java.sql.Date.valueOf(txtEDate.getText()));
				employee.setHour(Integer.parseInt(txtEHour.getText()));
				employee.setWage(Integer.parseInt(txtEWage.getText()));
				
				CafeDAO.getInstance().insertEmployeeHour(employee);
				payrollCostDialog.setEmployeeHourTable();
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
				employee.setDate(java.sql.Date.valueOf(txtEDate.getText()));
				employee.setHour(Integer.parseInt(txtEHour.getText()));
				employee.setWage(Integer.parseInt(txtEWage.getText()));
				
				CafeDAO.getInstance().updateEmployeeHour(employee);
				payrollCostDialog.setEmployeeHourTable();
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
	
	private JPanel getTab3Panel() {
	
			JPanel tab3Panel = new JPanel();
			tab3Panel.setLayout(new BorderLayout());	
			tab3Panel.add(getPeriodPanel(),BorderLayout.NORTH);
			tab3Panel.add(new JScrollPane(getEmployeeHourTable()),BorderLayout.CENTER);
			tab3Panel.add(new JScrollPane(getEmployeeWageTable()),BorderLayout.SOUTH);
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
					validateDate(startPeriod);
					validateDate(endPeriod);
					refreshEmployeeHourTable();
					refreshEmployeeWageTable();

			}
			});
		
		return enterBtn;
	}

	
	public JTable getEmployeeWageTable() {
		if(employeeWageTable == null) {
			employeeWageTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			employeeWageTable.setAutoCreateRowSorter(true);
			employeeWageTable.getTableHeader().setReorderingAllowed(false);
			employeeWageTable.getTableHeader().setResizingAllowed(false);
			
			DefaultTableModel tableModel = (DefaultTableModel)employeeWageTable.getModel();
			tableModel.addColumn("직원번호");
			tableModel.addColumn("직원이름");
			tableModel.addColumn("주간");
			tableModel.addColumn("급여");
			tableModel.addColumn("주휴수당");
			tableModel.addColumn("총 급여");
			setEmployeeWageTable();
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm =employeeWageTable.getColumnModel();
			for(int i=0; i<6; i++) tcm.getColumn(i).setCellRenderer(dtcr);

//			
		}
		return employeeWageTable;
	}
	
	
	public void setEmployeeWageTable() {
		DefaultTableModel tableModel = (DefaultTableModel) employeeWageTable.getModel();
		tableModel.setNumRows(0);
		for(EmployeeDTO dto : CafeDAO.getInstance().getEmployeeWageItems()) {
			String eno = dto.getEno()==0 ? "합계" : Integer.toString(dto.getEno());
			Object[] rowData = {eno,dto.getEname(),dto.getStartDate()+"~"+dto.getEndDate(),dto.getWage(),dto.getHolidayPay(),dto.getTotalSalary()};
			tableModel.addRow(rowData);
			
		}
	}
	

	public void refreshEmployeeWageTable() {
		DefaultTableModel tableModel = (DefaultTableModel) employeeWageTable.getModel();
		tableModel.setNumRows(0);

		for(EmployeeDTO dto : CafeDAO.getInstance().getEmployeeWageItemsbyPeriod(
			startPeriod.getText(),endPeriod.getText())
				) {
			String eno = dto.getEno()==0 ? "합계" : Integer.toString(dto.getEno());
			Object[] rowData = {eno,dto.getEname(),dto.getStartDate()+"~"+dto.getEndDate(),dto.getWage(),dto.getHolidayPay(),dto.getTotalSalary()};
			tableModel.addRow(rowData);
			
		}
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
			for(int i=0; i<5; i++) tcm.getColumn(i).setCellRenderer(dtcr);

//			
		}
		return employeeHourTable;
	}
	
	
	public void setEmployeeHourTable() {
		DefaultTableModel tableModel = (DefaultTableModel) employeeHourTable.getModel();
		tableModel.setNumRows(0);
		for(EmployeeDTO dto : CafeDAO.getInstance().getEmployeeHourItems()) {
			Object[] rowData = {dto.getEno(),dto.getEname(),dto.getDate(),dto.getHour(),dto.getWage()};
			tableModel.addRow(rowData);
			
		}
	}
	

	public void refreshEmployeeHourTable() {
		DefaultTableModel tableModel = (DefaultTableModel) employeeHourTable.getModel();
		tableModel.setNumRows(0);

		for(EmployeeDTO dto : CafeDAO.getInstance().getEmployeeHourItemsbyPeriod(
				startPeriod.getText(),endPeriod.getText())
				) {
			Object[] rowData = {dto.getEno(),dto.getEname(),dto.getDate(),dto.getHour(),dto.getWage()};
			tableModel.addRow(rowData);
			
		}
	}
	private void validateDate(JTextField period) throws  UnsuitableInputException {
		
		if(!Pattern.matches(REGEXP_DATE,period.getText().trim()))
			throw new UnsuitableInputException("yyyy-MM-dd 형식이 잘못 입력되었습니다");
	
	
	}
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
}

