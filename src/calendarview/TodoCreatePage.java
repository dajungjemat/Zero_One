package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.ContentsDAO;
import model.ContentsDTO;

public class TodoCreatePage extends JFrame{
	private JPanel contentInputPane, setdatePane, btnPane, startDatePane, endDatePane;
	private JTextField contentInputBox;
	private Date startDate;
	Calendar calStart = Calendar.getInstance();
	Calendar calEnd = Calendar.getInstance();
	private Date endDate;
	private String email = null;
	private TodoPage todoPage = null;
	private Date date=null;
	//style
	private Color beigeCol = new Color(243, 232, 214);
	private Color beigeColOp = new Color(255, 249, 239);
	private Color greyCol = new Color(165, 165, 165);
	ImageIcon saveBtnImage = new ImageIcon(TodoPage.class.getResource("saveBtn.png"));
	ImageIcon topBackImage = new ImageIcon(TodoPage.class.getResource("topBack.png"));

	
	public TodoCreatePage(TodoPage todoPage ,String email, Date date){
		this.date = date;
		this.todoPage = todoPage;
		this.email = email;
		this.setBackground(Color.white);
		setTitle("일정 생성");
		int x = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.4);
		int y = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.3);
		setSize(x,y);
		setResizable(false);
		locationCenter();
		setDefaultCloseOperation(TEXT_CURSOR);

		setLayout(new GridLayout(4,0));
		
		Image imgTop = topBackImage.getImage();
	    int imgWidth = (int) (this.getWidth());
	    int imgHeight = (int) (this.getHeight()*0.22);
	    Image imgTop2 = imgTop.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
	    
	    JLabel marginLabel = new JLabel();
	    marginLabel.setIcon(new ImageIcon(imgTop2));
	    
	    getContentPane().add(marginLabel);
		getContentPane().add(getContentInputPane());
		getContentPane().add(getSetdatePane());
		getContentPane().add(getBtnPane());
	}
	
	private JPanel getContentInputPane() {
		if(contentInputPane==null) {
			contentInputPane = new JPanel();
			contentInputPane.setBackground(Color.white);
			contentInputBox = new JTextField();
			contentInputBox.setText("할 일을 입력하세요");
			contentInputBox.setBorder(new LineBorder(beigeCol, 5 ,true));
	
			contentInputBox.setPreferredSize(new Dimension((int)(this.getWidth()*0.9),(int)(this.getHeight()*0.2)));
			contentInputPane.add(contentInputBox);
			
		}
		return contentInputPane;
	}
	
	private JPanel getSetdatePane() {
		try {
			if(setdatePane==null) {
				setdatePane = new JPanel();
				setdatePane.setBackground(Color.white);
				setdatePane.setLayout(new GridLayout(0,2));
				calStart.setTime(date);
				calEnd.setTime(date);				
				setdatePane.add(getStartDatePane());
				setdatePane.add(getEndDatePane());


		
			}
		}catch(Exception e) {}

		return setdatePane;
	}
	
	private JPanel	getStartDatePane() {
		if(startDatePane==null) {
			startDatePane = new JPanel();
			startDatePane.setBackground(Color.white);
//			startDatePane.setLayout(new FlowLayout());
			JComboBox yearCombo = new JComboBox(DateArray.getYear());
			yearCombo.setBackground(Color.white);
			yearCombo.setSelectedItem(calStart.get(Calendar.YEAR)+"");
			JComboBox monthCombo = new JComboBox(DateArray.getMonth());
			monthCombo.setBackground(Color.white);
			monthCombo.setSelectedItem((calStart.get(Calendar.MONTH)+1)+"");
			
			int yearInt = Integer.parseInt(yearCombo.getSelectedItem().toString());
			int monthInt = Integer.parseInt(monthCombo.getSelectedItem().toString());
			
			String[] getDay = new String[31];
			for(int i=0; i<calStart.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
				getDay[i] = (i+1)+"" ;
			}
			
			JComboBox dayCombo = new JComboBox(getDay);
			dayCombo.setBackground(Color.white);
			dayCombo.setSelectedItem(calStart.get(Calendar.DAY_OF_MONTH)+"");;
			int dayInt = Integer.parseInt(dayCombo.getSelectedItem().toString());
			
			startDatePane.add(new JLabel("START-DATE"));
			startDatePane.add(yearCombo);
			startDatePane.add(monthCombo);
			startDatePane.add(dayCombo);
			

			startDate = new Date(calStart.getTimeInMillis());			
			yearCombo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int yearIntC = Integer.parseInt(yearCombo.getSelectedItem().toString());
					calStart.set(Calendar.YEAR, yearIntC);
					
					refreshStartDatePane();

				}
			});
			
			
			monthCombo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int monthIntC = Integer.parseInt(monthCombo.getSelectedItem().toString());
					calStart.set(Calendar.MONTH, monthIntC-1);
					
					refreshStartDatePane();
				}
			});
			
			dayCombo.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int dayIntC = Integer.parseInt(dayCombo.getSelectedItem().toString());
					calStart.set(Calendar.DAY_OF_MONTH, dayIntC);
					
					refreshStartDatePane();
				}
			});
			
		}
		return startDatePane;
	}
	//-----------------------------------------------------------start--------------------------
	
	
	private JPanel	getEndDatePane() {
		if(endDatePane==null) {
			endDatePane = new JPanel();
			endDatePane.setBackground(Color.white);
			JComboBox yearComboE = new JComboBox(DateArray.getYear());
			yearComboE.setBackground(Color.white);
			yearComboE.setSelectedItem(calEnd.get(Calendar.YEAR)+"");
			JComboBox monthComboE = new JComboBox(DateArray.getMonth());
			monthComboE.setBackground(Color.white);
			monthComboE.setSelectedItem((calEnd.get(Calendar.MONTH)+1)+"");
			
			int yearInt = Integer.parseInt(yearComboE.getSelectedItem().toString());
			int monthInt = Integer.parseInt(monthComboE.getSelectedItem().toString());
			
			String[] getDay = new String[31];
			for(int i=0; i<calEnd.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
				getDay[i] = (i+1)+"" ;
			}
			
			JComboBox dayComboE = new JComboBox(getDay);
			dayComboE.setBackground(Color.white);
			dayComboE.setSelectedItem(calEnd.get(Calendar.DAY_OF_MONTH)+"");;
			int dayInt = Integer.parseInt(dayComboE.getSelectedItem().toString());
			
			endDatePane.add(new JLabel("END-DATE"));
			endDatePane.add(yearComboE);
			endDatePane.add(monthComboE);
			endDatePane.add(dayComboE);
			

			endDate = new Date(calEnd.getTimeInMillis());
			
			yearComboE.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int yearIntC = Integer.parseInt(yearComboE.getSelectedItem().toString());
					calEnd.set(Calendar.YEAR, yearIntC);
					
					refreshEndDatePane();

				}
			});
			
			
			monthComboE.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int monthIntC = Integer.parseInt(monthComboE.getSelectedItem().toString());
					calEnd.set(Calendar.MONTH, monthIntC-1);
					
					refreshEndDatePane();
				}
			});
			
			dayComboE.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int dayIntC = Integer.parseInt(dayComboE.getSelectedItem().toString());
					calEnd.set(Calendar.DAY_OF_MONTH, dayIntC);
					
					refreshEndDatePane();
					
				}
			});
			
		}
		return endDatePane;
	}
	
	
	private void refreshStartDatePane() {
		
		TodoCreatePage.this.startDatePane.removeAll();
		TodoCreatePage.this.setdatePane.remove(startDatePane);
		startDatePane=null;
		getSetdatePane().add(getStartDatePane(),0);
		TodoCreatePage.this.revalidate();
		
	}
	
	private void refreshEndDatePane() {
		
		TodoCreatePage.this.endDatePane.removeAll();
		TodoCreatePage.this.setdatePane.remove(endDatePane);
		endDatePane=null;
		getSetdatePane().add(getEndDatePane(),1);
		TodoCreatePage.this.revalidate();
		
	}
	//-----------------------------------------------------------------------
	
	private JPanel getBtnPane() {
		if(btnPane==null) {
			btnPane = new JPanel();
			btnPane.setPreferredSize(new Dimension((int)(this.getWidth()*0.9),(int)(this.getHeight()*0.15)));
			btnPane.setBackground(Color.white);
			
			Image imgTop = saveBtnImage.getImage();
			int btnSize = (int) (this.getHeight()*0.2);
			Image imgTop2 = imgTop.getScaledInstance(btnSize, btnSize, Image.SCALE_SMOOTH);
			
			JButton contentInsertButton = new JButton(new ImageIcon(imgTop2));
			contentInsertButton.setBorder(new EmptyBorder(0,0,0,0));
			contentInsertButton.setBackground(Color.white);
			
			contentInsertButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					//날짜 설정 에러
					if(startDate.getDate() > endDate.getDate()) {
						JOptionPane.showMessageDialog(
								TodoCreatePage.this,
								"마감기한이 시작일 이전입니다."
								+ "날짜를 수정해 주세요",
								"Date Setting error",
								JOptionPane.INFORMATION_MESSAGE
								);
					}else {
						//내용 없을 때 에러
						if(contentInputBox.getText().toString().equals("")||
								contentInputBox.getText().toString().equals("할 일을 입력하세요")) {
							JOptionPane.showMessageDialog(
									TodoCreatePage.this,
									"할 일을 입력하세요",
									"Empty contents error",
									JOptionPane.INFORMATION_MESSAGE
									);
						}else {
							ContentsDTO dto = new ContentsDTO();
							dto.setContent(contentInputBox.getText());
							dto.setStartDate(startDate);
							dto.setEndDate(endDate);
							dto.setEmail(email);
							
							ContentsDAO.getInstance().contentsDtoInsert(dto);
							todoPage.refreshContentPane();
							setVisible(false);
						}						
					}
					
				}
			});
			
			btnPane.add(contentInsertButton);
		}
		return btnPane;
	}
	

	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX-170, leftTopY-250);
	}	
	
}
