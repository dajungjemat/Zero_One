package model;

import java.sql.SQLException;

public class Doit {

	public static void main(String[] args) {
		MainDAO dao = MainDAO.getInstance();
		try {
			dao.connect();
			System.out.println("연결 됨");
			dao.close();
			System.out.println("연결 취소");
		}catch(SQLException e) {}

		
		

	}

}
