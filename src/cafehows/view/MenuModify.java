package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CafeDAO;
import cafehows.model.MenuDTO;

public class MenuModify extends JDialog{
	private MenuMDS menuMDS;
	private String mname;
	private JPanel pCenter, pMenuName, pPrice, pSouth, pPriceIn;
	private JTextField txtMenuName, txtPrice;
	private JButton btnOk, btnCancel;
//	private CafeDAO cafeDao = new CafeDAO();
	private MenuDTO menuDTO;
	private Main main;


	public MenuModify(Main main, MenuMDS menuMDS) {
		
		this.menuMDS = menuMDS;
		this.menuDTO = menuMDS.getMenuDTO();
		this.mname = menuMDS.getMenuDTO().getMname();
		this.main = main;
		this.setTitle("메뉴수정");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);

		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.setBoard();
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}

	public void setBoard() {
		int rowIndex = menuMDS.getMenuTable().getSelectedRow();
		
		List<MenuDTO> list = CafeDAO.getInstance().getMDSItems();
		getTxtMenuName().setText(menuDTO.getMname());
		getTxtPrice().setText(Integer.toString(menuDTO.getPrice()));
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getMenuName());
			pCenter.add(getPrice());
		}
		return pCenter;
	}
	
	public JPanel getMenuName() {
		if(pMenuName==null) {
			pMenuName = new JPanel();
			pMenuName.add(new JLabel("메뉴명", JLabel.CENTER));
			pMenuName.add(getTxtMenuName());
		}
		return pMenuName;
	}		
	
	public JPanel getPrice() {
		if(pPrice == null) {
			pPrice = new JPanel();
			pPriceIn = new JPanel();
			pPriceIn.add(new JLabel("가격", JLabel.CENTER));
			pPrice.add(pPriceIn);
			pPrice.add(getTxtPrice());
		}
		return pPrice;
	}
	
	public JTextField getTxtMenuName() {
		if(txtMenuName == null) {
			txtMenuName = new JTextField(20);
		}
		return txtMenuName;
	}
	
	public JTextField getTxtPrice() {
		if(txtPrice == null) {
			txtPrice = new JTextField(20);
		}
		return txtPrice;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			//pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnOk());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new RoundedButton();
			btnOk.setText("수정");
			
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					DefaultTableModel tableModel = (DefaultTableModel) MenuMDS.getMenuTable().getModel();
//					Vector<Vector> rows = tableModel.getDataVector();
//					Vector row = rows.elementAt(rowIndex);
//					row.set(1, txtMenuName.getText());
//					row.set(2, txtPrice.getText());
//					tableModel.fireTableDataChanged();
//					
//					List<MenuDTO> list = CafeDAO.getInstance().getMDSItems();
//					MenuDTO mdto= new MenuDTO();
//					mdto.setMname(txtMenuName.getText());
//					mdto.setPrice(Integer.parseInt(txtPrice.getText()));
					
					menuDTO.setMname(txtMenuName.getText());
					menuDTO.setPrice(Integer.parseInt(txtPrice.getText()));
					CafeDAO.getInstance().updateMenu(menuDTO,mname );
//					
//					
//					MenuModify.this.dispose();
					
					
			
					
					menuMDS.refreshTable();
					main.refreshTab();
					dispose();
					
				}
			});
		}
		return btnOk;
	}
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new RoundedButton();
			btnCancel.setText("취소");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MenuModify.this.dispose();
				}
			});
		}
		return btnCancel;
	}
}

