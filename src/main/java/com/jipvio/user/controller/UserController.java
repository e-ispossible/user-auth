package com.jipvio.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jipvio.api.common.ApiResponse;
import com.jipvio.user.dto.AuthDTO;
import com.jipvio.user.dto.UserDTO;
import com.jipvio.user.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import static com.jipvio.api.common.ApiResponse.emptyResponse;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/user", produces="application/json;charset=UTF-8")
@Slf4j
public class UserController {
	@Autowired
	private UserService usrSrv;
	
	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-Auth-Token", required = false, dataType = "string", paramType = "header", defaultValue = "")
	})
	public ResponseEntity<ApiResponse<Long>> saveUser(@RequestBody @Valid UserDTO userDTO) {
		ApiResponse<Long> response = emptyResponse();
		try {
			response.setPayload(usrSrv.save(userDTO));
		} catch(Exception e) {
			log.error("saveUser: \n{}",e);
			response.setError(e);
		}
		
		return new ResponseEntity<ApiResponse<Long>>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "토큰 발행")
	@PostMapping("/authenticate")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-Auth-Token", required = false, dataType = "string", paramType = "header", defaultValue = "")
	})
	public ResponseEntity<ApiResponse<String>> authenticate(
			@RequestBody @Valid AuthDTO authReq, HttpServletRequest request
	){
		ApiResponse<String> response = emptyResponse();
		try {
			response.setPayload(usrSrv.authenticate(authReq, request));
		} catch(Exception e) {
			log.error("authenticate: \n{}", e);
			response.setError(e);
		}
		
		return new ResponseEntity<ApiResponse<String>>(response,HttpStatus.OK);
	}
}
