package com.salle.user.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.salle.user.exception.custom.UnauthorizedRequestException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionApiInterceptor extends HandlerInterceptorAdapter{
	static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("============== Entering intercepter ==========");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		
		log.info("request method {}", httpReq.getMethod());
		
		if(crossOriginPreflight(httpReq, httpRes)) return false;
		
		String authToken = getAuthToken(httpReq);
		HttpSession session = httpReq.getSession(false);
		if(session == null) {
			log.info("no session with {}", authToken);
			throw new UnauthorizedRequestException("");
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
	
	private boolean crossOriginPreflight(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getMethod().contentEquals("OPTIONS")) {
			response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
			response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
			response.getWriter().flush();
			log.info("preflight response");
			return true;
		}
		return false;
	}
}
