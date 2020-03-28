package com.salle.user.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ExceptionResponse {
	private int status;
	private String message;
	private String code;
	private List<FieldError> errors;
	
	private ExceptionResponse(final ExceptionCode code, final List<FieldError> errors) {
	        this.message = code.getMessage();
	        this.status = code.getStatus();
	        this.errors = errors;
	        this.code = code.getCode();
	}
	private ExceptionResponse(final ExceptionCode code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
        this.errors = new ArrayList<>();
    }
	
	public static ExceptionResponse of(final ExceptionCode code, final BindingResult bindingResult) {
		return new ExceptionResponse(code, FieldError.of(bindingResult));
	}
	public static ExceptionResponse of(final ExceptionCode code) {
		return new ExceptionResponse(code);
	}
	public static ExceptionResponse of(final ExceptionCode code, final List<FieldError> errors) {
		return new ExceptionResponse(code, errors);
	}
	public static ExceptionResponse of(MethodArgumentTypeMismatchException ex) {
		final String value = ex.getValue() == null ? "" : ex.getValue().toString();
		final List<ExceptionResponse.FieldError> errors = ExceptionResponse.FieldError.of(ex.getName(), value, ex.getErrorCode());
		return new ExceptionResponse(ExceptionCode.INVALID_TYPE_VALUE, errors);
	}
	
	@Getter
	@NoArgsConstructor
	public static class FieldError {
		private String field;
		private String value;
		private String reason;
		
		private FieldError(final String field, final String value, final String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}
		
		public static List<FieldError> of(final String field, final String value, final String reason) {
			List<FieldError> fieldErrors = new ArrayList<>();
			fieldErrors.add(new FieldError(field, value, reason));
			return fieldErrors;
		}
		
		private static List<FieldError> of(final BindingResult bindingResult) {
			final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
			return fieldErrors.stream().map(error -> new FieldError(
								error.getField(), 
								error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
								error.getDefaultMessage()))
								.collect(Collectors.toList());
		}
	}
}
