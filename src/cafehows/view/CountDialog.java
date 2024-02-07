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
	private JPanel pCenter, pMenuName, pInquiry, pPrice, pSouth, pInquiryIn, pPriceIn;
	private JLabel txtMenuName, txtPrice,count;
	private JTextField InputCount;
	private JComboBox ComboInquiry;
	private JButton btnOk, btnCancel, btnInquiry;
	private String kindTemp = "커피";
	public int countInt=0;
	//private MenuDTO menuDTO;

	
	public CountDialog(Main main) {
		this.main = main;
	//	this.menuDTO = menuDTO;
		this.setTitle("수량 선택");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.setSize(300, 200);

		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	


	public JPanel getPCenter() {
		if (pCenter == null) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(3,1));
			pCenter = new JPanel();
			
			txtMenuName = new JLabel();
			txtMenuName.setText(main.getMenuDTO().getMname());
			pCenter.add(txtMenuName);
			
			txtPrice = new JLabel();
			txtPrice.setText(Integer.toString(main.getMenuDTO().getPrice()));
			pCenter.add(txtPrice);
			
			JPanel countPanel = new JPanel();
			JLabel countLabel = new JLabel("수량", JLabel.CENTER);
			InputCount = new JTextField(3);
			countPanel.add(countLabel);
			countPanel.add(InputCount);
			pCenter.add(countPanel);
	
		}
		return pCenter;
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
						if(dto2.getMname().equals(main.getMenuDTO().getMname())) {
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
