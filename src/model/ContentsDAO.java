package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.xpath.XPathResult;

public class ContentsDAO extends MainDAO{

	//기간 날짜에 속한 일정만 찾아 dto 리스트 만들기
	public List<ContentsDTO> getWillDTO(Date selectDate){
		List<ContentsDTO> dtoList = new ArrayList<ContentsDTO>();
		
		try {
			
			connect();
			
			String sql = """
					select *
					from contents
					where startdate <= ? and 
					enddate >= ? and 
					email = ? 
					""";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, selectDate);
			pstmt.setDate(2, selectDate);
			pstmt.setDate(3, ContentsDTO);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ContentsDTO conDTO = new ContentsDTO();
			}
			
			
			
		}catch(SQLException e) {}
		
		return dtoList;
		
		
	}
	//finishdate와 일치한 날만 찾아서 dto 리스트 만들기
	// dto받아서 DB에 넣기
	// dto받아서 DB에 수정하기
	// dto받아서 DB에 삭제하기
	
}
