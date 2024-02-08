package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cafehows.model.CafeDAO;
import cafehows.model.MenuDTO;

public class MenuMDS extends JDialog {
	private JPanel pCenter, pSouth, searchPanel;
	private JButton btnModify, btnVisible0, btnVisible1, btnCancel, initBtn, searchBtn;
	private static JTable menuTable;
	private JTextField searchInput;
	private static List<MenuDTO> menuList = CafeDAO.getInstance().getMDSItems();
	private CafeDAO cafeDao = new CafeDAO();
	private static MenuMDS menuBoard;
	//private Main main = new Main();
	private Main main;

	public MenuMDS(Main main) {
		this.main = main;
		this.setTitle("메뉴 수정/숨김");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 500);
		this.setModal(true); //상위 frame 클릭 불가
		this.getContentPane().add(getSearchPanel(), BorderLayout.NORTH);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);

		locationCenter();
	}

	private JPanel getSearchPanel() {
		if (searchPanel == null) {
			searchPanel = new JPanel();
			searchPanel.add(new JLabel("메뉴명", JLabel.CENTER));
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
			searchBtn = new JButton();
			// searchBtn.setText("검색");
			JLabel btnImage = new JLabel();
			btnImage.setIcon(new ImageIcon(getClass().getResource("search.png")));
			searchBtn.add(btnImage);
			if(searchInput.getText().equals("")) {}else {
			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					searchKeyword(searchInput.getText());
				}
			});
			}
		}
		return searchBtn;
	}

	public JButton getInitBtn() {
		if (initBtn == null) {
			initBtn = new JButton();
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

			JScrollPane jScrollPane = new JScrollPane(getMenuTable());
			jScrollPane.setPreferredSize(new Dimension(450,380));
			pCenter.add(jScrollPane);
		}
		return pCenter;
	}

	public static JTable getMenuTable() {		
		if (menuTable == null) {
			menuTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			menuTable.setAutoCreateRowSorter(true);

			DefaultTableModel tableModel = (DefaultTableModel) menuTable.getModel();
			tableModel.addColumn("종류");
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			tableModel.addColumn("상태");
			
			for (MenuDTO dto : CafeDAO.getInstance().getMDSItems()) {
				String visibility = dto.getVisibility()==1 ? "표시" : "숨김";
				Object[] rowData = { dto.getKind(), dto.getMname(), dto.getPrice(), visibility };
				tableModel.addRow(rowData);
			}
			
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
			pSouth.add(getBtnModify());
			pSouth.add(getBtnVisible0());
			pSouth.add(getBtnVisible1());
			pSouth.add(getBtnCancel());

		}
		return pSouth;
	}

	public JButton getBtnModify() {
		if (btnModify == null) {
			btnModify = new JButton();
			btnModify.setText("수정");
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					int row = menuTable.getSelectedRow();
					System.out.println(row);
					if (row == -1) {
						return;
					} else {
						MenuModify menuModify = new MenuModify(menuBoard, menuList.get(row).getMname(), main);
						menuModify.setModal(true);
						menuModify.setVisible(true);
					}
				}
			});
		}
		return btnModify;
	}

//	public JButton getBtnDel() {
//		if(btnDel == null) {
//			btnDel = new JButton();
//			btnDel.setText("삭제");
//			btnDel.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					int row = menuTable.getSelectedRow();
//					DefaultTableModel tableModel = (DefaultTableModel) getMenuTable().getModel();
//					if(row == -1) {
//						return;
//					}else {
//						tableModel.removeRow(row);
//						cafeDao.deleteMenu(menuList.get(row).getMname());
//					}
//				}
//			});
//		}
//		return btnDel;
//	}

	public JButton getBtnVisible0() {
		if (btnVisible0 == null) {
			btnVisible0 = new JButton();
			btnVisible0.setText("숨김");
			btnVisible0.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = menuTable.getSelectedRow();
					if (row == -1) {
						JOptionPane.showMessageDialog(null, "숨길 메뉴를 선택해 주세요.");
					} else {
						cafeDao.visibilityMenu0(menuList.get(row).getMname());
						refreshTable();
						
						System.out.println(menuList.get(row));
						main.refreshTab();
						//Main.refreshMenu(menuList.get(row).getCano(), main.getMenuTable());
					}
				}
			});
		}
		return btnVisible0;
	}

	public JButton getBtnVisible1() {
		if (btnVisible1 == null) {
			btnVisible1 = new JButton();
			btnVisible1.setText("해제");
			btnVisible1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = menuTable.getSelectedRow();
					if (row == -1) {
						JOptionPane.showMessageDialog(null, "숨김 해제할 메뉴를 선택해 주세요.");
					} else {
						cafeDao.visibilityMenu1(menuList.get(row).getMname());
						refreshTable();
						main.refreshTab();


					}
				}
			});
		}
		return btnVisible1;
	}

	public JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton();
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
