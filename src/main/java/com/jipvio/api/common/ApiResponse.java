package com.jipvio.api.common;

import java.sql.SQLException;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jipvio.api.common.exception.JipvioException;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.jipvio.api.common.exception.Error;
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	private T payload;
	private String message;
	private PagingInfo pagingInfo;
	private ErrDetail detail;
	
	public static<P> ApiResponse<P> emptyResponse() {
		return new ApiResponse<P>();
	}
	
	public void setPayload(T payload) {
		this.payload = payload;
	}
	public void setPage(Page<?> page) {
		this.pagingInfo = new PagingInfo();
		this.pagingInfo.pageNo = page.getNumber();
		this.pagingInfo.totalPages = page.getTotalPages();
		this.pagingInfo.totalCnt = page.getTotalElements();
		this.pagingInfo.size = page.getSize();
	}
	public void setError(Throwable t) {
		this.message = t.getMessage();
		this.detail = new ErrDetail();
		if(t instanceof JipvioException) {
			this.detail.code = ((JipvioException)t).getCode();
			this.detail.message = ((JipvioException)t).getReason();
			return;
		}
		Throwable cause;
		cause = t.getCause();
		if(cause != null) {
			while(true) {
				if(cause.getCause()!= null) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if(cause instanceof SQLException) {
				detail.code = ((SQLException)cause).getErrorCode();
			}
			detail.message = cause.getMessage();
		}
	}
	public void setHttpError(Error err, String message) {
		if(message == null) this.message = err.name();
		else this.message = message;
		
		detail = new ErrDetail();
		detail.setCode(err.getCode());
		detail.setMessage(err.getMessage());
	}

	@Getter
	@Setter
	@ToString
	public class PagingInfo {
		private Long totalCnt;
		private int totalPages;
		private int pageNo;
		private Integer size;
	}
	@Getter
	@Setter
	@ToString
	public class ErrDetail {
		private int code;
		private String message;
	}
}

