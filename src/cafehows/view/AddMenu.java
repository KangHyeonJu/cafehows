package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;
import cafehows.model.MenuDTO;

public class AddMenu extends JDialog {
	private Main main;
	private JPanel pCenter, pMenuName, pInquiry, pPrice, pSouth, pInquiryIn, pPriceIn, pIceIn;
	private JTextField txtMenuName, txtPrice;
	private JComboBox ComboInquiry,comboIce;
	private JButton btnOk, btnCancel, btnInquiry;
	private String kindTemp = "커피";
	private String iceTemp = "ice";
	private String iceChangeableTemp = "변경가능";

	public AddMenu(Main main) {
		this.main = main;
		this.setTitle("메뉴 추가");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 300);
		this.setModal(true); //상위 frame 클릭 불가
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	
	

	public JPanel getPCenter() {
		if (pCenter == null) {
			pCenter = new JPanel(new GridLayout(4,1));
			//pCenter.add(new JLabel("종류", JLabel.CENTER));
			pCenter.add(getInquiry());
			
			//pCenter.add(new JLabel("메뉴명", JLabel.CENTER));
			pCenter.add(getMenuName());
			
			//pCenter.add(new JLabel("아이스/핫", JLabel.CENTER));
			pCenter.add(getIce());
			
			//pCenter.add(new JLabel("가격", JLabel.CENTER));
			pCenter.add(getPrice());
			
		}
		return pCenter;
	}


	public JPanel getInquiry() {
		if (pInquiry == null) {
			pInquiry = new JPanel();
			pInquiryIn = new JPanel();
			pInquiryIn.add(new JLabel("종류", JLabel.CENTER));
			pInquiry.add(pInquiryIn);
			pInquiry.add(getComboInquiry(), BorderLayout.CENTER);
			pInquiry.add(getBtnInquiry(), BorderLayout.CENTER);
		}
		return pInquiry;
	}

	public JPanel getMenuName() {
		if (pMenuName == null) {
			pMenuName = new JPanel();
			pMenuName.add(new JLabel("메뉴명", JLabel.CENTER));
			pMenuName.add(getTxtMenuName(), BorderLayout.CENTER);

		}
		return pMenuName;
	}

	public JPanel getPrice() {
		if (pPrice == null) {
			pPrice = new JPanel();
			pPriceIn = new JPanel();
			pPriceIn.add(new JLabel("가격", JLabel.CENTER));
			pPrice.add(pPriceIn);
			pPrice.add(getTxtPrice(), BorderLayout.CENTER);
		}
		return pPrice;
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

			String[] arrIce = {"ICE","HOT"};
			comboIce = new JComboBox(arrIce);
			
			comboIce.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iceTemp = comboIce.getSelectedItem().toString();
				}
			});
		}
		return comboIce;
	}

	public JComboBox getComboIceChangeable() {
	
			String[] arrIceChangeable = {"변경가능","변경불가"};
			JComboBox comboIceChangeable = new JComboBox(arrIceChangeable);
			
			comboIceChangeable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					iceChangeableTemp = comboIceChangeable.getSelectedItem().toString();
				}
			});
	
		return comboIceChangeable;
	}

	public JTextField getTxtMenuName() {
		if (txtMenuName == null) {
			txtMenuName = new JTextField(14);
		}
		return txtMenuName;
	}

	public JComboBox getComboInquiry() {
		if (ComboInquiry == null) {

			Vector items = new Vector();
			List<CategoryDTO> itemsList = CafeDAO.getInstance().getCategoryItems();

			for (CategoryDTO item : itemsList) {
				items.add(item.getKind());
			}
			ComboInquiry = new JComboBox(items);
			ComboInquiry.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					kindTemp = ComboInquiry.getSelectedItem().toString();
				}
			});
		}
		return ComboInquiry;
	}

	public JTextField getTxtPrice() {
		if (txtPrice == null) {
			txtPrice = new JTextField(14);
		}
		return txtPrice;
	}

	public JButton getBtnInquiry() {
		if (btnInquiry == null) {
			btnInquiry = new JButton();
			btnInquiry.setText("조회");
			JLabel btnImage = new JLabel();
			btnImage.setIcon(new ImageIcon(getClass().getResource("search.png")));
			btnInquiry.add(btnImage);
			btnInquiry.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TypeInquiry typeInquiry = new TypeInquiry(main);
					typeInquiry.setVisible(true);
				}
			});
		}
		return btnInquiry;
	}

	public JPanel getPSouth() {
		if (pSouth == null) {
			pSouth = new JPanel();
			//pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnOk());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}

	public JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new RoundedButton();
			btnOk.setText("추가");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(txtMenuName.getText().equals("") || Integer.parseInt(txtPrice.getText())==0) {
						JOptionPane.showMessageDialog(null, "빈칸을 입력해주세요.");
					}else {
						MenuDTO menu = new MenuDTO();
						menu.setMname(txtMenuName.getText());
						menu.setPrice(Integer.parseInt(txtPrice.getText()));

						menu.setCano(CafeDAO.getInstance().getCategoryBykind(kindTemp).getCano());
						int ice = iceTemp.equals("ICE") ? 1 : 0;
						menu.setIce(ice);
						
						int iceChangeable = iceChangeableTemp.equals("변경가능") ? 1 : 0;
						menu.setIceChangeable(iceChangeable);
						
						CafeDAO.getInstance().insertMenu(menu);
						
						main.refreshTab();
						dispose();
					}
				}
			});
		}
		return btnOk;
	}

	public JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new RoundedButton();
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

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}

}
