package joinMemberJ;

import java.awt.BorderLayout;
import java.awt.BorderLayout;  
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.regex.Pattern;

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

import loginViewJ.Login;
import model.UserDAO;
import model.UserDTO;

public class JoinMember extends JFrame implements FocusListener{
	private JoinMember board;
	private JLabel logo;
	private JPanel background, center, bottom, nickname, nicknameLine, email,
	emailLine, emailCheck, password, passwordCheck;
	private JTextField txtNickname ,txtEmail, txtEmailCheck;
	private JPasswordField txtPassword, txtPasswordCheck;
	private RoundedButton btnDoubleCheck, btnNumSend, btnNewMember;
	private JButton btnLogin;
	StringBuilder stringBuilder = new StringBuilder();
	boolean emailBoolean, passwordBoolean;
	
	public JoinMember() {
		this.board = this;
		this.setTitle("JoinMember");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 600);
		this.setResizable(false);
		this.getContentPane().add(getBG());
		locationCenter();
		
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
			logo.setPreferredSize(new Dimension(120,120));
			logo.setIcon(new ImageIcon(getClass().getResource("logo.png")));
			logo.setBorder(new EmptyBorder(20,10,10,10));
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
			center.add(getNickname());
			center.add(getEmail());
			center.add(getEmailCheck());
			center.add(getPassword());
			center.add(getPasswordCheck());
		}
		return center;
	}
	
	//닉네임 입력칸
	public JPanel getNickname() {
		if(nickname==null) {
			nickname = new JPanel();
			nickname.setBackground(Color.white);
			nickname.setLayout(new BorderLayout());
			nickname.setBorder(new EmptyBorder(10,0,0,10));
			JLabel label = new JLabel("닉네임");
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
			label.setPreferredSize(new Dimension(90, 30));
			label.setHorizontalAlignment(JLabel.RIGHT);
			nickname.add(label, BorderLayout.WEST);
			nickname.add(getNicknameLine(), BorderLayout.CENTER);
			
		}
		return nickname;
	}
	
	public JPanel getNicknameLine() {
		if(nicknameLine==null) {
			nicknameLine = new JPanel();
			nicknameLine.setBackground(Color.white);
			txtNickname = new JTextField();
			txtNickname.setPreferredSize(new Dimension(180,30));
			txtNickname.setBorder(new LineBorder(new Color(243,232,214), 3));
			txtNickname.addFocusListener(this);
//			txtNickname.setBackground(new Color(204,204,255));
			nicknameLine.add(txtNickname, BorderLayout.CENTER);
			btnDoubleCheck = new RoundedButton();
			btnDoubleCheck.setText("중복확인");
			btnDoubleCheck.setPreferredSize(new Dimension(70, 30));
			btnDoubleCheck.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
			btnDoubleCheck.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String str;
					str = txtNickname.getText();
					if(str.equals("")) {
						JOptionPane.showMessageDialog(null, "닉네임을 입력해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
					}else if(UserDAO.getInstance().getUserNickName(str).equals("")) {
						JOptionPane.showMessageDialog(null, "사용가능한 닉네임입니다.","확인",JOptionPane.PLAIN_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(null, "중복된 아이디입니다. 다시 입력해주세요","확인",JOptionPane.PLAIN_MESSAGE);
					}
					
				}
			});
			nicknameLine.add(btnDoubleCheck, BorderLayout.EAST);
		}
		return nicknameLine;
	}
	
	//이메일 입력칸
	public JPanel getEmail() {
		if(email == null) {
			email = new JPanel();
			email.setBackground(Color.white);
			email.setLayout(new BorderLayout());
			email.setBorder(new EmptyBorder(10,0,0,10));
			JLabel label = new JLabel("이메일");
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
			label.setPreferredSize(new Dimension(90, 30));
			label.setHorizontalAlignment(JLabel.RIGHT);
			email.add(label, BorderLayout.WEST);
			email.add(getEmailLine(), BorderLayout.CENTER);
		}
		return email;
	}
	
	public JPanel getEmailLine() {
		if(emailLine==null) {
			emailLine = new JPanel();
			emailLine.setBackground(Color.white);
			txtEmail = new JTextField();
			txtEmail.setPreferredSize(new Dimension(180,30));
			txtEmail.setBorder(new LineBorder(new Color(243,232,214), 3));
			txtEmail.addFocusListener(this);
//			txtEmail.setBackground(new Color(204,204,255));
			emailLine.add(txtEmail, BorderLayout.CENTER);
			btnNumSend = new RoundedButton();
			btnNumSend.setText("인증번호발송");
			btnNumSend.setPreferredSize(new Dimension(70, 30));		
			btnNumSend.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
			btnNumSend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					stringBuilder.delete(0, stringBuilder.length());
					for(int i =0; i<6; i++) {
						String str = "";
						int random = (int)(Math.random() * 36 +1);
						switch(random) {
						case 1: str="0"; break;
						case 2: str="1"; break;
						case 3: str="2"; break;
						case 4: str="3"; break;
						case 5: str="4"; break;
						case 6:	str="5"; break;
						case 7:	str="6"; break;
						case 8:	str="7"; break;
						case 9:	str="8"; break;
						case 10: str="9"; break;
						case 11: str="a"; break;
						case 12: str="b"; break;
						case 13: str="c"; break;
						case 14: str="d"; break;
						case 15: str="e"; break;
						case 16: str="f"; break;
						case 17: str="g"; break;
						case 18: str="h"; break;
						case 19: str="i"; break;
						case 20: str="j"; break;
						case 21: str="k"; break;
						case 22: str="l"; break;
						case 23: str="m"; break;
						case 24: str="n"; break;
						case 25: str="o"; break;
						case 26: str="p"; break;
						case 27: str="q"; break;
						case 28: str="r"; break;
						case 29: str="s"; break;
						case 30: str="t"; break;
						case 31: str="u"; break;
						case 32: str="v"; break;
						case 33: str="w"; break;
						case 34: str="x"; break;
						case 35: str="y"; break;
						case 36: str="z"; break;
						}
						stringBuilder.append(str);				
					}
					JOptionPane.showMessageDialog(null, stringBuilder,"확인",JOptionPane.PLAIN_MESSAGE);
				}
			});
			emailLine.add(btnNumSend, BorderLayout.EAST);
		}
		return emailLine;
	}
	
	//인증번호 확인칸
	public JPanel getEmailCheck() {
		if(emailCheck == null) {
			emailCheck = new JPanel();
			emailCheck.setBackground(Color.white);
			emailCheck.setLayout(new BorderLayout());
			emailCheck.setBorder(new EmptyBorder(13,0,0,83));
			JLabel label = new JLabel("인증번호");
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
			label.setPreferredSize(new Dimension(90, 30));
			label.setHorizontalAlignment(JLabel.RIGHT);
			label.setBorder(new EmptyBorder(0,0,0,5));
			emailCheck.add(label, BorderLayout.WEST);
			txtEmailCheck = new JTextField();
			txtEmailCheck.setPreferredSize(new Dimension(180,30));
			txtEmailCheck.setBorder(new LineBorder(new Color(243,232,214), 3));
			txtEmailCheck.addFocusListener(this);
//			txtEmail.setBackground(new Color(204,204,255));
			emailCheck.add(txtEmailCheck, BorderLayout.CENTER);
		}
		return emailCheck;
	}
	
	
	//비밀번호 입력칸
	public JPanel getPassword() {
		if(password == null) {
			password = new JPanel();
			password.setBackground(Color.white);
			password.setLayout(new BorderLayout());
			password.setBorder(new EmptyBorder(20,0,0,83));
			JLabel label = new JLabel("비밀번호");
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
			label.setPreferredSize(new Dimension(90, 30));
			label.setHorizontalAlignment(JLabel.RIGHT);
			label.setBorder(new EmptyBorder(0,0,0,5));
			password.add(label, BorderLayout.WEST);
			txtPassword = new JPasswordField();
			txtPassword.setPreferredSize(new Dimension(180,30));
			txtPassword.setBorder(new LineBorder(new Color(243,232,214), 3));
			txtPassword.addFocusListener(this);
//			txtPassword.setBackground(new Color(204,204,255));
			password.add(txtPassword, BorderLayout.CENTER);
		}
		return password;
	}
	
	//비밀번호 확인칸
	public JPanel getPasswordCheck() {
		if(passwordCheck == null) {
			passwordCheck = new JPanel();
			passwordCheck.setBackground(Color.white);
			passwordCheck.setLayout(new BorderLayout());
			passwordCheck.setBorder(new EmptyBorder(20,0,0,83));
			JLabel label = new JLabel("비밀번호 확인");
			label.setFont(new Font("맑은 고딕", Font.PLAIN, 10));
			label.setPreferredSize(new Dimension(90, 30));
			label.setHorizontalAlignment(JLabel.RIGHT);
			label.setBorder(new EmptyBorder(0,0,0,5));
			passwordCheck.add(label, BorderLayout.WEST);
			txtPasswordCheck = new JPasswordField();
			txtPasswordCheck.setPreferredSize(new Dimension(180,30));
			txtPasswordCheck.setBorder(new LineBorder(new Color(243,232,214), 3));
			txtPasswordCheck.addFocusListener(this);
//			txtPassword.setBackground(new Color(204,204,255));
			passwordCheck.add(txtPasswordCheck, BorderLayout.CENTER);
		}
		return passwordCheck;
	}

	
	public JPanel getBottom() {
		if(bottom == null) {
			bottom = new JPanel();
			bottom.setBackground(Color.white);
			bottom.setPreferredSize(new Dimension(430, 120));
			bottom.add(getBtnNewMember());
			bottom.add(getBtnLogin());
		}
		return bottom;
	}
	
	
	//회원가입 버튼
	public RoundedButton getBtnNewMember() {
		if(btnNewMember == null) {
			btnNewMember = new RoundedButton();
			btnNewMember.setPreferredSize(new Dimension(250,35));
			btnNewMember.setText("회원가입");
			btnNewMember.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					emailBoolean = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", txtEmail.getText());
					passwordBoolean = Pattern.matches("\\w+", txtPassword.getText());
					if(txtNickname.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "닉네임을 입력해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
						txtNickname.requestFocus();
					}else if(txtEmail.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "E-Mail을 입력해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
						txtEmail.requestFocus();
					}else if(emailBoolean==false) {
						JOptionPane.showMessageDialog(null, "E-Mail형식이 잘못되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
						txtEmail.requestFocus();
					}else if(txtEmailCheck.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "인증번호를 입력해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
						txtEmailCheck.requestFocus();
					}else if(!txtEmailCheck.getText().equals(stringBuilder.toString())) {
						JOptionPane.showMessageDialog(null, "인증번호를 잘못입력하셨습니다.","확인",JOptionPane.PLAIN_MESSAGE);
						txtEmailCheck.requestFocus();
					}else if(txtPassword.getText().equals("")){
						JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
						txtPassword.requestFocus();
					}else if(passwordBoolean==false) {
						JOptionPane.showMessageDialog(null, "잘못된 비밀번호 형식입니다. 뛰어쓰기나 특수문자가 있는지 확인해주세요","확인",JOptionPane.PLAIN_MESSAGE);
						txtPassword.requestFocus();
					}else if(!txtPassword.getText().equals(txtPasswordCheck.getText())) {
						JOptionPane.showMessageDialog(null, "비밀번호가 다릅니다 확인해주세요.","확인",JOptionPane.PLAIN_MESSAGE);
						txtPassword.requestFocus();
					}else if(!UserDAO.getInstance().getUserEmail(txtEmail.getText()).equals("")){
						JOptionPane.showMessageDialog(null, "동일한 아이디가 존재합니다. 다시 입력해주세요","확인",JOptionPane.PLAIN_MESSAGE);
						txtEmail.requestFocus();
					}else {
						UserDTO user = new UserDTO();
						user.setEmail(txtEmail.getText());
						user.setPassword(txtPassword.getText());
						user.setNickname(txtNickname.getText());
						UserDAO.getInstance().insertUser(user);
						dispose();
						Login login = new Login();
						login.setVisible(true);
					}
					
				}
			});
		}
		return btnNewMember;
	}
	
	public JButton getBtnLogin() {
		if(btnLogin == null) {
			btnLogin = new JButton();
			btnLogin.setText("로그인창으로 돌아가기");
			btnLogin.setPreferredSize(new Dimension(250,35));
			btnLogin.setBorderPainted(false);
			btnLogin.setContentAreaFilled(false);
			btnLogin.setFocusPainted(false);
			btnLogin.setOpaque(false);
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
					Login login = new Login();
					login.setVisible(true);
				}
			});
		}
		return btnLogin;
		
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
        	JoinMember board = new JoinMember();
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