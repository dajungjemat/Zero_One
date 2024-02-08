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
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.BoardsDAO;
import model.BoardsDTO;

public class CalendarPage extends JFrame{
	// 공통 변수
	private JTabbedPane tabPane;
	private JPanel underBar;
	private Date date = null;
	public Calendar calendar = Calendar.getInstance();
	// 달력 변수
	private JButton preMonthBtn, nextMonthBtn;
	private JPanel topBar, monthYearBtnPane, calendarPane, calendarTabPane;
	private JLabel userNameInTopBar,monthYearLabel;
	// 게시판 변수
	private JPanel boardTabPane,pNorth,pCenter,pSouth,panel;
	private JLabel boardLabel;
	private JTable jTable;
	private RoundedButton btnInsert;
	
	private CalendarPage board; //
	// 로그인 창에서 받아오 정보
	private String email = null;
	private String nickname = null;
	
	
	//style
	private Color beigeCol = new Color(243, 232, 214);
	private Color beigeColOp = new Color(255, 249, 239);
	ImageIcon rightBtnImage = new ImageIcon(TodoPage.class.getResource("right.png"));
	ImageIcon leftBtnImage = new ImageIcon(TodoPage.class.getResource("left.png"));
	ImageIcon userLogo = new ImageIcon(TodoPage.class.getResource("userLogo.png"));
	List<ImageIcon> calendarImages;
	
	public CalendarPage (String email, String nickname){//연결 매개 변수 넣기
		this.board = this;
		this.email = email;
		this.nickname = nickname;
		setTitle("타이틀 이름");
		setSize(2000,1200);
		locationCenter();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		getContentPane().add(getTopBar(), BorderLayout.NORTH);
		getContentPane().add(getTabPane(), BorderLayout.CENTER);
		getContentPane().add(getUnderBar(), BorderLayout.SOUTH);
		getContentPane().add(new JPanel(), BorderLayout.WEST);
		getContentPane().add(new JPanel(), BorderLayout.EAST);
		
		

	}
	//---------------------------------------topBar-----------------------------------------------------------------
	private JPanel getTopBar() {
		if(topBar==null) {
			topBar = new JPanel();
			topBar.setLayout(new GridLayout(0,5));
			topBar.setPreferredSize(new Dimension(1800, 80));
			topBar.add(new JLabel(""));
			topBar.add(new JLabel(""));
			topBar.add(getUserNameInTopBar());
			topBar.add(new JLabel(""));
			topBar.add(new JLabel(""));
			//유저 이미지랑 이름 추가 
		}
		return topBar;
	}
	
	private JLabel getUserNameInTopBar() {
		if(userNameInTopBar==null) {
			userNameInTopBar = new JLabel();
			Font emailfont = new Font("굴림 보통", Font.BOLD, 15);
			userNameInTopBar.setText(email);
			userNameInTopBar.setFont(emailfont);
			userNameInTopBar.setIcon(userLogo);
			userNameInTopBar.setHorizontalAlignment(JLabel.CENTER);
		}
		return userNameInTopBar;
	}
	

	//---------------------------------------tap calendar Pane-----------------------------------------------------------------
	private JTabbedPane getTabPane() {
		if(tabPane==null) {
			tabPane = new JTabbedPane();
			tabPane.setBackground(beigeCol);
			tabPane.setTabPlacement(JTabbedPane.TOP);
			tabPane.addTab("calendar", getCalendarTabPane());
			tabPane.addTab("board", getBoardTabPane());
			
		}
		return tabPane;
	}
	
	
	
	private JPanel getCalendarTabPane() {
		try {
				if(calendarTabPane==null) {
				calendarTabPane =new JPanel();
				calendarTabPane.setBackground(Color.white);
				calendarTabPane.setLayout(new BorderLayout(10,20));
				calendarTabPane.add(getMonthYearBtnPane(),BorderLayout.NORTH);
				calendarTabPane.add(getCalendarPane(), BorderLayout.CENTER);
			}	
		}catch(Exception e) {}

		return calendarTabPane;
	}
	
