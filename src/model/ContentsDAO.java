package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.xpath.XPathResult;

public class ContentsDAO extends MainDAO{
	
	private static final ContentsDAO instance = new ContentsDAO();
	public List<ContentsDTO> dtoList;
	
	public static ContentsDAO getInstance() {
		return instance;
	}

	//기간 날짜에 속한 일정만 찾아 dto 리스트 만들기
	public List<ContentsDTO> getWillDTO(Date selectDate, String email){
		dtoList = new ArrayList<ContentsDTO>();
		
		try {
			
			connect();
			
			String sql = """
					select *
					from contents
					where startdate <= ? and 
					enddate >= ? and 
					email = ? and finishdate is null 
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, selectDate);
			pstmt.setDate(2, selectDate);
			pstmt.setString(3, email);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ContentsDTO conDTO = new ContentsDTO();
				conDTO.setContentNo(rs.getInt(1));
				conDTO.setEmail(rs.getString(2));
				conDTO.setStartDate(rs.getDate(3));
				conDTO.setEndDate(rs.getDate(4));
				conDTO.setContent(rs.getString(5));
				conDTO.setTimer(rs.getString(6));
				conDTO.setAccess(rs.getString(7));
				conDTO.setFinishdate(rs.getDate(8));
				
				dtoList.add(conDTO);
			}
			
			close();
	
		}catch(SQLException e) {}
		
		return dtoList;
	
	}
	
	//finishdate와 일치한 날만 찾아서 dto 리스트 만들기
	public List<ContentsDTO> getDidDTO(Date selectDate, String email){
		
		dtoList = new ArrayList<ContentsDTO>();
		
		try {
			
			connect();
			
			String sql = """
					select *
					from contents
					where startdate <= ? and 
					enddate >= ? and 
					email = ? and finishdate is not null 
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, selectDate);
			pstmt.setDate(2, selectDate);
			pstmt.setString(3, email);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ContentsDTO conDTO = new ContentsDTO();
				conDTO.setContentNo(rs.getInt(1));
				conDTO.setEmail(rs.getString(2));
				conDTO.setStartDate(rs.getDate(3));
				conDTO.setEndDate(rs.getDate(4));
				conDTO.setContent(rs.getString(5));
				conDTO.setTimer(rs.getString(6));
				conDTO.setAccess(rs.getString(7));
				conDTO.setFinishdate(rs.getDate(8));
				
				dtoList.add(conDTO);
			}
			
			close();
	
		}catch(SQLException e) {}
		
		return dtoList;
	}
	
	
	// dto받아서 DB에 넣기
	public void contentsDtoInsert(ContentsDTO dto) {
		try {
			connect();
			
			String sql = """
				insert into contents (
				email, startdate, enddate, content, timer, access, finishdate) 
				values (?, ?, ?, ?, ?, ?, ?)
				""";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getEmail());
			pstmt.setDate(2, dto.getStartDate());
			pstmt.setDate(3, dto.getEndDate());
			pstmt.setString(4, dto.getContent());
			pstmt.setString(5, dto.getTimer());
			pstmt.setString(6, dto.getAccess());
			pstmt.setDate(7, dto.getFinishdate());
			
			int row = pstmt.executeUpdate();
			
			close();
			
		}catch(SQLException e) {}
	}
	
	
	// dto받아서 DB에 수정하기
	public void contentsDtoUpdate(ContentsDTO dto) {
		try {
			connect();
			
			String sql = """
					update contents set
					startdate = ?,
					enddate = ?,
					content = ?
					where = contentNo = ?
					""";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, dto.getStartDate());
			pstmt.setDate(2, dto.getEndDate());
			pstmt.setString(3, dto.getContent());
			pstmt.setInt(4, dto.getContentNo());
			
			int row = pstmt.executeUpdate();
			
			close();
			
		}catch(SQLException e) {}
	}

	
	// dto받아서 DB에 삭제하기
	public void contentsDtoDelete(ContentsDTO dto) {
		try {
			connect();
			
			String sql = """
					delete from contents where contentNo = ?
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getContentNo());
			
			int row = pstmt.executeUpdate();
			
			close();
			
		}catch(SQLException e) {}
	}
	
	
	//할 일을 한 일로 >> 다하면 finishdate 설정
	public void contentFinish(ContentsDTO dto) {
		try {
			connect();
			
			String sql = """
					update contents set
					finishdate = now()
					where contentNo = ?
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);		
			pstmt.setInt(1, dto.getContentNo());
			int row = pstmt.executeUpdate();
			
			close();
			
		}catch(SQLException e) {}

		
	}
	
	
	//한 일을 할 일로 >> finishdate null
	public void contentRevert(ContentsDTO dto) {
		try {
			connect();
			
			String sql = """
					update contents set
					finishdate = null
					where contentNo = ?
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getContentNo());
			int row = pstmt.executeUpdate();
			
			close();
			
		}catch(SQLException e) {}
	}

	
}
