package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemoDAO extends MainDAO{
	private static final MemoDAO instance  = new MemoDAO();
	public MemoDTO memoDTO;
	
	public static MemoDAO getInstance() {
		return instance;
	}
	//그 날에 해당하는 메모 들고 오기
	public MemoDTO getMemo(Date selectdate, String email) {
		memoDTO = null;
		try {
			connect();
			
			String sql = """
					select *
					from memos
					where email = ? and 
					date = ? and memotype = 'memo'
					""";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setDate(2, selectdate);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				memoDTO = new MemoDTO();
				memoDTO.setMemoNo(rs.getInt(1));
				memoDTO.setEmail(rs.getString(2));
				memoDTO.setDate(rs.getDate(3));
				memoDTO.setMemo(rs.getString(4));
				memoDTO.setType(rs.getString(5));
			}
			close();
			
		}catch(SQLException e) {}
		
		return memoDTO;
	}
	
	
	//그 날에 해당하는 일기 들고 오기
	public MemoDTO getDiary(Date selectdate, String email) {
		memoDTO = null;
		try {
			connect();
			
			String sql = """
					select *
					from memos
					where email = ? and 
					date = ? and memotype = "diary"
					""";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setDate(2, selectdate);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				memoDTO = new MemoDTO();
				memoDTO.setMemoNo(rs.getInt(1));
				memoDTO.setEmail(rs.getString(2));
				memoDTO.setDate(rs.getDate(3));
				memoDTO.setMemo(rs.getString(4));
				memoDTO.setType(rs.getString(5));
				
				close();
			}
		}catch(SQLException e) {}
		
		return memoDTO;
	}
	
	//만들기
	public void memoDtoInsert(MemoDTO dto, Date selectdate) {
		try {
			connect();
			
			String  sql = """
					insert into memos (email, date, memo, memotype) 
					values (? , ?, ?, ?)
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEmail());
			pstmt.setDate(2, selectdate);
			pstmt.setString(3, dto.getMemo());
			pstmt.setString(4, dto.getType());
		
			int row = pstmt.executeUpdate();
			
			close();
		}catch(SQLException e) {}
	}
	
	
	//수정하기
	public void memoDtoUpdate(MemoDTO dto) {
		try {
			connect();
			
			String sql = """
					update memos set
					date = now(),
					memo = ?
					where memoNo = ?
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getMemo());
			pstmt.setInt(2, dto.getMemoNo());
			int row = pstmt.executeUpdate();
			
			close();
			
		}catch(SQLException e) {}
	}
	
	
	
	//삭제하기
	public void memoDtoDelete(MemoDTO dto) {
		try {
			connect();
			
			String sql = """
					delete from memos where memoNo = ?
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getMemoNo());
			
			int row = pstmt.executeUpdate();
			
			close();
			
		}catch(SQLException e) {}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
