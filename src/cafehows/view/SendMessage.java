package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cafehows.model.CafeDAO;
import cafehows.model.CustomerDTO;

public class SendMessage extends JDialog{
	private JTable recdateTable;
	private JPanel pCenter, pSouth, pNorth;
	private JTextArea txtMessage;
	private JButton btnOk, btnCancel;
	
	public SendMessage() {
		this.setTitle("문자 전송");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(500, 400);
		this.setModal(true); //상위 frame 클릭 불가
		this.getContentPane().add(getPNorth(), BorderLayout.NORTH);
		this.getContentPane().add(new JScrollPane(getPCenter()), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		locationCenter();
	}
	
	public JTable getRecDateTable() {
		if(recdateTable==null) {
			recdateTable = new JTable();
			recdateTable.setAutoCreateRowSorter(true);
			
			DefaultTableModel tableModel = (DefaultTableModel) recdateTable.getModel();
			tableModel.addColumn("회원번호");
			tableModel.addColumn("최근방문일");

			for(CustomerDTO dto : CafeDAO.getInstance().getRdcDate()) {
				Object[] rowData = {dto.getCno(), dto.getRecdate()};
				tableModel.addRow(rowData);
			}
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = recdateTable.getColumnModel();
			for(int i=0; i<2; i++) tcm.getColumn(i).setCellRenderer(dtcr);			
		}
		return recdateTable;
	}
	
	
	public JPanel getPNorth() {
		if(pNorth==null) {
			pNorth = new JPanel();
			JScrollPane jScrollPane = new JScrollPane(getRecDateTable());
			jScrollPane.setPreferredSize(new Dimension(400,100));
			pNorth.add(jScrollPane);
		}
		return pNorth;
	}
	
	
	public JPanel getPCenter() {
		if(pCenter==null) {
			pCenter = new JPanel();
			pCenter.add(getTxtMessage());
		}
		return pCenter;
	}

	public JTextArea getTxtMessage() {
		if(txtMessage==null) {
			txtMessage = new JTextArea(15, 45);
		}
		return txtMessage;
	}
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.add(getBtnOk());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnOk() {
		if(btnOk == null) {
			btnOk = new JButton();
			btnOk.setText("전송");
			btnOk.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					txtMessage.setText("");
					JOptionPane.showMessageDialog(null,"문자가 전송되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
				}
			});
		}
		return btnOk;
	}
	
	public JButton getBtnCancel() {
		if(btnCancel == null) {
			btnCancel = new JButton();
			btnCancel.setText("닫기");
			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SendMessage.this.dispose();
					
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
