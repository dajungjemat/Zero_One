package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CalendarPage extends JFrame{
	// 공통 변수
	private JTabbedPane tabPane;
	private JPanel underBar;
	// 달력 변수
	private JButton preMonthBtn, nextMonthBtn;
	private JPanel topBar, monthYearBtnPane, calendarPane, calendarTabPane;
	private JLabel userNameInTopBar,monthYearLabel;
	// 게시판 변수
	private JPanel boardTabPane;
	private JLabel boardLabel;
	private JTable boardTable;
	// 로그인 창에서 받아오 정보
	private String email = "dumi1";
	private Date date = null;
	public Calendar calendar = Calendar.getInstance();
	private Color beigeCol = new Color(243, 232, 214);
	private Color beigeColOp = new Color(255, 249, 239);
	
	public CalendarPage (){
		setTitle("타이틀 이름");
		setSize(2000,1200);
		locationCenter();
		
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
			topBar.setPreferredSize(new Dimension(1800, 80));
			topBar.add(getUserNameInTopBar());
			//유저 이미지랑 이름 추가 
		}
		return topBar;
	}
	
	private JLabel getUserNameInTopBar() {
		if(userNameInTopBar==null) {
			userNameInTopBar = new JLabel(email);
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
		
		preMonthBtn = new JButton("<");
		preMonthBtn.setBackground(beigeCol);
		preMonthBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				calendar.add(Calendar.MONTH, -1);
				getMonthYearBtnPane();
				getCalendarPane();
				
			}
		} );
		nextMonthBtn = new JButton(">");
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
		monthYearLabel.setFont(new Font("한컴 윤고딕 250", Font.BOLD, 30));
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

	        for (int day = 1; day <= daysInMonth; day++) {
	            JButton dayButton = new JButton(String.valueOf(day));
	            dayButton.setBackground(beigeColOp);
	            int finalDay = day;
	            dayButton.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {
	                	calendar.set(Calendar.DATE, finalDay);
	                	System.out.println(calendar.get(Calendar.YEAR));
	                	System.out.println(calendar.get(Calendar.MONTH)+1);
	                	System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
	                	date = new Date(calendar.getTimeInMillis());
	                	System.out.println(date);
	                	TodoPage tp = new TodoPage(date ,email);
	                	tp.setVisible(true);
	                }
	            });
	            calendarPane.add(dayButton);
	        }
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
			boardTabPane = new JPanel();
			boardTabPane.setBackground(Color.white);
			boardTabPane.add(getBoardLabel(),BorderLayout.NORTH);
			boardTabPane.add(getBoardTable(),BorderLayout.CENTER);
			
		}
		return boardTabPane;
	}
	
	private JLabel getBoardLabel() {
		if(boardLabel==null) {
			boardLabel = new JLabel("자유 게시판");
			boardLabel.setPreferredSize(new Dimension(1800, 100));
			boardLabel.setFont(new Font("한컴 윤고딕 250", Font.BOLD, 30));
		}
		return boardLabel;
	}
	
	private JTable getBoardTable() {
		if(boardTable==null) {
			boardTable = new JTable();
			final DefaultTableModel tableModel = (DefaultTableModel) boardTable.getModel();
			tableModel.addColumn("번호");
			tableModel.addColumn("제목");
			tableModel.addColumn("글쓴이");
			tableModel.addColumn("날짜");
		}
		return boardTable;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			CalendarPage cp = new CalendarPage();
			cp.setVisible(true);
			
		});
	}
	
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}	


}
