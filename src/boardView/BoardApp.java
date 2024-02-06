package boardView;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.BoardsDAO;
import model.BoardsDTO;


public class BoardApp extends JFrame {
	private BoardApp board;
	private JTable jTable;
	private JPanel pSouth;
	private JButton btnInsert;
	private String email;
	//private int hitcount;
	
	public BoardApp() {
		this.board = this;
		this.email = email;
		//this.hitcount = 0;
		this.setTitle("게시판 리스트");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(new JScrollPane(getBoardTable()), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.setSize(2000, 1200);
		locationCenter();
	}
	
	public JTable getBoardTable() {
		if(jTable == null) {
			jTable = new JTable();
			
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("제목");
			tableModel.addColumn("내용");
			tableModel.addColumn("날짜");
			tableModel.addColumn("글쓴이");
			tableModel.addColumn("조회수");
			refreshBoard();		
		    jTable = new JTable(tableModel);	
		    
		    
		    jTable.addMouseListener(new MouseAdapter() {
		    	public void mouseClicked(MouseEvent e) {
		    		
		    		int rowIndex = jTable.getSelectedRow();
		    		if(rowIndex != -1) {
		    			int boardNo = (int)jTable.getValueAt(rowIndex, 0);
		    			
		    			CommentDialog commentDialog = new CommentDialog(board,boardNo);
		    			commentDialog.setVisible(true);
		    		}
		    	}

			});		    
		}	
		return jTable;
	}	//tablecellrenderer 디자인
	
	public void refreshBoard() {
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.setNumRows(0);
		for(BoardsDTO dto : BoardsDAO.getInstance().getBoards()) {
			Object[] rowData = { dto.getBoardNo(), dto.getTitle(), dto.getBoardContent(), dto.getBoardDate(), dto.getEmail(),dto.getHitcount() };
			tableModel.addRow(rowData);
		}
	}
	
	
	public JPanel getPSouth() {
		if(pSouth == null) {
			/*코드 추가*/
			pSouth = new JPanel();
			pSouth.add(getBtnInsert());
			
			
		}
		return pSouth;
	}

	public JButton getBtnInsert() {
		if(btnInsert == null) {
			/*코드 추가*/
			btnInsert = new JButton();
			btnInsert.setText("추가");
			btnInsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new PostDialog(board, "casdf15@naver.com",3).setVisible(true);
					
				}
			});
			
		}
		return btnInsert;
	}
	

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> {
        	BoardApp board = new BoardApp();
        	board.setVisible(true);
	    });
	}	
	
}
