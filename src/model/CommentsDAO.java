package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CommentsDAO extends MainDAO{
	
	private static final CommentsDAO instance = new CommentsDAO();
	
	public static CommentsDAO getInstance() {
		return instance;
	}
	
	
	public void insertComment(CommentsDTO comments) {
		connect();
		sql = """
				insert into comments (commentNo, commentDate, comment,boardNo)
				values(?,now(),?,?)
				""";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comments.getCommentNo());
			pstmt.setString(2, comments.getComment());
			pstmt.setInt(3, comments.getBoardNo());
			
			int rows = pstmt.executeUpdate();
			if(rows == 1) {
				JOptionPane.showMessageDialog(null, "댓글 등록","확인",JOptionPane.PLAIN_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "댓글 등록 실패","확인",JOptionPane.WARNING_MESSAGE);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "댓글 등록 오류","확인",JOptionPane.WARNING_MESSAGE);
		}
		
		
	}
	
	public List<CommentsDTO> getComments() {
		connect();
		String sql = "select * from comments order by commentNo ";
		List<CommentsDTO> commentList = new ArrayList<>();
		
	
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CommentsDTO comment = new CommentsDTO();
				comment.setCommentNo(rs.getInt(1));
				comment.setCommentDate(rs.getDate(2));
				comment.setComment(rs.getString(3));
				comment.setBoardNo(rs.getInt(4));
				
				commentList.add(comment);
			}
			close();
		}catch(Exception e) {
			e.printStackTrace();
			/*
			 * JOptionPane.showMessageDialog(null, "오류","확인",JOptionPane.WARNING_MESSAGE);
			 */
		}
		
		return commentList;
	}
	
	//댓글 삭제, 수정 메소드 
	
	
}
//insertCommetn 댓글 등록
//getCommentByCommentNo 댓글 뜨는 화면
//deleteComment 댓글 삭제 