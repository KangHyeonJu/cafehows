package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import constant.CustomerViewState;


public class CustomerDialog extends JDialog{
	private JPanel pCenter, pSouth, searchPanel, searchCno;
	private JButton btnAD,btnSend, btnCancel, initBtn;
	private JTable customerTable;
	private JTextField searchInput;
	private CustomerDialog customerDialog;
	private String phoneNumber;

	public CustomerDialog() {
		this.customerDialog = this;
		this.setTitle("고객 관리");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		this.setModal(true); //상위 frame 클릭 불가
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getSearchPanel(),BorderLayout.NORTH);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPEast(), BorderLayout.EAST);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	
	
	
	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	//검색창
	private JPanel getSearchPanel() {
		if(searchPanel==null) {
			searchPanel = new JPanel();
			searchPanel.add(getSearchCno());
			searchPanel.add(getSearchBar());
			searchPanel.add(getSearchBtn());
			searchPanel.add(getInitBtn());
		}
		return searchPanel;
	}
	
	private JPanel getSearchCno() {
		if(searchCno==null) {
			searchCno = new JPanel();
			searchCno.add(new JLabel("회원 번호"));
		}
		return searchCno;
	}
	
	private JTextField getSearchBar() {
		if(searchInput == null) {
			searchInput = new JTextField(15);
			searchInput.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					String cnoText = searchInput.getText();
					//키를 떼었을 때
					searchKeyword(cnoText);
				}
			});
		}
		return searchInput;
	}
	public JButton getSearchBtn() {
		
			JButton searchBtn = new RoundedButton();
			searchBtn.setText("검색");
			//JLabel btnImage = new JLabel();
			//btnImage.setIcon(new ImageIcon(getClass().getResource("search.png")));
			//searchBtn.add(btnImage);
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
					refreshTable();
				}
			});
		}
		return initBtn;
	}
	
	public void searchKeyword(String cno) {
		DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
		tableModel.setNumRows(0);
		for(CustomerDTO dto : CafeDAO.getInstance().searchKeywordCustomer(cno)) {
			String visibility = dto.getVisibility()==1 ? "회원" : "탈퇴";
			Object[] rowData = { dto.getPhoneNumber(), dto.getPoint(), dto.getRecdate(), visibility };
			tableModel.addRow(rowData);
		}
	}

	
	public JPanel getPEast() {
	
		JPanel pEast = new JPanel(new GridLayout(2,1));
			pEast.add(getPEastGrid());
		
		return pEast;
	}
	
	public JPanel getPEastGrid() {
		
		JPanel pEastGrid = new JPanel(new GridLayout(3,1));
			pEastGrid.add(getModify());
			pEastGrid.add(getHide());
			pEastGrid.add(getShow());
		
		return pEastGrid;
	}
	
	
	public JPanel getModify() {
		
		 JPanel pModify = new JPanel();
			pModify.add(getBtnModify());
		
		return pModify;
	}
	public JPanel getHide() {
		
		JPanel pHide = new JPanel();
			pHide.add(getBtnHide());
		
		return pHide;
	}
	public JPanel getShow() {
	
		JPanel pShow = new JPanel();
			pShow.add(getBtnShow());
		
		return pShow;
	}
	public JButton getBtnModify() {
		
		JButton btnModify = new JButton();
			btnModify.setText("수정");
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = customerTable.getSelectedRow();
					if(row == -1 ) {
						JOptionPane.showMessageDialog(null, "수정할 회원을 선택해 주세요.");
					}else {
						
						CustomerModify customerModify = new CustomerModify(customerDialog, CustomerViewState.UPDATE);
						customerModify.setVisible(true);	
	
					}
				}
			});
		
		return btnModify;
	}
	
	
	public JButton getBtnHide() {
		
		JButton btnHide = new JButton();
			btnHide.setText("탈퇴");
			btnHide.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = customerTable.getSelectedRow();
					if(row == -1) {
						JOptionPane.showMessageDialog(null, "탈퇴할 회원을 선택해 주세요.");
					}else {
						CafeDAO.getInstance().deleteCustomer(phoneNumber);		
						refreshTable();
		
					}
				}
			});
		
		return btnHide;
	}
	
	public JButton getBtnShow() {
		
		JButton btnShow = new JButton();
			btnShow.setText("재가입");
			btnShow.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = customerTable.getSelectedRow();
					if(row == -1) {
						JOptionPane.showMessageDialog(null, "재가입할 회원을 선택해 주세요.");
					}else {
						CafeDAO.getInstance().reSign(phoneNumber);
						// main의 탭 reFresh
						refreshTable();
						
					}
				}
			});
		
		return btnShow;
	}
	
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			JScrollPane jScrollPane = new JScrollPane(getCustomerTable());
			jScrollPane.setPreferredSize(new Dimension(450,380));
			pCenter.add(jScrollPane);
			refreshTable();
		}
		return pCenter;
	}
	public  JTable getCustomerTable() {
		if(customerTable == null) {
			customerTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			customerTable.setAutoCreateRowSorter(true);
			customerTable.getTableHeader().setReorderingAllowed(false);
			customerTable.getTableHeader().setResizingAllowed(false);
			
			DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
			tableModel.addColumn("전화번호");
			tableModel.addColumn("포인트");
			tableModel.addColumn("최근방문일");
			tableModel.addColumn("상태");
			
			refreshTable();
			
			customerTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int rowIndex =	customerTable.getSelectedRow();
					if(rowIndex !=-1) {
						 phoneNumber = (String)customerTable.getValueAt(rowIndex, 0);
					}
				}		
			});
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = customerTable.getColumnModel();
			for(int i=0; i<4; i++) tcm.getColumn(i).setCellRenderer(dtcr);
		}
		return customerTable;
	}
	

	
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnAD());
			pSouth.add(getBtnSend());
			pSouth.add(getBtnCancel());
			
		}
		return pSouth;
	}

	
	public JButton getBtnAD() {
		if(btnAD == null) {
			btnAD = new RoundedButton();
			btnAD.setText("추가");
			btnAD.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CustomerModify customerModify = new CustomerModify(customerDialog, CustomerViewState.CREATE);
					customerModify.setVisible(true);	
				}
			});
		}
		return btnAD;
	}
	
	
	public JButton getBtnSend() {
		if(btnSend == null) {
			btnSend = new RoundedButton();
			btnSend.setText("문자 전송");
			btnSend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SendMessage sendMessage = new SendMessage();
					sendMessage.setModal(true);
					sendMessage.setVisible(true);
				}
			});
		}
		return btnSend;
	}

	
	
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new RoundedButton();
			btnCancel.setText("취소");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CustomerDialog.this.dispose();

				}
			});
		}
		return btnCancel;
	}
	


	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
		tableModel.setNumRows(0);
		for(CustomerDTO dto : CafeDAO.getInstance().getCustomerState()) {
			String visibility = dto.getVisibility()==1 ? "회원" : "탈퇴";
			Object[] rowData = { dto.getPhoneNumber(), dto.getPoint(), dto.getRecdate(), visibility };
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
