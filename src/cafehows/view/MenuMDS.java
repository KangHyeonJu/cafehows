package cafehows.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CategoryDTO;
import cafehows.model.CafeDAO;
import cafehows.model.MenuDTO;

public class MenuMDS extends JDialog{
	private JPanel pCenter, pSouth;
	private JButton btnModify,btnDel,btnVisible, btnCancel;
	private JTable menuTable;

	public MenuMDS() {
		this.setTitle("메뉴 수정/삭제/숨김");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);

		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			pCenter.add(new JScrollPane(getMenuTable()));
		}
		return pCenter;
	}
	public JTable getMenuTable() {
		if(menuTable == null) {
			menuTable = new JTable();
			menuTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
			tableModel.addColumn("종류");
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			
		
			refreshTable();
			
			menuTable.getColumn("종류").setPreferredWidth(40);
			menuTable.getColumn("메뉴명").setPreferredWidth(40);
			menuTable.getColumn("가격").setPreferredWidth(20);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
			menuTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int rowIndex =	menuTable.getSelectedRow();
					if(rowIndex !=-1) {
						int bno = (int)	menuTable.getValueAt(rowIndex, 0);
						
					}
				}		
			});
		}
			
		return menuTable;
		}

	
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnModify());
			pSouth.add(getBtnDel());
			pSouth.add(getBtnVisible());
			pSouth.add(getBtnCancel());
			
		}
		return pSouth;
	}

	
	public JButton getBtnModify() {
		if(btnModify == null) {
			btnModify = new JButton();
			btnModify.setText("수정");
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnModify;
	}
	
	
	public JButton getBtnDel() {
		if(btnDel == null) {
			btnDel = new JButton();
			btnDel.setText("삭제");
			btnDel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnDel;
	}
	
	public JButton getBtnVisible() {
		if(btnVisible == null) {
			btnVisible = new JButton();
			btnVisible.setText("숨김/해제");
			btnVisible.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnVisible;
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
		DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
		tableModel.setNumRows(0);
		for(MenuDTO dto : CafeDAO.getInstance().getMDSItems()) {
			Object[] rowData = {dto.getKind(), dto.getMname(),dto.getPrice()};
			tableModel.addRow(rowData);
			
		}
	
	}

}
