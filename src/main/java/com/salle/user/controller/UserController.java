package com.salle.user.controller;

import static com.salle.user.common.ApiResponse.emptyResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salle.user.common.ApiResponse;
import com.salle.user.common.AuthRequest;
import com.salle.user.common.AuthResponse;
import com.salle.user.dto.UserDTO;
import com.salle.user.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
	@Autowired
	private UserService usrSrv;
	
	@PostMapping("/auth")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-Auth-Token", required = false, dataType = "string", paramType = "header", defaultValue = "")
	})
	public ResponseEntity<AuthResponse<Void>> authenticate(
			@RequestBody @Valid AuthRequest authReq, HttpServletRequest request
	){
		return new ResponseEntity<AuthResponse<Void>>(usrSrv.authenticate(authReq, request),HttpStatus.OK);
	}
	
	@DeleteMapping("/auth")
	public ResponseEntity<ApiResponse<Void>> invalidateAuthToken(
			@RequestBody @Valid AuthRequest authReq, HttpServletRequest request
	){
		ApiResponse<Void> response = emptyResponse();
		usrSrv.invalidateAuthToken(request);
		response.setMessage("Auth token invalidated");
		return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.OK);
	}
	
	@PostMapping("")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-Auth-Token", required = false, dataType = "string", paramType = "header", defaultValue = "")
	})
	public ResponseEntity<ApiResponse<Long>> saveUser(@RequestBody @Valid UserDTO user) {
		ApiResponse<Long> response = emptyResponse();
		response.setPayload(usrSrv.save(user));
		
		return new ResponseEntity<ApiResponse<Long>>(response, HttpStatus.OK);
	}
	
	
}
