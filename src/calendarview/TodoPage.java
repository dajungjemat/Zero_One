package calendarview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class TodoPage extends JFrame{
	private JTabbedPane tabPane;
	//tap Panel
	private JPanel todoPane, memoPane, diaryPane;
	//in todoPane
	private JPanel willPane, didPane;
	
	
	public TodoPage() {
		setSize(800,1000);
		locationCenter();
		getContentPane().add(getTabPane());
		
	}
	
	public JTabbedPane getTabPane() {
		if(tabPane==null) {
			tabPane = new JTabbedPane();
			tabPane.addTab("To_Do List", getTodoPane());
			tabPane.addTab("Memo", getMemoPane());
			tabPane.addTab("Diary", getDiaryPane());
		}
		return tabPane;
	}
	
	public JPanel getTodoPane() {
		if(todoPane==null) {
			todoPane = new JPanel();
			JPanel timerButtonPane = new JPanel();
			timerButtonPane.setPreferredSize(new Dimension(700, 100));
			JPanel updownButtonPane = new JPanel();
			updownButtonPane.setPreferredSize(new Dimension(700, 100));
			updownButtonPane.add(new JButton("내리기"));
			updownButtonPane.add(new JButton("올리기"));
			todoPane.add(timerButtonPane);
			todoPane.add(new JScrollPane(getWillPane()));
			todoPane.add(updownButtonPane);
			todoPane.add(new JScrollPane(getDidPane()));
		}
		return todoPane;
	}
	
	public JPanel getWillPane() {
		if(willPane==null) {
			willPane = new JPanel();
			willPane.setBackground(Color.red);
			willPane.setPreferredSize(new Dimension(700, 300));
			//DAO객체에서 list<contentsDTO>를 가져온다
			//반복문 list size 만큼
			//DTO[i]를 매개변수로 받는다 
			willPane.add(getWillContent());
		}
		return willPane;
	}
	
	public JPanel getWillContent() {
		JPanel contentPane = new JPanel();
		contentPane.add(new JCheckBox());
		contentPane.add(new JLabel());
		contentPane.add(new JLabel());
		
		
		
		return contentPane;
	}
	
	public JPanel getDidPane() {
		if(didPane==null) {
			didPane = new JPanel();
			didPane.setBackground(Color.blue);
			didPane.setPreferredSize(new Dimension(700, 300));
		}
		return didPane;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public JPanel getMemoPane() {
		if(memoPane==null) {
			
		}
		return memoPane;
	}
	
	public JPanel getDiaryPane() {
		if(diaryPane==null) {
			
		}
		return diaryPane;
	}
	
	
	
	
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX+550, leftTopY);
	}	
}
