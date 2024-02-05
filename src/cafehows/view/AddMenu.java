package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;

public class AddMenu extends JFrame{
	private JPanel pCenter, pMenuName, pInquiry, pPrice, pSouth, pInquiryIn, pPriceIn;
	private JTextField txtMenuName, txtPrice;
	private JComboBox ComboInquiry;
	private JButton btnOk, btnCancel, btnInquiry;
	

	public AddMenu() {
		this.setTitle("메뉴 추가");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);

		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getMenuName());
			pCenter.add(getInquiry());
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
	
	public JPanel getInquiry() {
		if(pInquiry==null) {
			pInquiry = new JPanel();
			pInquiryIn = new JPanel();
			pInquiryIn.add(new JLabel("종류", JLabel.CENTER));
			pInquiry.add(pInquiryIn);
			pInquiry.add(getComboInquiry());
			pInquiry.add(getBtnInquiry());
		}
		return pInquiry;
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
	
	public JComboBox getComboInquiry() {
		if(ComboInquiry == null) {
			
			Vector items = new Vector();
			List<CategoryDTO> itemsList = CafeDAO.getInstance().getCategoryItems();
		
			for(CategoryDTO item : itemsList) {
				items.add(item.getKind());
			}
			ComboInquiry = new JComboBox(items);
		}
		return ComboInquiry;
	}
	
	public JTextField getTxtPrice() {
		if(txtPrice == null) {
			txtPrice = new JTextField(20);
		}
		return txtPrice;
	}
	
	public JButton getBtnInquiry() {
		if(btnInquiry == null) {
			btnInquiry = new JButton();
			btnInquiry.setText("종류 조회");
			btnInquiry.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					TypeInquiry typeInquiry=  new TypeInquiry();
					typeInquiry.setVisible(true);
				}
			});
		}
		return btnInquiry;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnOk());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("추가");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnOk;
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
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			AddMenu aM = new AddMenu();
        	aM.setVisible(true);
	    });


	}

}
