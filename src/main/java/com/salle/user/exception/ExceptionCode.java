package com.salle.user.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExceptionCode {
	INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
	// Exception
    NULL_BODY(400, "C007", "Required request body is null")
    
    ;
	
	private int status;
	private final String code;
	private final String message;
	
	ExceptionCode(final int status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public int getStatus() {
		return this.status;
	}
}
