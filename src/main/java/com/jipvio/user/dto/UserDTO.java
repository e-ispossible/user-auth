package com.jipvio.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.jipvio.user.entity.User;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String password;
	
	private Long userRoleId;
	private Long userTypeId;
	
	public User toUserEntity(String password) {
		User user = new User();
		user.setEmail(email);
		user.setPassword(password);
		
		user.setUserRoleId(userRoleId);
		user.setUserTypeId(userTypeId);
		return user;
	}
}
