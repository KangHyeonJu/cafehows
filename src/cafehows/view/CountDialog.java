package cafehows.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;
import cafehows.model.MenuDTO;

public class CountDialog extends JDialog {
	private Main main;
	private JPanel pCenter, pMenuName, pInquiry, pPrice, pSouth, pInquiryIn, pPriceIn, pCenterGrid, countPanel;
	private JLabel txtMenuName, txtPrice,count;
	private JTextField InputCount;
	private JComboBox ComboInquiry;
	private JComboBox comboIce;
	private JButton btnOk, btnCancel, btnInquiry;
	private String kindTemp = "커피";
	private String iceTemp;
	public int countInt=0;
	
	//private MenuDTO menuDTO;

	
	public CountDialog(Main main) {
		this.main = main;
	//	this.menuDTO = menuDTO;
		this.setTitle("수량 선택");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true); //상위 frame 클릭 불가
		this.setSize(200, 200);
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	


	public JPanel getPCenter() {
		if (pCenter == null) {
			pCenter = new JPanel();
			pCenter.setLayout(new GridLayout(3,1));
			pCenter.add(getPCenterGrid());
			pCenter.add(getCountPanel());
			pCenter.add(getIce());
		}
		return pCenter;
	}
			
	public JPanel getPCenterGrid() {
		if (pCenterGrid == null) {
			pCenterGrid = new JPanel();
			pCenterGrid.setLayout(new GridLayout(2,1));
			
			JPanel pCenterGrid1 = new JPanel();
			txtMenuName = new JLabel();
			txtMenuName.setText("메뉴: " + main.getMenuDTO().getMname());
			pCenterGrid1.add(txtMenuName);
			
			JPanel pCenterGrid2 = new JPanel();
			txtPrice = new JLabel();
			txtPrice.setText("가격: " + main.getMenuDTO().getPrice());
			pCenterGrid2.add(txtPrice);
			
			pCenterGrid.add(pCenterGrid1);
			pCenterGrid.add(pCenterGrid2);
		}
		return pCenterGrid;
	}
	
	public JPanel getCountPanel() {
		if(countPanel == null) {
			countPanel = new JPanel();
			JLabel countLabel = new JLabel("수량", JLabel.CENTER);
			InputCount = new JTextField(5);
			countPanel.add(countLabel);
			countPanel.add(InputCount);
		}
		return countPanel;
	}
		
//			JPanel menuPanel = new JPanel();
//			txtMenuName = new JLabel();
//			txtMenuName.setText("메뉴: " + main.getMenuDTO().getMname());
//			menuPanel.add(txtMenuName);
//			
//			txtPrice = new JLabel();
//			txtPrice.setText("가격: " + main.getMenuDTO().getPrice());
//			menuPanel.add(txtPrice);
//			
//			pCenter.add(menuPanel);
			
//			JPanel countPanel = new JPanel();
//			JLabel countLabel = new JLabel("수량", JLabel.CENTER);
//			InputCount = new JTextField(5);
//			countPanel.add(countLabel);
//			countPanel.add(InputCount);
//			pCenter.add(countPanel);
//			
//			pCenter.add(getIce());
//	
//		}
//		return pCenter;
//	}
	
	public JPanel getIce() {
		JPanel pIce = new JPanel();
		pIce.add(getComboIce());
		return pIce;
	}
	
	public JComboBox getComboIce() {
			
			iceTemp = main.getMenuDTO().getIce()==1? "ICE" : "HOT";
			if(main.getMenuDTO().getIceChangeable()==0&&iceTemp.equals("ICE")){
				String[] arrIce = {"ICE"};
				comboIce = new JComboBox(arrIce);
				return comboIce;
			}
			else if(main.getMenuDTO().getIceChangeable()==0&&iceTemp.equals("HOT")){
				String[] arrIce = {"HOT"};
				comboIce = new JComboBox(arrIce);
				return comboIce;
			}
			else if(main.getMenuDTO().getIceChangeable()==1){
				String[] arrIce = {"ICE","HOT"};
				comboIce = new JComboBox(arrIce);
				
				comboIce.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						iceTemp = comboIce.getSelectedItem().toString();
					}
				});
				return comboIce;
			}
	
		return comboIce;
	}


	public JPanel getPSouth() {
		if (pSouth == null) {
			pSouth = new JPanel();
			pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnOk());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}

	public JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("확인");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					main.getMenuDTO().setCount(Integer.parseInt(InputCount.getText()));
					System.out.println(InputCount.getText());
					
					for(MenuDTO dto2 : main.getOrderList()) {
						if(dto2.getMname().equals(main.getMenuDTO().getMname())
								&&dto2.getIce()==main.getMenuDTO().getIce()) {
							
							int ice = iceTemp.equals("ICE") ? 1 : 0;
							dto2.setIce(ice);
							dto2.setCount(main.getMenuDTO().getCount());
							System.out.println("countdialog에서 출력"+main.getMenuDTO());
							main.refreshOrderList();
							dispose();
							return;
						}}
					
					
					main.refreshOrderList();
					dispose();

				}
			});
		}
		return btnOk;
	}
	

	public JButton getBtnCancel() {
		if (btnCancel == null) {
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

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}

}
