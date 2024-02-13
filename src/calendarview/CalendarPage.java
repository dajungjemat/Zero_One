package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import model.BoardsDAO;
import model.BoardsDTO;
import widgetMode.WidgetMode;

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
	private Color orangeCol = new Color(250,198,96);
	Image mainTop2;
	ImageIcon userLogo = new ImageIcon(CalendarPage.class.getResource("userLogo.png"));
	ImageIcon createBtnImage = new ImageIcon(CalendarPage.class.getResource("createBtn.png"));

	public CalendarPage (String email, String nickname){//연결 매개 변수 넣기
		this.board = this;
		this.email = email;
		this.nickname = nickname;
		setTitle("타이틀 이름");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((int) screenSize.getWidth()*0.8);
		int y = (int) ((int) screenSize.getHeight()*0.8);
		setSize(x,y);
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
			topBar.setPreferredSize(new Dimension((int)(this.getWidth()*0.9), (int)this.getHeight()/15));
			topBar.add(new JLabel(""));
			topBar.add(new JLabel(""));
			topBar.add(getUserNameInTopBar());
			topBar.add(new JLabel(""));
			
			JPanel widgetPane = new JPanel();
			widgetPane.setLayout(new BorderLayout());
			JButton widgetBtn = new JButton("Widget Mode");
			widgetBtn.setBackground(beigeCol);
			widgetBtn.setBorder(new EmptyBorder(0,0,0,0));
			widgetBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					WidgetMode wm = new WidgetMode(email, board);
					wm.setVisible(true);
					board.setVisible(false);
				}
			});
			widgetPane.add(widgetBtn, BorderLayout.EAST);
			
			topBar.add(widgetPane);
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
			tabPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					CalendarPage.this.calendarTabPane.removeAll();
					CalendarPage.this.tabPane.revalidate();
					calendarTabPane.setBackground(Color.white);
					calendarTabPane.setLayout(new BorderLayout(10,20));
					calendarTabPane.add(getMonthYearBtnPane(),BorderLayout.NORTH);
					calendarTabPane.add(getCalendarPane(), BorderLayout.CENTER);
					
				}
			});
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
			monthYearBtnPane.setBackground(Color.white);
			monthYearBtnPane.setLayout(new BorderLayout());
			
			updateMonthYear();	
		}else {
			monthYearBtnPane.removeAll();
			updateMonthYear();
		}
		return monthYearBtnPane;
	}
	
	
	private void updateMonthYear() {
		
		Image mainTop = new ImageIcon(CalendarPage.class.getResource("mainTopBackImage.png")).getImage();
		mainTop2 = mainTop.getScaledInstance(this.getWidth(), (int)(this.getHeight()*0.056), Image.SCALE_SMOOTH);
		monthYearBtnPane.add(new JLabel(new ImageIcon(mainTop2)), BorderLayout.NORTH);
		int width = (int)(this.getWidth()*0.025);
		int height = (int)(this.getHeight()*0.07);
		Image rightBtnImage = new ImageIcon(CalendarPage.class.getResource("right.png")).getImage();
		Image rightImage = rightBtnImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Image leftBtnImage = new ImageIcon(CalendarPage.class.getResource("left.png")).getImage();
		Image leftImage	= leftBtnImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		preMonthBtn = new JButton(new ImageIcon(leftImage));
		preMonthBtn.setBorder(new EmptyBorder(0,0,0,0));
		preMonthBtn.setBackground(Color.white);
		preMonthBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.MONTH, -1);
				
				CalendarPage.this.monthYearBtnPane.removeAll();
				CalendarPage.this.calendarPane.removeAll();
				CalendarPage.this.remove(monthYearBtnPane);
				CalendarPage.this.remove(calendarPane);
				calendarPane=null; monthYearBtnPane=null;
				calendarTabPane.add(getMonthYearBtnPane(),BorderLayout.NORTH);
				calendarTabPane.add(getCalendarPane(), BorderLayout.CENTER);
				getMonthYearBtnPane().revalidate();
				getCalendarPane().revalidate();
				
			}
		} );
		nextMonthBtn = new JButton(new ImageIcon(rightImage));
		nextMonthBtn.setBorder(new EmptyBorder(0,0,0,0));
		nextMonthBtn.setBackground(Color.white);
		nextMonthBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.MONTH, 1);
				
				CalendarPage.this.monthYearBtnPane.removeAll();
				CalendarPage.this.calendarPane.removeAll();
				CalendarPage.this.remove(monthYearBtnPane);
				CalendarPage.this.remove(calendarPane);
				calendarPane=null; monthYearBtnPane=null;
				calendarTabPane.add(getMonthYearBtnPane(),BorderLayout.NORTH);
				calendarTabPane.add(getCalendarPane(), BorderLayout.CENTER);
				getMonthYearBtnPane().revalidate();
				getCalendarPane().revalidate();
			}
		});

		monthYearLabel = new JLabel();
		monthYearLabel.setOpaque(true);
