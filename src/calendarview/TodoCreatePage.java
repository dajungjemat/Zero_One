package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

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
	private TodoPage todoPage;
	
	public TodoCreatePage(TodoPage todoPage ,String email){
		this.todoPage = todoPage;
		this.email = email;
		setTitle("일정 생성");
		setSize(700,300);
		locationCenter();
		setLayout(new GridLayout(3,0));
		getContentPane().add(getContentInputPane());
		getContentPane().add(getSetdatePane());
		getContentPane().add(getBtnPane());
		
	}
	
	private JPanel getContentInputPane() {
		if(contentInputPane==null) {
			contentInputPane = new JPanel();
			contentInputPane.setBackground(Color.white);
			
			contentInputBox = new JTextField("") {
				@Override
				public void setBorder(Border border) {
					
				}
			};
			contentInputBox.setPreferredSize(new Dimension(600,80));
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
			startDatePane.setPreferredSize(new Dimension(300,80));
			JComboBox yearCombo = new JComboBox(DateArray.getYear());
			yearCombo.setSelectedItem(calStart.get(Calendar.YEAR)+"");
			JComboBox monthCombo = new JComboBox(DateArray.getMonth());
			monthCombo.setSelectedItem((calStart.get(Calendar.MONTH)+1)+"");
			
			int yearInt = Integer.parseInt(yearCombo.getSelectedItem().toString());
			int monthInt = Integer.parseInt(monthCombo.getSelectedItem().toString());
			
			String[] getDay = new String[31];
			for(int i=0; i<calStart.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
				getDay[i] = (i+1)+"" ;
			}
			
			JComboBox dayCombo = new JComboBox(getDay);
			dayCombo.setSelectedItem(calStart.get(Calendar.DAY_OF_MONTH)+"");;
			int dayInt = Integer.parseInt(dayCombo.getSelectedItem().toString());
			
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
			endDatePane.setPreferredSize(new Dimension(300,80));
			JComboBox yearComboE = new JComboBox(DateArray.getYear());
			yearComboE.setSelectedItem(calEnd.get(Calendar.YEAR)+"");
			JComboBox monthComboE = new JComboBox(DateArray.getMonth());
			monthComboE.setSelectedItem((calEnd.get(Calendar.MONTH)+1)+"");
			
			int yearInt = Integer.parseInt(yearComboE.getSelectedItem().toString());
			int monthInt = Integer.parseInt(monthComboE.getSelectedItem().toString());
			
			String[] getDay = new String[31];
			for(int i=0; i<calEnd.getActualMaximum(Calendar.DAY_OF_MONTH);i++){
				getDay[i] = (i+1)+"" ;
			}
			
			JComboBox dayComboE = new JComboBox(getDay);
			dayComboE.setSelectedItem(calEnd.get(Calendar.DAY_OF_MONTH)+"");;
			int dayInt = Integer.parseInt(dayComboE.getSelectedItem().toString());
			
			endDatePane.add(yearComboE);
			endDatePane.add(monthComboE);
			endDatePane.add(dayComboE);
			

			endDate = new Date(calEnd.getTimeInMillis());
			System.out.println(endDate);
			
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
			btnPane.setPreferredSize(new Dimension(600,80));
			btnPane.setBackground(Color.white);
			JButton contentInsertButton = new JButton("저장");
			
			contentInsertButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ContentsDTO dto = new ContentsDTO();
					dto.setContent(contentInputBox.getText());
					dto.setStartDate(startDate);
					dto.setEndDate(endDate);
					dto.setEmail(email);
					
					ContentsDAO.getInstance().contentsDtoInsert(dto);
					todoPage.refreshContentPane();
					setVisible(false);
					
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
