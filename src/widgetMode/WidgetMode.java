package widgetMode;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import calendarview.CalendarPage;
import calendarview.TodoPage;
import model.ContentsDAO;
import model.ContentsDTO;

public class WidgetMode extends JDialog{
	
	private CalendarPage calendarPage;
	private String email = null;
	private JPanel basePane, btnPane;
	private JScrollPane scroll;
	private List<ContentsDTO> contentList;
	Calendar now = Calendar.getInstance();
	//style
	private Color beigeCol = new Color(243, 232, 214);
	private Color beigeColOp = new Color(255, 249, 239);
	private Color greyCol = new Color(165, 165, 165);
	private JPanel contentPane;
	
	private WidgetMode widgetMode;

	public WidgetMode (String email, CalendarPage calendarPage)	{
		this.widgetMode = this;
		this.calendarPage = calendarPage;
		this.email = email;
		int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.2);
		int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.5);
		setSize(x,y);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		locationCenter();
		
		getContentPane().add(getBasePane());
	}
	
	public JPanel getBasePane() {
		if(basePane==null) {
			basePane = new JPanel();
			basePane.setBackground(Color.white);
			basePane.add(getScrollPane(), BorderLayout.CENTER);
			basePane.add(getBtnPane(), BorderLayout.SOUTH);
		}
		return basePane;
	}
	
	public JScrollPane getScrollPane() {
		if(scroll==null) {
			System.out.println(email);
			
			Calendar now = Calendar.getInstance();
			now.set(Calendar.YEAR, now.get(Calendar.YEAR));
			now.set(Calendar.MONTH, now.get(Calendar.MONTH));
			now.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));

			Date nowDate = new Date(now.getTimeInMillis());
			
			contentList = ContentsDAO.getInstance().getWillDTO(nowDate, email);
			System.out.println(contentList.size());

			
			JPanel paneInScroll = new JPanel();
			paneInScroll.setBackground(Color.WHITE);
			paneInScroll.setLayout(new FlowLayout());
			if(contentList.size()>7) {
				paneInScroll.setPreferredSize(new Dimension((int)(this.getWidth()*0.7), (int)(this.getHeight()*0.3)));
			}else {
				
				paneInScroll.setPreferredSize(new Dimension((int)(this.getWidth()*0.7), (int)(this.getHeight()*0.5)));
			}
			

			for(int i=0; i<contentList.size();i++) {
				ContentsDTO dto = contentList.get(i);

				JLabel contentLabel = new JLabel(dto.getContent());
				contentLabel.setPreferredSize(new Dimension((int)(this.getWidth()*0.6), (int)(this.getHeight()*0.06)));
				JButton doneBtn = new JButton("DONE");
				doneBtn.setBackground(beigeCol);
				doneBtn.setBorder(new LineBorder(greyCol,(int)(this.getWidth()*0.005),true));
				
				JPanel contentPane = new JPanel();
				contentPane.setBackground(Color.white);
				contentPane.setLayout(new BorderLayout());
				contentPane.add(contentLabel, BorderLayout.CENTER);
				contentPane.add(doneBtn,BorderLayout.EAST);
				
				paneInScroll.add(contentPane);
				
				doneBtn.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Calendar cal = Calendar.getInstance();
						ContentsDAO.getInstance().contentFinish(dto, new Date(cal.getTimeInMillis()));
						
						WidgetMode.this.basePane.removeAll();
						WidgetMode.this.remove(basePane);
						basePane=null; scroll=null;
						getContentPane().add(getBasePane());
						WidgetMode.this.revalidate();
						WidgetMode.this.repaint();	
					}
				});
			}
			
			scroll = new JScrollPane(paneInScroll);
			scroll.setPreferredSize(new Dimension((int)(this.getWidth()*0.9), (int)(this.getHeight()*0.6)));
			scroll.setBackground(Color.WHITE);
			scroll.setBorder(new LineBorder(beigeCol, (int)(this.getWidth()*0.02) , true));
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		}
		return scroll;
	}
	
	public JPanel getBtnPane() {
		if(btnPane==null) {
			btnPane = new JPanel();
			btnPane.setPreferredSize(new Dimension((int)(this.getWidth()*0.9), (int)(this.getHeight()*0.3)));
			btnPane.setBackground(Color.white);
			
			JButton backCalBtn = new JButton("Calendar Mode");
			backCalBtn.setBackground(beigeCol);
			backCalBtn.setBorder(new EmptyBorder(0,0,0,0));
			backCalBtn.setPreferredSize(new Dimension((int)(this.getWidth()*0.3), (int)(this.getHeight()*0.08)));
			backCalBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					calendarPage.setVisible(true);
				}
			});
			btnPane.add(backCalBtn, BorderLayout.EAST);
		}
		return btnPane;
	}
	

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()-this.getWidth());
		this.setLocation(leftTopX, 0);
	}
}
