package com.jipvio.user.config;

import static com.jipvio.api.common.ApiResponse.emptyResponse;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jipvio.api.common.exception.JipvioException;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jipvio.api.common.ApiResponse;
import com.jipvio.api.common.exception.Error;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletResponse;
@Component
@Slf4j
public class SessionApiInterceptor extends HandlerInterceptorAdapter{
	static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("=============preHandle============");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		
		HttpSession session = httpReq.getSession(false);
		if(session == null) {
			String authToken = getAuthToken(httpReq);
			log.error("no session with {}", authToken);
			sendUnauthorizedErrorResponse(httpRes);
			return false;
		}
		
		return true;
	}
	
	private String getAuthToken(HttpServletRequest request) {
		Enumeration<String> authTokens = request.getHeaders(AUTH_TOKEN_HEADER);
		while(authTokens.hasMoreElements()) {
			return authTokens.nextElement();
		}
		return null;
	}
	private void sendUnauthorizedErrorResponse(ServletResponse response) throws JsonProcessingException, IOException {
		ApiResponse<Void> errRes = emptyResponse();
		errRes.setHttpError(Error.UnAuthorized, "Unauthorized Request");
		HttpServletResponse httpRes = (HttpServletResponse) response;
		httpRes.setHeader("Access-Control-Allow-Origin", "*");
		httpRes.setContentType("application/json);charset=UTF-8");
		httpRes.getWriter().write(getSerializedJsonBytes(errRes));
		httpRes.getWriter().flush();
	}
	

	private String getSerializedJsonBytes(ApiResponse<Void> errResponse) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(errResponse);
	}
}