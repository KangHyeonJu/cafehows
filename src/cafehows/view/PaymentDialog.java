package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;
import cafehows.model.OrderDTO;



public class PaymentDialog extends JDialog{
	private Main main;
	private JPanel orderPanel;
	private JLabel priceField,onoField,pointField,finalPriceField;
	private JTable orderTable;

	private int cno, point,usePoint,ono,finalPrice;

	private PaymentDialog paymentDialog;
	

	public PaymentDialog(Main main) {
		this.setModal(true);
		this.paymentDialog = this;
		this.main = main;
		finalPrice = main.getTotalPrice();
	//	this.menuDTO = menuDTO;
		this.setTitle("결제");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true); //상위 frame 클릭 불가
		this.setSize(500,500);
		this.setResizable(false); //사이즈 고정
		this.getContentPane().add(getOrderPanel(), BorderLayout.CENTER);
		this.getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
		locationCenter();
	}
	
	

	public int getFinalPrice() {
		return finalPrice;
	}



	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}



	public JLabel getPointField() {
		return pointField;
	}



	public void setPointField(JLabel pointField) {
		this.pointField = pointField;
	}



	public JLabel getFinalPriceField() {
		return finalPriceField;
	}



	public void setFinalPriceField(JLabel finalPriceField) {
		this.finalPriceField = finalPriceField;
	}



	public int getCno() {
		return cno;
	}
	public int getPoint() {
		return point;
	}
	public int getUsePoint() {
		return usePoint;
	}
	


	public void setCno(int cno) {
		this.cno = cno;
	}


	public void setPoint(int point) {
		this.point = point;
	}


	public void setUsePoint(int usePoint) {
		this.usePoint = usePoint;
	}

	
	
	public JLabel getOnoField() {
		return onoField;
	}



	public void setOnoField(JLabel onoField) {
		this.onoField = onoField;
	}



	public JPanel getOrderPanel() {
		if(orderPanel==null) {
			orderPanel = new JPanel();
			orderPanel.setLayout(new BorderLayout());
			JPanel northPanel = new JPanel();
			JLabel label = new JLabel("주문 목록");
			label.setAlignmentX(JLabel.CENTER);
			label.setPreferredSize(new Dimension(70, 30));
			label.setHorizontalAlignment(JLabel.CENTER);
			northPanel.add(label);
			onoField = new JLabel();
			northPanel.add(onoField);
			
			orderPanel.add(northPanel, BorderLayout.NORTH);
			
			orderPanel.add(new JScrollPane(getOrderTable()),BorderLayout.CENTER);

		}
		return orderPanel;
	}
	
	public JTable getOrderTable() {
		if(orderTable == null) {
			orderTable = new JTable() {
				@Override
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			orderTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) orderTable.getModel();
			tableModel.addColumn("메뉴명");
			tableModel.addColumn("가격");
			tableModel.addColumn("수량");
			tableModel.addColumn("아이스/핫");
			
			for(MenuDTO dto : main.getOrderList()) {
				Object[] rowData = {dto.getMname(), dto.getPrice(),dto.getCount(),dto.getIce()};
				tableModel.addRow(rowData);
			}
			
			
			main.refreshOrderList();
		}
		return orderTable;
	}

	public JPanel getSouthPanel() {
			JPanel southPanel = new JPanel();
			southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.Y_AXIS));
			
			
			JPanel price = new JPanel();
			JLabel priceLabel = new JLabel("총 가격");
			priceField = new JLabel(Integer.toString(main.getTotalPrice()));
			price.add(priceLabel);
			price.add(priceField);
			southPanel.add(price);
			
			JPanel point = new JPanel();
			JLabel pointLabel = new JLabel("포인트");
			pointField = new JLabel(Integer.toString(usePoint));
			point.add(pointLabel);
			point.add(pointField);
			southPanel.add(point);
			
			JPanel finalPrice = new JPanel();
			JLabel finalPriceLabel = new JLabel("최종 가격");
			finalPriceField = new JLabel(Integer.toString(main.getTotalPrice()-usePoint));
			finalPrice.add( finalPriceLabel);
			finalPrice.add( finalPriceField);
			southPanel.add(	finalPrice);
			
			JPanel orderBtnPanel = new JPanel();	
			orderBtnPanel.add(getUsePointBtn());

		//	orderBtnPanel.add(getOkBtn());
			southPanel.add(	orderBtnPanel);

			orderBtnPanel.add(getCardBtn());
			orderBtnPanel.add(getCashBtn());
			southPanel.add(orderBtnPanel);

		
		return southPanel;
	}
	
	public JButton getUsePointBtn() {
			JButton usePointBtn = new RoundedButton();
			usePointBtn.setText("포인트 적립/사용");
			usePointBtn.addActionListener(e->{
				UsePoints usePoints= new UsePoints(paymentDialog,main);
				usePoints.setVisible(true);
			});
		
		
		return usePointBtn;
	}

	public JButton getCardBtn() {
			JButton cardBtn = new RoundedButton();
			cardBtn.setText("카드결제");
			cardBtn.addActionListener(e->{

				// orderlist date 저장, ono 생성, cno,price, finalprice 저장
				OrderDTO orderDTO= new OrderDTO();
				orderDTO.setCno(cno);
				orderDTO.setPrice(main.getTotalPrice());
				orderDTO.setFinalprice(main.getTotalPrice()-usePoint);
				ono = CafeDAO.getInstance().insertOrderList(orderDTO);
				onoField.setText(Integer.toString(ono));
				
				//결제하면 menusales count++, ono 저장,mno
				for(MenuDTO m : main.getOrderList()) {
					m.setOno(ono);
					m.setCumCount(m.getCumCount()+m.getCount());
					System.out.println(m);
					CafeDAO.getInstance().insertMenuSales(m);
				}
				
				//customer point 차감, recdate 갱신
				if(cno!=0 &&cno!=-1) {
				CustomerDTO cDTO = new CustomerDTO();
				cDTO.setPoint(point-usePoint+(int)(finalPrice*0.05));
				CafeDAO.getInstance().updatePoint(cDTO, cno);}
				main.getOrderList().clear();
				main.refreshOrderList();
				dispose();
			});

		return cardBtn;
	}
	
	public JButton getCashBtn() {
		JButton cashBtn = new RoundedButton();
		cashBtn.setText("현금결제");
		cashBtn.addActionListener(e -> {
			CashDialog cashDialog = new CashDialog(main,paymentDialog);
			cashDialog.setModal(true);
			cashDialog.setVisible(true);
		});
		return cashBtn;

	}
	

	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth() / 2;
		int leftTopY = centerPoint.y - this.getHeight() / 2;
		this.setLocation(leftTopX, leftTopY);
	}
}
