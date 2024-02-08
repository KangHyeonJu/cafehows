package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import cafehows.model.CategoryDTO;
import cafehows.model.MenuDTO;

public class TypeInquiry extends JDialog{
	private TypeInquiry typeInquiry;
	private JPanel pCenter, pEast, pSouth,pEastGrid,pModify,pHide,pShow;
	private JButton btnModify,btnDel,btnAdd, btnCancel, btnHide, btnShow;
	private JTable typeTable;
	private int cano;
	private Main main;
	private static List<CategoryDTO> categoryList = CafeDAO.getInstance().getCategoryItems();
	
	public TypeInquiry(Main main) {
		this.main = main;
		this.typeInquiry = this;
		this.setTitle("종류 조회");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(540, 300);
		this.setModal(true); //상위 frame 클릭 불가
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.getContentPane().add(getPEast(),BorderLayout.EAST);
		locationCenter();
	}
	public JPanel getPEast() {
		if(pEast == null) {
			pEast = new JPanel(new GridLayout(2,1));
			pEast.add(getPEastGrid());
		}
		return pEast;
	}
	
	public JPanel getPEastGrid() {
		if(pEastGrid == null) {
			pEastGrid = new JPanel(new GridLayout(3,1));
			pEastGrid.add(getModify());
			pEastGrid.add(getHide());
			pEastGrid.add(getShow());
		}
		return pEastGrid;
	}
	
	
	public JPanel getModify() {
		if(pModify == null) {
			pModify = new JPanel();
			pModify.add(getBtnModify());
		}
		return pModify;
	}
	public JPanel getHide() {
		if(pHide == null) {
			pHide = new JPanel();
			pHide.add(getBtnHide());
		}
		return pHide;
	}
	public JPanel getShow() {
		if(pShow == null) {
			pShow = new JPanel();
			pShow.add(getBtnShow());
		}
		return pShow;
	}
	public JButton getBtnModify() {
		if(btnModify == null) {
			btnModify = new JButton();
			btnModify.setText("수정");
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = typeTable.getSelectedRow();
					if(row == -1 ) {
						JOptionPane.showMessageDialog(null, "카테고리를 선택해 주세요.");
					}else {
					UpdateMenuKind updatekind = new UpdateMenuKind(typeInquiry, cano, main);
					updatekind.setVisible(true);
					}
				}
			});
		}
		return btnModify;
	}
	
	
	public JButton getBtnHide() {
		if(btnHide == null) {
			btnHide = new JButton();
			btnHide.setText("숨김");
			btnHide.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = typeTable.getSelectedRow();
					if(row == -1) {
						JOptionPane.showMessageDialog(null, "숨길 카테고리를 선택해 주세요.");
					}else {
						CafeDAO.getInstance().hideCategory(categoryList.get(row).getKind());
						// main의 탭 reFresh
						
						refreshTable();
						main.refreshTab();
					}
				}
			});
		}
		return btnHide;
	}
	
	public JButton getBtnShow() {
		if(btnShow == null) {
			btnShow = new JButton();
			btnShow.setText("해제");
			btnShow.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = typeTable.getSelectedRow();
					if(row == -1) {
						JOptionPane.showMessageDialog(null, "숨김 해제할 카테고리를 선택해 주세요.");
					}else {
						CafeDAO.getInstance().showCategory(categoryList.get(row).getKind());
						// main의 탭 reFresh
						
						refreshTable();
						main.refreshTab();
					}
				}
			});
		}
		return btnShow;
	}
	
	
	public JPanel getPCenter() {
		if(pCenter == null) {
			pCenter = new JPanel();
			pCenter.setLayout(new BorderLayout());
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
			typeTable.getTableHeader().setReorderingAllowed(false);
			typeTable.getTableHeader().setResizingAllowed(false);
			
			DefaultTableModel tableModel = (DefaultTableModel) typeTable.getModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("종류");
			tableModel.addColumn("상태");
			
			for(CategoryDTO dto : CafeDAO.getInstance().getCategoryItems()) {
				String visibility = dto.getVisibility()==1 ? "표시" : "숨김";
				Object[] rowData = {dto.getCano(), dto.getKind(), visibility};
				tableModel.addRow(rowData);
			}
			
			refreshTable();
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
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = typeTable.getColumnModel();
			for (int i = 0; i < 3; i++)
				tcm.getColumn(i).setCellRenderer(dtcr);
		}
			
		return 	typeTable;
		}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnAdd());
			pSouth.add(getBtnCancel());
			
		}
		return pSouth;
	}
	
	public JButton getBtnAdd() {
		if(btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("추가");
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AddKind addKind = new AddKind(typeInquiry, main);
					addKind.setVisible(true);
				}
			});
		}
		refreshTable();
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
			String visibility = dto.getVisibility()==1 ? "표시" : "숨김";
			Object[] rowData = {dto.getCano(), dto.getKind(), visibility};
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
