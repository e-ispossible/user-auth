package com.jipvio.api.common;

import java.util.List;

public class ApiUtil {
	public static PagingRequest buildClientRequest(Boolean usePaging, Integer pageNo, Integer sizePerPage, List<String> orderByProperties){
		PagingRequest req = new PagingRequest();
		if(usePaging) {
			PagingRequest.PagingInfo pagingInfo = new PagingRequest.PagingInfo();
			pagingInfo.setPageNo(pageNo);
			pagingInfo.setSizePerPage(sizePerPage);
			if(orderByProperties != null) pagingInfo.buildOrderByProperties(orderByProperties);
			
			req.setPagingInfo(pagingInfo);
		}
		return req;
	}
}
