package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;
import cafehows.model.MenuDTO;
import constant.CustomerViewState;

import exceptions.UnsuitableInputException;


public class CustomerModify extends JDialog{

	private JPanel customerNum, pCenter, pSouth, pNotice;
	private JTextField txtCustomerNum;
	private JButton btnOk, btnCancel, btnDelete,saveBtn;
	//private CafeDAO cafeDao = new CafeDAO();
//	private CustomerDTO cDto = new CustomerDTO();
//	private static List<CustomerDTO> customerList = CafeDAO.getInstance().getCustomerItems();
	//private static List<CustomerDTO> customerList2 = CafeDAO.getInstance().getCustomerState();
	private CustomerDialog customerDialog;
	private final CustomerViewState state;
	private final String PHONENUMBER_FORMAT = "^\\d{2,3}\\d{3,4}\\d{4}$";;
	//private String phoneNumber;
	
	public CustomerModify(CustomerDialog customerDialog,CustomerViewState state) {
		this.customerDialog = customerDialog;
		this.state = state;
		//this.phoneNumber = phoneNumber;			
		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		this.setModal(true); //상위 frame 클릭 불가
		this.getContentPane().add(getNotice(), BorderLayout.NORTH);
		this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		initTitle();
		locationCenter();
	}
	
	private void initTitle() {
		if(state == CustomerViewState.UPDATE) {
    		setTitle("고객 수정");
    	}else if(state == CustomerViewState.CREATE) {
    		setTitle("고객 추가");
    	}
	}
	
	public JPanel getNotice() {
		if(pNotice == null){
			pNotice = new JPanel();
			pNotice.add(new JLabel("010과 '-'를 제외한 번호 8자리를 입력해주세요."));
		}
		return pNotice;
	}
	
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getCustomerNum());
		}
		return pCenter;
	}
	
	public JPanel getCustomerNum() {
		if(customerNum==null) {
			customerNum = new JPanel();
			customerNum.add(new JLabel("회원 번호", JLabel.CENTER));
			customerNum.add(getTxtCustomerNum());
		}
		return customerNum;
	}
	
	
	public JTextField getTxtCustomerNum() {
		if(txtCustomerNum==null) {
			txtCustomerNum = new JTextField(20);
			if(state == CustomerViewState.UPDATE) {txtCustomerNum.setText(customerDialog.getPhoneNumber());}
		}
		return txtCustomerNum;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.setBackground(Color.WHITE);
			pSouth.add(getSaveBtn());
		//	pSouth.add(getBtnDelete());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	
//	
//	public JButton getBtnOk() {
//		if(btnOk == null) {
//			btnOk = new JButton();
//			btnOk.setText("등록");
//			btnOk.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					//입력제한
//					if(txtCustomerNum.getText().length() == 8) {
//						boolean flag = false;
//						for(CustomerDTO c : customerList2) {
//							if(c.getCno() == Integer.parseInt(txtCustomerNum.getText()))
//								flag = true;
//						}
//						if(flag) {
//							cDto.setCno(Integer.parseInt(txtCustomerNum.getText()));
//							cafeDao.reSign(cDto);
//						}else {
//							cDto.setCno(Integer.parseInt(txtCustomerNum.getText()));
//							cafeDao.insertCustomer(cDto);
//						}
//						customerDialog.refreshTable();
//						CustomerModify.this.dispose();
//					}else {
//						JOptionPane.showMessageDialog(null, "다시 입력해 주세요.","오류",JOptionPane.ERROR_MESSAGE);
//					}
//				}
//			});
//		}
//		return btnOk;
//	}
	
	private JButton getSaveBtn() {
		if(state == CustomerViewState.CREATE) {
        	saveBtn = new JButton("추가");
	        saveBtn.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	try {
	            		//유효성 검사
						validatePhoneNumber();
						CustomerDTO customerDTO = new CustomerDTO();
						customerDTO.setPhoneNumber(txtCustomerNum.getText().trim());
						CafeDAO.getInstance().insertCustomer(customerDTO);
						JOptionPane.showMessageDialog(null,"등록되었습니다","확인",JOptionPane.PLAIN_MESSAGE);
	            		
	            	}catch (UnsuitableInputException ue) {
						JOptionPane.showMessageDialog(null,ue.getMessage(),"확인",JOptionPane.WARNING_MESSAGE);
					}
	            }
	        });
    	}
        if(state == CustomerViewState.UPDATE) {
        	saveBtn = new JButton("수정");
	        saveBtn.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	try {
	            		validatePhoneNumber();
	    				CustomerDTO customerDTO = new CustomerDTO();
						customerDTO.setPhoneNumber(customerDialog.getPhoneNumber());
						CafeDAO.getInstance().updateCustomer(customerDTO,txtCustomerNum.getText().trim());
	            		JOptionPane.showMessageDialog(null,"수정되었습니다","확인",JOptionPane.PLAIN_MESSAGE);
		            }catch (UnsuitableInputException ue) {
						JOptionPane.showMessageDialog(null,ue.getMessage(),"확인",JOptionPane.WARNING_MESSAGE);
					}
	            }
	        });
    	}
        return saveBtn;
	}
	
//	public JButton getBtnDelete() {
//		if(btnDelete == null) {
//			btnDelete = new JButton();
//			btnDelete.setText("삭제");
//			btnDelete.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					//선택한 셀 전화 번호 받아와서 
//					//전화번호를 deleteCutomer 에 넘겨서 삭제
//					int cno = Integer.parseInt(txtCustomerNum.getText());
//					DefaultTableModel tableModel = (DefaultTableModel)customerDialog.getCustomerTable().getModel();
//					for(int i=0; i<customerList.size(); i++) {
//						if(cno == customerList.get(i).getCno()) {
//							tableModel.removeRow(i);
//						}
//					}
//					cafeDao.deleteCustomer(cDto, cno);
//					customerDialog.refreshTable();
//					CustomerModify.this.dispose();
//				}
//			});
//		}
//		return btnDelete;
//	}
//	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("취소");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CustomerModify.this.dispose();
				}
			});
		}
		return btnCancel;
	}
	private void validatePhoneNumber() throws UnsuitableInputException {
		
		if(!Pattern.matches(PHONENUMBER_FORMAT,txtCustomerNum.getText().trim()))
			throw new UnsuitableInputException("전화번호('-'없이 입력) 형식이 잘못 입력되었습니다");
	
		
	
	}
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
}
