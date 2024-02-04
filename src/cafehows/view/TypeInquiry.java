package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.cafeDAO;
import cafehows.model.CategoryDTO;

public class TypeInquiry extends JFrame{
	private JPanel pCenter, pEast, pSouth;
	private JButton btnModify,btnDel,btnAdd, btnCancel;
	private JTable typeTable;

	public TypeInquiry() {
		this.setTitle("종류 조회");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);

		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPEast(), BorderLayout.EAST);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			pCenter.add(new JScrollPane(getTypeTable()));
		}
		return pCenter;
	}
	public JTable getTypeTable() {
		if(typeTable == null) {
			typeTable = new JTable();
			typeTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) typeTable.getModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("종류");
		
			refreshTable();
			
			typeTable.getColumn("번호").setPreferredWidth(20);
			typeTable.getColumn("종류").setPreferredWidth(40);
			
//			CenterTableCellRenderer ctcr = new CenterTableCellRenderer();
//			menuTable.getColumn("메뉴명").setCellRenderer(ctcr);
//			menuTable.getColumn("가격").setCellRenderer(ctcr);
//			
			typeTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int rowIndex =	typeTable.getSelectedRow();
					if(rowIndex !=-1) {
						int bno = (int)	typeTable.getValueAt(rowIndex, 0);
						
					}
				}		
			});
		}
			
		return 	typeTable;
		}
	
	public JPanel getPEast() {
		if(pEast==null) {
			pEast = new JPanel();
			pEast.setLayout(new BoxLayout(pEast,BoxLayout.Y_AXIS));
			pEast.add(new JLabel("종류", JLabel.CENTER));
			JTextField kindName = new JTextField(10);
			pEast.add(kindName);
		}
		return pEast;
	}
	
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnModify());
			pSouth.add(getBtnDel());
			pSouth.add(getBtnAdd());
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
	
	public JButton getBtnAdd() {
		if(btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("추가");
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnAdd;
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
		DefaultTableModel tableModel = (DefaultTableModel) typeTable.getModel();
		tableModel.setNumRows(0);
		for(CategoryDTO dto : cafeDAO.getInstance().getCategoryItems()) {
			Object[] rowData = {dto.getCano(), dto.getKind()};
			tableModel.addRow(rowData);
			
		}
	
	}
	
}
