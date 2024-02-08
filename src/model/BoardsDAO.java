package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BoardsDAO extends MainDAO{
	
	private static final BoardsDAO instance = new BoardsDAO();
	
	public static BoardsDAO getInstance() {
		return instance;
	}
	
	
	public void insertBoards(BoardsDTO boards) {
		connect();
		sql = """
				insert into boards (title, boardContent,boardDate,email,hitcount)
				values (?,?,now(),?,0)
				""";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,boards.getTitle());
			pstmt.setString(2,boards.getBoardContent());
			pstmt.setString(3,boards.getEmail());
			
			int rows = pstmt.executeUpdate();
			
			if (rows == 1) { 
				JOptionPane.showMessageDialog(null, "게시글 등록에 성공했습니다.","확인",JOptionPane.PLAIN_MESSAGE);		
			}else {
				JOptionPane.showMessageDialog(null, "게시글 등록에 실패했습니다.","확인",JOptionPane.WARNING_MESSAGE);		
			}
			close();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "게시글 등록에 오류가 생겼습니다.","확인",JOptionPane.WARNING_MESSAGE);		
		}
	
		
	}//게시글 저장 쿼리 -postDialog

	
	public void updateBoards(BoardsDTO boards, int boardNo) {
		connect();
		sql = """
				update boards 
				set title=?, boardContent =?
				where boardNo = ?
				""" ;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,boards.getTitle());
			pstmt.setString(2,boards.getBoardContent());
			pstmt.setInt(3, boardNo);
			
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "게시글이 수정되었습니다.","확인",JOptionPane.PLAIN_MESSAGE);		
			}else {
				JOptionPane.showMessageDialog(null, "게시글 수정에 실패했습니다.","확인",JOptionPane.WARNING_MESSAGE);		
			}
			close();

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "게시글 수정에 오류가 생겼습니다.","확인",JOptionPane.WARNING_MESSAGE);				
		}		//게시글 수정 쿼리 
	}
	
	public List<BoardsDTO> getBoards() {
		connect();
		String sql = "select * from boards order by boardNo";
		List<BoardsDTO> boardsList = new ArrayList<>();
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardsDTO board = new BoardsDTO();
				board.setBoardNo(rs.getInt(1));
				board.setTitle(rs.getString(2));
				board.setBoardContent(rs.getString(3));
				board.setBoardDate(rs.getDate(4));
				board.setEmail(rs.getString(5));
				board.setHitcount(rs.getInt(6));
				
				boardsList.add(board);
			}
			close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return boardsList;
	}//게시글 호출 쿼리 
	
	public BoardsDTO getBoardsByBoardNo(int boardNo) {
		connect();
		BoardsDTO board = new BoardsDTO();
		sql = "select * from boards where boardNo=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board.setTitle(rs.getString("title"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setEmail(rs.getString("email"));
				board.setHitcount(rs.getInt("hitcount"));
				board.setBoardNo(rs.getInt("boardNo"));
				board.setBoardDate(rs.getDate("boardDate"));
			}
			close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return board;
	}
	
	
	
	public void deleteBoards(int boardNo) {
		connect();
		sql = "delete from boards where boardNo=?";		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "게시글이 삭제됐습니다.","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "게시글 삭제에 실패했습니다.","확인",JOptionPane.WARNING_MESSAGE);
			}
			close();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "삭제 오류","확인",JOptionPane.WARNING_MESSAGE);
		}//게시글 삭제 
		
	}
	
	public void addHitcount(int hitcount, int boardNo) {
		connect();
		
		sql = """
				update boards
				set hitcount =?
				where boardNo = ?
				""";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hitcount+1);
			pstmt.setInt(2, boardNo);
			pstmt.executeUpdate();
			close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}//조회수 증가 

}
