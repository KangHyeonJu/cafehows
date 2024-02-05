package cafehows.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import cafehows.model.CafeDAO;
import cafehows.model.MenuDTO;

public class MenuMDS extends JDialog{
	private JPanel pCenter, pSouth;
	private JButton btnModify,btnDel,btnVisible, btnCancel;
	private JTable menuTable;
	private static List<MenuDTO> menuList = CafeDAO.getInstance().getMDSItems();
	private CafeDAO cafeDao = new CafeDAO();

	public MenuMDS() {
		this.setTitle("메뉴 수정/삭제/숨김");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);

		this.getContentPane().add(new JScrollPane(getPCenter()), BorderLayout.CENTER);
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
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = menuTable.getColumnModel();
			for(int i=0; i<3; i++) tcm.getColumn(i).setCellRenderer(dtcr);
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
					int row = menuTable.getSelectedRow();
					//MenuModify menuModify = new MenuModify(menuList.get(row).getMname());
					
					
					//menuModify.setVisible(true);
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
					int row = menuTable.getSelectedRow();
					DefaultTableModel tableModel = (DefaultTableModel) getMenuTable().getModel();
					if(row == -1) {
						return;
					}else {
						tableModel.removeRow(row);
						cafeDao.deleteMenu(menuList.get(row).getMname());
					}
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
					int row = menuTable.getSelectedRow();
					if(row == -1) {
						return;
					}else {
						cafeDao.visibilityMenu(menuList.get(row).getMname());
					}
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
					MenuMDS.this.dispose();
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
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			MenuMDS mM = new MenuMDS();
        	mM.setVisible(true);
	    });


	}

}
