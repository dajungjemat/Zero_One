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


public class BoardApp extends JFrame {
	private BoardApp board;
	private JTable jTable;
	private JPanel pSouth;
	private JButton btnInsert,btnInsert2;
	
	public BoardApp() {
		this.board = this;
		this.setTitle("게시판 리스트");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(new JScrollPane(getBoardTable()), BorderLayout.CENTER);
		this.getContentPane().add(getPSouth(), BorderLayout.SOUTH);
		this.setSize(600, 450);
		locationCenter();
	}
	
	public JTable getBoardTable() {
		if(jTable == null) {
			/*코드 추가*/		 
//			List<BoardDTO> boards = BoardDAO.getInstance().getBoards();
//			
//			for(int board :boards) {
//				Object[][] rowData = {BoardDTO.getBno()
//			}
			String[] columnNames = {"번호","제목","글쓴이","조회수","날짜"};
			DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
			
		
		        jTable = new JTable(tableModel);
		        
		
		        
		}	
		return jTable;
	}	
	
	        

	
	public JPanel getPSouth() {
		if(pSouth == null) {
			/*코드 추가*/
			pSouth = new JPanel();
			pSouth.add(getBtnInsert());
			pSouth.add(getBtnInsert2());
			
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
					new PostDialog(BoardApp.this).setVisible(true);
					
				}
			});
			
		}
		return btnInsert;
	}
	
	public JButton getBtnInsert2() {
		if(btnInsert2 == null) {
			btnInsert2 = new JButton();
			btnInsert2.setText("모달 확인용");
			btnInsert2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new CommentDialog(BoardApp.this).setVisible(true);
					
				}
			}); //모달 확인용
		}
		
		return btnInsert2;
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
