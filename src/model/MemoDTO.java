package model;

import java.sql.Date;

import lombok.*;

@Data
@AllArgsConstructor
public class MemoDTO {

	private int memoNo;
	private String email;
	private Date date;
	private String memo;
	private String type;
	
	public MemoDTO() {}
}
