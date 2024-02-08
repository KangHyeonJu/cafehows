package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
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
import cafehows.model.MenuDTO;

public class MenuMDS extends JDialog {
	private JPanel pCenter, pSouth, searchPanel;
	private JButton btnModify, btnVisible0, btnVisible1, btnCancel, initBtn, searchBtn;
	private JTable menuTable;
	private JTextField searchInput;
//	private static List<MenuDTO> menuList = CafeDAO.getInstance().getMDSItems();
//	private CafeDAO cafeDao = new CafeDAO();
	//private static MenuMDS menuBoard;
	//private Main main = new Main();
	private Main main;
	private MenuDTO menuDTO ;
	private MenuMDS menuMDS;
	

	public MenuMDS(Main main) {
		this.main = main;
		this.menuMDS = this;
		this.setTitle("메뉴 수정/숨김");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		this.setModal(true); //상위 frame 클릭 불가
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getSearchPanel(), BorderLayout.NORTH);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.getContentPane().setBackground(Color.WHITE);

		locationCenter();
	}
	

	public MenuDTO getMenuDTO() {
		return menuDTO;
	}


	public void setMenuDTO(MenuDTO menuDTO) {
		this.menuDTO = menuDTO;
	}


	private JPanel getSearchPanel() {
		if (searchPanel == null) {
			searchPanel = new JPanel();
			searchPanel.add(new JLabel("메뉴명", JLabel.CENTER));
			//searchPanel.setBackground(Color.WHITE);
			searchPanel.add(getSearchBar());
			searchPanel.add(getSerachBtn());
			searchPanel.add(getInitBtn());
		}
		return searchPanel;
	}

	private JTextField getSearchBar() {
		if (searchInput == null) {
			searchInput = new JTextField(15);
			searchInput.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					searchKeyword(searchInput.getText());
				}
			});
		}
		return searchInput;
	}

	public void searchKeyword(String keyword) {
		DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
		tableModel.setNumRows(0);
		for (MenuDTO dto : CafeDAO.getInstance().searchKeyword(keyword)) {
			String visibility = dto.getVisibility()==1 ? "표시" : "숨김";
			Object[] rowData = { dto.getKind(), dto.getMname(), dto.getPrice(), visibility };		
			
			tableModel.addRow(rowData);
		}
	}

	public JButton getSerachBtn() {
		if (searchBtn == null) {
			searchBtn = new RoundedButton();
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
			
		}
		return searchBtn;
	}

	public JButton getInitBtn() {
		if (initBtn == null) {
			initBtn = new RoundedButton();
			initBtn.setText("초기화");
			initBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					refreshTable();
					searchInput.setText("");
				}
			});
		}
		return initBtn;
	}

	public JPanel getPCenter() {
		if (pCenter == null) {
			pCenter = new JPanel();
			//pCenter.setBackground(Color.WHITE);
			JScrollPane jScrollPane = new JScrollPane(getMenuTable());
			jScrollPane.setPreferredSize(new Dimension(450,380));
			//jScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
			
			pCenter.add(jScrollPane);
		}
		return pCenter;
	}

	public JTable getMenuTable() {		
		if (menuTable == null) {
			menuTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			menuTable.setAutoCreateRowSorter(true);
			menuTable.getTableHeader().setReorderingAllowed(false);
			menuTable.getTableHeader().setResizingAllowed(false);

			DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
			tableModel.addColumn("종류");
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			tableModel.addColumn("상태");
			
			refreshTable();
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = menuTable.getColumnModel();
			for (int i = 0; i < 4; i++)
				tcm.getColumn(i).setCellRenderer(dtcr);
		}

		return menuTable;
	}

	public JPanel getPSouth() {
		if (pSouth == null) {
			pSouth = new JPanel();
			//pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnModify());
			pSouth.add(getBtnVisible0());
			pSouth.add(getBtnVisible1());
			pSouth.add(getBtnCancel());

		}
		return pSouth;
	}

	public JButton getBtnModify() {
		if (btnModify == null) {
			btnModify = new RoundedButton();
			btnModify.setText("수정");
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					int rowIndex = menuTable.getSelectedRow();
					
					if (rowIndex != -1) {
						String mname =(String) menuTable.getValueAt(rowIndex, 1);
						menuDTO = CafeDAO.getInstance().getMenuByName(mname);
						MenuModify menuModify = new MenuModify(main,menuMDS);
						menuModify.setModal(true);
						menuModify.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "수정할 메뉴를 선택해 주세요.");
					}
				}
			});
		}
		return btnModify;
	}

	public JButton getBtnVisible0() {
		if (btnVisible0 == null) {
			btnVisible0 = new RoundedButton();
			btnVisible0.setText("숨김");
			btnVisible0.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int rowIndex = menuTable.getSelectedRow();
					
					if (rowIndex != -1) {
						String mname =(String) menuTable.getValueAt(rowIndex, 1);
						menuDTO = CafeDAO.getInstance().getMenuByName(mname);
						CafeDAO.getInstance().visibilityMenu0(menuDTO.getMname());
						refreshTable();
					
					} else {
						JOptionPane.showMessageDialog(null, "숨길 메뉴를 선택해 주세요.");
					}
			
						main.refreshTab();
						//Main.refreshMenu(menuList.get(row).getCano(), main.getMenuTable());
					}
				
			});
		}
		return btnVisible0;
	}

	public JButton getBtnVisible1() {
		if (btnVisible1 == null) {
			btnVisible1 = new RoundedButton();
			btnVisible1.setText("해제");
			btnVisible1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int rowIndex = menuTable.getSelectedRow();
					
					if (rowIndex != -1) {
						String mname =(String) menuTable.getValueAt(rowIndex, 1);
						menuDTO = CafeDAO.getInstance().getMenuByName(mname);
						CafeDAO.getInstance().visibilityMenu1(menuDTO.getMname());
						refreshTable();
					
					} else {
						JOptionPane.showMessageDialog(null, "숨길 메뉴를 선택해 주세요.");
					}
			
						main.refreshTab();
						//Main.refreshMenu(menuList.get(row).getCano(), main.getMenuTable());
					}
				
			});
		}
		return btnVisible1;
	}

	public JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new RoundedButton();
			btnCancel.setText("닫기");
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
		for (MenuDTO dto : CafeDAO.getInstance().getMDSItems()) {
			String visibility = dto.getVisibility()==1 ? "표시" : "숨김";
			Object[] rowData = { dto.getKind(), dto.getMname(), dto.getPrice(), visibility };
			tableModel.addRow(rowData);
		}
	}

	

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}



}
