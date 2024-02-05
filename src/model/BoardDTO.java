package model;

import java.sql.Date;
import lombok.*;

@Data
@AllArgsConstructor
public class BoardDTO {

	private int boardNo;
	private String	email;
	private Date boardDate;
	private String title;
	private String boardContent;
}
