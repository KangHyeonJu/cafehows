package cafehows.view;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
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

public class CustomerDialog extends JDialog{
	private JPanel pCenter, pSouth, searchPanel, searchCno;
	private JButton btnAD,btnSend, btnCancel, initBtn;
	private static JTable customerTable;
	private JTextField searchInput;

	public CustomerDialog() {
		this.setTitle("고객 관리");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		this.setModal(true); //상위 frame 클릭 불가
		this.getContentPane().add(getSearchPanel(),BorderLayout.NORTH);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	
	//검색창
	private JPanel getSearchPanel() {
		if(searchPanel==null) {
			searchPanel = new JPanel();
			searchPanel.add(getSearchCno());
			searchPanel.add(getSearchBar());
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
	
	public JButton getInitBtn() {
		if(initBtn==null) {
			initBtn = new JButton();
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
			Object[] rowData = { dto.getCno(), dto.getPoint(), dto.getRecdate(), visibility };
			tableModel.addRow(rowData);
		}
	}

	
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			pCenter.add(new JScrollPane(getCustomerTable()));
		}
		return pCenter;
	}
	public static JTable getCustomerTable() {
		if(customerTable == null) {
			customerTable = new JTable();
			customerTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
			tableModel.addColumn("전화번호");
			tableModel.addColumn("포인트");
			tableModel.addColumn("최근방문일");
			tableModel.addColumn("상태");
			
			refreshTable();
			
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
			btnAD = new JButton();
			btnAD.setText("등록/삭제");
			btnAD.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CustomerModify customerModify = new CustomerModify();
					customerModify.setVisible(true);	
				}
			});
		}
		return btnAD;
	}
	
	
	public JButton getBtnSend() {
		if(btnSend == null) {
			btnSend = new JButton();
			btnSend.setText("문자 전송");
			btnSend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SendMessage sendMessage = new SendMessage();
					sendMessage.setVisible(true);
				}
			});
		}
		return btnSend;
	}

	
	
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton();
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
	


	public static void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
		tableModel.setNumRows(0);
		for(CustomerDTO dto : CafeDAO.getInstance().getCustomerState()) {
			String visibility = dto.getVisibility()==1 ? "회원" : "탈퇴";
			Object[] rowData = { dto.getCno(), dto.getPoint(), dto.getRecdate(), visibility };
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
