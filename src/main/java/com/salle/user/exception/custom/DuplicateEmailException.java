package com.salle.user.exception.custom;

public class DuplicateEmailException extends RuntimeException{
	public DuplicateEmailException(String message) {
		super(message);
	}
	
	public DuplicateEmailException(String message, Throwable cause) {
		super(message, cause);
	}
}
