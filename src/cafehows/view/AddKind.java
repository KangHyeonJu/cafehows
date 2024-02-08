package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.security.auth.Refreshable;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import cafehows.model.CafeDAO;
import cafehows.model.CategoryDTO;

public class AddKind extends JDialog{

	private TypeInquiry typeInquiry;
	private JPanel notice, kind, pSouth;
	private JTextField kindInput;
	private JButton btnCancel, btnAdd;
	private Main main;
	
	public AddKind(TypeInquiry typeInquiry, Main main) {
		this.main = main;
		this.typeInquiry = typeInquiry;
		this.setTitle("종류 추가");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(250,150);
		this.setModal(true); //상위 frame 클릭 불가
		this.getContentPane().add(getNotice(),BorderLayout.NORTH);
		this.getContentPane().add(getKindInput(),BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	
	//notice
	public JPanel getNotice() {
		if(notice == null) {
			notice = new JPanel();
			notice.add(new JLabel("추가할 종류를 입력하세요."));
		}
		return notice;
	}
	
	//종류 받기
	public JPanel getKindInput() {
		if(kind == null) {
			kind = new JPanel();
			JLabel label = new JLabel("종류", JLabel.CENTER);
			kind.add(label);
			if(kindInput == null) {
				kindInput = new JTextField();
				kindInput.setPreferredSize(new Dimension(100,30));
			}
			kind.add(kindInput);
		}
		return kind;
	}
	
	//하단
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			JPanel pButton = new JPanel();
			pButton.add(getBtnAdd());
			pButton.add(getBtnCancel());
			pSouth.add(pButton);
		}
		return pSouth;
	}
	
	//추가 버튼
	public JButton getBtnAdd() {
		if(btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("추가");
			btnAdd.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					CategoryDTO category = new CategoryDTO();
					category.setKind(kindInput.getText());
					CafeDAO.getInstance().addCategory(category);
					typeInquiry.refreshTable();
					main.refreshTab();
					dispose();
				}
			});
		}
		return btnAdd;
	}
	
	//취소 버튼
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
	
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
}
