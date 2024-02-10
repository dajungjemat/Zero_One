package calendarview;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ButtonExame extends JFrame{

	public ButtonExame (){
		setSize(250,500);
		setLayout(new GridLayout(3,0));
		
		JLabel lb = new JLabel();
		lb.setIcon(new ImageIcon(this.getClass().getResource("calendar1.png")));
		
		JPanel marginPane = new JPanel(){
            public void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon(this.getClass().getResource("calendar1.png")).getImage(), 0, 0, null);
                setOpaque(false); 
                super.paintComponent(g);
            }
    };
		
		JButton btn = new JButton();
		
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource("calendar1.png"));
		Image img = imgIcon.getImage();
//		int btnWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int btnWidth = (int) (this.getWidth()*0.125);
		int btnHeight = (int) (this.getHeight()*0.1);
//		int btnHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		
		Image imgSize = img.getScaledInstance(btnWidth, btnHeight, Image.SCALE_SMOOTH);
		
		
		btn.setIcon(new ImageIcon(imgSize));
		
		btn.setPreferredSize(new Dimension(30,30));
		
		getContentPane().add(btn);
		getContentPane().add(lb);
		getContentPane().add(marginPane);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			ButtonExame be = new ButtonExame();
			be.setVisible(true);
		});
	}

}
