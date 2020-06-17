package com.jipvio.api.common.exception;

import lombok.Getter;

@Getter
public enum Error {
	UnAuthorized(1000, "인증을 실패했습니다."),
	InValidRequest(1001, "적절한 요청이 아닙니다."),
	EmailAlreadyExists(1003, "존재하는 이메일 입니다."),
	UserNotFound(1004, "존재하지 않는 계정 입니다."),
	InvalidPassword(1005, "비밀번호가 틀렸습니다.");
	private int code;
	private String message;
	private Error(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
