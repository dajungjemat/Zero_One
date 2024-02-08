package model;

import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private String password;
	private boolean userType;
	private String nickname;
}
