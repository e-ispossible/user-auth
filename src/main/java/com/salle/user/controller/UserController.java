package com.salle.user.controller;

import static com.salle.user.common.ApiResponse.emptyResponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salle.user.common.ApiResponse;
import com.salle.user.common.AuthRequest;
import com.salle.user.common.AuthResponse;
import com.salle.user.domain.User;
import com.salle.user.dto.UserDTO;
import com.salle.user.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
	@Autowired
	private UserService usrSrv;
	
	@ApiOperation(value = "토큰 발행")
	@PostMapping("/auth")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-Auth-Token", required = false, dataType = "string", paramType = "header", defaultValue = "")
	})
	public ResponseEntity<AuthResponse<Void>> authenticate(
			@RequestBody @Valid AuthRequest authReq, HttpServletRequest request
	){
		return new ResponseEntity<AuthResponse<Void>>(usrSrv.authenticate(authReq, request),HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "X-Auth-Token", required = false, dataType = "string", paramType = "header", defaultValue = "")
	})
	public ResponseEntity<ApiResponse<Long>> saveUser(@RequestBody @Valid UserDTO userDTO) {
		ApiResponse<Long> response = emptyResponse();
		response.setPayload(usrSrv.save(userDTO));
		
		return new ResponseEntity<ApiResponse<Long>>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "로그아웃")
	@DeleteMapping("/invalidate")
	public ResponseEntity<ApiResponse<Void>> invalidateAuthToken(
			@RequestBody @Valid AuthRequest authReq, HttpServletRequest request
	){
		ApiResponse<Void> response = emptyResponse();
		usrSrv.invalidateAuthToken(request);
		response.setMessage("Auth token invalidated");
		return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원수정")
	@PutMapping("")
	public ResponseEntity<ApiResponse<Void>> modifyUser(
			@RequestBody UserDTO userDTO
	){
		ApiResponse<Void> response = emptyResponse();
		usrSrv.update(userDTO);
		response.setMessage("Successfully deleted");
		return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원탈퇴")
	@DeleteMapping("/{uid}")
	public ResponseEntity<ApiResponse<Void>> removeUser(
			@PathVariable("uid") Long uid
	){
		ApiResponse<Void> response = emptyResponse();
		usrSrv.delete(uid);
		response.setMessage("Successfully deleted");
		return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "복수회원탈퇴")
	@DeleteMapping("")
	public ResponseEntity<ApiResponse<Void>> removeAllUsers(
			@RequestBody List<User> userList 
	){
		ApiResponse<Void> response = emptyResponse();
		usrSrv.deleteAll(userList);
		response.setMessage("Successfully deleted");
		return new ResponseEntity<ApiResponse<Void>>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원조회")
	@GetMapping("/{uid}")
	public ResponseEntity<ApiResponse<User>> getUser(
			@PathVariable("uid") Long uid
	){
		ApiResponse<User> response = emptyResponse();
		response.setPayload(usrSrv.get(uid));
		return new ResponseEntity<ApiResponse<User>>(response, HttpStatus.OK);
	}
}
