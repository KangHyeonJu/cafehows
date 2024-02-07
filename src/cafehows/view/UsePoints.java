package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;
import cafehows.model.OrderDTO;


public class UsePoints extends JFrame{
	private Main main;
	private JPanel pCenter, pCono, pPoint, pUsePoint, pSouth;
	private JTextField txtCono, txtPoint, txtUsePoint;
	private JButton btnOk, btnCancel, searchBtn;
//	private CustomerDTO cDto = new CustomerDTO();
//	private CafeDAO dao = new CafeDAO();
	private static List<OrderDTO> orderList = CafeDAO.getInstance().getOrderItems();
	private static List<CustomerDTO> customerList = CafeDAO.getInstance().getCustomerItems();
	
	public UsePoints(Main main) {
		this.main = main;
		this.setTitle("회원 포인트 사용");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(350, 200);
		
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(3,1));
			pCenter = new JPanel();
			panel.add(getPCono());
			panel.add(getPPoint());
			panel.add(getPUsePoint());
			pCenter.add(panel);
			locationCenter();
		}
		return pCenter;
	}
	
	public JPanel getPCono() {
		if(pCono==null) {
			pCono = new JPanel();
			pCono.add(new JLabel("회원 번호", JLabel.CENTER));
			pCono.add(getTxtCono());
			pCono.add(getSerachBtn());
		}
		return pCono;
	}	

	public JPanel getPPoint() {
		if(pPoint==null) {
			pPoint = new JPanel();
			pPoint.add(new JLabel("보유 포인트", JLabel.CENTER));
			pPoint.add(getTxtPoint());
		}
		return pPoint;
	}		
	
	public JPanel getPUsePoint() {
		if(pUsePoint == null) {
			pUsePoint = new JPanel();
			pUsePoint.add(new JLabel("포인트 사용", JLabel.CENTER));
			pUsePoint.add(getTxtUsePoint());
		}
		return pUsePoint;
	}
	
	public JTextField getTxtCono() {
		if(txtCono == null) {
			txtCono = new JTextField(20);
			txtCono.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {					
				}
			});
		}
		return txtCono;
	}
	
	public JButton getSerachBtn() {
		if (searchBtn == null) {
			searchBtn = new JButton();
			JLabel btnImage = new JLabel();
			btnImage.setIcon(new ImageIcon(getClass().getResource("search.png")));
			searchBtn.add(btnImage);
			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int customerNum = Integer.parseInt(txtCono.getText().trim());
					int customerPoint = -1;
					for(int i=0;i<customerList.size();i++) {
						if(customerNum == customerList.get(i).getCno()){
							customerPoint = customerList.get(i).getPoint();
							getTxtPoint().setText(Integer.toString(customerPoint));
						}
					}
					if(customerPoint == -1) {
						JOptionPane.showMessageDialog(null, "회원정보가 없습니다.","오류",JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		return searchBtn;
	}
	
	public JTextField getTxtPoint() {
		if(txtPoint == null) {
			txtPoint = new JTextField(20);
		}
		return txtPoint;
	}
	
	public JTextField getTxtUsePoint() {
		if(txtUsePoint == null) {
			txtUsePoint = new JTextField(20);
		}
		return txtUsePoint;
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
			btnOk.setText("결제");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int cno = Integer.parseInt(getTxtCono().getText());
					int point = Integer.parseInt(getTxtPoint().getText());
					int usePoint = Integer.parseInt(getTxtUsePoint().getText());
					
					if(point>usePoint) {
						// orderlist date 저장, ono 생성, cno,price, finalprice 저장
						OrderDTO orderDTO= new OrderDTO();
						orderDTO.setCno(cno);
						orderDTO.setFinalprice(main.getTotalPrice());
						orderDTO.setPrice(main.getTotalPrice()-usePoint);
						int ono = CafeDAO.getInstance().insertOrderList(orderDTO);
						
						//결제하면 menusales count++, ono 저장,mno
						for(MenuDTO m : main.getOrderList()) {
							m.setOno(ono);
							m.setCumCount(m.getCumCount()+m.getCount());
							System.out.println(m);
							CafeDAO.getInstance().insertMenuSales(m);
						}
						
						//customer point 차감, recdate 갱신
						CustomerDTO cDTO = new CustomerDTO();
						cDTO.setPoint(point-usePoint);
						CafeDAO.getInstance().updatePoint(cDTO, cno);
						main.getOrderList().clear();
						main.refreshOrderList();
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "사용할 포인트가 보유보인트보다 클 수 없습니다.","오류",JOptionPane.ERROR_MESSAGE);
					}	
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
					UsePoints.this.dispose();
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
