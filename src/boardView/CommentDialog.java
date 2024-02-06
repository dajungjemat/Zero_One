package boardView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.BoardsDAO;
import model.BoardsDTO;
import model.CommentsDAO;
import model.CommentsDTO;

public class CommentDialog extends JDialog{
	private JPanel pContent,pSouth, boardArea,pNorth;
	private JTable jTable;
    private JTextField titleField,commentField;
    private JTextArea contentArea;
    private JButton commentButton,updateButton, deleteButton;;
    private int boardNo;
    private String email;
    private BoardApp boardApp;
    
    //게시판 화면에서 게시글을 클릭했을때 보이도록 
    //,pCommentBoard
    
    public CommentDialog(BoardApp boardApp, int boardNo) {
    	this.setTitle("게시글 보기");   
    	this.boardNo = boardNo;
    	this.boardApp = boardApp;
    	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	this.setResizable(false);
    	this.setModal(true);
    	this.setSize(850,700);
    	this.setLocation(
    			boardApp.getLocationOnScreen().x + (boardApp.getWidth()-this.getWidth())/2,
    			boardApp.getLocationOnScreen().y + (boardApp.getHeight()-this.getHeight())/2);
    	
    	this.getContentPane().add(new JScrollPane(getPNorth()),BorderLayout.NORTH);
    	this.getContentPane().add(new JScrollPane(getPContent()),BorderLayout.CENTER);
    	this.getContentPane().add(new JScrollPane(getPSouth()),BorderLayout.SOUTH);
    	this.setBoard();   	
    }	
	
	public JPanel getPContent() {
        if (pContent == null) {
            pContent = new JPanel();
            pContent.setLayout(new BorderLayout()); 
            pContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            contentArea = new JTextArea();  
            contentArea.setBorder(new LineBorder(Color.BLACK, 5));        
            pContent.add(contentArea, BorderLayout.CENTER);
        } 
        return pContent;
    }

	private JPanel getPSouth() {
		  if (pSouth == null) {
	          pSouth = new JPanel(null);
	          pSouth.setPreferredSize(new Dimension(800,200));
	          pSouth.setBorder(BorderFactory.createCompoundBorder(
	        		    BorderFactory.createEmptyBorder(10, 10, 10, 10),
	        		    new LineBorder(Color.BLACK, 1)
	        		    ));
	         
	          pSouth.add(getBoardArea());
	          pSouth.add(getCommentField()); 
	          pSouth.add(getCommentButton());
				 
	        			         
		  } //댓글 게시판 table추가
		  return pSouth;
	}
	
	public JPanel getPNorth() { 
        if (pNorth == null) {
            pNorth = new JPanel(new FlowLayout()); 
            pNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Adding components to pNorth panel
            //pNorth.add(getPTitle());
            
    		titleField = new JTextField();
    		titleField.setText("Title");
    		titleField.setHorizontalAlignment(JTextField.CENTER);
    		titleField.setBorder(new LineBorder(Color.BLACK, 3));
    		titleField.setPreferredSize(new Dimension(400,30));
    		pNorth.add(titleField);           
            pNorth.add(getUpdateButton());
            pNorth.add(getDeleteButton());
        }
        return pNorth;
    }


//	public JPanel getPTitle() {
//    	if(pTitle == null) {
//    		pTitle = new JPanel();
//    		pTitle.setLayout(new BorderLayout());
//    		pTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
//    		pTitle.setPreferredSize(new Dimension(0,70));
//  	
//    		titleField = new JTextField();
//    		titleField.setText("Title");
//    		titleField.setHorizontalAlignment(JTextField.CENTER);
//    		titleField.setBorder(new LineBorder(Color.BLACK, 3));
//            pTitle.add(titleField);
//    	}
//    	return pTitle;
//    }

	  public JPanel getBoardArea() { 
		  if(boardArea == null) { 
			  boardArea = new JPanel();
			  //boardArea.setPreferredSize(new Dimension(600,50));
			  boardArea.setBounds(11, 11, 810, 110);
			  boardArea.add(new JScrollPane(getCommentTable()));
	  }	
	  
	  return boardArea; }
	 
    
	
	private JTextField getCommentField() {
		if(commentField == null) {
			commentField = new JTextField();
			//commentField.setBorder(new LineBorder(Color.BLACK, 3));
			commentField.setPreferredSize(new Dimension(400,50));
			commentField.setBounds(175, 140, 400, 50);
		}
		return commentField;
	}
	

	private JButton getCommentButton() {
		if(commentButton == null) {
			//ImageIcon img = new ImageIcon("message.png");
			commentButton = new JButton();
			commentButton.setText("게시");
			Font myFont1 = new Font("Serif", Font.BOLD, 12);
			commentButton.setFont(myFont1);
			commentButton.setPreferredSize(new Dimension(100,50));
			commentButton.setBounds(575,140,100,50);
			commentButton.setForeground(Color.WHITE);
			commentButton.setBackground(Color.BLACK);
			//commentButton.setBorder(new LineBorder(Color.BLACK, 1, true)); //둥글
			
			commentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CommentsDTO comment = new CommentsDTO();
					CommentsDAO.getInstance().insertComment(comment);
					boardApp.refreshBoard();
					dispose(); //수정 
				}
				
			});		
		}
		return commentButton;
	}
	
	public void setBoard() {
		BoardsDTO board = BoardsDAO.getInstance().getBoardsByBoardNo(boardNo);
		BoardsDAO.getInstance().addHitcount(board.getHitcount(), boardNo);
		titleField.setText(board.getTitle());
		contentArea.setText(board.getBoardContent());	
		//titleField.disable();
	}

	
	public JTable getCommentTable() {
		if(jTable ==null) {
			jTable = new JTable();
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("날짜");
			tableModel.addColumn("댓글");
			//tableModel.addColumn("게시판번호");
			refreshBoard();
			jTable = new JTable(tableModel);
			
			
			
			//jTable.setBackground(Color.WHITE);
		}
		return jTable;
	}
	
	public void refreshBoard() {
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.setNumRows(0);
		for(CommentsDTO cto : CommentsDAO.getInstance().getComments()) {
			Object[] rowData = {cto.getCommentNo(), cto.getCommentDate(), cto.getComment(),cto.getBoardNo() };
			tableModel.addRow(rowData);
		}
	}
	
	
	
	private JButton getUpdateButton() { 
        if (updateButton == null) {
            updateButton = new JButton("수정"); 
            Font myFont1 = new Font("Serif", Font.BOLD, 15);
          updateButton.setFont(myFont1);
          updateButton.setForeground(Color.WHITE);
          updateButton.setBackground(Color.BLACK);
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	BoardsDTO board = new BoardsDTO();
                	board.setTitle(titleField.getText());
                	board.setBoardContent(contentArea.getText());
                	
                	
                	
                	BoardsDAO.getInstance().updateBoards(board,boardNo);
                	boardApp.refreshBoard();
                	dispose();
                }
            });
        }
        return updateButton;
    }

    private JButton getDeleteButton() { 
        if (deleteButton == null) {
            deleteButton = new JButton("삭제"); 
            Font myFont1 = new Font("Serif", Font.BOLD, 15);
            deleteButton.setFont(myFont1);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setBackground(Color.BLACK);
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	BoardsDAO.getInstance().deleteBoards(boardNo);
                	boardApp.refreshBoard();
                	dispose();
                }
            });
        }
        return deleteButton;
    }
	
	
	//titleField - 버튼 2개 flowLayout으로 만들기, 
	
}//비행기 이미지 

	
	/*
	 * } CommentDAO(예시)에 저장된 데이터를 불러오는 창
	 */
