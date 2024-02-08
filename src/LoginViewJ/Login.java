package LoginViewJ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import JoinMemberJ.JoinMember;
import calendarview.CalendarPage;
import model.UserDAO;

public class Login extends JFrame implements FocusListener{
	private Login board;
	private JLabel logo;
	private JPanel background, center, bottom, email, password, btnGather;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnJoinMember, btnPasswordSearch;
	private RoundedButton btnLogin;
	
	public Login() {
		this.board = this;
		this.setTitle("login");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 550);
		this.setResizable(false);
		locationCenter();
		this.getContentPane().add(getBG());
	}
	
	public JPanel getBG() {
		if(background==null) {
			background = new JPanel();
			background.setBackground(Color.white);
			background.setLayout(new BorderLayout());
			background.add(getLogo(), BorderLayout.NORTH);
			background.add(getCenter(), BorderLayout.CENTER);
			background.add(getBottom(),BorderLayout.SOUTH);
		}
		return background;
	}
	
	//로고
	public JLabel getLogo() {
		if(logo == null) {
			logo = new JLabel();
			logo.setIcon(new ImageIcon(getClass().getResource("logo.png")));
			logo.setPreferredSize(new Dimension(250,250));
			logo.setBorder(new EmptyBorder(10,10,10,10));
			logo.setAlignmentX(JLabel.CENTER);
			logo.setHorizontalAlignment(JLabel.CENTER);
		}
		return logo;
	}
	
	public JPanel getCenter() {
		if(center==null) {
			center = new JPanel();
			center.setBackground(Color.white);
			center.setBorder(new EmptyBorder(10,10,10,10));
			center.add(getEmail());
			center.add(getPassword());
		}
		return center;
	}
	
	//이메일 입력칸
	public JPanel getEmail() {
		if(email == null) {
			email = new JPanel();
			email.setBackground(Color.white);
			email.setLayout(new BorderLayout());
			email.setBorder(new EmptyBorder(0,0,0,10));
			JLabel label = new JLabel("E-mail");
			label.setPreferredSize(new Dimension(80, 30));
			label.setBorder(new EmptyBorder(0,0,0,10));
			label.setHorizontalAlignment(JLabel.RIGHT);
			email.add(label, BorderLayout.WEST);
			txtEmail = new JTextField();
			txtEmail.setPreferredSize(new Dimension(200,30));
			txtEmail.setBorder(new LineBorder(new Color(243,232,214), 3));
			txtEmail.addFocusListener(this);
//			txtEmail.setBackground(new Color(204,204,255));
			email.add(txtEmail, BorderLayout.CENTER);
		}
		return email;
	}
	
	
	//비밀번호 입력칸
	public JPanel getPassword() {
		if(password == null) {
			password = new JPanel();
			password.setBackground(Color.white);
			password.setLayout(new BorderLayout());
			password.setBorder(new EmptyBorder(20,0,0,10));
			JLabel label = new JLabel("Password");
			label.setPreferredSize(new Dimension(80, 30));
			label.setBorder(new EmptyBorder(0,0,0,10));
			label.setHorizontalAlignment(JLabel.RIGHT);
			password.add(label, BorderLayout.WEST);
			txtPassword = new JPasswordField();
			txtPassword.setPreferredSize(new Dimension(200,30));
			txtPassword.setBorder(new LineBorder(new Color(243,232,214), 3));
			txtPassword.addFocusListener(this);
//			txtPassword.setBackground(new Color(204,204,255));
			password.add(txtPassword, BorderLayout.CENTER);
		}
		return password;
	}
	
	public JPanel getBottom() {
		if(bottom == null) {
			bottom = new JPanel();
			bottom.setBackground(Color.white);
			bottom.setPreferredSize(new Dimension(430, 135));
			bottom.add(getBtnLogin());
			bottom.add(getBtnGather());
		}
		return bottom;
	}
	
	
	//로그인 버튼
	public RoundedButton getBtnLogin() {
		if(btnLogin == null) {
			btnLogin = new RoundedButton();
			btnLogin.setPreferredSize(new Dimension(250,35));
			btnLogin.setText("로그인");
			btnLogin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(UserDAO.getInstance().getUserEmail(txtEmail.getText()).equals("")) {
						JOptionPane.showMessageDialog(null, "존재하지 않는 email입니다. 다시 입력해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
						txtEmail.requestFocus();
					}else if(!UserDAO.getInstance().getUserPassword(txtEmail.getText()).equals(txtPassword.getText())) {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다. 다시 입력해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
						txtPassword.requestFocus();
					}else {
						String emailCp = txtEmail.getText();
						String nicknameCp = UserDAO.getInstance().getUserNickName(emailCp);
						CalendarPage cp = new CalendarPage(emailCp, nicknameCp);
						dispose();
						cp.setVisible(true);
					}
				}
			});
		}
		return btnLogin;
	}
	
	public JPanel getBtnGather() {
		if(btnGather == null) {
			btnGather = new JPanel();
			btnGather.setBackground(Color.white);
			btnGather.add(getBtnJoinMember());
			btnGather.add(getBtnPasswordSearch());
		}
		return btnGather;
	}
	
	public JButton getBtnJoinMember() {
		if(btnJoinMember == null) {
			btnJoinMember = new JButton();
			btnJoinMember.setText("회원가입");
			btnJoinMember.setBorderPainted(false);
			btnJoinMember.setContentAreaFilled(false);
			btnJoinMember.setFocusPainted(false);
			btnJoinMember.setOpaque(false);
			btnJoinMember.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					JoinMember joinMember = new JoinMember();
					joinMember.setVisible(true);
				}
			});
		}
		return btnJoinMember;
	}
	
	public JButton getBtnPasswordSearch() {
		if(btnPasswordSearch == null) {
			btnPasswordSearch = new JButton();
			btnPasswordSearch.setText("비밀번호 찾기");
			btnPasswordSearch.setBorderPainted(false);
			btnPasswordSearch.setContentAreaFilled(false);
			btnPasswordSearch.setFocusPainted(false);
			btnPasswordSearch.setOpaque(false);
		}
		return btnPasswordSearch;
	}
	
	private void locationCenter() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int leftTopX = centerPoint.x - this.getWidth()/2;
		int leftTopY = centerPoint.y - this.getHeight()/2;
		this.setLocation(leftTopX, leftTopY);
	}
	
	public static void main(String[] args) {
	    SwingUtilities.invokeLater(() -> {
        	Login board = new Login();
        	board.setVisible(true);
	    });
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

//버튼 모양 클래스
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

//class RoundJTextField extends JTextField {
//    private Shape shape;
//    public RoundJTextField(int size) {
//        super(size);
//        setOpaque(false); // As suggested by @AVD in comment.
//    }
//    protected void paintComponent(Graphics g) {
//         g.setColor(getBackground());
//         g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
//         super.paintComponent(g);
//    }
//    protected void paintBorder(Graphics g) {
//         g.setColor(getForeground());
//         g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
//    }
//    public boolean contains(int x, int y) {
//         if (shape == null || !shape.getBounds().equals(getBounds())) {
//             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
//         }
//         return shape.contains(x, y);
//    }
//}
//	
	
