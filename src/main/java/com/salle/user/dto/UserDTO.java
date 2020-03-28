package com.salle.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import lombok.Data;

@Data
public class UserDTO {
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
}