	private JPanel getMonthYearBtnPane() {
		if(monthYearBtnPane==null) {
			monthYearBtnPane = new JPanel();
			monthYearBtnPane.setBackground(beigeCol);
			monthYearBtnPane.setLayout(new FlowLayout());
			updateMonthYear();	
		}else {
			monthYearBtnPane.removeAll();
			updateMonthYear();
		}
		return monthYearBtnPane;
	}
	
	
	private void updateMonthYear() {
		
		preMonthBtn = new JButton(leftBtnImage);
		preMonthBtn.setBorder(new EmptyBorder(0,0,0,0));
		preMonthBtn.setBackground(beigeCol);
		preMonthBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.MONTH, -1);
				getMonthYearBtnPane();
				getCalendarPane();
				
			}
		} );
		nextMonthBtn = new JButton(rightBtnImage);
		nextMonthBtn.setBorder(new EmptyBorder(0,0,0,0));
		nextMonthBtn.setBackground(beigeCol);
		nextMonthBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.MONTH, 1);
				getMonthYearBtnPane();
				getCalendarPane();
			}
		});

		monthYearLabel = new JLabel();
		monthYearLabel.setOpaque(true);
		monthYearLabel.setPreferredSize(new Dimension(500, 100));
		monthYearLabel.setBackground(beigeCol);
		SimpleDateFormat sdf = new SimpleDateFormat("MM.YYYY");
		monthYearLabel.setText(sdf.format(calendar.getTime()));
		monthYearLabel.setFont(new Font("한컴 윤고딕 250", Font.BOLD, 50));
		monthYearLabel.setHorizontalAlignment(JLabel.CENTER);
		
		monthYearBtnPane.add(preMonthBtn);
		monthYearBtnPane.add(monthYearLabel);
		monthYearBtnPane.add(nextMonthBtn);	
	}
	//---------------------------------------calendarPane-----------------------------------------------------------------

	private JPanel getCalendarPane() {
		if(calendarPane==null) {
			calendarPane = new JPanel();
			calendarPane.setBackground(Color.white);
			GridLayout grid = new GridLayout(0,7);
			grid.setHgap(5);
			grid.setVgap(5);
			calendarPane.setLayout(grid);
			updateCalendar();

		}else {
			calendarPane.removeAll();
			updateCalendar();
		       }
		return calendarPane;
	}
	
	private void updateCalendar() {
		 Calendar calendarCopy = (Calendar) calendar.clone();
	        calendarCopy.set(Calendar.DAY_OF_MONTH, 1);

	        int firstDayOfWeek = calendarCopy.get(Calendar.DAY_OF_WEEK);
	        int daysInMonth = calendarCopy.getActualMaximum(Calendar.DAY_OF_MONTH);

	        for (int i = 1; i < firstDayOfWeek; i++) {
	            calendarPane.add(new JLabel(""));
	        }
	        try {
		        for (int day = 1; day <= daysInMonth; day++) {
		  	      
		        	ImageIcon calendarBtnImage = new ImageIcon(CalendarPage.class.getResource("calendar"+day+".png"));
		            JButton dayButton = new JButton(calendarBtnImage);
		            dayButton.setBackground(Color.white);
		            dayButton.setBorder(new EmptyBorder(0,0,0,0));
		            int finalDay = day;
		            dayButton.addActionListener(new ActionListener() {
		                @Override
		                public void actionPerformed(ActionEvent e) {
		                	calendar.set(Calendar.DATE, finalDay);
		                	date = new Date(calendar.getTimeInMillis());
		                	System.out.println(date);
		                	System.out.println(email);
		                	TodoPage tp = new TodoPage(date ,email);
		                	tp.setVisible(true);
		                }
		            });
		            calendarPane.add(dayButton);
		        }
	        }catch(NullPointerException e) {}

	}
	
	private JPanel getUnderBar() {
		if(underBar==null) {
			underBar = new JPanel();
			underBar.setPreferredSize(new Dimension(1800, 80));
		}
		return underBar;
	}
	
	//-----------------------------------------tap board Pane---------------------------------------------------------------
	private JPanel getBoardTabPane() {
		if(boardTabPane==null) {
			boardTabPane = new JPanel(new BorderLayout());
			boardTabPane.setBackground(Color.white);
			boardTabPane.add(getPNorth(),BorderLayout.NORTH);
			boardTabPane.add(getPCenter(),BorderLayout.CENTER);
			
		}
		return boardTabPane;
	}
	
	public JPanel getPNorth() {
		if(pNorth == null) {
			pNorth = new JPanel();
			pNorth.add(getBoardLabel());
			pNorth.add(Box.createHorizontalGlue());
			pNorth.setBackground(beigeCol);
		}
		return pNorth;
	}
	
	private JLabel getBoardLabel() {
		if(boardLabel == null) {
			boardLabel = new JLabel("라벨");
			boardLabel.setHorizontalAlignment(JLabel.CENTER);
			boardLabel.setPreferredSize(new Dimension(500, 100));
			
		}
		return boardLabel;
	}
	
	
	public JPanel getPCenter() {
		if(pCenter ==null) {
			pCenter = new JPanel(new BorderLayout());
			pCenter.add(getPanel(),BorderLayout.NORTH);
			JScrollPane centerScrollPane = new JScrollPane(getBoardTable());
			centerScrollPane.setBorder(null);
			pCenter.add(centerScrollPane,BorderLayout.CENTER);
//			pCenter.setBorder(BorderFactory.createEmptyBorder(200, 200, 50, 200));
			pCenter.setBackground(Color.WHITE);
			
		}
			return pCenter;
		
	}
	
	private JPanel getPanel() {
		if(panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(Color.WHITE);
			panel.setPreferredSize(new Dimension(1100,200));
			RoundedButton button = new RoundedButton();
			button.setText("추가");
			button.setBounds(1850, 150, 100, 40);
			panel.add(button);
			JLabel label = new JLabel();
			label.setIcon(new ImageIcon(getClass().getResource("picture.png")));
			label.setBounds(475, 25, 1000, 150);
			panel.add(label);
		}
		return panel;
	}
		
	
	private JTable getBoardTable() {
		if (jTable == null) {
			jTable = new JTable(new NonEditableTableModel());
			//
			
			DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();

			tableModel.addColumn("번호");
			tableModel.addColumn("제목");
			tableModel.addColumn("내용");
			tableModel.addColumn("날짜");
			tableModel.addColumn("글쓴이");
			tableModel.addColumn("조회수");
			refreshBoard();
			jTable = new JTable(tableModel);
			jTable.getColumn("번호").setPreferredWidth(100);
			jTable.getColumn("제목").setPreferredWidth(450);
			jTable.getColumn("내용").setPreferredWidth(450);
			jTable.getColumn("날짜").setPreferredWidth(450);
			jTable.getColumn("글쓴이").setPreferredWidth(450);
			jTable.getColumn("조회수").setPreferredWidth(100);
			jTable.setFillsViewportHeight(true);
			
			
			jTable.setRowHeight(50);
			jTable.getTableHeader().setReorderingAllowed(false);
			jTable.setDefaultRenderer(Object.class, new EvenOddRenderer());
			jTable.setDragEnabled(false);
			
			
			JTableHeader header = jTable.getTableHeader();

			header.setBackground(new Color(243, 232, 214));
			header.setForeground(new Color(165, 165, 165));
			header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

			TableColumnModel columnModel = jTable.getColumnModel();
			for (int i = 0; i < columnModel.getColumnCount(); i++) {
				TableColumn column = columnModel.getColumn(i);
				column.setResizable(false);
			}}

			jTable.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {

					int rowIndex = jTable.getSelectedRow();
					if (rowIndex != -1) {
						int boardNo = (int) jTable.getValueAt(rowIndex, 0);
						System.out.println("캘린더에서 이메일"+email);
						CommentDialog commentDialog = new CommentDialog(board, boardNo, email);
						commentDialog.setVisible(true);
						refreshBoard();
					}
				}

			});
		
		return jTable;
	} //게시판 첫 화면 테이블
	
	

	public RoundedButton getBtnInsert() {
		if (btnInsert == null) {
			/* 코드 추가 */
			btnInsert = new RoundedButton();
			btnInsert.setText("추가");
//			btnInsert.setAlignmentX(JLabel.CENTER);
//			btnInsert.setVerticalAlignment(JLabel.BOTTOM);
//			btnInsert.setHorizontalAlignment(JLabel.RIGHT);
//	            //memoTopBar.add(memodate, BorderLayout.EAST);
			
			
			
			btnInsert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new PostDialog(CalendarPage.this.board, email).setVisible(true);

				}
			});

		}
		return btnInsert;
	}//postDialog를 모달창으로 띄우는 버튼 

	
	
	
	public void refreshBoard() {
		DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
		tableModel.setNumRows(0);
		for (BoardsDTO dto : BoardsDAO.getInstance().getBoards()) {
			Object[] rowData = { dto.getBoardNo(), dto.getTitle(), dto.getBoardContent(), dto.getBoardDate(),
					dto.getEmail(), dto.getHitcount() };
			tableModel.addRow(rowData);
		}
	}//새로고침
	
	

	
	//-----------------------------------------실행 메서드---------------------------------------------------------------
	
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
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
		}//테이블 열마다 색상변경, 글자 가운데 배치 
	}
	

	public class NonEditableTableModel extends DefaultTableModel {
		@Override
		public boolean isCellEditable(int row, int column) {

			return false;
		}//테이블 수정불가능
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

		public RoundedButton(javax.swing.Action action) {
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
		}//버튼 양식
	}
}


