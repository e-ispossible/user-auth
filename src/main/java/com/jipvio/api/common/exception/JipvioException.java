package com.jipvio.api.common.exception;

import lombok.Getter;

@Getter
public class JipvioException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private int code;
	private String reason;
	
	public JipvioException(Error error, String message) {
		this(error.getMessage(), message, error.getCode());
	}
	public JipvioException(Error error) {
		this(error.getMessage(), error.name(), error.getCode());
	}
	
	public JipvioException(String message, String reason, int code) {
		super(message);
		this.reason = reason;
		this.code = code;
	}
}
