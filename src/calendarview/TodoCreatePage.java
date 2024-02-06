package calendarview;

import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TodoCreatePage extends JFrame{
	private JPanel contentInputPane, setdatePane, btnPane;
	
	public TodoCreatePage(){
		setTitle("일정 생성");
		setSize(700,400);
		locationCenter();
		setLayout(new GridLayout(3,0));
		
	}
	
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX-170, leftTopY-250);
	}	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			TodoCreatePage tcp = new TodoCreatePage();
			tcp.setVisible(true);
		});
	}
}
