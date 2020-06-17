package com.jipvio.api.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingRequest {
	private PagingInfo pagingInfo;
	
	public boolean usePaging() {
		return this.pagingInfo != null;
	}
	
	@Getter
	@Setter
	@ToString
	public static class PagingInfo {
		private Integer pageNo;
		private Integer sizePerPage;
		private List<OrderBy> orderByList;
		
		public Pageable toPageable() {
			return PageRequest.of(this.pageNo == null ? 0 : this.pageNo,this.sizePerPage == null ? 10 : this.sizePerPage,
					(orderByList == null) ? Sort.unsorted() : Sort.by(orderByList.stream().map(this::toOrder).collect(Collectors.toList())));		
					
		}
		public void buildOrderByProperties(List<String> orderByProperties) {
			this.orderByList = orderByProperties.stream().map(this::toOrderBy).collect(Collectors.toList());
		}
		
		private OrderBy toOrderBy(String orderByParam) {
			OrderBy orderBy = new OrderBy();
			String [] propAndDir = orderByParam.split(":");
			orderBy.setProperty(propAndDir[0]);
			orderBy.setDirection(propAndDir[1]);
			return orderBy;
		}
		
		private Order toOrder(OrderBy orderBy) {
			return("desc".equals(orderBy.getDirection()) ? Order.desc(orderBy.getProperty()) : Order.asc(orderBy.getProperty()));
		}
	}
	
	@Getter
	@Setter
	@ToString
	private static class OrderBy {
		private String direction;
		private String property;
	}

}