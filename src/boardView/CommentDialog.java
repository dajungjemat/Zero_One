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

public class CommentDialog extends JDialog{
	private JPanel pContent, pTitle,pSouth,pTextField; 
    private JTextField titleField,commentField;
    private JTextArea contentArea,boardArea;
    private JButton commentButton;
    //게시판 화면에서 게시글을 클릭했을때 보이도록 
    //,pCommentBoard
    
    public CommentDialog(BoardApp boardApp) {
    	this.setTitle("게시글 보기");   //다른 사용자가 올린 게시글 제목으로 설정?
    	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	this.setResizable(false);
    	this.setModal(true);
    	this.setSize(850,600);
    	this.setLocation(
    			boardApp.getLocationOnScreen().x + (boardApp.getWidth()-this.getWidth())/2,
    			boardApp.getLocationOnScreen().y + (boardApp.getHeight()-this.getHeight())/2);
    	
    	this.getContentPane().add(new JScrollPane(getPTitle()),BorderLayout.NORTH);
    	this.getContentPane().add(new JScrollPane(getPContent()),BorderLayout.CENTER);
    	this.getContentPane().add(new JScrollPane(getPTextField()),BorderLayout.SOUTH);
    	
    	
    }

	private JTextArea getBoardArea() {
		if(boardArea == null) {
			boardArea = new JTextArea();
			boardArea.setEditable(false);
			boardArea.add(getPSouth());
		}
		return boardArea;
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
    	//hinttext 추가하면 좋을듯(placeholder 기능)
    	}
    	return pTitle;
    }

	public JPanel getPContent() {
        if (pContent == null) {
            pContent = new JPanel();
            pContent.setLayout(new BorderLayout()); 
            pContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
            contentArea = new JTextArea();  
            contentArea.setBorder(new LineBorder(Color.BLACK, 5));        
            pContent.add(contentArea, BorderLayout.CENTER);
        } 
        return pContent;
    }

	
	private JPanel getPTextField() {
		if (pTextField ==null) {
			pTextField = new JPanel();
			pTextField.setLayout(new BorderLayout());
			pTextField.setBorder(BorderFactory.createCompoundBorder(
				     BorderFactory.createEmptyBorder(10, 10, 20, 10),
			         new LineBorder(Color.BLACK, 5)));
			pTextField.setPreferredSize(new Dimension(0,300));
			boardArea = new JTextArea();
			boardArea.setBorder(new LineBorder(Color.BLACK, 3));
			pTextField.add(boardArea,BorderLayout.CENTER);
			
			pTextField.add(getPSouth());
		      
			
		}
		return pTextField;
	}
	
	
	
	private JPanel getPSouth() {
		  if (pSouth == null) {
	          pSouth = new JPanel(null);
	          pSouth.setPreferredSize(new Dimension(800,200));
	          //pSouth.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
	         //텍스트필드 - 버튼 순으로 플로우레이아 
	         // pSouth.setBorder(new LineBorder(Color.BLACK, 5)); 
	          pSouth.add(getCommentField());
	          pSouth.add(getCommentButton());
	          pSouth.setBorder(BorderFactory.createCompoundBorder(
	        		    BorderFactory.createEmptyBorder(10, 10, 20, 10),
	        		    new LineBorder(Color.BLACK, 5)
	        		));	         
		  } //댓글 게시판 table추가
		  return pSouth;
	}

	/*
	 * private JPanel getPSouth() { if (pSouth == null) { pSouth = new JPanel();
	 * pSouth.setPreferredSize(new Dimension(800, 200));
	 * pSouth.setBorder(BorderFactory.createCompoundBorder(
	 * BorderFactory.createEmptyBorder(10, 10, 20, 10), new LineBorder(Color.BLACK,
	 * 5)));
	 * 
	 * 
	 * 
	 * boardArea = new JTextArea(); boardArea.setEditable(false);
	 * pSouth.add(boardArea); pSouth.add(getCommentField());
	 * pSouth.add(getCommentButton()); } return pSouth; }
	 */

	
	
	
	private JTextField getCommentField() {
		if(commentField == null) {
			commentField = new JTextField();
			commentField.setBorder(new LineBorder(Color.BLACK, 3));
			commentField.setPreferredSize(new Dimension(400,50));
			commentField.setBounds(175, 100, 400, 50);
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
			commentButton.setBounds(575,100,100,50);
			commentButton.setForeground(Color.WHITE);
			commentButton.setBackground(Color.BLACK);
			commentButton.setBorder(new LineBorder(Color.BLACK, 1, true)); //둥글
			
			commentButton.addActionListener(new ActionListener() {
				 public void actionPerformed(ActionEvent e) {
				 getBoardArea().append(getCommentField().getText() + "\n");
				 getCommentField().setText("");
				 }
				 });
		
		}
		return commentButton;
	}
}//비행기 이미지 

	
	/*
	 * } CommentDAO(예시)에 저장된 데이터를 불러오는 창
	 */
