package calendarview;


import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
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

import calendarview.CalendarPage;

public class PostDialog extends JDialog {
	private JPanel pContent, pTitle,pSouth;
    private JTextField titleField;
    private JTextArea contentArea;
    private RoundedButton saveButton,updateButton,deleteButton;
    private String email;
    private int boardNo;
    private CalendarPage boardApp;

    public PostDialog(CalendarPage boardApp, String email) {
    	this.email = email;
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
    	
    	JScrollPane centerScrollPane = new JScrollPane(getPContent());
    	centerScrollPane.setBorder(null);
    	this.getContentPane().add(centerScrollPane,BorderLayout.CENTER);
    	this.getContentPane().add(getPSouth(),BorderLayout.SOUTH);
    }
       

	public JPanel getPTitle() {
    	if(pTitle == null) {
    		pTitle = new JPanel();
    		pTitle.setLayout(new BorderLayout());
    		pTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
    		pTitle.setPreferredSize(new Dimension(0,70));
    		pTitle.setBackground(Color.WHITE);
    		//setPreferredSize(new Dimension) null 아니라도 사용가능
    	
    		titleField = new JTextField();
    		titleField.setText("Title");
    		titleField.setHorizontalAlignment(JTextField.CENTER);
    		
    		titleField.setBorder(new LineBorder(new Color(243, 232, 214), 3));
            pTitle.add(titleField);
            
    	}
    	return pTitle;
    }//제목 
    
	  public JPanel getPContent() {
	        if (pContent == null) {
	            pContent = new JPanel();
	            pContent.setLayout(new BorderLayout()); 
	            pContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
	            pContent.setBackground(Color.WHITE);
	            JScrollPane js = new JScrollPane(); 
	            pContent.add(js);
	            contentArea = new JTextArea();  
	            contentArea.setBorder(new LineBorder(new Color(243, 232, 214), 5));
	            contentArea.setLineWrap(true);
	            pContent.add(contentArea, BorderLayout.CENTER);
	        } //JScrollpane
	        return pContent;
	    }//게시글 내용 작성란
     
    private JPanel getPSouth() {
        if (pSouth == null) {
            pSouth = new JPanel(null);  
            pSouth.setPreferredSize(new Dimension(800,100));
            getSaveButton().setBounds(310, 25, 150, 50);
            pSouth.setBackground(Color.WHITE);
            pSouth.add(getSaveButton());

        }
        return pSouth;
    }// 저장버튼 있는 패널 

    private RoundedButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new RoundedButton();
            saveButton.setText("저장");
           
          
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
    class RoundedButton extends JButton {
		public RoundedButton() {
			super();
			decorate();
		}

		public RoundedButton(String text) {
			super(text);
			decorate();
		}

		public RoundedButton(Action action) {
			super(action);
			decorate();
		}

		public RoundedButton(Icon icon) {
			super(icon);
			decorate();
		}

		public RoundedButton(String text, Icon icon) {
			super(text, icon);
			decorate();
		}

		protected void decorate() {
			setBorderPainted(false);
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Color c = new Color(243, 232, 214); // 배경색 결정
			Color o = new Color(165, 165, 165); // 글자색 결정
			int width = getWidth();
			int height = getHeight();
			Graphics2D graphics = (Graphics2D) g;
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (getModel().isArmed()) {
				graphics.setColor(c.darker());
			} else if (getModel().isRollover()) {
				graphics.setColor(c.brighter());
			} else {
				graphics.setColor(c);
			}
			graphics.fillRoundRect(0, 0, width, height, 10, 10);
			FontMetrics fontMetrics = graphics.getFontMetrics();
			Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
			int textX = (width - stringBounds.width) / 2;
			int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
			graphics.setColor(o);
			graphics.setFont(getFont());
			graphics.drawString(getText(), textX, textY);
			graphics.dispose();
			super.paintComponent(g);
		}
	}
    
} 