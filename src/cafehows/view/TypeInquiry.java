package cafehows.view;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;

public class TypeInquiry extends JDialog{
	private TypeInquiry typeInquiry;
	private JPanel pCenter, pEast, pSouth;
	private JButton btnModify,btnDel,btnAdd, btnCancel;
	private JTable typeTable;
	private int cano;
	private Main main;
	


	public TypeInquiry() {
	
		this.typeInquiry = this;
		this.setTitle("종류 조회");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 200);

		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
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
			typeTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
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
						cano = (int)typeTable.getValueAt(rowIndex, 0);
						
					}
				}		
			});
		}
			
		return 	typeTable;
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
					UpdateMenuKind updatekind = new UpdateMenuKind(typeInquiry,cano);
					updatekind.setVisible(true);
					
				
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
					CafeDAO.getInstance().deleteCategory(cano);
					refreshTable();
					
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
					AddKind addKind = new AddKind();
					addKind.setVisible(true);
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
					dispose();
				}
			});
		}
		return btnCancel;
	}
	


	public void refreshTable() {
		DefaultTableModel tableModel = (DefaultTableModel) typeTable.getModel();
		tableModel.setNumRows(0);
		for(CategoryDTO dto : CafeDAO.getInstance().getCategoryItems()) {
			Object[] rowData = {dto.getCano(), dto.getKind()};
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
