package boardView;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import model.BoardsDAO;
import model.BoardsDTO;

public class PostDialog extends JDialog {
	private JPanel pContent, pTitle,pSouth;
    private JTextField titleField;
    private JTextArea contentArea;
    private JButton saveButton,updateButton,deleteButton;
    private String email;
    private int boardNo;
    private BoardApp boardApp;

    public PostDialog(BoardApp boardApp, String email, int boardNo) {
    	this.email = email;
    	this.boardNo = boardNo;
    	this.boardApp = boardApp;
    	this.setTitle("게시물 작성");
    	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	this.setResizable(false);
    	this.setModal(true);
    	this.setSize(800,600);
    	
    	 this.setLocation(
		boardApp.getLocationOnScreen().x + (boardApp.getWidth()-this.getWidth())/2,
		boardApp.getLocationOnScreen().y + (boardApp.getHeight()-this.getHeight())/2
	);
    	
    	 
    	this.getContentPane().add(getPTitle(),BorderLayout.NORTH);
    	this.getContentPane().add(getPContent(),BorderLayout.CENTER);
    	this.getContentPane().add(getPSouth(),BorderLayout.SOUTH);
    }
       

	public JPanel getPTitle() {
    	if(pTitle == null) {
    		pTitle = new JPanel();
    		pTitle.setLayout(new BorderLayout());
    		pTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
    		pTitle.setPreferredSize(new Dimension(0,70));
    		//setPreferredSize(new Dimension) null 아니라도 사용가능
    	
    		titleField = new JTextField();
    		titleField.setText("Title");
    		titleField.setHorizontalAlignment(JTextField.CENTER);
    		titleField.setBorder(new LineBorder(Color.BLACK, 3));
            pTitle.add(titleField);
            
    	}
    	return pTitle;
    }
    
	  public JPanel getPContent() {
	        if (pContent == null) {
	            pContent = new JPanel();
	            pContent.setLayout(new BorderLayout()); 
	            pContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
	            JScrollPane js = new JScrollPane(); 
	            pContent.add(js);
	            contentArea = new JTextArea();  
	            contentArea.setBorder(new LineBorder(Color.BLACK, 5));        
	            pContent.add(contentArea, BorderLayout.CENTER);
	        } //JScrollpane
	        return pContent;
	    }
     
    private JPanel getPSouth() {
        if (pSouth == null) {
            pSouth = new JPanel(null);  
            pSouth.setPreferredSize(new Dimension(800,100));
            getSaveButton().setBounds(300, 25, 150, 50);
            pSouth.add(getSaveButton());
//            getUpdateButton().setBounds(150, 25, 150, 50);
//            pSouth.add(getUpdateButton());
//            getUpdateButton().setBounds(310, 25, 150, 50);
//            pSouth.add(getDeleteButton());
        }
        return pSouth;
    }

    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton();
            saveButton.setText("저장");
            Font myFont1 = new Font("Serif", Font.BOLD, 15);
            saveButton.setFont(myFont1);
            saveButton.setForeground(Color.WHITE);
            saveButton.setBackground(Color.BLACK);

            // ActionListener 추가
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BoardsDTO board = new BoardsDTO();
                    board.setTitle(titleField.getText());
                    board.setBoardContent(contentArea.getText());
                    board.setEmail(email);            
                    BoardsDAO.getInstance().insertBoards(board);
                    boardApp.refreshBoard();
                    dispose();
                }
            });
        }
        return saveButton;
    }
    
//    private JButton getUpdateButton() {
//    	 if (updateButton == null) {
//    		 updateButton = new JButton();
//    		 updateButton.setText("수정");
//             Font myFont1 = new Font("Serif", Font.BOLD, 15);
//             updateButton.setFont(myFont1);
//             updateButton.setForeground(Color.WHITE);
//             updateButton.setBackground(Color.BLACK);
//             
//             updateButton.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					BoardsDTO board = new BoardsDTO();
//					 board.setTitle(titleField.getText());
//	                 board.setBoardContent(contentArea.getText());
//	                 BoardsDAO.getInstance().updateBoards(board,boardNo);
//					 dispose();
//					
//				}           	 
//             });
//             
//    	    }
//    	 return updateButton;
//    }  
//    private JButton getDeleteButton() {
//   	 if (deleteButton == null) {
//   		 deleteButton = new JButton();
//   		 deleteButton.setText("삭제");
//            Font myFont1 = new Font("Serif", Font.BOLD, 15);
//            deleteButton.setFont(myFont1);
//            deleteButton.setForeground(Color.WHITE);
//            deleteButton.setBackground(Color.BLACK);
//            
//            deleteButton.addActionListener(new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					
//	                 BoardsDAO.getInstance().deleteBoards(boardNo); //
//					 dispose();
//					
//				}           	 
//            });
//            
//   	    }
//   	 return updateButton;
//   }  
    
}
    		