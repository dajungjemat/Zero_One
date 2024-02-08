package model;



import java.sql.Date;

import lombok.Data;

@Data
public class BoardsDTO {
	private int boardNo;
	private String title;
	private String boardContent;
	private Date boardDate;
	private String email;
	private int hitcount;
}
