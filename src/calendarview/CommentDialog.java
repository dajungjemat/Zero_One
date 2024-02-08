package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import calendarview.*;
import model.BoardsDAO;
import model.BoardsDTO;
import model.CommentsDAO;
import model.CommentsDTO;

import calendarview.CalendarPage;


public class CommentDialog extends JDialog{
	private JPanel pContent,pSouth, boardArea,pNorth;
	private JTable jTable;
    private JTextField titleField,commentField;
    private JTextArea contentArea;
    private RoundedButton commentButton,updateButton, deleteButton;;
    private int boardNo;
    private String email;
    private CalendarPage boardApp;
    
    //게시판 화면에서 게시글을 클릭했을때 보이도록 
    //,pCommentBoard
    
    public CommentDialog(CalendarPage boardApp, int boardNo,String email) {
    	this.setTitle("게시글 보기");   
    	this.boardNo = boardNo;
    	this.email = email;
    	this.boardApp = boardApp;
    	this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    	this.setResizable(false);
    	this.setModal(true);
    	this.setSize(850,700);
	    this.setLocation(
	    	boardApp.getLocationOnScreen().x + (boardApp.getWidth()-this.getWidth())/2,
	    	boardApp.getLocationOnScreen().y + (boardApp.getHeight()-this.getHeight())/2);    		

   
    	JScrollPane northScrollPane = new JScrollPane(getPNorth());
        northScrollPane.setBorder(null); 
        this.getContentPane().add(northScrollPane, BorderLayout.NORTH);
        
        JScrollPane centerScrollPane = new JScrollPane(getPContent());
        centerScrollPane.setBorder(null); 
        this.getContentPane().add(centerScrollPane, BorderLayout.CENTER);
        
        JScrollPane southScrollPane = new JScrollPane(getPSouth());
        southScrollPane.setBorder(null); 
        this.getContentPane().add(southScrollPane, BorderLayout.SOUTH);
    	
    	
    	this.setBoard(email);   	
    }	
	
    
    
    
	public JPanel getPContent() {
        if (pContent == null) {
            pContent = new JPanel();
            pContent.setBackground(Color.WHITE);
            pContent.setLayout(new BorderLayout()); 
           
           
            contentArea = new JTextArea();                      
            contentArea.setLineWrap(true);
            contentArea.setBorder(BorderFactory.createCompoundBorder(
        		    BorderFactory.createEmptyBorder(10, 10, 10, 10),
        		    new LineBorder(new Color(243, 232, 214), 5)
        		    ));
            pContent.add(contentArea, BorderLayout.CENTER);
            
        } 
        return pContent;
    }//작성된 게시글 내용
	
	public JPanel getPNorth() { 
        if (pNorth == null) {
            pNorth = new JPanel(new FlowLayout()); 
            pNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            pNorth.setBackground(Color.WHITE);
      
            //pNorth.add(getPTitle());
            
    		titleField = new JTextField();    		//titleField.setText("Title");
    		titleField.setHorizontalAlignment(JTextField.CENTER);
    		titleField.setBorder(new LineBorder(new Color(243,232,214), 3));
    		titleField.setPreferredSize(new Dimension(400,30));
    	
    		pNorth.add(titleField);           
            pNorth.add(getUpdateButton());
            pNorth.add(getDeleteButton());
           
            
        }
        return pNorth;
    }
	
	private JPanel getPSouth() {
		  if (pSouth == null) {
	          pSouth = new JPanel(null);
	          pSouth.setBackground(Color.WHITE);
	          pSouth.setPreferredSize(new Dimension(800,200));
	          
	          pSouth.setBorder(BorderFactory.createCompoundBorder(
	        		    BorderFactory.createEmptyBorder(10, 10, 10, 10),
	        		    new LineBorder(new Color(243, 232, 214), 5)
	        		    ));
	         
	          pSouth.add(getBoardArea());
	          pSouth.add(getCommentField()); 
	          pSouth.add(getCommentButton());
				 
	        			         
		  } 
		  return pSouth;
	}
	
	

	  public JPanel getBoardArea() { 
		  if(boardArea == null) { 
			  boardArea = new JPanel();
			  boardArea.setBackground(Color.WHITE);
			  boardArea.setBounds(20, 20, 800, 110);
			  JScrollPane scrollPane = new JScrollPane(getCommentTable());
			  scrollPane.getViewport().setBackground(Color.WHITE);
//			  scrollPane.setBorder(BorderFactory.createEmptyBorder());
			  scrollPane.setBorder(new EmptyBorder(0,0,0,0));
		      scrollPane.setPreferredSize(new Dimension(790, 105));
		      
		      boardArea.add(scrollPane);
	  }	
	  
	  return boardArea; }
	 
    
	
	private JTextField getCommentField() {
		if(commentField == null) {
			commentField = new JTextField();
			commentField.setPreferredSize(new Dimension(400,30));
			commentField.setBounds(175, 140, 400, 30);
			commentField.setBorder(new LineBorder(new Color(243,232,214), 2));
			
			//commentField.setHorizontalAlignment(JTextField.CENTER);
		}
		return commentField;
	}
	

