package com.salle.user.exception.custom;

public class UnauthorizedHttpRequestException extends RuntimeException {
	public static final String errorCode = "unauthorized.req";
	public UnauthorizedHttpRequestException(String message) {
		super(message);
	}
	public UnauthorizedHttpRequestException(String message, Throwable cause) {
		super(message, cause);
	}
}
