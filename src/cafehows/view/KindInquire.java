package cafehows.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class KindInquire extends JFrame{
	private JPanel pEast, pWest, pSouth;
	private JTextField txtKind;
	private JButton btnModify, btnCancel, btnDelete, btnAdd;
	private static JTable jTable;
	
	public KindInquire() {
		this.setTitle("고객-등록/삭제");					
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(300, 200);
		
		//this.getContentPane().add(getPCenter(), BorderLayout.CENTER);
		this.getContentPane().add(new JScrollPane(getTable()), BorderLayout.WEST);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
	}
	
	public static JTable getTable() {
		if(jTable == null) {
			jTable = new JTable();
			final DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
			tableModel.addColumn("종류");
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel tcm = jTable.getColumnModel();tcm.getColumn(2).setCellRenderer(dtcr);
			for(int i=0; i<5; i++) tcm.getColumn(i).setCellRenderer(dtcr);
			
			
			jTable.getColumn("번호").setPreferredWidth(5);
		}
		return jTable;
	}	
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			pSouth = new JPanel();
			pSouth.setBackground(Color.WHITE);
			pSouth.add(getBtnModify());
			pSouth.add(getBtnDelete());
			pSouth.add(getBtnAdd());
			pSouth.add(getBtnCancel());
		}
		return pSouth;
	}
	
	public JButton getBtnModify() {
		if(btnModify == null) {
			btnModify = new JButton();
			btnModify.setText("수정");
			btnModify.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
				
			});
		}
		return btnModify;
	}
	
	public JButton getBtnAdd() {
		if(btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setText("추가");
			btnAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnAdd;
	}
	
	public JButton getBtnDelete() {
		if(btnDelete == null) {
			btnDelete = new JButton();
			btnDelete.setText("삭제");
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
				}
			});
		}
		return btnDelete;
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
			KindInquire kI = new KindInquire();
        	kI.setVisible(true);
	    });

	}

}