	private RoundedButton getCommentButton() {
		if(commentButton == null) {
			commentButton = new RoundedButton();
			commentButton.setText("게시");
//			Font myFont1 = new Font("Serif", Font.BOLD, 12);
//			commentButton.setFont(myFont1);
			commentButton.setPreferredSize(new Dimension(100,30));
			commentButton.setBounds(600,140,100,30);
			

			commentButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CommentsDTO comment = new CommentsDTO();
					comment.setComment(commentField.getText());
					comment.setBoardNo(boardNo);
					CommentsDAO.getInstance().insertComment(comment);
					refreshComment();
					commentField.setText("");
					
				}
				
			});		
		}
		return commentButton;
	}
	
	public void setBoard(String email) {
		BoardsDTO board = BoardsDAO.getInstance().getBoardsByBoardNo(boardNo); //???
		BoardsDAO.getInstance().addHitcount(board.getHitcount(), boardNo);
		titleField.setText(board.getTitle());
		contentArea.setText(board.getBoardContent());	
		System.out.println(email);
		System.out.println(board.getEmail());
		
		
		if(email.equals(board.getEmail())) {
			updateButton.setVisible(true);
			deleteButton.setVisible(true);
		}else {
			updateButton.setVisible(false);
			deleteButton.setVisible(false);
			titleField.setEditable(false);
			contentArea.setEditable(false);
		}
	}

	
	public JTable getCommentTable() {
		if(jTable ==null) {
			jTable = new JTable(new NonEditableTableModel());
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
			tableModel.addColumn("번호");				
			tableModel.addColumn("댓글");
			tableModel.addColumn("날짜");
			
			
			
			jTable = new JTable(tableModel);
			jTable.getColumn("번호").setPreferredWidth(20);
			jTable.getColumn("댓글").setPreferredWidth(350);
			jTable.getColumn("날짜").setPreferredWidth(50);
			jTable.setDefaultRenderer(Object.class, new EvenOddRenderer());
			jTable.setCellSelectionEnabled(false);
			//jTable.setBorder(null);
			JTableHeader header = jTable.getTableHeader();
			header.setBackground(new Color(243,232,214));
			header.setForeground(new Color(165,165,165));
			
			jTable.getTableHeader().setReorderingAllowed(false);
			TableColumnModel columnModel = jTable.getColumnModel();
			for (int i = 0; i < columnModel.getColumnCount(); i++) {
			    TableColumn column = columnModel.getColumn(i);
			    column.setResizable(false); 
			jTable.setDragEnabled(false);
			}
					
			refreshComment();
		}
		return jTable;
	}
	
	public void refreshComment() {
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.setNumRows(0);	
		List<CommentsDTO> cDtoList = CommentsDAO.getInstance().getComments(boardNo);
		for(int i=0; i<cDtoList.size();i++) {
			Object[] rowData = {i+1, cDtoList.get(i).getComment(), cDtoList.get(i).getCommentDate()};
			tableModel.addRow(rowData);}
		}
//		for(CommentsDTO cto : CommentsDAO.getInstance().getComments(boardNo)) {
//			Object[] rowData = {cto.getCommentNo(),cto.getComment(), cto.getCommentDate(),cto.getBoardNo() };
//			tableModel.addRow(rowData);
//			
//		}		
//	
	
	private RoundedButton getUpdateButton() { 
        if (updateButton == null) {
            updateButton = new RoundedButton("수정");         
          
          
          
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

    private RoundedButton getDeleteButton() { 
        if (deleteButton == null) {
            deleteButton = new RoundedButton("삭제"); 
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {               	
                	CommentsDAO.getInstance().deleteComment(boardNo);         
                	BoardsDAO.getInstance().deleteBoards(boardNo);
                	boardApp.refreshBoard();
                	dispose();
                }
            });
        }
        return deleteButton;
    }
    
    public class EvenOddRenderer extends DefaultTableCellRenderer {
		private static final Color EVEN_COLOR = new Color(255, 249, 239);
		private static final Color ODD_COLOR = Color.WHITE;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component component = super.getTableCellRendererComponent(jTable, value, isSelected, hasFocus, row, column);
			if (!isSelected) {
				Color bgColor = (row % 2 == 0) ? EVEN_COLOR : ODD_COLOR;
				component.setBackground(bgColor);
				setHorizontalAlignment(SwingConstants.CENTER);
			}
			return component;
		}
	}
	

	public class NonEditableTableModel extends DefaultTableModel {
		@Override
		public boolean isCellEditable(int row, int column) {

			return false;
		}
	}
    class RoundedButton extends JButton{
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
                Color c=new Color(243,232,214); //배경색 결정
                Color o=new Color(165,165,165); //글자색 결정
                int width = getWidth(); 
                int height = getHeight(); 
                Graphics2D graphics = (Graphics2D) g; 
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
                if (getModel().isArmed()) { graphics.setColor(c.darker()); } 
                else if (getModel().isRollover()) { graphics.setColor(c.brighter()); } 
                else { graphics.setColor(c); } 
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
