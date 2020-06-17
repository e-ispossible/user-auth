package com.jipvio.api.common;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSession implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private Long userRoleId;
}