//		monthYearLabel.setPreferredSize(new Dimension(500, 100));
		monthYearLabel.setPreferredSize(new Dimension((int)(this.getWidth()*0.25), (int)this.getHeight()/12));
		monthYearLabel.setBackground(Color.white);
		SimpleDateFormat sdf = new SimpleDateFormat("MM.YYYY");
		monthYearLabel.setText(sdf.format(calendar.getTime()));
		monthYearLabel.setFont(new Font("한컴 윤고딕 250", Font.BOLD, 50));
		monthYearLabel.setHorizontalAlignment(JLabel.CENTER);
		
		monthYearBtnPane.add(preMonthBtn, BorderLayout.WEST);
		monthYearBtnPane.add(monthYearLabel, BorderLayout.CENTER);
		monthYearBtnPane.add(nextMonthBtn, BorderLayout.EAST);	
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
	        
	        for(int i=0; i<7; i++) {
	        	JLabel weekSt = new JLabel();
	        	weekSt.setText(DateArray.weekString[i]);
	        	weekSt.setBackground(orangeCol);
	        	weekSt.setOpaque(true);
	        	weekSt.setHorizontalAlignment(JLabel.CENTER);
	        	calendarPane.add(weekSt);
	        }


	        for (int i = 1; i < firstDayOfWeek; i++) {
	            calendarPane.add(new JLabel(""));
	        }
	        try {
		        for (int day = 1; day <= daysInMonth; day++) {
		  	      
		        	ImageIcon calendarBtnImage = new ImageIcon(CalendarPage.class.getResource("calendar"+day+".png"));
		        	Image img = calendarBtnImage.getImage();
		        	
		        	int width = (int)(this.getWidth()*0.12);
		        	int height = (int)(this.getHeight()*0.1);
		        	Image img2 = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		        	
		        	
//		            JButton dayButton = new JButton(new ImageIcon(img2));
		            JButton dayButton = new JButton();
		            dayButton.setText("	 "+day);
		            dayButton.setForeground(orangeCol);
		            dayButton.setFont(new Font("한컴 고딕", Font.BOLD, 20));
		            dayButton.setHorizontalAlignment(SwingConstants.LEFT);
		            dayButton.setVerticalAlignment(SwingConstants.TOP);
		            dayButton.setBackground(beigeColOp);
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
			underBar.setPreferredSize(new Dimension((int)(this.getWidth()*0.9), (int)this.getHeight()/15));
		}
		return underBar;
	}
	
	//-----------------------------------------tap board Pane---------------------------------------------------------------
	private JPanel getBoardTabPane() {
		if(boardTabPane==null) {
			boardTabPane = new JPanel(new BorderLayout());
			boardTabPane.setBackground(Color.white);
			boardTabPane.add(getBoardLabel(),BorderLayout.NORTH);
			boardTabPane.add(getPCenter(),BorderLayout.CENTER);
			
		}
		return boardTabPane;
	}
	
	
	private JLabel getBoardLabel() {
		if(boardLabel == null) {
			boardLabel = new JLabel(new ImageIcon(mainTop2));
			
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
			pCenter.setBackground(Color.WHITE);
			
		}
			return pCenter;
		
	}
	
	private JPanel getPanel() {
		if(panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(Color.WHITE);
//			panel.setPreferredSize(new Dimension(1100,200));
			panel.setPreferredSize(new Dimension((int)(this.getWidth()*0.55),(int)(this.getHeight()/6)));
			
			Image imgTopbtn = createBtnImage.getImage();
			int btnSize = (int) (this.getWidth()*0.06);
			Image imgTop2btn = imgTopbtn.getScaledInstance(btnSize, btnSize, Image.SCALE_SMOOTH);
			
			JButton button = new JButton(new ImageIcon(imgTop2btn));
			
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new PostDialog(CalendarPage.this.board, email).setVisible(true);

				}
			});
			button.setBackground(Color.white);
			button.setBorder(new EmptyBorder(0,0,0,0));
