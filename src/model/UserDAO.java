package model;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class UserDAO extends MainDAO{
	private static final UserDAO instance = new UserDAO();

	public static UserDAO getInstance() {
		return instance;
	}
	
	//회원정보 데이터베이스에 입력메소드
	public void insertUser(UserDTO board) {
		connect();
		sql = """
				insert into users (email, userpassword, userType, nickname)
				values (?, ?, false, ?)
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getEmail());
			pstmt.setString(2, board.getPassword());
			pstmt.setString(3, board.getNickname());
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "회원가입이 되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "회원가입을 할 수 없습니다. 다시 확인해주세요","확인",JOptionPane.PLAIN_MESSAGE);	
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "회원가입을 할 수 없습니다. 다시 확인해주세요","확인",JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	//데이터베이스에서 닉네임 중복 검사하기 위해 닉네임 검색메소드
	public String getUserNickName(String name) {
		connect();
		String str = "";
		sql = "select nickname from users where nickname=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				str = rs.getString("nickname");				
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	//데이터베이스에서 email일 가져오는 메소드
	public String getUserEmail(String email) {
		connect();
		String str = "";
		sql = "select email from users where email=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				str = rs.getString("email");				
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	//데이터베이스에서 이메일에 해당하는 비밀번호 가져오는 메소드
	public String getUserPassword(String email) {
		connect();
		String str = "";
		sql = "select userpassword from users where email=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				str = rs.getString("userpassword");				
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	public String getUsernickname(String email) {
		connect();
		String str = "";
		sql = "select nickname from users where email=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				str = rs.getString("nickname");				
			}
			close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	
}
