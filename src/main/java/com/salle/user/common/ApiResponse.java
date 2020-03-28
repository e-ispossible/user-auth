package com.salle.user.common;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	private T payload;
	private String message;
	private PagingInfo pagingInfo;
	
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

	@Getter
	@Setter
	@ToString
	public class PagingInfo {
		private Long totalCnt;
		private int totalPages;
		private int pageNo;
		private Integer size;
	}
}