//			button.setText("추가");
//			button.setPreferredSize(new Dimension((int)(this.getWidth()*0.05),(int)(this.getHeight()*0.01)));
//			button.setBounds(1850, 150, 100, 40);
//			button.setBounds(this.getWidth()*185/200, this.getHeight()*15/120, this.getWidth()/12, this.getHeight()/30);
			
			JLabel label = new JLabel();
			Image imgTop = new ImageIcon(getClass().getResource("picture.png")).getImage();
			int imgWidth = (int) (this.getWidth()*0.8);
			int imgHeight = (int) (this.getHeight()*0.2);
			Image imgTop2 = imgTop.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
			
			label.setIcon(new ImageIcon(imgTop2));
			label.setHorizontalAlignment(JLabel.CENTER);
//			label.setBounds(this.getWidth()*475/2000, this.getHeight()*25/1200, this.getWidth()/2, this.getHeight()*15/120);
			
			panel.add(label, BorderLayout.CENTER);
			panel.add(button, BorderLayout.EAST);
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
			tableModel.addColumn("BN");
			refreshBoard();
			jTable = new JTable(tableModel);
			jTable.getColumn("번호").setPreferredWidth(this.getWidth()/4);
			jTable.getColumn("제목").setPreferredWidth(this.getWidth()*150/200);
			jTable.getColumn("내용").setPreferredWidth(this.getWidth()*300/200);
			jTable.getColumn("날짜").setPreferredWidth(this.getWidth()*80/200);
			jTable.getColumn("글쓴이").setPreferredWidth(this.getWidth()*80/200);
			jTable.getColumn("조회수").setPreferredWidth(this.getWidth()/4);
			jTable.getColumn("BN").setMinWidth(0);
			jTable.getColumn("BN").setMaxWidth(0);
			jTable.getColumn("BN").setPreferredWidth(0);
			jTable.setFillsViewportHeight(true);
			
			jTable.setRowHeight(this.getWidth()/24);
			jTable.getTableHeader().setReorderingAllowed(false);
			jTable.setDefaultRenderer(Object.class, new EvenOddRenderer());
			jTable.setDragEnabled(false);
			
			
			JTableHeader header = jTable.getTableHeader();

			header.setBackground(new Color(243, 232, 214));
			header.setForeground(new Color(165, 165, 165));
			header.setPreferredSize(new Dimension(header.getPreferredSize().width, this.getHeight()/24));

			TableColumnModel columnModel = jTable.getColumnModel();
			for (int i = 0; i < columnModel.getColumnCount(); i++) {
				TableColumn column = columnModel.getColumn(i);
				column.setResizable(false);
			}}

			jTable.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {

					int rowIndex = jTable.getSelectedRow();
					if (rowIndex != -1) {
						int boardNo = (int) jTable.getValueAt(rowIndex,6);
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
		List<BoardsDTO> dto = BoardsDAO.getInstance().getBoards();
		for(int i=0; i<dto.size();i++) {
			Object[] rowData = {i+1, dto.get(i).getTitle(), dto.get(i).getBoardContent(), dto.get(i).getBoardDate(),
					dto.get(i).getEmail(), dto.get(i).getHitcount(), dto.get(i).getBoardNo()};
			tableModel.addRow(rowData);}
		
//	for (BoardsDTO dto : BoardsDAO.getInstance().getBoards()) {
//	Object[] rowData = { dto.getBoardNo(), dto.getTitle(), dto.getBoardContent(), dto.getBoardDate(),
//			dto.getEmail(), dto.getHitcount() };
//	tableModel.addRow(rowData);
//}
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


