package cafehows.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CustomerDTO;
import cafehows.model.CafeDAO;

public class CustomerDialog extends JDialog{
	private JPanel pCenter, pSouth;
	private JButton btnAD,btnSend, btnCancel;
	private JTable customerTable;

	public CustomerDialog() {
		this.setTitle("고객 관리");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);

		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			pCenter.add(new JScrollPane(getCustomerTable()));
		}
		return pCenter;
	}
	public JTable getCustomerTable() {
		if(customerTable == null) {
			customerTable = new JTable();
			customerTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
			tableModel.addColumn("전화번호");
			tableModel.addColumn("포인트");
			tableModel.addColumn("최근방문일");
			
		
			refreshTable();
			
			customerTable.getColumn("전화번호").setPreferredWidth(40);
			customerTable.getColumn("포인트").setPreferredWidth(30);
			customerTable.getColumn("최근방문일").setPreferredWidth(40);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
			customerTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int rowIndex =	customerTable.getSelectedRow();
					if(rowIndex !=-1) {
						int bno = (int)	customerTable.getValueAt(rowIndex, 0);
						
					}
				}		
			});
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
					
				}
			});
		}
		return btnCancel;
	}
	


	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) customerTable.getModel();
		tableModel.setNumRows(0);
		for(CustomerDTO dto : CafeDAO.getInstance().getCustomerItems()) {
			Object[] rowData = {dto.getCno(), dto.getPoint(),dto.getRecdate()};
			tableModel.addRow(rowData);
			
		}
	
	}

}
