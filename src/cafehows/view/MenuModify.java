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
import javax.swing.JComboBox;
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
	private JPanel pCenter, pMenuName, pPrice, pSouth, pPriceIn,pIceIn;
	private JTextField txtMenuName, txtPrice;
	private JComboBox ComboInquiry,comboIce, comboIceChangeable ;
	private String iceTemp;
	private String iceChangeableTemp;
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
		System.out.println(menuDTO);
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
			pCenter.add(getIce());
		}
		return pCenter;
	}
	public JPanel getIce() {
		JPanel pIce = new JPanel();
		pIce = new JPanel();
		pIceIn = new JPanel();
		pIceIn.add(new JLabel("아이스/핫", JLabel.CENTER));
		pIce.add(pIceIn);
		pIce.add(getComboIce());
		pIce.add(getComboIceChangeable());

	return pIce;
}
	public JComboBox getComboIce() {
		if (comboIce == null) {
			
			if(menuDTO.getIceChangeable()==0 && menuDTO.getIce()==0) {
				String[] arrIce = {"HOT"};
				comboIce = new JComboBox(arrIce);
			}
			else if(menuDTO.getIceChangeable()==0 && menuDTO.getIce()==1) {
				String[] arrIce = {"ICE"};
				comboIce = new JComboBox(arrIce);
				
			}
			else {
				String[] arrIce = {"ICE","HOT"};
				comboIce = new JComboBox(arrIce);
			}
			
			
			iceTemp = comboIce.getSelectedItem().toString();
			comboIce.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iceTemp = comboIce.getSelectedItem().toString();
				}
			});
		}
		return comboIce;
	}

	public JComboBox getComboIceChangeable() {
			if(menuDTO.getIceChangeable()==0) {
				String[] arrIceChangeable = {"변경불가","변경가능"};
				 comboIceChangeable = new JComboBox(arrIceChangeable);
			}
			else {
				String[] arrIceChangeable = {"변경가능","변경불가"};
				 comboIceChangeable = new JComboBox(arrIceChangeable);
			}
			
			iceChangeableTemp = comboIceChangeable.getSelectedItem().toString();
			comboIceChangeable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iceChangeableTemp = comboIceChangeable.getSelectedItem().toString();
				}
			});
	
		return comboIceChangeable;
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

					int ice = iceTemp.equals("ice") ? 1 : 0;
					menuDTO.setIce(ice);
					
					int iceChangeable = iceChangeableTemp.equals("변경가능") ? 1 : 0;
					menuDTO.setIceChangeable(iceChangeable);
					System.out.println(menuDTO);
//					menuDTO.setIce(iceTemp);
//					menuDTO.setMname(txtMenuName.getText());
//					menuDTO.setPrice(Integer.parseInt(txtPrice.getText()));
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

