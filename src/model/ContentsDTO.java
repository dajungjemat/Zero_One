package model;

import java.sql.Date;
import lombok.*;

@Data
@AllArgsConstructor
public class ContentsDTO {
	
	private int contentNo;
	private String email;
	private Date startDate;
	private Date endDate;
	private String content;
	private String timer;
	private String access;
	private Date finishdate;
	public boolean check = false;

	public ContentsDTO() {}
}
